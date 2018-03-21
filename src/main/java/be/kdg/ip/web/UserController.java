package be.kdg.ip.web;

import be.kdg.ip.domain.*;
import be.kdg.ip.services.api.AddressService;
import be.kdg.ip.services.api.CompositionService;
import be.kdg.ip.services.api.RoleService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.dto.RoleDTO;
import be.kdg.ip.web.dto.RolesDTO;
import be.kdg.ip.web.resources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private AddressService addressService;
    private CompositionService compositionService;

    private Logger logger = LoggerFactory.getLogger(GroupController.class);

    public UserController(UserService userService, RoleService roleService, AddressService addressService, CompositionService compositionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.addressService = addressService;
        this.compositionService = compositionService;
    }


    //bekijken return type
    @PostMapping
    //ToDo: Authorization fix: instrument post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserResource userResource) {

        User user = new User();
        user.setFirstname(userResource.getFirstname());
        user.setLastname(userResource.getLastname());
        user.setPassword(userResource.getPassword());
        user.setUsername(userResource.getUsername());
        List<Role> roles = new ArrayList<>();


        for (Integer roleid : userResource.getRoleids())
        {
            roles.add(roleService.getRole(roleid));
        }

        user.setRoles(roles);

        //image omzetten
        String imageString = userResource.getUserimage();

        try {
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            user.setUserImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            logger.error("Error converting image while creating a user.");
        }

        Address address = new Address();
        address.setStreet(userResource.getStreet());
        address.setStreetNumber(userResource.getStreetnumber());
        address.setCountry(userResource.getCountry());
        address.setCity(userResource.getCity());
        address.setPostalCode(userResource.getPostalcode());

        addressService.addAddress(address);

        user.setAddress(address);

        User out = userService.addUser(user);

        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    //1 User opvragen userId
    @GetMapping("/{userId}")
    //ToDo: Authorization fix: user get
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<UserAddressDetailsResource> findUserByUserId(@PathVariable int userId) throws UserServiceException {
        User user = userService.findUser(userId);
        UserAddressDetailsResource userResource = new UserAddressDetailsResource();
        userResource.setUsername(user.getUsername());
        userResource.setFirstname(user.getFirstname());
        userResource.setLastname(user.getLastname());
        userResource.setCity(user.getAddress().getCity());
        userResource.setCountry(user.getAddress().getCountry());
        userResource.setPostalcode(user.getAddress().getPostalCode());
        userResource.setStreet(user.getAddress().getStreet());
        userResource.setStreetnumber(user.getAddress().getStreetNumber());
        userResource.setUserimage(new sun.misc.BASE64Encoder().encode(user.getUserImage()));

        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/loggedin")
    //ToDo: Authorization fix: group get by user
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<UserAddressDetailsResource> getCurrentUser(Principal principal) {
        String username = principal.getName();
        UserAddressDetailsResource userResource = new UserAddressDetailsResource();
        User user = null;
        try {
            user = userService.findUserByUsername(username);
            userResource.setId(user.getId());
            userResource.setUsername(user.getUsername());
            userResource.setFirstname(user.getFirstname());
            userResource.setLastname(user.getLastname());
            userResource.setCity(user.getAddress().getCity());
            userResource.setCountry(user.getAddress().getCountry());
            userResource.setPostalcode(user.getAddress().getPostalCode());
            userResource.setStreet(user.getAddress().getStreet());
            userResource.setStreetnumber(user.getAddress().getStreetNumber());
            userResource.setUserimage(new sun.misc.BASE64Encoder().encode(user.getUserImage()));
        } catch (UserServiceException e) {
            logger.error("No current user found.");
        }

        return new ResponseEntity<>(userResource, HttpStatus.OK);
    }

    //Alle groepen opvragen
    @GetMapping
    @CrossOrigin(origins = "*")
    //ToDo: Authorization fix: get all users
    public ResponseEntity<UserGetResource> findAll() {
        List<User> users = userService.getUsers();

        UserGetResource userGetResource = new UserGetResource();
        userGetResource.setUsers(new ArrayList<>());
        for (User user : users) {
            UserDetailsResource userDetailsResource = createUserDetailsResource(user);
            userGetResource.getUsers().add(userDetailsResource);
        }
        return new ResponseEntity<>(userGetResource, HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<UserGetResource> getStudents() {
        Role role = roleService.getRoleByName("Student");

        List<User> users = userService.getUserWithRole(role);
        UserGetResource userGetResource = new UserGetResource();
        userGetResource.setUsers(new ArrayList<>());
        for (User user : users) {
            UserDetailsResource userDetailsResource = createUserDetailsResource(user);
            userGetResource.getUsers().add(userDetailsResource);
        }
        return new ResponseEntity<>(userGetResource, HttpStatus.OK);
    }

    @GetMapping("/teacherAdmin")
    public ResponseEntity<UserGetResource> getTeacherAdmins() {
        Role teacher = roleService.getRoleByName("Teacher");
        Role admin = roleService.getRoleByName("Admin");
        List<User> teachers = userService.getUserWithRole(teacher);
        List<User> admins = userService.getUserWithRole(admin);

        UserGetResource userGetResource = new UserGetResource();
        userGetResource.setUsers(new ArrayList<>());

        for (User user : admins) {
            if (!teachers.contains(user)) {
                UserDetailsResource userDetailsResource = createUserDetailsResource(user);
                userGetResource.getUsers().add(userDetailsResource);
            }
        }
        for (User user : teachers) {
            UserDetailsResource userDetailsResource = createUserDetailsResource(user);
            userGetResource.getUsers().add(userDetailsResource);
        }

        return new ResponseEntity<>(userGetResource, HttpStatus.OK);
    }

    //Een user verwijderen
    @DeleteMapping("/{userId}")
    //ToDo: Authorization fix: delete user
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    //ToDo: Authorization fix: instrument updaten
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody UserAddressDetailsResource userResource) {

        User user = userService.findUser(id);

        user.setFirstname(userResource.getFirstname());
        user.setLastname(userResource.getLastname());
        user.setUsername(userResource.getUsername());

        //image omzetten
        String imageString = userResource.getUserimage();
        try {
            imageString = imageString.replaceAll("(\\r|\\n)", "");
            byte[] decodedString = Base64.getDecoder().decode(imageString.getBytes("UTF-8"));
            user.setUserImage(decodedString);
        } catch (UnsupportedEncodingException e) {
            logger.error("Error converting image while updating user.");
        }

        Address address = addressService.getAddress(user.getAddress().getId());
        address.setStreet(userResource.getStreet());
        address.setStreetNumber(userResource.getStreetnumber());
        address.setCountry(userResource.getCountry());
        address.setCity(userResource.getCity());
        address.setPostalCode(userResource.getPostalcode());

        addressService.updateAddress(address);
        User out = userService.updateUser(user);
        //
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/role/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateRoles(@PathVariable("id") int id, @RequestBody RoleUpdateUserResource roleUpdateUserResource) {

        User user = userService.findUser(id);
        List<Role> roles = user.getRoles();
        for (int i : roleUpdateUserResource.getRoleids()) {
            roles.add(roleService.getRole(i));
        }
        user.setRoles(roles);
        User out = userService.updateUser(user);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }


    //returned voorlopig nog User maar dit moet jwt token worden (denk ik?) dat terug doorgegeven wordt naar browser
    @PostMapping("/login")
    //ToDo: Delete this method ?
    public ResponseEntity<User> checkUser(@Valid @RequestBody UserResource userResource) throws UserServiceException {
        User checkedUser = userService.findUserByUsername(userResource.getUsername());
        if ((checkedUser != null) && (checkedUser.getPassword().equals(userResource.getPassword()))) {
            return new ResponseEntity<>(checkedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(checkedUser, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/excercises/{userId}")
    public ResponseEntity<List<Composition>> getExcercisesUser(@PathVariable int userId) {

        User user = userService.findUser(userId);
        List<Composition> excercises = user.getExercises();
        return new ResponseEntity<>(excercises, HttpStatus.OK);
    }

    @PostMapping("/excercises/{userId}")
    public ResponseEntity<List<Composition>> addExcerciseUser(@PathVariable int userId, @RequestBody UserExcerciseResource userExcerciseResource) {

        User user = userService.findUser(userId);
        Composition excercise = compositionService.getComposition(userExcerciseResource.getExcerciseid());
        List<Composition> excercises = user.getExercises();
        excercises.add(excercise);
        user.setExercises(excercises);
        userService.updateUser(user);
        return new ResponseEntity<>(user.getExercises(), HttpStatus.OK);
    }

    @PostMapping("/excercises/remove/{userId}")
    public ResponseEntity<List<Composition>> deleteExcerciseUser(@PathVariable int userId, @RequestBody UserExcerciseResource userExcerciseResource) {
        User user = userService.findUser(userId);
        Composition excercise = compositionService.getComposition(userExcerciseResource.getExcerciseid());
        List<Composition> excercises = user.getExercises();

        excercises.remove(excercise);

        user.setExercises(excercises);
        userService.updateUser(user);

        return new ResponseEntity<>(user.getExercises(), HttpStatus.OK);
    }

    @GetMapping("/myinstrumentlevels/")
    public ResponseEntity<List<InstrumentLevelUserInstrumentResource>> getInstrumentLevelsUser(Principal principal) throws UserServiceException {
        User user = userService.findUserByUsername(principal.getName());

        List<InstrumentLevel> instrumentLevels = user.getInstrumentLevels();


        List<InstrumentLevelUserInstrumentResource> resources = new ArrayList<>();

        for (InstrumentLevel i : instrumentLevels){
            InstrumentLevelUserInstrumentResource resource = new InstrumentLevelUserInstrumentResource();
            resource.setMaxLevel(i.getMaxLevel());
            resource.setLevel(i.getLevel());
            resource.setUser(i.getUser());
            resource.setInstrument(i.getInstrument());
            resources.add(resource);
        }


        return new ResponseEntity<>(resources,HttpStatus.OK);

    }

    private UserDetailsResource createUserDetailsResource(User user) {
        UserDetailsResource userDetailsResource = new UserDetailsResource();
        userDetailsResource.setUserid(user.getId());
        userDetailsResource.setFirstname(user.getFirstname());
        userDetailsResource.setLastname(user.getLastname());
        return userDetailsResource;
    }


    @RequestMapping(value = "/userroles", method = RequestMethod.GET)
    public ResponseEntity<RolesDTO> getRolesfromUser(Principal principal) {

        try {
            User user = userService.findUserByUsername(principal.getName());
            RolesDTO rolesDTO = new RolesDTO();
            for (Role role : user.getRoles()) {
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setRoleid(role.getRoleId());
                roleDTO.setRolename(role.getRoleName());
                rolesDTO.getRoles().add(roleDTO);
            }


            return new ResponseEntity<>(rolesDTO, HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}