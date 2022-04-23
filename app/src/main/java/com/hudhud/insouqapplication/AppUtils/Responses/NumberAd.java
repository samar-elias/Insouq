package com.hudhud.insouqapplication.AppUtils.Responses;

import java.io.Serializable;

public class NumberAd implements Serializable {
    private String status, id, enMobilePlan, arMobilePlan, userId, description, arOperator, title, lat, lng, enLocation, arLocation, postDate, price, arEmirate, arPlateType, emirate, plateType, plateCode, category, number, operator, code, phoneNumber, isFav;

    public NumberAd() {
        status = "";
        this.id = "";
        this.title = "";
        this.enLocation = "";
        this.arLocation = "";
        this.postDate = "";
        this.price = "";
        this.emirate = "";
        this.plateType = "";
        this.plateCode = "";
        this.category = "";
        this.number = "";
        this.operator = "";
        this.arOperator = "";
        this.code = "";
        this.phoneNumber = "";
        this.isFav = "";
        this.description = "";
        this.userId = "";
        this.lat = "";
        this.lng = "";
        this.arEmirate = "";
        this.arPlateType = "";
        this.arMobilePlan = "";
        this.enMobilePlan = "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnMobilePlan() {
        return enMobilePlan;
    }

    public void setEnMobilePlan(String enMobilePlan) {
        this.enMobilePlan = enMobilePlan;
    }

    public String getArMobilePlan() {
        return arMobilePlan;
    }

    public void setArMobilePlan(String arMobilePlan) {
        this.arMobilePlan = arMobilePlan;
    }

    public String getArOperator() {
        return arOperator;
    }

    public void setArOperator(String arOperator) {
        this.arOperator = arOperator;
    }

    public String getArEmirate() {
        return arEmirate;
    }

    public void setArEmirate(String arEmirate) {
        this.arEmirate = arEmirate;
    }

    public String getArPlateType() {
        return arPlateType;
    }

    public void setArPlateType(String arPlateType) {
        this.arPlateType = arPlateType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEmirate() {
        return emirate;
    }

    public void setEmirate(String emirate) {
        this.emirate = emirate;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
