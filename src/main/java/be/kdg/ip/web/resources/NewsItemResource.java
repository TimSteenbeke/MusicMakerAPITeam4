package be.kdg.ip.web.resources;

import java.util.List;

public class NewsItemResource {
    private String message;
    private String editor;
    private String date;
    private String messageImage;
    private String title;
    private List<Integer> groupids;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }

    public List<Integer> getGroupids() {
        return groupids;
    }

    public void setGroupids(List<Integer> groupids) {
        this.groupids = groupids;
    }
}
