package be.kdg.ip.web.resources;

import be.kdg.ip.domain.Role;

import java.util.List;

public class UserAddressDetailsResource {
    private int id;
    private String firstname;
    private String lastname;
    private String street;
    private String streetnumber;
    private String postalcode;
    private String city;
    private String country;
    private String username;
    private String userimage;
    private List<Role> roles;
    private List<Integer> roleids;

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetnumber() {
        return streetnumber;
    }

    public void setStreetnumber(String streetnumber) {
        this.streetnumber = streetnumber;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Integer> getRoleids() {
        return roleids;
    }

    public void setRoleids(List<Integer> roleids) {
        this.roleids = roleids;
    }
}
