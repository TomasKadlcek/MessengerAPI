package com.tomas.messenger.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tomas.messenger.repositories.Entity.MessageEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class FriendDTO implements Serializable {

    private static final long serialVersionUID = 555L;

    private long id;

    private String friendEmail;

    private String firstName;

    private String lastName;

    private String friendUserId;




    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }
}
