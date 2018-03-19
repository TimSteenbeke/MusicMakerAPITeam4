package be.kdg.ip.web.resources;

import be.kdg.ip.domain.Lesson;
import be.kdg.ip.domain.Performance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class AgendaResource {
    private int agendaId;
    @JsonIgnoreProperties({"absentMembers","presentMembers"})
    private List<Performance> performances;
    @JsonIgnoreProperties({"absentStudents","presentStudents"})
    private List<Lesson> lessons;
    private String agendaOwner;

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

    public String getAgendaOwner() {
        return agendaOwner;
    }

    public void setAgendaOwner(String agendaOwner) {
        this.agendaOwner = agendaOwner;
    }
}