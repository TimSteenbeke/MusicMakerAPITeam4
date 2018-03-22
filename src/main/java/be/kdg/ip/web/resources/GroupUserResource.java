package be.kdg.ip.web.resources;

import be.kdg.ip.domain.NewsItem;
import be.kdg.ip.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

public class GroupUserResource {

    private int groupid;

    private String name;
    @JsonIgnoreProperties({"password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","teachescourses","courses","instrumentLevels"})
    private User supervisor;
    @JsonIgnoreProperties({"password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","teachescourses","courses","instrumentLevels"})
    private List<User> users;
    @JsonIgnoreProperties({"password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired","teachescourses","courses","instrumentLevels"})
    private List<Integer> userids;
    @JsonIgnoreProperties({"groups"})
    private List<NewsItem> newsItems;
    private byte[] groupimage;

    public GroupUserResource() {
        this.users = new ArrayList<User>();
        this.userids = new ArrayList<Integer>();
        this.newsItems = new ArrayList<NewsItem>();
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public List<Integer> getUserids() {
        return userids;
    }

    public void setUserids(List<Integer> userids) {
        this.userids = userids;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public byte[] getGroupimage() {
        return groupimage;
    }

    public void setGroupimage(byte[] groupimage) {
        this.groupimage = groupimage;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }
}
