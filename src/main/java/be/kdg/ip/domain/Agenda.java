package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Agenda")
public class Agenda {

    @Id
    @GeneratedValue
    @Column
    private int agendaId;

    @ManyToMany()
    //@JoinTable(name="Agenda_lessons", joinColumns=@JoinColumn(name="Agendas_agenda_id", referencedColumnName="agendaId"), inverseJoinColumns=@JoinColumn(name="lessons_lesson_id", referencedColumnName="lessonId"))
    private List<Lesson> lessons;

    @ManyToMany
    @JoinTable(name="agenda_performances") //, joinColumns=@JoinColumn(name="agenda_agenda_id"), inverseJoinColumns=@JoinColumn(name="performances_performance_id"))
    private List<Performance> performances;

    @JsonIgnore
    @OneToOne(mappedBy = "agenda")
    private User user;

    public Agenda() {
        this.lessons = new ArrayList<>();
        this.performances = new ArrayList<>();
    }

    public int getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(int agendaId) {
        this.agendaId = agendaId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
