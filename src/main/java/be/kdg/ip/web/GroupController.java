package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.resources.GroupResource;
import be.kdg.ip.web.resources.GroupUserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/allgroups")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<GroupUserResource>> findAll() {
        List<Group> groups = groupService.getAllGroups();
        List<GroupUserResource> groupUserResources = new ArrayList<>();
        List<Integer> userids = new ArrayList<>();
        for (Group group : groups) {
            GroupUserResource groupUserResource = new GroupUserResource();
            groupUserResource.setGroupid(group.getGroupId());
            groupUserResource.setGroupimage(group.getGroupImage());
            groupUserResource.setName(group.getName());
            groupUserResource.setSupervisor(group.getSupervisor());
            groupUserResource.setUsers(new ArrayList<>());

            for (User user : group.getUsers()) {
                groupUserResource.getUsers().add(user);
                userids.add(user.getId());
                if (!user.getGroups().contains(group)) {
                    user.getGroups().add(group);
                }
                userService.updateUser(user);
            }
            groupUserResource.setUserids(userids);
            groupUserResources.add(groupUserResource);
        }

        return new ResponseEntity<>(groupUserResources, HttpStatus.OK);
    }

    @PostMapping
    //ToDo: Authorization fix: group post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<GroupUserResource> postNewGroup(@RequestBody GroupResource groupResource) {
        Group group = new Group();
        group.setName(groupResource.getName());
        GroupUserResource groupUserResource = new GroupUserResource();

        List<User> users = new ArrayList<>();
        for (int i : groupResource.getUserids()) {
            users.add(userService.findUser(i));
        }

        group.setUsers(users);
        group.setSupervisor(userService.findUser(groupResource.getSupervisorid()));

        String imageString = groupResource.getGroupimage();

        try {
            // byte[] name = Base64.getEncoder().encode("hello world".getBytes());
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            group.setGroupImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Group out = groupService.addGroup(group);
        groupUserResource.setName(out.getName());
        groupUserResource.setSupervisor(out.getSupervisor());
        groupUserResource.setUsers(out.getUsers());
        groupUserResource.setGroupimage(out.getGroupImage());
        for (User user : group.getUsers()) {
            if (!user.getGroups().contains(group)) {
                user.getGroups().add(group);
            }
            userService.updateUser(user);
        }
        return new ResponseEntity<>(groupUserResource, HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    //ToDo: Authorization fix: group get by id
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<GroupUserResource> getGroup(@PathVariable int groupId) {
        Group group = this.groupService.getGroup(groupId);
        GroupUserResource groupUserResource = new GroupUserResource();
        groupUserResource.setGroupid(groupId);
        if(group.getGroupImage() != null) {
            groupUserResource.setGroupimage(group.getGroupImage());
        }
        groupUserResource.setName(group.getName());
        groupUserResource.setSupervisor(group.getSupervisor());
        groupUserResource.setUsers(new ArrayList<>());
        groupUserResource.setUserids(new ArrayList<>());

        for (User user : group.getUsers()) {
            groupUserResource.getUsers().add(user);
            groupUserResource.getUserids().add(user.getId());
            if (!user.getGroups().contains(group)) {
                user.getGroups().add(group);
            }
            userService.updateUser(user);
        }

        return new ResponseEntity<>(groupUserResource, HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: group get by user
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<GroupUserResource>> getGroupsByUser(Principal principal) {
        String username = principal.getName();
        User user = null;
        List<GroupUserResource> groupUserResources = new ArrayList<>();
        try {
            user = this.userService.findUserByUsername(username);

            for(Group group: user.getGroups()){
                GroupUserResource groupUserResource = new GroupUserResource();
                groupUserResource.setGroupid(group.getGroupId());
                groupUserResource.setGroupimage(group.getGroupImage());
                groupUserResource.setName(group.getName());
                groupUserResource.setSupervisor(group.getSupervisor());

                groupUserResources.add(groupUserResource);
            }
        } catch (UserServiceException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        return new ResponseEntity<>(groupUserResources, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Group> deleteGroupById(@PathVariable("groupId") Integer groupId) {

        Group group = groupService.getGroup(groupId);
        for(User user: group.getUsers()){
            user.getGroups().remove(group);
            userService.updateUser(user);
        }

        groupService.removeGroup(groupId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Group> updateGroup(@PathVariable("id") int id, @RequestBody GroupResource groupResource) {
        Group group = new Group();
        group.setGroupId(id);
        group.setName(groupResource.getName());

        GroupUserResource groupUserResource = new GroupUserResource();

        List<User> users = new ArrayList<>();
        for (int i : groupResource.getUserids()) {
            users.add(userService.findUser(i));
        }
        group.setUsers(users);
        group.setSupervisor(userService.findUser(groupResource.getSupervisorid()));

        String imageString = groupResource.getGroupimage();

        try {
            // byte[] name = Base64.getEncoder().encode("hello world".getBytes());
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            group.setGroupImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Group out = groupService.addGroup(group);
        groupUserResource.setName(out.getName());
        groupUserResource.setSupervisor(out.getSupervisor());
        groupUserResource.setUsers(out.getUsers());
        groupUserResource.setGroupimage(out.getGroupImage());
        for (User user : group.getUsers()) {
            if (!user.getGroups().contains(group)) {
                user.getGroups().add(group);
            }
            userService.updateUser(user);
        }
        return new ResponseEntity<>(out, HttpStatus.OK);
    }
}
