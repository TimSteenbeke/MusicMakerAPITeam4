package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


import javax.persistence.*;
import java.time.LocalDateTime;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column
    private LocalDateTime startDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column
    private LocalDateTime endDateTime;

    @ManyToOne
    private Course course;

    @ManyToMany
    private List<User> absentStudents;

    @ManyToMany
    private List<User> presentStudents;

    @JsonIgnore
    @ManyToMany(mappedBy = "lessons")
    List<Agenda> agendas;

    public List<User> getAbsentStudents() {
        return absentStudents;
    }

    public void setAbsentStudents(List<User> absentStudents) {
        this.absentStudents = absentStudents;
    }

    public List<User> getPresentStudents() {
        return presentStudents;
    }

    public void setPresentStudents(List<User> presentStudents) {
        this.presentStudents = presentStudents;
    }

    public Lesson() {
        this.agendas = new ArrayList<Agenda>();
        this.absentStudents = new ArrayList<User>();
        this.presentStudents = new ArrayList<User>();
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
