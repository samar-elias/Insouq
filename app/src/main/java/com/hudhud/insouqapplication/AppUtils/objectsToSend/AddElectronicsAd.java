package com.hudhud.insouqapplication.AppUtils.objectsToSend;

import java.util.ArrayList;

public class AddElectronicsAd {

    private String title, description, location, lat, lan, mainPhoto, categoryId, age, usage, price, color, warranty, subCategoryId, otherSubCategory, subTypeId, otherSubTypeId, phoneNumber, version, ram, storage;
    private ArrayList<String> pictures;

    public AddElectronicsAd() {
        this.title = "";
        this.description = "";
        this.location = "";
        this.lat = "";
        this.lan = "";
        this.mainPhoto = "";
        this.categoryId = "";
        this.age = "";
        this.usage = "";
        this.price = "";
        this.color = "";
        this.warranty = "";
        this.subCategoryId = "";
        this.otherSubCategory = "";
        this.subTypeId = "";
        this.otherSubTypeId = "";
        this.phoneNumber = "";
        this.version = "";
        this.ram = "";
        this.storage = "";
        pictures = new ArrayList<>();
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
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

    public String getOtherSubTypeId() {
        return otherSubTypeId;
    }

    public void setOtherSubTypeId(String otherSubTypeId) {
        this.otherSubTypeId = otherSubTypeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
}
