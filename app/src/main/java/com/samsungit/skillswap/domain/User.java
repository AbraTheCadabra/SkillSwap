package com.samsungit.skillswap.domain;

import com.samsungit.skillswap.R;

import java.util.List;
import java.util.Objects;


public class User {
    public String userId;
    private String email;
    private String name;
    private String lastName;
    private int pfp;

    public User() {
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPfp() {
        return pfp;
    }

    public void setPfp(int pfp) {
        this.pfp = pfp;
    }

    public String getFullName() {
        return name + " " + lastName;
    }


}