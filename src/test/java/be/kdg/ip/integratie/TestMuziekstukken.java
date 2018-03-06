package be.kdg.ip.integratie;

import be.kdg.ip.IP2Application;
import be.kdg.ip.domain.Composition;
import be.kdg.ip.repositories.api.CompositionRepository;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.web.CompositionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestMuziekstukken {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Mock
    private CompositionService compositionService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void givenGreetURIWithPostAndFormData_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc.perform(post("/api/compositions/")
                .param("titel", "Tim")
                .param("artist", "Tim")
                .param("language", "Tim")
                .param("genre", "Tim")
                .param("subject", "Tim")
                .param("instrumentType", "Tim")
                .param("link", "Tim")
                .param("fileFormat", "Tim")
                .param("content", "Tim")).andDo(print()).andExpect(status().isOk())

                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.titel").value("Tim"))
                .andExpect(jsonPath("$.MuziekstukId").value(1));
    }

    @Test
    public void getRepairByRepairId() throws Exception {
        // mock evil repair
        int compId = 1;
        Composition composition = new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5]);

        given(compositionService.getComposition(compId)).willReturn(composition);

        mockMvc.perform(get("http://localhost:8080/api/compositions/" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("muziekstukId", is(composition.getMuziekstukId())));
    }

    /*@Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        given(compositionService.getComposition(1))
                .willReturn(new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5]));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("https://musicmaker-api-team4.herokuapp.com/api/compositions/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonComposition.write(new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5])).getJson()
        );
    }*/

    /*@Test
    public void createANewComposition() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                post("https://musicmaker-api-team4.herokuapp.com/api/compositions/").contentType(MediaType.APPLICATION_OCTET_STREAM).content(
                        jsonComposition.write(new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5])).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }*/





}
