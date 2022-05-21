package com.hudhud.insouqapplication.AppUtils.Responses;

public class ServiceAd {
    private String status, id, agentId, title, enLocation, arLocation, postedDate, categoryArName, categoryEnName, description, lat, lng, categoryId, userId, serviceTypeEnName, serviceTypeArName, otherServiceType, carLiftFrom, carLiftTo, phoneNumber, isFav;

    public ServiceAd() {
        status = "";
        this.id = "";
        this.agentId = "";
        this.title = "";
        this.enLocation = "";
        this.arLocation = "";
        this.postedDate = "";
        this.categoryArName = "";
        this.categoryEnName = "";
        this.description = "";
        this.lat = "";
        this.lng = "";
        this.categoryId = "";
        this.userId = "";
        this.serviceTypeEnName = "";
        this.serviceTypeArName = "";
        this.otherServiceType = "";
        this.carLiftFrom = "";
        this.carLiftTo = "";
        this.phoneNumber = "";
        this.isFav = "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceTypeEnName() {
        return serviceTypeEnName;
    }

    public void setServiceTypeEnName(String serviceTypeEnName) {
        this.serviceTypeEnName = serviceTypeEnName;
    }

    public String getServiceTypeArName() {
        return serviceTypeArName;
    }

    public void setServiceTypeArName(String serviceTypeArName) {
        this.serviceTypeArName = serviceTypeArName;
    }

    public String getOtherServiceType() {
        return otherServiceType;
    }

    public void setOtherServiceType(String otherServiceType) {
        this.otherServiceType = otherServiceType;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
