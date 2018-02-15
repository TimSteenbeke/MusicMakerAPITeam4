package be.kdg.ip.domain;

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
    @Column
    private String Beschrijving;
    @Column
    private int prijs;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    public Course() {
        this.lessons = new ArrayList<Lesson>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        Beschrijving = beschrijving;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
