package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.resources.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    //Aanmaken van een instrument
    @PostMapping
    //ToDo: Authorization fix: instrument post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserResource userResource) {

        User user = new User();
        user.setFirstname(userResource.getFirstname());
        user.setLastname(userResource.getLastname());
        user.setPassword(userResource.getPassword());
        user.setUsername(userResource.getUsername());

        User out = userService.addUser(user);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }

 /*   //1 User opvragen met username
    @GetMapping("/{userName}")
    //ToDo: Authorization fix: user get
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> findUserByUserName(@PathVariable String userName) throws UserServiceException {
        User user = userService.findUserByUsername(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
*/
    //1 User opvragen userId
    @GetMapping("/{userId}")
    //ToDo: Authorization fix: user get
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> findUserByUserId(@PathVariable int userId) throws UserServiceException {
        User user = userService.findUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Alle groepen opvragen
    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: get all Group
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Een user verwijderen
    @DeleteMapping("/{userId}")
    //ToDo: Authorization fix: delete instrument
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) {
        User user = userService.findUser(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrument updaten
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody UserResource userResource) {

        User user = userService.findUser(id);

        user.setFirstname(userResource.getFirstname());
        user.setLastname(userResource.getLastname());
        user.setPassword(userResource.getPassword());
        user.setUsername(userResource.getUsername());
        User out = userService.addUser(user);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }
}
