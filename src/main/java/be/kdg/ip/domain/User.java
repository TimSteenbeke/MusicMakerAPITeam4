package be.kdg.ip.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by wouter on 30.01.17.
 */

@Entity
@Table
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name="UserId",nullable = false)
    private String id;

    @Column(name = "Username", nullable = true, length = 255)
    private String username;
    @Column (name = "FirstName",nullable = true,length = 255)
    private String firstname;
    @Column(name="LastName")
    private String lastname;
    @Column
    private String password;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Group> groups;

    @ManyToMany(/*targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER,*/ mappedBy = "users")
    private List<Role> roles;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;


    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private List<Course> teachescourses;

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;



    public User(){
        this.agenda = new Agenda();
    }

    public User(String username, String password, String firstname, String lastname, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.agenda = new Agenda();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public List<Course> getTeachescourses() {
        return teachescourses;
    }

    public void setTeachescourses(List<Course> teachescourses) {
        this.teachescourses = teachescourses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
