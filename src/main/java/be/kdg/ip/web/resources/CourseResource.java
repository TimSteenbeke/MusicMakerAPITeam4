package be.kdg.ip.web.resources;

import java.util.List;

public class CourseResource {

    private int courseTypeId;
    private List<Integer> teacherids;
    private List<Integer> studentids;


    public List<Integer> getTeacherids() {
        return teacherids;
    }

    public void setTeacherids(List<Integer> teacherids) {
        this.teacherids = teacherids;
    }

    public List<Integer> getStudentids() {
        return studentids;
    }

    public void setStudentids(List<Integer> studentids) {
        this.studentids = studentids;
    }

    public int getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(int courseTypeId) {
        this.courseTypeId = courseTypeId;
    }
}
