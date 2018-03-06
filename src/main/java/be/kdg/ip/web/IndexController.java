package be.kdg.ip.web;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wouter on 31.01.17.
 */
@RestController
@CrossOrigin(origins = "*")
public class IndexController {

    @RequestMapping("/")
    //ToDo: Authorization fix: index/home
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public String index() {
        return "index";
    }
}
