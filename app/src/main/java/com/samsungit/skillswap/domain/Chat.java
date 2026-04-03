package com.samsungit.skillswap.domain;

import java.security.Timestamp;
import java.util.List;

public class Chat {
    private long id;
    private List<Message> messages;
    private Timestamp createdTime;
    private String name;
    private List<Integer> userIds;

}
