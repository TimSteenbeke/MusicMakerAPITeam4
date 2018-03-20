package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.Performance;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.GroupRepository;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.PerformanceService;
import be.kdg.ip.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("GroupService")
@Transactional
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PerformanceService performanceService;

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
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void removeGroup(int groupId) {
        Group group = groupRepository.findOne(groupId);

        for (Performance performance: group.getPerformances()) {
            performanceService.deletePerformance(performance.getPerformanceId());
        }

        for(User user: group.getUsers()){
            user.getGroups().remove(group);
            userService.updateUser(user);
        }
        groupRepository.delete(group);
    }
}
