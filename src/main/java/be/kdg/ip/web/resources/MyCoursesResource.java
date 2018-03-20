package be.kdg.ip.web.resources;

import be.kdg.ip.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesResource {
    List<Course> followCourses;
    List<Course> teachesCourses;

    public MyCoursesResource() {
        this.followCourses = new ArrayList<>();
        this.teachesCourses = new ArrayList<>();
    }

    public List<Course> getFollowCourses() {
        return followCourses;
    }

    public void setFollowCourses(List<Course> followCourses) {
        this.followCourses = followCourses;
    }

    public List<Course> getTeachesCourses() {
        return teachesCourses;
    }

    public void setTeachesCourses(List<Course> teachesCourses) {
        this.teachesCourses = teachesCourses;
    }
}
