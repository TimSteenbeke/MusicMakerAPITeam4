package be.kdg.ip.services.api;

import be.kdg.ip.domain.Course;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CourseService {
    Course addCourse(Course course);
    Course getCourse(int courseId);
    Course updateCourse(Course course);
    void removeCourse(int courseId);
    List<Course> getAllCourses();
}
