package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.Performance;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.PerformanceService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.dto.StatusDTO;
import be.kdg.ip.web.resources.PerformanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST,value ="/api/performance")
    //ToDo: Authorization fix: performance post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<PerformanceResource> addLesson(@Valid @RequestBody PerformanceResource performanceResource) {

        //performance aanmaken based op perforamnceresource
        Performance performance = new Performance();
        performance.setStartDateTime(performanceResource.getStartdatetime());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setBeschrijving(performanceResource.getBeschrijving());

        //Group object ophalen en koppelen aan performance
        Group group = groupService.getGroup(performanceResource.getGroupId());
        performance.setGroup(group);

        //performance toevoegen
        performanceService.addPerformance(performance);

        //add Performance to every involved agenda
        agendaService.addPerformanceToEveryAgenda(performance);

        return  new ResponseEntity<>(performanceResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/api/performance/present/{performanceid}")
    public ResponseEntity registerUserPresent(@PathVariable("performanceid")  int performanceId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            performanceService.setUserPresent(performanceId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/api/performance/absent/{performanceid}")
    public ResponseEntity registerUserAbsent(@PathVariable("performanceid")  int performanceId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            performanceService.setUserAbsent(performanceId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/api/lesson/attendancestatus/{performanceid}")
    public ResponseEntity<StatusDTO> getAttendanceStatus(@PathVariable("performanceid")  int performanceId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            Performance performance = performanceService.getPerformance(performanceId);

            if (performance.getAbsentMembers().contains(user)) {
                // ABSENT
                return new ResponseEntity<StatusDTO>(new StatusDTO("absent"),HttpStatus.OK);
            }
            else {
                if (performance.getPresentMembers().contains(user)) {
                    //PRESENT
                    return new ResponseEntity<StatusDTO>(new StatusDTO("present"),HttpStatus.OK);
                } else {
                    // NOT SET
                    return new ResponseEntity<StatusDTO>(new StatusDTO("nostatus"),HttpStatus.OK);
                }
            }
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }







}
