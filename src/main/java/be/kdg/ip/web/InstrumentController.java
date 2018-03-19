package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.InstrumentCategoryService;
import be.kdg.ip.web.assemblers.InstrumentAssembler;
import be.kdg.ip.web.assemblers.InstrumentUpdateAssembler;
import be.kdg.ip.web.resources.InstrumentResource;
import be.kdg.ip.web.resources.InstrumentUpdateResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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
    public ResponseEntity<InstrumentResource> createInstrument(@Valid @RequestBody InstrumentResource instrumentResource) {
        Instrument in = new Instrument();
        in.setInstrumentCategory(instrumentCategoryService.getInstrumentCategory(instrumentResource.getInstrumentid()));
        in.setInstrumentName(instrumentResource.getInstrumentname());
        in.setType(instrumentResource.getType());
        in.setDetails(instrumentResource.getDetails());

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

        return new ResponseEntity<>(instrumentAssembler.toResource(out), HttpStatus.OK);
    }

    //Request 1 instrument
    @GetMapping("/{instrumentId}")
    //ToDo: Authorization fix: get instrument
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Instrument> findInstrumentById(@PathVariable int instrumentId) {
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        //InstrumentResource instrumentResource = instrumentAssembler.toResource(instrument);
        return new ResponseEntity<Instrument>(instrument, HttpStatus.OK);
    }

    //Request all instruments
    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: get all instrument
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<Instrument>> findAll() {
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return new ResponseEntity<>(instruments, HttpStatus.OK);
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
    public ResponseEntity<InstrumentUpdateResource> updateInstrument(@PathVariable("id") int id, @RequestBody InstrumentUpdateResource instrumentUpdateResource) {
        //Instrument in = mapperFacade.map(instrumentUpdateResource,Instrument.class);
        Instrument in = new Instrument();
        in.setInstrumentCategory(instrumentCategoryService.getInstrumentCategory(instrumentUpdateResource.getInstrumentcategoryid()));
        in.setInstrumentId(id);
        in.setInstrumentName(instrumentUpdateResource.getName());
        in.setDetails(instrumentUpdateResource.getDetails());
        in.setType(instrumentUpdateResource.getType());

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


        return new ResponseEntity<>(instrumentUpdateAssembler.toResource(out), HttpStatus.OK);
    }
}
