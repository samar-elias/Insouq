package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics;

public class ElectronicSpecificationModel {

    private String specificationTitle, specificationValue;

    public ElectronicSpecificationModel(String specificationTitle, String specificationValue) {
        this.specificationTitle = specificationTitle;
        this.specificationValue = specificationValue;
    }

    public String getSpecificationTitle() {
        return specificationTitle;
    }

    public String getSpecificationValue() {
        return specificationValue;
    }
}
