package be.kdg.ip.web;

import be.kdg.ip.domain.CourseType;
import be.kdg.ip.services.api.CourseTypeService;
import be.kdg.ip.web.resources.CourseTypeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        courseTypeResource.setCourseTypeId(courseType.getCourseTypeId());
        courseTypeResource.setCourseTypeDescription(courseType.getDescription());
        courseTypeResource.setPrice(courseType.getPrice());

        return new ResponseEntity<CourseTypeResource>(courseTypeResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value="api/courseTypes/{courseTypeId}")
    //ToDo: Authorization fix: courses delete
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseType> updateCourseType(@PathVariable("courseTypeId") int courseTypeId, @RequestBody CourseTypeResource courseTypeResource){
        CourseType courseType = new CourseType();
        courseType.setCourseTypeId(courseTypeId);
        courseType.setDescription(courseTypeResource.getCourseTypeDescription());
        courseType.setPrice(courseTypeResource.getPrice());

        CourseType out = courseTypeService.updateCourseType(courseType);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE,value ="api/courseTypes/{courseTypeId}")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseTypeResource> removeCourseType(@PathVariable("courseTypeId") int courseTypeId) {
        CourseType courseType = courseTypeService.getCourseType(courseTypeId);

        courseTypeService.removeCourseType(courseType.getCourseTypeId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST,value ="api/courseTypes")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<CourseType> addCourseType(@Valid @RequestBody CourseTypeResource courseTypeResource) {
        CourseType courseType = new CourseType();
        courseType.setDescription(courseTypeResource.getCourseTypeDescription());
        courseType.setPrice(courseTypeResource.getPrice());

        CourseType out = courseTypeService.addCourseType(courseType);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET,value ="api/courseTypes")
    //ToDo: Authorization fix
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public List<CourseType> getAllCourseTypes() {
        return courseTypeService.getAllCourseTypes();
    }
}
