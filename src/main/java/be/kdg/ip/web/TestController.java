package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserServiceImpl userService;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @RequestMapping(method = RequestMethod.GET,value ="/api/greeting")
    public String hallo() {
      return "Hallo Mankey " + auth;
    }


    @RequestMapping(method = RequestMethod.GET,value ="/api/lestype")
    public Course lesType() {
        Course course = new Course();
        course.setPrijs(20);
        course.setBeschrijving("een beschrijving voor Lorenz");
        course.setCourseId(1);
        return course;
    }

    @RequestMapping(method = RequestMethod.GET, value="api/geefuser")
    public User geefUsterTerug() throws UserServiceException {
      return  userService.findUserByUsername("lode.wouters@student.kdg.be");
    }

}
