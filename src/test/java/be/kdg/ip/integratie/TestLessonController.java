package be.kdg.ip.integratie;

import be.kdg.ip.OAuthHelper;
import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.CourseType;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.LessonService;
import be.kdg.ip.web.LessonController;
import be.kdg.ip.web.resources.LessonResource;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestLessonController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private LessonController controller;

    @Autowired
    private OAuthHelper oAuthHelper;


    @MockBean
    private LessonService lessonService;

    @MockBean
    private CourseService courseService;

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
    public void testAddLesson() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        LessonResource lessonResource = new LessonResource();
        lessonResource.setCourseid(22);
        Course course = new Course();

        CourseType courseType = new CourseType();
        courseType.setDescription("gitaar");
        courseType.setPrice(20);
        course.setCourseType(courseType);

        given(courseService.getCourse(lessonResource.getCourseid())).willReturn(course);

        this.mockMvc.perform(post("http://localhost:8080/api/lesson/").with(bearerToken)
                .content(asJsonString(lessonResource))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testLessonById() throws Exception{
        int lessonId = 11;

        Lesson lesson = new Lesson();
        Course course = new Course();

        CourseType courseType = new CourseType();
        courseType.setDescription("gitaar");
        courseType.setPrice(20);
        course.setCourseType(courseType);

        lesson.setLessonId(lessonId);
        lesson.setCourse(course);

        given(this.lessonService.getLesson(lessonId)).willReturn(lesson);

        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        mockMvc.perform(get("http://localhost:8080/api/lesson/11").with(bearerToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(lesson.getLessonId())))
                .andExpect(jsonPath("$.course.courseType.description", CoreMatchers.is(lesson.getCourse().getCourseType().getDescription())))
                .andExpect(jsonPath("$.course.courseType.price", CoreMatchers.is(lesson.getCourse().getCourseType().getPrice())));
    }

    @Test
    public void testGetAllLessons() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        int lessonId = 11;

        Lesson lesson = new Lesson();
        Course course = new Course();

        CourseType courseType = new CourseType();
        courseType.setDescription("gitaar");
        courseType.setPrice(20);
        course.setCourseType(courseType);

        lesson.setLessonId(lessonId);
        lesson.setCourse(course);

        List<Lesson> lessonList = singletonList(lesson);

        given(lessonService.getAllLessons()).willReturn(lessonList);

        mockMvc.perform(get("http://localhost:8080/api/lesson").with(bearerToken)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(lesson.getLessonId())))
                .andExpect(jsonPath("$[0].course.courseType.description", CoreMatchers.is(lesson.getCourse().getCourseType().getDescription())))
                .andExpect(jsonPath("$[0].course.courseType.price", CoreMatchers.is(lesson.getCourse().getCourseType().getPrice())));
    }

    @Test
    public void testDeleteLesson() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        this.mockMvc.perform(delete("http://localhost:8080/api/lesson/11").with(bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateLesson() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        LessonResource lessonResource = new LessonResource();
        Lesson lesson = new Lesson();
        Course course = new Course();
        course.setCourseId(12);

        CourseType courseType = new CourseType();
        courseType.setDescription("gitaar");
        courseType.setPrice(20);
        course.setCourseType(courseType);

        lesson.setLessonId(10);
        lesson.setCourse(course);

        lessonResource.setCourseid(12);


        given(lessonService.getLesson(lesson.getLessonId())).willReturn(lesson);
        given(courseService.getCourse(lessonResource.getCourseid())).willReturn(course);

        this.mockMvc.perform(put("http://localhost:8080/api/lesson/lesson/10").with(bearerToken)
                .content(asJsonString(lessonResource))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseid", CoreMatchers.is(lessonResource.getCourseid())));
    }

    @Test
    public void testReturn404WhenNotFound() throws Exception {
        RequestPostProcessor bearerToken = oAuthHelper.addBearerToken("gemockteUser","ADMIN");

        given(lessonService.getLesson(987)).willReturn(null);

        this.mockMvc.perform(get("http://localhost:8080/api/lesson/123").with(bearerToken)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
