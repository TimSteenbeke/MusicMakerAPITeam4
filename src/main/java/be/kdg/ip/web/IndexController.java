package be.kdg.ip.web;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Created by wouter on 31.01.17.
 */
@RestController
@CrossOrigin(origins = "*")
public class IndexController {

    @RequestMapping("/")
    public ResponseEntity index() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://musicmaker-api-team4.herokuapp.com/swagger-ui.html"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

    }
}
