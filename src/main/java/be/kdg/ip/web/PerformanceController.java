package be.kdg.ip.web;

import be.kdg.ip.domain.Performance;
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

    @RequestMapping(method = RequestMethod.POST,value ="/api/performance")
    public ResponseEntity<PerformanceResource> addLesson(@Valid @RequestBody PerformanceResource performanceResource) {

        //performance aanmaken based op perforamnceresource
        Performance performance = new Performance();
        performance.setStartDateTime(performanceResource.getStartdatetime());
        performance.setEndDateTime(performanceResource.getEnddatetime());
        performance.setBeschrijving(performanceResource.getBeschrijving());



        //performance toevoegen
        performanceService.addPerformance(performance);

        //TODO: Via group de users ophalen en voor elke user performance koppelen met hun kalender.



        return  new ResponseEntity<>(performanceResource, HttpStatus.OK);
    }



}
