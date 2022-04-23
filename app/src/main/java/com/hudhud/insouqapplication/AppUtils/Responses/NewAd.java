package com.hudhud.insouqapplication.AppUtils.Responses;

public class NewAd {
    String adId, image, title, price;

    public NewAd() {
        this.adId = "";
        this.image = "";
        this.title = "";
        this.price = "";
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
