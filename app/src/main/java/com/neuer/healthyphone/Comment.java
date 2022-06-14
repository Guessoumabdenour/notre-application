package com.neuer.healthyphone;


import com.google.firebase.database.ServerValue;

public class Comment {
    private String Comment,UID,UName,Time;

    public Comment() {

    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Comment(String comment, String UID, String UName) {
        Comment = comment;
        this.UID = UID;
        this.UName = UName;
    }



    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }



    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }


}
