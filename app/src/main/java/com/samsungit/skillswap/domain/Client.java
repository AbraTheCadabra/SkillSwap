package com.samsungit.skillswap.domain;

import java.util.List;

import java.util.Objects;

public class Client {
    private long id;
    private String login;
    private String password;

    private List<Chat> chats;
    private List<Client> friends;
    // private List<Listing> listings;

    public Client(long id, String login, String password, List<Chat> chats, List<Client> friends) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.chats = chats;
        this.friends = friends;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Client(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public List<Client> getFriends() {
        return friends;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(login, client.login) && Objects.equals(password, client.password) && Objects.equals(chats, client.chats) && Objects.equals(friends, client.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, chats, friends);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", chats=" + chats +
                ", friends=" + friends +
                '}';
    }
}
