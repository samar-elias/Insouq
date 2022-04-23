package com.hudhud.insouqapplication.AppUtils.Responses;

public class SubCategory {
    int id, numberOfAds, status, typeId;
    String nameAr, nameEn, icon;

    public SubCategory() {
        this.id = 0;
        this.numberOfAds = 0;
        this.status = 0;
        this.typeId = 0;
        this.nameAr = "";
        this.nameEn = "";
        this.icon = "";
    }

    public SubCategory(int id, int numberOfAds, int status, int typeId, String nameAr, String nameEn, String icon) {
        this.id = id;
        this.numberOfAds = numberOfAds;
        this.status = status;
        this.typeId = typeId;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfAds() {
        return numberOfAds;
    }

    public void setNumberOfAds(int numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
