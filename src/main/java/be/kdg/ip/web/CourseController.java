package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.services.api.CourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="api/courses")
    //ToDo: Authorization fix: courses
    // @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }
}
