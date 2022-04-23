package com.hudhud.insouqapplication.AppUtils.objectsToSend;

import java.util.ArrayList;

public class AddClassifiedAd {
    private String title, categoryId, subCategoryId, otherSubCategory, subTypeId, otherSubType, adId, description, location, latitude, longitude, mainPhone, age, usage, brand, condition, phoneNumber, price;
    private ArrayList<String> pictures;

    public AddClassifiedAd() {
        this.title = "";
        this.categoryId = "";
        this.subCategoryId = "";
        this.otherSubCategory = "";
        this.subTypeId = "";
        this.otherSubType = "";
        this.adId = "";
        this.description = "";
        this.location = "";
        this.latitude = "";
        this.longitude = "";
        this.mainPhone = "";
        this.age = "";
        this.usage = "";
        this.brand = "";
        this.condition = "";
        this.phoneNumber = "";
        this.price = "";
        this.pictures = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getOtherSubCategory() {
        return otherSubCategory;
    }

    public void setOtherSubCategory(String otherSubCategory) {
        this.otherSubCategory = otherSubCategory;
    }

    public String getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getOtherSubType() {
        return otherSubType;
    }

    public void setOtherSubType(String otherSubType) {
        this.otherSubType = otherSubType;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
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

    public String getMainPhone() {
        return mainPhone;
    }

    public void setMainPhone(String mainPhone) {
        this.mainPhone = mainPhone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
