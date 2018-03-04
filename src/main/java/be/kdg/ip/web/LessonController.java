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

        //Heroku rebuild


        return  new ResponseEntity<>(lessonResource, HttpStatus.OK);
    }


}
