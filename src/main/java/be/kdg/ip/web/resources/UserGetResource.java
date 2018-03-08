package be.kdg.ip.web.resources;

import java.util.List;

public class UserGetResource {
    private List<UserDetailsResource> users;

    public List<UserDetailsResource> getUsers() {
        return users;
    }

    public void setUsers(List<UserDetailsResource> users) {
        this.users = users;
    }
}
