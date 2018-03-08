package be.kdg.ip.web;

import be.kdg.ip.domain.Composition;
import be.kdg.ip.domain.Instrument;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.web.assemblers.CompositionAssembler;
import be.kdg.ip.web.resources.CompositionResource;
import be.kdg.ip.web.resources.InstrumentResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

    //Muziekstuk uploaden
    @RequestMapping(method = RequestMethod.POST, value="/", consumes = { "multipart/form-data" })
    @CrossOrigin(origins = "*")
    public @ResponseBody ResponseEntity<?> upload(@RequestParam("files") MultipartFile files, @RequestParam("compresource") String compresource) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

        CompositionResource compositionResource =  mapper.readValue(compresource, CompositionResource.class);
        Composition composition = new Composition();
        composition.setTitel(compositionResource.getTitel());
        composition.setArtist(compositionResource.getArtist());
        composition.setLanguage(compositionResource.getLanguage());
        composition.setGenre(compositionResource.getGenre());
        composition.setSubject(compositionResource.getSubject());
        composition.setLink(compositionResource.getLink());
        composition.setInstrumentType(compositionResource.getInstrumentType());

        try {
            composition.setFileFormat(files.getOriginalFilename());

            byte [] byteArr = files.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            byte[] bytes = IOUtils.toByteArray(inputStream);

            composition.setContent(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Composition out = compositionService.addComposition(composition);

        return new ResponseEntity<>(compositionAssembler.toResource(out), HttpStatus.OK);
    }

    //Alle muziekstukken opvragen
    @GetMapping
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Composition>> findAll(){
        List<Composition> compositions = compositionService.getAllCompositions();
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    //Muziekstukken opvragen
    @GetMapping("/{compositionId}")
    public ResponseEntity<Composition> findCompositionById(@PathVariable int compositionId){
        Composition composition = compositionService.getComposition(compositionId);

        return  new ResponseEntity<Composition>(composition,HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Composition>> findCompositionByTitle(@PathVariable("title") String title){
        List<Composition> compositions = compositionService.getCompositionsByTitle(title);
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Composition>> findCompositionByGenre(@PathVariable("genre") String genre){
        List<Composition> compositions = compositionService.getCompositionsByGenre(genre);
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Composition>> findCompositionBySubject(@PathVariable("subject") String subject){
        List<Composition> compositions = compositionService.getCompositionsBySubject(subject);
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Composition>> findCompositionByType(@PathVariable("type") String type){
        List<Composition> compositions = compositionService.getCompositionsByType(type);
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    @GetMapping("/fileformat/{fileformat}")
    public ResponseEntity<List<Composition>> findCompositionByFileFormat(@PathVariable("fileformat") String fileformat){
        List<Composition> compositions = compositionService.getCompositionsByFormat(fileformat);
        return new ResponseEntity<>(compositions,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/composition/{compositionId}")
    public ResponseEntity<CompositionResource> updateComposition(@PathVariable("compositionId") int compositionId,@Valid @RequestBody CompositionResource compositionResource) {
        Composition composition = compositionService.getComposition(compositionId);

        composition.setTitel(compositionResource.getTitel());
        composition.setArtist(compositionResource.getArtist());
        composition.setLanguage(compositionResource.getLanguage());
        composition.setGenre(compositionResource.getGenre());
        composition.setSubject(compositionResource.getSubject());
        composition.setLink(compositionResource.getLink());
        composition.setFileFormat(compositionResource.getFileFormat());
        composition.setInstrumentType(compositionResource.getInstrumentType());


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
