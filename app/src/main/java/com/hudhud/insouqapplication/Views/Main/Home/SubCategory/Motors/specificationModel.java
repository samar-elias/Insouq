package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors;

public class specificationModel {

    private String specificationTitle, specificationValue;
    private int image;

    public specificationModel( String specificationTitle, String specificationValue) {
        this.specificationTitle = specificationTitle;
        this.specificationValue = specificationValue;
        this.image = 0;
    }

    public specificationModel(String specificationTitle, String specificationValue, int image) {
        this.specificationTitle = specificationTitle;
        this.specificationValue = specificationValue;
        this.image = image;
    }

    public String getSpecificationTitle() {
        return specificationTitle;
    }

    public String getSpecificationValue() {
        return specificationValue;
    }

    public int getImage() {
        return image;
    }
}
