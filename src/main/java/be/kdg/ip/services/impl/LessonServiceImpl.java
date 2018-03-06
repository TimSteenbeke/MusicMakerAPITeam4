package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.repositories.api.LessonRepository;
import be.kdg.ip.services.api.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LessonService")
public class LessonServiceImpl implements LessonService {
    @Autowired
    LessonRepository lessonRepository;

    @Override
    public void addLesson(Agenda agenda, Lesson lesson) {
        agenda.getLessons().add(lesson);
        lessonRepository.save(lesson);
    }

    @Override
    public void addLesson(Lesson lesson) {
        lessonRepository.save(lesson);
    }


}
