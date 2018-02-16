package be.kdg.ip.services.impl;
import be.kdg.ip.domain.*;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.api.*;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by wouter on 31.01.17.
 */
@Service
public class Initializer {

    @Autowired
    private UserService userService;

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private InstrumentSoortService instrumentSoortService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GroupService groupService;


    @PostConstruct
    public void addDummyInstruments() {

        InstrumentSoort instrumentSoort = new InstrumentSoort("Slag");
        InstrumentSoort instrumentSoort2 = new InstrumentSoort("Blaas");
        InstrumentSoort instrumentSoort3 = new InstrumentSoort("Snaar");

        instrumentSoortService.addInstrumentSoort(instrumentSoort);
        instrumentSoortService.addInstrumentSoort(instrumentSoort2);
        instrumentSoortService.addInstrumentSoort(instrumentSoort3);

        Instrument instrument = new Instrument(instrumentSoort,"Drum","drummen","Tim","Tim");
        Instrument instrument2 = new Instrument(instrumentSoort2,"Trompet","Tim","Tim","Tim");
        Instrument instrument3 = new Instrument(instrumentSoort3,"Tim","Tim","Tim","Tim");

        instrumentService.addInstrument(instrument);
        instrumentService.addInstrument(instrument2);
        instrumentService.addInstrument(instrument3);
    }


    @PostConstruct
    public void addDummyUser() throws UserServiceException {
        List<Role> roles = Arrays.asList(new Administrator());
        User dummyUser = new User("lode.wouters@student.kdg.be", "password", "Lode", "Wouters", roles);

        userService.addUser(dummyUser);
        /*try {
            userService.findUserByUsername("dummy@kdg.be");
            System.out.println(userService.findUserByUsername("dummy@kdg.be").getRoles().get(0).toString());
        } catch (UserServiceException use) {
            List<Role> roles = Arrays.asList(new Administrator());
            User dummyUser = new User("lode.wouters@student.kdg.be", "password", "Lode", "Wouters", null);

            userService.addUser(dummyUser);


        }*/
    }

    @PostConstruct
    public void addDummyGroups(){
        List<User> users = new ArrayList<>();
        //List<User> users2 = new ArrayList<>();
        User u1 = new User("lode.wouters@student.kdg.be", "password", "Lode", "Wouters", null);
        User u2 = new User("test.test@student.kdg.be", "test", "test", "test", null);
        User supervisor = new User("supervisor.sup@student.kdg.be", "supervisor", "super", "visor", null);

        users.add(u1);
        users.add(u2);
        //users2.add(u2);

        List<Group> groups = new ArrayList<>();
        Group group1 = new Group("testGroup", supervisor, users);
        //Group group2 = new Group("testGroup2", supervisor, users);

        groups.add(group1);
        //groups.add(group2);
        for(Group group: groups){
            groupService.addGroup(group);
        }

        /*for (User user: users) {
            userService.addUser(user);
        }

        userService.addUser(supervisor);*/

        //groupService.getGroup(1).setUsers(users);
        //groupService.getGroup(1).setSupervisor(supervisor);
        /*groupService.getGroup(2).setUsers(users2);
        groupService.getGroup(2).setSupervisor(supervisor);*/

    }


    @PostConstruct void addAgendaItems() throws UserServiceException {
        List<Role> roles = Arrays.asList(new Administrator());
        User jef = new User("jef","jefiscool","jef","jefferson",roles);
        userService.addUser(jef);


        User tim = new User("tim","tim","brouwers","brouwersiscool",roles);
        userService.addUser(tim);

        Course course = new Course();
        course.setBeschrijving("Een muziekCOURSE");
        course.setPrijs(20);



        Agenda agenda = jef.getAgenda();

        LocalDateTime vandaag = LocalDateTime.now();

        Lesson lesson = new Lesson();
        lesson.setStartDateTime(vandaag.plusDays(3));
        lesson.setEndDateTime(vandaag.plusDays(3).plusHours(5));

        course.getStudents().add(jef);
        course.getTeachers().add(tim);
        courseService.addCourse(course);



        lesson.setCourse(course);
        lessonService.addLesson(agenda,lesson);

        Performance performance = new Performance();
        performance.setBeschrijving("een beschrijving van een optreden");



        performance.setStartDateTime(vandaag);
        performance.setEndDateTime(vandaag.plusHours(2));


        Performance performance2 = new Performance();
        performance2.setBeschrijving("een beschrijving van ANDER OPTREDEN");
        performance2.setStartDateTime(vandaag.plusDays(1));
        performance2.setEndDateTime(vandaag.plusDays(1).plusHours(4));

        performanceService.addPerformance(performance);
        performanceService.addPerformance(performance2);

        agenda.getPerformances().add(performance);
        agenda.getPerformances().add(performance2);
        agendaService.saveAgenda(agenda);



        System.out.println("agenda items toegevoegd");
        System.out.println("ok");
        System.out.println("test");

    }
}
