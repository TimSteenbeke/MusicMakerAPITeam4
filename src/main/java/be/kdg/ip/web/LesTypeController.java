package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.services.api.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LesTypeController {
    private CourseService courseService;

    public LesTypeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="/lestypes")
    public List<Course> getLesTypes() {
        return courseService.getAllCourses();
    }
}
