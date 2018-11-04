package com.wish.brachio.wishlist.model;

import java.util.HashMap;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private HashMap<String, User> friends;

    public User(){}

    public User(String f, String l, String e){
        firstName = f;
        lastName = l;
        email = e;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public HashMap<String, User> getFriends() {
        return friends;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFriends(HashMap<String, User> friends) {
        this.friends = friends;
    }

    public boolean isNull() {
        return (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty());
    }
}
