package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.GroupRepository;
import be.kdg.ip.services.api.GroupService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("GroupService")
@Transactional
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void addUsersToGroup(Group group, List<User> users) {
        group.setUsers(users);
    }

    @Override
    public Group addGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroup(int groupId) {
        return groupRepository.findOne(groupId);
    }

    @Override
    public List<User> getAllUsers(int groupId) {
        return groupRepository.findOne(groupId).getUsers();
    }

    @Override
    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void removeGroup(int groupId) {
        Group group = groupRepository.findOne(groupId);

        groupRepository.delete(group);
    }
}
