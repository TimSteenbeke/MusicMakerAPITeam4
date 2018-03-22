package be.kdg.ip.web;

import be.kdg.ip.domain.*;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.resources.AgendaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AgendaController {


    @Autowired
    private UserService userService;


    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET,value ="/api/agenda")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<AgendaResource> getAgenda(Principal principal) {

        try {
            String username = principal.getName();
            User user =  userService.findUserByUsername(username);

            AgendaResource agendaResource = new AgendaResource();
            agendaResource.setAgendaOwner(user.getUsername());

            List<Lesson> lessons = new ArrayList<>();
            for (Course course : user.getCourses()) {
                lessons.addAll(course.getLessons());
            }

            for (Course course: user.getTeachescourses()) {
                lessons.addAll(course.getLessons());
            }
            agendaResource.setLessons(lessons);

            List<Performance> performances = new ArrayList<>();
            for (Group group: user.getGroups()) {
                performances.addAll(group.getPerformances());
            }

            agendaResource.setPerformances(performances);

            return  new ResponseEntity<>(agendaResource, HttpStatus.OK);

        } catch (UserServiceException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET,value ="/api/agenda/{userid}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    public ResponseEntity<AgendaResource> getOtherAgenda(@PathVariable("userid") int userId) {

        User user =  userService.findUser(userId);

        AgendaResource agendaResource = new AgendaResource();
        agendaResource.setAgendaOwner(user.getFirstname() + " " + user.getLastname());

        List<Lesson> lessons = new ArrayList<>();
        for (Course course : user.getCourses()) {
            lessons.addAll(course.getLessons());
        }
        agendaResource.setLessons(lessons);

        List<Performance> performances = new ArrayList<>();
        for (Group group: user.getGroups()) {
            performances.addAll(group.getPerformances());
        }

        agendaResource.setPerformances(performances);

        return  new ResponseEntity<>(agendaResource, HttpStatus.OK);
    }



}
