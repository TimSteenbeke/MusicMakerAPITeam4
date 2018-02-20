package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.services.api.InstrumentSoortService;
import be.kdg.ip.web.assemblers.InstrumentAssembler;
import be.kdg.ip.web.resources.InstrumentResource;
import be.kdg.ip.web.resources.InstrumentUpdateResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/instruments")
public class InstrumentController {
    private InstrumentService instrumentService;
    private final MapperFacade mapperFacade;
    private final InstrumentAssembler instrumentAssembler;
    private InstrumentSoortService instrumentSoortService;

    public InstrumentController(InstrumentService instrumentService, MapperFacade mapperFacade,InstrumentAssembler instrumentAssembler, InstrumentSoortService instrumentSoortService){
        this.instrumentService = instrumentService;
        this.mapperFacade = mapperFacade;
        this.instrumentAssembler = instrumentAssembler;
        this.instrumentSoortService = instrumentSoortService;
    }

    //Nog beter bekijken
    //Aanmaken van een instrument
    @PostMapping
    public ResponseEntity<InstrumentResource> createInstrument(@Valid @RequestBody InstrumentResource instrumentResource) {
        InstrumentResource binnenkomendeData = instrumentResource;
        Instrument in = mapperFacade.map(instrumentResource,Instrument.class);
        in.setSoort(instrumentSoortService.getInstrumentSoort(instrumentResource.getInstrumentsoortid()));
        Instrument out = instrumentService.addInstrument(in);


        return  new ResponseEntity<>(instrumentAssembler.toResource(out), HttpStatus.OK);
    }

    //1 Instrument opvragen
    @GetMapping("/{instrumentId}")
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
    @PostMapping("/{instrumentId}")
    public ResponseEntity<Instrument> deleteInstrumentById(@PathVariable("instrumentId") Integer instrumentId){
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        instrumentService.removeInstrument(instrument.getInstrumentId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Een instrument updaten
    @RequestMapping(value = "/instrument/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Instrument> updateUser(@PathVariable("id") int id, @RequestBody InstrumentUpdateResource instrumentUpdateResource) {


        Instrument opgehaaldInstrument = instrumentService.getInstrument(id);
        Instrument in = mapperFacade.map(instrumentUpdateResource,Instrument.class);
        in.setSoort(instrumentSoortService.getInstrumentSoort(instrumentUpdateResource.getInstrumentsoortid()));
        opgehaaldInstrument = in;
        opgehaaldInstrument.setInstrumentId(id);
        Instrument out = instrumentService.updateInstrument(opgehaaldInstrument);


        return  new ResponseEntity<>(out, HttpStatus.OK);
    }
}
