package be.kdg.ip.services.impl;

import be.kdg.ip.domain.*;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.domain.roles.Student;
import be.kdg.ip.domain.roles.Teacher;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.api.*;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private NewsItemService newsItemService;

    @PostConstruct
    public void addDummyInstruments() {

        InstrumentSoort instrumentSoort = new InstrumentSoort("Slag");
        InstrumentSoort instrumentSoort2 = new InstrumentSoort("Blaas");
        InstrumentSoort instrumentSoort3 = new InstrumentSoort("Snaar");

        instrumentSoortService.addInstrumentSoort(instrumentSoort);
        instrumentSoortService.addInstrumentSoort(instrumentSoort2);
        instrumentSoortService.addInstrumentSoort(instrumentSoort3);

        Instrument instrument = new Instrument(instrumentSoort, "Drum", "drummen", "Tim");
        Instrument instrument2 = new Instrument(instrumentSoort2, "Trompet", "Tim", "Tim");
        Instrument instrument3 = new Instrument(instrumentSoort3, "Tim", "Tim", "Tim");

        instrumentService.addInstrument(instrument);
        instrumentService.addInstrument(instrument2);
        instrumentService.addInstrument(instrument3);


        Composition composition = new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test",new byte[5]);
        compositionService.addComposition(composition);

    }

    @PostConstruct
    void addAgendaItems() throws UserServiceException {
        Role administrator = new Administrator();
        roleService.addRole(administrator);
        Role teacher = new Teacher();
        roleService.addRole(teacher);
        Role student = new Student();
        roleService.addRole(student);

        List<Role> rolesAdmin = new ArrayList<Role>();
        rolesAdmin.add(administrator);
        List<Role> rolesTeacher = new ArrayList<Role>();
        rolesTeacher.add(teacher);
        List<Role> rolesStudent = new ArrayList<Role>();
        rolesStudent.add(student);
        List<Role> rolesAll = new ArrayList<Role>();
        rolesAll.add(administrator);
        rolesAll.add(teacher);
        rolesAll.add(student);

        Address address = new Address("straat","29","2910","Essen","belgie");
        Address address2 = new Address("straatje","2","2910","Essen","belgie");
        Address address3 = new Address("straatweg","8","2910","Essen","belgie");
        Address address4 = new Address("wegstraat","77","2910","Essen","belgie");

        addressService.addAddress(address);
        addressService.addAddress(address2);
        addressService.addAddress(address3);
        addressService.addAddress(address4);


        User jef = new User("jef", "jefiscool", "jef", "jefferson", rolesAdmin,new byte[0],address);
        User jos = new User("jos", "josiscooler", "jos", "josserson", rolesStudent,new byte[0],address2);
        User tim = new User("tim", "tim", "brouwers", "brouwersiscool", rolesTeacher,new byte[0],address3);
        User timS = new User("timS", "tims", "Tim", "Steenbeke", rolesAll,new byte[0],address4);



        userService.addUser(timS);
        userService.addUser(tim);
        userService.addUser(jef);
        userService.addUser(jos);

        NewsItem newsItem = new NewsItem("Dit is een melding!","Dit is de inhoud van de melding","Tim Brouwers",new Date());
        newsItemService.addNewsItem(newsItem);

        Group group = new Group();
        group.setName("testGroup");
        List<User> users = group.getUsers();
        users.add(userService.findUserByUsername("jef"));
        users.add(userService.findUserByUsername("jos"));
        group.setUsers(users);
        group.setSupervisor(userService.findUserByUsername("tim"));
        groupService.addGroup(group);


        jef.getGroups().add(group);
        jos.getGroups().add(group);


        Group group2 = new Group();
        group2.setName("testGroup2");
        group2.getUsers().add(userService.findUserByUsername("tim"));
        group2.getUsers().add(userService.findUserByUsername("jos"));
        group2.setSupervisor(userService.findUserByUsername("jef"));
        groupService.addGroup(group2);

        tim.getGroups().add(group2);
        jos.getGroups().add(group2);

        userService.updateUser(tim);
        userService.updateUser(jos);
        userService.updateUser(jef);


        Course course = new Course();
        course.setBeschrijving("Een muziekCOURSE");
        course.setPrijs(20);


        Agenda agenda = jef.getAgenda();

        LocalDateTime vandaag = LocalDateTime.now();

        Lesson lesson = new Lesson();
        lesson.setStartDateTime(vandaag.plusDays(3));
        lesson.setEndDateTime(vandaag.plusDays(3).plusHours(5));

        course.getStudents().add(jef);
        jef.getCourses().add(course);
        userService.updateUser(jef);
        course.getTeachers().add(tim);
        tim.getTeachescourses().add(course);
        userService.updateUser(tim);
        courseService.addCourse(course);


        lesson.setCourse(course);
        lessonService.addLesson(agenda, lesson);

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


        //GROUPS TOEVOEGEN
    }
}
