package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Composition;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.web.resources.CompositionResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
public class TestMuziekstukken {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private OAuthHelper oAuthHelper;

    @MockBean
    private CompositionService compositionService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    public void testGetCompositionById() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("jef", "ADMIN");

        int compositionId = 1;

        Composition composition = new Composition("Tim", "Test", "Test", "Test", "Test", "Test", "Test", "Test", new byte[5]);

        given(this.compositionService.getComposition(compositionId)).willReturn(composition);

        RequestBuilder request = get("http://localhost:8080/api/compositions/1").with(bearerToken);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titel", CoreMatchers.is(composition.getTitel())));
    }

    @Test
    public void testGetAllCompositions() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        Composition composition = new Composition();
        composition.setTitel("TitelTest");

        List<Composition> compositionList = singletonList(composition);

        given(compositionService.getAllCompositions()).willReturn(compositionList);

        mockMvc.perform(get("http://localhost:8080/api/compositions/").with(bearerToken)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titel", is(composition.getTitel())));
    }

    @Test
    public void testPostComposition() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");


        CompositionResource compositionResource = new CompositionResource();
        compositionResource.setTitel("Tim");
        compositionResource.setArtist("Tim");
        compositionResource.setGenre("Tim");
        compositionResource.setLanguage("Tim");
        compositionResource.setSubject("Tim");
        compositionResource.setInstrumentType("Tim");
        compositionResource.setFileFormat("Tim");
        compositionResource.setContent("Tim");

        this.mockMvc.perform(post("").with(bearerToken)
                //.with(user("admin1").roles("ADMIN"))
                .param("files", "Tim")
                .param("compresource", asJsonString(compositionResource))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(csrf())
                .accept(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCompositionWhenIdExists() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        // given
        given(compositionService.getComposition(1))
                .willReturn(new Composition("Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", new byte[5]));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("https://musicmaker-api-team4.herokuapp.com/api/compositions/1").with(bearerToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        CompositionResource compositionResource = new CompositionResource();
        compositionResource.setTitel("Tim");
        compositionResource.setArtist("Tim");
        compositionResource.setGenre("Tim");
        compositionResource.setLanguage("Tim");
        compositionResource.setSubject("Tim");
        compositionResource.setInstrumentType("Tim");
        compositionResource.setFileFormat("Tim");
        compositionResource.setContent("Tim");

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //assertThat(response.getContentAsString()).isEqualTo(new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5])).getjson();
    }

    @Test
    public void testDeleteComposition() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        compositionService.addComposition(new Composition("Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test", new byte[5]));

        this.mockMvc.perform(delete("/api/compositions/1").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /*@Test
    public void testUpdateComposition() throws Exception {
        Composition composition = new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5]);

        when(compositionService.getComposition(composition.getMuziekstukId())).thenReturn(composition);
        doNothing().when(compositionService).updateComposition(composition).;

        mockMvc.perform(
                put("/api/compositions/{MuziekstukId}", composition.getMuziekstukId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(composition)))
                .andExpect(status().isOk());

        verify(compositionService, times(1)).getComposition(composition.getMuziekstukId());
        verify(compositionService, times(1)).updateComposition(composition);
        verifyNoMoreInteractions(compositionService);
    }*/

    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "ADMIN");

        when(compositionService.getComposition(23)).thenReturn(null);

        this.mockMvc.perform(get("api/compositions/23").with(bearerToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}