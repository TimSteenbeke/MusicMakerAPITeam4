
package be.kdg.ip.restTests;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.*;
import be.kdg.ip.security.SecurityConfig;
import be.kdg.ip.services.api.RoleService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.AgendaController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAgendaController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private AgendaController controller;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    OAuthHelper oAuthHelper;


    @Before
    public  void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Before
    public void createUser() {
        //Create dumy user
        //ROLE:
        Role administrator = roleService.getRoleByName("Admin");
        List<Role> rolesAdmins = new ArrayList<Role>();
        rolesAdmins.add(administrator);
        //User:
        User aputje = new User("aputje", "apuiscool", "apuz", "apuz", rolesAdmins);
        userService.addUser(aputje);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
        this.deleteTheTestUser();
    }

    @Test
    public void testGetAgenda() throws Exception{

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("aputje","ADMIN");

        RequestBuilder request = get("http://localhost:8080/api/agenda").with(bearerToken);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendaOwner", CoreMatchers.is("aputje")));

        this.deleteTheTestUser();
    }


    @Test
    public void testOtherAgenda() throws Exception {

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        this.mockMvc.perform(get("http://localhost:8080/api/agenda/1").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$.agendaId", CoreMatchers.is(1)));

    }

    @Test
    @WithAnonymousUser
    public void testOtherAgendaWhenUsingStudent() throws Exception {
            this.mockMvc.perform(get("http://localhost:8080/api/agenda/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        this.deleteTheTestUser();
    }



    private void deleteTheTestUser() {
        try {
            User apu = userService.findUserByUsername("aputje");
            userService.deleteUser(apu.getId());
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }
}

