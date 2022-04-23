package com.hudhud.insouqapplication.AppUtils.Responses;

import java.io.Serializable;
import java.util.ArrayList;

public class BusinessAd implements Serializable {
    private String status, id, mainImage, title, enLocation, arLocation, price, postedDate, description, lat, lng, categoryId, categoryArName, categoryEnName, otherCategoryNAme, subCategoryId, subCategoryArName, subCategoryEnName, otherSubCategoryNAme, userId, phoneNumber, isFav;
    private ArrayList<Picture> pictures;;

    public BusinessAd() {
        status = "";
        this.id = "";
        this.mainImage = "";
        this.title = "";
        this.enLocation = "";
        this.arLocation = "";
        this.price = "";
        this.postedDate = "";
        this.description = "";
        this.lat = "";
        this.lng = "";
        this.categoryId = "";
        this.categoryArName = "";
        this.categoryEnName = "";
        this.otherCategoryNAme = "";
        this.subCategoryId = "";
        this.subCategoryArName = "";
        this.subCategoryEnName = "";
        this.otherSubCategoryNAme = "";
        this.userId = "";
        this.phoneNumber = "";
        this.isFav = "";
        this.pictures = new ArrayList<>();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCategoryArName() {
        return categoryArName;
    }

    public void setCategoryArName(String categoryArName) {
        this.categoryArName = categoryArName;
    }

    public String getCategoryEnName() {
        return categoryEnName;
    }

    public void setCategoryEnName(String categoryEnName) {
        this.categoryEnName = categoryEnName;
    }

    public String getOtherCategoryNAme() {
        return otherCategoryNAme;
    }

    public void setOtherCategoryNAme(String otherCategoryNAme) {
        this.otherCategoryNAme = otherCategoryNAme;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
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

    public String getSubCategoryArName() {
        return subCategoryArName;
    }

    public void setSubCategoryArName(String subCategoryArName) {
        this.subCategoryArName = subCategoryArName;
    }

    public String getSubCategoryEnName() {
        return subCategoryEnName;
    }

    public void setSubCategoryEnName(String subCategoryEnName) {
        this.subCategoryEnName = subCategoryEnName;
    }

    public String getOtherSubCategoryNAme() {
        return otherSubCategoryNAme;
    }

    public void setOtherSubCategoryNAme(String otherSubCategoryNAme) {
        this.otherSubCategoryNAme = otherSubCategoryNAme;
    }
}
