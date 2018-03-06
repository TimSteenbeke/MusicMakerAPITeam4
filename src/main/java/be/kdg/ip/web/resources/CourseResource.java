package be.kdg.ip.web.resources;

import java.util.List;

public class CourseResource {
    private String coursedescription;
    private List<Integer> teacherids;
    private int price;
    private List<Integer> studentids;

    public String getCoursedescription() {
        return coursedescription;
    }

    public void setCoursedescription(String coursedescription) {
        this.coursedescription = coursedescription;
    }

    public List<Integer> getTeacherids() {
        return teacherids;
    }

    public void setTeacherids(List<Integer> teacherids) {
        this.teacherids = teacherids;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getStudentids() {
        return studentids;
    }

    public void setStudentids(List<Integer> studentids) {
        this.studentids = studentids;
    }
}
