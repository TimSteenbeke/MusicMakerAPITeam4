package be.kdg.ip.services.impl;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.Course;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.User;
import be.kdg.ip.repositories.api.AgendaRepository;
import be.kdg.ip.services.api.AgendaService;
import be.kdg.ip.services.api.UserService;
import be.kdg.ip.services.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("AgendaService")
public class AgendaServiceImpl implements AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UserService userService;


    @Override
    public Agenda getAgenda(String username) {

        try {
            User user = userService.findUserByUsername(username);
            return user.getAgenda();

        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAgenda(Agenda agenda) {
        agendaRepository.save(agenda);
    }

    @Override
    public Agenda getAgendaById(int agendaId) {
        return agendaRepository.findOne(agendaId);
    }


    @Override
    public void addLessonToEveryAgenda(Lesson lesson) {
        //Get course from lesson
        Course course = lesson.getCourse();

        //create collection of users (empty)
        List<User> users = new ArrayList<User>();

        //loop over group,teachers, students and add UNIQUE Users
            // TODO: implement group feature
            //loop teachers
            for (User teacher : course.getTeachers()) {
                if (!users.contains(teacher)) {
                    users.add(teacher);
                }
            }
            //loop students
            for (User student : course.getStudents()) {
                if (!users.contains(student)) {
                    users.add(student);
                }
            }

        //Loop over collection of users and add lesson to their agenda
        for (User user : users) {
                Agenda agenda = user.getAgenda();
                agenda.getLessons().add(lesson);
                agendaRepository.save(agenda);
        }
}


}
