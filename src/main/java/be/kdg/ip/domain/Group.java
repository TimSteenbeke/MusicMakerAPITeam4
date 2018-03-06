package be.kdg.ip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tgroup") //groupName can't be group because it is a reserved keyword within the h2 database
public class Group {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int groupId;

    @Column
    @Size(min = 4)
    private String groupName;

    @ManyToOne
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User supervisor;

    @JsonIgnore
    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    //@JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<Performance> performances;

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public Group(String name, User supervisor, List<User> users) {
        this.groupName = name;
        this.supervisor = supervisor;
        this.users = users;
    }

    public Group() {
        this.users = new ArrayList<User>();

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
