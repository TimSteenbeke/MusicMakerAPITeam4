package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/instrumentCategories")
public class InstrumentCategoryController {

    private InstrumentCategoryService instrumentCategoryService;

    public InstrumentCategoryController(InstrumentCategoryService instrumentCategoryService){

        this.instrumentCategoryService = instrumentCategoryService;
    }

    //creation of an instrument
    @PostMapping
    //ToDo: Authorization fix: instrumentsoort create
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> createInstrument(@Valid @RequestBody InstrumentCategory instrumentCategory) {

        InstrumentCategory out = instrumentCategoryService.addInstrumentCategory(instrumentCategory);


        return  new ResponseEntity<>(out, HttpStatus.OK);
    }

    //Request 1 InstrumentCategory
    @GetMapping("/{instrumentCategoryId}")
    //ToDo: Authorization fix: instrumentsoort get
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> findInstrumentById(@PathVariable int instrumentSoortId){
        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentSoortId);
        return  new ResponseEntity<InstrumentCategory>(instrumentCategory,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: instrumentsoort get all
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<InstrumentCategory>> findAll(){
        List<InstrumentCategory> instrumentCategoryList = instrumentCategoryService.getAllInstrumentCategories();
        return new ResponseEntity<>(instrumentCategoryList, HttpStatus.OK);
    }

    //Delete instrument category
    @PostMapping("/{instrumentCategoryId}")
    //ToDo: Authorization fix: instrumentsoort delete
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> deleteInstrumentById(@PathVariable("instrumentSoortId") Integer instrumentSoortId){

        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentSoortId);

        for (Instrument i : instrumentCategory.getInstrumentList()){
            i.setInstrumentCategory(null);
        }

        instrumentCategoryService.removeInstrumentCategory(instrumentSoortId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Een instrumentCategory updaten
    @RequestMapping(value = "/instrumentsoort/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrumentsoort update
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCategory> updateUser(@PathVariable("id") int id, @RequestBody InstrumentCategory instrumentCategory) {

        instrumentCategory.setInstrumentCategoryId(id);
        instrumentCategoryService.updateInstrumentCategory(instrumentCategory);
        return  new ResponseEntity<>(instrumentCategory, HttpStatus.OK);
    }
}
