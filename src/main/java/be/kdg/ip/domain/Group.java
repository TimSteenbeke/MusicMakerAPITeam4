package be.kdg.ip.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tgroup") //name can't be group because it is a reserved keyword within the h2 database
public class Group {
    @Id
    @GeneratedValue
    @Column(nullable = false, name = "groupId")
    private int groupId;

    @Column
    private String name;

    @ManyToOne
    private User supervisor;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    @OneToMany(mappedBy = "group")
    private List<Performance> performances;

    @OneToMany(mappedBy = "group")
    private List<NewsItem> newsItems;

    @Lob
    @Column(nullable = true)
    private byte[] groupImage;


    public Group() {
        this.users = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.newsItems = new ArrayList<>();

    }

    public Group(String name, User supervisor, List<User> users) {
        this.name = name;
        this.supervisor = supervisor;
        this.users = users;
        this.performances = new ArrayList<>();
        this.newsItems = new ArrayList<>();
    }

    public byte[] getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(byte[] groupImage) {
        this.groupImage = groupImage;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
