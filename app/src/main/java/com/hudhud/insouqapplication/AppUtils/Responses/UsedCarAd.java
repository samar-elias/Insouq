package com.hudhud.insouqapplication.AppUtils.Responses;

import java.io.Serializable;
import java.util.ArrayList;

public class UsedCarAd implements Serializable {
    private String id, chats, views, title, description, enLocation, arLocation, latitude, longitude, status, postDate, categoryId, categoryEnName, categoryArName,
            subCategoryId, subCategoryEnName, subCategoryArName, otherSubCategory, subTypeId, subTypeEnName, subTypeArName, otherSubType, typeId, userId, agentId, getCategoryEnName,
            enMaker, arMaker, enModel, arModel, enTrim, arTrim, enColor, arColor, kiloMeters, year, enDoors, arDoors, warranty, enTransmission, arTransmission, enRegionalSpecs, arRegionalSpecs, enBodyType,
            arBodyType, enFuelType, arFuelType, enNoOfCylinders, arNoOfCylinders, enHorsepower, arHorsepower, enCondition, arCondition, enMechanicalCondition, arMechanicalCondition
            , enCapacity, arCapacity, enEngineSize, arEngineSize, enAge, arAge, enUsage, arUsage, enLength, arLength, enWheels, arWheels, enSellerType
            , arSellerType, enDriveSystem, arDriveSystem, enPartName, arPartName, nameOfPart, price, userImage, phoneNumber, enSteeringSide, arSteeringSide, mainImage, isFav;
    private ArrayList<Picture> pictures;

    public UsedCarAd() {
        this.id = "";
        this.enCondition = "";
        this.arCondition = "";
        this.enMechanicalCondition = "";
        this.arMechanicalCondition = "";
        this.enCapacity = "";
        this.arCapacity = "";
        this.enEngineSize = "";
        this.arEngineSize = "";
        this.enAge = "";
        this.arAge = "";
        this.enUsage = "";
        this.arUsage = "";
        this.enLength = "";
        this.arLength = "";
        this.enWheels = "";
        this.arWheels = "";
        this.enSellerType = "";
        this.arSellerType = "";
        this.enDriveSystem = "";
        this.arDriveSystem = "";
        this.enPartName = "";
        this.arPartName = "";
        this.nameOfPart = "";
        this.chats = "";
        this.views = "";
        this.title = "";
        this.description = "";
        this.enLocation = "";
        this.arLocation = "";
        this.latitude = "";
        this.longitude = "";
        this.status = "";
        this.postDate = "";
        this.categoryId = "";
        this.categoryEnName = "";
        this.categoryArName = "";
        this.subCategoryId = "";
        this.subCategoryEnName = "";
        this.subCategoryArName = "";
        this.otherSubCategory = "";
        this.subTypeId = "";
        this.subTypeEnName = "";
        this.subTypeArName = "";
        this.otherSubType = "";
        this.typeId = "";
        this.userId = "";
        this.agentId = "";
        this.getCategoryEnName = "";
        this.enMaker = "";
        this.arMaker = "";
        this.enModel = "";
        this.arModel = "";
        this.enTrim = "";
        this.arTrim = "";
        this.enColor = "";
        this.arColor = "";
        this.kiloMeters = "";
        this.year = "";
        this.enDoors = "";
        this.arDoors = "";
        this.warranty = "";
        this.enTransmission = "";
        this.arTransmission = "";
        this.enRegionalSpecs = "";
        this.arRegionalSpecs = "";
        this.enBodyType = "";
        this.arBodyType = "";
        this.enFuelType = "";
        this.arFuelType = "";
        this.enNoOfCylinders = "";
        this.arNoOfCylinders = "";
        this.enHorsepower = "";
        this.arHorsepower = "";
        this.price = "";
        this.userImage = "";
        this.phoneNumber = "";
        this.enSteeringSide = "";
        this.arSteeringSide = "";
        pictures = new ArrayList<>();
        this.mainImage = "";
        this.isFav = "";
    }

    public ArrayList<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getEnCondition() {
        return enCondition;
    }

    public void setEnCondition(String enCondition) {
        this.enCondition = enCondition;
    }

    public String getArCondition() {
        return arCondition;
    }

    public void setArCondition(String arCondition) {
        this.arCondition = arCondition;
    }

    public String getEnMechanicalCondition() {
        return enMechanicalCondition;
    }

    public void setEnMechanicalCondition(String enMechanicalCondition) {
        this.enMechanicalCondition = enMechanicalCondition;
    }

    public String getArMechanicalCondition() {
        return arMechanicalCondition;
    }

    public void setArMechanicalCondition(String arMechanicalCondition) {
        this.arMechanicalCondition = arMechanicalCondition;
    }

    public String getEnCapacity() {
        return enCapacity;
    }

    public void setEnCapacity(String enCapacity) {
        this.enCapacity = enCapacity;
    }

    public String getArCapacity() {
        return arCapacity;
    }

    public void setArCapacity(String arCapacity) {
        this.arCapacity = arCapacity;
    }

    public String getEnEngineSize() {
        return enEngineSize;
    }

    public void setEnEngineSize(String enEngineSize) {
        this.enEngineSize = enEngineSize;
    }

    public String getArEngineSize() {
        return arEngineSize;
    }

    public void setArEngineSize(String arEngineSize) {
        this.arEngineSize = arEngineSize;
    }

    public String getEnAge() {
        return enAge;
    }

    public void setEnAge(String enAge) {
        this.enAge = enAge;
    }

    public String getArAge() {
        return arAge;
    }

    public void setArAge(String arAge) {
        this.arAge = arAge;
    }

    public String getEnUsage() {
        return enUsage;
    }

    public void setEnUsage(String enUsage) {
        this.enUsage = enUsage;
    }

    public String getArUsage() {
        return arUsage;
    }

    public void setArUsage(String arUsage) {
        this.arUsage = arUsage;
    }

    public String getEnLength() {
        return enLength;
    }

    public void setEnLength(String enLength) {
        this.enLength = enLength;
    }

    public String getArLength() {
        return arLength;
    }

    public void setArLength(String arLength) {
        this.arLength = arLength;
    }

    public String getEnWheels() {
        return enWheels;
    }

    public void setEnWheels(String enWheels) {
        this.enWheels = enWheels;
    }

    public String getArWheels() {
        return arWheels;
    }

    public void setArWheels(String arWheels) {
        this.arWheels = arWheels;
    }

    public String getEnSellerType() {
        return enSellerType;
    }

    public void setEnSellerType(String enSellerType) {
        this.enSellerType = enSellerType;
    }

    public String getArSellerType() {
        return arSellerType;
    }

    public void setArSellerType(String arSellerType) {
        this.arSellerType = arSellerType;
    }

    public String getEnDriveSystem() {
        return enDriveSystem;
    }

    public void setEnDriveSystem(String enDriveSystem) {
        this.enDriveSystem = enDriveSystem;
    }

    public String getArDriveSystem() {
        return arDriveSystem;
    }

    public void setArDriveSystem(String arDriveSystem) {
        this.arDriveSystem = arDriveSystem;
    }

    public String getEnPartName() {
        return enPartName;
    }

    public void setEnPartName(String enPartName) {
        this.enPartName = enPartName;
    }

    public String getArPartName() {
        return arPartName;
    }

    public void setArPartName(String arPartName) {
        this.arPartName = arPartName;
    }

    public String getNameOfPart() {
        return nameOfPart;
    }

    public void setNameOfPart(String nameOfPart) {
        this.nameOfPart = nameOfPart;
    }

    public String getEnSteeringSide() {
        return enSteeringSide;
    }

    public void setEnSteeringSide(String enSteeringSide) {
        this.enSteeringSide = enSteeringSide;
    }

    public String getArSteeringSide() {
        return arSteeringSide;
    }

    public void setArSteeringSide(String arSteeringSide) {
        this.arSteeringSide = arSteeringSide;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChats() {
        return chats;
    }

    public void setChats(String chats) {
        this.chats = chats;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
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

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryEnName() {
        return subCategoryEnName;
    }

    public void setSubCategoryEnName(String subCategoryEnName) {
        this.subCategoryEnName = subCategoryEnName;
    }

    public String getSubCategoryArName() {
        return subCategoryArName;
    }

    public void setSubCategoryArName(String subCategoryArName) {
        this.subCategoryArName = subCategoryArName;
    }

    public String getOtherSubCategory() {
        return otherSubCategory;
    }

    public void setOtherSubCategory(String otherSubCategory) {
        this.otherSubCategory = otherSubCategory;
    }

    public String getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(String subTypeId) {
        this.subTypeId = subTypeId;
    }

    public String getSubTypeEnName() {
        return subTypeEnName;
    }

    public void setSubTypeEnName(String subTypeEnName) {
        this.subTypeEnName = subTypeEnName;
    }

    public String getSubTypeArName() {
        return subTypeArName;
    }

    public void setSubTypeArName(String subTypeArName) {
        this.subTypeArName = subTypeArName;
    }

    public String getOtherSubType() {
        return otherSubType;
    }

    public void setOtherSubType(String otherSubType) {
        this.otherSubType = otherSubType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getGetCategoryEnName() {
        return getCategoryEnName;
    }

    public void setGetCategoryEnName(String getCategoryEnName) {
        this.getCategoryEnName = getCategoryEnName;
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

    public String getEnTrim() {
        return enTrim;
    }

    public void setEnTrim(String enTrim) {
        this.enTrim = enTrim;
    }

    public String getArTrim() {
        return arTrim;
    }

    public void setArTrim(String arTrim) {
        this.arTrim = arTrim;
    }

    public String getEnColor() {
        return enColor;
    }

    public void setEnColor(String enColor) {
        this.enColor = enColor;
    }

    public String getArColor() {
        return arColor;
    }

    public void setArColor(String arColor) {
        this.arColor = arColor;
    }

    public String getKiloMeters() {
        return kiloMeters;
    }

    public void setKiloMeters(String kiloMeters) {
        this.kiloMeters = kiloMeters;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEnDoors() {
        return enDoors;
    }

    public void setEnDoors(String enDoors) {
        this.enDoors = enDoors;
    }

    public String getArDoors() {
        return arDoors;
    }

    public void setArDoors(String arDoors) {
        this.arDoors = arDoors;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getEnTransmission() {
        return enTransmission;
    }

    public void setEnTransmission(String enTransmission) {
        this.enTransmission = enTransmission;
    }

    public String getArTransmission() {
        return arTransmission;
    }

    public void setArTransmission(String arTransmission) {
        this.arTransmission = arTransmission;
    }

    public String getEnRegionalSpecs() {
        return enRegionalSpecs;
    }

    public void setEnRegionalSpecs(String enRegionalSpecs) {
        this.enRegionalSpecs = enRegionalSpecs;
    }

    public String getArRegionalSpecs() {
        return arRegionalSpecs;
    }

    public void setArRegionalSpecs(String arRegionalSpecs) {
        this.arRegionalSpecs = arRegionalSpecs;
    }

    public String getEnBodyType() {
        return enBodyType;
    }

    public void setEnBodyType(String enBodyType) {
        this.enBodyType = enBodyType;
    }

    public String getArBodyType() {
        return arBodyType;
    }

    public void setArBodyType(String arBodyType) {
        this.arBodyType = arBodyType;
    }

    public String getEnFuelType() {
        return enFuelType;
    }

    public void setEnFuelType(String enFuelType) {
        this.enFuelType = enFuelType;
    }

    public String getArFuelType() {
        return arFuelType;
    }

    public void setArFuelType(String arFuelType) {
        this.arFuelType = arFuelType;
    }

    public String getEnNoOfCylinders() {
        return enNoOfCylinders;
    }

    public void setEnNoOfCylinders(String enNoOfCylinders) {
        this.enNoOfCylinders = enNoOfCylinders;
    }

    public String getArNoOfCylinders() {
        return arNoOfCylinders;
    }

    public void setArNoOfCylinders(String arNoOfCylinders) {
        this.arNoOfCylinders = arNoOfCylinders;
    }

    public String getEnHorsepower() {
        return enHorsepower;
    }

    public void setEnHorsepower(String enHorsepower) {
        this.enHorsepower = enHorsepower;
    }

    public String getArHorsepower() {
        return arHorsepower;
    }

    public void setArHorsepower(String arHorsepower) {
        this.arHorsepower = arHorsepower;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }
}
