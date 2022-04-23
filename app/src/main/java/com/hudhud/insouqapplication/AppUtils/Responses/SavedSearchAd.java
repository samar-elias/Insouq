package com.hudhud.insouqapplication.AppUtils.Responses;

public class SavedSearchAd {
    private String id, date, alert, emailAlert, categoryId, categoryAr, categoryEn, typeEn, typeAr, location, typeId;

    public SavedSearchAd() {
        this.id = "";
        this.alert = "";
        this.emailAlert = "";
        this.categoryAr = "";
        this.categoryEn = "";
        this.typeEn = "";
        this.typeAr = "";
        this.location = "";
        date = "";
        typeId = "";
        categoryId = "";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getEmailAlert() {
        return emailAlert;
    }

    public void setEmailAlert(String emailAlert) {
        this.emailAlert = emailAlert;
    }

    public String getCategoryAr() {
        return categoryAr;
    }

    public void setCategoryAr(String categoryAr) {
        this.categoryAr = categoryAr;
    }

    public String getCategoryEn() {
        return categoryEn;
    }

    public void setCategoryEn(String categoryEn) {
        this.categoryEn = categoryEn;
    }

    public String getTypeEn() {
        return typeEn;
    }

    public void setTypeEn(String typeEn) {
        this.typeEn = typeEn;
    }

    public String getTypeAr() {
        return typeAr;
    }

    public void setTypeAr(String typeAr) {
        this.typeAr = typeAr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
