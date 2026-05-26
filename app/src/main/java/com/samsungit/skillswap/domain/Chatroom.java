package com.samsungit.skillswap.domain;

import java.security.Timestamp;
import java.util.List;
import java.util.Map;

public class Chatroom {
    private String chatroomId;
    private Map<String, Boolean> users; // ?
    private Map<String, Message> messages;
    private String lastMessageSent;
    private String lastMessageSenderId;

    public Chatroom() {
    }

    public Chatroom(String chatroomId, Map<String, Boolean> users, Map<String, Message> messages, String lastMessageSent, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        this.users = users;
        this.messages = messages;
        this.lastMessageSent = lastMessageSent;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }


    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }

    public String getLastMessageSent() {
        return lastMessageSent;
    }

    public void setLastMessageSent(String lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
