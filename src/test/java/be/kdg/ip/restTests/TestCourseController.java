package be.kdg.ip.restTests;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Course;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.web.CourseController;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsMapContaining;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.CompletableFuture.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "STUDENT");

        RequestBuilder request = get("http://localhost:8080/api/courses").with(bearerToken);

        //TODO: write a better test

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addCourse() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "STUDENT");

        RequestBuilder request = post("http://localhost:8080/api/courses").with(bearerToken);



        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    public void updateCourse() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "STUDENT");

        int id= 1;

        RequestBuilder request = put("http://localhost:8080/api/courses" + id).with(bearerToken);


        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    public void deleteCourse() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "STUDENT");

        int id= 2;

        RequestBuilder request = delete("http://localhost:8080/api/courses" + id).with(bearerToken);


        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getASingleCourse() throws  Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("mockedUser", "STUDENT");

        int id=8000;

        Course course = new Course("description",50,new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
        courseService.addCourse(course);

        given(this.courseService.getCourse(id)).willReturn(course);


        RequestBuilder request = get("http://localhost:8080/api/courses/" + id).with(bearerToken);


        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

}
