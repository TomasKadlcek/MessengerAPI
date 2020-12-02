package com.tomas.messenger.shared;

import com.tomas.messenger.repositories.Entity.FriendEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 555L;

    private String message;
    private Timestamp timestamp;
    private String senderUserId;
    private String receiverUserId;
    private String senderName;
    private String receiverName;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
