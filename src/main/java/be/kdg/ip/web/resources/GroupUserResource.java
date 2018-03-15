package be.kdg.ip.web.resources;

import be.kdg.ip.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class GroupUserResource {

    private int groupid;

    private String name;
    @JsonIgnoreProperties({"username","lastname","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired"})
    private User supervisor;
    @JsonIgnoreProperties({"username","lastname","password","groups","roles","agenda","enabled","authorities","credentialsNonExpired","accountNonLocked","accountNonExpired"})
    private List<User> users;
    private List<Integer> userids;
    private byte[] groupimage;

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
}
