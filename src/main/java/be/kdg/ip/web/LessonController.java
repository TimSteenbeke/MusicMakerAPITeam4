package be.kdg.ip.web;

import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.LessonService;
import be.kdg.ip.web.resources.LessonResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@CrossOrigin(origins = "*")
@RestController
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    CourseService courseService;

    @Autowired
    AgendaService agendaService;

    //@CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST,value ="/api/lesson")
    //ToDo: Authorization fix: lesson post
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<LessonResource> addLesson(@Valid @RequestBody LessonResource lessonResource) {

        //Create lesson based on lessonResource
        Lesson lesson = new Lesson();
        lesson.setStartDateTime(lessonResource.getStartdatetime());
        lesson.setEndDateTime(lessonResource.getEnddatetime());
        //Get a course
        Course course = courseService.getCourse(lessonResource.getCourseid());
        lesson.setCourse(course);

        //Add lesson
        lessonService.addLesson(lesson);

        //For each User in Course of Lesson (add lesson to agenda of user) = best to make a seperate service for this
        agendaService.addLessonToEveryAgenda(lesson);


        return  new ResponseEntity<>(lessonResource, HttpStatus.OK);
    }


}
