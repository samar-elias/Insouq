package com.hudhud.insouqapplication.Views.Main.Home.SubCategory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.RangeSlider;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AdSubCategory;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.City;
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NewNumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.Picture;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.FiltersAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business.BusinessesAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business.BusinessesGridAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Classifieds.ClassifiedsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Classifieds.ClassifiedsGridItemAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics.ElectronicsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics.ElectronicsGridItemAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening.JobItemGridAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening.JobsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening.JobsFeaturedAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobWanted.JobWantedItemGridAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobWanted.JobsWantedAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobWanted.JobsWantedFeaturedAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.MotorItemAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.MotorItemGridAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.MotorItemLinearAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers.FeaturedNumbersAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers.NumberGridItemAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers.NumberItemAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Property.PropertyItemAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Property.PropertyItemGridAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Services.ServicesAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Services.ServicesGridAdapter;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.rizlee.rangeseekbar.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.http.Url;
import timber.log.Timber;

public class SubCategoryFragment extends Fragment {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    String categoryName, categoryId, typeId;
    TextView subCategoryTitle, featuredItemsTitle, allItemsTitle;
    RecyclerView filterRV, allItemsRV, allItemsGridRV, allItemsLinearRV;
    ImageView backArrow, changeLayout, closeFilterMotors, closeFilterProperty, closerFilterServices, closeFilterNumbers, closeFilterJobs, closeFilterListing, closeFilterBusiness
            , closeFilterWantedJobs, closeFilterElectronics, home, profile, chat, sellItem, notifications, scrollToTop;
    Spinner sortSpinner;
    LinearLayout  filter, sort, alert;
    LinearLayout motorsFilter, propertyFilter, servicesFilter, numbersFilter, jobsFilter, listingFilter, electronicsFilter, businessFilter, jobsWantedFilter;
    NavController navController;
    int position;
    NestedScrollView scrollView;
    MaterialButton resetFilters;
    LinearLayoutCompat noAds;
    LinearLayout navBar;
    public MainActivity mainActivity;
    TextView usedCarsCloseBtn, propertyCloseBtn, jobsCloseBtn, serviceCloseBtn, businessCloseBtn, numberCloseBtn, classifiedsCloseBtn, electronicsCloseBtn;
    ConstraintLayout toolbarLayout;
    MaterialButton usedCarsFilterResults, boatsFilterResults, machineFilterResults, bikeFilterResults, partFilterResults, jobFilterResults, serviceFilterResults, businessFilterResults, numberFilterResults,
            classifiedsFilterResults, electronicsFilterResults;
    boolean isSaved = false;
    String layout = "list";
    ArrayList<UsedCarAd> usedCars = new ArrayList<>();
    ArrayList<JobAd> jobAds = new ArrayList<>();
    ArrayList<ServiceAd> serviceAds = new ArrayList<>();
    ArrayList<BusinessAd> businessAds = new ArrayList<>();
    ArrayList<NumberAd> numberAds = new ArrayList<>();
    ArrayList<ElectronicAd> electronicAds = new ArrayList<>();
    ArrayList<ElectronicAd> classifiedsAds = new ArrayList<>();
    TextView resetMotorFilter, resetJobFilter, resetServiceFilter, resetBusinessFilter, resetNumberFilter, resetClassifiedsFilter, resetElectronicsFilter;

    //Motor filter
    ConstraintLayout usedCarsFilterLayout, boatsFilterLayout, machineryFilterLayout, bikeFilterLayout, partFilterLayout;
    RangeSeekBar usedCarsPriceBar, usedCarsYearBar, usedCarsMileageBar, boatsPriceBar, machinesPriceBar, machinesYearBar, bikesPriceBar, bikesYearBar, bikesMileageBar, partPriceBar, partYearBar;
    Spinner usedCarsBrandsSpinner, usedCarsModelsSpinner, usedCarsTrimsSpinner, boatAgeSpinner, boatUsageSpinner,
            boatWarrantySpinner, boatHorsepowerSpinner, machineryCategoriesSpinner, machinerySubCatSpinner, machineryUsageSpinner,
            machineryHorsepowerSpinner, bikesCategorySpinner, bikesMakerSpinner, bikePostedInSpinner, partsCategorySpinner, partsMakeSpinner, partsNameSpinner;
    ArrayList<String> brandIds = new ArrayList<>(), brandArTitles, brandEnTitles, modelArTitles, modelEnTitles, trimArTitles, trimEnTitles, locationArTitles, locationEnTitles,
            boatAgeArTitles, boatAgeEnTitles, boatUsageArTitles, boatUsageEnTitles, boatWarrantyTitles, boatHorsepowerArTitles, boatHorsepowerEnTitles,
            machineryArCategoryTitles, machineryEnCategoryTitles, machineryCatIds, machinerySubCatIds, machineryArSubCatTitles,
            machineryEnSubCatTitles, machineryArUsageTitles, machineryEnUsageTitles, machineryArHorsepowerTitles, machineryEnHorsepowerTitles,  bikesArCategoryTitles, bikesEnCategoryTitles, bikesCatIds,
            adPostedBikeTitles, bikesSubCatIds, bikesArSubCatTitles, bikesEnSubCatTitles,
            partArCatTitles, partEnCatTitles, partArMakeTitles, partEnMakeTitles, partCatIds, partMakeIds, partNameArTitles, partNameEnTitles;
    RecyclerView usedCarsRV, boatsRV, machinesRV, bikesRV, partsRV;
    ImageView carsAddLocation, boatsAddLocation, machinesAddLocation, bikesAddLocation, partsAddLocation;
    String currentBrand = "";
    String brandId = "";
    String currentModel = "";
    String currentTrim = "";
    String currentLocation = "";
    String currentBoatAge = "";
    String currentBoatUsage = "";
    String currentBoatWarranty = "";
    String currentBoatHorsePower = "";
    String machineryCategory = "";
    String machinerySubCat = "";
    String machineryUsage = "";
    String machineryHorsepower = "";
    String bikeCat = "";
    String bikeSubCat = "";
    String partCat = "";
    String partMake = "";
    String partName = "";
    String bikeAdPosted = "";

    //Job filter
     Spinner postedInSpinner, employmentTypeSpinner, jobTypeSpinner, workExperienceSpinner, educationLevelSpinner, professionalLevelSpinner;
     ArrayList<String> employmentTypeArTitles, employmentTypeEnTitles, jobTypeArTitles, jobTypeEnTitles, workExperienceArTitles, workExperienceEnTitles, educationLevelArTitles,
             educationLevelEnTitles, adPostedTitles, professionalLevelEnTitles, professionalLevelArTitles;
     String postedIn = "", employmentType = "", jobType = "", workExperience = "", educationLevel = "", professionalLevel = "";
     RecyclerView jobsRV;
     ImageView jobsAddLocation;

     //Service filter
    Spinner servicesPostedInSpinner, servicesSubCategorySpinner;
    TextView categoryTitle;
    ArrayList<String> servicesAdPostedTitles, servicesSubCatArTitles, servicesSubCatEnTitles, serviceSubCatIds;
    String servicePostIn = "", serviceSubCat = "0";
    RecyclerView serviceRV;
    ImageView serviceAddLocation;

    //Business filter
    Spinner businessSubTypeSpinner, businessCitySpinner, businessAdPostedSpinner;
    RangeSeekBar businessPriceBar;
    ArrayList<String> businessSubTypeArTitles, businessSubTypeEnTitles, businessSubTypeIds, businessCityArTitles, businessCityEnTitles, businessPostedTitles;
    String businessSubType = "",  businessCity = "", businessPostedIn = "";

    //Numbers filter
    Spinner emirateSpinner, plateCodeSpinner, plateTypeSpinner, operatorSpinner, codeSpinner, mobilePlanSpinner;
    RadioButton digit1, digit2, digit3, digit4, digit5, digit3Similar, digit4Similar, digit5Similar, digit6Similar, digit7Similar;
    RangeSeekBar platePriceBar, mobilePriceBar;
    ArrayList<String> emirateArTitles, emirateEnTitles, plateCodes, plateTypeArTitles, plateTypeEnTitles, operatorArTitles, operatorEnTitles, codes,
            mobilePlanArTitles, mobilePlanEnTitles;
    String emirate = "", plateCode = "", plateType = "", operator = "", code = "", mobilePlan = "", mobileLocation = "";
    ScrollView plateView, mobileView;
    RecyclerView plateNumbersRV, mobileNumbersRV;
    ImageView plateNumbersAddLocation, mobileNumbersAddLocation;

    //Classifieds filter
    Spinner classifiedsSubCatSpinner, classifiedsItemNameSpinner, classifiedsBrandSpinner, category1UsageSpinner, category2UsageSpinner, category1AgeSpinner,
            category2AgeSpinner;
    RangeSeekBar classifiedsPriceBar;
    ArrayList<String> classifiedsSubCatArTitles, classifiedsSubCatEnTitles, classifiedsSubCatIds, classifiedsItemNameArTitles, classifiedsItemNameEnTitles, classifiedsItemNameIds,
            classifiedsBrandArTitles, classifiedsBrandEnTitles, classifiedsCategory1UsageArTitles, classifiedsCategory1UsageEnTitles, classifiedsCategory2UsageArTitles, classifiedsCategory2UsageEnTitles,
            classifiedsCategory1AgeArTitles, classifiedsCategory1AgeEnTitles, classifiedsCategory2AgeArTitles, classifiedsCategory2AgeEnTitles;
    String classifiedsSubCatId = "", classifiedsItemNameId= "", classifiedsBrand = "", category1Age = "", category2Age = "", category1Usage = "", category2Usage = "";
    LinearLayout category1, category2;
    RecyclerView classifiedsRV;
    ImageView classifiedsAddLocation;

    //Electronics filter
    Spinner electronicsSubCatSpinner, electronicsTrimSpinner, electronicsAgeSpinner, electronicsWarrantySpinner, electronicsAdPostedSpinner;
    RangeSeekBar electronicsPriceBar;
    ArrayList<String> electronicsSubCatArTitles, electronicsSubCatEnTitles, electronicsSubCatIds, electronicsTrimArTitles, electronicsTrimEnTitles, electronicsTrimIds,
            electronicsAgeArTitles, electronicsAgeEnTitles, electronicsWarrantyTitles, electronicsPostedInTitles;
    String electronicsSubCat = "", electronicsTrim = "", electronicsAge = "", electronicsWarranty = "1", electronicsPosted = "";
    RecyclerView electronicsRV;
    ImageView electronicsAddLocation;

    ArrayList<City> cities = new ArrayList<>();
    public ArrayList<City> selectedCities = new ArrayList<>();

    EditText usedCarFromEdt, usedCarToEdt;
    String sortBy = "0";

    String fromPrice = "", toPrice = "", fromYear = "", toYear = "", fromKilo = "", toKilo = "", enMaker = "", arMaker = "", enModel = "", arModel = "", enTrim = "", arTrim = ""
            , enHorse = "", arHorse = "", enAge = "", arAge = "", enUsage= "", arUsage = "", warranty = "", enPart = "", arPart = "", subCatId = "", subTypeId = "", enCommitment = "", arCommitment = ""
            , enJobType = "", arJobType = "", enWorkExperience = "", arWorkExperience = "", enEducation = "", arEducation = "", enEmirate = "", arEmirate = "", enOperator = "", arOperator = ""
            , enMobileCode = "", arMobileCode = "", enNumberPlan = "", arNumberPlan = "", enPlateType = "", arPlateType = "", enPlateCode = "", arPlateCode = "", mobileDigits3 = ""
            , mobileDigits4 = "", mobileDigits5 = "", mobileDigits6 = "",mobileDigits7 = "", plateDigits1= "", plateDigits2= "", plateDigits3= "", plateDigits4= "", plateDigits5= "", enBrand = "", arBrand = "";

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_category, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        setData();
        onSpinnerClick();
        getLocations();
//        setAdapters();
//        setSortSpinner();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View view){
        if (getArguments() != null){
            categoryName = SubCategoryFragmentArgs.fromBundle(getArguments()).getCategoryName();
            position = SubCategoryFragmentArgs.fromBundle(getArguments()).getPosition();
            categoryId = SubCategoryFragmentArgs.fromBundle(getArguments()).getCategoryId();
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        navController = Navigation.findNavController(view);
        navBar = view.findViewById(R.id.nav_bar_layout);
        scrollView = view.findViewById(R.id.scrollView);
        toolbarLayout = view.findViewById(R.id.toolbar);
        subCategoryTitle = view.findViewById(R.id.sub_category_title);
        featuredItemsTitle = view.findViewById(R.id.featured_items_title);
        allItemsTitle = view.findViewById(R.id.all_items_title);
        allItemsRV = view.findViewById(R.id.all_items_RV);
        allItemsGridRV = view.findViewById(R.id.all_items_RV_grid);
        allItemsLinearRV = view.findViewById(R.id.all_items_RV_linear);
        backArrow = view.findViewById(R.id.back_arrow);
        changeLayout = view.findViewById(R.id.change_layout);
        sort = view.findViewById(R.id.sort);
        alert = view.findViewById(R.id.alert);
        scrollToTop = view.findViewById(R.id.scroll_to_top);
//        sortSpinner = view.findViewById(R.id.sort_spinner);
        filter = view.findViewById(R.id.filter);
        motorsFilter = view.findViewById(R.id.motor_filters);
        motorsFilter.setVisibility(View.GONE);
        closeFilterMotors = view.findViewById(R.id.close_filter_motors);
        closeFilterProperty = view.findViewById(R.id.close_filter_property);
        closeFilterJobs = view.findViewById(R.id.close_filter_jobs);
        closeFilterBusiness = view.findViewById(R.id.close_filter_business);
        closeFilterListing = view.findViewById(R.id.close_filter_listing);
        closeFilterNumbers = view.findViewById(R.id.close_filter_numbers);
        closerFilterServices = view.findViewById(R.id.close_filter_services);
        closeFilterWantedJobs = view.findViewById(R.id.close_filter_wanted_jobs);
        closeFilterElectronics = view.findViewById(R.id.close_filter_electronics);
        propertyFilter = view.findViewById(R.id.property_filters);
        propertyFilter.setVisibility(View.GONE);
        servicesFilter = view.findViewById(R.id.services_filters);
        servicesFilter.setVisibility(View.GONE);
        jobsFilter = view.findViewById(R.id.jobs_filters);
        jobsFilter.setVisibility(View.GONE);
        listingFilter = view.findViewById(R.id.listing_filters);
        electronicsFilter = view.findViewById(R.id.electronics_filters);
        electronicsFilter.setVisibility(View.GONE);
        listingFilter.setVisibility(View.GONE);
        businessFilter = view.findViewById(R.id.business_filters);
        businessFilter.setVisibility(View.GONE);
        numbersFilter = view.findViewById(R.id.numbers_filters);
        numbersFilter.setVisibility(View.GONE);
        jobsWantedFilter = view.findViewById(R.id.job_wanted_filters);
        jobsWantedFilter.setVisibility(View.GONE);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
        filterRV = view.findViewById(R.id.filter_RV);

        noAds = view.findViewById(R.id.no_ads_layout);
        resetFilters = view.findViewById(R.id.reset_filters);

        usedCarsCloseBtn = view.findViewById(R.id.close_used_cars_btn);
        jobsCloseBtn = view.findViewById(R.id.close_job_btn);
        serviceCloseBtn = view.findViewById(R.id.service_close_btn);
        usedCarsFilterResults = view.findViewById(R.id.filter_results_used_cars_btn);
        boatsFilterResults = view.findViewById(R.id.filter_results_boats_btn);
        machineFilterResults = view.findViewById(R.id.filter_results_machinery_btn);
        partFilterResults = view.findViewById(R.id.filter_results_parts_btn);

        //Motor Filters
        usedCarsFilterLayout = view.findViewById(R.id.used_cars_filter);
        boatsFilterLayout = view.findViewById(R.id.boats_filter);
        machineryFilterLayout = view.findViewById(R.id.machinery_filter);
        partFilterLayout = view.findViewById(R.id.parts_filter);
        usedCarsBrandsSpinner = view.findViewById(R.id.used_cars_brand_spinner);
        usedCarsModelsSpinner = view.findViewById(R.id.used_cars_model_spinner);
        usedCarsTrimsSpinner = view.findViewById(R.id.used_cars_trim_spinner);
        usedCarsPriceBar = view.findViewById(R.id.used_cars_price_seek_bar);
        usedCarsMileageBar = view.findViewById(R.id.used_cars_mileage_bar);
        usedCarsYearBar = view.findViewById(R.id.used_cars_year_bar);
        usedCarsYearBar.setCurrentValues(1900, year+1);
        resetMotorFilter = view.findViewById(R.id.reset_motor_filter_btn);
        boatAgeSpinner = view.findViewById(R.id.boat_age_spinner);
        boatUsageSpinner = view.findViewById(R.id.boat_usage_spinner);
        boatWarrantySpinner = view.findViewById(R.id.boat_warranty_spinner);
        boatHorsepowerSpinner = view.findViewById(R.id.boat_horsepower_spinner);
        boatsPriceBar = view.findViewById(R.id.boats_price_seek_bar);
        machineryCategoriesSpinner = view.findViewById(R.id.machinery_category_spinner);
        machinerySubCatSpinner = view.findViewById(R.id.machinery_sub_cat_spinner);
        machineryUsageSpinner = view.findViewById(R.id.machinery_usage_spinner);
        machineryHorsepowerSpinner = view.findViewById(R.id.machinery_horsepower_spinner);
        machinesPriceBar = view.findViewById(R.id.machinery_price_seek_bar);
        machinesYearBar = view.findViewById(R.id.machinery_year_bar);
        machinesYearBar.setCurrentValues(1900, year+1);
        bikeFilterLayout = view.findViewById(R.id.bikes_filter);
        bikeFilterResults = view.findViewById(R.id.filter_results_bikes_btn);
        bikesCategorySpinner = view.findViewById(R.id.bikes_brand_spinner);
        bikesMakerSpinner = view.findViewById(R.id.bikes_model_spinner);
        bikePostedInSpinner = view.findViewById(R.id.bikes_posted_in_spinner);
        bikesPriceBar = view.findViewById(R.id.bikes_price_seek_bar);
        bikesYearBar = view.findViewById(R.id.bikes_year_bar);
        bikesYearBar.setCurrentValues(1900, year+1);
        bikesMileageBar = view.findViewById(R.id.bikes_mileage_bar);
        partsNameSpinner = view.findViewById(R.id.part_names_spinner);
        partsCategorySpinner = view.findViewById(R.id.parts_category_spinner);
        partsMakeSpinner = view.findViewById(R.id.parts_sub_cat_spinner);
        partPriceBar = view.findViewById(R.id.parts_price_seek_bar);
        partYearBar = view.findViewById(R.id.parts_year_bar);
        partYearBar.setCurrentValues(1900, year+1);


        usedCarsRV = view.findViewById(R.id.location_RV);
        bikesRV = view.findViewById(R.id.location_RV2);
        boatsRV = view.findViewById(R.id.location_RV3);
        machinesRV = view.findViewById(R.id.location_RV4);
        partsRV = view.findViewById(R.id.location_RV5);
        
        carsAddLocation = view.findViewById(R.id.add_location);
        bikesAddLocation = view.findViewById(R.id.add_location2);
        boatsAddLocation = view.findViewById(R.id.add_location3);
        machinesAddLocation = view.findViewById(R.id.add_location4);
        partsAddLocation = view.findViewById(R.id.add_location5);

        brandArTitles = new ArrayList<>();
        brandEnTitles = new ArrayList<>();
        modelArTitles = new ArrayList<>();
        modelEnTitles = new ArrayList<>();
        adPostedBikeTitles = new ArrayList<>();
        trimArTitles = new ArrayList<>();
        trimEnTitles = new ArrayList<>();
        locationArTitles = new ArrayList<>();
        locationEnTitles = new ArrayList<>();
        boatAgeArTitles = new ArrayList<>();
        boatAgeEnTitles = new ArrayList<>();
        boatUsageArTitles = new ArrayList<>();
        boatUsageEnTitles = new ArrayList<>();
        boatWarrantyTitles = new ArrayList<>();
        boatHorsepowerArTitles = new ArrayList<>();
        boatHorsepowerEnTitles = new ArrayList<>();
        machineryArCategoryTitles = new ArrayList<>();
        machineryEnCategoryTitles = new ArrayList<>();
        machineryCatIds = new ArrayList<>();
        machinerySubCatIds = new ArrayList<>();
        machineryArSubCatTitles = new ArrayList<>();
        machineryEnSubCatTitles = new ArrayList<>();
        machineryArUsageTitles = new ArrayList<>();
        machineryEnUsageTitles = new ArrayList<>();
        machineryArHorsepowerTitles = new ArrayList<>();
        machineryEnHorsepowerTitles = new ArrayList<>();
        bikesArCategoryTitles = new ArrayList<>();
        bikesArSubCatTitles = new ArrayList<>();
        bikesCatIds = new ArrayList<>();
        bikesSubCatIds = new ArrayList<>();
        bikesEnCategoryTitles = new ArrayList<>();
        bikesEnSubCatTitles = new ArrayList<>();
        partNameEnTitles = new ArrayList<>();
        partNameArTitles = new ArrayList<>();
        partEnMakeTitles = new ArrayList<>();
        partArMakeTitles = new ArrayList<>();
        partEnCatTitles = new ArrayList<>();
        partArCatTitles = new ArrayList<>();
        partCatIds = new ArrayList<>();
        partMakeIds = new ArrayList<>();

        //JOb filter
        resetJobFilter = view.findViewById(R.id.reset_job_btn);
        closeFilterJobs = view.findViewById(R.id.close_filter_jobs);
        jobFilterResults = view.findViewById(R.id.filter_job_results);
        postedInSpinner = view.findViewById(R.id.posted_in_spinner);
        employmentTypeSpinner = view.findViewById(R.id.employment_types_spinner);
        jobTypeSpinner = view.findViewById(R.id.job_types_spinner);
        workExperienceSpinner = view.findViewById(R.id.work_experience_spinner);
        educationLevelSpinner = view.findViewById(R.id.education_level_spinner);
        professionalLevelSpinner = view.findViewById(R.id.professional_level_spinner);
        adPostedTitles = new ArrayList<>();
        employmentTypeArTitles = new ArrayList<>();
        employmentTypeEnTitles = new ArrayList<>();
        jobTypeArTitles = new ArrayList<>();
        jobTypeEnTitles = new ArrayList<>();
        workExperienceEnTitles = new ArrayList<>();
        workExperienceArTitles = new ArrayList<>();
        educationLevelArTitles = new ArrayList<>();
        educationLevelEnTitles = new ArrayList<>();
        professionalLevelEnTitles = new ArrayList<>();
        professionalLevelArTitles = new ArrayList<>();

        jobsRV = view.findViewById(R.id.location_RV10);
        jobsAddLocation = view.findViewById(R.id.add_location10);

        //Services filter
        resetServiceFilter = view.findViewById(R.id.service_reset_btn);
        serviceFilterResults = view.findViewById(R.id.service_filter_results);
        categoryTitle = view.findViewById(R.id.sub_cat);
        servicesPostedInSpinner = view.findViewById(R.id.services_posted_in_spinner);
        servicesSubCategorySpinner = view.findViewById(R.id.services_sub_cat_spinner);
        servicesAdPostedTitles = new ArrayList<>();
        servicesSubCatArTitles = new ArrayList<>();
        servicesSubCatEnTitles = new ArrayList<>();
        serviceSubCatIds = new ArrayList<>();

        serviceRV = view.findViewById(R.id.location_RV6);
        serviceAddLocation = view.findViewById(R.id.add_location6);

        //Business filter
        businessCloseBtn = view.findViewById(R.id.business_close_btn);
        businessFilterResults = view.findViewById(R.id.business_filter_results);
        resetBusinessFilter = view.findViewById(R.id.business_reset_btn);
        businessCitySpinner = view.findViewById(R.id.business_city_spinner);
        businessSubTypeSpinner = view.findViewById(R.id.business_sub_type_spinner);
        businessAdPostedSpinner = view.findViewById(R.id.business_ad_posted_spinner);
        businessPriceBar = view.findViewById(R.id.business_price_seek_bar);
        businessCityArTitles = new ArrayList<>();
        businessCityEnTitles = new ArrayList<>();
        businessSubTypeEnTitles = new ArrayList<>();
        businessSubTypeArTitles = new ArrayList<>();
        businessSubTypeIds = new ArrayList<>();
        businessPostedTitles = new ArrayList<>();

        //Number filters
        resetNumberFilter = view.findViewById(R.id.reset_numbers_btn);
        numberCloseBtn = view.findViewById(R.id.number_close_btn);
        numberFilterResults = view.findViewById(R.id.numbers_filter_results);
        emirateSpinner = view.findViewById(R.id.emirate_spinner);
        plateTypeSpinner = view.findViewById(R.id.plate_type_spinner);
        plateCodeSpinner = view.findViewById(R.id.plate_code_spinner);
        digit1 = view.findViewById(R.id.digit1);
        digit2 = view.findViewById(R.id.digit2);
        digit3 = view.findViewById(R.id.digit3);
        digit4 = view.findViewById(R.id.digit4);
        digit5 = view.findViewById(R.id.digit5);
        platePriceBar = view.findViewById(R.id.plate_price_seek_bar);
        operatorSpinner = view.findViewById(R.id.operators_spinner);
        codeSpinner = view.findViewById(R.id.codes_spinner);
        mobilePlanSpinner = view.findViewById(R.id.mobile_plan_spinner);
        digit3Similar = view.findViewById(R.id.digit3_similar);
        digit4Similar = view.findViewById(R.id.digit4_similar);
        digit5Similar = view.findViewById(R.id.digit5_similar);
        digit6Similar = view.findViewById(R.id.digit6_similar);
        digit7Similar = view.findViewById(R.id.digit7_similar);
        mobilePriceBar = view.findViewById(R.id.mobile_price_seek_bar);
        emirateArTitles = new ArrayList<>();
        emirateEnTitles = new ArrayList<>();
        plateTypeArTitles = new ArrayList<>();
        plateTypeEnTitles = new ArrayList<>();
        plateCodes = new ArrayList<>();
        operatorArTitles = new ArrayList<>();
        operatorEnTitles = new ArrayList<>();
        codes = new ArrayList<>();
        mobilePlanArTitles = new ArrayList<>();
        mobilePlanEnTitles = new ArrayList<>();
        plateView = view.findViewById(R.id.plate_view);
        mobileView = view.findViewById(R.id.mobile_view);

        plateNumbersRV = view.findViewById(R.id.location_RV12);
        plateNumbersAddLocation = view.findViewById(R.id.add_location12);
        mobileNumbersRV = view.findViewById(R.id.location_RV7);
        mobileNumbersAddLocation = view.findViewById(R.id.add_location7);

        //Classifieds
        classifiedsCloseBtn = view.findViewById(R.id.classifieds_close_btn);
        classifiedsFilterResults = view.findViewById(R.id.classifieds_filter_results);
        resetClassifiedsFilter = view.findViewById(R.id.classifieds_reset_btn);
        classifiedsSubCatSpinner = view.findViewById(R.id.classifieds_sub_cat_spinner);
        classifiedsItemNameSpinner = view.findViewById(R.id.classifieds_item_name_spinner);
        classifiedsBrandSpinner = view.findViewById(R.id.classifieds_brand_spinner);
        category1UsageSpinner = view.findViewById(R.id.category1_usage_spinner);
        category1AgeSpinner = view.findViewById(R.id.category1_age_spinner);
        category2UsageSpinner = view.findViewById(R.id.category2_usage_spinner);
        category2AgeSpinner = view.findViewById(R.id.category2_age_spinner);
        classifiedsPriceBar = view.findViewById(R.id.classifieds_price_seek_bar);
        classifiedsSubCatArTitles = new ArrayList<>();
        classifiedsSubCatEnTitles = new ArrayList<>();
        classifiedsSubCatIds = new ArrayList<>();
        classifiedsItemNameArTitles = new ArrayList<>();
        classifiedsItemNameEnTitles = new ArrayList<>();
        classifiedsItemNameIds = new ArrayList<>();
        classifiedsBrandArTitles = new ArrayList<>();
        classifiedsBrandEnTitles = new ArrayList<>();
        classifiedsCategory1AgeArTitles = new ArrayList<>();
        classifiedsCategory1AgeEnTitles = new ArrayList<>();
        classifiedsCategory1UsageArTitles = new ArrayList<>();
        classifiedsCategory1UsageEnTitles = new ArrayList<>();
        classifiedsCategory2AgeArTitles = new ArrayList<>();
        classifiedsCategory2AgeEnTitles = new ArrayList<>();
        classifiedsCategory2UsageArTitles = new ArrayList<>();
        classifiedsCategory2UsageEnTitles = new ArrayList<>();
        category1 = view.findViewById(R.id.categories1_layout);
        category2 = view.findViewById(R.id.categories2_layout);

        classifiedsRV = view.findViewById(R.id.location_RV9);
        classifiedsAddLocation = view.findViewById(R.id.add_location9);

        //Electronics Filter
        electronicsCloseBtn = view.findViewById(R.id.electronics_close_btn);
        electronicsFilterResults = view.findViewById(R.id.electronics_filter_results);
        resetElectronicsFilter = view.findViewById(R.id.electronics_reset_btn);
        electronicsSubCatSpinner = view.findViewById(R.id.electronics_sub_cat_spinner);
        electronicsTrimSpinner = view.findViewById(R.id.electronics_trim_spinner);
        electronicsAgeSpinner = view.findViewById(R.id.electronics_age_spinner);
        electronicsWarrantySpinner = view.findViewById(R.id.electronics_warranty_spinner);
        electronicsAdPostedSpinner = view.findViewById(R.id.electronics_posted_in_spinner);
        electronicsPriceBar = view.findViewById(R.id.electronics_price_seek_bar);
        electronicsSubCatArTitles = new ArrayList<>();
        electronicsSubCatEnTitles = new ArrayList<>();
        electronicsSubCatIds = new ArrayList<>();
        electronicsTrimArTitles = new ArrayList<>();
        electronicsTrimEnTitles = new ArrayList<>();
        electronicsTrimIds = new ArrayList<>();
        electronicsAgeArTitles = new ArrayList<>();
        electronicsAgeEnTitles = new ArrayList<>();
        electronicsWarrantyTitles = new ArrayList<>();
        electronicsPostedInTitles = new ArrayList<>();

        electronicsRV = view.findViewById(R.id.location_RV11);
        electronicsAddLocation = view.findViewById(R.id.add_location11);

        usedCarFromEdt = view.findViewById(R.id.price_used_edt_from);
        usedCarToEdt = view.findViewById(R.id.price_used_edt_to);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    private void onClick(){
        backArrow.setOnClickListener(view -> navController.popBackStack());
        filter.setOnClickListener(view -> {
            switch(categoryName) {
                case "Motors":
                    openFilters(motorsFilter);
                    switch (categoryId){
                        case "2":
                        case "8":
                            usedCarsFilterLayout.setVisibility(View.VISIBLE);
                            boatsFilterLayout.setVisibility(View.GONE);
                            machineryFilterLayout.setVisibility(View.GONE);
                            bikeFilterLayout.setVisibility(View.GONE);
                            partFilterLayout.setVisibility(View.GONE);
                            getBrands();
                            break;
                        case "5":
                            usedCarsFilterLayout.setVisibility(View.GONE);
                            boatsFilterLayout.setVisibility(View.VISIBLE);
                            machineryFilterLayout.setVisibility(View.GONE);
                            bikeFilterLayout.setVisibility(View.GONE);
                            partFilterLayout.setVisibility(View.GONE);
                            getBoatAges();
                            break;
                        case "6":
                            usedCarsFilterLayout.setVisibility(View.GONE);
                            boatsFilterLayout.setVisibility(View.GONE);
                            machineryFilterLayout.setVisibility(View.VISIBLE);
                            bikeFilterLayout.setVisibility(View.GONE);
                            partFilterLayout.setVisibility(View.GONE);
                            getMachineryCategories();
                            break;
                        case "9":
                            usedCarsFilterLayout.setVisibility(View.GONE);
                            boatsFilterLayout.setVisibility(View.GONE);
                            machineryFilterLayout.setVisibility(View.GONE);
                            bikeFilterLayout.setVisibility(View.VISIBLE);
                            partFilterLayout.setVisibility(View.GONE);
                            getBikeCategories();
                            break;
                        case "7":
                            usedCarsFilterLayout.setVisibility(View.GONE);
                            boatsFilterLayout.setVisibility(View.GONE);
                            machineryFilterLayout.setVisibility(View.GONE);
                            bikeFilterLayout.setVisibility(View.GONE);
                            partFilterLayout.setVisibility(View.VISIBLE);
                            getPartCats();
                            break;
                    }
                    break;
                case "Property":
                    openFilters(propertyFilter);
                    break;
                case "Jobs":
                    openFilters(jobsWantedFilter);
                    getPostedIn();
                    break;
                case "Numbers":
                    openFilters(numbersFilter);
                    if (categoryId.equals("17")){
                        mobileView.setVisibility(View.GONE);
                        plateView.setVisibility(View.VISIBLE);
                        getEmirates();
                    }else {
                        mobileView.setVisibility(View.VISIBLE);
                        plateView.setVisibility(View.GONE);
                        getAllOperators();
                    }
                    break;
                case "Business":
                    openFilters(businessFilter);
                    getBusinessSubCategories();
                    break;
                case "Services":
                    openFilters(servicesFilter);
                    if (categoryId.equals("31")){
                        categoryTitle.setVisibility(View.GONE);
                        servicesSubCategorySpinner.setVisibility(View.GONE);
                        getServicePostedIn();
                    }else {
                        categoryTitle.setVisibility(View.VISIBLE);
                        servicesSubCategorySpinner.setVisibility(View.VISIBLE);
                        getServicesSubCategories();
                    }
                    break;
                case "Classifieds":
                    openFilters(listingFilter);
                    switch (categoryId){
                        case "39":
                        case "40":
                        case "41":
                        case "42":
                        case "43":
                        case "44":
                        case "45":
                            category1.setVisibility(View.VISIBLE);
                            category2.setVisibility(View.GONE);
                            getClassifiedsBrands();
                            break;
                        case "46":
                        case "47":
                        case "48":
                        case "49":
                        case "51":
                        case "52":
                            category1.setVisibility(View.GONE);
                            category2.setVisibility(View.VISIBLE);
                            getClassifieds2Ages();
                            break;
                        case "53":
                        case "54":
                        case "55":
                            category1.setVisibility(View.GONE);
                            category2.setVisibility(View.GONE);
                    }
                    getClassifiedsSubCategories(categoryId);
                    break;
                case "Electronics":
                    openFilters(electronicsFilter);
                    getElectronicsSubCategories();
                    break;
            }

        });
        changeLayout.setOnClickListener(view -> {
            if (categoryName.equals("Motors")){
                switch (layout){
                    case "list":
                        allItemsRV.setVisibility(View.GONE);
                        allItemsGridRV.setVisibility(View.VISIBLE);
                        allItemsLinearRV.setVisibility(View.GONE);
                        Glide.with(mainActivity).load(R.drawable.ic_baseline_format_list_bulleted_24_white).into(changeLayout);
                        layout = "grid";
                        break;
                    case "grid":
                        allItemsRV.setVisibility(View.GONE);
                        allItemsGridRV.setVisibility(View.GONE);
                        allItemsLinearRV.setVisibility(View.VISIBLE);
                        Glide.with(mainActivity).load(R.drawable.list_white).into(changeLayout);
                        layout = "linear";
                        break;
                    case "linear":
                        allItemsRV.setVisibility(View.VISIBLE);
                        allItemsGridRV.setVisibility(View.GONE);
                        allItemsLinearRV.setVisibility(View.GONE);
                        Glide.with(mainActivity).load(R.drawable.grid_icon).into(changeLayout);
                        layout = "list";
                        break;
                }
            }else {
                allItemsLinearRV.setVisibility(View.GONE);
                if (layout.equals("grid")){
                    allItemsRV.setVisibility(View.VISIBLE);
                    allItemsGridRV.setVisibility(View.GONE);
                    Glide.with(mainActivity).load(R.drawable.grid_icon).into(changeLayout);
                    layout = "list";
                }else if (layout.equals("list")){
                    allItemsRV.setVisibility(View.GONE);
                    allItemsGridRV.setVisibility(View.VISIBLE);
                    Glide.with(mainActivity).load(R.drawable.list_white).into(changeLayout);
                    layout = "grid";
                }
            }
        });
        closeFilterMotors.setOnClickListener(view -> closeFilter(motorsFilter));
        closerFilterServices.setOnClickListener(view -> closeFilter(servicesFilter));
        closeFilterNumbers.setOnClickListener(view -> closeFilter(numbersFilter));
        closeFilterListing.setOnClickListener(view -> closeFilter(listingFilter));
        closeFilterBusiness.setOnClickListener(view -> closeFilter(businessFilter));
        closeFilterJobs.setOnClickListener(view -> closeFilter(jobsFilter));
        closeFilterProperty.setOnClickListener(view -> closeFilter(propertyFilter));
        closeFilterWantedJobs.setOnClickListener(view -> closeFilter(jobsWantedFilter));
        closeFilterElectronics.setOnClickListener(view -> closeFilter(electronicsFilter));

        sort.setOnClickListener(view -> sortPopUp());
        alert.setOnClickListener(view ->{
            setAlertPopUp();
        });

        resetFilters.setOnClickListener(v -> {
            switch(categoryName) {
                case "Motors":
                    switch (categoryId) {
                        case "2":
                        case "8":
                            getAllUsedCars();
                            break;
                        case "5":
                            getAllBoats();
                            break;
                        case "6":
                            getAllMachines();
                            break;
                        case "7":
                            getAllParts();
                            break;
                        case "9":
                            getAllBikes();
                            break;
                    }
                    break;
                case "Jobs":
                    getAllJobs();
                    break;
                case "Numbers":
                    getAllNumbers();
                    break;
                case "Business":
                    filterBusiness();
//                    getBusinessAds();
                    break;
                case "Services":
//                    getServicesAds();
                    filterServices();
                    break;
                case "Classifieds":
                    getAllClassifieds();
                    break;
                case "Electronics":
                    getAllElectronics();
                    break;
            }
        });

        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));

        usedCarsCloseBtn.setOnClickListener(view -> closeFilter(motorsFilter));
        jobsCloseBtn.setOnClickListener(view -> closeFilter(jobsWantedFilter));
        serviceCloseBtn.setOnClickListener(view -> closeFilter(servicesFilter));
        businessCloseBtn.setOnClickListener(view -> closeFilter(businessFilter));
        numberCloseBtn.setOnClickListener(view -> closeFilter(numbersFilter));
        classifiedsCloseBtn.setOnClickListener(view -> closeFilter(listingFilter));
        electronicsCloseBtn.setOnClickListener(view -> closeFilter(electronicsFilter));

        carsAddLocation.setOnClickListener(view ->  locationsPopUp(usedCarsRV));
        bikesAddLocation.setOnClickListener(view -> locationsPopUp(bikesRV));
        boatsAddLocation.setOnClickListener(view -> locationsPopUp(boatsRV));
        machinesAddLocation.setOnClickListener(view -> locationsPopUp(machinesRV));
        partsAddLocation.setOnClickListener(view -> locationsPopUp(partsRV));
        electronicsAddLocation.setOnClickListener(view -> locationsPopUp(electronicsRV));
        classifiedsAddLocation.setOnClickListener(view -> locationsPopUp(classifiedsRV));
        mobileNumbersAddLocation.setOnClickListener(view -> locationsPopUp(mobileNumbersRV));
        plateNumbersAddLocation.setOnClickListener(view -> locationsPopUp(plateNumbersRV));
        serviceAddLocation.setOnClickListener(view -> locationsPopUp(serviceRV));
        jobsAddLocation.setOnClickListener(view -> locationsPopUp(jobsRV));

        usedCarsFilterResults.setOnClickListener(view -> {
            filterUsedCars();
            closeFilter(motorsFilter);
        });
        boatsFilterResults.setOnClickListener(view -> {
            filterBoats();
            closeFilter(motorsFilter);
        });
        machineFilterResults.setOnClickListener(view -> {
            filterMachines();
            closeFilter(motorsFilter);
        });
        bikeFilterResults.setOnClickListener(view -> {
            filterBikes();
            closeFilter(motorsFilter);
        });
        partFilterResults.setOnClickListener(view -> {
            filterParts();
            closeFilter(motorsFilter);
        });

        jobFilterResults.setOnClickListener(view -> {
            filterJobs();
            closeFilter(jobsWantedFilter);
        });

        serviceFilterResults.setOnClickListener(view -> {
            filterServices();
            closeFilter(servicesFilter);
        });

        businessFilterResults.setOnClickListener(view -> {
            filterBusiness();
            closeFilter(businessFilter);
        });

        numberFilterResults.setOnClickListener(view -> {
            filterNumbers();
            closeFilter(numbersFilter);
        });

        classifiedsFilterResults.setOnClickListener(view -> {
            filterClassifieds();
            closeFilter(listingFilter);
        });

        electronicsFilterResults.setOnClickListener(view -> {
            filterElectronics();
            closeFilter(electronicsFilter);
        });

        resetMotorFilter.setOnClickListener(view -> {
//            if (categoryId.equals("2") || categoryId.equals("8")){
//                filterUsedCars1();
//            } else if (categoryId.equals("5")) {
//                filterBoats1();
//            } else if (categoryId.equals("6")) {
//                filterMachines1();
//            } else if (categoryId.equals("7")) {
//                filterParts1();
//            } else if (categoryId.equals("9")) {
//                filterBikes1();
//            }
            setData();
            closeFilter(motorsFilter);
        });

        resetJobFilter.setOnClickListener(view ->  {
//            filterJobs1();
//            getJobAds(categoryId);
            setData();
            closeFilter(jobsWantedFilter);
        });

        resetServiceFilter.setOnClickListener(view -> {
//            getServicesAds();
//            filterServices();
            setData();
            closeFilter(servicesFilter);
        });

        resetBusinessFilter.setOnClickListener(view -> {
//            getBusinessAds();
//            filterBusiness();
            setData();
            closeFilter(businessFilter);
        });

        resetNumberFilter.setOnClickListener(view -> {
//            filterNumbers1();
            setData();
            closeFilter(numbersFilter);
        });

        resetClassifiedsFilter.setOnClickListener(view -> {
//            filterClassifieds1();
//            getClassifiedsAds();
            setData();
            closeFilter(listingFilter);
        });

        resetElectronicsFilter.setOnClickListener(view -> {
//            filterElectronics1();
//            getElectronicsAds();
            setData();
            closeFilter(electronicsFilter);
        });

        scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (view, i, i1, i2, i3) -> {
            if (i1>i3){
                toolbarLayout.setVisibility(View.GONE);
                scrollToTop.setVisibility(View.VISIBLE);
                navBar.setVisibility(View.GONE);
            }
            if (i1 == 0){
                toolbarLayout.setVisibility(View.VISIBLE);
                scrollToTop.setVisibility(View.GONE);
                navBar.setVisibility(View.VISIBLE);
            }
        });

        scrollToTop.setOnClickListener(view -> scrollView.scrollTo(0,0));

//        usedCarsPriceBar.setListenerPost(new RangeSeekBar.OnRangeSeekBarPostListener() {
//            @Override
//            public void onValuesChanged(float v, float v1) {
//
//            }
//
//            @Override
//            public void onValuesChanged(int i, int i1) {
//                usedCarFromEdt.setText(i);
//                usedCarToEdt.setText(i1);
//            }
//        });


//        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
//            int scrollY = scrollView.getScrollY();
//            if (scrollY == 0){
//                toolbarLayout.setVisibility(View.VISIBLE);
//            }else if (scrollY>0){
//                toolbarLayout.setVisibility(View.GONE);
//            }
//        });

//        allItemsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//            }
//        });

//        allItemsRV.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                int scrollY = allItemsRV.getScrollY();
//                Log.d("scrollY", String.valueOf(scrollY));
//                if (scrollY>0){
//                    toolbarLayout.setVisibility(View.GONE);
//                }
//            }
//        });

//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_SCROLL:
//                        toolbarLayout.setVisibility(View.GONE);
//                        break;
////                    case scrollView.issc:
////                        toolbarLayout.setVisibility(View.VISIBLE);
////                        break;
//                }
//                return false;
//            }
//        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
//                if (i1>i3){
//                    toolbarLayout.setVisibility(View.GONE);
//                }else if (i1<i3){
//                    toolbarLayout.setVisibility(View.VISIBLE);
//                }
//            });
//        }

//        allItemsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy>0 && toolbarLayout.getVisibility() == View.VISIBLE){
//                    toolbarLayout.setVisibility(View.GONE);
//                }else if (dy<0 && toolbarLayout.getVisibility() != View.VISIBLE){
//                    toolbarLayout.setVisibility(View.VISIBLE);
//                }
//            }
//        });

//        allItemsRV.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
//                if (i1>i3 && toolbarLayout.getVisibility() != View.GONE){
//                    toolbarLayout.setVisibility(View.GONE);
//                }else if (i1<i3 && toolbarLayout.getVisibility() != View.VISIBLE){
//                    toolbarLayout.setVisibility(View.VISIBLE);
//                }
//        });

    }

    private void setData(){
        subCategoryTitle.setText(AppDefs.subCatName);
        featuredItemsTitle.setText(getResources().getString(R.string.featured) + " " + categoryName);
        allItemsTitle.setText(getResources().getString(R.string.all) + " " + categoryName);

        if(AppDefs.isSaved){
            categoryId = AppDefs.savedSearchAd.getCategoryId();
            currentLocation = AppDefs.savedSearchAd.getLocation();
            switch(categoryName) {
                case "Motors":
//                getUsedCarsAds();
                    switch (categoryId) {
                        case "2":
                        case "8":
                            currentBrand = AppDefs.brand;
                            currentModel = AppDefs.model;
                            filterUsedCars();
                            break;
                        case "5":
                            filterBoats();
                            break;
                        case "6":
                            filterMachines1();
                            break;
                        case "7":
                            filterParts1();
                            break;
                        case "9":
                            filterBikes1();
                            break;
                    }
                    typeId = "1";
                    break;
                case "Jobs":
                    filterJobs();
//                getJobAds(categoryId);
                    typeId = "3";
                    break;
                case "Services":
//                getServicesAds();
                    filterServices();
                    typeId = "4";
                    break;
                case "Business":
//                getBusinessAds();
                    filterBusiness();
                    typeId = "5";
                    break;
                case "Numbers":
//                    getNumbersAds();
                    filterNumbers();
                    typeId = "7";
                    break;
                case "Electronics":
//                    getElectronicsAds();
                    filterElectronics();
                    typeId = "8";
                    break;
                case "Classifieds":
//                    getClassifiedsAds();
                    filterClassifieds();
                    typeId = "6";
                    break;
//            case "Jobs":
//                getAds("3");
//                break;
//            case "Services":
//                getAds("4");
//                break;
//             case "Business":
//                 getAds("5");
//                 break;
//            case "Classifieds":
//                getAds("6");
//                break;
//            case "Numbers":
//                getAds("7");
//                break;
//            case "Electronics":
//                getAds("8");
//                break;
            }

        }else{
            if (AppDefs.brand.equals("-1")){
                switch (categoryName){
                    case "Motors":
                        switch (categoryId) {
                            case "2":
                            case "8":
                                getAllUsedCars();
                                currentBrand = AppDefs.brand;
                                currentModel = AppDefs.model;
                                break;
                            case "5":
                                getAllBoats();
                                break;
                            case "6":
                                getAllMachines();
                                break;
                            case "7":
                                getAllParts();
                                break;
                            case "9":
                                getAllBikes();
                                break;
                        }
                        typeId = "1";
                        break;
                    case "Jobs":
                        getAllJobs();
                        typeId = "3";
                        break;
                    case "Numbers":
                        getAllNumbers();
                        typeId = "7";
                        break;
                    case "Business":
                        filterBusiness();
                        typeId = "5";
                        break;
                    case "Services":
                        filterServices();
                        typeId = "4";
                        break;
                    case "Classifieds":
                        getAllClassifieds();
                        typeId = "6";
                        break;
                    case "Electronics":
                        getAllElectronics();
                        typeId = "8";
                        break;
                }
            }else if (AppDefs.model.equals("-1")){
                switch(categoryName) {
                    case "Motors":
                        switch (categoryId) {
                            case "2":
                            case "8":
                                currentBrand = AppDefs.brand;
                                currentModel = AppDefs.model;
                                filterUsedCars2();
                                break;
                            case "5":
                                filterBoats2();
                                break;
                            case "6":
                                filterMachines2();
                                break;
                            case "7":
                                filterParts2();
                                break;
                            case "9":
                                filterBikes2();
                                break;
                        }
                        typeId = "1";
                        break;
                    case "Jobs":
                        filterJobs2();
                        typeId = "3";
                        break;
                    case "Numbers":
                        filterNumbers2();
                        typeId = "7";
                        break;
                    case "Business":
                        filterBusiness();
                        typeId = "5";
                        break;
                    case "Services":
                        filterServices();
                        typeId = "4";
                        break;
                    case "Classifieds":
                        filterClassifieds2();
                        typeId = "6";
                        break;
                    case "Electronics":
                        filterElectronics2();
                        typeId = "8";
                        break;
                }
            }else {
                switch(categoryName) {
                    case "Motors":
//                getUsedCarsAds();
                        switch (categoryId) {
                            case "2":
                            case "8":
                                currentBrand = AppDefs.brand;
                                currentModel = AppDefs.model;
                                filterUsedCars1();
                                break;
                            case "5":
                                currentBoatAge = AppDefs.brand;
                                currentBoatUsage = AppDefs.model;
                                filterBoats1();
                                break;
                            case "6":
                                filterMachines1();
                                break;
                            case "7":
                                filterParts1();
                                break;
                            case "9":
                                filterBikes1();
                                break;
                        }
                        typeId = "1";
                        break;
                    case "Jobs":
                        filterJobs1();
//                getJobAds(categoryId);
                        typeId = "3";
                        break;
                    case "Services":
//                getServicesAds();
                        filterServices();
                        typeId = "4";
                        break;
                    case "Business":
//                getBusinessAds();
                        filterBusiness();
                        typeId = "5";
                        break;
                    case "Numbers":
                        if(categoryId.equals("17")){
                            numbersList();
                        }else if (categoryId.equals("18")){
                            mobileNumbersList();
                        }
//                    filterNumbers1();
                        typeId = "7";
                        break;
                    case "Electronics":
                        filterElectronics1();
                        typeId = "8";
                        break;
                    case "Classifieds":
                        filterClassifieds1();
                        typeId = "6";
                        break;
//            case "Jobs":
//                getAds("3");
//                break;
//            case "Services":
//                getAds("4");
//                break;
//             case "Business":
//                 getAds("5");
//                 break;
//            case "Classifieds":
//                getAds("6");
//                break;
//            case "Numbers":
//                getAds("7");
//                break;
//            case "Electronics":
//                getAds("8");
//                break;
                }
            }
        }
    }

    /* Motors */
    //UsedCars
    private void filterUsedCars1(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=2&UserId="+AppDefs.user.getId()+"&Maker="+AppDefs.brand+"&Model="+AppDefs.model, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
                closeFilter(motorsFilter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            Log.d("errorFilterMotors", error.getMessage());
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterUsedCars2(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=2&UserId="+AppDefs.user.getId()+"&Maker="+AppDefs.brand, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
                closeFilter(motorsFilter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            Log.d("errorFilterMotors", error.getMessage());
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void getAllUsedCars(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=2&UserId="+AppDefs.user.getId(), response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
//                closeFilter(motorsFilter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            Log.d("errorFilterMotors", error.getMessage());
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

//    private void filterUsedCars(){
//        usedCars.clear();
//        setUsedCarsAdapter();
//        fromPrice = String.valueOf((int)  usedCarsPriceBar.getCurrentValues().getLeftValue());
//        toPrice = String.valueOf((int)  usedCarsPriceBar.getCurrentValues().getRightValue());
//        fromYear = String.valueOf((int)  usedCarsYearBar.getCurrentValues().getLeftValue());
//        toYear = String.valueOf((int)  usedCarsYearBar.getCurrentValues().getRightValue());
//        fromKilo = String.valueOf((int)  usedCarsMileageBar.getCurrentValues().getLeftValue());
//        toKilo = String.valueOf((int)  usedCarsMileageBar.getCurrentValues().getRightValue());
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
//        StringRequest usedCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.MotorAds_URL+"FilterMotors?fromPrice=0&toPrice=10000000&fromYear=0&toYear=3000&fromKilometers=0&toKilometers=9999&maker=Dodge-&categoryId=2", response -> {
//            mainActivity.hideProgressDialog();
//            try {
//                JSONArray usedArray = new JSONArray(response);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> {
//            mainActivity.hideProgressDialog();
//
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
//                return params;
//            }
//        };
//        mainActivity.queue.add(usedCarsFilterRequest);
//    }

    private void filterUsedCars(){
        usedCars.clear();
        setUsedCarsAdapter();
        fromPrice = String.valueOf((int) usedCarsPriceBar.getCurrentValues().getLeftValue());
        toPrice = String.valueOf((int) usedCarsPriceBar.getCurrentValues().getRightValue());
        fromYear = String.valueOf((int) usedCarsYearBar.getCurrentValues().getLeftValue());
        toYear = String.valueOf((int) usedCarsYearBar.getCurrentValues().getRightValue());
        fromKilo = String.valueOf((int) usedCarsMileageBar.getCurrentValues().getLeftValue());
        toKilo = String.valueOf((int) usedCarsMileageBar.getCurrentValues().getRightValue());
        JSONObject params = new JSONObject();
        try {
            params.put("Trim", currentTrim);
            params.put("Model", currentModel);
            params.put("FromPrice", String.valueOf((int) usedCarsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) usedCarsPriceBar.getCurrentValues().getRightValue()));
            params.put("FromYear", String.valueOf((int) usedCarsYearBar.getCurrentValues().getLeftValue()));
            params.put("ToYear", String.valueOf((int) usedCarsYearBar.getCurrentValues().getRightValue()));
            params.put("FromKilometers", String.valueOf((int) usedCarsMileageBar.getCurrentValues().getLeftValue()));
            params.put("ToKilometers", String.valueOf((int) usedCarsMileageBar.getCurrentValues().getRightValue()));
            params.put("Maker", currentBrand);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
//            params.put("Warranty", "3");
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest userCarsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.MotorAds_URL+"FilterMotors2", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = response.getJSONArray("results");
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("Id"));
                    usedCarAd.setChats(adObj.getString("Chates"));
                    usedCarAd.setViews(adObj.getString("Views"));
                    usedCarAd.setTitle(adObj.getString("Title"));
                    usedCarAd.setDescription(adObj.getString("Description"));
                    usedCarAd.setEnLocation(adObj.getString("En_Location"));
                    usedCarAd.setArLocation(adObj.getString("Ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("Lat"));
                    usedCarAd.setLongitude(adObj.getString("Lng"));
                    usedCarAd.setStatus(adObj.getString("Status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("PostDate").substring(0, adObj.getString("PostDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("CategoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("CategoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("CategoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("SubCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("SubCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("SubCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("OtherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("SubTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("OtherSubType"));
                    usedCarAd.setTypeId(adObj.getString("TypeId"));
                    usedCarAd.setUserId(adObj.getString("UserId"));
                    usedCarAd.setAgentId(adObj.getString("AgentId"));
                    usedCarAd.setEnMaker(adObj.getString("En_Maker"));
                    usedCarAd.setArMaker(adObj.getString("Ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("En_Model"));
                    usedCarAd.setArModel(adObj.getString("Ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("En_Trim"));
                    usedCarAd.setArTrim(adObj.getString("Ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("En_Color"));
                    usedCarAd.setArColor(adObj.getString("Ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("Kilometers"));
                    usedCarAd.setYear(adObj.getString("Year"));
                    usedCarAd.setEnDoors(adObj.getString("En_Doors"));
                    usedCarAd.setArDoors(adObj.getString("Ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("Warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("En_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("Ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("En_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("Ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("En_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("Ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("En_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("Ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("En_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("Ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("En_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("Ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("En_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("Ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("Price"));
                    usedCarAd.setUserImage(adObj.getString("UserImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("PhoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("En_Condition"));
                    usedCarAd.setArCondition(adObj.getString("Ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("En_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("Ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("En_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("Ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("En_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("Ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("En_Age"));
                    usedCarAd.setArAge(adObj.getString("Ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("En_Usage"));
                    usedCarAd.setArUsage(adObj.getString("Ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("En_Length"));
                    usedCarAd.setArLength(adObj.getString("Ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("En_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("Ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("En_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("Ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("En_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("Ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("En_PartName"));
                    usedCarAd.setArPartName(adObj.getString("Ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("NameOfPart"));
                    JSONArray pics = adObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("IsFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }


        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

//    private void filterUsedCars(){
//        JSONObject params = new JSONObject();
//        try {
//            params.put("fromPrice", 0);
//            params.put("toPrice", 1000000000);
//            params.put("fromYear", 0);
//            params.put("toYear", 3000);
//            params.put("fromKilometers", 0);
//            params.put("toKilometers", 1000000000);
//            params.put("maker", "dodge-");
//            params.put("model", "");
////            params.put("trim", "");
////            params.put("horsepower", "");
////            params.put("age", "");
////            params.put("usage", "");
////            params.put("warranty", "0");
////            params.put("location", "");
////            params.put("partName", "");
//            params.put("categoryId", 2);
////            params.put("subCategoryId", "0");
////            params.put("subTypeId", "0");
////            params.put("sortBy", "1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest usedCarsFilterRequest = new JsonObjectRequest(Request.Method.GET, "https://apinew.insouq.com/api/MotorAds/FilterMotors2",params, response -> {
//
//            try {
//                JSONArray result = response.getJSONArray("results");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> {
//            Log.d("error", "error");
//
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJJZCI6Ijc4IiwiZW1haWwiOiJ5YXppZGhhbGFoMkBnbWFpbC5jb20iLCJzdWIiOiJ5YXppZGhhbGFoMkBnbWFpbC5jb20iLCJqdGkiOiI3YzE3NTYzZC0wYzg1LTQ0NDgtOTQ2NC04Mjk0YzhkOWYxZDEiLCJuYmYiOjE2NTA3MTU4NzUsImV4cCI6MTY1MTMyMDY3NSwiaWF0IjoxNjUwNzE1ODc1fQ.B5MJITxKe7o3bX3sT6rA4bzk7gqE3QQKBbF2d6knZnw");
//                return params;
//            }
//
//        };
//        mainActivity.queue.add(usedCarsFilterRequest);
//    }

    //Boats
    private void filterBoats1(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=5&UserId="+AppDefs.user.getId()+"&Age="+AppDefs.brand+"&Usage="+AppDefs.model, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
                closeFilter(motorsFilter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterBoats2(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=5&UserId="+AppDefs.user.getId()+"&Age="+AppDefs.brand, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
                closeFilter(motorsFilter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void getAllBoats(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=5&UserId="+AppDefs.user.getId(), response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
                closeFilter(motorsFilter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterBoats(){
        usedCars.clear();
        setUsedCarsAdapter();
        fromPrice = String.valueOf((int) boatsPriceBar.getCurrentValues().getLeftValue());
        toPrice = String.valueOf((int) boatsPriceBar.getCurrentValues().getRightValue());
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) boatsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) boatsPriceBar.getCurrentValues().getRightValue()));
            params.put("Age", currentBoatAge);
            params.put("Usage", currentBoatUsage);
            params.put("Warranty", currentBoatWarranty);
            params.put("Horsepower", currentBoatHorsePower);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest userCarsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.MotorAds_URL+"FilterMotors2", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = response.getJSONArray("results");
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("Id"));
                    usedCarAd.setChats(adObj.getString("Chates"));
                    usedCarAd.setViews(adObj.getString("Views"));
                    usedCarAd.setTitle(adObj.getString("Title"));
                    usedCarAd.setDescription(adObj.getString("Description"));
                    usedCarAd.setEnLocation(adObj.getString("En_Location"));
                    usedCarAd.setArLocation(adObj.getString("Ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("Lat"));
                    usedCarAd.setLongitude(adObj.getString("Lng"));
                    usedCarAd.setStatus(adObj.getString("Status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("PostDate").substring(0, adObj.getString("PostDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("CategoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("CategoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("CategoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("SubCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("SubCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("SubCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("OtherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("SubTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("OtherSubType"));
                    usedCarAd.setTypeId(adObj.getString("TypeId"));
                    usedCarAd.setUserId(adObj.getString("UserId"));
                    usedCarAd.setAgentId(adObj.getString("AgentId"));
                    usedCarAd.setEnMaker(adObj.getString("En_Maker"));
                    usedCarAd.setArMaker(adObj.getString("Ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("En_Model"));
                    usedCarAd.setArModel(adObj.getString("Ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("En_Trim"));
                    usedCarAd.setArTrim(adObj.getString("Ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("En_Color"));
                    usedCarAd.setArColor(adObj.getString("Ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("Kilometers"));
                    usedCarAd.setYear(adObj.getString("Year"));
                    usedCarAd.setEnDoors(adObj.getString("En_Doors"));
                    usedCarAd.setArDoors(adObj.getString("Ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("Warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("En_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("Ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("En_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("Ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("En_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("Ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("En_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("Ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("En_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("Ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("En_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("Ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("En_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("Ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("Price"));
                    usedCarAd.setUserImage(adObj.getString("UserImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("PhoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("En_Condition"));
                    usedCarAd.setArCondition(adObj.getString("Ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("En_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("Ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("En_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("Ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("En_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("Ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("En_Age"));
                    usedCarAd.setArAge(adObj.getString("Ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("En_Usage"));
                    usedCarAd.setArUsage(adObj.getString("Ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("En_Length"));
                    usedCarAd.setArLength(adObj.getString("Ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("En_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("Ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("En_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("Ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("En_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("Ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("En_PartName"));
                    usedCarAd.setArPartName(adObj.getString("Ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("NameOfPart"));
                    JSONArray pics = adObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("IsFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    //Machines
    private void filterMachines1(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=6&UserId="+AppDefs.user.getId()+"&SubCategory="+AppDefs.brand+"&SubType="+AppDefs.model, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterMachines2(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=6&UserId="+AppDefs.user.getId()+"&SubCategory="+AppDefs.brand, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void getAllMachines(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=6&UserId="+AppDefs.user.getId(), response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterMachines(){
        usedCars.clear();
        setUsedCarsAdapter();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) machinesPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) machinesPriceBar.getCurrentValues().getRightValue()));
            params.put("FromYear", String.valueOf((int) machinesYearBar.getCurrentValues().getLeftValue()));
            params.put("ToYear", String.valueOf((int) machinesYearBar.getCurrentValues().getRightValue()));
            params.put("SubCategoryId", machineryCategory);
            params.put("SubTypeId", machinerySubCat);
            params.put("Horsepower", machineryHorsepower);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest userCarsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.MotorAds_URL+"FilterMotors2", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = response.getJSONArray("results");
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("Id"));
                    usedCarAd.setChats(adObj.getString("Chates"));
                    usedCarAd.setViews(adObj.getString("Views"));
                    usedCarAd.setTitle(adObj.getString("Title"));
                    usedCarAd.setDescription(adObj.getString("Description"));
                    usedCarAd.setEnLocation(adObj.getString("En_Location"));
                    usedCarAd.setArLocation(adObj.getString("Ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("Lat"));
                    usedCarAd.setLongitude(adObj.getString("Lng"));
                    usedCarAd.setStatus(adObj.getString("Status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("PostDate").substring(0, adObj.getString("PostDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("CategoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("CategoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("CategoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("SubCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("SubCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("SubCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("OtherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("SubTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("OtherSubType"));
                    usedCarAd.setTypeId(adObj.getString("TypeId"));
                    usedCarAd.setUserId(adObj.getString("UserId"));
                    usedCarAd.setAgentId(adObj.getString("AgentId"));
                    usedCarAd.setEnMaker(adObj.getString("En_Maker"));
                    usedCarAd.setArMaker(adObj.getString("Ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("En_Model"));
                    usedCarAd.setArModel(adObj.getString("Ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("En_Trim"));
                    usedCarAd.setArTrim(adObj.getString("Ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("En_Color"));
                    usedCarAd.setArColor(adObj.getString("Ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("Kilometers"));
                    usedCarAd.setYear(adObj.getString("Year"));
                    usedCarAd.setEnDoors(adObj.getString("En_Doors"));
                    usedCarAd.setArDoors(adObj.getString("Ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("Warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("En_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("Ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("En_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("Ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("En_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("Ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("En_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("Ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("En_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("Ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("En_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("Ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("En_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("Ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("Price"));
                    usedCarAd.setUserImage(adObj.getString("UserImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("PhoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("En_Condition"));
                    usedCarAd.setArCondition(adObj.getString("Ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("En_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("Ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("En_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("Ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("En_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("Ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("En_Age"));
                    usedCarAd.setArAge(adObj.getString("Ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("En_Usage"));
                    usedCarAd.setArUsage(adObj.getString("Ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("En_Length"));
                    usedCarAd.setArLength(adObj.getString("Ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("En_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("Ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("En_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("Ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("En_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("Ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("En_PartName"));
                    usedCarAd.setArPartName(adObj.getString("Ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("NameOfPart"));
                    JSONArray pics = adObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("IsFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    //Bikes
    private void filterBikes1(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=9&UserId="+AppDefs.user.getId()+"&SubCategory="+AppDefs.brand+"&SubType="+AppDefs.model, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){


            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getLeftValue()));
                params.put("toPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getRightValue()));
                params.put("fromYear", String.valueOf((int) bikesYearBar.getCurrentValues().getLeftValue()));
                params.put("toYear", String.valueOf((int) bikesYearBar.getCurrentValues().getRightValue()));
                params.put("fromKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getLeftValue()));
                params.put("toKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getRightValue()));
                params.put("subCategoryId", AppDefs.brand);
                params.put("subTypeId", AppDefs.model);
                params.put("postedInDL", bikeAdPosted);
                params.put("location", currentLocation);
                params.put("categoryId", categoryId);
                params.put("sortBy", "1");
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterBikes2(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=9&UserId="+AppDefs.user.getId()+"&SubCategory="+AppDefs.brand, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){


            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getLeftValue()));
                params.put("toPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getRightValue()));
                params.put("fromYear", String.valueOf((int) bikesYearBar.getCurrentValues().getLeftValue()));
                params.put("toYear", String.valueOf((int) bikesYearBar.getCurrentValues().getRightValue()));
                params.put("fromKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getLeftValue()));
                params.put("toKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getRightValue()));
                params.put("subCategoryId", AppDefs.brand);
                params.put("subTypeId", bikeSubCat);
                params.put("postedInDL", bikeAdPosted);
                params.put("location", currentLocation);
                params.put("categoryId", categoryId);
                params.put("sortBy", "1");
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void getAllBikes(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=9&UserId="+AppDefs.user.getId(), response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){


            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fromPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getLeftValue()));
                params.put("toPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getRightValue()));
                params.put("fromYear", String.valueOf((int) bikesYearBar.getCurrentValues().getLeftValue()));
                params.put("toYear", String.valueOf((int) bikesYearBar.getCurrentValues().getRightValue()));
                params.put("fromKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getLeftValue()));
                params.put("toKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getRightValue()));
                params.put("subCategoryId", AppDefs.brand);
                params.put("subTypeId", bikeSubCat);
                params.put("postedInDL", bikeAdPosted);
                params.put("location", currentLocation);
                params.put("categoryId", categoryId);
                params.put("sortBy", "1");
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterBikes(){
        usedCars.clear();
        setUsedCarsAdapter();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) bikesPriceBar.getCurrentValues().getRightValue()));
            params.put("FromYear", String.valueOf((int) bikesYearBar.getCurrentValues().getLeftValue()));
            params.put("ToYear", String.valueOf((int) bikesYearBar.getCurrentValues().getRightValue()));
            params.put("FromKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getLeftValue()));
            params.put("ToKilometers", String.valueOf((int) bikesMileageBar.getCurrentValues().getRightValue()));
            params.put("SubCategoryId", bikeCat);
            params.put("SubTypeId", bikeSubCat);
            params.put("PostedInDL", bikeAdPosted);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest userCarsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.MotorAds_URL+"FilterMotors2", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = response.getJSONArray("results");
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("Id"));
                    usedCarAd.setChats(adObj.getString("Chates"));
                    usedCarAd.setViews(adObj.getString("Views"));
                    usedCarAd.setTitle(adObj.getString("Title"));
                    usedCarAd.setDescription(adObj.getString("Description"));
                    usedCarAd.setEnLocation(adObj.getString("En_Location"));
                    usedCarAd.setArLocation(adObj.getString("Ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("Lat"));
                    usedCarAd.setLongitude(adObj.getString("Lng"));
                    usedCarAd.setStatus(adObj.getString("Status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("PostDate").substring(0, adObj.getString("PostDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("CategoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("CategoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("CategoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("SubCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("SubCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("SubCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("OtherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("SubTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("OtherSubType"));
                    usedCarAd.setTypeId(adObj.getString("TypeId"));
                    usedCarAd.setUserId(adObj.getString("UserId"));
                    usedCarAd.setAgentId(adObj.getString("AgentId"));
                    usedCarAd.setEnMaker(adObj.getString("En_Maker"));
                    usedCarAd.setArMaker(adObj.getString("Ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("En_Model"));
                    usedCarAd.setArModel(adObj.getString("Ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("En_Trim"));
                    usedCarAd.setArTrim(adObj.getString("Ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("En_Color"));
                    usedCarAd.setArColor(adObj.getString("Ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("Kilometers"));
                    usedCarAd.setYear(adObj.getString("Year"));
                    usedCarAd.setEnDoors(adObj.getString("En_Doors"));
                    usedCarAd.setArDoors(adObj.getString("Ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("Warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("En_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("Ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("En_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("Ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("En_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("Ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("En_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("Ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("En_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("Ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("En_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("Ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("En_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("Ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("Price"));
                    usedCarAd.setUserImage(adObj.getString("UserImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("PhoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("En_Condition"));
                    usedCarAd.setArCondition(adObj.getString("Ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("En_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("Ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("En_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("Ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("En_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("Ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("En_Age"));
                    usedCarAd.setArAge(adObj.getString("Ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("En_Usage"));
                    usedCarAd.setArUsage(adObj.getString("Ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("En_Length"));
                    usedCarAd.setArLength(adObj.getString("Ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("En_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("Ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("En_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("Ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("En_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("Ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("En_PartName"));
                    usedCarAd.setArPartName(adObj.getString("Ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("NameOfPart"));
                    JSONArray pics = adObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("IsFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    //Parts
    private void filterParts1(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=7&UserId="+AppDefs.user.getId()+"&SubCategory="+AppDefs.brand+"&SubType="+AppDefs.model, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterParts2(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST,Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=7&UserId="+AppDefs.user.getId()+"&SubCategory="+AppDefs.brand , response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void getAllParts(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.POST,Urls.Ads_URL+"GetAds?TypeId=1&CategoryId=7&UserId="+AppDefs.user.getId(), response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);
    }

    private void filterParts(){
        usedCars.clear();
        setUsedCarsAdapter();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) partPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) partPriceBar.getCurrentValues().getRightValue()));
            params.put("FromYear", String.valueOf((int) partYearBar.getCurrentValues().getLeftValue()));
            params.put("ToYear", String.valueOf((int) partYearBar.getCurrentValues().getRightValue()));
            params.put("SubCategoryId", partCat);
            params.put("SubTypeId", partMake);
            params.put("PartName", partName);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest userCarsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.MotorAds_URL+"FilterMotors2", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = response.getJSONArray("results");
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("Id"));
                    usedCarAd.setChats(adObj.getString("Chates"));
                    usedCarAd.setViews(adObj.getString("Views"));
                    usedCarAd.setTitle(adObj.getString("Title"));
                    usedCarAd.setDescription(adObj.getString("Description"));
                    usedCarAd.setEnLocation(adObj.getString("En_Location"));
                    usedCarAd.setArLocation(adObj.getString("Ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("Lat"));
                    usedCarAd.setLongitude(adObj.getString("Lng"));
                    usedCarAd.setStatus(adObj.getString("Status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("PostDate").substring(0, adObj.getString("PostDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("CategoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("CategoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("CategoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("SubCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("SubCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("SubCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("OtherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("SubTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("SubTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("SubTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("OtherSubType"));
                    usedCarAd.setTypeId(adObj.getString("TypeId"));
                    usedCarAd.setUserId(adObj.getString("UserId"));
                    usedCarAd.setAgentId(adObj.getString("AgentId"));
                    usedCarAd.setEnMaker(adObj.getString("En_Maker"));
                    usedCarAd.setArMaker(adObj.getString("Ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("En_Model"));
                    usedCarAd.setArModel(adObj.getString("Ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("En_Trim"));
                    usedCarAd.setArTrim(adObj.getString("Ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("En_Color"));
                    usedCarAd.setArColor(adObj.getString("Ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("Kilometers"));
                    usedCarAd.setYear(adObj.getString("Year"));
                    usedCarAd.setEnDoors(adObj.getString("En_Doors"));
                    usedCarAd.setArDoors(adObj.getString("Ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("Warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("En_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("Ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("En_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("Ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("En_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("Ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("En_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("Ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("En_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("Ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("En_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("Ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("En_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("Ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("Price"));
                    usedCarAd.setUserImage(adObj.getString("UserImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("PhoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("En_Condition"));
                    usedCarAd.setArCondition(adObj.getString("Ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("En_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("Ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("En_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("Ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("En_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("Ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("En_Age"));
                    usedCarAd.setArAge(adObj.getString("Ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("En_Usage"));
                    usedCarAd.setArUsage(adObj.getString("Ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("En_Length"));
                    usedCarAd.setArLength(adObj.getString("Ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("En_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("Ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("En_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("Ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("En_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("Ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("En_PartName"));
                    usedCarAd.setArPartName(adObj.getString("Ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("NameOfPart"));
                    JSONArray pics = adObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("IsFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(userCarsFilterRequest);
    }

    /* Jobs */
    private void getAllJobs(){
        jobAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterJobRequest = new JsonObjectRequest(Request.Method.POST, Urls.JobAds_URL+"FilterJobs", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobsArray = response.getJSONArray("results");
                for (int i=0; i<jobsArray.length(); i++){
                    JSONObject jobObj = jobsArray.getJSONObject(i);
                    JobAd jobAd = new JobAd();
                    jobAd.setId(jobObj.getString("Id"));
                    jobAd.setAgentId(jobObj.getString("AgentId"));
                    jobAd.setTitle(jobObj.getString("Title"));
                    jobAd.setDescription(jobObj.getString("Description"));
                    jobAd.setEnLocation(jobObj.getString("En_Location"));
                    jobAd.setArLocation(jobObj.getString("Ar_Location"));
                    jobAd.setLat(jobObj.getString("Lat"));
                    jobAd.setLng(jobObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(jobObj.getString("PostDate").substring(0, jobObj.getString("PostDate").lastIndexOf(".")));
                    jobAd.setPostedDate(postedDate);
                    jobAd.setCategoryId(jobObj.getString("CategoryId"));
                    jobAd.setCategoryArName(jobObj.getString("CategoryArName"));
                    jobAd.setCategoryEnName(jobObj.getString("CategoryEnName"));
                    jobAd.setUserId(jobObj.getString("UserId"));
                    jobAd.setArJobType(jobObj.getString("Ar_JobType"));
                    jobAd.setEnJobType(jobObj.getString("En_JobType"));
                    jobAd.setOtherJobType(jobObj.getString("OtherJobType"));
                    jobAd.setPhoneNumber(jobObj.getString("PhoneNumber"));
                    jobAd.setCv(jobObj.getString("CV"));
                    jobAd.setArGender(jobObj.getString("Ar_Gender"));
                    jobAd.setEnGender(jobObj.getString("En_Gender"));
                    jobAd.setEnNationality(jobObj.getString("En_Nationality"));
                    jobAd.setArNationality(jobObj.getString("Ar_Nationality"));
                    jobAd.setArCurrentLocation(jobObj.getString("Ar_CurrentLocation"));
                    jobAd.setEnCurrentLocation(jobObj.getString("En_CurrentLocation"));
                    jobAd.setEnEducationalLevel(jobObj.getString("En_EducationLevel"));
                    jobAd.setArEducationalLevel(jobObj.getString("Ar_EducationLevel"));
                    jobAd.setCurrentPosition(jobObj.getString("CurrentPosition"));
                    jobAd.setArWorkExperience(jobObj.getString("Ar_WorkExperience"));
                    jobAd.setEnWorkExperience(jobObj.getString("En_Commitment"));
                    jobAd.setArCommitment(jobObj.getString("Ar_Commitment"));
                    jobAd.setEnCommitment(jobObj.getString("En_Commitment"));
                    jobAd.setArNoticePeriod(jobObj.getString("Ar_NoticePeriod"));
                    jobAd.setEnNoticePeriod(jobObj.getString("En_NoticePeriod"));
                    jobAd.setArVisaStatus(jobObj.getString("Ar_VisaStatus"));
                    jobAd.setEnVisaStatus(jobObj.getString("En_VisaStatus"));
                    jobAd.setEnCareerLevel(jobObj.getString("En_CareerLevel"));
                    jobAd.setArCareerLevel(jobObj.getString("Ar_CareerLevel"));
                    jobAd.setExpectedSalary(jobObj.getString("ExpectedMonthlySalary"));
                    jobAd.setArEmploymentType(jobObj.getString("Ar_EmploymentType"));
                    jobAd.setEnEmploymentType(jobObj.getString("En_EmploymentType"));
                    jobAd.setArMinWorkExperience(jobObj.getString("Ar_MinWorkExperience"));
                    jobAd.setEnMinWorkExperience(jobObj.getString("En_MinWorkExperience"));
                    jobAd.setEnMinEducationalLevel(jobObj.getString("En_MinEducationLevel"));
                    jobAd.setArMinEducationalLevel(jobObj.getString("Ar_MinEducationLevel"));
                    jobAd.setCompanyName(jobObj.getString("CompanyName"));
                    jobAd.setJobTitle(jobObj.getString("JobTitle"));
                    jobAd.setIsFav(jobObj.getString("IsFavorite"));
                    jobAds.add(jobAd);
                }
                AppDefs.jobAds = jobAds;
                if (categoryId.equals("3")){
                    setOpeningJobsAdapter();
                }else {
                    setWantedJobsAdapter();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterJobRequest);
    }

    private void filterJobs1(){
        jobAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("JobType", jobType);
            params.put("CareerLevel", AppDefs.brand);
            params.put("WorkExperience", workExperience);
            params.put("EducationLevel", educationLevel);
            params.put("Commitment", AppDefs.model);
            params.put("PostedIn", postedIn);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterJobRequest = new JsonObjectRequest(Request.Method.POST, Urls.JobAds_URL+"FilterJobs", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobsArray = response.getJSONArray("results");
                for (int i=0; i<jobsArray.length(); i++){
                    JSONObject jobObj = jobsArray.getJSONObject(i);
                    JobAd jobAd = new JobAd();
                    jobAd.setId(jobObj.getString("Id"));
                    jobAd.setAgentId(jobObj.getString("AgentId"));
                    jobAd.setTitle(jobObj.getString("Title"));
                    jobAd.setDescription(jobObj.getString("Description"));
                    jobAd.setEnLocation(jobObj.getString("En_Location"));
                    jobAd.setArLocation(jobObj.getString("Ar_Location"));
                    jobAd.setLat(jobObj.getString("Lat"));
                    jobAd.setLng(jobObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(jobObj.getString("PostDate").substring(0, jobObj.getString("PostDate").lastIndexOf(".")));
                    jobAd.setPostedDate(postedDate);
                    jobAd.setCategoryId(jobObj.getString("CategoryId"));
                    jobAd.setCategoryArName(jobObj.getString("CategoryArName"));
                    jobAd.setCategoryEnName(jobObj.getString("CategoryEnName"));
                    jobAd.setUserId(jobObj.getString("UserId"));
                    jobAd.setArJobType(jobObj.getString("Ar_JobType"));
                    jobAd.setEnJobType(jobObj.getString("En_JobType"));
                    jobAd.setOtherJobType(jobObj.getString("OtherJobType"));
                    jobAd.setPhoneNumber(jobObj.getString("PhoneNumber"));
                    jobAd.setCv(jobObj.getString("CV"));
                    jobAd.setArGender(jobObj.getString("Ar_Gender"));
                    jobAd.setEnGender(jobObj.getString("En_Gender"));
                    jobAd.setEnNationality(jobObj.getString("En_Nationality"));
                    jobAd.setArNationality(jobObj.getString("Ar_Nationality"));
                    jobAd.setArCurrentLocation(jobObj.getString("Ar_CurrentLocation"));
                    jobAd.setEnCurrentLocation(jobObj.getString("En_CurrentLocation"));
                    jobAd.setEnEducationalLevel(jobObj.getString("En_EducationLevel"));
                    jobAd.setArEducationalLevel(jobObj.getString("Ar_EducationLevel"));
                    jobAd.setCurrentPosition(jobObj.getString("CurrentPosition"));
                    jobAd.setArWorkExperience(jobObj.getString("Ar_WorkExperience"));
                    jobAd.setEnWorkExperience(jobObj.getString("En_Commitment"));
                    jobAd.setArCommitment(jobObj.getString("Ar_Commitment"));
                    jobAd.setEnCommitment(jobObj.getString("En_Commitment"));
                    jobAd.setArNoticePeriod(jobObj.getString("Ar_NoticePeriod"));
                    jobAd.setEnNoticePeriod(jobObj.getString("En_NoticePeriod"));
                    jobAd.setArVisaStatus(jobObj.getString("Ar_VisaStatus"));
                    jobAd.setEnVisaStatus(jobObj.getString("En_VisaStatus"));
                    jobAd.setEnCareerLevel(jobObj.getString("En_CareerLevel"));
                    jobAd.setArCareerLevel(jobObj.getString("Ar_CareerLevel"));
                    jobAd.setExpectedSalary(jobObj.getString("ExpectedMonthlySalary"));
                    jobAd.setArEmploymentType(jobObj.getString("Ar_EmploymentType"));
                    jobAd.setEnEmploymentType(jobObj.getString("En_EmploymentType"));
                    jobAd.setArMinWorkExperience(jobObj.getString("Ar_MinWorkExperience"));
                    jobAd.setEnMinWorkExperience(jobObj.getString("En_MinWorkExperience"));
                    jobAd.setEnMinEducationalLevel(jobObj.getString("En_MinEducationLevel"));
                    jobAd.setArMinEducationalLevel(jobObj.getString("Ar_MinEducationLevel"));
                    jobAd.setCompanyName(jobObj.getString("CompanyName"));
                    jobAd.setJobTitle(jobObj.getString("JobTitle"));
                    jobAd.setIsFav(jobObj.getString("IsFavorite"));
                    jobAds.add(jobAd);
                }
                AppDefs.jobAds = jobAds;
                if (categoryId.equals("3")){
                    setOpeningJobsAdapter();
                }else {
                    setWantedJobsAdapter();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterJobRequest);
    }

    private void filterJobs2(){
        jobAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("JobType", jobType);
            params.put("CareerLevel", AppDefs.brand);
            params.put("WorkExperience", workExperience);
            params.put("EducationLevel", educationLevel);
            params.put("Commitment", employmentType);
            params.put("PostedIn", postedIn);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterJobRequest = new JsonObjectRequest(Request.Method.POST, Urls.JobAds_URL+"FilterJobs", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobsArray = response.getJSONArray("results");
                for (int i=0; i<jobsArray.length(); i++){
                    JSONObject jobObj = jobsArray.getJSONObject(i);
                    JobAd jobAd = new JobAd();
                    jobAd.setId(jobObj.getString("Id"));
                    jobAd.setAgentId(jobObj.getString("AgentId"));
                    jobAd.setTitle(jobObj.getString("Title"));
                    jobAd.setDescription(jobObj.getString("Description"));
                    jobAd.setEnLocation(jobObj.getString("En_Location"));
                    jobAd.setArLocation(jobObj.getString("Ar_Location"));
                    jobAd.setLat(jobObj.getString("Lat"));
                    jobAd.setLng(jobObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(jobObj.getString("PostDate").substring(0, jobObj.getString("PostDate").lastIndexOf(".")));
                    jobAd.setPostedDate(postedDate);
                    jobAd.setCategoryId(jobObj.getString("CategoryId"));
                    jobAd.setCategoryArName(jobObj.getString("CategoryArName"));
                    jobAd.setCategoryEnName(jobObj.getString("CategoryEnName"));
                    jobAd.setUserId(jobObj.getString("UserId"));
                    jobAd.setArJobType(jobObj.getString("Ar_JobType"));
                    jobAd.setEnJobType(jobObj.getString("En_JobType"));
                    jobAd.setOtherJobType(jobObj.getString("OtherJobType"));
                    jobAd.setPhoneNumber(jobObj.getString("PhoneNumber"));
                    jobAd.setCv(jobObj.getString("CV"));
                    jobAd.setArGender(jobObj.getString("Ar_Gender"));
                    jobAd.setEnGender(jobObj.getString("En_Gender"));
                    jobAd.setEnNationality(jobObj.getString("En_Nationality"));
                    jobAd.setArNationality(jobObj.getString("Ar_Nationality"));
                    jobAd.setArCurrentLocation(jobObj.getString("Ar_CurrentLocation"));
                    jobAd.setEnCurrentLocation(jobObj.getString("En_CurrentLocation"));
                    jobAd.setEnEducationalLevel(jobObj.getString("En_EducationLevel"));
                    jobAd.setArEducationalLevel(jobObj.getString("Ar_EducationLevel"));
                    jobAd.setCurrentPosition(jobObj.getString("CurrentPosition"));
                    jobAd.setArWorkExperience(jobObj.getString("Ar_WorkExperience"));
                    jobAd.setEnWorkExperience(jobObj.getString("En_Commitment"));
                    jobAd.setArCommitment(jobObj.getString("Ar_Commitment"));
                    jobAd.setEnCommitment(jobObj.getString("En_Commitment"));
                    jobAd.setArNoticePeriod(jobObj.getString("Ar_NoticePeriod"));
                    jobAd.setEnNoticePeriod(jobObj.getString("En_NoticePeriod"));
                    jobAd.setArVisaStatus(jobObj.getString("Ar_VisaStatus"));
                    jobAd.setEnVisaStatus(jobObj.getString("En_VisaStatus"));
                    jobAd.setEnCareerLevel(jobObj.getString("En_CareerLevel"));
                    jobAd.setArCareerLevel(jobObj.getString("Ar_CareerLevel"));
                    jobAd.setExpectedSalary(jobObj.getString("ExpectedMonthlySalary"));
                    jobAd.setArEmploymentType(jobObj.getString("Ar_EmploymentType"));
                    jobAd.setEnEmploymentType(jobObj.getString("En_EmploymentType"));
                    jobAd.setArMinWorkExperience(jobObj.getString("Ar_MinWorkExperience"));
                    jobAd.setEnMinWorkExperience(jobObj.getString("En_MinWorkExperience"));
                    jobAd.setEnMinEducationalLevel(jobObj.getString("En_MinEducationLevel"));
                    jobAd.setArMinEducationalLevel(jobObj.getString("Ar_MinEducationLevel"));
                    jobAd.setCompanyName(jobObj.getString("CompanyName"));
                    jobAd.setJobTitle(jobObj.getString("JobTitle"));
                    jobAd.setIsFav(jobObj.getString("IsFavorite"));
                    jobAds.add(jobAd);
                }
                AppDefs.jobAds = jobAds;
                if (categoryId.equals("3")){
                    setOpeningJobsAdapter();
                }else {
                    setWantedJobsAdapter();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterJobRequest);
    }

    private void filterJobs(){
        jobAds.clear();
        setOpeningJobsAdapter();
        setWantedJobsAdapter();
        JSONObject params = new JSONObject();
        try {
            params.put("JobType", jobType);
            params.put("CareerLevel", professionalLevel);
            params.put("WorkExperience", workExperience);
            params.put("EducationLevel", educationLevel);
            params.put("Commitment", employmentType);
            params.put("PostedIn", postedIn);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterJobRequest = new JsonObjectRequest(Request.Method.POST, Urls.JobAds_URL+"FilterJobs", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobsArray = response.getJSONArray("results");
                for (int i=0; i<jobsArray.length(); i++){
                    JSONObject jobObj = jobsArray.getJSONObject(i);
                    JobAd jobAd = new JobAd();
                    jobAd.setId(jobObj.getString("Id"));
                    jobAd.setAgentId(jobObj.getString("AgentId"));
                    jobAd.setTitle(jobObj.getString("Title"));
                    jobAd.setDescription(jobObj.getString("Description"));
                    jobAd.setEnLocation(jobObj.getString("En_Location"));
                    jobAd.setArLocation(jobObj.getString("Ar_Location"));
                    jobAd.setLat(jobObj.getString("Lat"));
                    jobAd.setLng(jobObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(jobObj.getString("PostDate").substring(0, jobObj.getString("PostDate").lastIndexOf(".")));
                    jobAd.setPostedDate(postedDate);
                    jobAd.setCategoryId(jobObj.getString("CategoryId"));
                    jobAd.setCategoryArName(jobObj.getString("CategoryArName"));
                    jobAd.setCategoryEnName(jobObj.getString("CategoryEnName"));
                    jobAd.setUserId(jobObj.getString("UserId"));
                    jobAd.setArJobType(jobObj.getString("Ar_JobType"));
                    jobAd.setEnJobType(jobObj.getString("En_JobType"));
                    jobAd.setOtherJobType(jobObj.getString("OtherJobType"));
                    jobAd.setPhoneNumber(jobObj.getString("PhoneNumber"));
                    jobAd.setCv(jobObj.getString("CV"));
                    jobAd.setArGender(jobObj.getString("Ar_Gender"));
                    jobAd.setEnGender(jobObj.getString("En_Gender"));
                    jobAd.setEnNationality(jobObj.getString("En_Nationality"));
                    jobAd.setArNationality(jobObj.getString("Ar_Nationality"));
                    jobAd.setArCurrentLocation(jobObj.getString("Ar_CurrentLocation"));
                    jobAd.setEnCurrentLocation(jobObj.getString("En_CurrentLocation"));
                    jobAd.setEnEducationalLevel(jobObj.getString("En_EducationLevel"));
                    jobAd.setArEducationalLevel(jobObj.getString("Ar_EducationLevel"));
                    jobAd.setCurrentPosition(jobObj.getString("CurrentPosition"));
                    jobAd.setArWorkExperience(jobObj.getString("Ar_WorkExperience"));
                    jobAd.setEnWorkExperience(jobObj.getString("En_Commitment"));
                    jobAd.setArCommitment(jobObj.getString("Ar_Commitment"));
                    jobAd.setEnCommitment(jobObj.getString("En_Commitment"));
                    jobAd.setArNoticePeriod(jobObj.getString("Ar_NoticePeriod"));
                    jobAd.setEnNoticePeriod(jobObj.getString("En_NoticePeriod"));
                    jobAd.setArVisaStatus(jobObj.getString("Ar_VisaStatus"));
                    jobAd.setEnVisaStatus(jobObj.getString("En_VisaStatus"));
                    jobAd.setEnCareerLevel(jobObj.getString("En_CareerLevel"));
                    jobAd.setArCareerLevel(jobObj.getString("Ar_CareerLevel"));
                    jobAd.setExpectedSalary(jobObj.getString("ExpectedMonthlySalary"));
                    jobAd.setArEmploymentType(jobObj.getString("Ar_EmploymentType"));
                    jobAd.setEnEmploymentType(jobObj.getString("En_EmploymentType"));
                    jobAd.setArMinWorkExperience(jobObj.getString("Ar_MinWorkExperience"));
                    jobAd.setEnMinWorkExperience(jobObj.getString("En_MinWorkExperience"));
                    jobAd.setEnMinEducationalLevel(jobObj.getString("En_MinEducationLevel"));
                    jobAd.setArMinEducationalLevel(jobObj.getString("Ar_MinEducationLevel"));
                    jobAd.setCompanyName(jobObj.getString("CompanyName"));
                    jobAd.setJobTitle(jobObj.getString("JobTitle"));
                    jobAd.setIsFav(jobObj.getString("IsFavorite"));
                    jobAds.add(jobAd);
                }
                AppDefs.jobAds = jobAds;
                if (categoryId.equals("3")){
                    setOpeningJobsAdapter();
                }else {
                    setWantedJobsAdapter();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterJobRequest);
    }

    /* Services */
    private void filterServices(){
        serviceAds.clear();
        setServicesAdsAdapter();
        JSONObject params = new JSONObject();
        try {
            params.put("SubCategoryId", serviceSubCat);
            params.put("PostedIn", servicePostIn);
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterServicesRequest = new JsonObjectRequest(Request.Method.POST, Urls.ServicesAds_URL+"FilterServices", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray serviceAdsArray = response.getJSONArray("results");
                for (int i=0; i<serviceAdsArray.length(); i++){
                    JSONObject serviceObj = serviceAdsArray.getJSONObject(i);
                    ServiceAd serviceAd = new ServiceAd();
                    serviceAd.setId(serviceObj.getString("Id"));
                    serviceAd.setAgentId(serviceObj.getString("AgentId"));
                    serviceAd.setTitle(serviceObj.getString("Title"));
                    serviceAd.setEnLocation(serviceObj.getString("En_Location"));
                    serviceAd.setArLocation(serviceObj.getString("Ar_Location"));
                    String postedDate = mainActivity.convertDate(serviceObj.getString("PostDate").substring(0, serviceObj.getString("PostDate").lastIndexOf(".")));
                    serviceAd.setPostedDate(postedDate);
                    serviceAd.setCategoryArName(serviceObj.getString("CategoryArName"));
                    serviceAd.setCategoryEnName(serviceObj.getString("CategoryEnName"));
                    serviceAd.setDescription(serviceObj.getString("Description"));
                    serviceAd.setLat(serviceObj.getString("Lat"));
                    serviceAd.setLng(serviceObj.getString("Lng"));
                    serviceAd.setCategoryId(serviceObj.getString("CategoryId"));
                    serviceAd.setUserId(serviceObj.getString("UserId"));
                    serviceAd.setServiceTypeEnName(serviceObj.getString("ServiceTypeEn_Name"));
                    serviceAd.setServiceTypeArName(serviceObj.getString("ServiceTypeAr_Name"));
                    serviceAd.setOtherServiceType(serviceObj.getString("OtherServiceType"));
                    serviceAd.setPhoneNumber(serviceObj.getString("PhoneNumber"));
                    serviceAd.setCarLiftFrom(serviceObj.getString("CarLiftFrom"));
                    serviceAd.setCarLiftTo(serviceObj.getString("CarLiftTo"));
                    serviceAd.setIsFav(serviceObj.getString("IsFavorite"));
                    serviceAds.add(serviceAd);
                }
                AppDefs.serviceAds = serviceAds;
                setServicesAdsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterServicesRequest);
    }

    /* Business */
    private void filterBusiness(){
        businessAds.clear();
        setBusinessAdsAdapter();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) businessPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) businessPriceBar.getCurrentValues().getRightValue()));
            params.put("SubCategoryId", bikeSubCat);
            params.put("PostedIn", businessPostedIn);
            params.put("Location", businessCity);
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterBusinessRequest = new JsonObjectRequest(Request.Method.POST, Urls.BusinessAds_URL+"FilterBusiness", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray businessArray = response.getJSONArray("results");
                for (int i=0; i<businessArray.length(); i++){
                    JSONObject businessObj = businessArray.getJSONObject(i);
                    BusinessAd businessAd = new BusinessAd();
                    businessAd.setId(businessObj.getString("Id"));
                    businessAd.setAgentId(businessObj.getString("AgentId"));
                    businessAd.setTitle(businessObj.getString("Title"));
                    businessAd.setArLocation(businessObj.getString("Ar_Location"));
                    businessAd.setEnLocation(businessObj.getString("En_Location"));
                    businessAd.setPrice(businessObj.getString("Price"));
                    String postedDate = mainActivity.convertDate(businessObj.getString("PostDate").substring(0, businessObj.getString("PostDate").lastIndexOf(".")));
                    businessAd.setPostedDate(postedDate);
                    businessAd.setDescription(businessObj.getString("Description"));
                    businessAd.setLat(businessObj.getString("Lat"));
                    businessAd.setLng(businessObj.getString("Lng"));
                    businessAd.setCategoryId(businessObj.getString("CategoryId"));
                    businessAd.setCategoryArName(businessObj.getString("CategoryArName"));
                    businessAd.setCategoryEnName(businessObj.getString("CategoryEnName"));
                    businessAd.setOtherCategoryNAme(businessObj.getString("OtherCategoryName"));
                    businessAd.setUserId(businessObj.getString("UserId"));
                    businessAd.setPhoneNumber(businessObj.getString("PhoneNumber"));
                    businessAd.setIsFav(businessObj.getString("IsFavorite"));
                    JSONArray pics = businessObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            businessAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    businessAd.setPictures(pictures);
                    businessAds.add(businessAd);
                }
                AppDefs.businessAds = businessAds;
                setBusinessAdsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterBusinessRequest);
    }

    /* Numbers */
    private void filterNumbers1(){
        numberAds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest numberFilterRequest = new StringRequest(Request.Method.POST, Urls.NumberAds_URL+"FilterNumbers", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray numbersArray = new JSONArray(response);
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("title"));
                    if (numberObj.has("price")){
                        numberAd.setPrice(numberObj.getString("price"));
                    }
                    numberAd.setArLocation(numberObj.getString("ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("en_Location"));
                    numberAd.setDescription(numberObj.getString("description"));
                    numberAd.setUserId(numberObj.getString("userId"));
                    numberAd.setLat(numberObj.getString("lat"));
                    numberAd.setLng(numberObj.getString("lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("postDate").substring(0, numberObj.getString("postDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("en_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("plateCode"));
                    numberAd.setArPlateType(numberObj.getString("ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("en_PlateType"));
                    numberAd.setNumber(numberObj.getString("number"));
                    numberAd.setCategory(numberObj.getString("categoryEnName"));
                    numberAd.setOperator(numberObj.getString("en_Operator"));
                    numberAd.setArOperator(numberObj.getString("ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("en_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("code"));
                    numberAd.setPhoneNumber(numberObj.getString("phoneNumber"));
                    numberAd.setIsFav(numberObj.getString("isFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if (categoryId.equals("17")){
                    params.put("FromPrice",String.valueOf((int) platePriceBar.getCurrentValues().getLeftValue()));
                    params.put("ToPrice", "10000000");
                    params.put("Emirate", AppDefs.brand);
                    params.put("PlateType", AppDefs.model);
                    params.put("PlateCode", plateCode);
                    params.put("Operator", "");
                    params.put("MobileCode", "");
                    params.put("NumberPlan", "");
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                    if (digit1.isChecked()){
                        params.put("PlateDigits1", "1");
                        params.put("PlateDigits2", "");
                        params.put("PlateDigits3", "");
                        params.put("PlateDigits4", "");
                        params.put("PlateDigits5", "");
                    }else if (digit2.isChecked()){
                        params.put("PlateDigits1", "");
                        params.put("PlateDigits2", "2");
                        params.put("PlateDigits3", "");
                        params.put("PlateDigits4", "");
                        params.put("PlateDigits5", "");
                    }else if (digit3.isChecked()){
                        params.put("PlateDigits1", "");
                        params.put("PlateDigits2", "");
                        params.put("PlateDigits3", "3");
                        params.put("PlateDigits4", "");
                        params.put("PlateDigits5", "");
                    }else if (digit4.isChecked()){
                        params.put("PlateDigits1", "");
                        params.put("PlateDigits2", "");
                        params.put("PlateDigits3", "");
                        params.put("PlateDigits4", "4");
                        params.put("PlateDigits5", "");
                    }else {
                        params.put("PlateDigits1", "");
                        params.put("PlateDigits2", "");
                        params.put("PlateDigits3", "");
                        params.put("PlateDigits4", "");
                        params.put("PlateDigits5", "5");
                    }
                    params.put("Location", currentLocation);
                }else{
                    params.put("FromPrice",String.valueOf((int) mobilePriceBar.getCurrentValues().getLeftValue()));
                    params.put("ToPrice", String.valueOf((int) mobilePriceBar.getCurrentValues().getRightValue()));
                    params.put("Operator", AppDefs.brand);
                    params.put("MobileCode", AppDefs.model);
                    params.put("NumberPlan", mobilePlan);
                    params.put("Emirate", "");
                    params.put("PlateType", "");
                    params.put("PlateCode", "");
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                    if (digit3Similar.isChecked()){
                        params.put("MobileDigits3", "3");
                        params.put("MobileDigits4", "");
                        params.put("MobileDigits5", "");
                        params.put("MobileDigits6", "");
                        params.put("MobileDigits7", "");
                    }else if (digit4Similar.isChecked()){
                        params.put("MobileDigits3", "");
                        params.put("MobileDigits4", "4");
                        params.put("MobileDigits5", "");
                        params.put("MobileDigits6", "");
                        params.put("MobileDigits7", "");
                    }else if (digit5Similar.isChecked()){
                        params.put("MobileDigits3", "");
                        params.put("MobileDigits4", "");
                        params.put("MobileDigits5", "5");
                        params.put("MobileDigits6", "");
                        params.put("MobileDigits7", "");
                    }else if (digit6Similar.isChecked()){
                        params.put("MobileDigits3", "");
                        params.put("MobileDigits4", "");
                        params.put("MobileDigits5", "");
                        params.put("MobileDigits6", "6");
                        params.put("MobileDigits7", "");
                    }else {
                        params.put("MobileDigits3", "");
                        params.put("MobileDigits4", "");
                        params.put("MobileDigits5", "");
                        params.put("MobileDigits6", "");
                        params.put("MobileDigits7", "7");
                    }
                    params.put("Location", mobileLocation);
                }
                params.put("CategoryId", categoryId);
                params.put("SortBy", "1");
                params.put("Keyword", "");
                params.put("UserId", AppDefs.user.getId());
                return params;
            }
        };
        mainActivity.queue.add(numberFilterRequest);
    }

    private void numbersList(){
        numberAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", "0");
            params.put("ToPrice", "10000000");
            params.put("Emirate", AppDefs.brand);
            params.put("PlateType", AppDefs.model);
            params.put("CategoryId", "17");
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest numberFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"FilterNumbers", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray numbersArray = response.getJSONArray("results");
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("Id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("Title"));
                    if (numberObj.has("Price")){
                        numberAd.setPrice(numberObj.getString("Price"));
                    }
                    numberAd.setArLocation(numberObj.getString("Ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("En_Location"));
                    numberAd.setDescription(numberObj.getString("Description"));
                    numberAd.setUserId(numberObj.getString("UserId"));
                    numberAd.setLat(numberObj.getString("Lat"));
                    numberAd.setLng(numberObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("PostDate").substring(0, numberObj.getString("PostDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("En_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("Ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("PlateCode"));
                    numberAd.setArPlateType(numberObj.getString("Ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("En_PlateType"));
                    numberAd.setNumber(numberObj.getString("Number"));
                    numberAd.setCategory(numberObj.getString("CategoryEnName"));
                    numberAd.setOperator(numberObj.getString("En_Operator"));
                    numberAd.setArOperator(numberObj.getString("Ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("En_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("Ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("Code"));
                    numberAd.setPhoneNumber(numberObj.getString("PhoneNumber"));
                    numberAd.setIsFav(numberObj.getString("IsFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(numberFilterRequest);
    }

    private void getAllNumbers(){
        numberAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", "0");
            params.put("ToPrice", "10000000");
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest numberFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"FilterNumbers", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray numbersArray = response.getJSONArray("results");
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("Id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("Title"));
                    if (numberObj.has("Price")){
                        numberAd.setPrice(numberObj.getString("Price"));
                    }
                    numberAd.setArLocation(numberObj.getString("Ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("En_Location"));
                    numberAd.setDescription(numberObj.getString("Description"));
                    numberAd.setUserId(numberObj.getString("UserId"));
                    numberAd.setLat(numberObj.getString("Lat"));
                    numberAd.setLng(numberObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("PostDate").substring(0, numberObj.getString("PostDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("En_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("Ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("PlateCode"));
                    numberAd.setArPlateType(numberObj.getString("Ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("En_PlateType"));
                    numberAd.setNumber(numberObj.getString("Number"));
                    numberAd.setCategory(numberObj.getString("CategoryEnName"));
                    numberAd.setOperator(numberObj.getString("En_Operator"));
                    numberAd.setArOperator(numberObj.getString("Ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("En_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("Ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("Code"));
                    numberAd.setPhoneNumber(numberObj.getString("PhoneNumber"));
                    numberAd.setIsFav(numberObj.getString("IsFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(numberFilterRequest);
    }

    private void mobileNumbersList(){
        numberAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", "0");
            params.put("ToPrice", "10000000");
            params.put("Operator", AppDefs.brand);
            params.put("CategoryId", "18");
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest numberFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"FilterNumbers", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray numbersArray = response.getJSONArray("results");
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("Id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("Title"));
                    if (numberObj.has("Price")){
                        numberAd.setPrice(numberObj.getString("Price"));
                    }
                    numberAd.setArLocation(numberObj.getString("Ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("En_Location"));
                    numberAd.setDescription(numberObj.getString("Description"));
                    numberAd.setUserId(numberObj.getString("UserId"));
                    numberAd.setLat(numberObj.getString("Lat"));
                    numberAd.setLng(numberObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("PostDate").substring(0, numberObj.getString("PostDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("En_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("Ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("PlateCode"));
                    numberAd.setArPlateType(numberObj.getString("Ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("En_PlateType"));
                    numberAd.setNumber(numberObj.getString("Number"));
                    numberAd.setCategory(numberObj.getString("CategoryEnName"));
                    numberAd.setOperator(numberObj.getString("En_Operator"));
                    numberAd.setArOperator(numberObj.getString("Ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("En_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("Ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("Code"));
                    numberAd.setPhoneNumber(numberObj.getString("PhoneNumber"));
                    numberAd.setIsFav(numberObj.getString("IsFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(numberFilterRequest);
    }

    private void filterNumbers2(){
        numberAds.clear();
        JSONObject params = new JSONObject();
        try {
            if (categoryId.equals("17")){
                params.put("FromPrice",String.valueOf((int) platePriceBar.getCurrentValues().getLeftValue()));
                params.put("ToPrice", "10000000");
                params.put("Emirate", AppDefs.brand);
                params.put("PlateType", AppDefs.model);
                params.put("PlateCode", plateCode);
                params.put("Operator", "");
                params.put("MobileCode", "");
                params.put("NumberPlan", "");
                params.put("MobileDigits3", "");
                params.put("MobileDigits4", "");
                params.put("MobileDigits5", "");
                params.put("MobileDigits6", "");
                params.put("MobileDigits7", "");
                if (digit1.isChecked()){
                    params.put("PlateDigits1", "1");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                }else if (digit2.isChecked()){
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "2");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                }else if (digit3.isChecked()){
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "3");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                }else if (digit4.isChecked()){
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "4");
                    params.put("PlateDigits5", "");
                }else {
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "5");
                }
                params.put("Location", currentLocation);
            }else{
                params.put("FromPrice",String.valueOf((int) mobilePriceBar.getCurrentValues().getLeftValue()));
                params.put("ToPrice", String.valueOf((int) mobilePriceBar.getCurrentValues().getRightValue()));
                params.put("Operator", AppDefs.brand);
                params.put("MobileCode", AppDefs.model);
                params.put("NumberPlan", mobilePlan);
                params.put("Emirate", "");
                params.put("PlateType", "");
                params.put("PlateCode", "");
                params.put("PlateDigits1", "");
                params.put("PlateDigits2", "");
                params.put("PlateDigits3", "");
                params.put("PlateDigits4", "");
                params.put("PlateDigits5", "");
                if (digit3Similar.isChecked()){
                    params.put("MobileDigits3", "3");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                }else if (digit4Similar.isChecked()){
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "4");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                }else if (digit5Similar.isChecked()){
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "5");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                }else if (digit6Similar.isChecked()){
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "6");
                    params.put("MobileDigits7", "");
                }else {
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "7");
                }
                params.put("Location", mobileLocation);
            }
            params.put("CategoryId", categoryId);
            params.put("Keyword", "");
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest numberFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"FilterNumbers", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray numbersArray = response.getJSONArray("results");
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("Id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("Title"));
                    if (numberObj.has("Price")){
                        numberAd.setPrice(numberObj.getString("Price"));
                    }
                    numberAd.setArLocation(numberObj.getString("Ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("En_Location"));
                    numberAd.setDescription(numberObj.getString("Description"));
                    numberAd.setUserId(numberObj.getString("UserId"));
                    numberAd.setLat(numberObj.getString("Lat"));
                    numberAd.setLng(numberObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("PostDate").substring(0, numberObj.getString("PostDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("En_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("Ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("PlateCode"));
                    numberAd.setArPlateType(numberObj.getString("Ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("En_PlateType"));
                    numberAd.setNumber(numberObj.getString("Number"));
                    numberAd.setCategory(numberObj.getString("CategoryEnName"));
                    numberAd.setOperator(numberObj.getString("En_Operator"));
                    numberAd.setArOperator(numberObj.getString("Ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("En_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("Ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("Code"));
                    numberAd.setPhoneNumber(numberObj.getString("PhoneNumber"));
                    numberAd.setIsFav(numberObj.getString("IsFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(numberFilterRequest);
    }

    private void filterNumbers(){
        numberAds.clear();
        JSONObject params = new JSONObject();
        try {
            if (categoryId.equals("17")){
                params.put("FromPrice",String.valueOf((int) platePriceBar.getCurrentValues().getLeftValue()));
                params.put("ToPrice", "10000000");
                params.put("Emirate", AppDefs.brand);
                params.put("PlateType", AppDefs.model);
                params.put("PlateCode", plateCode);
                params.put("Operator", "");
                params.put("MobileCode", "");
                params.put("NumberPlan", "");
                params.put("MobileDigits3", "");
                params.put("MobileDigits4", "");
                params.put("MobileDigits5", "");
                params.put("MobileDigits6", "");
                params.put("MobileDigits7", "");
                if (digit1.isChecked()){
                    plateDigits1 = "true";
                    plateDigits2 = "false";
                    plateDigits3 = "false";
                    plateDigits4 = "false";
                    plateDigits5 = "false";
                    params.put("PlateDigits1", "1");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                }else if (digit2.isChecked()){
                    plateDigits1 = "false";
                    plateDigits2 = "true";
                    plateDigits3 = "false";
                    plateDigits4 = "false";
                    plateDigits5 = "false";
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "2");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                }else if (digit3.isChecked()){
                    plateDigits1 = "false";
                    plateDigits2 = "false";
                    plateDigits3 = "true";
                    plateDigits4 = "false";
                    plateDigits5 = "false";
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "3");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "");
                }else if (digit4.isChecked()){
                    plateDigits1 = "false";
                    plateDigits2 = "false";
                    plateDigits3 = "false";
                    plateDigits4 = "true";
                    plateDigits5 = "false";
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "4");
                    params.put("PlateDigits5", "");
                }else {
                    plateDigits1 = "false";
                    plateDigits2 = "false";
                    plateDigits3 = "fasle";
                    plateDigits4 = "false";
                    plateDigits5 = "true";
                    params.put("PlateDigits1", "");
                    params.put("PlateDigits2", "");
                    params.put("PlateDigits3", "");
                    params.put("PlateDigits4", "");
                    params.put("PlateDigits5", "5");
                }
                params.put("Location", currentLocation);
            }else{
                params.put("FromPrice",String.valueOf((int) mobilePriceBar.getCurrentValues().getLeftValue()));
                params.put("ToPrice", String.valueOf((int) mobilePriceBar.getCurrentValues().getRightValue()));
                params.put("Operator", AppDefs.brand);
                params.put("MobileCode", AppDefs.model);
                params.put("NumberPlan", mobilePlan);
                params.put("Emirate", "");
                params.put("PlateType", "");
                params.put("PlateCode", "");
                params.put("PlateDigits1", "");
                params.put("PlateDigits2", "");
                params.put("PlateDigits3", "");
                params.put("PlateDigits4", "");
                params.put("PlateDigits5", "");
                if (digit3Similar.isChecked()){
                    mobileDigits3 = "true";
                    mobileDigits4 = "false";
                    mobileDigits5 = "false";
                    mobileDigits6 = "false";
                    mobileDigits7 = "false";
                    params.put("MobileDigits3", "3");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                }else if (digit4Similar.isChecked()){
                    mobileDigits3 = "false";
                    mobileDigits4 = "true";
                    mobileDigits5 = "false";
                    mobileDigits6 = "false";
                    mobileDigits7 = "false";
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "4");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                }else if (digit5Similar.isChecked()){
                    mobileDigits3 = "false";
                    mobileDigits4 = "false";
                    mobileDigits5 = "true";
                    mobileDigits6 = "false";
                    mobileDigits7 = "false";
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "5");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "");
                }else if (digit6Similar.isChecked()){
                    mobileDigits3 = "false";
                    mobileDigits4 = "false";
                    mobileDigits5 = "false";
                    mobileDigits6 = "true";
                    mobileDigits7 = "false";
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "6");
                    params.put("MobileDigits7", "");
                }else {
                    mobileDigits3 = "false";
                    mobileDigits4 = "false";
                    mobileDigits5 = "false";
                    mobileDigits6 = "false";
                    mobileDigits7 = "true";
                    params.put("MobileDigits3", "");
                    params.put("MobileDigits4", "");
                    params.put("MobileDigits5", "");
                    params.put("MobileDigits6", "");
                    params.put("MobileDigits7", "7");
                }
                params.put("Location", mobileLocation);
            }
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
            params.put("Keyword", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setNumberAdsAdapter();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest numberFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"FilterNumbers", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray numbersArray = response.getJSONArray("results");
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("Id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("Title"));
                    if (numberObj.has("Price")){
                        numberAd.setPrice(numberObj.getString("Price"));
                    }
                    numberAd.setArLocation(numberObj.getString("Ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("En_Location"));
                    numberAd.setDescription(numberObj.getString("Description"));
                    numberAd.setUserId(numberObj.getString("UserId"));
                    numberAd.setLat(numberObj.getString("Lat"));
                    numberAd.setLng(numberObj.getString("Lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("PostDate").substring(0, numberObj.getString("PostDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("En_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("Ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("PlateCode"));
                    numberAd.setArPlateType(numberObj.getString("Ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("En_PlateType"));
                    numberAd.setNumber(numberObj.getString("Number"));
                    numberAd.setCategory(numberObj.getString("CategoryEnName"));
                    numberAd.setOperator(numberObj.getString("En_Operator"));
                    numberAd.setArOperator(numberObj.getString("Ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("En_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("Ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("Code"));
                    numberAd.setPhoneNumber(numberObj.getString("PhoneNumber"));
                    numberAd.setIsFav(numberObj.getString("IsFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(numberFilterRequest);
    }

    /* Classifieds */
    private void getAllClassifieds(){
        classifiedsAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getRightValue()));
            params.put("CategoryId", categoryId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterClassifiedsRequest = new JsonObjectRequest(Request.Method.POST, Urls.ClassifiedAds_URL+"FilterClassifieds", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("En_Brand"));
                    electronicAd.setArBrand(electronicObj.getString("Ar_Brand"));
                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterClassifiedsRequest);
    }

    private void filterClassifieds1(){
        classifiedsAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getRightValue()));
            if (category1.getVisibility() == View.VISIBLE){
                params.put("Age", category1Age);
                params.put("Usage", category1Usage);
                params.put("Brand", classifiedsBrand);
            }else if (category2.getVisibility() == View.VISIBLE){
                params.put("Age", category2Age);
                params.put("Usage", category2Usage);
                params.put("Brand", "");
            }else {
                params.put("Age", "");
                params.put("Usage", "");
                params.put("Brand", "");
            }
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", AppDefs.brand);
            params.put("SubTypeId", AppDefs.model);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterClassifiedsRequest = new JsonObjectRequest(Request.Method.POST, Urls.ClassifiedAds_URL+"FilterClassifieds", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("En_Brand"));
                    electronicAd.setArBrand(electronicObj.getString("Ar_Brand"));
                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterClassifiedsRequest);
    }

    private void filterClassifieds2(){
        classifiedsAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getRightValue()));
            if (category1.getVisibility() == View.VISIBLE){
                params.put("Age", category1Age);
                params.put("Usage", category1Usage);
                params.put("Brand", classifiedsBrand);
            }else if (category2.getVisibility() == View.VISIBLE){
                params.put("Age", category2Age);
                params.put("Usage", category2Usage);
                params.put("Brand", "");
            }else {
                params.put("Age", "");
                params.put("Usage", "");
                params.put("Brand", "");
            }
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", AppDefs.brand);
            params.put("SubTypeId", classifiedsItemNameId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterClassifiedsRequest = new JsonObjectRequest(Request.Method.POST, Urls.ClassifiedAds_URL+"FilterClassifieds", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("En_Brand"));
                    electronicAd.setArBrand(electronicObj.getString("Ar_Brand"));
                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterClassifiedsRequest);
    }

    private void filterClassifieds(){
        classifiedsAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) classifiedsPriceBar.getCurrentValues().getRightValue()));
            if (category1.getVisibility() == View.VISIBLE){
                params.put("Age", category1Age);
                params.put("Usage", category1Usage);
                params.put("Brand", classifiedsBrand);
            }else if (category2.getVisibility() == View.VISIBLE){
                params.put("Age", category2Age);
                params.put("Usage", category2Usage);
                params.put("Brand", "");
            }else {
                params.put("Age", "");
                params.put("Usage", "");
                params.put("Brand", "");
            }
            params.put("Location", currentLocation);
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", classifiedsSubCatId);
            params.put("SubTypeId", classifiedsItemNameId);
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setClassifiedsAdsAdapter(classifiedsAds);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest filterClassifiedsRequest = new JsonObjectRequest(Request.Method.POST, Urls.ClassifiedAds_URL+"FilterClassifieds", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("En_Brand"));
                    electronicAd.setArBrand(electronicObj.getString("Ar_Brand"));
                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(filterClassifiedsRequest);
    }

    /* Electronics */
    private void getAllElectronics(){
        electronicAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", "0");
            params.put("ToPrice", "10000000");
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", "0");
            params.put("SubTypeId", "0");
            params.put("SortBy", sortBy);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest electronicsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.ElectronicAds_URL+"FilterElectronics", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("CategoryEnName"));
                    electronicAd.setArBrand(electronicObj.getString("CategoryArName"));
//                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
//                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    electronicAd.setArRam(electronicObj.getString("Ram_Ar"));
                    electronicAd.setEnRam(electronicObj.getString("Ram_En"));
                    electronicAd.setArVersion(electronicObj.getString("Version_Ar"));
                    electronicAd.setEnVersion(electronicObj.getString("Version_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_Ar"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(electronicsFilterRequest);
    }

    private void filterElectronics1(){
        electronicAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) electronicsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) electronicsPriceBar.getCurrentValues().getRightValue()));
            params.put("Location", currentLocation);
            params.put("Age", electronicsAge);
            params.put("Warranty", electronicsWarranty);
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", AppDefs.brand);
            params.put("SubTypeId", AppDefs.model);
            params.put("SortBy", sortBy);
            params.put("PostedIn", electronicsPosted);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest electronicsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.ElectronicAds_URL+"FilterElectronics", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("CategoryEnName"));
                    electronicAd.setArBrand(electronicObj.getString("CategoryArName"));
//                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
//                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    electronicAd.setArRam(electronicObj.getString("Ram_Ar"));
                    electronicAd.setEnRam(electronicObj.getString("Ram_En"));
                    electronicAd.setArVersion(electronicObj.getString("Version_Ar"));
                    electronicAd.setEnVersion(electronicObj.getString("Version_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_Ar"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(electronicsFilterRequest);
    }

    private void filterElectronics2(){
        electronicAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) electronicsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) electronicsPriceBar.getCurrentValues().getRightValue()));
            params.put("Location", currentLocation);
            params.put("Age", electronicsAge);
            params.put("Warranty", electronicsWarranty);
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", AppDefs.brand);
            params.put("SubTypeId", electronicsTrim);
            params.put("SortBy", sortBy);
            params.put("PostedIn", electronicsPosted);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest electronicsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.ElectronicAds_URL+"FilterElectronics", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("CategoryEnName"));
                    electronicAd.setArBrand(electronicObj.getString("CategoryArName"));
//                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
//                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    electronicAd.setArRam(electronicObj.getString("Ram_Ar"));
                    electronicAd.setEnRam(electronicObj.getString("Ram_En"));
                    electronicAd.setArVersion(electronicObj.getString("Version_Ar"));
                    electronicAd.setEnVersion(electronicObj.getString("Version_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_Ar"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(electronicsFilterRequest);
    }

    private void filterElectronics(){
        electronicAds.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("FromPrice", String.valueOf((int) electronicsPriceBar.getCurrentValues().getLeftValue()));
            params.put("ToPrice", String.valueOf((int) electronicsPriceBar.getCurrentValues().getRightValue()));
            params.put("Location", currentLocation);
            params.put("Age", electronicsAge);
            params.put("Warranty", electronicsWarranty);
            params.put("CategoryId", categoryId);
            params.put("SubCategoryId", electronicsSubCat);
            params.put("SubTypeId", electronicsTrim);
            params.put("SortBy", sortBy);
            params.put("PostedIn", electronicsPosted);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setElectronicAdsAdapter(electronicAds);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JsonObjectRequest electronicsFilterRequest = new JsonObjectRequest(Request.Method.POST, Urls.ElectronicAds_URL+"FilterElectronics", params, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray electronicsArray = response.getJSONArray("results");
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("Id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("Title"));
                    electronicAd.setArLocation(electronicObj.getString("Ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("En_Location"));
                    electronicAd.setPrice(electronicObj.getString("Price"));
                    electronicAd.setEnAge(electronicObj.getString("En_Age"));
                    electronicAd.setArAge(electronicObj.getString("Ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("En_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("Ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("CategoryEnName"));
                    electronicAd.setArBrand(electronicObj.getString("CategoryArName"));
//                    electronicAd.setEnCondition(electronicObj.getString("En_Condition"));
//                    electronicAd.setArCondition(electronicObj.getString("Ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("Description"));
                    electronicAd.setLat(electronicObj.getString("Lat"));
                    electronicAd.setLng(electronicObj.getString("Lng"));
                    electronicAd.setUserId(electronicObj.getString("UserId"));
                    electronicAd.setSubCatArName(electronicObj.getString("SubCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("SubCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("OtherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("SubTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("SubTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("OtherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("PhoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("IsFavorite"));
                    electronicAd.setArRam(electronicObj.getString("Ram_Ar"));
                    electronicAd.setEnRam(electronicObj.getString("Ram_En"));
                    electronicAd.setArVersion(electronicObj.getString("Version_Ar"));
                    electronicAd.setEnVersion(electronicObj.getString("Version_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_En"));
                    electronicAd.setArRam(electronicObj.getString("Storage_Ar"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("PostDate").substring(0, electronicObj.getString("PostDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("Pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("Id"));
                        picture.setImageURL(picObj.getString("ImageURL"));
                        picture.setMain(picObj.getBoolean("MainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(electronicsFilterRequest);
    }

    public void setAlertPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.set_alert_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.done);
        done.setOnClickListener(view -> {
            if(currentLocation.isEmpty()){
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.choose_location));
//                saveSearch(myAdsOptionsAlertBuilder, "Dubai-دبي");
                myAdsOptionsAlertBuilder.dismiss();
            }else {
                saveSearch(myAdsOptionsAlertBuilder, currentLocation);
            }
        });
    }

    private void saveSearch(AlertDialog myAdsOptionsAlertBuilder, String location){
        JSONObject obj = new JSONObject();
        try {
            obj.put("categoryId", categoryId);
            obj.put("typeId", typeId);
            obj.put("location", location);
            obj.put("fromPrice", fromPrice);
            obj.put("toPrice", toPrice);
            obj.put("fromYear", fromYear);
            obj.put("toYear", toYear);
            obj.put("fromKilometers", fromKilo);
            obj.put("toKilometers", toKilo);
            obj.put("en_Maker", enMaker);
            obj.put("ar_Maker", arMaker);
            obj.put("en_Model", enModel);
            obj.put("ar_Model", arModel);
            obj.put("en_Trim", enTrim);
            obj.put("ar_Trim", arTrim);
            obj.put("en_Horsepower", enHorse);
            obj.put("ar_Horsepower", arHorse);
            obj.put("en_Age", enAge);
            obj.put("ar_Age", arAge);
            obj.put("en_Usage", enUsage);
            obj.put("ar_Usage", arUsage);
            obj.put("warranty", warranty);
            obj.put("en_PartName", enPart);
            obj.put("ar_PartName", arPart);
            obj.put("subCategoryId", subCatId);
            obj.put("subTypeId", subTypeId);
            obj.put("en_Commitment", enCommitment);
            obj.put("ar_Commitment", arCommitment);
            obj.put("en_JobType", enJobType);
            obj.put("ar_JobType", arJobType);
            obj.put("en_WorkExperience", enWorkExperience);
            obj.put("ar_WorkExperience", arWorkExperience);
            obj.put("en_EducationLevel", enEducation);
            obj.put("ar_EducationLevel", arEducation);
            obj.put("en_Emirate", enEmirate);
            obj.put("ar_Emirate", arEmirate);
            obj.put("en_Operator", enOperator);
            obj.put("ar_Operator", arOperator);
            obj.put("en_MobileCode", enMobileCode);
            obj.put("ar_MobileCode", arMobileCode);
            obj.put("en_NumberPlan", enNumberPlan);
            obj.put("ar_NumberPlan", arNumberPlan);
            obj.put("en_PlateType", enPlateType);
            obj.put("ar_PlateType", arPlateType);
            obj.put("en_PlateCode", enPlateCode);
            obj.put("ar_PlateCode", arPlateCode);
            obj.put("mobileDigits3", mobileDigits3);
            obj.put("mobileDigits4", mobileDigits4);
            obj.put("mobileDigits5", mobileDigits5);
            obj.put("mobileDigits6", mobileDigits6);
            obj.put("mobileDigits7", mobileDigits7);
            obj.put("plateDigits1", plateDigits1);
            obj.put("plateDigits2", plateDigits2);
            obj.put("plateDigits3", plateDigits3);
            obj.put("plateDigits4", plateDigits4);
            obj.put("plateDigits5", plateDigits5);
            obj.put("en_Brand", enBrand);
            obj.put("ar_Brand", arBrand);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"SaveFillters", obj, response -> {
            mainActivity.hideProgressDialog();
            myAdsOptionsAlertBuilder.dismiss();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.save_search_success), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.save_search), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }

    public void deleteAlertPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.delete_alert_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.done);
        done.setOnClickListener(view -> {
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

//    public static String getTimeAgo(String time) {
//        PrettyTime p = new PrettyTime();
//        CharSequence ago = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss.SSS");
//
//        ago = p.format(sdf.parse(time).getTime());
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        try {
//            long timeNow = sdf.parse(time).getTime();
//            long now = System.currentTimeMillis();
//            ago = DateUtils.getRelativeTimeSpanString(timeNow, now, DateUtils.MINUTE_IN_MILLIS);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//         return String.valueOf(ago);
//
////        if (time < 1000000000000L) {
////            time *= 1000;
////        }
////
////        long now = System.currentTimeMillis();
////        if (time > now || time <= 0) {
////            return null;
////        }
////
////
////        final long diff = now - time;
////        if (diff < MINUTE_MILLIS) {
////            return "just now";
////        } else if (diff < 2 * MINUTE_MILLIS) {
////            return "a minute ago";
////        } else if (diff < 50 * MINUTE_MILLIS) {
////            return diff / MINUTE_MILLIS + " minutes ago";
////        } else if (diff < 90 * MINUTE_MILLIS) {
////            return "an hour ago";
////        } else if (diff < 24 * HOUR_MILLIS) {
////            return diff / HOUR_MILLIS + " hours ago";
////        } else if (diff < 48 * HOUR_MILLIS) {
////            return "yesterday";
////        } else {
////            return diff / DAY_MILLIS + " days ago";
////        }
//    }

    private void onSpinnerClick(){
        usedCarsBrandsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentBrand = brandEnTitles.get(i)+"-"+brandArTitles.get(i);
                brandId = brandIds.get(i);
                enMaker = brandEnTitles.get(i);
                arMaker = brandArTitles.get(i);
                if (brandId.equals("-1")){
                    classifiedsBrand = "";
                }
                getModels();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        usedCarsModelsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (modelEnTitles.size()>0 && modelArTitles.size()>0) {
                    currentModel = modelEnTitles.get(i) + "-" + modelArTitles.get(i);
//                    currentModel = modelEnTitles.get(i) + "-";
                    enModel = modelEnTitles.get(i);
                    arModel = modelArTitles.get(i);
                    if(modelEnTitles.get(i).equals(mainActivity.getResources().getString(R.string.other))){
                        currentModel = "";
                    }
                    getTrims();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        usedCarsTrimsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (trimEnTitles.size()>0 && trimArTitles.size()>0){
                    currentTrim = trimEnTitles.get(i)+"-"+trimArTitles.get(i);
                    arTrim = trimArTitles.get(i);
                    enTrim = trimEnTitles.get(i);
                    if(trimEnTitles.get(i).equals(mainActivity.getResources().getString(R.string.other))){
                        currentTrim = "";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        boatWarrantySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    currentBoatWarranty = "0";
                }else if (i==1){
                    currentBoatWarranty = "1";
                }else {
                    currentBoatWarranty = "2";
                }
                warranty = currentBoatWarranty;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        boatHorsepowerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentBoatHorsePower = boatHorsepowerEnTitles.get(i)+"-"+boatHorsepowerArTitles.get(i);
                enHorse = boatHorsepowerEnTitles.get(i);
                arHorse = boatHorsepowerArTitles.get(i);
                if(enHorse.equals(mainActivity.getResources().getString(R.string.other))){
                    currentBoatHorsePower = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        boatAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentBoatAge = boatAgeEnTitles.get(i)+"-"+boatAgeArTitles.get(i);
                enAge = boatAgeEnTitles.get(i);
                arAge = boatAgeArTitles.get(i);
                if(enAge.equals(mainActivity.getResources().getString(R.string.other))){
                    currentBoatAge = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        boatUsageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentBoatUsage = boatUsageEnTitles.get(i)+"-"+boatUsageArTitles.get(i);
                enUsage = boatUsageEnTitles.get(i);
                arUsage = boatUsageArTitles.get(i);
                if(enUsage.equals(mainActivity.getResources().getString(R.string.other))){
                    currentBoatUsage = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        machineryCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (machineryEnCategoryTitles.size() == 1){
                    machineryCategory = "0";
                }else if (i == machineryEnCategoryTitles.size()-1){
                    machineryCategory = "0";
                }else {
                    machineryCategory = machineryCatIds.get(i);
                    getMachinerySubCategories();
                }
                subCatId = machineryCategory;
                if (subCatId.equals("-1")){
                    machineryCategory = "";
                    subCatId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        machinerySubCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (machineryEnSubCatTitles.size() == 1){
                    machinerySubCat = "0";
                }else if (i == machineryEnSubCatTitles.size()-1){
                    machinerySubCat = "0";
                }else {
                    machinerySubCat = machinerySubCatIds.get(i);
                }
                subTypeId = machinerySubCat;
                if (subCatId.equals("-1")){
                    machinerySubCat = "";
                    subTypeId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        machineryHorsepowerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                machineryHorsepower = machineryEnHorsepowerTitles.get(i)+"-"+machineryArHorsepowerTitles.get(i);
                enHorse = machineryEnHorsepowerTitles.get(i);
                arHorse = machineryArHorsepowerTitles.get(i);
                if (enHorse.equals("-1")){
                    machineryHorsepower = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bikesCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (bikesEnCategoryTitles.size() == 1){
                    bikeCat = "0";
                }else if (i == bikesEnCategoryTitles.size()-1){
                    bikeCat = "0";
                }else {
                    bikeCat = bikesCatIds.get(i);
                    getbikeMakers();
                }
                subCatId = bikeCat;
                if (subCatId.equals("-1")){
                    bikeCat = "";
                    subCatId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bikesMakerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (bikesEnSubCatTitles.size() == 1){
                    bikeSubCat = "0";
                }else if (i == bikesEnSubCatTitles.size()-1){
                    bikeSubCat = "0";
                }else {
                    bikeSubCat = bikesSubCatIds.get(i);
                }
                subTypeId = bikeSubCat;
                if (subTypeId.equals("-1")){
                    bikeSubCat = "";
                    subTypeId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bikePostedInSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bikeAdPosted = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        partsCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (partEnCatTitles.size() == 1){
                    partCat = "0";
                }else if (i == partEnCatTitles.size()-1){
                    partCat = "0";
                }else {
                    partCat = partCatIds.get(i);
                    getPartMake();
                }
                subCatId = partCat;
                if (subCatId.equals("-1")){
                    partCat = "";
                    subCatId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        partsMakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (partEnMakeTitles.size() == 1){
                    partMake = "0";
                }else if (i == partEnMakeTitles.size()-1){
                    partMake = "0";
                }else {
                    partMake = partMakeIds.get(i);
                    if (partMake.equals("-1")){
                        partMake = "";
                    }
                    getParts();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        partsNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (partNameEnTitles.size() == 1){
                    partName = "0";
                }else if (i == partNameEnTitles.size()-1){
                    partName = "0";
                }else {
                    partName = partNameEnTitles.get(i)+"-"+partNameArTitles.get(i);
                }
                enPart = partNameEnTitles.get(i);
                arPart = partNameArTitles.get(i);
                if (enPart.equals(mainActivity.getResources().getString(R.string.other))){
                    partName = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        postedInSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                postedIn = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                employmentType = employmentTypeEnTitles.get(i)+"-"+employmentTypeArTitles.get(i);
                enCommitment = employmentTypeEnTitles.get(i);
                arCommitment = employmentTypeArTitles.get(i);
                if (employmentTypeEnTitles.equals(mainActivity.getResources().getString(R.string.other))){
                    employmentType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jobTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobType = jobTypeEnTitles.get(i)+"-"+jobTypeArTitles.get(i);
                enJobType = jobTypeEnTitles.get(i);
                arJobType = jobTypeArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workExperienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                workExperience = workExperienceEnTitles.get(i)+"-"+workExperienceArTitles.get(i);
                enWorkExperience = workExperienceEnTitles.get(i);
                arWorkExperience = workExperienceArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        educationLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                educationLevel = educationLevelEnTitles.get(i)+"-"+educationLevelArTitles.get(i);
                enEducation = educationLevelEnTitles.get(i);
                arEducation = educationLevelArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        professionalLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                professionalLevel = professionalLevelEnTitles.get(i)+"-"+professionalLevelArTitles.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        servicesPostedInSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                servicePostIn = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        servicesSubCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (servicesSubCatEnTitles.size() == 1){
                    serviceSubCat = "0";
                }else {
                    serviceSubCat = serviceSubCatIds.get(i);
                }
                subCatId = serviceSubCat;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        businessCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                businessCity = businessCityEnTitles.get(i)+"-"+businessCityArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        businessAdPostedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                businessPostedIn = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        businessSubTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (businessSubTypeEnTitles.size() == 1){
                    businessSubType = "0";
                }else if (i == businessSubTypeEnTitles.size()-1){
                    businessSubType = "0";
                }else {
                    businessSubType = businessSubTypeIds.get(i);
                }
                subTypeId = businessSubType;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        emirateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                emirate = emirateEnTitles.get(i)+"-"+emirateArTitles.get(i);
                enEmirate = emirateEnTitles.get(i);
                arEmirate = emirateArTitles.get(i);
                getPlateType();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        plateTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                plateType = plateTypeEnTitles.get(i)+"-"+plateTypeArTitles.get(i);
                enPlateType = plateTypeEnTitles.get(i);
                arPlateType = plateTypeArTitles.get(i);
                getPlateCodes(emirate, plateType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        plateCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (plateCodes.size()>0){
                    plateCode = plateCodes.get(i);
                    enPlateCode = plateCodes.get(i);
                    arPlateCode = plateCodes.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        operatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                operator = operatorEnTitles.get(i)+"-"+operatorArTitles.get(i);
                enOperator = operatorEnTitles.get(i);
                arOperator = operatorArTitles.get(i);
                getMobileNumberCodes(operator);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        codeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                code = codes.get(i);
                arMobileCode = code;
                enMobileCode = code;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mobilePlanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mobilePlan = mobilePlanEnTitles.get(i)+"-"+mobilePlanArTitles.get(i);
                arNumberPlan = mobilePlanArTitles.get(i);
                enNumberPlan = mobilePlanEnTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        classifiedsSubCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getClassifiedsSubTypes(classifiedsSubCatId);
                subCatId = classifiedsSubCatId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        classifiedsItemNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (classifiedsItemNameIds.size()>0){
                    classifiedsItemNameId = classifiedsItemNameIds.get(i);
                }else {
                    classifiedsItemNameId = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        classifiedsBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classifiedsBrand = classifiedsBrandEnTitles.get(i)+"-"+classifiedsBrandArTitles.get(i);
                enBrand = classifiedsBrandEnTitles.get(i);
                arBrand = classifiedsBrandArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category1AgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category1Age = classifiedsCategory1AgeEnTitles.get(i)+"-"+classifiedsCategory1AgeArTitles.get(i);
                enAge = classifiedsCategory1AgeEnTitles.get(i);
                arAge = classifiedsCategory1AgeArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category2AgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category2Age = classifiedsCategory2AgeEnTitles.get(i)+"-"+classifiedsCategory2AgeArTitles.get(i);
                enAge = classifiedsCategory2AgeEnTitles.get(i);
                arAge = classifiedsCategory2AgeArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category1UsageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category1Usage = classifiedsCategory1UsageEnTitles.get(i)+"-"+classifiedsCategory1UsageArTitles.get(i);
                enUsage = classifiedsCategory1UsageEnTitles.get(i);
                arUsage = classifiedsCategory1UsageArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        category2UsageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category2Usage = classifiedsCategory2UsageEnTitles.get(i)+"-"+classifiedsCategory2UsageArTitles.get(i);
                enUsage = classifiedsCategory2UsageEnTitles.get(i);
                arUsage = classifiedsCategory2UsageArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        electronicsSubCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (electronicsSubCatIds.size()>1){
                    electronicsSubCat = electronicsSubCatIds.get(i);
                }else {
                    electronicsSubCat = "0";
                }
                getElectronicsTrim(electronicsSubCat);
                subCatId = electronicsSubCat;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        electronicsTrimSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (electronicsTrimIds.size()>1){
                    electronicsTrim = electronicsTrimIds.get(i);
                }else {
                    electronicsTrim = "0";
                }
                enTrim = electronicsTrimEnTitles.get(i);
                arTrim = electronicsTrimArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        electronicsAgeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                electronicsAge = electronicsAgeEnTitles.get(i)+"-"+electronicsAgeArTitles.get(i);
                enAge = electronicsAgeEnTitles.get(i);
                arAge = electronicsAgeArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        electronicsWarrantySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    electronicsWarranty = "0";
                }else if (i==1){
                    electronicsWarranty = "1";
                }else {
                    electronicsWarranty = "2";
                }
                warranty = electronicsWarranty;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        electronicsAdPostedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                electronicsPosted = String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell4_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    //Motors filter
    private void getBrands(){
        brandArTitles.clear();
        brandEnTitles.clear();
        brandIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest brandsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorMaker", response -> {
            try {
                JSONArray brandsArray = new JSONArray(response);
                for (int i=0; i<brandsArray.length(); i++){
                    JSONObject brandObj = brandsArray.getJSONObject(i);
                    brandArTitles.add(brandObj.getString("ar_Text"));
                    brandEnTitles.add(brandObj.getString("en_Text"));
                    brandIds.add(brandObj.getString("id"));
                }
                brandArTitles.add(mainActivity.getResources().getString(R.string.other));
                brandEnTitles.add(mainActivity.getResources().getString(R.string.other));
                brandIds.add("-1");
                if (AppDefs.language.equals("ar")){
                    setSpinner(usedCarsBrandsSpinner, brandArTitles);
                }else {
                    setSpinner(usedCarsBrandsSpinner, brandEnTitles);
                }
                if (brandEnTitles.size()>0) {
                    currentBrand = brandIds.get(0);
//                    getModels();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(brandsRequest);
    }

    private void getModels(){
        modelArTitles.clear();
        modelEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest modelsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorModelByMakerId?makerId="+brandId, response -> {
            try {
                JSONArray modelsArray = new JSONArray(response);
                for (int i=0; i<modelsArray.length(); i++){
                    JSONObject modelObj = modelsArray.getJSONObject(i);
                    modelArTitles.add(modelObj.getString("ar_Text"));
                    modelEnTitles.add(modelObj.getString("en_Text"));
                }
                modelEnTitles.add(mainActivity.getResources().getString(R.string.other));
                modelArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(usedCarsModelsSpinner, modelArTitles);
                }else {
                    setSpinner(usedCarsModelsSpinner, modelEnTitles);
                }
                if (modelEnTitles.size()>0) {
                    currentModel = modelEnTitles.get(0) + "-" + modelArTitles.get(0);
//                    getTrims();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(modelsRequest);
    }

    private void getTrims(){
        trimArTitles.clear();
        trimEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest trimsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorTrimByModel?model="+currentModel, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray trimsArray = new JSONArray(response);
                for (int i=0; i<trimsArray.length(); i++){
                    JSONObject trimObj = trimsArray.getJSONObject(i);
                    trimArTitles.add(trimObj.getString("ar_Text"));
                    trimEnTitles.add(trimObj.getString("en_Text"));
                }
                trimArTitles.add(mainActivity.getResources().getString(R.string.other));
                trimEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(usedCarsTrimsSpinner, trimArTitles);
                }else {
                    setSpinner(usedCarsTrimsSpinner, trimEnTitles);
                }
                if (trimEnTitles.size()>0) {
                    currentTrim = trimEnTitles.get(0) + "-" + trimArTitles.get(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.trim), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.trim), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(trimsRequest);
    }

    private void getLocations(){
        locationEnTitles.clear();
        locationArTitles.clear();
        cities.clear();
        selectedCities.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    City city = new City();
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    locationArTitles.add(locationObj.getString("ar_Text"));
                    locationEnTitles.add(locationObj.getString("en_Text"));
                    city.setId(locationObj.getString("id"));
                    city.setTitleAr(locationObj.getString("ar_Text"));
                    city.setTitleEn(locationObj.getString("en_Text"));
                    cities.add(city);
                }
//                if (AppDefs.language.equals("ar")){
//                    setSpinner(usedCarsLocationSpinner, locationArTitles);
//                }else {
//                    setSpinner(usedCarsLocationSpinner, locationEnTitles);
//                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(locationsRequest);
    }

    private void getBoatHorsepower(){
        boatHorsepowerEnTitles.clear();
        boatHorsepowerArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest horsepowerRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllHorsepower?categoryId=5", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray horsepowerArray = new JSONArray(response);
                for (int i=0; i<horsepowerArray.length(); i++){
                    JSONObject horsepowerObj = horsepowerArray.getJSONObject(i);
                    boatHorsepowerArTitles.add(horsepowerObj.getString("ar_Text"));
                    boatHorsepowerEnTitles.add(horsepowerObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(boatHorsepowerSpinner, boatHorsepowerArTitles);
                }else {
                    setSpinner(boatHorsepowerSpinner, boatHorsepowerEnTitles);
                }
                if (boatHorsepowerEnTitles.size()>0) {
                    currentBoatHorsePower = boatHorsepowerEnTitles.get(0) + "-" + boatHorsepowerArTitles.get(0);
                }
                getBoatWarranty();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.horsepower), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.horsepower), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(horsepowerRequest);
    }

    private void getBoatWarranty(){
        boatWarrantyTitles.clear();
        boatWarrantyTitles.add(mainActivity.getResources().getString(R.string.no));
        boatWarrantyTitles.add(mainActivity.getResources().getString(R.string.yes));
        boatWarrantyTitles.add(mainActivity.getResources().getString(R.string.not_apply));

        setSpinner(boatWarrantySpinner, boatWarrantyTitles);
        currentBoatWarranty = "0";
    }

    private void getBoatAges(){
        boatAgeEnTitles.clear();
        boatAgeArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllAge?categoryId=5", response -> {
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    boatAgeArTitles.add(ageObj.getString("ar_Text"));
                    boatAgeEnTitles.add(ageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(boatAgeSpinner, boatAgeArTitles);
                }else {
                    setSpinner(boatAgeSpinner, boatAgeEnTitles);
                }
                currentBoatAge = boatAgeEnTitles.get(0)+"-"+boatAgeArTitles.get(0);
                getBoatUsages();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getBoatUsages(){

        boatUsageEnTitles.clear();
        boatUsageArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest usageRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllUsage?categoryId=5", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray usagesArray = new JSONArray(response);
                for (int i=0; i<usagesArray.length(); i++){
                    JSONObject usageObj = usagesArray.getJSONObject(i);
                    boatUsageArTitles.add(usageObj.getString("ar_Text"));
                    boatUsageEnTitles.add(usageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(boatUsageSpinner, boatUsageArTitles);
                }else {
                    setSpinner(boatUsageSpinner, boatUsageEnTitles);
                }
                currentBoatUsage = boatUsageEnTitles.get(0)+"-"+boatUsageArTitles.get(0);
                getBoatHorsepower();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(usageRequest);
    }

    private void getMachineryCategories(){
        machineryArCategoryTitles.clear();
        machineryEnCategoryTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest brandsRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId=6", response -> {
            try {
                JSONArray brandsArray = new JSONArray(response);
                for (int i=0; i<brandsArray.length(); i++){
                    JSONObject brandObj = brandsArray.getJSONObject(i);
                    machineryArCategoryTitles.add(brandObj.getString("ar_Name"));
                    machineryEnCategoryTitles.add(brandObj.getString("en_Name"));
                    machineryCatIds.add(brandObj.getString("id"));
                }
                machineryArCategoryTitles.add(mainActivity.getResources().getString(R.string.other));
                machineryEnCategoryTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(machineryCategoriesSpinner, machineryArCategoryTitles);
                }else {
                    setSpinner(machineryCategoriesSpinner, machineryEnCategoryTitles);
                }
                if (machineryCatIds.size() > 0){
                    machineryCategory = machineryCatIds.get(0);
                }else {
                    machineryCategory = "0";
                }
//                getMachinerySubCategories();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(brandsRequest);
    }

    private void getMachinerySubCategories(){
        machineryArSubCatTitles.clear();
        machineryEnSubCatTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest modelsRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+machineryCategory, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray modelsArray = new JSONArray(response);
                for (int i=0; i<modelsArray.length(); i++){
                    JSONObject modelObj = modelsArray.getJSONObject(i);
                    machineryArSubCatTitles.add(modelObj.getString("ar_Name"));
                    machineryEnSubCatTitles.add(modelObj.getString("en_Name"));
                    machinerySubCatIds.add(modelObj.getString("id"));
                }
                machineryEnSubCatTitles.add(mainActivity.getResources().getString(R.string.other));
                machineryArSubCatTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(machinerySubCatSpinner, machineryArSubCatTitles);
                }else {
                    setSpinner(machinerySubCatSpinner, machineryEnSubCatTitles);
                }
                if(machinerySubCatIds.size() > 0){
                    machinerySubCat = machinerySubCatIds.get(0);
                }else {
                    machinerySubCat = "0";
                }
                getMachineryHorsepower();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(modelsRequest);
    }

    private void getMachineryHorsepower(){
        machineryEnHorsepowerTitles.clear();
        machineryArHorsepowerTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest horsepowerRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllHorsepower?categoryId=2", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray horsepowerArray = new JSONArray(response);
                for (int i=0; i<horsepowerArray.length(); i++){
                    JSONObject horsepowerObj = horsepowerArray.getJSONObject(i);
                    machineryArHorsepowerTitles.add(horsepowerObj.getString("ar_Text"));
                    machineryEnHorsepowerTitles.add(horsepowerObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(machineryHorsepowerSpinner, machineryArHorsepowerTitles);
                }else {
                    setSpinner(machineryHorsepowerSpinner, machineryEnHorsepowerTitles);
                }
                machineryHorsepower = machineryEnHorsepowerTitles.get(0)+"-"+machineryArHorsepowerTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.horsepower), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.horsepower), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(horsepowerRequest);
    }

    private void getBikeCategories(){
        bikesEnCategoryTitles.clear();
        bikesArCategoryTitles.clear();
        bikesCatIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoryRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId=9", response -> {
            try {
                JSONArray subCategoryArray = new JSONArray(response);
                for (int i=0; i<subCategoryArray.length(); i++){
                    JSONObject subCategoryObj = subCategoryArray.getJSONObject(i);
                    bikesArCategoryTitles.add(subCategoryObj.getString("ar_Name"));
                    bikesEnCategoryTitles.add(subCategoryObj.getString("en_Name"));
                    bikesCatIds.add(subCategoryObj.getString("id"));
                }
                bikesArCategoryTitles.add(mainActivity.getResources().getString(R.string.other));
                bikesEnCategoryTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(bikesCategorySpinner, bikesArCategoryTitles);
                }else {
                    setSpinner(bikesCategorySpinner, bikesEnCategoryTitles);
                }
                if (bikesCatIds.size() > 0){
                    bikeCat = bikesCatIds.get(0);
                }else {
                    bikeCat = "0";
                }
//                getbikeMakers();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoryRequest);
    }

    private void getbikeMakers(){
        bikesEnSubCatTitles.clear();
        bikesArSubCatTitles.clear();
        bikesSubCatIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypeRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+bikeCat, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subTypeArray = new JSONArray(response);
                for (int i=0; i<subTypeArray.length(); i++){
                    JSONObject subTypeObj = subTypeArray.getJSONObject(i);
                    bikesArSubCatTitles.add(subTypeObj.getString("ar_Name"));
                    bikesEnSubCatTitles.add(subTypeObj.getString("en_Name"));
                    bikesSubCatIds.add(subTypeObj.getString("id"));
                }
                bikesEnSubCatTitles.add(mainActivity.getResources().getString(R.string.other));
                bikesArSubCatTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(bikesMakerSpinner,bikesArSubCatTitles);
                }else {
                    setSpinner(bikesMakerSpinner, bikesEnSubCatTitles);
                }
                if(bikesSubCatIds.size() > 0){
                    bikeSubCat = bikesSubCatIds.get(0);
                }else {
                    bikeSubCat = "0";
                }
                getBikePostedIn();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subTypeRequest);
    }

    private void getBikePostedIn(){
        adPostedBikeTitles.clear();
        adPostedBikeTitles.add(mainActivity.getResources().getString(R.string.posted_today));
        adPostedBikeTitles.add(mainActivity.getResources().getString(R.string.posted_week));
        adPostedBikeTitles.add(mainActivity.getResources().getString(R.string.posted_month));
        adPostedBikeTitles.add(mainActivity.getResources().getString(R.string.posted_year));

        setSpinner(bikePostedInSpinner, adPostedBikeTitles);
        bikeAdPosted = "1";
    }

    private void getPartCats(){
        partArCatTitles.clear();
        partEnCatTitles.clear();
        partCatIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoryRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId=7", response -> {
            try {
                JSONArray subCategoryArray = new JSONArray(response);
                for (int i=0; i<subCategoryArray.length(); i++){
                    JSONObject subCategoryObj = subCategoryArray.getJSONObject(i);
                    partArCatTitles.add(subCategoryObj.getString("ar_Name"));
                    partEnCatTitles.add(subCategoryObj.getString("en_Name"));
                    partCatIds.add(subCategoryObj.getString("id"));
                }
                partArCatTitles.add(mainActivity.getResources().getString(R.string.other));
                partEnCatTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(partsCategorySpinner, partArCatTitles);
                }else {
                    setSpinner(partsCategorySpinner, partEnCatTitles);
                }
                if (partCatIds.size() > 0){
                    partCat = partCatIds.get(0);
                }else {
                    partCat = "0";
                }
//                getPartMake();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoryRequest);
    }

    private void getPartMake(){
        partArMakeTitles.clear();
        partEnMakeTitles.clear();
        partMakeIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypeRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+partCat, response -> {
            try {
                JSONArray subTypeArray = new JSONArray(response);
                for (int i=0; i<subTypeArray.length(); i++){
                    JSONObject subTypeObj = subTypeArray.getJSONObject(i);
                    partArMakeTitles.add(subTypeObj.getString("ar_Name"));
                    partEnMakeTitles.add(subTypeObj.getString("en_Name"));
                    partMakeIds.add(subTypeObj.getString("id"));
                }
                partEnMakeTitles.add(mainActivity.getResources().getString(R.string.other));
                partArMakeTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(partsMakeSpinner,partArMakeTitles);
                }else {
                    setSpinner(partsMakeSpinner, partEnMakeTitles);
                }
                if(partMakeIds.size() > 0){
                    partMake = partMakeIds.get(0);
                }else {
                    partMake = "0";
                }
//                getParts();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subTypeRequest);
    }

    private void getParts(){
        partNameArTitles.clear();
        partNameEnTitles.clear();
        StringRequest partsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllParts?subTypeId="+partMake, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray partsArray = new JSONArray(response);
                for (int i=0; i<partsArray.length(); i++){
                    JSONObject partObj = partsArray.getJSONObject(i);
                    partNameArTitles.add(partObj.getString("ar_Text"));
                    partNameEnTitles.add(partObj.getString("en_Text"));
                }
                partNameEnTitles.add(mainActivity.getResources().getString(R.string.other));
                partNameArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(partsNameSpinner, partNameArTitles);
                }else {
                    setSpinner(partsNameSpinner, partNameEnTitles);
                }
                partName = partNameEnTitles.get(0)+"-"+partNameArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.parts), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.parts), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(partsRequest);
    }

    //Job filter
    private void getPostedIn(){
        adPostedTitles.clear();
        adPostedTitles.add(mainActivity.getResources().getString(R.string.posted_today));
        adPostedTitles.add(mainActivity.getResources().getString(R.string.posted_week));
        adPostedTitles.add(mainActivity.getResources().getString(R.string.posted_month));
        adPostedTitles.add(mainActivity.getResources().getString(R.string.posted_year));

        setSpinner(postedInSpinner, adPostedTitles);
        postedIn = "1";
        getProfessionalLevel();
    }

    private void getProfessionalLevel(){
        professionalLevelEnTitles.clear();
        professionalLevelArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest professionalLevelRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllCareerLevel", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray professionalLevelArray = new JSONArray(response);
                for (int i=0; i<professionalLevelArray.length(); i++){
                    JSONObject professionalLevelObj = professionalLevelArray.getJSONObject(i);
                    professionalLevelEnTitles.add(professionalLevelObj.getString("en_Text"));
                    professionalLevelArTitles.add(professionalLevelObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(professionalLevelSpinner, professionalLevelArTitles);
                }else {
                    setSpinner(professionalLevelSpinner, professionalLevelEnTitles);
                }
                professionalLevel = professionalLevelEnTitles.get(0)+"-"+professionalLevelArTitles.get(0);
                getEmploymentType();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.career_level), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.career_level), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(professionalLevelRequest);
    }

    private void getEmploymentType(){
        employmentTypeEnTitles.clear();
        employmentTypeArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest commitmentRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllEmploymentType", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray commitmentArray = new JSONArray(response);
                for (int i=0; i<commitmentArray.length(); i++){
                    JSONObject commitmentObj = commitmentArray.getJSONObject(i);
                    employmentTypeEnTitles.add(commitmentObj.getString("en_Text"));
                    employmentTypeArTitles.add(commitmentObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(employmentTypeSpinner, employmentTypeArTitles);
                }else {
                    setSpinner(employmentTypeSpinner, employmentTypeEnTitles);
                }
                employmentType = employmentTypeEnTitles.get(0)+"-"+employmentTypeArTitles.get(0);
                getJobTypes();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.commitment), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.commitment), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(commitmentRequest);
    }

    private void getJobTypes(){
        jobTypeEnTitles.clear();
        jobTypeArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest jobTypesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllJobType", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobTypesArray = new JSONArray(response);
                for (int i=0; i<jobTypesArray.length(); i++){
                    JSONObject jobTypeObj = jobTypesArray.getJSONObject(i);
                    jobTypeArTitles.add(jobTypeObj.getString("ar_Text"));
                    jobTypeEnTitles.add(jobTypeObj.getString("en_Text"));
                }
                jobTypeArTitles.add(mainActivity.getResources().getString(R.string.other));
                jobTypeEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(jobTypeSpinner, jobTypeArTitles);
                }else {
                    setSpinner(jobTypeSpinner, jobTypeEnTitles);
                }
                jobType = jobTypeEnTitles.get(0)+"-"+jobTypeArTitles.get(0);
                getWorkExperienceLevel();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.job_type), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.job_type), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(jobTypesRequest);
    }

    private void getWorkExperienceLevel(){
        workExperienceEnTitles.clear();
        workExperienceArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest workExperienceRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllWorkExperience", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray workExperienceArray = new JSONArray(response);
                for (int i=0; i<workExperienceArray.length(); i++){
                    JSONObject workExperienceObj = workExperienceArray.getJSONObject(i);
                    workExperienceEnTitles.add(workExperienceObj.getString("en_Text"));
                    workExperienceArTitles.add(workExperienceObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(workExperienceSpinner, workExperienceArTitles);
                }else {
                    setSpinner(workExperienceSpinner, workExperienceEnTitles);
                }
                workExperience = workExperienceEnTitles.get(0)+"-"+workExperienceArTitles.get(0);
                getEducationLevel();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(workExperienceRequest);
    }

    private void getEducationLevel(){
        educationLevelArTitles.clear();
        educationLevelEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest educationLevelRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllEducationLevel", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray educationLevelArray = new JSONArray(response);
                for (int i=0; i<educationLevelArray.length(); i++){
                    JSONObject educationLevelObj = educationLevelArray.getJSONObject(i);
                    educationLevelEnTitles.add(educationLevelObj.getString("en_Text"));
                    educationLevelArTitles.add(educationLevelObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(educationLevelSpinner, educationLevelArTitles);
                }else {
                    setSpinner(educationLevelSpinner, educationLevelEnTitles);
                }
                educationLevel = educationLevelEnTitles.get(0)+"-"+educationLevelArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.education_level), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.education_level), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(educationLevelRequest);
    }

    //Service filter
    private void getServicesSubCategories(){
        servicesSubCatEnTitles.clear();
        servicesSubCatArTitles.clear();
        serviceSubCatIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    serviceSubCatIds.add(subCategoryObj.getString("id"));
                    servicesSubCatArTitles.add(subCategoryObj.getString("ar_Name"));
                    servicesSubCatEnTitles.add(subCategoryObj.getString("en_Name"));
                }
                servicesSubCatEnTitles.add(mainActivity.getResources().getString(R.string.other));
                servicesSubCatArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(servicesSubCategorySpinner, servicesSubCatArTitles);
                }else {
                    setSpinner(servicesSubCategorySpinner, servicesSubCatEnTitles);
                }
                if (serviceSubCatIds.size() == 0){
                    serviceSubCat = "0";
                }else {
                    serviceSubCat = String.valueOf(serviceSubCatIds.get(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.error_occured));
            }
            getServicePostedIn();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoriesRequest);
    }

    private void getServicePostedIn(){
        servicesAdPostedTitles.clear();
        servicesAdPostedTitles.add(mainActivity.getResources().getString(R.string.posted_today));
        servicesAdPostedTitles.add(mainActivity.getResources().getString(R.string.posted_week));
        servicesAdPostedTitles.add(mainActivity.getResources().getString(R.string.posted_month));
        servicesAdPostedTitles.add(mainActivity.getResources().getString(R.string.posted_year));

        setSpinner(servicesPostedInSpinner, servicesAdPostedTitles);
        servicePostIn = "1";
    }

    //Business filter
    private void getBusinessSubCategories(){
        businessSubTypeArTitles.clear();
        businessSubTypeEnTitles.clear();
        businessSubTypeIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    businessSubTypeIds.add(subCategoryObj.getString("id"));
                    businessSubTypeArTitles.add(subCategoryObj.getString("ar_Name"));
                    businessSubTypeEnTitles.add(subCategoryObj.getString("en_Name"));
                }
                businessSubTypeEnTitles.add(mainActivity.getResources().getString(R.string.other));
                businessSubTypeArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(businessSubTypeSpinner, businessSubTypeArTitles);
                }else {
                    setSpinner(businessSubTypeSpinner, businessSubTypeEnTitles);
                }
                if (businessSubTypeIds.size() == 0){
                    businessSubType = "0";
                }else {
                    businessSubType = String.valueOf(businessSubTypeIds.get(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.error_occured));
            }
            getBusinessPostedIn();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoriesRequest);
    }

    private void getBusinessPostedIn(){
        businessPostedTitles.clear();
        businessPostedTitles.add(mainActivity.getResources().getString(R.string.posted_today));
        businessPostedTitles.add(mainActivity.getResources().getString(R.string.posted_week));
        businessPostedTitles.add(mainActivity.getResources().getString(R.string.posted_month));
        businessPostedTitles.add(mainActivity.getResources().getString(R.string.posted_year));

        setSpinner(businessAdPostedSpinner, businessPostedTitles);
        businessPostedIn = "1";
        getBusinessLocation();
    }

    private void getBusinessLocation(){
        businessCityEnTitles.clear();
        businessCityArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    businessCityArTitles.add(locationObj.getString("ar_Text"));
                    businessCityEnTitles.add(locationObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(businessCitySpinner, businessCityArTitles);
                }else {
                    setSpinner(businessCitySpinner, businessCityEnTitles);
                }
                businessCity = businessCityArTitles.get(0)+"-"+businessCityEnTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(locationsRequest);
    }

    //Number filter
    private void getEmirates(){
        emirateEnTitles.clear();
        emirateArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest emiratesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllEmirate", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray emiratesArray = new JSONArray(response);
                for (int i=0; i<emiratesArray.length(); i++){
                    JSONObject emirateObj = emiratesArray.getJSONObject(i);
                    emirateArTitles.add(emirateObj.getString("ar_Text"));
                    emirateEnTitles.add(emirateObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(emirateSpinner, emirateArTitles);
                }else {
                    setSpinner(emirateSpinner, emirateEnTitles);
                }
                if (emirateEnTitles.size()>0) {
                    emirate = emirateEnTitles.get(0) + "-" + emirateArTitles.get(0);
//                    getPlateType();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.emirate), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.emirate), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(emiratesRequest);
    }

    private void getPlateType(){
        plateTypeArTitles.clear();
        plateTypeEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest plateTypesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllPlateTypeByEmirate?emirate="+emirate, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray platesArray = new JSONArray(response);
                for (int i=0; i<platesArray.length(); i++){
                    JSONObject plateObj = platesArray.getJSONObject(i);
                    plateTypeArTitles.add(plateObj.getString("ar_Text"));
                    plateTypeEnTitles.add(plateObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(plateTypeSpinner, plateTypeArTitles);
                }else {
                    setSpinner(plateTypeSpinner, plateTypeEnTitles);
                }
                if (plateTypeEnTitles.size()>0) {
                    plateType = plateTypeEnTitles.get(0) + "-" + plateTypeArTitles.get(0);
//                    getPlateCodes(emirate, plateType);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_type), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_type), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(plateTypesRequest);
    }

    private void getPlateCodes(String emirate, String plateType){
        plateCodes.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest plateCodeRequest = new StringRequest(Request.Method.POST, Urls.DropDowns_URL+"GetAllPlateCode", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray array = new JSONArray(response);
                for (int i=0; i<array.length(); i++){
                    JSONObject plateCodeObj = array.getJSONObject(i);
                    plateCodes.add(plateCodeObj.getString("value"));
                }
                setSpinner(plateCodeSpinner, plateCodes);
                if (plateCodes.size() > 0) {
                    plateCode = plateCodes.get(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_code), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_code), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("plateType", plateType);
                params.put("emirate", emirate);
                return params;
            }
        };
        mainActivity.queue.add(plateCodeRequest);
    }

    private void getAllOperators(){
        operatorArTitles.clear();
        operatorEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest getOperatorsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllOperator", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray operatorsArray = new JSONArray(response);
                for (int i=0; i<operatorsArray.length(); i++){
                    JSONObject operatorObj = operatorsArray.getJSONObject(i);
                    operatorArTitles.add(operatorObj.getString("ar_Text"));
                    operatorEnTitles.add(operatorObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(operatorSpinner, operatorArTitles);
                }else {
                    setSpinner(operatorSpinner, operatorEnTitles);
                }
                if (operatorEnTitles.size()>0) {
                    operator = operatorEnTitles.get(0) + "-" + operatorArTitles.get(0);
//                    getMobileNumberCodes(operator);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.operator), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.operator), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(getOperatorsRequest);
    }

    private void getMobileNumberCodes(String operator){
        codes.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest mobileCodesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMobileNumberCodeByOperator?operatorName="+operator, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray codesArray = new JSONArray(response);
                for (int i=0; i< codesArray.length(); i++){
                    JSONObject codeObj = codesArray.getJSONObject(i);
                    codes.add(codeObj.getString("value"));
                }
                if (codes.size()>0) {
                    code = codes.get(0);
                }
                setSpinner(codeSpinner, codes);
                getNumberPlans();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.code), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.code), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(mobileCodesRequest);
    }

    private void getNumberPlans(){
        mobilePlanArTitles.clear();
        mobilePlanEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest plansRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllNumberPlans", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray plansArray = new JSONArray(response);
                for (int i=0; i<plansArray.length(); i++){
                    JSONObject planObj = plansArray.getJSONObject(i);
                    mobilePlanArTitles.add(planObj.getString("ar_Text"));
                    mobilePlanEnTitles.add(planObj.getString("en_Text"));
                }
                mobilePlan = mobilePlanEnTitles.get(0)+"-"+mobilePlanArTitles.get(0);
                if (AppDefs.language.equals("ar")){
                    setSpinner(mobilePlanSpinner, mobilePlanArTitles);
                }else {
                    setSpinner(mobilePlanSpinner, mobilePlanEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number_plan), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number_plan), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(plansRequest);
    }

    //Classifieds filter
    private void getClassifiedsSubCategories(String categoryId){
        classifiedsSubCatArTitles.clear();
        classifiedsSubCatEnTitles.clear();
        classifiedsSubCatIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    classifiedsSubCatIds.add(subCategoryObj.getString("id"));
                    classifiedsSubCatArTitles.add(subCategoryObj.getString("ar_Name"));
                    classifiedsSubCatEnTitles.add(subCategoryObj.getString("en_Name"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(classifiedsSubCatSpinner, classifiedsSubCatArTitles);
                }else {
                    setSpinner(classifiedsSubCatSpinner, classifiedsSubCatEnTitles);
                }
                if (classifiedsSubCatIds.size()>0){
                    classifiedsSubCatId = String.valueOf(classifiedsSubCatIds.get(0));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoriesRequest);
    }

    private void getClassifiedsSubTypes(String subCategoryId){
        classifiedsItemNameEnTitles.clear();
        classifiedsItemNameArTitles.clear();
        classifiedsItemNameIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypesRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+subCategoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subTypesArray = new JSONArray(response);
                for (int i=0; i<subTypesArray.length(); i++){
                    JSONObject subTypeObj = subTypesArray.getJSONObject(i);
                    classifiedsItemNameIds.add(subTypeObj.getString("id"));
                    classifiedsItemNameArTitles.add(subTypeObj.getString("ar_Name"));
                    classifiedsItemNameEnTitles.add(subTypeObj.getString("en_Name"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(classifiedsItemNameSpinner, classifiedsItemNameArTitles);
                }else {
                    setSpinner(classifiedsItemNameSpinner, classifiedsItemNameEnTitles);
                }
                if (classifiedsItemNameIds.size()>0){
                    classifiedsItemNameId = String.valueOf(classifiedsItemNameIds.get(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.item_name), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.item_name), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subTypesRequest);
    }

    private void getClassifiedsBrands(){
        classifiedsBrandEnTitles.clear();
        classifiedsBrandArTitles.clear();
        StringRequest classifiedsBrandRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllClassifiedBrand", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subTypesArray = new JSONArray(response);
                for (int i=0; i<subTypesArray.length(); i++){
                    JSONObject subTypeObj = subTypesArray.getJSONObject(i);
                    classifiedsBrandArTitles.add(subTypeObj.getString("ar_Text"));
                    classifiedsBrandEnTitles.add(subTypeObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(classifiedsBrandSpinner, classifiedsBrandArTitles);
                }else {
                    setSpinner(classifiedsBrandSpinner, classifiedsBrandEnTitles);
                }
                classifiedsBrand = classifiedsBrandEnTitles.get(0)+"-"+classifiedsBrandArTitles.get(0);
                getClassifieds1Ages();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brand), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brand), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(classifiedsBrandRequest);

    }

    private void getClassifieds1Ages(){
        classifiedsCategory1AgeEnTitles.clear();
        classifiedsCategory1AgeArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllAgeByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    classifiedsCategory1AgeArTitles.add(ageObj.getString("ar_Text"));
                    classifiedsCategory1AgeEnTitles.add(ageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(category1AgeSpinner, classifiedsCategory1AgeArTitles);
                }else {
                    setSpinner(category1AgeSpinner, classifiedsCategory1AgeEnTitles);
                }
                category1Age = classifiedsCategory1AgeEnTitles.get(0)+"-"+classifiedsCategory1AgeArTitles.get(0);
                getClassifieds1Usages();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getClassifieds1Usages(){
        classifiedsCategory1UsageArTitles.clear();
        classifiedsCategory1UsageEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest usageRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllUsageByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray usagesArray = new JSONArray(response);
                for (int i=0; i<usagesArray.length(); i++){
                    JSONObject usageObj = usagesArray.getJSONObject(i);
                    classifiedsCategory1UsageArTitles.add(usageObj.getString("ar_Text"));
                    classifiedsCategory1UsageEnTitles.add(usageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(category1UsageSpinner, classifiedsCategory1UsageArTitles);
                }else {
                    setSpinner(category1UsageSpinner, classifiedsCategory1UsageEnTitles);
                }
                category1Usage = classifiedsCategory1UsageEnTitles.get(0)+"-"+classifiedsCategory1UsageArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(usageRequest);
    }

    private void getClassifieds2Ages(){
        classifiedsCategory2AgeEnTitles.clear();
        classifiedsCategory2AgeArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllAgeByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    classifiedsCategory2AgeArTitles.add(ageObj.getString("ar_Text"));
                    classifiedsCategory2AgeEnTitles.add(ageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(category2AgeSpinner, classifiedsCategory2AgeArTitles);
                }else {
                    setSpinner(category2AgeSpinner, classifiedsCategory2AgeEnTitles);
                }
                category2Age = classifiedsCategory2AgeEnTitles.get(0)+"-"+classifiedsCategory2AgeArTitles.get(0);
                getClassifieds2Usages();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getClassifieds2Usages(){
        classifiedsCategory2UsageArTitles.clear();
        classifiedsCategory2UsageEnTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest usageRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllUsageByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray usagesArray = new JSONArray(response);
                for (int i=0; i<usagesArray.length(); i++){
                    JSONObject usageObj = usagesArray.getJSONObject(i);
                    classifiedsCategory2UsageArTitles.add(usageObj.getString("ar_Text"));
                    classifiedsCategory2UsageEnTitles.add(usageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(category2UsageSpinner, classifiedsCategory2UsageArTitles);
                }else {
                    setSpinner(category2UsageSpinner, classifiedsCategory2UsageEnTitles);
                }
                category2Usage = classifiedsCategory2UsageEnTitles.get(0)+"-"+classifiedsCategory2UsageArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(usageRequest);
    }

    //Electronics filter
    private void getElectronicsSubCategories(){
        electronicsSubCatArTitles.clear();
        electronicsSubCatEnTitles.clear();
        electronicsSubCatIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    electronicsSubCatIds.add(subCategoryObj.getString("id"));
                    electronicsSubCatArTitles.add(subCategoryObj.getString("ar_Name"));
                    electronicsSubCatEnTitles.add(subCategoryObj.getString("en_Name"));
                }
                electronicsSubCatEnTitles.add(mainActivity.getResources().getString(R.string.other));
                electronicsSubCatArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(electronicsSubCatSpinner, electronicsSubCatArTitles);
                }else {
                    setSpinner(electronicsSubCatSpinner, electronicsSubCatEnTitles);
                }
                if (electronicsSubCatIds.size() == 0){
                    electronicsSubCat = "0";
                }else {
                    electronicsSubCat = String.valueOf(electronicsSubCatIds.get(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoriesRequest);
    }

    private void getElectronicsTrim(String subCategoryId){
        electronicsTrimEnTitles.clear();
        electronicsTrimArTitles.clear();
        electronicsTrimIds.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypesRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+subCategoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subTypesArray = new JSONArray(response);
                for (int i=0; i<subTypesArray.length(); i++){
                    JSONObject subTypeObj = subTypesArray.getJSONObject(i);
                    electronicsTrimIds.add(subTypeObj.getString("id"));
                    electronicsTrimArTitles.add(subTypeObj.getString("ar_Name"));
                    electronicsTrimEnTitles.add(subTypeObj.getString("en_Name"));
                }
                electronicsTrimEnTitles.add(mainActivity.getResources().getString(R.string.other));
                electronicsTrimArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(electronicsTrimSpinner, electronicsTrimArTitles);
                }else {
                    setSpinner(electronicsTrimSpinner, electronicsTrimEnTitles);
                }
                if (electronicsTrimIds.size() > 1){
                    electronicsTrim = String.valueOf(electronicsTrimIds.get(0));
                }else {
                    electronicsTrim = "0";
                }
                getElectronicsAge();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.item_name), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.item_name), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subTypesRequest);
    }

    private void getElectronicsAge(){
        electronicsAgeEnTitles.clear();
        electronicsAgeArTitles.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllAgeByTypeId?typeId=8", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    electronicsAgeArTitles.add(ageObj.getString("ar_Text"));
                    electronicsAgeEnTitles.add(ageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(electronicsAgeSpinner, electronicsAgeArTitles);
                }else {
                    setSpinner(electronicsAgeSpinner, electronicsAgeEnTitles);
                }
                electronicsAge = electronicsAgeEnTitles.get(0)+"-"+electronicsAgeArTitles.get(0);
                getElectronicsWarranty();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getElectronicsWarranty(){
        electronicsWarrantyTitles.clear();
        electronicsWarrantyTitles.add(mainActivity.getResources().getString(R.string.no));
        electronicsWarrantyTitles.add(mainActivity.getResources().getString(R.string.yes));
        electronicsWarrantyTitles.add(mainActivity.getResources().getString(R.string.not_apply));

        setSpinner(electronicsWarrantySpinner, electronicsWarrantyTitles);
        getElectronicsPostedIn();
    }

    private void getElectronicsPostedIn(){
        electronicsPostedInTitles.clear();
        electronicsPostedInTitles.add(mainActivity.getResources().getString(R.string.posted_today));
        electronicsPostedInTitles.add(mainActivity.getResources().getString(R.string.posted_week));
        electronicsPostedInTitles.add(mainActivity.getResources().getString(R.string.posted_month));
        electronicsPostedInTitles.add(mainActivity.getResources().getString(R.string.posted_year));

        setSpinner(electronicsAdPostedSpinner, electronicsPostedInTitles);
        electronicsPosted = "1";
    }

    private void setSortSpinner(){
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("Price Highest to Lowest");
        sorts.add("Price Lowest to Highest");
        sorts.add("Newest to Oldest");
        sorts.add("Oldest to Newest");
        sorts.add("Default");


        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sort_by_spinner,sorts);
        sortAdapter.setDropDownViewResource(R.layout.sort_by_spinner);
        sortSpinner.setAdapter(sortAdapter);
    }

    private void openFilters(LinearLayout filterLayout){
        filterLayout.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(
                filterLayout.getWidth(),0, 0,0
        );
        int shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
        animation.setDuration(shortAnimationDuration);
        filterLayout.setAnimation(animation);
    }

    private void closeFilter(LinearLayout filterLayout){
        filterLayout.setVisibility(View.GONE);
        TranslateAnimation animation = new TranslateAnimation(
                0,filterLayout.getWidth(), 0,0
        );
        int shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
        animation.setDuration(shortAnimationDuration);
        filterLayout.setAnimation(animation);
        selectedCities.clear();
    }

    private void setAdapters(){
        ProductsAdapter productsAdapter = new ProductsAdapter(this, categoryName);
        FiltersAdapter filtersAdapter = new FiltersAdapter();
        filterRV.setAdapter(filtersAdapter);
        filterRV.setLayoutManager(new LinearLayoutManager(mainActivity, RecyclerView.HORIZONTAL, false));

        FeaturedNumbersAdapter featuredNumbersAdapter = new FeaturedNumbersAdapter(this);
        JobsFeaturedAdapter jobsFeaturedAdapter = new JobsFeaturedAdapter();
        JobsWantedFeaturedAdapter jobsWantedFeaturedAdapter = new JobsWantedFeaturedAdapter();

//        MotorItemAdapter motorItemAdapter = new MotorItemAdapter(this);
//        MotorItemGridAdapter motorItemGridAdapter = new MotorItemGridAdapter(this);
        PropertyItemAdapter propertyItemAdapter = new PropertyItemAdapter(this);
        PropertyItemGridAdapter propertyItemGridAdapter = new PropertyItemGridAdapter(this);
//        JobsAdapter jobsAdapter = new JobsAdapter(this);
//        JobItemGridAdapter jobItemGridAdapter = new JobItemGridAdapter(this);
//        JobsWantedAdapter jobsWantedAdapter = new JobsWantedAdapter(this);
//        JobWantedItemGridAdapter jobWantedItemGridAdapter = new JobWantedItemGridAdapter(this);
//        ServicesAdapter servicesAdapter = new ServicesAdapter(this);
//        ServicesGridAdapter servicesGridAdapter = new ServicesGridAdapter(this);
//        BusinessesAdapter businessesAdapter = new BusinessesAdapter(this);
//        BusinessesGridAdapter businessesGridAdapter = new BusinessesGridAdapter(this);
//        NumberItemAdapter numberItemAdapter = new NumberItemAdapter(this);
//        NumberGridItemAdapter numberGridItemAdapter = new NumberGridItemAdapter(this);
//        ElectronicsAdapter electronicsAdapter = new ElectronicsAdapter(this);
//        ElectronicsGridItemAdapter electronicsGridItemAdapter = new ElectronicsGridItemAdapter(this);

        switch (categoryName){
//            case "Motors":
//                allItemsRV.setAdapter(motorItemAdapter);
//                allItemsGridRV.setAdapter(motorItemGridAdapter);
//                break;
            case "Property":
                allItemsRV.setAdapter(propertyItemAdapter);
                allItemsGridRV.setAdapter(propertyItemGridAdapter);
                break;
//            case "Jobs":
//                if (position!=0) {
//                    allItemsRV.setAdapter(jobsAdapter);
//                    allItemsGridRV.setAdapter(jobItemGridAdapter);
//                }else {
//                    allItemsRV.setAdapter(jobsWantedAdapter);
//                    allItemsGridRV.setAdapter(jobWantedItemGridAdapter);
//                }
//                break;
//            case "Services":
//                allItemsRV.setAdapter(servicesAdapter);
//                allItemsGridRV.setAdapter(servicesGridAdapter);
//                break;
//            case "Business":
//                allItemsRV.setAdapter(businessesAdapter);
//                allItemsGridRV.setAdapter(businessesGridAdapter);
//                break;
//            case "Numbers":
//                allItemsRV.setAdapter(numberItemAdapter);
//                allItemsGridRV.setAdapter(numberGridItemAdapter);
//                break;
//            case "Classifieds":
//            case "Electronics":
//                allItemsRV.setAdapter(electronicsAdapter);
//                allItemsGridRV.setAdapter(electronicsGridItemAdapter);
//                break;
        }
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public void navigateToMotorsDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToMotorDetailsFragment2(position, false));
    }

    public void navigateToPropertyDetails(){
//        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToPropertyDetailsFragment2());
    }

    public void navigateToServicesDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToServicesDetailsFragment2(position, false));
    }

    public void navigateToJobOpeningDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToJobDetailsFragment2(position, false));
    }

    public void navigateToNumberDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToNumberDetailsFragment2(position, false));
    }

    public void navigateToBusinessDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToBusinessDetailsFragment2(position, false));
    }

    public void navigateToElectronicsDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToElectronicsDetailsFragment2(position, false, false));
    }

    public void navigateToJobWantedDetails(int position){
        navController.navigate(SubCategoryFragmentDirections.actionSubCategoryFragment2ToJobWantedDetailsFragment2(position, false));
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    private void getUsedCarsAds(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest getAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=1&categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray adsArray = new JSONArray(response);
                for (int i=0; i< adsArray.length(); i++){
                    UsedCarAd usedCarAd = new UsedCarAd();
                    JSONObject adObj = adsArray.getJSONObject(i);
                    usedCarAd.setId(adObj.getString("id"));
                    usedCarAd.setChats(adObj.getString("chates"));
                    usedCarAd.setViews(adObj.getString("views"));
                    usedCarAd.setTitle(adObj.getString("title"));
                    usedCarAd.setDescription(adObj.getString("description"));
                    usedCarAd.setEnLocation(adObj.getString("en_Location"));
                    usedCarAd.setArLocation(adObj.getString("ar_Location"));
                    usedCarAd.setLatitude(adObj.getString("lat"));
                    usedCarAd.setLongitude(adObj.getString("lng"));
                    usedCarAd.setStatus(adObj.getString("status"));
                    String postedDate = mainActivity.convertDate(adObj.getString("postDate").substring(0, adObj.getString("postDate").lastIndexOf(".")));
                    usedCarAd.setPostDate(postedDate);
                    usedCarAd.setCategoryId(adObj.getString("categoryId"));
                    usedCarAd.setCategoryEnName(adObj.getString("categoryEnName"));
                    usedCarAd.setCategoryArName(adObj.getString("categoryArName"));
                    usedCarAd.setSubCategoryId(adObj.getString("subCategoryId"));
                    usedCarAd.setSubCategoryEnName(adObj.getString("subCategoryEn_Name"));
                    usedCarAd.setSubCategoryArName(adObj.getString("subCategoryAr_Name"));
                    usedCarAd.setOtherSubCategory(adObj.getString("otherSubCategory"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeEnName(adObj.getString("subTypeEn_Name"));
                    usedCarAd.setSubTypeArName(adObj.getString("subTypeId"));
                    usedCarAd.setSubTypeId(adObj.getString("subTypeAr_Name"));
                    usedCarAd.setOtherSubType(adObj.getString("otherSubType"));
                    usedCarAd.setTypeId(adObj.getString("typeId"));
                    usedCarAd.setUserId(adObj.getString("userId"));
                    usedCarAd.setAgentId(adObj.getString("agentId"));
                    usedCarAd.setEnMaker(adObj.getString("en_Maker"));
                    usedCarAd.setArMaker(adObj.getString("ar_Maker"));
                    usedCarAd.setEnModel(adObj.getString("en_Model"));
                    usedCarAd.setArModel(adObj.getString("ar_Model"));
                    usedCarAd.setEnTrim(adObj.getString("en_Trim"));
                    usedCarAd.setArTrim(adObj.getString("ar_Trim"));
                    usedCarAd.setEnColor(adObj.getString("en_Color"));
                    usedCarAd.setArColor(adObj.getString("ar_Color"));
                    usedCarAd.setKiloMeters(adObj.getString("kilometers"));
                    usedCarAd.setYear(adObj.getString("year"));
                    usedCarAd.setEnDoors(adObj.getString("en_Doors"));
                    usedCarAd.setArDoors(adObj.getString("ar_Doors"));
                    usedCarAd.setWarranty(adObj.getString("warranty"));
                    usedCarAd.setEnTransmission(adObj.getString("en_Transmission"));
                    usedCarAd.setArTransmission(adObj.getString("ar_Transmission"));
                    usedCarAd.setEnRegionalSpecs(adObj.getString("en_RegionalSpecs"));
                    usedCarAd.setArRegionalSpecs(adObj.getString("ar_RegionalSpecs"));
                    usedCarAd.setEnBodyType(adObj.getString("en_BodyType"));
                    usedCarAd.setArBodyType(adObj.getString("ar_BodyType"));
                    usedCarAd.setEnFuelType(adObj.getString("en_FuelType"));
                    usedCarAd.setArFuelType(adObj.getString("ar_FuelType"));
                    usedCarAd.setEnNoOfCylinders(adObj.getString("en_NoOfCylinders"));
                    usedCarAd.setArNoOfCylinders(adObj.getString("ar_NoOfCylinders"));
                    usedCarAd.setEnHorsepower(adObj.getString("en_Horsepower"));
                    usedCarAd.setArHorsepower(adObj.getString("ar_Horsepower"));
                    usedCarAd.setEnSteeringSide(adObj.getString("en_SteeringSide"));
                    usedCarAd.setArSteeringSide(adObj.getString("ar_SteeringSide"));
                    usedCarAd.setPrice(adObj.getString("price"));
                    usedCarAd.setUserImage(adObj.getString("userImage"));
                    usedCarAd.setPhoneNumber(adObj.getString("phoneNumber"));
                    usedCarAd.setEnCondition(adObj.getString("en_Condition"));
                    usedCarAd.setArCondition(adObj.getString("ar_Condition"));
                    usedCarAd.setEnMechanicalCondition(adObj.getString("en_MechanicalCondition"));
                    usedCarAd.setArMechanicalCondition(adObj.getString("ar_MechanicalCondition"));
                    usedCarAd.setEnCapacity(adObj.getString("en_Capacity"));
                    usedCarAd.setArCapacity(adObj.getString("ar_Capacity"));
                    usedCarAd.setEnEngineSize(adObj.getString("en_EngineSize"));
                    usedCarAd.setArEngineSize(adObj.getString("ar_EngineSize"));
                    usedCarAd.setEnAge(adObj.getString("en_Age"));
                    usedCarAd.setArAge(adObj.getString("ar_Age"));
                    usedCarAd.setEnUsage(adObj.getString("en_Usage"));
                    usedCarAd.setArUsage(adObj.getString("ar_Usage"));
                    usedCarAd.setEnLength(adObj.getString("en_Length"));
                    usedCarAd.setArLength(adObj.getString("ar_Length"));
                    usedCarAd.setEnWheels(adObj.getString("en_Wheels"));
                    usedCarAd.setArWheels(adObj.getString("ar_Wheels"));
                    usedCarAd.setEnSellerType(adObj.getString("en_SellerType"));
                    usedCarAd.setArSellerType(adObj.getString("ar_SellerType"));
                    usedCarAd.setEnDriveSystem(adObj.getString("en_FinalDriveSystem"));
                    usedCarAd.setArDriveSystem(adObj.getString("ar_FinalDriveSystem"));
                    usedCarAd.setEnPartName(adObj.getString("en_PartName"));
                    usedCarAd.setArPartName(adObj.getString("ar_PartName"));
                    usedCarAd.setNameOfPart(adObj.getString("nameOfPart"));
                    JSONArray pics = adObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            usedCarAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    usedCarAd.setPictures(pictures);
                    usedCarAd.setIsFav(adObj.getString("isFavorite"));
                    usedCars.add(usedCarAd);
                }
                AppDefs.motorAds = usedCars;
                setUsedCarsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(getAdsRequest);
    }

    private void setUsedCarsAdapter(){
        mainActivity.hideProgressDialog();
        MotorItemAdapter motorItemAdapter = new MotorItemAdapter(this, usedCars, categoryId);
        MotorItemGridAdapter motorItemGridAdapter = new MotorItemGridAdapter(this, usedCars, categoryId);
        MotorItemLinearAdapter motorItemLinearAdapter = new MotorItemLinearAdapter(this, usedCars, categoryId);
        allItemsRV.setAdapter(motorItemAdapter);
        allItemsGridRV.setAdapter(motorItemGridAdapter);
        allItemsLinearRV.setAdapter(motorItemLinearAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        allItemsLinearRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (usedCars.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getJobAds(String categoryId){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        jobAds.clear();
        StringRequest jobAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=3&categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobsArray = new JSONArray(response);
                for (int i=0; i<jobsArray.length(); i++){
                    JSONObject jobObj = jobsArray.getJSONObject(i);
                    JobAd jobAd = new JobAd();
                    jobAd.setId(jobObj.getString("id"));
                    jobAd.setAgentId(jobObj.getString("AgentId"));
                    jobAd.setTitle(jobObj.getString("title"));
                    jobAd.setDescription(jobObj.getString("description"));
                    jobAd.setEnLocation(jobObj.getString("en_Location"));
                    jobAd.setArLocation(jobObj.getString("ar_Location"));
                    jobAd.setLat(jobObj.getString("lat"));
                    jobAd.setLng(jobObj.getString("lng"));
                    String postedDate = mainActivity.convertDate(jobObj.getString("postDate").substring(0, jobObj.getString("postDate").lastIndexOf(".")));
                    jobAd.setPostedDate(postedDate);
                    jobAd.setCategoryId(jobObj.getString("categoryId"));
                    jobAd.setCategoryArName(jobObj.getString("categoryArName"));
                    jobAd.setCategoryEnName(jobObj.getString("categoryEnName"));
                    jobAd.setUserId(jobObj.getString("userId"));
                    jobAd.setArJobType(jobObj.getString("ar_JobType"));
                    jobAd.setEnJobType(jobObj.getString("en_JobType"));
                    jobAd.setOtherJobType(jobObj.getString("otherJobType"));
                    jobAd.setPhoneNumber(jobObj.getString("phoneNumber"));
                    jobAd.setCv(jobObj.getString("cv"));
                    jobAd.setArGender(jobObj.getString("ar_Gender"));
                    jobAd.setEnGender(jobObj.getString("en_Gender"));
                    jobAd.setEnNationality(jobObj.getString("en_Nationality"));
                    jobAd.setArNationality(jobObj.getString("ar_Nationality"));
                    jobAd.setArCurrentLocation(jobObj.getString("ar_CurrentLocation"));
                    jobAd.setEnCurrentLocation(jobObj.getString("en_CurrentLocation"));
                    jobAd.setEnEducationalLevel(jobObj.getString("en_EducationLevel"));
                    jobAd.setArEducationalLevel(jobObj.getString("ar_EducationLevel"));
                    jobAd.setCurrentPosition(jobObj.getString("currentPosition"));
                    jobAd.setArWorkExperience(jobObj.getString("ar_WorkExperience"));
                    jobAd.setEnWorkExperience(jobObj.getString("en_Commitment"));
                    jobAd.setArCommitment(jobObj.getString("ar_Commitment"));
                    jobAd.setEnCommitment(jobObj.getString("en_Commitment"));
                    jobAd.setArNoticePeriod(jobObj.getString("ar_NoticePeriod"));
                    jobAd.setEnNoticePeriod(jobObj.getString("en_NoticePeriod"));
                    jobAd.setArVisaStatus(jobObj.getString("ar_VisaStatus"));
                    jobAd.setEnVisaStatus(jobObj.getString("en_VisaStatus"));
                    jobAd.setEnCareerLevel(jobObj.getString("en_CareerLevel"));
                    jobAd.setArCareerLevel(jobObj.getString("ar_CareerLevel"));
                    jobAd.setExpectedSalary(jobObj.getString("expectedMonthlySalary"));
                    jobAd.setArEmploymentType(jobObj.getString("ar_EmploymentType"));
                    jobAd.setEnEmploymentType(jobObj.getString("en_EmploymentType"));
                    jobAd.setArMinWorkExperience(jobObj.getString("ar_MinWorkExperience"));
                    jobAd.setEnMinWorkExperience(jobObj.getString("en_MinWorkExperience"));
                    jobAd.setEnMinEducationalLevel(jobObj.getString("en_MinEducationLevel"));
                    jobAd.setArMinEducationalLevel(jobObj.getString("ar_MinEducationLevel"));
                    jobAd.setCompanyName(jobObj.getString("companyName"));
                    jobAd.setJobTitle(jobObj.getString("jobTitle"));
                    jobAd.setIsFav(jobObj.getString("isFavorite"));
                    jobAds.add(jobAd);
                }
                AppDefs.jobAds = jobAds;
                if (categoryId.equals("3")){
                    setOpeningJobsAdapter();
                }else {
                    setWantedJobsAdapter();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(jobAdsRequest);
    }

    private void setWantedJobsAdapter(){
        JobsWantedAdapter jobsWantedAdapter = new JobsWantedAdapter(this, jobAds);
        JobWantedItemGridAdapter jobWantedItemGridAdapter = new JobWantedItemGridAdapter(this, jobAds);

        allItemsRV.setAdapter(jobsWantedAdapter);
        allItemsGridRV.setAdapter(jobWantedItemGridAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (jobAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void setOpeningJobsAdapter(){
        JobsAdapter jobsAdapter = new JobsAdapter(this, jobAds);
        JobItemGridAdapter jobItemGridAdapter = new JobItemGridAdapter(this, jobAds);

        allItemsRV.setAdapter(jobsAdapter);
        allItemsGridRV.setAdapter(jobItemGridAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (jobAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }

    }

    private void getServicesAds(){
        serviceAds.clear();
        StringRequest serviceAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=4&categoryId="+categoryId, response -> {
            try {
                JSONArray serviceAdsArray = new JSONArray(response);
                for (int i=0; i<serviceAdsArray.length(); i++){
                    JSONObject serviceObj = serviceAdsArray.getJSONObject(i);
                    ServiceAd serviceAd = new ServiceAd();
                    serviceAd.setId(serviceObj.getString("id"));
                    serviceAd.setAgentId(serviceObj.getString("AgentId"));
                    serviceAd.setTitle(serviceObj.getString("title"));
                    serviceAd.setEnLocation(serviceObj.getString("en_Location"));
                    serviceAd.setArLocation(serviceObj.getString("ar_Location"));
                    String postedDate = mainActivity.convertDate(serviceObj.getString("postDate").substring(0, serviceObj.getString("postDate").lastIndexOf(".")));
                    serviceAd.setPostedDate(postedDate);
                    serviceAd.setCategoryArName(serviceObj.getString("categoryArName"));
                    serviceAd.setCategoryEnName(serviceObj.getString("categoryEnName"));
                    serviceAd.setDescription(serviceObj.getString("description"));
                    serviceAd.setLat(serviceObj.getString("lat"));
                    serviceAd.setLng(serviceObj.getString("lng"));
                    serviceAd.setCategoryId(serviceObj.getString("categoryId"));
                    serviceAd.setUserId(serviceObj.getString("userId"));
                    serviceAd.setServiceTypeEnName(serviceObj.getString("serviceTypeEn_Name"));
                    serviceAd.setServiceTypeArName(serviceObj.getString("serviceTypeAr_Name"));
                    serviceAd.setOtherServiceType(serviceObj.getString("otherServiceType"));
                    serviceAd.setPhoneNumber(serviceObj.getString("phoneNumber"));
                    serviceAd.setCarLiftFrom(serviceObj.getString("carLiftFrom"));
                    serviceAd.setCarLiftTo(serviceObj.getString("carLiftTo"));
                    serviceAd.setIsFav(serviceObj.getString("isFavorite"));
                    serviceAds.add(serviceAd);
                }
                AppDefs.serviceAds = serviceAds;
                setServicesAdsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));

            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error));

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(serviceAdsRequest);
    }

    private void setServicesAdsAdapter(){
        ServicesAdapter servicesAdapter = new ServicesAdapter(this, serviceAds);
        ServicesGridAdapter servicesGridAdapter = new ServicesGridAdapter(this, serviceAds);

        allItemsRV.setAdapter(servicesAdapter);
        allItemsGridRV.setAdapter(servicesGridAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (serviceAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getBusinessAds(){
        businessAds.clear();
        StringRequest businessAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=5&categoryId="+categoryId, response -> {
            try {
                JSONArray businessArray = new JSONArray(response);
                for (int i=0; i<businessArray.length(); i++){
                    JSONObject businessObj = businessArray.getJSONObject(i);
                    BusinessAd businessAd = new BusinessAd();
                    businessAd.setId(businessObj.getString("id"));
                    businessAd.setTitle(businessObj.getString("title"));
                    businessAd.setArLocation(businessObj.getString("ar_Location"));
                    businessAd.setEnLocation(businessObj.getString("en_Location"));
                    businessAd.setPrice(businessObj.getString("price"));
                    String postedDate = mainActivity.convertDate(businessObj.getString("postDate").substring(0, businessObj.getString("postDate").lastIndexOf(".")));
                    businessAd.setPostedDate(postedDate);
                    businessAd.setDescription(businessObj.getString("description"));
                    businessAd.setLat(businessObj.getString("lat"));
                    businessAd.setLng(businessObj.getString("lng"));
                    businessAd.setCategoryId(businessObj.getString("categoryId"));
                    businessAd.setCategoryArName(businessObj.getString("categoryArName"));
                    businessAd.setCategoryEnName(businessObj.getString("categoryEnName"));
                    businessAd.setOtherCategoryNAme(businessObj.getString("otherCategoryName"));
                    businessAd.setUserId(businessObj.getString("userId"));
                    businessAd.setPhoneNumber(businessObj.getString("phoneNumber"));
                    businessAd.setIsFav(businessObj.getString("isFavorite"));
                    JSONArray pics = businessObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            businessAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    businessAd.setPictures(pictures);
                    businessAds.add(businessAd);
                }
                AppDefs.businessAds = businessAds;
                setBusinessAdsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(businessAdsRequest);
    }

    private void setBusinessAdsAdapter(){
        BusinessesAdapter businessesAdapter = new BusinessesAdapter(this, businessAds);
        BusinessesGridAdapter businessesGridAdapter = new BusinessesGridAdapter(this, businessAds);

        allItemsRV.setAdapter(businessesAdapter);
        allItemsGridRV.setAdapter(businessesGridAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (businessAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getNumbersAds(){
        numberAds.clear();
        StringRequest numberAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=7&categoryId="+categoryId, response -> {
            try {
                JSONArray numbersArray = new JSONArray(response);
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("id"));
                    numberAd.setAgentId(numberObj.getString("AgentId"));
                    numberAd.setTitle(numberObj.getString("title"));
                    if (numberObj.has("price")){
                        numberAd.setPrice(numberObj.getString("price"));
                    }
                    numberAd.setArLocation(numberObj.getString("ar_Location"));
                    numberAd.setEnLocation(numberObj.getString("en_Location"));
                    numberAd.setDescription(numberObj.getString("description"));
                    numberAd.setUserId(numberObj.getString("userId"));
                    numberAd.setLat(numberObj.getString("lat"));
                    numberAd.setLng(numberObj.getString("lng"));
                    String postedDate = mainActivity.convertDate(numberObj.getString("postDate").substring(0, numberObj.getString("postDate").lastIndexOf(".")));
                    numberAd.setPostDate(postedDate);
                    numberAd.setEmirate(numberObj.getString("en_Emirate"));
                    numberAd.setArEmirate(numberObj.getString("ar_Emirate"));
                    numberAd.setPlateCode(numberObj.getString("plateCode"));
                    numberAd.setArPlateType(numberObj.getString("ar_PlateType"));
                    numberAd.setPlateType(numberObj.getString("en_PlateType"));
                    numberAd.setNumber(numberObj.getString("number"));
                    numberAd.setCategory(numberObj.getString("categoryEnName"));
                    numberAd.setOperator(numberObj.getString("en_Operator"));
                    numberAd.setArOperator(numberObj.getString("ar_Operator"));
                    numberAd.setEnMobilePlan(numberObj.getString("en_MobileNumberPlan"));
                    numberAd.setArMobilePlan(numberObj.getString("ar_MobileNumberPlan"));
                    numberAd.setCode(numberObj.getString("code"));
                    numberAd.setPhoneNumber(numberObj.getString("phoneNumber"));
                    numberAd.setIsFav(numberObj.getString("isFavorite"));
                    numberAds.add(numberAd);
                }
                AppDefs.numberAds = numberAds;
                setNumberAdsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error))){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(numberAdsRequest);
    }

    private void setNumberAdsAdapter(){
        NumberItemAdapter numberItemAdapter = new NumberItemAdapter(this, numberAds);
        NumberGridItemAdapter numberGridItemAdapter = new NumberGridItemAdapter(this, numberAds);

        allItemsRV.setAdapter(numberItemAdapter);
        allItemsGridRV.setAdapter(numberGridItemAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (numberAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getElectronicsAds(){
        electronicAds.clear();
        StringRequest electronicAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=8&categoryId="+categoryId, response -> {
            try {
                JSONArray electronicsArray = new JSONArray(response);
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("title"));
                    electronicAd.setArLocation(electronicObj.getString("ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("en_Location"));
                    electronicAd.setPrice(electronicObj.getString("price"));
                    electronicAd.setEnAge(electronicObj.getString("en_Age"));
                    electronicAd.setArAge(electronicObj.getString("ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("en_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("ar_Usage"));
                    electronicAd.setEnColor(electronicObj.getString("en_Color"));
                    electronicAd.setArColor(electronicObj.getString("ar_Color"));
                    electronicAd.setEnVersion(electronicObj.getString("version_En"));
                    electronicAd.setArVersion(electronicObj.getString("version_Ar"));
                    electronicAd.setEnRam(electronicObj.getString("ram_En"));
                    electronicAd.setArRam(electronicObj.getString("ram_Ar"));
                    electronicAd.setEnStorage(electronicObj.getString("storage_En"));
                    electronicAd.setArStorage(electronicObj.getString("storage_Ar"));
                    electronicAd.setWarranty(electronicObj.getString("warranty"));
                    electronicAd.setDescription(electronicObj.getString("description"));
                    electronicAd.setLat(electronicObj.getString("lat"));
                    electronicAd.setLng(electronicObj.getString("lng"));
                    electronicAd.setUserId(electronicObj.getString("userId"));
                    electronicAd.setSubCatArName(electronicObj.getString("subCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("subCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("otherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("subTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("subTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("otherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("phoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("isFavorite"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("postDate").substring(0, electronicObj.getString("postDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    electronicAds.add(electronicAd);
                }
                AppDefs.electronicAds = electronicAds;
                setElectronicAdsAdapter(electronicAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(electronicAdsRequest);
    }

    private void getClassifiedsAds(){
        classifiedsAds.clear();
        StringRequest electronicAdsRequest = new StringRequest(Request.Method.POST, Urls.Ads_URL+"GetAds?typeId=6&categoryId="+categoryId, response -> {
            try {
                JSONArray electronicsArray = new JSONArray(response);
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("id"));
                    electronicAd.setAgentId(electronicObj.getString("AgentId"));
                    electronicAd.setTitle(electronicObj.getString("title"));
                    electronicAd.setArLocation(electronicObj.getString("ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("en_Location"));
                    electronicAd.setPrice(electronicObj.getString("price"));
                    electronicAd.setEnAge(electronicObj.getString("en_Age"));
                    electronicAd.setArAge(electronicObj.getString("ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("en_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("ar_Usage"));
                    electronicAd.setEnBrand(electronicObj.getString("en_Brand"));
                    electronicAd.setArBrand(electronicObj.getString("ar_Brand"));
                    electronicAd.setEnCondition(electronicObj.getString("en_Condition"));
                    electronicAd.setArCondition(electronicObj.getString("ar_Condition"));
                    electronicAd.setDescription(electronicObj.getString("description"));
                    electronicAd.setLat(electronicObj.getString("lat"));
                    electronicAd.setLng(electronicObj.getString("lng"));
                    electronicAd.setUserId(electronicObj.getString("userId"));
                    electronicAd.setSubCatArName(electronicObj.getString("subCategoryAr_Name"));
                    electronicAd.setSubCatEnName(electronicObj.getString("subCategoryEn_Name"));
                    electronicAd.setOtherSubCat(electronicObj.getString("otherSubCategory"));
                    electronicAd.setSubTypeArName(electronicObj.getString("subTypeAr_Name"));
                    electronicAd.setSubTypeEnName(electronicObj.getString("subTypeEn_Name"));
                    electronicAd.setOtherSubType(electronicObj.getString("otherSubType"));
                    electronicAd.setPhoneNumber(electronicObj.getString("phoneNumber"));
                    electronicAd.setIsFav(electronicObj.getString("isFavorite"));
                    String postedDate = mainActivity.convertDate(electronicObj.getString("postDate").substring(0, electronicObj.getString("postDate").lastIndexOf(".")));
                    electronicAd.setPostedDate(postedDate);
                    JSONArray pics = electronicObj.getJSONArray("pictures");
                    ArrayList<Picture> pictures = new ArrayList<>();
                    for (int j=0; j<pics.length(); j++){
                        JSONObject picObj = pics.getJSONObject(j);
                        Picture picture = new Picture();
                        picture.setId(picObj.getString("id"));
                        picture.setImageURL(picObj.getString("imageURL"));
                        picture.setMain(picObj.getBoolean("mainPicture"));
                        if (picture.isMain()){
                            electronicAd.setMainImage(picture.getImageURL());
                        }
                        pictures.add(picture);
                    }
                    electronicAd.setPictures(pictures);
                    classifiedsAds.add(electronicAd);
                }
                AppDefs.electronicAds = classifiedsAds;
                setClassifiedsAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> mainActivity.showResponseMessage(categoryName, mainActivity.getResources().getString(R.string.internet_connection_error))){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(electronicAdsRequest);
    }

    private void setClassifiedsAdsAdapter(ArrayList<ElectronicAd> ads){
        ClassifiedsAdapter electronicsAdapter = new ClassifiedsAdapter(this, ads);
        ClassifiedsGridItemAdapter electronicsGridItemAdapter = new ClassifiedsGridItemAdapter(this, ads);

        allItemsRV.setAdapter(electronicsAdapter);
        allItemsGridRV.setAdapter(electronicsGridItemAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (ads.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void setElectronicAdsAdapter(ArrayList<ElectronicAd> ads){
        ElectronicsAdapter electronicsAdapter = new ElectronicsAdapter(this, ads, categoryId);
        ElectronicsGridItemAdapter electronicsGridItemAdapter = new ElectronicsGridItemAdapter(this, ads, categoryId);

        allItemsRV.setAdapter(electronicsAdapter);
        allItemsGridRV.setAdapter(electronicsGridItemAdapter);
        allItemsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        allItemsGridRV.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (ads.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    public void addToFavourite(String adId){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
            favObj.put("UserId", AppDefs.user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"AddToFavorite", favObj, response -> {
//            mainActivity.hideProgressDialog();
            setData();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_success), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }

    public void removeFromFavourite(String adId){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"RemoveFromFavorite", favObj, response -> {
//            mainActivity.hideProgressDialog();
            setData();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_remove), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }

    public void sortPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.sort_by_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.sort);
        RadioButton rb1 = myAdsOptionsAlertView.findViewById(R.id.newest_oldest);
        RadioButton rb2 = myAdsOptionsAlertView.findViewById(R.id.oldest_newest);
        RadioButton rb3 = myAdsOptionsAlertView.findViewById(R.id.sort_test);
        RadioButton rb4 = myAdsOptionsAlertView.findViewById(R.id.default_sort);
        done.setOnClickListener(view -> {
            if (rb1.isChecked()){
                sortBy = "2";
            }else if (rb2.isChecked()){
                sortBy = "3";
            }else if (rb3.isChecked()){
                sortBy = "4";
            }else if (rb4.isChecked()){
                sortBy = "1";
            }
            switch(categoryName) {
                case "Motors":
                    switch (categoryId) {
                        case "2":
                        case "8":
                            currentBrand = AppDefs.brand;
                            currentModel = AppDefs.model;
                            filterUsedCars();
                            break;
                        case "5":
                            filterBoats();
                            break;
                        case "6":
                            filterMachines1();
                            break;
                        case "7":
                            filterParts1();
                            break;
                        case "9":
                            filterBikes1();
                            break;
                    }
                    typeId = "1";
                    break;
                case "Jobs":
                    currentLocation = AppDefs.savedSearchAd.getLocation();
                    filterJobs();
//                getJobAds(categoryId);
                    typeId = "3";
                    break;
                case "Services":
//                getServicesAds();
                    filterServices();
                    typeId = "4";
                    break;
                case "Business":
//                getBusinessAds();
                    filterBusiness();
                    typeId = "5";
                    break;
                case "Numbers":
//                    getNumbersAds();
                    filterNumbers();
                    typeId = "7";
                    break;
                case "Electronics":
//                    getElectronicsAds();
                    filterElectronics();
                    typeId = "8";
                    break;
                case "Classifieds":
//                    getClassifiedsAds();
                    filterClassifieds();
                    typeId = "6";
                    break;
//            case "Jobs":
//                getAds("3");
//                break;
//            case "Services":
//                getAds("4");
//                break;
//             case "Business":
//                 getAds("5");
//                 break;
//            case "Classifieds":
//                getAds("6");
//                break;
//            case "Numbers":
//                getAds("7");
//                break;
//            case "Electronics":
//                getAds("8");
//                break;
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    public void locationsPopUp(RecyclerView recyclerView){
        View alertView = LayoutInflater.from(getContext()).inflate(R.layout.locations_pop_up, null);
        AlertDialog alertBuilder = new AlertDialog.Builder(getContext()).setView(alertView).show();
        alertBuilder.show();
        alertBuilder.setCancelable(false);

        alertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        RecyclerView locationsRV = alertView.findViewById(R.id.locations_RV);
        setLocationsAdapter(locationsRV);

        MaterialButton done = alertView.findViewById(R.id.done);
        done.setOnClickListener(view -> {
            setSelectedLocationsAdapter(recyclerView);
            alertBuilder.dismiss();
        });
    }

    private void setLocationsAdapter(RecyclerView recyclerView){
        LocationsAdapter locationsAdapter = new LocationsAdapter(this, cities);
        recyclerView.setAdapter(locationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    private void setSelectedLocationsAdapter(RecyclerView locationRV){
        if (selectedCities.size()>0) {
            currentLocation = selectedCities.get(0).getTitleEn() + "-" + selectedCities.get(0).getTitleAr();
        }
        SelectedLocationsAdapter locationsAdapter = new SelectedLocationsAdapter(this, selectedCities);
        locationRV.setAdapter(locationsAdapter);
        locationRV.setLayoutManager(new GridLayoutManager(mainActivity, 2));
    }

}