package be.kdg.ip.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name="UserId",nullable = false)
    private int id;

    @Column(name = "Username", nullable = true, length = 255)
    private String username;
    @Column (name = "FirstName",nullable = true,length = 255)
    private String firstname;
    @Column(name="LastName")
    private String lastname;
    @Column
    private String password;

    //@JsonIgnore
    @ManyToMany
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.EAGER)
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
        this.groups = new ArrayList<>();
        this.courses= new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    public User(String firstname) {
        this.firstname = firstname;
        this.agenda = new Agenda();
        this.groups = new ArrayList<>();
        this.courses= new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    public User(String username, String password, String firstname, String lastname, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.agenda = new Agenda();
        this.groups = new ArrayList<Group>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles){
            if(role.getRoleId() == 1) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            }else if(role.getRoleId()==2){
                authorities.add(new SimpleGrantedAuthority("TEACHER"));
            }else if(role.getRoleId()==3){
                authorities.add(new SimpleGrantedAuthority("STUDENT"));
            }
        }
        return authorities;
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
