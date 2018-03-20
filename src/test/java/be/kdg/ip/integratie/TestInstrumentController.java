package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.web.InstrumentController;
import be.kdg.ip.web.resources.InstrumentGetResource;
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
import static org.assertj.core.api.Assertions.in;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestInstrumentController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private InstrumentController instrumentController;

    @Autowired
    private OAuthHelper oAuthHelper;

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
        assertThat(instrumentController).isNotNull();
    }

    @Test
    public void findInstrumentById() throws Exception {
        int instrumentId =1;

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

        given(instrumentService.getInstrument(instrumentId)).willReturn(instrument);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/instruments/1").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", CoreMatchers.is(instrument.getType())))
                .andExpect(jsonPath("$.details", CoreMatchers.is(instrument.getDetails())))
                .andExpect(jsonPath("$.instrumentid",CoreMatchers.is(instrument.getInstrumentId())))
                .andExpect(jsonPath("$.instrumentname",CoreMatchers.is(instrument.getInstrumentName())))
                .andExpect(jsonPath("$.instrumentCategory.instrumentCategoryId",CoreMatchers.is(instrument.getInstrumentCategory().getInstrumentCategoryId())))
                .andExpect(jsonPath("$.instrumentCategory.categoryName",CoreMatchers.is(instrument.getInstrumentCategory().getCategoryName())));
    }

    @Test
    public void findAllInstruments() throws Exception {


        
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

        List<Instrument> instruments = singletonList(instrument);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentService.getAllInstruments()).willReturn(instruments);

        mockMvc.perform(get("http://localhost:8080/api/instruments").with(bearerToken))
                .andDo(print())
                .andExpect(jsonPath("$[0].type", CoreMatchers.is(instrument.getType())))
                .andExpect(jsonPath("$[0].details", CoreMatchers.is(instrument.getDetails())))
                .andExpect(jsonPath("$[0].instrumentid",CoreMatchers.is(instrument.getInstrumentId())))
                .andExpect(jsonPath("$[0].instrumentname",CoreMatchers.is(instrument.getInstrumentName())))
                .andExpect(jsonPath("$[0].instrumentCategory.instrumentCategoryId",CoreMatchers.is(instrument.getInstrumentCategory().getInstrumentCategoryId())))
                .andExpect(jsonPath("$[0].instrumentCategory.categoryName",CoreMatchers.is(instrument.getInstrumentCategory().getCategoryName())));
    }

    @Test
    public void testDeleteInstrument() throws Exception {

        int instrumentId =1;

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

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentService.getInstrument(instrumentId)).willReturn(null);
        mockMvc.perform(delete("http://localhost:8080/api/instruments/" + instrumentId).with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddInstrument() throws Exception {

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

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentService.addInstrument(instrument)).willReturn(instrument);

        mockMvc.perform(post("http://localhost:8080/api/instruments").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", CoreMatchers.is(instrument.getType())))
                .andExpect(jsonPath("$.details", CoreMatchers.is(instrument.getDetails())))
                .andExpect(jsonPath("$.instrumentid",CoreMatchers.is(instrument.getInstrumentId())))
                .andExpect(jsonPath("$.instrumentname",CoreMatchers.is(instrument.getInstrumentName())))
                .andExpect(jsonPath("$.instrumentCategory.instrumentCategoryId",CoreMatchers.is(instrument.getInstrumentCategory().getInstrumentCategoryId())))
                .andExpect(jsonPath("$.instrumentCategory.categoryName",CoreMatchers.is(instrument.getInstrumentCategory().getCategoryName())));
    }

    @Test
    public void testUpdateInstrument() throws Exception {

        int instrumentId = 10;
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

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentService.getInstrument(instrumentId)).willReturn(new Instrument());
        given(instrumentService.updateInstrument(Matchers.isA(Instrument.class))).willReturn(instrument);

        mockMvc.perform(put("http://localhost:8080/api/instruments/instrument/" + instrumentId).with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", CoreMatchers.is(instrument.getType())))
                .andExpect(jsonPath("$.details", CoreMatchers.is(instrument.getDetails())))
                .andExpect(jsonPath("$.instrumentid",CoreMatchers.is(instrument.getInstrumentId())))
                .andExpect(jsonPath("$.instrumentname",CoreMatchers.is(instrument.getInstrumentName())))
                .andExpect(jsonPath("$.instrumentCategory.instrumentCategoryId",CoreMatchers.is(instrument.getInstrumentCategory().getInstrumentCategoryId())))
                .andExpect(jsonPath("$.instrumentCategory.categoryName",CoreMatchers.is(instrument.getInstrumentCategory().getCategoryName())));
    }

    @Test
    public void testReturn404WhenNotFound() throws Exception {

        int instrumentId = 1100;
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(instrumentService.getInstrument(instrumentId)).willReturn(null);

        mockMvc.perform(get("http://localhost:8080/api/instruments/"+ instrumentId).with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
