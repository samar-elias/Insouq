package com.hudhud.insouqapplication.AppUtils.Responses;

public class Picture {

    private String id, imageURL;
    boolean isMain;

    public Picture() {
        this.id = "";
        this.imageURL = "";
        this.isMain = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
