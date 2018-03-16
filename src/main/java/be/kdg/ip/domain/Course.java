package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Course")
public class Course {
    @Id
    @Column
    @GeneratedValue
    private int courseId;

    @ManyToOne
    private CourseType courseType;

    @JsonIgnore
    @ManyToMany
    private List<User> teachers;

    @JsonIgnore
    @ManyToMany
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

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    public Course() {
        this.lessons = new ArrayList<Lesson>();
        this.students = new ArrayList<User>();
        this.teachers = new ArrayList<User>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
