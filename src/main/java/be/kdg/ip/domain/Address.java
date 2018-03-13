package be.kdg.ip.domain;

import javax.persistence.*;


@Entity
@Table
public class Address {

    @Id
    @GeneratedValue
    @Column(name="AddressId",nullable = false)
    private int id;

    @Column
    private String street;
    @Column
    private String streetNumber;
    @Column
    private String postalCode;
    @Column
    private String city;
    @Column
    private String country;



    public Address() {
    }

    public Address(String street, String streetNumber, String postalCode, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

}
