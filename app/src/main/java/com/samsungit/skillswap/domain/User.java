package com.samsungit.skillswap.domain;

import java.util.List;
import java.util.Objects;
import com.samsungit.skillswap.domain.Chat;

public class User {
    private long id;
    private String login;
    private List<Chat> chats;


    public User(long id, String login, List<Chat> chats) {
        this.id = id;
        this.login = login;
        this.chats = chats;
    }

    public User(long id, String login) {
        this.id = id;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Chat> getChats() {
        return chats;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", chats=" + chats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(chats, user.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, chats);
    }
}