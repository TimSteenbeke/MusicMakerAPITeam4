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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    GroupService groupService;

    @RequestMapping(method = RequestMethod.POST,value ="/api/performance")
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



}
