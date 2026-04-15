package com.samsungit.skillswap.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private long id;
    private String text;
    private long userIdSender;
    private Timestamp timeSent;

    public Message(long id, String text, long user_id_sender, Timestamp time_sent) {
        this.id = id;
        this.text = text;
        this.userIdSender = user_id_sender;
        this.timeSent = time_sent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && userIdSender == message.userIdSender && Objects.equals(text, message.text) && Objects.equals(timeSent, message.timeSent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, userIdSender, timeSent);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user_id_sender=" + userIdSender +
                ", time_sent=" + timeSent +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getUserIdSender() {
        return userIdSender;
    }

    public Timestamp getTimeSent() {
        return timeSent;
    }
}
