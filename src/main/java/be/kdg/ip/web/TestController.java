package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(method = RequestMethod.GET,value ="/api/greeting")
    public String hallo() {
      return "Hallo Mankey";
    }


    @RequestMapping(method = RequestMethod.GET,value ="/api/lestype")
    public Course lesType() {
        Course course = new Course();
        course.setPrijs(20);
        course.setBeschrijving("een beschrijving voor Lorenz");
        course.setCourseId(1);
        return course;
    }

}
