package com.hudhud.insouqapplication.AppUtils.objectsToSend;

import java.util.ArrayList;

public class AddBusinessAd {
    private String title, description, location, latitude, longitude, mainPhoto, categoryId, otherCategoryName, phoneNumber, subCategoryId, otherSubCategoryName, price;
    ArrayList<String> pictures;

    public AddBusinessAd() {
        this.title = "";
        this.description = "";
        this.location = "";
        this.latitude = "";
        this.longitude = "";
        this.mainPhoto = "";
        this.categoryId = "";
        this.otherCategoryName = "";
        this.phoneNumber = "";
        this.subCategoryId = "";
        this.otherSubCategoryName = "";
        this.price = "";
        this.pictures = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getOtherCategoryName() {
        return otherCategoryName;
    }

    public void setOtherCategoryName(String otherCategoryName) {
        this.otherCategoryName = otherCategoryName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getOtherSubCategoryName() {
        return otherSubCategoryName;
    }

    public void setOtherSubCategoryName(String otherSubCategoryName) {
        this.otherSubCategoryName = otherSubCategoryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
}
