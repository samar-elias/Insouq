package com.hudhud.insouqapplication.AppUtils.Responses;

import java.util.List;

public class Message {

    private Integer Sender;

    private String Message;

    private Boolean isLiveForCustamer;

    private Boolean isLiveForOwner ;

    private String MessageTime;

    private Integer seen;// 1 seen // 2 not seen

    private String ChatId;

    private String MessageId ;
    private New aNew;
    private List<String> LocURL;
    public List<String> files;
    private String image;


    public Message() {
    }
    public Message(New aNew) {
        this.aNew=aNew;
    }

    public Message(Integer sender, String message, Boolean isLiveForCustamer, Boolean isLiveForOwner, String messageTime, Integer seen, String chatId, String messageId, List<String> LocURL, List<String> files) {
        Sender = sender;
        Message = message;
        this.isLiveForCustamer = isLiveForCustamer;
        this.isLiveForOwner = isLiveForOwner;
        MessageTime = messageTime;
        this.seen = seen;
        ChatId = chatId;
        MessageId = messageId;

        this.LocURL=LocURL;
        this.files=files;
    }
    public Message(Integer sender, String message, Boolean isLiveForCustamer, Boolean isLiveForOwner, String messageTime, Integer seen, String chatId, String messageId, List<String> LocURL, List<String> files,String image) {
        Sender = sender;
        Message = message;
        this.isLiveForCustamer = isLiveForCustamer;
        this.isLiveForOwner = isLiveForOwner;
        MessageTime = messageTime;
        this.seen = seen;
        ChatId = chatId;
        MessageId = messageId;

        this.LocURL=LocURL;
        this.files=files;
        this.image=image;
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

    public New getaNew() {
        return aNew;
    }

    public void setaNew(New aNew) {
        this.aNew = aNew;
    }

    public List<String> getLocURL() {
        return LocURL;
    }

    public void setLocURL(List<String> locURL) {
        LocURL = locURL;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
