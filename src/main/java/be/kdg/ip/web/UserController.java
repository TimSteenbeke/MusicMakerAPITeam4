package be.kdg.ip.web;

import be.kdg.ip.domain.Group;
import be.kdg.ip.domain.Role;
import be.kdg.ip.domain.User;
import be.kdg.ip.domain.roles.Administrator;
import be.kdg.ip.domain.roles.Student;
import be.kdg.ip.domain.roles.Teacher;
import be.kdg.ip.services.api.RoleService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.resources.RoleUpdateUserResource;
import be.kdg.ip.web.resources.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    //Aanmaken van een instrument
    @PostMapping
    //ToDo: Authorization fix: instrument post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserResource userResource) {

        User user = new User();
        user.setFirstname(userResource.getFirstname());
        user.setLastname(userResource.getLastname());
        user.setPassword(userResource.getPassword());
        user.setUsername(userResource.getUsername());
        List<Role> roles = user.getRoles();
        roles.add(roleService.getRole(3));
        user.setRoles(roles);
        User out = userService.addUser(user);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }

 /*   //1 User opvragen met username
    @GetMapping("/{userName}")
    //ToDo: Authorization fix: user get
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> findUserByUserId(@PathVariable String userName) throws UserServiceException {
        User user = userService.findUserByUsername(userName);
        return new ResponseEntity<User>(user, HttpStatus.OK);
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
    //ToDo: Authorization fix: get all users
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<User>> getStudents(){
        Role role = roleService.getRoleByName("Student");

        List<User> users =  userService.getUserWithRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/teacherAdmin")
    public ResponseEntity<List<User>> getTeacherAdmins(){
        Role teacher = roleService.getRoleByName("Teacher");
        Role admin = roleService.getRoleByName("Admin");
        List<User> teachers =  userService.getUserWithRole(teacher);
        List<User> admins = userService.getUserWithRole(admin);

        List<User> users = new ArrayList<>();

        for (User u : admins){
            if (!teachers.contains(u)){
                users.add(u);
            }
        }
        users.addAll(teachers);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Een user verwijderen
    @DeleteMapping("/{userId}")
    //ToDo: Authorization fix: delete user
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) {
        User user = userService.findUser(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrument updaten
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody UserResource userResource) {

        User user = userService.findUser(id);

        user.setFirstname(userResource.getFirstname());
        user.setLastname(userResource.getLastname());
        user.setPassword(userResource.getPassword());
        user.setUsername(userResource.getUsername());
        User out = userService.addUser(user);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles(){
        List<Role> roles = roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @RequestMapping(value = "/user/role/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateRoles(@PathVariable("id") int id, @RequestBody RoleUpdateUserResource roleUpdateUserResource){

        User user = userService.findUser(id);
        List<Role> roles = user.getRoles();
        for (int i : roleUpdateUserResource.getRoleids()){
            roles.add(roleService.getRole(i));
        }
        user.setRoles(roles);
        User out = userService.updateUser(user);
        return new  ResponseEntity<>(out , HttpStatus.OK);
    }


    //returned voorlopig nog User maar dit moet jwt token worden (denk ik?) dat terug doorgegeven wordt naar browser
    @PostMapping("/login")
    //ToDo: Delete this method ?
    public ResponseEntity<User> checkUser(@Valid @RequestBody UserResource userResource) throws UserServiceException {
        User checkedUser = userService.findUserByUsername(userResource.getUsername());
        if (checkedUser != null) {
            if(checkedUser.getPassword().equals(userResource.getPassword())) {
                return new ResponseEntity<User>(checkedUser, HttpStatus.OK);
            }
        }
        return new ResponseEntity<User>(checkedUser, HttpStatus.FORBIDDEN);
    }
}