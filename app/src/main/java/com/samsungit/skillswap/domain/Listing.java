package com.samsungit.skillswap.domain;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Listing {
    private long id;
    // private User creator;
    private String opId; // id of firebase user
    private String description;
    private List<String> canTeach;
    private List<String> wantToLearn;
    private String opName;
    private long timestamp;

//    public Listing(long id, User creator, String description) {
//        this.id = id;
//        this.creator = creator;
//        Description = description;
//    }


    public Listing(String opId, String description, List<String> canTeach, List<String> wantToLearn, long timestamp, String opName) {
        this.id = 0;
        this.opId = opId;
        this.description = description;
        this.canTeach = canTeach;
        this.wantToLearn = wantToLearn;
        this.timestamp = timestamp;
        this.opName = opName;
    }

    public Listing() {
    }

    public long getId() {
        return id;
    }

//    public User getCreator() {
//        return creator;
//    }

    public String getDescription() {
        return description;
    }

    public List<String> getCanTeach() {
        return canTeach;
    }

    public List<String> getWantToLearn() {
        return wantToLearn;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getOpId() {
        return opId;
    }

    public String getOpName() {
        return opName;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", opId='" + opId + '\'' +
                ", description='" + description + '\'' +
                ", canTeach=" + canTeach +
                ", wantToLearn=" + wantToLearn +
                ", timestamp=" + timestamp +
                '}';
    }
}
