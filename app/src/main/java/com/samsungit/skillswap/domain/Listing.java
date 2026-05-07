package com.samsungit.skillswap.domain;

public class Listing {
    private long id;
    private User creator;
    private String Description;
    private String[] canTeach;
    private String[] wantsToLearn;

    public Listing(long id, User creator, String description) {
        this.id = id;
        this.creator = creator;
        Description = description;
    }

    public long getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public String getDescription() {
        return Description;
    }

    public String[] getCanTeach() {
        return canTeach;
    }

    public String[] getWantsToLearn() {
        return wantsToLearn;
    }
}
