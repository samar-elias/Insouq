package com.hudhud.insouqapplication.AppUtils.Responses;

import java.util.ArrayList;

public class Search {
    private String id, title, enLocation, arLocation, postDate, status, categoryId, categoryEnName, categoryArName, subCategoryEnMotors, subCategoryArMotors, subCategoryEnService, subCategoryArService
            , subCategoryEnBusiness, subCategoryArBusiness, subCategoryEnClassifieds, subCategoryArClassifieds, subCategoryEnElectronics, subCategoryArElectronics, subTypeEnMotors, subTypeArMotors
            , subTypeEnClassifieds, subTypeArClassifieds, subTypeEnElectronics, subTypeArElectronics, enMaker, arMaker, enModel, arModel, typeId, price, enEmirate, arEmirate, enPlateType, arPlateType
            , plateCode, enMobileNumberPlan, arMobileNumberPlan, number, enOperator, arOperator, code, packageId;
    private ArrayList<Picture> pictures;

    public Search() {
        this.id = "";
        this.title = "";
        this.enLocation = "";
        this.arLocation = "";
        this.postDate = "";
        this.status = "";
        this.categoryId = "";
        this.categoryEnName = "";
        this.categoryArName = "";
        this.subCategoryEnMotors = "";
        this.subCategoryArMotors = "";
        this.subCategoryEnService = "";
        this.subCategoryArService = "";
        this.subCategoryEnBusiness = "";
        this.subCategoryArBusiness = "";
        this.subCategoryEnClassifieds = "";
        this.subCategoryArClassifieds = "";
        this.subCategoryEnElectronics = "";
        this.subCategoryArElectronics = "";
        this.subTypeEnMotors = "";
        this.subTypeArMotors = "";
        this.subTypeEnClassifieds = "";
        this.subTypeArClassifieds = "";
        this.subTypeEnElectronics = "";
        this.subTypeArElectronics = "";
        this.enMaker = "";
        this.arMaker = "";
        this.enModel = "";
        this.arModel = "";
        this.typeId = "";
        this.price = "";
        this.enEmirate = "";
        this.arEmirate = "";
        this.enPlateType = "";
        this.arPlateType = "";
        this.plateCode = "";
        this.enMobileNumberPlan = "";
        this.arMobileNumberPlan = "";
        this.number = "";
        this.enOperator = "";
        this.arOperator = "";
        this.code = "";
        this.packageId = "";
        this.pictures = new ArrayList<>();
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryEnName() {
        return categoryEnName;
    }

    public void setCategoryEnName(String categoryEnName) {
        this.categoryEnName = categoryEnName;
    }

    public String getCategoryArName() {
        return categoryArName;
    }

    public void setCategoryArName(String categoryArName) {
        this.categoryArName = categoryArName;
    }

    public String getSubCategoryEnMotors() {
        return subCategoryEnMotors;
    }

    public void setSubCategoryEnMotors(String subCategoryEnMotors) {
        this.subCategoryEnMotors = subCategoryEnMotors;
    }

    public String getSubCategoryArMotors() {
        return subCategoryArMotors;
    }

    public void setSubCategoryArMotors(String subCategoryArMotors) {
        this.subCategoryArMotors = subCategoryArMotors;
    }

    public String getSubCategoryEnService() {
        return subCategoryEnService;
    }

    public void setSubCategoryEnService(String subCategoryEnService) {
        this.subCategoryEnService = subCategoryEnService;
    }

    public String getSubCategoryArService() {
        return subCategoryArService;
    }

    public void setSubCategoryArService(String subCategoryArService) {
        this.subCategoryArService = subCategoryArService;
    }

    public String getSubCategoryEnBusiness() {
        return subCategoryEnBusiness;
    }

    public void setSubCategoryEnBusiness(String subCategoryEnBusiness) {
        this.subCategoryEnBusiness = subCategoryEnBusiness;
    }

    public String getSubCategoryArBusiness() {
        return subCategoryArBusiness;
    }

    public void setSubCategoryArBusiness(String subCategoryArBusiness) {
        this.subCategoryArBusiness = subCategoryArBusiness;
    }

    public String getSubCategoryEnClassifieds() {
        return subCategoryEnClassifieds;
    }

    public void setSubCategoryEnClassifieds(String subCategoryEnClassifieds) {
        this.subCategoryEnClassifieds = subCategoryEnClassifieds;
    }

    public String getSubCategoryArClassifieds() {
        return subCategoryArClassifieds;
    }

    public void setSubCategoryArClassifieds(String subCategoryArClassifieds) {
        this.subCategoryArClassifieds = subCategoryArClassifieds;
    }

    public String getSubCategoryEnElectronics() {
        return subCategoryEnElectronics;
    }

    public void setSubCategoryEnElectronics(String subCategoryEnElectronics) {
        this.subCategoryEnElectronics = subCategoryEnElectronics;
    }

    public String getSubCategoryArElectronics() {
        return subCategoryArElectronics;
    }

    public void setSubCategoryArElectronics(String subCategoryArElectronics) {
        this.subCategoryArElectronics = subCategoryArElectronics;
    }

    public String getSubTypeEnMotors() {
        return subTypeEnMotors;
    }

    public void setSubTypeEnMotors(String subTypeEnMotors) {
        this.subTypeEnMotors = subTypeEnMotors;
    }

    public String getSubTypeArMotors() {
        return subTypeArMotors;
    }

    public void setSubTypeArMotors(String subTypeArMotors) {
        this.subTypeArMotors = subTypeArMotors;
    }

    public String getSubTypeEnClassifieds() {
        return subTypeEnClassifieds;
    }

    public void setSubTypeEnClassifieds(String subTypeEnClassifieds) {
        this.subTypeEnClassifieds = subTypeEnClassifieds;
    }

    public String getSubTypeArClassifieds() {
        return subTypeArClassifieds;
    }

    public void setSubTypeArClassifieds(String subTypeArClassifieds) {
        this.subTypeArClassifieds = subTypeArClassifieds;
    }

    public String getSubTypeEnElectronics() {
        return subTypeEnElectronics;
    }

    public void setSubTypeEnElectronics(String subTypeEnElectronics) {
        this.subTypeEnElectronics = subTypeEnElectronics;
    }

    public String getSubTypeArElectronics() {
        return subTypeArElectronics;
    }

    public void setSubTypeArElectronics(String subTypeArElectronics) {
        this.subTypeArElectronics = subTypeArElectronics;
    }

    public String getEnMaker() {
        return enMaker;
    }

    public void setEnMaker(String enMaker) {
        this.enMaker = enMaker;
    }

    public String getArMaker() {
        return arMaker;
    }

    public void setArMaker(String arMaker) {
        this.arMaker = arMaker;
    }

    public String getEnModel() {
        return enModel;
    }

    public void setEnModel(String enModel) {
        this.enModel = enModel;
    }

    public String getArModel() {
        return arModel;
    }

    public void setArModel(String arModel) {
        this.arModel = arModel;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEnEmirate() {
        return enEmirate;
    }

    public void setEnEmirate(String enEmirate) {
        this.enEmirate = enEmirate;
    }

    public String getArEmirate() {
        return arEmirate;
    }

    public void setArEmirate(String arEmirate) {
        this.arEmirate = arEmirate;
    }

    public String getEnPlateType() {
        return enPlateType;
    }

    public void setEnPlateType(String enPlateType) {
        this.enPlateType = enPlateType;
    }

    public String getArPlateType() {
        return arPlateType;
    }

    public void setArPlateType(String arPlateType) {
        this.arPlateType = arPlateType;
    }

    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

    public String getEnMobileNumberPlan() {
        return enMobileNumberPlan;
    }

    public void setEnMobileNumberPlan(String enMobileNumberPlan) {
        this.enMobileNumberPlan = enMobileNumberPlan;
    }

    public String getArMobileNumberPlan() {
        return arMobileNumberPlan;
    }

    public void setArMobileNumberPlan(String arMobileNumberPlan) {
        this.arMobileNumberPlan = arMobileNumberPlan;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEnOperator() {
        return enOperator;
    }

    public void setEnOperator(String enOperator) {
        this.enOperator = enOperator;
    }

    public String getArOperator() {
        return arOperator;
    }

    public void setArOperator(String arOperator) {
        this.arOperator = arOperator;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }
}
