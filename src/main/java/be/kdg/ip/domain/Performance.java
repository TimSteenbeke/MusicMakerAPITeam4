package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Performance {

    @Id
    @GeneratedValue
    private int performanceId;

    @Column
    private String beschrijving;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column
    private LocalDateTime startDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column
    private LocalDateTime endDateTime;

    @ManyToMany
    private List<User> absentMembers;

    @ManyToMany
    private List<User> presentMembers;

    @JsonIgnore
    @ManyToMany(mappedBy="performances")
    private List<Agenda> agendas;

    @JsonIgnore
    @ManyToOne
    private Group group;


    public Performance() {
        this.agendas = new ArrayList<>();
        this.absentMembers = new ArrayList<>();
        this.presentMembers = new ArrayList<>();
    }

    public Performance(String beschrijving, LocalDateTime startDateTime, LocalDateTime endDateTime, Group group) {
        this.beschrijving = beschrijving;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
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

    public int getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(int performanceId) {
        this.performanceId = performanceId;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<User> getAbsentMembers() {
        return absentMembers;
    }

    public void setAbsentMembers(List<User> absentMembers) {
        this.absentMembers = absentMembers;
    }

    public List<User> getPresentMembers() {
        return presentMembers;
    }

    public void setPresentMembers(List<User> presentMembers) {
        this.presentMembers = presentMembers;
    }
}
