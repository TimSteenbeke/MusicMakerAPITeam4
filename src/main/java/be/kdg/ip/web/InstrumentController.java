package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.services.api.InstrumentService;
import be.kdg.ip.web.assemblers.InstrumentAssembler;
import be.kdg.ip.web.resources.InstrumentResource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {
    private InstrumentService instrumentService;
    private final MapperFacade mapperFacade;
    private final InstrumentAssembler instrumentAssembler;

    public InstrumentController(InstrumentService instrumentService, MapperFacade mapperFacade,InstrumentAssembler instrumentAssembler){
        this.instrumentService = instrumentService;
        this.mapperFacade = mapperFacade;
        this.instrumentAssembler = instrumentAssembler;
    }

    //Nog beter bekijken
    //Aanmaken van een instrument
    @PostMapping
    public ResponseEntity<InstrumentResource> createInstrument(@Valid @RequestBody InstrumentResource instrumentResource) {
        Instrument in = mapperFacade.map(instrumentResource,Instrument.class);
        Instrument out = instrumentService.addInstrument(in);

        return  new ResponseEntity<>(instrumentAssembler.toResource(out), HttpStatus.OK);
    }

    //1 Instrument opvragen
    @GetMapping("/{instrumentId}")
    public ResponseEntity<InstrumentResource> findInstrumentById(@PathVariable int instrumentId){
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        InstrumentResource instrumentResource = instrumentAssembler.toResource(instrument);
        return  new ResponseEntity<>(instrumentResource,HttpStatus.OK);
    }

    //Alle instrumenten opvragen
    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<InstrumentResource>> findAll(){
        List<Instrument> instruments = instrumentService.getAllInstruments();
        return new ResponseEntity<>(instrumentAssembler.toResources(instruments),HttpStatus.OK);
    }

    //Een instrument verwijderen
    @PostMapping("/{instrumentId}")
    public ResponseEntity<InstrumentResource> deleteInstrumentById(@PathVariable("instrumentId") Integer instrumentId){
        Instrument instrument = instrumentService.getInstrument(instrumentId);
        instrumentService.removeInstrument(instrument.getInstrumentId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
