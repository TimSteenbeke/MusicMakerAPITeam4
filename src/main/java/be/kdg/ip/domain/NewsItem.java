package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="newsitem")
public class NewsItem {
    @Id
    @GeneratedValue
    @Column(name="newsItemId",nullable = false)
    private int newsItemId;

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private String editor;

    @JsonIgnore
    @ManyToMany
    private List<Group> groups;

    @Column
    private Date date;

    @Lob
    @Column(nullable = true)
    private byte[] messageImage;

    public NewsItem(){

    }

    public NewsItem(String title, String message, String editor, Date date){
        this.title = title;
        this.message = message;
        this.editor = editor;
        this.date = date;
        this.groups = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNewsItemId() {
        return newsItemId;
    }

    public void setNewsItemId(int newsItemId) {
        this.newsItemId = newsItemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(byte[] messageImage) {
        this.messageImage = messageImage;
    }
}
