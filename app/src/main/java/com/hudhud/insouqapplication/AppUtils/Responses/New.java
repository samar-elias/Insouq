package com.hudhud.insouqapplication.AppUtils.Responses;

public class New {

    private Integer Sender;
    private String Message ;

    private Boolean isLiveForCustamer;

    private Boolean isLiveForOwner;

    private String MessageTime;

    private Integer seen;

    private String ChatId;

    private String MessageId;

    public New() {
    }

    public New(Integer sender, String message, Boolean isLiveForCustamer, Boolean isLiveForOwner, String messageTime, Integer seen, String chatId, String messageId) {
        Sender = sender;
        Message = message;
        this.isLiveForCustamer = isLiveForCustamer;
        this.isLiveForOwner = isLiveForOwner;
        MessageTime = messageTime;
        this.seen = seen;
        ChatId = chatId;
        MessageId = messageId;
    }

    public Integer getSender() {
        return Sender;
    }

    public void setSender(Integer sender) {
        Sender = sender;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Boolean getLiveForCustamer() {
        return isLiveForCustamer;
    }

    public void setLiveForCustamer(Boolean liveForCustamer) {
        isLiveForCustamer = liveForCustamer;
    }

    public Boolean getLiveForOwner() {
        return isLiveForOwner;
    }

    public void setLiveForOwner(Boolean liveForOwner) {
        isLiveForOwner = liveForOwner;
    }

    public String getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(String messageTime) {
        MessageTime = messageTime;
    }

    public Integer getSeen() {
        return seen;
    }

    public void setSeen(Integer seen) {
        this.seen = seen;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }
}
