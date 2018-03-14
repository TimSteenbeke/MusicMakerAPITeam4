package be.kdg.ip.integratie;

import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.UserResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void createUser() throws Exception {

        UserResource userResource = new UserResource();
        userResource.setFirstname("Joe");
        userResource.setLastname("monkey");
        userResource.setUsername("Mankey");
        userResource.setPassword("joske");

        this.mockMvc.perform(post("")
                //.with(user("admin1").roles("ADMIN"))
                .param("compresource", asJsonString(userResource))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(asJsonString(userResource))
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        User user = new User();
        user.setFirstname("Joe");
        user.setLastname("monkey");
        user.setUsername("Mankey");
        user.setPassword("joske");

        //userService.addUser(user);
        int id = 1;

        given(userService.findUser(id)).willReturn(user);

        this.mockMvc.perform(get("http://localhost:8080/api/users/{userId}",id)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("tim"),
                new User("jos"),
                new User("jeff"));

        given(userService.getUsers()).willReturn(users);

        mockMvc.perform(get(/*https://musicmaker-api-team4.herokuapp.com/api/users")*/"http://localhost:8080/api/users")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
                //.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                //.andExpect(jsonPath("$", hasSize(3)));
    }
}
