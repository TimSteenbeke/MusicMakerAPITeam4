package be.kdg.ip.services.api;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    void addUsersToGroup(Group group, List<User> user);

    Group addGroup(Group group);

    Group getGroup(int groupId);

    List<User> getAllUsers(int groupId);

    Group updateGroup(Group group);

    void removeGroup(int groupId);

    List<Group> getAllGroups();
}
