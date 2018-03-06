package be.kdg.ip.web;

import be.kdg.ip.domain.Composition;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.web.assemblers.CompositionAssembler;
import be.kdg.ip.web.resources.CompositionResource;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/compositions")
public class CompositionController {
    private CompositionService compositionService;
    private CompositionAssembler compositionAssembler;

    public CompositionController(CompositionService compositionService,CompositionAssembler compositionAssembler){
        this.compositionService = compositionService;
        this.compositionAssembler = compositionAssembler;
    }

    //uploading of a composition
    @PostMapping
    public @ResponseBody ResponseEntity<?> upload(@Valid @RequestBody CompositionResource compositionResource) throws Exception
    {
        Composition composition = new Composition();
        composition.setTitle(compositionResource.getTitle());
        composition.setArtist(compositionResource.getArtist());
        composition.setLanguage(compositionResource.getLanguage());
        composition.setGenre(compositionResource.getGenre());
        composition.setSubject(compositionResource.getSubject());
        composition.setLink(compositionResource.getLink());
        composition.setFileFormat(compositionResource.getFileFormat());
        composition.setInstrumentType(compositionResource.getInstrumentcategory());

        try {
            byte[] byteArr = Base64.getDecoder().decode(compositionResource.getContent().getBytes("UTF-8"));
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            byte[] bytes = IOUtils.toByteArray(inputStream);

            composition.setContent(bytes);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Composition out = compositionService.addComposition(composition);

        return new ResponseEntity<>(compositionAssembler.toResource(out), HttpStatus.OK);
    }


    //Request all compositions
    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Composition>> findAll(){
        List<Composition> compositions = compositionService.getAllCompositions();
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    //Request single composition
    @GetMapping("/{compositionId}")
    public ResponseEntity<Composition> findCompositionById(@PathVariable int compositionId){
        Composition composition = compositionService.getComposition(compositionId);
        //InstrumentResource instrumentResource = instrumentAssembler.toResource(instrument);
        return  new ResponseEntity<Composition>(composition,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/composition/{compositionId}")
    public ResponseEntity<CompositionResource> updateComposition(@PathVariable("compositionId") int compositionId,@Valid @RequestBody CompositionResource compositionResource) {
        Composition composition = compositionService.getComposition(compositionId);

        composition.setTitle(compositionResource.getTitle());
        composition.setArtist(compositionResource.getArtist());
        composition.setLanguage(compositionResource.getLanguage());
        composition.setGenre(compositionResource.getGenre());
        composition.setSubject(compositionResource.getSubject());
        composition.setLink(compositionResource.getLink());
        composition.setFileFormat(compositionResource.getFileFormat());
        composition.setInstrumentType(compositionResource.getInstrumentcategory());


        try {
            byte[] byteArr = Base64.getDecoder().decode(compositionResource.getContent().getBytes("UTF-8"));
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            byte[] bytes = IOUtils.toByteArray(inputStream);

            composition.setContent(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        compositionService.updateComposition(composition);

        Composition out = compositionService.updateComposition(composition);

        return new ResponseEntity<>(compositionAssembler.toResource(out), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/{compositionId}")
    public ResponseEntity<CompositionResource> deleteComposition(@PathVariable("compositionId") int compositionId) {
        compositionService.removeComposition(compositionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
