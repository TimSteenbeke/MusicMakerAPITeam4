package be.kdg.ip.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tgroup") //name can't be group because it is a reserved keyword within the h2 database
public class Group {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column
    @Size(min = 4)
    private String name;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User supervisor;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    public Group(String name, User supervisor, List<User> users) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
