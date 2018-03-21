package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.NewsItem;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.resources.GroupResource;
import be.kdg.ip.web.resources.GroupUserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(GroupController.class);

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

        for (Group group : groups) {
            GroupUserResource groupUserResource = new GroupUserResource();
            groupUserResource.setGroupid(group.getGroupId());
            groupUserResource.setGroupimage(group.getGroupImage());
            groupUserResource.setName(group.getName());
            groupUserResource.setSupervisor(group.getSupervisor());
            groupUserResource.setUsers(group.getUsers());

            groupUserResources.add(groupUserResource);
        }

        return new ResponseEntity<>(groupUserResources, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<GroupUserResource> postNewGroup(@RequestBody GroupResource groupResource) {
        Group group = new Group();
        group.setName(groupResource.getName());

        List<User> users = new ArrayList<>();
        for (int i : groupResource.getUserids()) {
            users.add(userService.findUser(i));
        }

        group.setUsers(users);
        group.setSupervisor(userService.findUser(groupResource.getSupervisorid()));

        if(groupResource.getGroupimage() != null) {
            String imageString = groupResource.getGroupimage();
            try {
                byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
                group.setGroupImage(decodedString);
            } catch (UnsupportedEncodingException e) {
                logger.error("Error converting image for new group.");
            }
        }
        groupService.addGroup(group);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<GroupUserResource> getGroup(@PathVariable int groupId) {
        Group group = groupService.getGroup(groupId);

        GroupUserResource groupUserResource = new GroupUserResource();
        groupUserResource.setGroupid(groupId);
        if(group.getGroupImage() != null) {
            groupUserResource.setGroupimage(group.getGroupImage());
        }
        groupUserResource.setName(group.getName());
        groupUserResource.setSupervisor(group.getSupervisor());

        for(NewsItem newsItem : group.getNewsItems()){
            groupUserResource.getNewsItems().add(newsItem);
        }

        groupUserResource.setUsers(group.getUsers());

        return new ResponseEntity<>(groupUserResource, HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<GroupUserResource>> getGroupsByUser(Principal principal) {
        String username = principal.getName();

        List<GroupUserResource> groupUserResources = new ArrayList<>();
        try {
           User user = this.userService.findUserByUsername(username);

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
        groupService.removeGroup(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Group> updateGroup(@PathVariable("id") int id, @RequestBody GroupResource groupResource) {

        Group group = groupService.getGroup(id);
        group.setName(groupResource.getName());

        List<User> users = new ArrayList<>();
        for (int i : groupResource.getUserids()) {
            users.add(userService.findUser(i));
        }

        group.setUsers(users);
        group.setSupervisor(userService.findUser(groupResource.getSupervisorid()));

        String imageString = groupResource.getGroupimage();
        if (groupResource.getGroupimage() != null) {
            try {
                byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
                group.setGroupImage(decodedString);
            } catch (UnsupportedEncodingException e) {
                logger.error("Error converting image while updating group.");
            }
        }

        groupService.updateGroup(group);

        return new ResponseEntity<>(HttpStatus.OK);
    }

   @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public String return404(NullPointerException ex) {
        return ex.getMessage();
    }
}
