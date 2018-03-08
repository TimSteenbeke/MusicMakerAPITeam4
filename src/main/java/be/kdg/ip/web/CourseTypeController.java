package be.kdg.ip.web;

import be.kdg.ip.domain.CourseType;
import be.kdg.ip.services.api.CourseTypeService;
import be.kdg.ip.web.resources.CourseTypeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class CourseTypeController {
    @Autowired
    private CourseTypeService courseTypeService;

    public CourseTypeController(CourseTypeService courseTypeService) {
        this.courseTypeService = courseTypeService;
    }

    @RequestMapping(method = RequestMethod.GET,value ="api/courseTypes/{courseTypeId}")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseTypeResource> getCourseType(@PathVariable("courseTypeId") int courseTypeId) {
        CourseTypeResource courseTypeResource = new CourseTypeResource();
        CourseType courseType = courseTypeService.getCourseType(courseTypeId);

        courseTypeResource.setDescription(courseType.getDescription());

        return new ResponseEntity<CourseTypeResource>(courseTypeResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value="api/courseTypes/{courseTypeId}")
    //ToDo: Authorization fix: courses delete
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseTypeResource> updateCourseType(@PathVariable("courseTypeId") int courseTypeId){
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE,value ="api/courseTypes/{courseTypeId}")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseTypeResource> removeCourseType(@PathVariable("courseTypeId") int courseTypeId) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST,value ="api/courseTypes/{courseTypeId}")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseTypeResource> addCourseType(@PathVariable("courseTypeId") int courseTypeId) {
        return null;
    }


    @RequestMapping(method = RequestMethod.GET,value ="api/courseTypes/")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseTypeResource> getAllCourseTypes() {
        return null;
    }
}
