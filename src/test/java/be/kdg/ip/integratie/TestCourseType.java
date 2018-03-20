package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.CourseType;
import be.kdg.ip.services.api.CourseTypeService;

import be.kdg.ip.web.CourseTypeController;
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
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestCourseType {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CourseTypeController controller;

    @Autowired
    private OAuthHelper oAuthHelper;


    @MockBean
    private CourseTypeService courseTypeService;

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
    public void testGetCourseTypeById() throws Exception{
        int courseTypeId = 11;

        CourseType courseType = new CourseType();
        courseType.setPrice(10);
        courseType.setDescription("Gitaarles");

        given(this.courseTypeService.getCourseType(courseTypeId)).willReturn(courseType);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/courseTypes/11").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(courseType.getDescription())));
    }

    @Test
    public void testGetAllCourseTypes() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        CourseType courseType = new CourseType();
        courseType.setDescription("PianoLes");
        courseType.setPrice(20);

        List<CourseType> courseTypeList = singletonList(courseType);

        given(courseTypeService.getAllCourseTypes()).willReturn(courseTypeList);

        mockMvc.perform(get("http://localhost:8080/api/courseTypes").with(bearerToken)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is(courseType.getDescription())))
                .andExpect(jsonPath("$[0].price", is(courseType.getPrice())));

    }

    @Test
    public void testDeleteCourseType() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        CourseType courseType = new CourseType();
        courseType.setCourseTypeId(11);
        courseType.setDescription("PianoLes");
        courseType.setPrice(40);
        given(courseTypeService.getCourseType(11)).willReturn(courseType);

        this.mockMvc.perform(delete("http://localhost:8080/api/courseTypes/11").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddCourseType() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        CourseType courseType = new CourseType();
        courseType.setDescription("PianoLes");
        courseType.setPrice(40);

        given(courseTypeService.addCourseType(Matchers.isA(CourseType.class))).willReturn(courseType);

        this.mockMvc.perform(post("http://localhost:8080/api/courseTypes").with(bearerToken)
                .content(asJsonString(courseType))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(courseType.getDescription())));
    }

    @Test
    public void testUpdateCourseType() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        CourseType courseType = new CourseType();
        courseType.setCourseTypeId(98);
        courseType.setDescription("GitaarLes");
        courseType.setPrice(10);

        given(courseTypeService.getCourseType(98)).willReturn(new CourseType());
        given(courseTypeService.updateCourseType(Matchers.isA(CourseType.class))).willReturn(courseType);

        this.mockMvc.perform(put("http://localhost:8080/api/courseTypes/98").with(bearerToken)
                .content(asJsonString(courseType))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseTypeId", CoreMatchers.is(courseType.getCourseTypeId())))
                .andExpect(jsonPath("$.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(courseType.getDescription())));
    }


    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        when(courseTypeService.getCourseType(123)).thenReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/courseTypes/123").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
