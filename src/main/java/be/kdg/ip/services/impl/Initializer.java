package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.User;
import be.kdg.ip.domain.roles.Administrator;
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
