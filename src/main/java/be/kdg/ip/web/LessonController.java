package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.User;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.LessonService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import be.kdg.ip.web.dto.StatusDTO;
import be.kdg.ip.web.resources.LessonGetResource;
import be.kdg.ip.web.resources.LessonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/lesson")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    CourseService courseService;


    @Autowired
    UserService userService;

    //@CrossOrigin(origins = "*")
    @PostMapping
    //ToDo: Authorization fix: lesson post
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<LessonResource> addLesson(@Valid @RequestBody LessonResource lessonResource) {

        //Create lesson based on lessonResource
        Lesson lesson = new Lesson();
        lesson.setStartDateTime(lessonResource.getStartdatetime().plusHours(1));
        lesson.setEndDateTime(lessonResource.getEnddatetime().plusHours(1));
        //Get a course
        Course course = courseService.getCourse(lessonResource.getCourseid());
        lesson.setCourse(course);

        //Add lesson
        lessonService.addLesson(lesson);

        return  new ResponseEntity<>(lessonResource, HttpStatus.OK);
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonGetResource> getLesson(@PathVariable int lessonId){

        Lesson lesson = lessonService.getLesson(lessonId);

        LessonGetResource lessonGetResource = new LessonGetResource();

        lessonGetResource.setCourse(lesson.getCourse());
        lessonGetResource.setStartdatetime(lesson.getStartDateTime());
        lessonGetResource.setEnddatetime(lesson.getEndDateTime());

        return new ResponseEntity<>(lessonGetResource,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<LessonGetResource>> getAllLessons(){
        List<Lesson> lessons = lessonService.getAllLessons();
        List<LessonGetResource> lessonGetResources = new ArrayList<>();
        for (Lesson lesson : lessons){
            LessonGetResource lessonGetResource = new LessonGetResource();
            lessonGetResource.setId(lesson.getLessonId());
            lessonGetResource.setEnddatetime(lesson.getEndDateTime());
            lessonGetResource.setStartdatetime(lesson.getStartDateTime());
            lessonGetResource.setCourse(lesson.getCourse());
            lessonGetResources.add(lessonGetResource);
        }

        return new ResponseEntity<>(lessonGetResources,HttpStatus.OK);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable("lessonId") Integer lessonId){


        lessonService.deleteLesson(lessonId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/lesson/{id}")
    public ResponseEntity<LessonResource> updateLesson(@PathVariable("id") int id, @RequestBody LessonResource lessonResource){

        Lesson lesson = lessonService.getLesson(id);

        lesson.setStartDateTime(lessonResource.getStartdatetime().plusHours(1));
        lesson.setEndDateTime(lessonResource.getEnddatetime().plusHours(1));
        //Course ophalen
        Course course = courseService.getCourse(lessonResource.getCourseid());
        lesson.setCourse(course);


        //Voor iedere User in Course van les ( les toevoegen aan agenda van user) = Best aparte service voor maken
        //agendaService.updateLessonFromEveryAgenda(lesson);


        //Lesson toevoegen
        lessonService.updateLesson(lesson);
        return new ResponseEntity<>(lessonResource,HttpStatus.OK);
    }



    @PostMapping("/absent/{lessonid}")
    public ResponseEntity registerUserAbsent(@PathVariable("lessonid")  int lessonId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            lessonService.setUserAbsent(lessonId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/present/{lessonid}")
    public ResponseEntity registerUserPresent(@PathVariable("lessonid")  int lessonId, Principal principal) {
        try {
            User user = userService.findUserByUsername(principal.getName());
            lessonService.setUserPresent(lessonId,user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserServiceException e) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/attendancestatus/{lessonid}")
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
