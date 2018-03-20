package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Performance {

    @Id
    @GeneratedValue
    private int performanceId;

    @Column
    private String description;

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
    @ManyToOne
    private Group group;


    public Performance() {
        this.absentMembers = new ArrayList<>();
        this.presentMembers = new ArrayList<>();
    }

    public Performance(String beschrijving, LocalDateTime startDateTime, LocalDateTime endDateTime, Group group) {
        this.description = beschrijving;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
