package be.kdg.ip.web;

import be.kdg.ip.domain.Instrument;
import be.kdg.ip.domain.InstrumentSoort;
import be.kdg.ip.services.api.InstrumentSoortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/instrumentsoorten")
public class InstrumentSoortController {

    private InstrumentSoortService instrumentSoortService;

    public InstrumentSoortController(InstrumentSoortService instrumentSoortService){

        this.instrumentSoortService = instrumentSoortService;
    }

    //Aanmaken van een instrument
    @PostMapping
    public ResponseEntity<InstrumentSoort> createInstrument(@Valid @RequestBody InstrumentSoort instrumentSoort) {

        InstrumentSoort out = instrumentSoortService.addInstrumentSoort(instrumentSoort);


        return  new ResponseEntity<>(out, HttpStatus.OK);
    }

    //1 InstrumentSoort opvragen
    @GetMapping("/{instrumentSoortId}")
    public ResponseEntity<InstrumentSoort> findInstrumentById(@PathVariable int instrumentSoortId){
        InstrumentSoort instrumentSoort = instrumentSoortService.getInstrumentSoort(instrumentSoortId);
        return  new ResponseEntity<InstrumentSoort>(instrumentSoort,HttpStatus.OK);
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<InstrumentSoort>> findAll(){
        List<InstrumentSoort> instrumentSoortList = instrumentSoortService.getAllInstrumentSoorten();
        return new ResponseEntity<>(instrumentSoortList, HttpStatus.OK);
    }

    //Een instrumentSoort verwijderen
    @PostMapping("/{instrumentSoortId}")
    public ResponseEntity<InstrumentSoort> deleteInstrumentById(@PathVariable("instrumentSoortId") Integer instrumentSoortId){

        InstrumentSoort instrumentSoort = instrumentSoortService.getInstrumentSoort(instrumentSoortId);

        for (Instrument i : instrumentSoort.getInstrumentList()){
            i.setSoort(null);
        }

        instrumentSoortService.removeInstrumentSoort(instrumentSoortId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Een instrumentSoort updaten
    @RequestMapping(value = "/instrumentsoort/{id}", method = RequestMethod.PUT)
    public ResponseEntity<InstrumentSoort> updateUser(@PathVariable("id") int id, @RequestBody InstrumentSoort instrumentSoort) {

        instrumentSoort.setInstrumentSoortId(id);
        instrumentSoortService.updateInstrumentSoort(instrumentSoort);
        return  new ResponseEntity<>(instrumentSoort, HttpStatus.OK);
    }
}
