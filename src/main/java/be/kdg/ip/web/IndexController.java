package be.kdg.ip.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wouter on 31.01.17.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public String index() {
        return "index";
    }
}
