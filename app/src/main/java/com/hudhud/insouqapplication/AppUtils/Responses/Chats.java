package com.hudhud.insouqapplication.AppUtils.Responses;

public class Chats {

    private Integer AdId;
    private Integer WhoArchive;
    private Integer WhoBlock;
    private Integer CustumerUserId;
    private Message Message;
    private String LastUpdate;
    private Boolean isLiveForCustamer;
    private String lastMessage;
    private String ChatId;
    private Integer OwnerUserId;
    private Boolean isLiveForOwner;
    private String AdMainImage;
    private Integer status;
    private String type;
    private String description;
    private String title;
    private String price;
    private String userImage;
    private String firstName;
    private String lastName;
    private String casomerUserName;

    public Chats() {
    }

    public Chats(Integer AdId, Integer WhoArchive, Integer WhoBlock, Integer CustumerUserId, Message message, String LastUpdate, Boolean isLiveForCustamer, String lastMessage, String chatId, Integer OwnerUserId, Boolean isLiveForOwner, String AdMainImage,
                 Integer status, String type, String description, String title, String price, String userImage, String firstName, String lastName, String casomerUserName) {
        this.AdId = AdId;
        this.WhoArchive = WhoArchive;
        this.WhoBlock = WhoBlock;
        this.CustumerUserId = CustumerUserId;
        this.Message = message;
        this.LastUpdate = LastUpdate;
        this.isLiveForCustamer = isLiveForCustamer;
        this.lastMessage = lastMessage;
        this.ChatId = chatId;
        this.OwnerUserId = OwnerUserId;
        this.isLiveForOwner = isLiveForOwner;
        this.AdMainImage = AdMainImage;
        this.status = status;
        this.description = description;
        this.title = title;
        this.type = type;
        this.price = price;
        this.userImage=userImage;
        this.firstName=firstName;
        this.lastName=lastName;
        this.casomerUserName=casomerUserName;


    }


    public Integer getAdId() {
        return AdId;
    }

    public void setAdId(Integer adId) {
        AdId = adId;
    }

    public Integer getWhoArchive() {
        return WhoArchive;
    }

    public void setWhoArchive(Integer whoArchive) {
        WhoArchive = whoArchive;
    }

    public Integer getWhoBlock() {
        return WhoBlock;
    }

    public void setWhoBlock(Integer whoBlock) {
        WhoBlock = whoBlock;
    }

    public Integer getCustumerUserId() {
        return CustumerUserId;
    }

    public void setCustumerUserId(Integer custumerUserId) {
        CustumerUserId = custumerUserId;
    }

    public Message getMessage() {
        return Message;
    }

    public void setMessage(Message message) {
        Message = message;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public Boolean getLiveForCustamer() {
        return isLiveForCustamer;
    }

    public void setLiveForCustamer(Boolean liveForCustamer) {
        isLiveForCustamer = liveForCustamer;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public Integer getOwnerUserId() {
        return OwnerUserId;
    }

    public void setOwnerUserId(Integer ownerUserId) {
        OwnerUserId = ownerUserId;
    }

    public Boolean getLiveForOwner() {
        return isLiveForOwner;
    }

    public void setLiveForOwner(Boolean liveForOwner) {
        isLiveForOwner = liveForOwner;
    }

    public String getAdMainImage() {
        return AdMainImage;
    }

    public void setAdMainImage(String adMainImage) {
        AdMainImage = adMainImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
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

    public String getCasomerUserName() {
        return casomerUserName;
    }

    public void setCasomerUserName(String casomerUserName) {
        this.casomerUserName = casomerUserName;
    }
}
