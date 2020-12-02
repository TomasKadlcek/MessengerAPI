package com.tomas.messenger.presentation.model.request;

public class MessageDetailsRequest {

    private String receiverUserId;
    private String message;


    public String getReceiverUserIdId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverId) {
        this.receiverUserId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
