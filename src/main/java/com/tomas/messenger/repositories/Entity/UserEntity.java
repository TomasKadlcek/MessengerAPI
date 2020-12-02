package com.tomas.messenger.repositories.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 555L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 40)
    private String userId;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String email;

    private String encryptedPassword;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<FriendEntity> friends;


    @OneToMany(mappedBy = "receiver")
    private List<MessageEntity> messagesReceived;

    @OneToMany(mappedBy = "sender")
    private List<MessageEntity> messagesSent;




    public List<MessageEntity> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(List<MessageEntity> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public List<MessageEntity> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<MessageEntity> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<FriendEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendEntity> friends) {
        this.friends = friends;
    }
}
