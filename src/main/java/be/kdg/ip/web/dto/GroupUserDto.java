package be.kdg.ip.web.dto;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;

import java.util.List;

public class GroupUserDto {
    private Group group;
    private List<User> users;

    public GroupUserDto(Group group, List<User> users) {
        this.group = group;
        this.users = users;
    }

    public Group getGroup() {
        return group;
    }


    public void setGroup(Group group) {
        this.group = group;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
