package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.CourseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.web.resources.CourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="api/courses")
    //ToDo: Authorization fix: courses
    // @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @RequestMapping(method = RequestMethod.POST, value="api/courses")
    public ResponseEntity<CourseResource> addCourse(@Valid @RequestBody CourseResource courseResource) {
        Course course = new Course();

        course.setPrice(courseResource.getPrice());
        course.setDescription(courseResource.getCoursedescription());

        //Add all students to the course
        List<User> students = new ArrayList<User>();
        for (Integer studentid : courseResource.getStudentids()) {
            students.add(userService.findUser(studentid));
        }
        course.setStudents(students);

        //Add all teachers to the course
        List<User> teachers = new ArrayList<User>();
        for(Integer teacherid : courseResource.getTeacherids()) {
            teachers.add(userService.findUser(teacherid));
        }

        course.setTeachers(teachers);
        courseService.addCourse(course);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value="api/courses/{courseId}")
    public ResponseEntity<CourseResource> updateCourse(@PathVariable("courseId") int courseId,@Valid @RequestBody CourseResource courseResource) {
        Course course = courseService.getCourse(courseId);

        course.setPrice(courseResource.getPrice());
        course.setDescription(courseResource.getCoursedescription());

        //Add all students to the course
        List<User> students = new ArrayList<User>();
        for (Integer studentid : courseResource.getStudentids()) {
            students.add(userService.findUser(studentid));
        }
        course.setStudents(students);

        //Add all teachers to the course
        List<User> teachers = new ArrayList<User>();
        for(Integer teacherid : courseResource.getTeacherids()) {
            teachers.add(userService.findUser(teacherid));
        }

        course.setTeachers(teachers);
        courseService.updateCourse(course);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="api/courses/{courseId}")
    public ResponseEntity<CourseResource> updateCourse(@PathVariable("courseId") int courseId) {
        courseService.removeCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
