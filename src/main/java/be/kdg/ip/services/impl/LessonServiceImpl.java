package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.LessonRepository;
import be.kdg.ip.services.api.LessonService;
import be.kdg.ip.services.exceptions.UserServiceException;
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

    @Override
    public Lesson getLesson(int lessonId) {
        return lessonRepository.findOne(lessonId);
    }

    //TODO: error handling
    @Override
    public void setUserPresent(int lessonId, User user) {
        Lesson lesson = lessonRepository.findOne(lessonId);

        if (!lesson.getPresentStudents().contains(user)) {
            lesson.getPresentStudents().add(user);

            if (lesson.getAbsentStudents().contains(user)) {
                lesson.getAbsentStudents().remove(user);
            }
        }
        lessonRepository.save(lesson);
    }


    //TODO: error handling
    @Override
    public void setUserAbsent(int lessonId, User user) {

        Lesson lesson = lessonRepository.findOne(lessonId);

        if (!lesson.getAbsentStudents().contains(user)) {
            lesson.getAbsentStudents().add(user);

            if (lesson.getPresentStudents().contains(user)) {
                lesson.getPresentStudents().remove(user);
            }
        }
        lessonRepository.save(lesson);
    }
}




