package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentCategoryService;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.web.resources.InstrumentCategoryResource;
import be.kdg.ip.web.resources.InstrumentCatergoryGetResource;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/instrumentsoorten")
public class InstrumentCategoryController {

    private InstrumentCategoryService instrumentCategoryService;

    private InstrumentService instrumentService;

    public InstrumentCategoryController(InstrumentCategoryService instrumentCategoryService, InstrumentService instrumentService){

        this.instrumentCategoryService = instrumentCategoryService;
        this.instrumentService = instrumentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    public ResponseEntity<InstrumentCatergoryGetResource> createInstrumentCategory(@Valid @RequestBody InstrumentCategoryResource instrumentCategoryResource) {

        InstrumentCategory instrumentCategory = new InstrumentCategory();
        instrumentCategory.setCategoryName(instrumentCategoryResource.getCategoryname());

        for (Integer i : instrumentCategoryResource.getInstrumentIds()){
            Instrument instrument = instrumentService.getInstrument(i);
            instrumentCategory.getInstrumentList().add(instrument);
        }

        InstrumentCategory out = instrumentCategoryService.addInstrumentCategory(instrumentCategory);


        InstrumentCatergoryGetResource instrumentCatergoryGetResource = new InstrumentCatergoryGetResource();

        instrumentCatergoryGetResource.setCategoryname(out.getCategoryName());
        instrumentCatergoryGetResource.getInstruments().addAll(out.getInstrumentList());


        return  new ResponseEntity<>(instrumentCatergoryGetResource, HttpStatus.OK);
    }

    //1 InstrumentSoort opvragen
    @GetMapping("/{instrumentSoortId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentCatergoryGetResource> findInstrumentCategoryById(@PathVariable int instrumentSoortId){
        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentSoortId);

        InstrumentCatergoryGetResource instrumentCatergoryGetResource = new InstrumentCatergoryGetResource();

        instrumentCatergoryGetResource.setCategoryname(instrumentCategory.getCategoryName());
        instrumentCatergoryGetResource.getInstruments().addAll(instrumentCategory.getInstrumentList());
        return  new ResponseEntity<>(instrumentCatergoryGetResource,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<InstrumentCatergoryGetResource>> findAll(){
        List<InstrumentCategory> allInstrumentCategories = instrumentCategoryService.getAllInstrumentCategories();
        List<InstrumentCatergoryGetResource> instrumentCatergoryGetResources = new ArrayList<>();
        for (InstrumentCategory instrumentCategory : allInstrumentCategories){
            InstrumentCatergoryGetResource instrumentCatergoryGetResource = new InstrumentCatergoryGetResource();
            instrumentCatergoryGetResource.setCategoryid(instrumentCategory.getInstrumentCategoryId());
            instrumentCatergoryGetResource.setCategoryname(instrumentCategory.getCategoryName());
            instrumentCatergoryGetResource.getInstruments().addAll(instrumentCategory.getInstrumentList());
            instrumentCatergoryGetResources.add(instrumentCatergoryGetResource);
        }
        return new ResponseEntity<>(instrumentCatergoryGetResources, HttpStatus.OK);
    }

    //Een instrumentSoort verwijderen
    @PostMapping("/{instrumentSoortId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    public ResponseEntity<InstrumentCategory> deleteInstrumentCaterogyById(@PathVariable("instrumentSoortId") Integer instrumentSoortId){

        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentSoortId);


        for (Instrument i : instrumentCategory.getInstrumentList()){
            i.setInstrumentCategory(null);
            instrumentService.updateInstrument(i);
        }

        instrumentCategoryService.removeInstrumentCategory(instrumentSoortId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Een instrumentCategory updaten
    @RequestMapping(value = "/instrumentsoort/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    public ResponseEntity<InstrumentCatergoryGetResource> updateInstrumentCategory(@PathVariable("id") int id, @RequestBody InstrumentCategoryResource instrumentCategoryResource) {

        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(id);
        instrumentCategory.setCategoryName(instrumentCategoryResource.getCategoryname());
        instrumentCategory.getInstrumentList().removeAll(instrumentCategory.getInstrumentList());
        for (Integer i : instrumentCategoryResource.getInstrumentIds()){
            Instrument instrument = instrumentService.getInstrument(i);
            instrumentCategory.getInstrumentList().add(instrument);
        }
        InstrumentCategory out =  instrumentCategoryService.updateInstrumentCategory(instrumentCategory);

        InstrumentCatergoryGetResource instrumentCatergoryGetResource = new InstrumentCatergoryGetResource();

        instrumentCatergoryGetResource.setCategoryname(out.getCategoryName());
        instrumentCatergoryGetResource.getInstruments().addAll(out.getInstrumentList());


        return  new ResponseEntity<>(instrumentCatergoryGetResource, HttpStatus.OK);
    }

}
