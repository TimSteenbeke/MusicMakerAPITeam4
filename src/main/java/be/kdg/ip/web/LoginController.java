package be.kdg.ip.web;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/login")
public class LoginController {
    //ToDo: login tokenizer
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
