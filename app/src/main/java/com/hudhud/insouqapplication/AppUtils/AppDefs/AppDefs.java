package com.hudhud.insouqapplication.AppUtils.AppDefs;

import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.SavedSearchAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UserFS;

import java.util.ArrayList;
import java.util.Locale;

public class AppDefs {
    public static ArrayList<UsedCarAd> motorAds = new ArrayList<>();
    public static ArrayList<JobAd> jobAds = new ArrayList<>();
    public static ArrayList<ServiceAd> serviceAds = new ArrayList<>();
    public static ArrayList<BusinessAd> businessAds = new ArrayList<>();
    public static ArrayList<ElectronicAd> electronicAds = new ArrayList<>();
    public static ArrayList<NumberAd> numberAds = new ArrayList<>();

    public static ArrayList<UsedCarAd> newMotorAds = new ArrayList<>();
    public static ArrayList<JobAd> newOpeningJobAds = new ArrayList<>();
    public static ArrayList<JobAd> newWantedJobAds = new ArrayList<>();
    public static ArrayList<JobAd> newJobAds = new ArrayList<>();
    public static ArrayList<ServiceAd> newServiceAds = new ArrayList<>();
    public static ArrayList<BusinessAd> newBusinessAds = new ArrayList<>();
    public static ArrayList<ElectronicAd> newClassifiedAds = new ArrayList<>();
    public static ArrayList<ElectronicAd> newElectronicAds = new ArrayList<>();
    public static ArrayList<NumberAd> newNumberAds = new ArrayList<>();

    public static String brand = "";
    public static String model = "";
    public static String subCatName = "";

    public static boolean isSaved = false;
    public static boolean isFav = false;
    public static SavedSearchAd savedSearchAd = new SavedSearchAd();

    //Constants
    public static final String SHARED_PREF_KEY = "user_login";
    public static final String ID_KEY = "ID";
    public static final String TOKEN_KEY = "TOKEN";
    public static final String LANGUAGE_KEY = "LANGUAGE";
    public static String token = "";

    public static UserFS user = new UserFS();
    public static String language = getLanguage();

    public static String getLanguage(){
        return Locale.getDefault().getDisplayLanguage();
    }
}
