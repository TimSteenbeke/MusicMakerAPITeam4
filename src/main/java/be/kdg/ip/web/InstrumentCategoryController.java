package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/instrumentsoorten")
public class InstrumentCategoryController {

    private InstrumentCategoryService instrumentCategoryService;

    public InstrumentCategoryController(InstrumentCategoryService instrumentCategoryService){

        this.instrumentCategoryService = instrumentCategoryService;
    }

    //Aanmaken van een instrument
    @PostMapping
    //ToDo: Authorization fix: instrumentsoort create
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> createInstrument(@Valid @RequestBody InstrumentCategory instrumentCategory) {

        InstrumentCategory out = instrumentCategoryService.addInstrumentCategory(instrumentCategory);


        return  new ResponseEntity<>(out, HttpStatus.OK);
    }

    //1 InstrumentSoort opvragen
    @GetMapping("/{instrumentSoortId}")
    //ToDo: Authorization fix: instrumentsoort get
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> findInstrumentById(@PathVariable int instrumentSoortId){
        InstrumentCategory instrumentSoort = instrumentCategoryService.getInstrumentCategory(instrumentSoortId);
        return  new ResponseEntity<InstrumentCategory>(instrumentSoort,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: instrumentsoort get all
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<InstrumentCategory>> findAll(){
        List<InstrumentCategory> instrumentSoortList = instrumentCategoryService.getAllInstrumentCategories();
        return new ResponseEntity<>(instrumentSoortList, HttpStatus.OK);
    }

    //Een instrumentSoort verwijderen
    @PostMapping("/{instrumentSoortId}")
    //ToDo: Authorization fix: instrumentsoort delete
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> deleteInstrumentById(@PathVariable("instrumentSoortId") Integer instrumentSoortId){

        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentSoortId);

        for (Instrument i : instrumentCategory.getInstrumentList()){
            i.setType(null);
        }

        instrumentCategoryService.removeInstrumentCategory(instrumentSoortId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Een instrumentCategory updaten
    @RequestMapping(value = "/instrumentsoort/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrumentsoort update
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> updateUser(@PathVariable("id") int id, @RequestBody InstrumentCategory instrumentCategory) {

        instrumentCategory.setInstrumentCategoryId(id);
        instrumentCategoryService.updateInstrumentCategory(instrumentCategory);
        return  new ResponseEntity<>(instrumentCategory, HttpStatus.OK);
    }
}
