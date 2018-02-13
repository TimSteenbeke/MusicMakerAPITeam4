package be.kdg.ip.services.impl;

import be.kdg.ip.domain.*;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.services.api.*;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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

    @PostConstruct
    public void addDummyInstruments() {
        Instrument instrument = new Instrument(new InstrumentSoort("Slag"),"Drum","drummen","Tim","Tim");
        Instrument instrument2 = new Instrument(new InstrumentSoort("Blaas"),"Trompet","Tim","Tim","Tim");
        Instrument instrument3 = new Instrument(new InstrumentSoort("Snaar"),"Tim","Tim","Tim","Tim");

        instrumentService.addInstrument(instrument);
        instrumentService.addInstrument(instrument2);
        instrumentService.addInstrument(instrument3);
    }


    @PostConstruct
    public void addDummyUser() throws UserServiceException {

        try {
            userService.findUserByUsername("dummy@kdg.be");
            System.out.println(userService.findUserByUsername("dummy@kdg.be").getRoles().get(0).toString());
        } catch (UserServiceException use) {
            List<Role> roles = Arrays.asList(new Administrator());
            User dummyUser = new User("dummy@kdg.be", "Dumb", "Dumber", "dummy@kdg.be", "dummy", roles);

            userService.addUser(dummyUser);
            System.out.println("catch");
            System.out.println(userService.findUserByUsername("dummy@kdg.be").getUsername());

        }
    }


    @PostConstruct void addAgendaItems() throws UserServiceException {
        List<Role> roles = Arrays.asList(new Administrator());
        User jef = new User("jef","jef","jefferson","jef@hotmail.com","jefiscool",roles);
        userService.addUser(jef);

        Agenda agenda = jef.getAgenda();

        LocalDateTime vandaag = LocalDateTime.now();

        Lesson lesson = new Lesson();
        lesson.setStartDateTime(vandaag.plusDays(3));
        lesson.setEndDateTime(vandaag.plusDays(3).plusHours(5));




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
