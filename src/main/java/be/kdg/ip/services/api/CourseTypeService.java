package be.kdg.ip.services.api;

import be.kdg.ip.domain.CourseType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseTypeService {
    CourseType addCourseType(CourseType courseType);
    CourseType getCourseType(int courseTypeId);
    CourseType updateCourseType(CourseType courseType);
    void removeCourseType(int courseTypeId);
    List<CourseType> getAllCourseTypes();
}
