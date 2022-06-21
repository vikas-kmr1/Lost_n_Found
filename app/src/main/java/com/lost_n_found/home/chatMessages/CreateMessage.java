package com.lost_n_found.home.chatMessages;

public class CreateMessage {
    public String message = null;
    public String senderId = null;

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public CreateMessage(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public  CreateMessage()
    {

    }
}
