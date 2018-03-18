package be.kdg.ip.web.resources;

import be.kdg.ip.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

public class LessonWithStudentsResource {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startdatetime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime enddatetime;
    private int courseid;

    @JsonIgnoreProperties({"username","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","address","exercises","instrumentLevels","userImage"})
    private List<User> absentStudents;
    private List<User> presentStudents;
    @JsonIgnoreProperties({"username","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","address","exercises","instrumentLevels","userImage"})
    private List<User> noStatusStudents;
    @JsonIgnoreProperties({"username","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","address","exercises","instrumentLevels","userImage"})


    public LessonWithStudentsResource(LocalDateTime startdatetime, LocalDateTime enddatetime, int courseid, List<User> absentStudents, List<User> presentStudents, List<User> noStatusStudents) {
        this.startdatetime = startdatetime;
        this.enddatetime = enddatetime;
        this.courseid = courseid;
        this.absentStudents = absentStudents;
        this.presentStudents = presentStudents;
        this.noStatusStudents = noStatusStudents;
    }

    public LessonWithStudentsResource() {
    }

    public LocalDateTime getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(LocalDateTime startdatetime) {
        this.startdatetime = startdatetime;
    }

    public LocalDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(LocalDateTime enddatetime) {
        this.enddatetime = enddatetime;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

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

    public List<User> getNoStatusStudents() {
        return noStatusStudents;
    }

    public void setNoStatusStudents(List<User> noStatusStudents) {
        this.noStatusStudents = noStatusStudents;
    }
}
