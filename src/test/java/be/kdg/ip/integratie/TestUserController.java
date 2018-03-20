package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Address;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.UserController;
import be.kdg.ip.web.resources.UserAddressDetailsResource;
import be.kdg.ip.web.resources.UserDetailsResource;
import be.kdg.ip.web.resources.UserGetResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestUserController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserController controller;

    @Autowired
    private OAuthHelper oAuthHelper;


    @MockBean
    private UserService userService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
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
    }

    @Test
    public void testGetUser() throws Exception {
        int userId = 1;

        User user = new User();
        user.setFirstname("Jos");
        user.setLastname("Bakkers");
        user.setUsername("Jos.Bakkers@gmail.com");
        user.setPassword("password");
        user.setUserImage(new byte[0]);
        Address address = new Address();
        address.setStreet("straat");
        address.setStreetNumber("20");
        address.setCity("Antwerpen");
        address.setPostalCode("2980");
        address.setCountry("Belgie");
        user.setAddress(address);
        user.setId(userId);

        UserAddressDetailsResource userAddressDetailsResource = new UserAddressDetailsResource();
        userAddressDetailsResource.setFirstname(user.getFirstname());
        userAddressDetailsResource.setLastname(user.getLastname());
        userAddressDetailsResource.setUsername(user.getUsername());
        userAddressDetailsResource.setUserimage(new sun.misc.BASE64Encoder().encode(user.getUserImage()));
        userAddressDetailsResource.setStreet(user.getAddress().getStreet());
        userAddressDetailsResource.setStreetnumber(user.getAddress().getStreetNumber());
        userAddressDetailsResource.setCity(user.getAddress().getCity());
        userAddressDetailsResource.setPostalcode(user.getAddress().getPostalCode());
        userAddressDetailsResource.setCountry(user.getAddress().getCountry());
        userAddressDetailsResource.setId(user.getId());

        given(userService.findUser(userId)).willReturn(user);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/users/" + userId).with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", CoreMatchers.is(userAddressDetailsResource.getFirstname())))
                .andExpect(jsonPath("$.lastname", CoreMatchers.is(userAddressDetailsResource.getLastname())))
                .andExpect(jsonPath("$.username", CoreMatchers.is(userAddressDetailsResource.getUsername())))
                .andExpect(jsonPath("$.userimage", CoreMatchers.is(userAddressDetailsResource.getUserimage())))
                .andExpect(jsonPath("$.street", CoreMatchers.is(userAddressDetailsResource.getStreet())))
                .andExpect(jsonPath("$.streetnumber", CoreMatchers.is(userAddressDetailsResource.getStreetnumber())))
                .andExpect(jsonPath("$.city", CoreMatchers.is(userAddressDetailsResource.getCity())))
                .andExpect(jsonPath("$.postalcode", CoreMatchers.is(userAddressDetailsResource.getPostalcode())))
                .andExpect(jsonPath("$.country", CoreMatchers.is(userAddressDetailsResource.getCountry())));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user = new User();
        user.setFirstname("Jos");
        user.setLastname("Bakkers");
        user.setUsername("Jos.Bakkers@gmail.com");
        user.setPassword("password");
        user.setUserImage(new byte[0]);
        Address address = new Address();
        address.setStreet("straat");
        address.setStreetNumber("20");
        address.setCity("Antwerpen");
        address.setPostalCode("2980");
        address.setCountry("Belgie");
        user.setAddress(address);

        UserDetailsResource userDetailsResource = new UserDetailsResource();
        userDetailsResource.setFirstname(user.getFirstname());
        userDetailsResource.setLastname(user.getLastname());
        userDetailsResource.setUserid(user.getId());

        UserGetResource userGetResource = new UserGetResource();
        userGetResource.setUsers(new ArrayList<>());
        userGetResource.getUsers().add(userDetailsResource);

        List<User> users = singletonList(user);

        given(userService.getUsers()).willReturn(users);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/users").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$", hasSize(1)))
                //.andExpect(jsonPath("$[0]['users']['firstname']", CoreMatchers.is(user.getFirstname())))
                //.andExpect(jsonPath("$[0].lastname", CoreMatchers.is(user.getLastname())))
                //.andExpect(jsonPath("$[0].id", CoreMatchers.is(user.getId())));
    }
}


