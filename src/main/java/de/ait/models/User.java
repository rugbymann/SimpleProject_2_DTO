package de.ait.models;

public class User {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String street;
    private String houseNumber;

    public User(String id, String email, String password, String firstName, String lastName,
                String phoneNumber, String street, String houseNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public User(String email, String password,
                String firstName, String lastName,
                String phoneNumber,
                String street, String houseNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getStreet() {
        return street;
    }
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return id + " " + email + " " + password + " " + firstName + " " + lastName + " " + phoneNumber + " " + street + " " + houseNumber;
    }
}
