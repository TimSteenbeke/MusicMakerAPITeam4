package be.kdg.ip.services.api;

import be.kdg.ip.domain.Agenda;
import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.Performance;

import java.util.List;

public interface AgendaService {
    Agenda getAgenda(String username);
    void saveAgenda(Agenda agenda);
    Agenda getAgendaById(int agendaId);
    void addLessonToEveryAgenda(Lesson lesson);
    void removeLessonFromEveryAgenda(Lesson lesson);
    void addPerformanceToEveryAgenda(Performance performance);
    void removePerformanceFromEveryAgenda(Performance performance);
}
