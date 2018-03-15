package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentLevel;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.InstrumentLevelService;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.InstrumentLevelResource;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<InstrumentLevel> createInstrumentLevel(@Valid @RequestBody InstrumentLevelResource instrumentLevelResource) {

        InstrumentLevel instrumentLevel = new InstrumentLevel();
        instrumentLevel.setMaxLevel(instrumentLevelResource.getMaxlevel());
        instrumentLevel.setLevel(instrumentLevelResource.getLevel());
        Instrument instrument = instrumentService.getInstrument(instrumentLevelResource.getInstrumentid());
        instrumentLevel.setInstrument(instrument);
        instrumentLevelService.addInstrumentLevel(instrumentLevel);

        User user = userService.findUser(instrumentLevelResource.getUserid());
        List<InstrumentLevel> instrumentLevels = user.getInstrumentLevels();
        instrumentLevels.add(instrumentLevel);

        userService.updateUser(user);

        return new ResponseEntity<>(instrumentLevel,HttpStatus.OK);
    }

    @GetMapping("/{instrumentLevelId}")
    public ResponseEntity<InstrumentLevel> getInstrumentLevel(@PathVariable int instrumentLevelId){

        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(instrumentLevelId);
        return new ResponseEntity<>(instrumentLevel,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<InstrumentLevel>> getAllInstrumentLevels(){
        List<InstrumentLevel> instrumentLevels = instrumentLevelService.getAllInstrumentLevels();

        return new ResponseEntity<>(instrumentLevels,HttpStatus.OK);
    }

    //fix delete in user!!!!!!
    @DeleteMapping("/{instrumentLevelId}")
    public ResponseEntity<InstrumentLevel> deleteInstrumentLevel(@PathVariable("instrumentLevelId") Integer instrumentLevelId){
        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(instrumentLevelId);
        instrumentLevelService.removeInstrumentLevel(instrumentLevelId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/instrumentlevel/{id}", method = RequestMethod.PUT)
    public ResponseEntity<InstrumentLevel> updateInstrumentLevel(@PathVariable("id") int id, @RequestBody InstrumentLevelResource instrumentLevelResource){
        InstrumentLevel instrumentLevel = instrumentLevelService.getIntrumentLevel(id);
        instrumentLevel.setMaxLevel(instrumentLevelResource.getMaxlevel());
        instrumentLevel.setLevel(instrumentLevelResource.getLevel());
        Instrument instrument = instrumentService.getInstrument(instrumentLevelResource.getInstrumentid());
        instrumentLevel.setInstrument(instrument);
        instrumentLevelService.updateInstrumentLevel(instrumentLevel);

        User user = userService.findUser(instrumentLevelResource.getUserid());
        List<InstrumentLevel> instrumentLevels = user.getInstrumentLevels();
        instrumentLevels.remove(instrumentLevelService.getIntrumentLevel(id));
        instrumentLevels.add(instrumentLevel);
        /*if(!instrumentLevels.contains(instrumentLevel)){
            InstrumentLevel remove = null;
            for(InstrumentLevel i : instrumentLevels){
                if(i.getInstrumentLevelId()==instrumentLevel.getInstrumentLevelId()){
                    remove = i;
                }
            }
            //remove object
            if (remove != null){
                instrumentLevels.remove(remove);
            }
            instrumentLevels.add(instrumentLevel);
        }*/

        user.setInstrumentLevels(instrumentLevels);
        userService.updateUser(user);
        return new ResponseEntity<>(instrumentLevel,HttpStatus.OK);
    }
}
