package com.samsungit.skillswap.domain;

import android.widget.ImageView;

import com.samsungit.skillswap.R;

import java.util.List;
import java.util.Objects;


public class User {
    private long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private List<Chat> chats;
    private List<Listing> listings;
    private List<User> friends;
    private int pfp;

    public User(long id, String name, String lastName, List<Chat> chats) {
        this.name = name;
        this.pfp = R.drawable.account_icon;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.chats = chats;
    }

    public User(long id, String login, String name) {
        this.name = name;
        this.pfp = R.drawable.account_icon;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public int getPfp() {
        return pfp;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public String getPassword() {
        return password;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public List<User> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", chats=" + chats +
                ", listings=" + listings +
                ", friends=" + friends +
                ", pfp=" + pfp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && pfp == user.pfp && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(chats, user.chats) && Objects.equals(listings, user.listings) && Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, lastName, chats, listings, friends, pfp);
    }
}