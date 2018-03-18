package be.kdg.ip.web.resources;

import java.util.ArrayList;
import java.util.List;

public class LessonsResource {
    private List<LessonWithStudentsResource> lessonResources;

    public LessonsResource() {
        this.lessonResources = new ArrayList<LessonWithStudentsResource>();
    }

    public List<LessonWithStudentsResource> getLessonResources() {
        return lessonResources;
    }

    public void setLessonResources(List<LessonWithStudentsResource> lessonResources) {
        this.lessonResources = lessonResources;
    }
}
