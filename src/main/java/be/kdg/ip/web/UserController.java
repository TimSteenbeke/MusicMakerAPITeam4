package be.kdg.ip.web;

import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService ) {
        this.userService = userService;
    }

    //1 User opvragen
    @GetMapping("/{userName}")
    //ToDo: Authorization fix: user get
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> findUserByUserId(@PathVariable String userName) throws UserServiceException {
        User user= userService.findUserByUsername(userName);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
