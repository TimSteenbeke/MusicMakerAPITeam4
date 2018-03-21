package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.Performance;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.PerformanceService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.dto.StatusDTO;
import be.kdg.ip.web.resources.PerformanceGetResource;
import be.kdg.ip.web.resources.PerformanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    GroupService groupService;

    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<PerformanceResource> addPerformance(@Valid @RequestBody PerformanceResource performanceResource) {

        //create performance based on performanceresource
        Performance performance = new Performance();
        performance.setStartDateTime(performanceResource.getStartdatetime());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setDescription(performanceResource.getDescription());


        //Fetch group object and link it to a performance
        Group group = groupService.getGroup(performanceResource.getGroup());
        performance.setGroup(group);

        //Add performance
        performanceService.addPerformance(performance);


        return  new ResponseEntity<>(performanceResource, HttpStatus.OK);
    }

    @GetMapping("/{performanceId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<PerformanceGetResource> getPerformance(@PathVariable int performanceId){

        Performance performance = performanceService.getPerformance(performanceId);

        PerformanceGetResource performanceGetResource = new PerformanceGetResource();

       performanceGetResource.setDescription(performance.getDescription());
       performanceGetResource.setEnddatetime(performance.getEndDateTime());
       performanceGetResource.setStartdatetime(performance.getEndDateTime());
       performanceGetResource.setGroup(performance.getGroup());

        return new ResponseEntity<>(performanceGetResource,HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<PerformanceGetResource>> getAllPerformances(){
        List<Performance> performances = performanceService.getAllPerformances();
        List<PerformanceGetResource> performanceGetResources = new ArrayList<>();

        for (Performance performance : performances){
            PerformanceGetResource performanceGetResource = new PerformanceGetResource();
            performanceGetResource.setId(performance.getPerformanceId());
            performanceGetResource.setDescription(performance.getDescription());
            performanceGetResource.setEnddatetime(performance.getEndDateTime());
            performanceGetResource.setStartdatetime(performance.getEndDateTime());
            performanceGetResource.setGroup(performance.getGroup());
            performanceGetResources.add(performanceGetResource);
        }

        return new ResponseEntity<>(performanceGetResources,HttpStatus.OK);
    }

    @DeleteMapping("/{performanceId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Performance> deletePerformance(@PathVariable("performanceId") Integer performanceId){


        performanceService.deletePerformance(performanceId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/performance/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<PerformanceResource> updatePerformance(@PathVariable("id") int id, @RequestBody PerformanceResource performanceResource){

        Performance performance= performanceService.getPerformance(id);


        Group group = groupService.getGroup(performanceResource.getGroup());
        performance.setGroup(group);
        performance.setDescription(performanceResource.getDescription());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setStartDateTime(performanceResource.getStartdatetime());


        //Lesson toevoegen
        performanceService.updatePerformance(performance);

        return new ResponseEntity<>(performanceResource,HttpStatus.OK);
    }

    @PostMapping("/present/{performanceid}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<StatusDTO> getAttendanceStatus(@PathVariable("performanceid")  int performanceId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            Performance performance = performanceService.getPerformance(performanceId);

            if (performance.getAbsentMembers().contains(user)) {
                // ABSENT
                return new ResponseEntity<>(new StatusDTO("absent"),HttpStatus.OK);
            }
            else {
                if (performance.getPresentMembers().contains(user)) {
                    //PRESENT
                    return new ResponseEntity<>(new StatusDTO("present"),HttpStatus.OK);
                } else {
                    // NOT SET
                    return new ResponseEntity<>(new StatusDTO("nostatus"),HttpStatus.OK);
                }
            }
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }







}
