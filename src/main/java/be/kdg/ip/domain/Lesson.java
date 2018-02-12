package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Lesson")
public class Lesson {

    @Id
    @GeneratedValue
    @Column
    private int lessonId;

    @Column
    private Date datum;

    @Column
    private String beginUur;

    @Column
    private String eindUur;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons")
   // @JoinTable(name="Agenda_lessons", joinColumns=@JoinColumn(name="lessons_lesson_id", referencedColumnName="lessonId"), inverseJoinColumns=@JoinColumn(name="Agendas_agenda_id", referencedColumnName="agendaId"))
    List<Agenda> agendas;

    public Lesson() {
        this.agendas = new ArrayList<Agenda>();
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBeginUur() {
        return beginUur;
    }

    public void setBeginUur(String beginUur) {
        this.beginUur = beginUur;
    }

    public String getEindUur() {
        return eindUur;
    }

    public void setEindUur(String eindUur) {
        this.eindUur = eindUur;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
}
