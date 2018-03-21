package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.CourseType;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.CourseTypeService;
import be.kdg.ip.services.api.LessonService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.dto.CourseDTO;
import be.kdg.ip.web.resources.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import be.kdg.ip.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseTypeService courseTypeService;

    @Autowired
    private LessonService lessonService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="api/courses")
    //ToDo: Authorization fix: courses get
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @RequestMapping(method = RequestMethod.POST, value="api/courses")
    //ToDo: Authorization fix: courses post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseResource> addCourse(@Valid @RequestBody CourseResource courseResource) {

        CourseType courseType = courseTypeService.getCourseType(courseResource.getCourseTypeId());
        if(courseType != null) {
            Course course = new Course();

            course.setCourseType(courseType);

            //Add all students to the course
            List<User> students = new ArrayList<User>();
            for (Integer studentid : courseResource.getStudentids()) {
                students.add(userService.findUser(studentid));
            }
            course.setStudents(students);

            //Add all teachers to the course
            List<User> teachers = new ArrayList<User>();
            for (Integer teacherid : courseResource.getTeacherids()) {
                teachers.add(userService.findUser(teacherid));
            }

            course.setTeachers(teachers);
            courseService.addCourse(course);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value="api/courses/{courseId}")
    //ToDo: Authorization fix: courses put
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseResource> updateCourse(@PathVariable("courseId") int courseId,@Valid @RequestBody CourseResource courseResource) {

        CourseType courseType = courseTypeService.getCourseType(courseResource.getCourseTypeId());

        if (courseType != null) {
            Course course = courseService.getCourse(courseId);


            course.setCourseType(courseType);

            //Add all students to the course
            List<User> students = new ArrayList<User>();
            if (courseResource.getStudentids() != null) {
                for (Integer studentid : courseResource.getStudentids()) {
                    students.add(userService.findUser(studentid));
                }
            }
            course.setStudents(students);

            //Add all teachers to the course
            List<User> teachers = new ArrayList<User>();
            if (courseResource.getTeacherids() != null) {
                for (Integer teacherid : courseResource.getTeacherids()) {
                    teachers.add(userService.findUser(teacherid));
                }
            }

            course.setTeachers(teachers);
            courseService.updateCourse(course);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value="api/courses/{courseId}")
    //ToDo: Authorization fix: courses delete
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseResource> updateCourse(@PathVariable("courseId") int courseId) {
        courseService.removeCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET,value ="api/courses/{courseId}")
    //ToDo: Authorization fix: courses get
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable("courseId") int courseId) {
        Course course =  courseService.getCourse(courseId);

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setStudents(course.getStudents());
        courseDTO.setTeachers(course.getTeachers());

        CourseType courseType = courseTypeService.getCourseType(course.getCourseType().getCourseTypeId());
        courseDTO.setCourseType(courseType);

        return new ResponseEntity<CourseDTO>(courseDTO,HttpStatus.OK);



    }

    @RequestMapping(method = RequestMethod.GET, value ="api/courses/{courseId}/lessons")
    public ResponseEntity<LessonsResource> getLessonFromCourse(@PathVariable("courseId")int courseId) {
        Course course = courseService.getCourse(courseId);
        LessonsResource lessonsResource = new LessonsResource();

        for (Lesson lesson : course.getLessons()) {
            LessonWithStudentsResource lessonWithStudentsResource = new LessonWithStudentsResource();
            lessonWithStudentsResource.setAbsentStudents(lesson.getAbsentStudents());
            lessonWithStudentsResource.setPresentStudents(lesson.getPresentStudents());
            lessonWithStudentsResource.setNoStatusStudents(lessonService.getNoStatusStudents(lesson));
            lessonWithStudentsResource.setCourseid(course.getCourseId());
            lessonWithStudentsResource.setEnddatetime(lesson.getEndDateTime());
            lessonWithStudentsResource.setStartdatetime(lesson.getStartDateTime());

            lessonsResource.getLessonResources().add(lessonWithStudentsResource);
        }
        return new ResponseEntity<LessonsResource>(lessonsResource,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET,value = "api/mycourses")
    public ResponseEntity<MyCoursesResource> getMyCourses(Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());

            MyCoursesResource myCoursesResource = new MyCoursesResource();
            myCoursesResource.setTeachesCourses(user.getTeachescourses());
            myCoursesResource.setFollowCourses(user.getCourses());

            return new ResponseEntity<MyCoursesResource>(myCoursesResource,HttpStatus.OK);

        } catch (UserServiceException e) {
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NullPointerException.class)
    public String return404(NullPointerException ex) {
        return ex.getMessage();
    }


}
