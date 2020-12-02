package com.tomas.messenger.repositories.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity(name = "friends")
public class FriendEntity implements Serializable {

    private static final long serialVersionUID = 555L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private long friendId;

    @Column(nullable = false, length = 100)
    private String friendEmail;

    // check
    @Transient
    private long lastMessageId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private UserEntity user;


    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(long lastMessageId) {
        this.lastMessageId = lastMessageId;
    }
}