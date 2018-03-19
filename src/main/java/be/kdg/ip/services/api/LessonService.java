package be.kdg.ip.services.api;

import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonService {
    void addLesson(Lesson lesson);
    Lesson getLesson(int lessonId);
    List<Lesson> getAllLessons();
    void deleteLesson(int lessonId );
    Lesson updateLesson(Lesson lesson);
    void setUserPresent(int lessonId, User user);
    void setUserAbsent(int lessonId, User user);
    List<User> getNoStatusStudents(Lesson lesson);
}
