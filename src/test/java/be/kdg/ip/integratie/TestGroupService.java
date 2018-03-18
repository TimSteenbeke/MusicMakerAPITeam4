package be.kdg.ip.integratie;


import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.GroupRepository;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.impl.GroupServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestGroupService {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private OAuthHelper oAuthHelper;

    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    private GroupService groupService;

    @MockBean
    private UserService userService;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        this.groupService = new GroupServiceImpl(this.groupRepository);
    }

    @Test
    public void testGetGroupById() throws Exception{
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("jef", "ADMIN");

        int groupId = 1;
        User supervisor = new User("lode.wouters@student.kdg.be", "rootpwd", "Lode", "Wouters",null);
        User newUser = new User("Michiel.bervoets@student.kdg.be", "testpwd2", "Michiel", "Bervoets",null);

        userService.addUser(supervisor);
        userService.addUser(newUser);

        Group group = new Group();
        group.setName("testGroep");
        List<User> users = new ArrayList<>();
        users.add(newUser);
        group.setUsers(users);
        group.setSupervisor(supervisor);
        groupService.addGroup(group);

        supervisor.getGroups().add(group);
        newUser.getGroups().add(group);

        given(this.groupService.getGroup(groupId)).willReturn(group);

        RequestBuilder request = get("http://localhost:8080/api/groups/1").with(bearerToken);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(group.getName())));

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

        //assertEquals(allUsers.get(0).getGroupId(), newUser.getGroupId());
        //assertEquals(allUsers.get(1).getGroupId(), newUser2.getGroupId());
    }
}
