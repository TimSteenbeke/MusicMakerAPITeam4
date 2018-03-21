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
@Table(name ="tUser")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name="userId",nullable = false)
    private int id;

    @Column(name = "Username", nullable = true, length = 255)
    private String username;
    @Column (name = "FirstName",nullable = true,length = 255)
    private String firstname;
    @Column(name="LastName")
    private String lastname;
    @Column
    private String password;

    @ManyToMany
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private List<Course> teachescourses;

    @JsonIgnore
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @Lob
    @Column
    private byte[] userImage;

    @OneToOne
    private Address address;

    @ManyToMany
    private List<Composition> exercises;

    @ManyToMany
    private List<Composition> playList;

    @OneToMany
    private List<InstrumentLevel> instrumentLevels;


    public User(){
        this.groups = new ArrayList<>();
        this.courses= new ArrayList<>();
        this.roles = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.instrumentLevels = new ArrayList<>();
        this.teachescourses= new ArrayList<>();
        this.playList = new ArrayList<>();
    }

    public User(String firstname) {
        this.firstname = firstname;
        this.groups = new ArrayList<>();
        this.courses= new ArrayList<>();
        this.roles = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.instrumentLevels = new ArrayList<>();
        this.teachescourses= new ArrayList<>();
        this.playList = new ArrayList<>();
    }

    public User(String username, String password, String firstname, String lastname, List<Role> roles, byte[] userImage, Address address) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.groups = new ArrayList<>();
        this.userImage = userImage;
        this.address = address;
        this.exercises = new ArrayList<>();
        this.instrumentLevels = new ArrayList<>();
        this.teachescourses= new ArrayList<>();
        this.courses= new ArrayList<>();
        this.playList = new ArrayList<>();

    }

    public User(String username, String firstname, String lastname, String password, List<Role> roles) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.roles = roles;
        this.exercises = new ArrayList<>();
        this.instrumentLevels = new ArrayList<>();
        this.groups  = new ArrayList<>();
        this.teachescourses= new ArrayList<>();
        this.courses= new ArrayList<>();
        this.playList = new ArrayList<>();

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


    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Composition> getExercises() {
        return exercises;
    }

    public void setExercises(List<Composition> exercises) {
        this.exercises = exercises;
    }

    public List<InstrumentLevel> getInstrumentLevels() {
        return instrumentLevels;
    }

    public void setInstrumentLevels(List<InstrumentLevel> instrumentLevels) {
        this.instrumentLevels = instrumentLevels;
    }

    public List<Composition> getPlayList() {
        return playList;
    }

    public void setPlayList(List<Composition> playList) {
        this.playList = playList;
    }
}
