package be.kdg.ip.web.resources;

import java.util.List;

public class CourseResource {
    private String coursebeschrijving;
    private List<Integer> teacherids;
    private int prijs;
    private List<Integer> studentids;

    public String getCoursebeschrijving() {
        return coursebeschrijving;
    }

    public void setCoursebeschrijving(String coursebeschrijving) {
        this.coursebeschrijving = coursebeschrijving;
    }

    public List<Integer> getTeacherids() {
        return teacherids;
    }

    public void setTeacherids(List<Integer> teacherids) {
        this.teacherids = teacherids;
    }

    public int getPrijs() {
        return prijs;
    }

    public void setPrijs(int prijs) {
        this.prijs = prijs;
    }

    public List<Integer> getStudentids() {
        return studentids;
    }

    public void setStudentids(List<Integer> studentids) {
        this.studentids = studentids;
    }
}
