package be.kdg.ip.integratie;


import be.kdg.ip.IP2Application;
import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.GroupRepository;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.impl.GroupServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IP2Application.class)
@WebAppConfiguration
public class TestGroupService {

    @MockBean
    private GroupRepository groupRepository;

    private GroupService groupService;

    @Before
    public void setup(){
        this.groupService = new GroupServiceImpl(this.groupRepository);
    }

    @Test
    public void testCreateGroup(){
        //Group group = new Group("nameGroup");
        //groupService.addGroup(group);
    }

    @Test
    public void testAddMultipleUsersToGroup(){
        Group group = new Group();
        User newUser = new User("lode.wouters@student.kdg.be", "rootpwd", "Lode", "Wouters",null);
        User newUser2 = new User("Michiel.bervoets@student.kdg.be", "testpwd2", "Michiel", "Bervoets",null);

        List<User> users = new ArrayList<>();
        users.add(newUser);
        users.add(newUser2);

        groupService.addUsersToGroup(group, users);

        List<User> allUsers = this.groupService.getAllUsers(group.getGroupId());

        //assertEquals(allUsers.get(0).getGroupid(), newUser.getGroupid());
        //assertEquals(allUsers.get(1).getGroupid(), newUser2.getGroupid());
    }
}
