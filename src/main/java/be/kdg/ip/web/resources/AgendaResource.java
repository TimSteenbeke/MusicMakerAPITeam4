package be.kdg.ip.web.resources;

import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.Performance;

import java.util.List;

public class AgendaResource {
    private int agendaId;
    private List<Performance> performances;
    private List<Lesson> lessons;
    private String agendaEigenaar;

    public int getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(int agendaId) {
        this.agendaId = agendaId;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getAgendaEigenaar() {
        return agendaEigenaar;
    }

    public void setAgendaEigenaar(String agendaEigenaar) {
        this.agendaEigenaar = agendaEigenaar;
    }
}