package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.*;
import be.kdg.ip.domain.roles.Student;
import be.kdg.ip.domain.roles.Teacher;
import be.kdg.ip.services.api.AddressService;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.services.api.RoleService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.UserController;
import be.kdg.ip.web.resources.*;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private RoleService roleService;

    @MockBean
    private AddressService addressService;
    @MockBean
    private CompositionService compositionService;

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].firstname", CoreMatchers.is(user.getFirstname())))
                .andExpect(jsonPath("$.users[0].lastname", CoreMatchers.is(user.getLastname())))
                .andExpect(jsonPath("$.users[0].userid", CoreMatchers.is(user.getId())));
    }

    @Test
    public void testDeleteUser() throws Exception {

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

        given(userService.findUser(userId)).willReturn(user);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");
        this.mockMvc.perform(delete("http://localhost:8080/api/users/" + userId).with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddUser() throws Exception {

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

        UserResource userResource = new UserResource();
        userResource.setFirstname(user.getFirstname());
        userResource.setLastname(user.getLastname());
        userResource.setUsername(user.getUsername());
        userResource.setUserimage(new BASE64Encoder().encode(user.getUserImage()));
        userResource.setStreet(user.getAddress().getStreet());
        userResource.setStreetnumber(user.getAddress().getStreetNumber());
        userResource.setCity(user.getAddress().getCity());
        userResource.setPostalcode(user.getAddress().getPostalCode());
        userResource.setCountry(user.getAddress().getCountry());

        given(userService.addUser(Matchers.isA(User.class))).willReturn(user);
        given(roleService.getRoleByName("Student")).willReturn(new Student());

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(post("http://localhost:8080/api/users").with(bearerToken)
                .content(asJsonString(userResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", CoreMatchers.is(user.getFirstname())))
                .andExpect(jsonPath("$.lastname", CoreMatchers.is(user.getLastname())))
                .andExpect(jsonPath("$.username", CoreMatchers.is(user.getUsername())))
                .andExpect(jsonPath("$.address.street", CoreMatchers.is(user.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.streetNumber", CoreMatchers.is(user.getAddress().getStreetNumber())))
                .andExpect(jsonPath("$.address.city", CoreMatchers.is(user.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postalCode", CoreMatchers.is(user.getAddress().getPostalCode())))
                .andExpect(jsonPath("$.address.country", CoreMatchers.is(user.getAddress().getCountry())));
    }

    //nog verder uitwerken
    @Test
    public void testUpdateUser() throws Exception {

        int userId = 58;
        int addressId = 10;
        User user = new User();
        user.setFirstname("Jos");
        user.setLastname("Bakkers");
        user.setUsername("Jos.Bakkers@gmail.com");
        user.setPassword("password");
        user.setUserImage(new byte[0]);
        Address address = new Address();
        address.setId(addressId);
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
        given(userService.updateUser(Matchers.isA(User.class))).willReturn(user);
        given(addressService.getAddress(addressId)).willReturn(new Address());
        given(addressService.updateAddress(Matchers.isA(Address.class))).willReturn(address);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(put("http://localhost:8080/api/users/user/" + userId).with(bearerToken)
                .content(asJsonString(userAddressDetailsResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", CoreMatchers.is(user.getFirstname())))
                .andExpect(jsonPath("$.lastname", CoreMatchers.is(user.getLastname())))
                .andExpect(jsonPath("$.username", CoreMatchers.is(user.getUsername())))
                .andExpect(jsonPath("$.address.street", CoreMatchers.is(user.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.streetNumber", CoreMatchers.is(user.getAddress().getStreetNumber())))
                .andExpect(jsonPath("$.address.city", CoreMatchers.is(user.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postalCode", CoreMatchers.is(user.getAddress().getPostalCode())))
                .andExpect(jsonPath("$.address.country", CoreMatchers.is(user.getAddress().getCountry())));
    }

    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(userService.findUser(123)).willReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/users/123").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //uitwerken
    @Test
    public void testGetCurrentUser(){
    }

    @Test
    public void testgetStudents() throws Exception {
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
        List<Role> roles = new ArrayList<>();
        roles.add(new Student());
        user.setRoles(roles);

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].firstname", CoreMatchers.is(user.getFirstname())))
                .andExpect(jsonPath("$.users[0].lastname", CoreMatchers.is(user.getLastname())))
                .andExpect(jsonPath("$.users[0].userid", CoreMatchers.is(user.getId())));
    }

    @Test
    public void testgetTeacherAdmins() throws Exception {
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
        List<Role> roles = new ArrayList<>();
        roles.add(new Teacher());
        user.setRoles(roles);

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].firstname", CoreMatchers.is(user.getFirstname())))
                .andExpect(jsonPath("$.users[0].lastname", CoreMatchers.is(user.getLastname())))
                .andExpect(jsonPath("$.users[0].userid", CoreMatchers.is(user.getId())));
    }

    @Test
    public void testgetRoles() throws Exception {

        Role role = new Student();

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        given(roleService.getRoles()).willReturn(roles);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/users/roles").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].roleName",CoreMatchers.is(role.getRoleName())));
    }

    @Test
    public void testGetExcercisesUser() throws Exception {

        int userId = 58;
        int addressId = 10;
        User user = new User();
        user.setFirstname("Jos");
        user.setLastname("Bakkers");
        user.setUsername("Jos.Bakkers@gmail.com");
        user.setPassword("password");
        user.setUserImage(new byte[0]);
        Address address = new Address();
        address.setId(addressId);
        address.setStreet("straat");
        address.setStreetNumber("20");
        address.setCity("Antwerpen");
        address.setPostalCode("2980");
        address.setCountry("Belgie");
        user.setAddress(address);
        user.setId(userId);
        List<Composition> excercises = new ArrayList<>();
        Composition composition = new Composition();
        composition.setTitle("compostion");
        composition.setGenre("genre");
        composition.setArtist("artist");
        composition.setSubject("subject");

        excercises.add(composition);
        user.setExercises(excercises);

        given(userService.findUser(userId)).willReturn(user);
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/users/excercises/" + userId).with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",CoreMatchers.is(user.getExercises().get(0).getTitle())))
                .andExpect(jsonPath("$[0].artist",CoreMatchers.is(user.getExercises().get(0).getArtist())))
                .andExpect(jsonPath("$[0].genre",CoreMatchers.is(user.getExercises().get(0).getGenre())));
    }

    @Test
    public void testAddExcerciseUser() throws Exception {
        int userId = 58;
        int addressId = 10;
        int compositionId =0 ;
        User user = new User();
        user.setFirstname("Jos");
        user.setLastname("Bakkers");
        user.setUsername("Jos.Bakkers@gmail.com");
        user.setPassword("password");
        user.setUserImage(new byte[0]);
        Address address = new Address();
        address.setId(addressId);
        address.setStreet("straat");
        address.setStreetNumber("20");
        address.setCity("Antwerpen");
        address.setPostalCode("2980");
        address.setCountry("Belgie");
        user.setAddress(address);
        user.setId(userId);
        List<Composition> excercises = new ArrayList<>();
        Composition composition = new Composition();
        composition.setTitle("compostion");
        composition.setGenre("genre");
        composition.setArtist("artist");
        composition.setSubject("subject");
        composition.setCompositionId(compositionId);

        excercises.add(composition);
        user.setExercises(excercises);

        UserExcerciseResource userExcerciseResource = new UserExcerciseResource();
        userExcerciseResource.setExcerciseid(compositionId);

        given(userService.updateUser(Matchers.isA(User.class))).willReturn(user);
        given(userService.findUser(userId)).willReturn(user);
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(post("http://localhost:8080/api/users/excercises/" + userId).with(bearerToken)
                .content(asJsonString(userExcerciseResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title",CoreMatchers.is(user.getExercises().get(0).getTitle())))
                .andExpect(jsonPath("$[0].artist",CoreMatchers.is(user.getExercises().get(0).getArtist())))
                .andExpect(jsonPath("$[0].genre",CoreMatchers.is(user.getExercises().get(0).getGenre())));
    }

    @Test
    public void testDeleteExcerciseUser() throws Exception {
        int userId = 58;
        int addressId = 10;
        int compositionId =0 ;
        User user = new User();
        user.setFirstname("Jos");
        user.setLastname("Bakkers");
        user.setUsername("Jos.Bakkers@gmail.com");
        user.setPassword("password");
        user.setUserImage(new byte[0]);
        Address address = new Address();
        address.setId(addressId);
        address.setStreet("straat");
        address.setStreetNumber("20");
        address.setCity("Antwerpen");
        address.setPostalCode("2980");
        address.setCountry("Belgie");
        user.setAddress(address);
        user.setId(userId);
        List<Composition> excercises = new ArrayList<>();
        Composition composition = new Composition();
        composition.setTitle("compostion");
        composition.setGenre("genre");
        composition.setArtist("artist");
        composition.setSubject("subject");
        composition.setCompositionId(compositionId);

        excercises.add(composition);
        user.setExercises(excercises);

        UserExcerciseResource userExcerciseResource = new UserExcerciseResource();
        userExcerciseResource.setExcerciseid(compositionId);

        given(userService.updateUser(Matchers.isA(User.class))).willReturn(user);
        given(userService.findUser(userId)).willReturn(user);
        given(compositionService.getComposition(compositionId)).willReturn(composition);
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(post("http://localhost:8080/api/users/excercises/remove/" + userId).with(bearerToken)
                .content(asJsonString(userExcerciseResource))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}


