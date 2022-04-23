package com.hudhud.insouqapplication.AppUtils.objectsToSend;

import java.util.ArrayList;

public class AddMechinaryAd {
    private String title, categoryId, subCategoryId, otherSubCategory, subTypeId, otherSubType, year, adId, description, location,
            latitude, longitude, horsepower, capacity, kilometers, warranty, fuelType, noOfCylinders, condition, sellerType, mechanicalCondition, price, phoneNumber, mainPhoto;
    private ArrayList<String> pictures;

    public AddMechinaryAd() {
        this.title = "";
        this.categoryId = "";
        this.subCategoryId = "";
        this.otherSubCategory = "";
        this.subTypeId = "";
        this.otherSubType = "";
        this.year = "";
        this.adId = "";
        this.description = "";
        this.location = "";
        this.latitude = "";
        this.longitude = "";
        this.horsepower = "";
        this.warranty = "";
        this.capacity = "";
        this.kilometers = "";
        this.fuelType = "";
        this.noOfCylinders = "";
        this.condition = "";
        this.sellerType = "";
        this.mechanicalCondition = "";
        this.price = "";
        this.phoneNumber = "";
        this.mainPhoto = "";
        this.pictures = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getNoOfCylinders() {
        return noOfCylinders;
    }

    public void setNoOfCylinders(String noOfCylinders) {
        this.noOfCylinders = noOfCylinders;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public String getMechanicalCondition() {
        return mechanicalCondition;
    }

    public void setMechanicalCondition(String mechanicalCondition) {
        this.mechanicalCondition = mechanicalCondition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
}
