package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="newsitem")
public class NewsItem {
    @Id
    @GeneratedValue
    @Column(name="NewsItemId",nullable = false)
    private int NewsItemId;

    @Column
    private String title;

    @Column
    private String message;

    @Column
    private String editor;

    @JsonIgnore
    @ManyToOne
    private Group group;

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
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNewsItemId() {
        return NewsItemId;
    }

    public void setNewsItemId(int newsItemId) {
        NewsItemId = newsItemId;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
