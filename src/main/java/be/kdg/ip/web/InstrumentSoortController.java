package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentSoortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/instrumentsoorten")
public class InstrumentSoortController {

    private InstrumentSoortService instrumentSoortService;

    public InstrumentSoortController(InstrumentSoortService instrumentSoortService){

        this.instrumentSoortService = instrumentSoortService;
    }

    //Aanmaken van een instrument
    @PostMapping
    //ToDo: Authorization fix: instrumentsoort create
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> createInstrument(@Valid @RequestBody InstrumentCategory instrumentCategory) {

        InstrumentCategory out = instrumentSoortService.addInstrumentSoort(instrumentCategory);


        return  new ResponseEntity<>(out, HttpStatus.OK);
    }

    //1 InstrumentCategory opvragen
    @GetMapping("/{instrumentSoortId}")
    //ToDo: Authorization fix: instrumentsoort get
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> findInstrumentById(@PathVariable int instrumentSoortId){
        InstrumentCategory instrumentCategory = instrumentSoortService.getInstrumentSoort(instrumentSoortId);
        return  new ResponseEntity<InstrumentCategory>(instrumentCategory,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: instrumentsoort get all
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<InstrumentCategory>> findAll(){
        List<InstrumentCategory> instrumentCategoryList = instrumentSoortService.getAllInstrumentSoorten();
        return new ResponseEntity<>(instrumentCategoryList, HttpStatus.OK);
    }

    //Een instrumentSoort verwijderen
    @PostMapping("/{instrumentSoortId}")
    //ToDo: Authorization fix: instrumentsoort delete
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> deleteInstrumentById(@PathVariable("instrumentSoortId") Integer instrumentSoortId){

        InstrumentCategory instrumentCategory = instrumentSoortService.getInstrumentSoort(instrumentSoortId);

        for (Instrument i : instrumentCategory.getInstrumentList()){
            i.setInstrumentCategory(null);
        }

        instrumentSoortService.removeInstrumentSoort(instrumentSoortId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Een instrumentCategory updaten
    @RequestMapping(value = "/instrumentsoort/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrumentsoort update
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> updateUser(@PathVariable("id") int id, @RequestBody InstrumentCategory instrumentCategory) {

        instrumentCategory.setInstrumentCategoryId(id);
        instrumentSoortService.updateInstrumentSoort(instrumentCategory);
        return  new ResponseEntity<>(instrumentCategory, HttpStatus.OK);
    }
}
