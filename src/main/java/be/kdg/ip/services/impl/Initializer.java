package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentSoort;
import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.User;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.InstrumentSoortService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
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
    private InstrumentSoortService instrumentSoortService;

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
}
