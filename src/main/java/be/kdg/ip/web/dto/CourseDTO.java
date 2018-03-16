package be.kdg.ip.web.dto;

import be.kdg.ip.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class CourseDTO {

    private int courseTypeId;
    private String description;
    @JsonIgnoreProperties({"username","lastname","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired"})
    private List<User> teachers;
    @JsonIgnoreProperties({"username","lastname","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired"})
    private List<User> students;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers = teachers;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public int getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }
}
