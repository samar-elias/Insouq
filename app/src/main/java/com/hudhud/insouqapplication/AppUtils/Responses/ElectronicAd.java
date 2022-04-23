package com.hudhud.insouqapplication.AppUtils.Responses;

import java.util.ArrayList;

public class ElectronicAd {
    private String status, id, title, enLocation, arLocation, postedDate, price, mainImage, enAge, arAge, enUsage, arUsage, enBrand, arBrand, enCondition, arCondition, description, lat, lng, userId, subCatArName, subCatEnName, otherSubCat, subTypeArName, subTypeEnName,
            otherSubType, phoneNumber, isFav, enColor, arColor, warranty, enVersion, arVersion, enRam, arRam, enStorage, arStorage;
    private ArrayList<Picture> pictures;

    public ElectronicAd() {
        status = "";
        this.id = "";
        this.title = "";
        this.enLocation = "";
        this.arLocation = "";
        this.postedDate = "";
        this.price = "";
        this.mainImage = "";
        this.enAge = "";
        this.arAge = "";
        this.enUsage = "";
        this.arUsage = "";
        this.enBrand = "null";
        this.arBrand = "null";
        this.enCondition = "null";
        this.arCondition = "null";
        this.description = "";
        this.lat = "";
        this.lng = "";
        this.userId = "";
        this.subCatArName = "";
        this.subCatEnName = "";
        this.otherSubCat = "";
        this.subTypeArName = "";
        this.subTypeEnName = "";
        this.otherSubType = "";
        this.phoneNumber = "";
        this.isFav = "";
        this.warranty = "null";
        this.enColor = "null";
        this.arColor = "null";
        this.enVersion = "";
        this.arVersion = "";
        this.enRam = "";
        this.arRam = "";
        this.enStorage = "";
        this.arStorage = "";
        this.pictures = new ArrayList<>();
    }

    public String getEnColor() {
        return enColor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEnColor(String enColor) {
        this.enColor = enColor;
    }

    public String getArColor() {
        return arColor;
    }

    public void setArColor(String arColor) {
        this.arColor = arColor;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getEnAge() {
        return enAge;
    }

    public void setEnAge(String enAge) {
        this.enAge = enAge;
    }

    public String getArAge() {
        return arAge;
    }

    public void setArAge(String arAge) {
        this.arAge = arAge;
    }

    public String getEnUsage() {
        return enUsage;
    }

    public void setEnUsage(String enUsage) {
        this.enUsage = enUsage;
    }

    public String getArUsage() {
        return arUsage;
    }

    public void setArUsage(String arUsage) {
        this.arUsage = arUsage;
    }

    public String getEnBrand() {
        return enBrand;
    }

    public void setEnBrand(String enBrand) {
        this.enBrand = enBrand;
    }

    public String getArBrand() {
        return arBrand;
    }

    public void setArBrand(String arBrand) {
        this.arBrand = arBrand;
    }

    public String getEnCondition() {
        return enCondition;
    }

    public void setEnCondition(String enCondition) {
        this.enCondition = enCondition;
    }

    public String getArCondition() {
        return arCondition;
    }

    public void setArCondition(String arCondition) {
        this.arCondition = arCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubCatArName() {
        return subCatArName;
    }

    public void setSubCatArName(String subCatArName) {
        this.subCatArName = subCatArName;
    }

    public String getSubCatEnName() {
        return subCatEnName;
    }

    public void setSubCatEnName(String subCatEnName) {
        this.subCatEnName = subCatEnName;
    }

    public String getOtherSubCat() {
        return otherSubCat;
    }

    public void setOtherSubCat(String otherSubCat) {
        this.otherSubCat = otherSubCat;
    }

    public String getSubTypeArName() {
        return subTypeArName;
    }

    public void setSubTypeArName(String subTypeArName) {
        this.subTypeArName = subTypeArName;
    }

    public String getSubTypeEnName() {
        return subTypeEnName;
    }

    public void setSubTypeEnName(String subTypeEnName) {
        this.subTypeEnName = subTypeEnName;
    }

    public String getOtherSubType() {
        return otherSubType;
    }

    public void setOtherSubType(String otherSubType) {
        this.otherSubType = otherSubType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnLocation() {
        return enLocation;
    }

    public void setEnLocation(String enLocation) {
        this.enLocation = enLocation;
    }

    public String getArLocation() {
        return arLocation;
    }

    public void setArLocation(String arLocation) {
        this.arLocation = arLocation;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getEnVersion() {
        return enVersion;
    }

    public void setEnVersion(String enVersion) {
        this.enVersion = enVersion;
    }

    public String getArVersion() {
        return arVersion;
    }

    public void setArVersion(String arVersion) {
        this.arVersion = arVersion;
    }

    public String getEnRam() {
        return enRam;
    }

    public void setEnRam(String enRam) {
        this.enRam = enRam;
    }

    public String getArRam() {
        return arRam;
    }

    public void setArRam(String arRam) {
        this.arRam = arRam;
    }

    public String getEnStorage() {
        return enStorage;
    }

    public void setEnStorage(String enStorage) {
        this.enStorage = enStorage;
    }

    public String getArStorage() {
        return arStorage;
    }

    public void setArStorage(String arStorage) {
        this.arStorage = arStorage;
    }
}
