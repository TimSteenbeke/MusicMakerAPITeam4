package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.InstrumentSoortService;
import be.kdg.ip.web.assemblers.InstrumentAssembler;
import be.kdg.ip.web.assemblers.InstrumentUpdateAssembler;
import be.kdg.ip.web.resources.InstrumentResource;
import be.kdg.ip.web.resources.InstrumentUpdateResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
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
    private InstrumentSoortService instrumentSoortService;

    public InstrumentController(InstrumentService instrumentService, MapperFacade mapperFacade,InstrumentAssembler instrumentAssembler, InstrumentUpdateAssembler instrumentUpdateAssembler, InstrumentSoortService instrumentSoortService){
        this.instrumentService = instrumentService;
        this.mapperFacade = mapperFacade;
        this.instrumentAssembler = instrumentAssembler;
        this.instrumentSoortService = instrumentSoortService;
        this.instrumentUpdateAssembler = instrumentUpdateAssembler;
    }

    //Nog beter bekijken
    //Aanmaken van een instrument
    @PostMapping
    public ResponseEntity<InstrumentResource> createInstrument(@Valid @RequestBody InstrumentResource instrumentResource) {
        Instrument in = new Instrument();
        in.setSoort(instrumentSoortService.getInstrumentSoort(instrumentResource.getInstrumentsoortid()));
        in.setNaam(instrumentResource.getNaam());
        in.setType(instrumentResource.getType());
        in.setUitvoering(instrumentResource.getUitvoering());

        //image omzetten
        String imageString = instrumentResource.getAfbeelding();

        try {
           // byte[] name = Base64.getEncoder().encode("hello world".getBytes());
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            in.setAfbeelding(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Instrument out = instrumentService.addInstrument(in);

        return  new ResponseEntity<>(instrumentAssembler.toResource(out), HttpStatus.OK);
    }

    //1 Instrument opvragen
    @GetMapping("/{instrumentId}")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Instrument> findInstrumentById(@PathVariable int instrumentId){
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        //InstrumentResource instrumentResource = instrumentAssembler.toResource(instrument);
        return  new ResponseEntity<Instrument>(instrument,HttpStatus.OK);
    }

    //Alle instrumenten opvragen
    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Instrument>> findAll(){
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return new ResponseEntity<>(instruments,HttpStatus.OK);
    }

    //Een instrument verwijderen
<<<<<<< HEAD
    @PostMapping("/{instrumentId}")
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
=======
    @DeleteMapping("/{instrumentId}")
>>>>>>> master
    public ResponseEntity<Instrument> deleteInstrumentById(@PathVariable("instrumentId") Integer instrumentId){
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        instrumentService.removeInstrument(instrument.getInstrumentId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Een instrument updaten
    @RequestMapping(value = "/instrument/{id}", method = RequestMethod.PUT)
<<<<<<< HEAD
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Instrument> updateUser(@PathVariable("id") int id, @RequestBody InstrumentUpdateResource instrumentUpdateResource) {
=======
    public ResponseEntity<InstrumentUpdateResource> updateUser(@PathVariable("id") int id, @RequestBody InstrumentUpdateResource instrumentUpdateResource) {
>>>>>>> master


        //Instrument in = mapperFacade.map(instrumentUpdateResource,Instrument.class);
        Instrument in = new Instrument();
        in.setSoort(instrumentSoortService.getInstrumentSoort(instrumentUpdateResource.getInstrumentsoortid()));
        in.setInstrumentId(id);
        in.setNaam(instrumentUpdateResource.getNaam());
        in.setUitvoering(instrumentUpdateResource.getUitvoering());
        in.setType(instrumentUpdateResource.getType());

        //image omzetten
        String imageString = instrumentUpdateResource.getAfbeelding();

        try {
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            in.setAfbeelding(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Instrument out = instrumentService.updateInstrument(in);


        return  new ResponseEntity<>(instrumentUpdateAssembler.toResource(out), HttpStatus.OK);
    }
}
