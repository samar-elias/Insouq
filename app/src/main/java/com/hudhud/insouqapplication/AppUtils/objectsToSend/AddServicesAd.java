package com.hudhud.insouqapplication.AppUtils.objectsToSend;

public class AddServicesAd {
    private String title, description, location, latitude, longitude, categoryId, subCategoryId, otherSubCategory, phoneNumber, carLiftFrom, carLiftTo;

    public AddServicesAd() {
        this.title = "";
        this.description = "";
        this.location = "";
        this.latitude = "";
        this.longitude = "";
        this.categoryId = "";
        this.subCategoryId = "";
        this.otherSubCategory = "";
        this.phoneNumber = "";
        this.carLiftFrom = "";
        this.carLiftTo = "";
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarLiftFrom() {
        return carLiftFrom;
    }

    public void setCarLiftFrom(String carLiftFrom) {
        this.carLiftFrom = carLiftFrom;
    }

    public String getCarLiftTo() {
        return carLiftTo;
    }

    public void setCarLiftTo(String carLiftTo) {
        this.carLiftTo = carLiftTo;
    }
}
