package be.kdg.ip.web;

import be.kdg.ip.domain.Composition;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.assemblers.CompositionAssembler;
import be.kdg.ip.web.resources.CompositionResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/compositions")
public class CompositionController {
    private CompositionService compositionService;
    private UserService userService;
    private CompositionAssembler compositionAssembler;


    private Logger logger = LoggerFactory.getLogger(CompositionController.class);

    public CompositionController(CompositionService compositionService, UserService userService, CompositionAssembler compositionAssembler) {

        this.compositionService = compositionService;
        this.userService = userService;
        this.compositionAssembler = compositionAssembler;
    }

    //uploading of a composition
    @RequestMapping(method = RequestMethod.POST, value = "/", consumes = {"multipart/form-data"})
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public @ResponseBody
    ResponseEntity<?> upload(@RequestParam("files") MultipartFile files, @RequestParam("compresource") String compresource) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        CompositionResource compositionResource = mapper.readValue(compresource, CompositionResource.class);
        Composition composition = new Composition();
        composition.setTitle(compositionResource.getTitle());
        composition.setArtist(compositionResource.getArtist());
        composition.setLanguage(compositionResource.getLanguage());
        composition.setGenre(compositionResource.getGenre());
        composition.setSubject(compositionResource.getSubject());
        composition.setLink(compositionResource.getLink());
        composition.setInstrumentType(compositionResource.getInstrumentType());
        composition.setFileFormat(compositionResource.getFileFormat());
        composition.setInstrumentType(compositionResource.getInstrumentType());

        try {
            composition.setFileFormat(files.getOriginalFilename());

            byte[] byteArr = files.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            byte[] bytes = IOUtils.toByteArray(inputStream);

            composition.setContent(bytes);

        } catch (UnsupportedEncodingException e) {
            logger.error("Error converting image when uploading a new composition.");
        }

        Composition out = compositionService.addComposition(composition);

        return new ResponseEntity<>(compositionAssembler.toResource(out), HttpStatus.OK);
    }

    //Request all compositions
    @GetMapping
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<Composition>> findAll() {
        List<Composition> compositions = compositionService.getAllCompositions();
        return new ResponseEntity<>(compositions, HttpStatus.OK);
    }

    //Request single composition
    @GetMapping("/{compositionId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Composition> findCompositionById(@PathVariable int compositionId) {
        Composition composition = compositionService.getComposition(compositionId);

        return new ResponseEntity<>(composition, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addtoplaylist/{compositionId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> addCompositionToMyPlaylist(@PathVariable("compositionId") int compositionId, Principal principal) throws UserServiceException {
        Composition composition = compositionService.getComposition(compositionId);
        User user = userService.findUserByUsername(principal.getName());

        List<Composition> compositions = user.getPlayList();

        compositions.add(composition);

        user.setPlayList(compositions);
        userService.updateUser(user);


        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/mycompositions")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<Composition>> findCompositionsByUser(Principal principal) {
        User user = null;
        List<Composition> playList = new ArrayList<>();

        try {
            user = userService.findUserByUsername(principal.getName());
            playList = user.getPlayList();
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(playList, HttpStatus.OK);
    }

    @GetMapping("/filter/{filter}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<List<Composition>> findCompositionByFilter(@PathVariable("filter") String filter) {
        List<Composition> compositions = compositionService.getCompositionsByFilter(filter);
        return new ResponseEntity<>(compositions, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/composition/{compositionId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    public ResponseEntity<CompositionResource> updateComposition(@PathVariable("compositionId") int compositionId, @Valid @RequestBody CompositionResource compositionResource) {
        Composition composition = compositionService.getComposition(compositionId);

        composition.setTitle(compositionResource.getTitle());
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

    @RequestMapping(method = RequestMethod.DELETE, value = "/{compositionId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER')")
    public ResponseEntity<CompositionResource> deleteComposition(@PathVariable("compositionId") int compositionId) {
        compositionService.removeComposition(compositionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
