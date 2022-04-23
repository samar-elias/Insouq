package com.hudhud.insouqapplication.AppUtils.Responses;

public class AdSubCategory {

    private String  arName, enName, categoryId, status;
    private int id;

    public AdSubCategory() {
        this.id = 0;
        this.arName = "";
        this.enName = "";
        this.categoryId = "";
        this.status = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
