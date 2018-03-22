/*
package be.kdg.ip.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/login")
public class LoginController {
    // Login form
    @RequestMapping("*")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
*/
