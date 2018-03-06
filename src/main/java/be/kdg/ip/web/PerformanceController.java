package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.Performance;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.GroupService;
import be.kdg.ip.services.api.PerformanceService;
import be.kdg.ip.web.resources.PerformanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    GroupService groupService;

    @RequestMapping(method = RequestMethod.POST,value ="/api/performance")
    //ToDo: Authorization fix: performance post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<PerformanceResource> addLesson(@Valid @RequestBody PerformanceResource performanceResource) {

        //create performance based on performanceresource
        Performance performance = new Performance();
        performance.setStartDateTime(performanceResource.getStartdatetime());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setDescription(performanceResource.getDescription());

        //Fetch group object and link it to a performance
        Group group = groupService.getGroup(performanceResource.getGroupid());
        performance.setGroup(group);

        //Add performance
        performanceService.addPerformance(performance);

        //add Performance to every involved agenda
        agendaService.addPerformanceToEveryAgenda(performance);

        return  new ResponseEntity<>(performanceResource, HttpStatus.OK);
    }



}
