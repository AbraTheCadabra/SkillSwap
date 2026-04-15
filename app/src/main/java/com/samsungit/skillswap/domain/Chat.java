package com.samsungit.sqlitedb.domain;

import com.samsungit.skillswap.domain.Message;

import java.util.List;
import java.util.Objects;

public class Chat {
    private long id;
    private String name;
    private List<Message> messages;

    public Chat(long id, String name, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.messages = messages;
    }

    public Chat(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id == chat.id && Objects.equals(name, chat.name) && Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, messages);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", messages=" + messages +
                '}';
    }
}
