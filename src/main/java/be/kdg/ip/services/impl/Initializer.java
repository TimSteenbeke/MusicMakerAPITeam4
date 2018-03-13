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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


        Composition composition = new Composition("Test", "Test", "Test","Test","Test","Test","Test","Test.pdf",new byte[5]);
        Composition composition2 = new Composition("Tim", "Tim Brouwers", "Engels","Rock","Tim ziet het niet meer zitten","Blaas","www.youtube.com/timbrouwers","Timbrouwers.xml",new byte[5]);

        compositionService.addComposition(composition);
        compositionService.addComposition(composition2);
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


        Address address = new Address("straatje","12","2910","Essen","belgie");
        Address address2 = new Address("street","40","2910","Essen","belgie");
        Address address3 = new Address("stationsweg","50","2910","Essen","belgie");
        Address address4 = new Address("veldweg","68","2910","Essen","belgie");

        addressService.addAddress(address);
        addressService.addAddress(address2);
        addressService.addAddress(address3);
        addressService.addAddress(address4);

        User jef = new User("jef", "jefiscool", "jef", "jefferson", rolesAdmin,new byte[0],address);
        User jos = new User("jos", "josiscooler", "jos", "josserson", rolesStudent,new byte[0],address2);
        User tim = new User("tim", "tim", "brouwers", "brouwersiscool", rolesTeacher,new byte[0],address3);
        userService.addUser(tim);
        User timS = new User("timS", "tims", "Tim", "Steenbeke", rolesAll,new byte[0],address4);
        userService.addUser(timS);

        Group group = new Group();
        group.setName("testGroup");
        group.getUsers().add(jef);
        group.getUsers().add(jos);
        group.setSupervisor(tim);
        groupService.addGroup(group);

        jef.getGroups().add(group);

        userService.addUser(jef);
        userService.addUser(jos);


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
