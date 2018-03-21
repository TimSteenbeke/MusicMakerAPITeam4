package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentLevel;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.InstrumentLevelService;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.InstrumentLevelIncDecResource;
import be.kdg.ip.web.resources.InstrumentLevelResource;
import be.kdg.ip.web.resources.InstrumentLevelUserInstrumentResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/instrumentlevels")
public class InstrumentLevelController {

    private InstrumentService instrumentService;
    private InstrumentLevelService instrumentLevelService;
    private UserService userService;

    public InstrumentLevelController(InstrumentService instrumentService, InstrumentLevelService instrumentLevelService, UserService userService) {
        this.instrumentService = instrumentService;
        this.instrumentLevelService = instrumentLevelService;
        this.userService = userService;
    }

    @PostMapping
    //ToDo: Authorization fix: instrument post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentLevelResource> createInstrumentLevel(@Valid @RequestBody InstrumentLevelResource instrumentLevelResource) {

        InstrumentLevel instrumentLevel = new InstrumentLevel();
        instrumentLevel.setMaxLevel(instrumentLevelResource.getMaxlevel());
        instrumentLevel.setLevel(instrumentLevelResource.getLevel());

        Instrument instrument = instrumentService.getInstrument(instrumentLevelResource.getInstrumentid());
        instrumentLevel.setInstrument(instrument);

        User user = userService.findUser(instrumentLevelResource.getUserid());
        instrumentLevel.setUser(user);

        InstrumentLevel out =  instrumentLevelService.addInstrumentLevel(instrumentLevel);


        List<InstrumentLevel> instrumentLevels = user.getInstrumentLevels();
        instrumentLevels.add(instrumentLevel);

        user.setInstrumentLevels(instrumentLevels);

        userService.updateUser(user);

        InstrumentLevelUserInstrumentResource resource = new InstrumentLevelUserInstrumentResource();
        resource.setMaxLevel(out.getMaxLevel());
        resource.setLevel(out.getLevel());
        resource.setUser(out.getUser());
        resource.setInstrument(out.getInstrument());


        return new ResponseEntity<>(instrumentLevelResource,HttpStatus.OK);
    }

    @GetMapping("/{instrumentLevelId}")
    public ResponseEntity<InstrumentLevelUserInstrumentResource> getInstrumentLevel(@PathVariable int instrumentLevelId){

        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(instrumentLevelId);

        InstrumentLevelUserInstrumentResource resource = new InstrumentLevelUserInstrumentResource();
        resource.setMaxLevel(instrumentLevel.getMaxLevel());
        resource.setLevel(instrumentLevel.getLevel());
        resource.setUser(instrumentLevel.getUser());
        resource.setInstrument(instrumentLevel.getInstrument());

        return new ResponseEntity<>(resource,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<InstrumentLevelUserInstrumentResource>> getAllInstrumentLevels(){
        List<InstrumentLevel> instrumentLevels = instrumentLevelService.getAllInstrumentLevels();

        List<InstrumentLevelUserInstrumentResource> resources = new ArrayList<>();
        for (InstrumentLevel i : instrumentLevels){
            InstrumentLevelUserInstrumentResource resource = new InstrumentLevelUserInstrumentResource();
            resource.setMaxLevel(i.getMaxLevel());
            resource.setLevel(i.getLevel());
            resource.setUser(i.getUser());
            resource.setInstrument(i.getInstrument());
            resources.add(resource);
        }

        return new ResponseEntity<>(resources,HttpStatus.OK);
    }

    //fix delete in user!!!!!!
    @DeleteMapping("/{instrumentLevelId}")
    public ResponseEntity<InstrumentLevel> deleteInstrumentLevel(@PathVariable("instrumentLevelId") Integer instrumentLevelId){
        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(instrumentLevelId);

        User user = userService.findUser(instrumentLevel.getUser().getId());

        user.getInstrumentLevels().remove(instrumentLevel);

        userService.updateUser(user);

        instrumentLevelService.removeInstrumentLevel(instrumentLevelId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/instrumentlevel/{instrumentLevelId}", method = RequestMethod.PUT)
    public ResponseEntity<InstrumentLevelUserInstrumentResource> updateInstrumentLevel(@PathVariable("instrumentLevelId") int instrumentLevelId, @RequestBody InstrumentLevelResource instrumentLevelResource){
        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(instrumentLevelId);
        instrumentLevel.setMaxLevel(instrumentLevelResource.getMaxlevel());
        instrumentLevel.setLevel(instrumentLevelResource.getLevel());
        InstrumentLevel out =  instrumentLevelService.updateInstrumentLevel(instrumentLevel);

        User user = userService.findUser(instrumentLevel.getUser().getId());
        List<InstrumentLevel> instrumentLevels = user.getInstrumentLevels();
        instrumentLevels.remove(instrumentLevelService.getIntrumentLevel(instrumentLevelId));
        instrumentLevels.add(instrumentLevel);
        user.setInstrumentLevels(instrumentLevels);
        userService.updateUser(user);

        InstrumentLevelUserInstrumentResource resource = new InstrumentLevelUserInstrumentResource();
        resource.setMaxLevel(out.getMaxLevel());
        resource.setLevel(out.getLevel());
        resource.setUser(out.getUser());
        resource.setInstrument(out.getInstrument());

        return new ResponseEntity<>(resource,HttpStatus.OK);
    }


    @PostMapping("/instrumentleveldown/{id}")
    public ResponseEntity<InstrumentLevelUserInstrumentResource> decreaseLevel(@PathVariable("id") int id){

        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(id);
        int newLevel = instrumentLevel.getLevel()-1;
        if (newLevel>=0){
            instrumentLevel.setLevel(newLevel);
            instrumentLevel = instrumentLevelService.updateInstrumentLevel(instrumentLevel);
        }
        InstrumentLevelUserInstrumentResource instrumentLevelUserInstrumentResource = new InstrumentLevelUserInstrumentResource();
        instrumentLevelUserInstrumentResource.setMaxLevel(instrumentLevel.getMaxLevel());
        instrumentLevelUserInstrumentResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelUserInstrumentResource.setInstrument(instrumentLevel.getInstrument());
        instrumentLevelUserInstrumentResource.setUser(instrumentLevel.getUser());
        return new ResponseEntity<>(instrumentLevelUserInstrumentResource,HttpStatus.OK);
    }

    @PostMapping("/instrumentlevelup/{id}")
    public ResponseEntity<InstrumentLevelUserInstrumentResource> increaseLevel(@PathVariable("id") int id){

        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(id);
        int newLevel = instrumentLevel.getLevel()+1;
        if (newLevel<=10){
            instrumentLevel.setLevel(newLevel);
            instrumentLevel = instrumentLevelService.updateInstrumentLevel(instrumentLevel);
        }
        InstrumentLevelUserInstrumentResource instrumentLevelUserInstrumentResource = new InstrumentLevelUserInstrumentResource();
        instrumentLevelUserInstrumentResource.setMaxLevel(instrumentLevel.getMaxLevel());
        instrumentLevelUserInstrumentResource.setLevel(instrumentLevel.getLevel());
        instrumentLevelUserInstrumentResource.setInstrument(instrumentLevel.getInstrument());
        instrumentLevelUserInstrumentResource.setUser(instrumentLevel.getUser());
        return new ResponseEntity<>(instrumentLevelUserInstrumentResource,HttpStatus.OK);
    }
}
