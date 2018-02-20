package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.services.api.CourseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="api/courses")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }
}
