package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.dto.GroupUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Group getGroup(@PathVariable int groupId){
        return this.groupService.getGroup(groupId);
    }

    @GetMapping("/api/groups/{userId}/user}")
    public Collection<Group> getGroupsByUser(@PathVariable int userId) {
        User user = this.userService.findUser(userId);

        return user.getGroups();
    }

    @PostMapping("/groups")
    public void postNewGroup(@RequestBody Group group){
        if(group != null){
            groupService.addGroup(group);
        }
    }

    @PostMapping("/groups/user")
    public void postNewUserToGroup(@RequestBody GroupUserDto groupUserDto){
        List<User> users = groupUserDto.getUsers();
        groupService.addUsersToGroup(groupUserDto.getGroup(), users);
    }
}
