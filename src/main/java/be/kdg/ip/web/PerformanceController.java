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
import be.kdg.ip.web.resources.PerformanceGetResource;
import be.kdg.ip.web.resources.PerformanceResource;
import org.bouncycastle.pqc.crypto.gmss.GMSSRootCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @PostMapping
    //ToDo: Authorization fix: performance post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<PerformanceResource> addPerformance(@Valid @RequestBody PerformanceResource performanceResource) {

        //performance aanmaken based op perforamnceresource
        Performance performance = new Performance();
        performance.setStartDateTime(performanceResource.getStartdatetime());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setBeschrijving(performanceResource.getBeschrijving());

        //Group object ophalen en koppelen aan performance
        Group group = groupService.getGroup(performanceResource.getGroup());
        performance.setGroup(group);

        //performance toevoegen
        performanceService.addPerformance(performance);

        //add Performance to every involved agenda
        agendaService.addPerformanceToEveryAgenda(performance);

        return  new ResponseEntity<>(performanceResource, HttpStatus.OK);
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<PerformanceGetResource> getPerformance(@PathVariable int performanceId){

        Performance performance = performanceService.getPerformance(performanceId);

        PerformanceGetResource performanceGetResource = new PerformanceGetResource();

       performanceGetResource.setBeschrijving(performance.getBeschrijving());
       performanceGetResource.setEnddatetime(performance.getEndDateTime());
       performanceGetResource.setStartdatetime(performance.getEndDateTime());
       performanceGetResource.setGroup(performance.getGroup());

        return new ResponseEntity<>(performanceGetResource,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PerformanceGetResource>> getAllPerformances(){
        List<Performance> performances = performanceService.getAllPerformances();
        List<PerformanceGetResource> performanceGetResources = new ArrayList<>();

        for (Performance performance : performances){
            PerformanceGetResource performanceGetResource = new PerformanceGetResource();
            performanceGetResource.setBeschrijving(performance.getBeschrijving());
            performanceGetResource.setEnddatetime(performance.getEndDateTime());
            performanceGetResource.setStartdatetime(performance.getEndDateTime());
            performanceGetResource.setGroup(performance.getGroup());
            performanceGetResources.add(performanceGetResource);
        }

        return new ResponseEntity<>(performanceGetResources,HttpStatus.OK);
    }

    @DeleteMapping("/{performanceId}")
    public ResponseEntity<Performance> deletePerformance(@PathVariable("performanceId") Integer performanceId){


        agendaService.removePerformanceFromEveryAgenda(performanceService.getPerformance(performanceId));
        performanceService.deletePerformance(performanceId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/performance/{id}")
    public ResponseEntity<PerformanceResource> updatePerformance(@PathVariable("id") int id, @RequestBody PerformanceResource performanceResource){

        Performance performance= performanceService.getPerformance(id);


        agendaService.removePerformanceFromEveryAgenda(performance);
        Group group = groupService.getGroup(performanceResource.getGroup());
        performance.setGroup(group);
        performance.setBeschrijving(performanceResource.getBeschrijving());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setStartDateTime(performanceResource.getStartdatetime());

        agendaService.addPerformanceToEveryAgenda(performance);

        //Lesson toevoegen
        performanceService.updatePerformance(performance);

        return new ResponseEntity<>(performanceResource,HttpStatus.OK);
    }



    @PostMapping("/present/{performanceid}")
    public ResponseEntity registerUserPresent(@PathVariable("performanceid")  int performanceId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            performanceService.setUserPresent(performanceId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/absent/{performanceid}")
    public ResponseEntity registerUserAbsent(@PathVariable("performanceid")  int performanceId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            performanceService.setUserAbsent(performanceId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/attendancestatus/{performanceid}")
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
