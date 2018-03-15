package be.kdg.ip.services.api;

import be.kdg.ip.domain.Course;
import java.util.List;


public interface CourseService {
    Course addCourse(Course course);
    Course getCourse(int courseId);
    Course updateCourse(Course course);
    void removeCourse(int courseId);
    List<Course> getAllCourses();
}
