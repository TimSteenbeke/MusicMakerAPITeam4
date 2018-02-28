package be.kdg.ip.services.api;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.Lesson;
import org.springframework.stereotype.Service;

@Service
public interface LessonService {
    void addLesson(Agenda agenda, Lesson lesson);
    void addLesson(Lesson lesson);
    Lesson getLesson(int lessonId);
}
