package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.GroupResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
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
    public ResponseEntity<List<Group>> findAll(){
        List<Group> groups = groupService.getAllGroups();
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }

    @PostMapping
    //ToDo: Authorization fix: group post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Group> postNewGroup(@RequestBody GroupResource groupResource){
        Group group = new Group();
        group.setName(groupResource.getName());

        List<User> users = new ArrayList<>();
        for (int i:groupResource.getUserids()){
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
        return new ResponseEntity<>(out,HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    //ToDo: Authorization fix: group get by id
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Group> getGroup(@PathVariable int groupId){
        Group group = this.groupService.getGroup(groupId);
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: group get by user
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Collection<Group>> getGroupsByUser(@PathVariable int userId) {
        User user = this.userService.findUser(userId);

        return new ResponseEntity<>(user.getGroups(),HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Group> deleteGroupById(@PathVariable("groupId") Integer groupId) {

        Group group = groupService.getGroup(groupId);
        groupService.removeGroup(groupId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Group> updateGroup(@PathVariable("id") int id, @RequestBody GroupResource groupResource) {
        Group group = new Group();
        group.setGroupId(id);
        group.setName(groupResource.getName());

        List<User> users = new ArrayList<>();
        for (int i:groupResource.getUserids()){
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

        return new ResponseEntity<>(out,HttpStatus.OK);
    }
}
