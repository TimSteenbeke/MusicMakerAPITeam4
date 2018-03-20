package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Course;
import be.kdg.ip.repositories.api.CourseRepository;
import be.kdg.ip.services.api.CourseService;
import be.kdg.ip.services.api.LessonService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private LessonService lessonService;

    @Override
    public Course addCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public Course getCourse(int courseId) {
        return repository.findOne(courseId);
    }

    @Override
    public Course updateCourse(Course course) {
        return repository.save(course);
    }

    @Override
    public void removeCourse(int courseId) {
        Course course = repository.findOne(courseId);
        lessonService.removeAllLessonsFromCourse(course);
        repository.delete(courseId);
    }

    @Override
    public List<Course> getAllCourses() {
        return repository.findAll();
    }
}
