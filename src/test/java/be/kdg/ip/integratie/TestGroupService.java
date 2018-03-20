package be.kdg.ip.integratie;


import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.GroupRepository;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.impl.GroupServiceImpl;
import be.kdg.ip.web.resources.GroupResource;
import be.kdg.ip.web.resources.GroupUserResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
        group.setGroupImage(new byte[5]);
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
    public void testGetAllGroups() throws Exception{
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        Group group = new Group();
        group.setName("Groepsnaam");

        GroupUserResource groupUserResource = new GroupUserResource();
        groupUserResource.setName(group.getName());

        List<GroupUserResource> groupUserResourceList = Collections.singletonList(groupUserResource);

        mockMvc.perform(get("http://localhost:8080/api/groups/allgroups").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void testPostGroup() throws Exception{
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        User user = new User();
        user.setUsername("lode.wouters@student.kdg.be");

        GroupResource groupResource = new GroupResource();
        groupResource.setName("testGroep");
        groupResource.setSupervisorid(1);
        List<Integer> userids = new ArrayList<>();
        userids.add(1);
        groupResource.setUserids(userids);
        groupResource.setGroupimage("image");

        this.mockMvc.perform(post("").with(bearerToken)
                .param("files","testGroep")
                .param("groupresource", asJsonString(groupResource))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(csrf())
                .accept(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());
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

    @Test
    public void testDeleteGroup() throws Exception{
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser","ADMIN");

    }
}
