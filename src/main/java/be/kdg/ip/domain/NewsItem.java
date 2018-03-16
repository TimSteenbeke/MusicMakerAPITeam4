package be.kdg.ip.domain;

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

    @ManyToOne
    @JoinColumn(name = "userId")
    private User editor;

    @Column
    private Date date;

    @Lob
    @Column(nullable = true)
    private byte[] messageImage;

    public NewsItem(){

    }

    public NewsItem(String title, String message, User editor, Date date){
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

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
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
