package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.domain.Instrument;

import be.kdg.ip.services.api.InstrumentCategoryService;
import be.kdg.ip.services.api.InstrumentService;

import be.kdg.ip.web.InstrumentCategoryController;
import be.kdg.ip.web.resources.InstrumentCategoryResource;
import org.codehaus.jackson.map.ObjectMapper;
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

import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * Created by jariv on 21/03/2018.
 */
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestInstrumentCategoryService {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private InstrumentCategoryController controller;

    @Autowired
    private OAuthHelper oAuthHelper;


    @MockBean
    private InstrumentCategoryService instrumentCategoryService;

    @MockBean
    private InstrumentService instrumentService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testFindInstrumentCategoryById() throws Exception {
        int instrumentCategoryId = 11;
        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("Biano");
        instrumentCategory.setInstrumentList(new ArrayList<>());

        given(this.instrumentCategoryService.getInstrumentCategory(instrumentCategoryId)).willReturn(instrumentCategory);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/instrumentsoorten/11").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryname", CoreMatchers.is(instrumentCategory.getCategoryName())))
                .andExpect(jsonPath("$.instruments", CoreMatchers.is(instrumentCategory.getInstrumentList())));
    }

    @Test
    public void testGetAllInstrumentCategories() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("Biano");
        instrumentCategory.setInstrumentList(new ArrayList<>());

        List<InstrumentCategory> instrumentCategoryList = singletonList(instrumentCategory);

        given(instrumentCategoryService.getAllInstrumentCategories()).willReturn(instrumentCategoryList);

        mockMvc.perform(get("http://localhost:8080/api/instrumentsoorten").with(bearerToken)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoryname", CoreMatchers.is(instrumentCategory.getCategoryName())))
                .andExpect(jsonPath("$[0].instruments", CoreMatchers.is(instrumentCategory.getInstrumentList())));
    }

    @Test
    public void testDeleteInstrumentCategory() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        ArrayList<Instrument> instruments = new ArrayList<>();

        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("Biano");
        instrumentCategory.setInstrumentCategoryId(11);

        Instrument instrument = new Instrument();
        instrument.setInstrumentId(1);
        instrument.setDetails("Test");
        instrument.setInstrumentName("Test");
        instrument.setType("Test");
        instrument.setInstrumentCategory(instrumentCategory);

        instruments.add(instrument);
        instrumentCategory.setInstrumentList(instruments);
        ArrayList<Integer> instrumentIds = new ArrayList<>();
        instrumentIds.add(1);

        given(instrumentCategoryService.getInstrumentCategory(11)).willReturn(instrumentCategory);
        given(instrumentService.updateInstrument(Matchers.isA(Instrument.class))).willReturn(instrument);

        this.mockMvc.perform(post("http://localhost:8080/api/instrumentsoorten/11").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateInstrumentCategory() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName("Biano");
        instrumentCategory.setInstrumentList(new ArrayList<>());

        InstrumentCategoryResource resource = new InstrumentCategoryResource();
        resource.setCategoryname("Biano");
        resource.setInstrumentIds(new ArrayList<>());


        given(instrumentCategoryService.addInstrumentCategory(Matchers.isA(InstrumentCategory.class))).willReturn(instrumentCategory);


        this.mockMvc.perform(post("http://localhost:8080/api/instrumentsoorten").with(bearerToken)
                .content(asJsonString(resource))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryname", CoreMatchers.is(resource.getCategoryname())))
                .andExpect(jsonPath("$.instruments", CoreMatchers.is(resource.getInstrumentIds())));
    }

    @Test
    public void testUpdateInstrumentCategory() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setInstrumentCategoryId(11);
        instrumentCategory.setCategoryName("Biano");
        instrumentCategory.setInstrumentList(new ArrayList<>());

        InstrumentCategoryResource resource = new InstrumentCategoryResource();
        resource.setCategoryname("Biano");
        resource.setInstrumentIds(new ArrayList<>());

        given(instrumentCategoryService.getInstrumentCategory(11)).willReturn(new InstrumentCategory());
        given(instrumentCategoryService.updateInstrumentCategory(Matchers.isA(InstrumentCategory.class))).willReturn(instrumentCategory);

        this.mockMvc.perform(put("http://localhost:8080/api/instrumentsoorten/instrumentsoort/11").with(bearerToken)
                .content(asJsonString(resource))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryname", CoreMatchers.is(resource.getCategoryname())))
                .andExpect(jsonPath("$.instruments", CoreMatchers.is(resource.getInstrumentIds())));
    }


    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "ADMIN");

        when(instrumentCategoryService.getInstrumentCategory(123)).thenReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/instrumentsoorten/123").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

