package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentCategory;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.InstrumentCategoryService;
import be.kdg.ip.web.assemblers.InstrumentAssembler;
import be.kdg.ip.web.assemblers.InstrumentUpdateAssembler;
import be.kdg.ip.web.resources.InstrumentGetResource;
import be.kdg.ip.web.resources.InstrumentResource;
import be.kdg.ip.web.resources.InstrumentUpdateResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/instruments")
public class InstrumentController {
    private InstrumentService instrumentService;
    private final MapperFacade mapperFacade;
    private final InstrumentAssembler instrumentAssembler;
    private final InstrumentUpdateAssembler instrumentUpdateAssembler;
    private InstrumentCategoryService instrumentCategoryService;

    public InstrumentController(InstrumentService instrumentService, MapperFacade mapperFacade, InstrumentAssembler instrumentAssembler, InstrumentUpdateAssembler instrumentUpdateAssembler, InstrumentCategoryService instrumentCategoryService) {
        this.instrumentService = instrumentService;
        this.mapperFacade = mapperFacade;
        this.instrumentAssembler = instrumentAssembler;
        this.instrumentCategoryService = instrumentCategoryService;
        this.instrumentUpdateAssembler = instrumentUpdateAssembler;
    }

    //Creation of an instrument
    @PostMapping
    //ToDo: Authorization fix: instrument post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentGetResource> createInstrument(@Valid @RequestBody InstrumentResource instrumentResource) {
        Instrument in = new Instrument();
        in.setInstrumentCategory(instrumentCategoryService.getInstrumentCategory(instrumentResource.getInstrumentid()));
        in.setInstrumentName(instrumentResource.getInstrumentname());
        in.setType(instrumentResource.getType());
        in.setDetails(instrumentResource.getDetails());
        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentResource.getInstrumentCategoryid());
        in.setInstrumentCategory(instrumentCategory);

        //converting image
        String imageString = instrumentResource.getImage();

        try {
            // byte[] name = Base64.getEncoder().encode("hello world".getBytes());
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            in.setImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Instrument out = instrumentService.addInstrument(in);

        InstrumentGetResource instrumentGetResource = new InstrumentGetResource();
        instrumentGetResource.setType(out.getType());
        instrumentGetResource.setDetails(out.getDetails());
        instrumentGetResource.setImage(new sun.misc.BASE64Encoder().encode(out.getImage()));
        instrumentGetResource.setInstrumentname(out.getInstrumentName());
        instrumentGetResource.setInstrumentCategory(out.getInstrumentCategory());

        return new ResponseEntity<>(instrumentGetResource, HttpStatus.OK);
    }

    //Request 1 instrument
    @GetMapping("/{instrumentId}")
    //ToDo: Authorization fix: get instrument
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentGetResource> findInstrumentById(@PathVariable int instrumentId) {
        Instrument instrument = instrumentService.getInstrument(instrumentId);

        InstrumentGetResource instrumentGetResource = new InstrumentGetResource();
        instrumentGetResource.setType(instrument.getType());
        instrumentGetResource.setDetails(instrument.getDetails());
        instrumentGetResource.setImage(new sun.misc.BASE64Encoder().encode(instrument.getImage()));
        instrumentGetResource.setInstrumentname(instrument.getInstrumentName());
        instrumentGetResource.setInstrumentCategory(instrument.getInstrumentCategory());
        return new ResponseEntity<>(instrumentGetResource, HttpStatus.OK);
    }

    //Request all instruments
    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: get all instrument
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<InstrumentGetResource>> findAll() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        List<InstrumentGetResource> instrumentGetResources = new ArrayList<>();
        for (Instrument i : instruments){
            InstrumentGetResource instrumentGetResource = new InstrumentGetResource();
            instrumentGetResource.setType(i.getType());
            instrumentGetResource.setDetails(i.getDetails());
            instrumentGetResource.setImage(new sun.misc.BASE64Encoder().encode(i.getImage()));
            instrumentGetResource.setInstrumentname(i.getInstrumentName());
            instrumentGetResource.setInstrumentCategory(i.getInstrumentCategory());
            instrumentGetResources.add(instrumentGetResource);
        }
        return new ResponseEntity<>(instrumentGetResources, HttpStatus.OK);
    }

    //Delete an instrument
    @DeleteMapping("/{instrumentId}")
    //ToDo: Authorization fix: delete instrument
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Instrument> deleteInstrumentById(@PathVariable("instrumentId") Integer instrumentId) {
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        instrumentService.removeInstrument(instrument.getInstrumentId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Update an instrument
    @RequestMapping(value = "/instrument/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrument updaten
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<InstrumentGetResource> updateInstrument(@PathVariable("id") int id, @RequestBody InstrumentUpdateResource instrumentUpdateResource) {
        Instrument in = instrumentService.getInstrument(id);
        in.setInstrumentName(instrumentUpdateResource.getInstrumentname());
        in.setDetails(instrumentUpdateResource.getDetails());
        in.setType(instrumentUpdateResource.getType());

        InstrumentCategory instrumentCategory = instrumentCategoryService.getInstrumentCategory(instrumentUpdateResource.getInstrumentCategoryid());
        in.setInstrumentCategory(instrumentCategory);

        //convert image
        String imageString = instrumentUpdateResource.getImage();

        try {
            imageString = imageString.replaceAll("(\\r|\\n)", "");
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            in.setImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Instrument out = instrumentService.updateInstrument(in);


        InstrumentGetResource instrumentGetResource = new InstrumentGetResource();
        instrumentGetResource.setType(out.getType());
        instrumentGetResource.setDetails(out.getDetails());
        instrumentGetResource.setImage(new sun.misc.BASE64Encoder().encode(out.getImage()));
        instrumentGetResource.setInstrumentname(out.getInstrumentName());
        instrumentGetResource.setInstrumentCategory(out.getInstrumentCategory());

        return new ResponseEntity<>(instrumentGetResource, HttpStatus.OK);
    }
}
