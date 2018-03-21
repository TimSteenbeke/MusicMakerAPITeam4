package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.*;
import be.kdg.ip.services.api.InstrumentLevelService;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.InstrumentLevelController;
import be.kdg.ip.web.resources.InstrumentLevelResource;
import be.kdg.ip.web.resources.InstrumentLevelUserInstrumentResource;
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

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
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
public class TestInstrumentLevelController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private InstrumentLevelController controller;

    @Autowired
    private OAuthHelper oAuthHelper;


    @MockBean
    private InstrumentLevelService instrumentLevelService;

    @MockBean
    private UserService userService;

    @MockBean
    private InstrumentService instrumentService;

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
    public void testGetInstrumentLevel() throws Exception {

        int instrumentLevelId = 50;
        int userId = 58;
        int addressId = 10;
        int instrumentId = 10;

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

        Instrument instrument = new Instrument();
        instrument.setType("type");
        instrument.setDetails("details");
        instrument.setImage(new byte[0]);
        instrument.setInstrumentName("instrumentname");
        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("categoryname");
        instrumentCategory.setInstrumentCategoryId(1);
        instrument.setInstrumentCategory(instrumentCategory);
        instrument.setInstrumentCategory(instrument.getInstrumentCategory());
        instrument.setInstrumentId(instrumentId);

        InstrumentLevel instrumentLevel = new InstrumentLevel();
        instrumentLevel.setMaxLevel(10);
        instrumentLevel.setLevel(8);
        instrumentLevel.setUser(user);
        instrumentLevel.setInstrument(instrument);
        instrumentLevel.setInstrumentLevelId(instrumentLevelId);

        InstrumentLevelUserInstrumentResource instrumentLevelUserInstrumentResource = new InstrumentLevelUserInstrumentResource();
        instrumentLevelUserInstrumentResource.setMaxLevel(instrumentLevel.getMaxLevel());
        instrumentLevelUserInstrumentResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelUserInstrumentResource.setInstrument(instrumentLevel.getInstrument());
        instrumentLevelUserInstrumentResource.setUser(instrumentLevel.getUser());

        given(instrumentLevelService.getIntrumentLevel(instrumentLevel.getInstrumentLevelId())).willReturn(instrumentLevel);
        given(userService.findUser(instrumentLevel.getUser().getId())).willReturn(user);
        given(userService.updateUser(user)).willReturn(user);
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/instrumentlevels/" +instrumentLevel.getInstrumentLevelId()).with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxLevel", CoreMatchers.is(instrumentLevelUserInstrumentResource.getMaxLevel())))
                .andExpect(jsonPath("$.level", CoreMatchers.is(instrumentLevelUserInstrumentResource.getLevel())))
                .andExpect(jsonPath("$.instrument.instrumentId", CoreMatchers.is(instrumentLevelUserInstrumentResource.getInstrument().getInstrumentId())))
                .andExpect(jsonPath("$.user.id", CoreMatchers.is(instrumentLevelUserInstrumentResource.getUser().getId())));
    }

    @Test
    public void testGetInstrumentLevels() throws Exception {
        int instrumentLevelId = 50;
        int userId = 58;
        int addressId = 10;
        int instrumentId = 10;

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

        Instrument instrument = new Instrument();
        instrument.setType("type");
        instrument.setDetails("details");
        instrument.setImage(new byte[0]);
        instrument.setInstrumentName("instrumentname");
        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("categoryname");
        instrumentCategory.setInstrumentCategoryId(1);
        instrument.setInstrumentCategory(instrumentCategory);
        instrument.setInstrumentCategory(instrument.getInstrumentCategory());
        instrument.setInstrumentId(instrumentId);

        InstrumentLevel instrumentLevel = new InstrumentLevel();
        instrumentLevel.setMaxLevel(10);
        instrumentLevel.setLevel(8);
        instrumentLevel.setUser(user);
        instrumentLevel.setInstrument(instrument);
        instrumentLevel.setInstrumentLevelId(instrumentLevelId);

        List<InstrumentLevel> instrumentLevels = singletonList(instrumentLevel);

        InstrumentLevelUserInstrumentResource instrumentLevelUserInstrumentResource = new InstrumentLevelUserInstrumentResource();
        instrumentLevelUserInstrumentResource.setMaxLevel(instrumentLevel.getMaxLevel());
        instrumentLevelUserInstrumentResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelUserInstrumentResource.setInstrument(instrumentLevel.getInstrument());
        instrumentLevelUserInstrumentResource.setUser(instrumentLevel.getUser());

        given(instrumentLevelService.getAllInstrumentLevels()).willReturn(instrumentLevels);
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/instrumentlevels").with(bearerToken)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].maxLevel", CoreMatchers.is(instrumentLevelUserInstrumentResource.getMaxLevel())))
                .andExpect(jsonPath("$[0].level", CoreMatchers.is(instrumentLevelUserInstrumentResource.getLevel())))
                .andExpect(jsonPath("$[0].instrument.instrumentId", CoreMatchers.is(instrumentLevelUserInstrumentResource.getInstrument().getInstrumentId())))
                .andExpect(jsonPath("$[0].user.id", CoreMatchers.is(instrumentLevelUserInstrumentResource.getUser().getId())));


    }

    @Test
    public void testCreateInstrumentLevel() throws Exception {
        int instrumentLevelId = 50;
        int userId = 58;
        int addressId = 10;
        int instrumentId = 10;

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

        Instrument instrument = new Instrument();
        instrument.setType("type");
        instrument.setDetails("details");
        instrument.setImage(new byte[0]);
        instrument.setInstrumentName("instrumentname");
        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("categoryname");
        instrumentCategory.setInstrumentCategoryId(1);
        instrument.setInstrumentCategory(instrumentCategory);
        instrument.setInstrumentCategory(instrument.getInstrumentCategory());
        instrument.setInstrumentId(instrumentId);

        InstrumentLevel instrumentLevel = new InstrumentLevel();
        instrumentLevel.setMaxLevel(10);
        instrumentLevel.setLevel(8);
        instrumentLevel.setUser(user);
        instrumentLevel.setInstrument(instrument);
        instrumentLevel.setInstrumentLevelId(instrumentLevelId);

        InstrumentLevelResource instrumentLevelResource = new InstrumentLevelResource();
        instrumentLevelResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelResource.setMaxlevel(instrumentLevel.getMaxLevel());
        instrumentLevelResource.setInstrumentid(instrumentLevel.getInstrument().getInstrumentId());
        instrumentLevelResource.setUserid(instrumentLevel.getUser().getId());

        InstrumentLevelUserInstrumentResource instrumentLevelUserInstrumentResource = new InstrumentLevelUserInstrumentResource();
        instrumentLevelUserInstrumentResource.setMaxLevel(instrumentLevel.getMaxLevel());
        instrumentLevelUserInstrumentResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelUserInstrumentResource.setInstrument(instrumentLevel.getInstrument());
        instrumentLevelUserInstrumentResource.setUser(instrumentLevel.getUser());

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentLevelService.addInstrumentLevel(Matchers.isA(InstrumentLevel.class))).willReturn(instrumentLevel);
        given(instrumentService.getInstrument(instrument.getInstrumentId())).willReturn(instrument);
        given(userService.findUser(user.getId())).willReturn(user);
        given(userService.updateUser(Matchers.isA(User.class))).willReturn(user);

        mockMvc.perform(post("http://localhost:8080/api/instrumentlevels").with(bearerToken)
                .content(asJsonString(instrumentLevelResource))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxLevel", CoreMatchers.is(instrumentLevelUserInstrumentResource.getMaxLevel())))
                .andExpect(jsonPath("$.level", CoreMatchers.is(instrumentLevelUserInstrumentResource.getLevel())))
                .andExpect(jsonPath("$.instrument.instrumentId", CoreMatchers.is(instrumentLevelUserInstrumentResource.getInstrument().getInstrumentId())))
                .andExpect(jsonPath("$.user.id", CoreMatchers.is(instrumentLevelUserInstrumentResource.getUser().getId())));
    }

    @Test
    public void testUpateInstrumentLevel() throws Exception {
        int instrumentLevelId = 50;
        int userId = 58;
        int addressId = 10;
        int instrumentId = 10;

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

        Instrument instrument = new Instrument();
        instrument.setType("type");
        instrument.setDetails("details");
        instrument.setImage(new byte[0]);
        instrument.setInstrumentName("instrumentname");
        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("categoryname");
        instrumentCategory.setInstrumentCategoryId(1);
        instrument.setInstrumentCategory(instrumentCategory);
        instrument.setInstrumentCategory(instrument.getInstrumentCategory());
        instrument.setInstrumentId(instrumentId);

        InstrumentLevel instrumentLevel = new InstrumentLevel();
        instrumentLevel.setMaxLevel(10);
        instrumentLevel.setLevel(8);
        instrumentLevel.setUser(user);
        instrumentLevel.setInstrument(instrument);
        instrumentLevel.setInstrumentLevelId(instrumentLevelId);

        InstrumentLevelResource instrumentLevelResource = new InstrumentLevelResource();
        instrumentLevelResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelResource.setMaxlevel(instrumentLevel.getMaxLevel());
        instrumentLevelResource.setInstrumentid(instrumentLevel.getInstrument().getInstrumentId());
        instrumentLevelResource.setUserid(instrumentLevel.getUser().getId());

        InstrumentLevelUserInstrumentResource instrumentLevelUserInstrumentResource = new InstrumentLevelUserInstrumentResource();
        instrumentLevelUserInstrumentResource.setMaxLevel(instrumentLevel.getMaxLevel());
        instrumentLevelUserInstrumentResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelUserInstrumentResource.setInstrument(instrumentLevel.getInstrument());
        instrumentLevelUserInstrumentResource.setUser(instrumentLevel.getUser());

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentLevelService.getIntrumentLevel(instrumentLevelId)).willReturn(instrumentLevel);
        given(instrumentLevelService.updateInstrumentLevel(Matchers.isA(InstrumentLevel.class))).willReturn(instrumentLevel);
        given(instrumentService.getInstrument(instrument.getInstrumentId())).willReturn(instrument);
        given(userService.findUser(user.getId())).willReturn(user);
        given(userService.updateUser(Matchers.isA(User.class))).willReturn(user);

        mockMvc.perform(put("http://localhost:8080/api/instrumentlevels/instrumentlevel/" + instrumentLevelId).with(bearerToken)
                .content(asJsonString(instrumentLevelResource))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxLevel", CoreMatchers.is(instrumentLevelUserInstrumentResource.getMaxLevel())))
                .andExpect(jsonPath("$.level", CoreMatchers.is(instrumentLevelUserInstrumentResource.getLevel())))
                .andExpect(jsonPath("$.instrument.instrumentId", CoreMatchers.is(instrumentLevelUserInstrumentResource.getInstrument().getInstrumentId())))
                .andExpect(jsonPath("$.user.id", CoreMatchers.is(instrumentLevelUserInstrumentResource.getUser().getId())));
    }

    @Test
    public void testReturn404WhenNotFound() throws Exception {

        given(instrumentLevelService.getIntrumentLevel(123)).willReturn(null);
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/instrumentlevels/" +123).with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }
}
