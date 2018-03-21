package be.kdg.ip.restTests;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.CourseType;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.CourseTypeService;
import be.kdg.ip.web.CourseController;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static be.kdg.ip.integratie.TestCourseType.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
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
@ContextConfiguration
public class TestCourseController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CourseController controller;

    @Autowired
    private OAuthHelper oAuthHelper;

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseTypeService courseTypeService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }


    @Test
    public void getAllCourses() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser", "STUDENT");

        RequestBuilder request = get("http://localhost:8080/api/courses").with(bearerToken);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCourse() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");
        Course course = new Course();
        course.setPrice(40);
        given(courseService.getCourse(11)).willReturn(course);

        this.mockMvc.perform(delete("http://localhost:8080/api/courses/11").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void getCourseById() throws Exception {
        int courseId = 11;

        Course course = new Course();
        CourseType courseType = new CourseType();
        courseType.setCourseTypeId(800);
        courseType.setPrice(50);
        courseType.setDescription("banjoles");

        course.setCourseType(courseType);


        given(this.courseTypeService.getCourseType(800)).willReturn(courseType);
        given(this.courseService.getCourse(courseId)).willReturn(course);


        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/courses/11").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseType.courseTypeId", CoreMatchers.is(courseType.getCourseTypeId())))
                .andExpect(jsonPath("$.courseType.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.courseType.description", CoreMatchers.is(courseType.getDescription())));
    }

    @Test
    public void addCourse() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        Course course = new Course();
        course.setCourseId(11);
        CourseType courseType = new CourseType();
        courseType.setCourseTypeId(400);
        courseType.setDescription("Triangelles");
        courseType.setPrice(70);

        course.setCourseType(courseType);


        given(courseService.getCourse(11)).willReturn(course);
        given(courseTypeService.getCourseType(400)).willReturn(courseType);

        this.mockMvc.perform(post("http://localhost:8080/api/courses").with(bearerToken)
                .content(asJsonString(course))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("http://localhost:8080/api/courses/11").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseType.courseTypeId", CoreMatchers.is(courseType.getCourseTypeId())))
                .andExpect(jsonPath("$.courseType.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.courseType.description", CoreMatchers.is(courseType.getDescription())));
    }

    @Test
    public void updateCourse() throws Exception {
        //TODO: CourseId comes in as 0 in controller -> FIX
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        Course course = new Course();
        course.setCourseId(11);
        CourseType courseType = new CourseType();
        courseType.setCourseTypeId(400);
        courseType.setDescription("Triangelles");
        courseType.setPrice(75);

        course.setCourseType(courseType);


        given(courseTypeService.getCourseType(400)).willReturn(courseType);
        given(courseService.getCourse(11)).willReturn(course);


        this.mockMvc.perform(put("http://localhost:8080/api/courses/11").with(bearerToken)
                .content(asJsonString(course))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("http://localhost:8080/api/courses/11").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseType.courseTypeId", CoreMatchers.is(courseType.getCourseTypeId())))
                .andExpect(jsonPath("$.courseType.price", CoreMatchers.is(courseType.getPrice())))
                .andExpect(jsonPath("$.courseType.description", CoreMatchers.is(courseType.getDescription())));



    }

    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        when(courseService.getCourse(999)).thenReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/courses/999").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }





}
