package com.tomas.messenger.repositories.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "messages")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 555L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @ManyToOne
    @JoinColumn(name = "sender")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private UserEntity receiver;

    @Column(length = 100)
    private Timestamp timestamp;

    @Column(length = 5000)
    private String message;



    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
