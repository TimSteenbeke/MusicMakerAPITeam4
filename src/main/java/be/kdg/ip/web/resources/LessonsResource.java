package be.kdg.ip.web.resources;

import java.util.ArrayList;
import java.util.List;

public class LessonsResource {
    private List<LessonResource> lessonResources;

    public LessonsResource() {
        this.lessonResources = new ArrayList<LessonResource>();
    }

    public List<LessonResource> getLessonResources() {
        return lessonResources;
    }

    public void setLessonResources(List<LessonResource> lessonResources) {
        this.lessonResources = lessonResources;
    }
}
