package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.LessonService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.dto.StatusDTO;
import be.kdg.ip.web.resources.LessonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    CourseService courseService;

    @Autowired
    AgendaService agendaService;

    @Autowired
    UserService userService;

    //@CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST,value ="/api/lesson")
    //ToDo: Authorization fix: lesson post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<LessonResource> addLesson(@Valid @RequestBody LessonResource lessonResource) {

        //Lesson aanmaken based op lessonResource
        Lesson lesson = new Lesson();
        lesson.setStartDateTime(lessonResource.getStartdatetime());
        lesson.setEndDateTime(lessonResource.getEnddatetime());
        //Course ophalen
        Course course = courseService.getCourse(lessonResource.getCourseid());
        lesson.setCourse(course);

        //Lesson toevoegen
        lessonService.addLesson(lesson);
        //Voor iedere User in Course van les ( les toevoegen aan agenda van user) = Best aparte service voor maken
        agendaService.addLessonToEveryAgenda(lesson);

        return  new ResponseEntity<>(lessonResource, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value="/api/lesson/absent/{lessonid}")
    public ResponseEntity registerUserAbsent(@PathVariable("lessonid")  int lessonId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            lessonService.setUserAbsent(lessonId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value="/api/lesson/present/{lessonid}")
    public ResponseEntity registerUserPresent(@PathVariable("lessonid")  int lessonId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            lessonService.setUserPresent(lessonId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/api/lesson/attendancestatus/{lessonid}")
    public ResponseEntity<StatusDTO> getAttendanceStatus(@PathVariable("lessonid")  int lessonId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            Lesson lesson = lessonService.getLesson(lessonId);

            if (lesson.getAbsentStudents().contains(user)) {
                // ABSENT
                return new ResponseEntity<StatusDTO>(new StatusDTO("absent"),HttpStatus.OK);
            }
                else {
                    if (lesson.getPresentStudents().contains(user)) {
                        //PRESENT
                        return new ResponseEntity<StatusDTO>(new StatusDTO("present"),HttpStatus.OK);
                    } else {
                        // NOT SET
                        return new ResponseEntity<StatusDTO>(new StatusDTO("nostatus"),HttpStatus.OK);
                    }
                }
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }




}
