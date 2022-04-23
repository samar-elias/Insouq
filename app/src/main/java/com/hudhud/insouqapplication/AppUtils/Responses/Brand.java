package com.hudhud.insouqapplication.AppUtils.Responses;

public class Brand {
    private String id, titleAr, titleEn;

    public Brand() {
        this.id = "";
        this.titleAr = "";
        this.titleEn = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }
}
