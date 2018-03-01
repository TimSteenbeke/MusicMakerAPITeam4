package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.dto.GroupUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/api/groups/{groupId}")
<<<<<<< HEAD
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
=======
    //ToDo: Authorization fix: group get by id
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
>>>>>>> 47f213e4c1860aff1664f5910820f7f2aaf32c48
    public Group getGroup(@PathVariable int groupId){
        return this.groupService.getGroup(groupId);
    }

    @GetMapping("/api/groups/{userId}/user}")
    //ToDo: Authorization fix: group get by user
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public Collection<Group> getGroupsByUser(@PathVariable int userId) {
        User user = this.userService.findUser(userId);

        return user.getGroups();
    }

    @PostMapping("/groups")
    //ToDo: Authorization fix: group post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public void postNewGroup(@RequestBody Group group){
        if(group != null){
            groupService.addGroup(group);
        }
    }

    @PostMapping("/groups/user")
    //ToDo: Authorization fix: users in group post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public void postNewUserToGroup(@RequestBody GroupUserDto groupUserDto){
        List<User> users = groupUserDto.getUsers();
        groupService.addUsersToGroup(groupUserDto.getGroup(), users);
    }
}
