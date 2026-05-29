package com.samsungit.skillswap.domain;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Listing {
    private String id;
    private String opId; // id of firebase user
    private String description;
    private List<String> canTeach;
    private List<String> wantToLearn;
    private String opName;
    private long timestamp;


    public Listing(String id, String opId, String description, List<String> canTeach, List<String> wantToLearn, String opName, long timestamp) {
        this.id = id;
        this.opId = opId;
        this.description = description;
        this.canTeach = canTeach;
        this.wantToLearn = wantToLearn;
        this.opName = opName;
        this.timestamp = timestamp;
    }

    public Listing() {
    }


    public void setOpId(String opId) {
        this.opId = opId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCanTeach(List<String> canTeach) {
        this.canTeach = canTeach;
    }

    public void setWantToLearn(List<String> wantToLearn) {
        this.wantToLearn = wantToLearn;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
