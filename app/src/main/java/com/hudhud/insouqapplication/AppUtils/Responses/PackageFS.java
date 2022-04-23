package com.hudhud.insouqapplication.AppUtils.Responses;

public class PackageFS {
    private String id, days, price;

    public PackageFS() {
        this.id = "";
        this.days = "";
        this.price = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
