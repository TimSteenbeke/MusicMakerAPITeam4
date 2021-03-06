package be.kdg.ip.integratie;

import be.kdg.ip.IP2Application;
import be.kdg.ip.domain.*;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.services.api.*;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IP2Application.class)
@WebAppConfiguration
public class TestAgendaService {


    @Autowired
    private UserService userService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CourseService courseService;


    private Lesson lesson;

    private User jef;

    private User tim;



    @Before
    public void setup(){
        /* //INIT SCENARIO
        List<Role> roles = Arrays.asList(new Administrator());
        User jef = new User("jeffrycopy","jefiscool","jef","jefferson",roles);
        User tim = new User("timcopy","tim","brouwers","brouwersiscool",roles);
        userService.addUser(tim);
        Group group = new Group();
        group.setName("testagendaservicegroup");
        group.getUsers().add(jef);
        group.setSupervisor(tim);
        groupService.addGroup(group);
        jef.getGroups().add(group);
        userService.addUser(jef);
        Course course = new Course();
        course.setDescription("Een muziekCOURSE");
        course.setPrice(20);
        course.getStudents().add(jef);
        course.getTeachers().add(tim);
        courseService.addCourse(course);

        //Les aanmaken
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        //Persist lesson
        lessonService.addLesson(lesson);

        this.lesson = lesson;
        this.jef = jef;
        this.tim = tim;
        */
    }

    @Test
    public void checkIfAgendaIsCreated() throws UserServiceException {


    }

    @Test
    public void checkIfLessonIsAddedToAgenda() throws UserServiceException {



    }


    @Test
    public void checkifPerformanceisAddedToAgenda() {

    }

    @Test
    public void checkIfPerformanceisAddedToEVERyAgenda() {

    }

    @Test
    public void checkIfLessonIsRemovedFromAgenda() {

    }

    @Test
    public void checkIfLessonIsRemovedFromEVERYAgenda() {

    }


    @After
    public void removeData() {

    }


    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
