package be.kdg.ip.integratie;


import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.domain.roles.Student;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.RoleService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.GroupResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
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

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private GroupService groupService;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    public void testGetGroupById() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("jef", "ADMIN");

        User supervisor = new User("lode.wouters@student.kdg.be", "rootpwd", "Lode", "Wouters", null);
        User newUser = new User("Michiel.bervoets@student.kdg.be", "testpwd2", "Michiel", "Bervoets", null);
        List<User> users = new ArrayList<>();
        users.add(newUser);

        Group group = new Group("testGroep",supervisor,users);

        System.out.println(group);
        List<Group> getAllGroups = groupService.getAllGroups();
        System.out.println(getAllGroups);
        given(this.groupService.getGroup(group.getGroupId())).willReturn(group);

        RequestBuilder request = get("http://localhost:8080/api/groups/" + group.getGroupId()).with(bearerToken);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(group.getName())));

    }

    @Test
    public void testGetAllGroups() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        Group group = new Group();
        group.setName("Groepsnaam");

        List<Group> groupList = Collections.singletonList(group);

        given(groupService.getAllGroups()).willReturn(groupList);


        mockMvc.perform(get("http://localhost:8080/api/groups/allgroups").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void testPostGroup() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");
        Role administrator = new Administrator();
        roleService.addRole(administrator);
        List<Role> rolesAdmin = new ArrayList<>();
        rolesAdmin.add(administrator);
        User supervisor = new User("lode.wouters@student.kdg.be", "rootpwd", "Lode", "Wouters", null);
        supervisor.setRoles(rolesAdmin);

        Role student = new Student();
        roleService.addRole(student);
        List<Role> rolesStudent = new ArrayList<>();
        rolesStudent.add(student);
        User newUser = new User("Michiel.bervoets@student.kdg.be", "testpwd2", "Michiel", "Bervoets", null);
        newUser.setRoles(rolesStudent);

        GroupResource groupResource = new GroupResource();
        groupResource.setName("test");
        groupResource.setSupervisorid(supervisor.getId());
        List<Integer> userIds = new ArrayList<>();
        userIds.add(newUser.getId());
        groupResource.setUserids(userIds);
        List<Group> allGroups = groupService.getAllGroups();
        System.out.println(allGroups);

        this.mockMvc.perform(post("http://localhost:8080/api/groups/").with(bearerToken)
                .content(asJsonString(groupResource))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAddMultipleUsersToGroup() {
        Group group = new Group();
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        User newUser = new User("lode.wouters@student.kdg.be", "rootpwd", "Lode", "Wouters", null);
        User newUser2 = new User("Michiel.bervoets@student.kdg.be", "testpwd2", "Michiel", "Bervoets", null);

        List<User> users = new ArrayList<>();
        users.add(newUser);
        users.add(newUser2);



        List<User> allUsers = this.groupService.getAllUsers(group.getGroupId());

        assertEquals(allUsers.get(0).getId(), newUser.getId());
        assertEquals(allUsers.get(1).getId(), newUser2.getId());
    }

    @Test
    public void testDeleteGroup() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        Group group = new Group();
        group.setGroupId(1);
        group.setName("testgroep");
        groupService.addGroup(group);
        System.out.println(groupService.getGroup(1));
        System.out.println(groupService.getAllGroups());
        given(groupService.getGroup(1)).willReturn(group);

        this.mockMvc.perform(delete("/api/groups/1").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        when(groupService.getGroup(99)).thenReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/groups/99").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
