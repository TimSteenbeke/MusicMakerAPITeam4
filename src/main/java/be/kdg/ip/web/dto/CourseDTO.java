package be.kdg.ip.web.dto;

import be.kdg.ip.domain.CourseType;
import be.kdg.ip.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class CourseDTO {

    private CourseType courseType;
    @JsonIgnoreProperties({"username","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","address","exercises","instrumentLevels","userImage"})
    private List<User> teachers;
    @JsonIgnoreProperties({"username","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","address","exercises","instrumentLevels","userImage"})
    private List<User> students;

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

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
