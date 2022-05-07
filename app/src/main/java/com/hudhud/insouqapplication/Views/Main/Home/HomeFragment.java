package com.hudhud.insouqapplication.Views.Main.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NewAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NewNumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.Picture;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.AppUtils.Responses.SubCategory;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.BusinessProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ClassifiedsProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ElectronicsProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.JobsProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.MotorProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.NumbersAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ServiceProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.SubCategoriesAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragmentDirections;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ConstraintLayout motors, property, jobs, services, business, classifieds, numbers, electronics;
    RecyclerView mightLikeRV, motorsRV, propertiesRV, jobsRV, servicesRV, businessesRV, classifiedsRV, numbersRV, electronicsRV;
    ImageView profile, chat, notifications, sellAnItem;
    ScrollView scrollView;
    AlertDialog subCategoriesAlertBuilder;
    MainActivity mainActivity;
    NavController navController;
    ImageView motorIcon, jobIcon, serviceIcon, businessIcon, classifiedIcon, numberIcon, electronicIcon;
    TextView motorTitle, jobTitle, serviceTitle, businessTitle, classifiedTitle, numberTitle, electronicTitle;
    TextView viewMotors, viewProperties, viewJobs, viewServices, viewBusiness, viewClassifieds, viewNumbers, viewElectronics;
    ArrayList<SubCategory> subCategories;
    ArrayList<NewAd> newMotorAds, newJobAds, newServiceAds, newBusinessAds, newClassifiedAds, newElectronicAds;
    ArrayList<NewNumberAd> newNumberAds;

    // home

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        setNewAds();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);

        profile = view.findViewById(R.id.profile);
        chat = view.findViewById(R.id.chat);
        notifications = view.findViewById(R.id.notification);
        sellAnItem = view.findViewById(R.id.sell_item);

        switch (mainActivity.fragName) {
            case "profile":
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment());
                break;
            case "chat":
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment());
                break;
            case "notifications":
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToNotificationsFragment());
                break;
            case "sellItem":
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToSellAnItemFragment());
                break;
        }

        motors = view.findViewById(R.id.motors);
        property = view.findViewById(R.id.property);
        jobs = view.findViewById(R.id.jobs);
        services = view.findViewById(R.id.services);
        business = view.findViewById(R.id.business);
        classifieds = view.findViewById(R.id.classifieds);
        numbers = view.findViewById(R.id.numbers);
        electronics = view.findViewById(R.id.electronics);

        mightLikeRV = view.findViewById(R.id.ads_RV);
        motorsRV = view.findViewById(R.id.new_motors_RV);
        propertiesRV = view.findViewById(R.id.new_property_RV);
        jobsRV = view.findViewById(R.id.new_jobs_RV);
        servicesRV = view.findViewById(R.id.new_services_RV);
        businessesRV = view.findViewById(R.id.new_business_RV);
        classifiedsRV = view.findViewById(R.id.new_classifieds_RV);
        numbersRV = view.findViewById(R.id.new_number_RV);
        electronicsRV = view.findViewById(R.id.new_electronics_RV);

        scrollView = view.findViewById(R.id.scroll_view);

        viewMotors = view.findViewById(R.id.view_all_motors);
        viewProperties = view.findViewById(R.id.view_all_property);
        viewJobs = view.findViewById(R.id.view_all_jobs);
        viewServices = view.findViewById(R.id.view_all_services);
        viewBusiness = view.findViewById(R.id.view_all_business);
        viewClassifieds = view.findViewById(R.id.view_all_classifieds);
        viewNumbers = view.findViewById(R.id.view_all_numbers);
        viewElectronics = view.findViewById(R.id.view_all_electronics);

        motorIcon = view.findViewById(R.id.motors_new_icon);
        jobIcon = view.findViewById(R.id.jobs_new_icon);
        serviceIcon = view.findViewById(R.id.services_new_icon);
        businessIcon = view.findViewById(R.id.business_new_icon);
        classifiedIcon = view.findViewById(R.id.classifieds_new_icon);
        numberIcon = view.findViewById(R.id.numbers_new_icon);
        electronicIcon = view.findViewById(R.id.electronics_new_icon);

        motorTitle = view.findViewById(R.id.motors_new_title);
        jobTitle = view.findViewById(R.id.jobs_new_title);
        serviceTitle = view.findViewById(R.id.services_new_title);
        businessTitle = view.findViewById(R.id.business_new_title);
        classifiedTitle = view.findViewById(R.id.classifieds_new_title);
        numberTitle = view.findViewById(R.id.numbers_new_title);
        electronicTitle = view.findViewById(R.id.electronics_new_title);

        subCategories = new ArrayList<>();
        newMotorAds = new ArrayList<>();
        newJobAds = new ArrayList<>();
        newServiceAds = new ArrayList<>();
        newBusinessAds = new ArrayList<>();
        newClassifiedAds = new ArrayList<>();
        newNumberAds = new ArrayList<>();
        newElectronicAds = new ArrayList<>();
    }

    private void onClick(){
        motors.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.foshia), getResources().getString(R.string.motors), getResources().getDrawable(R.drawable.motors_icon), 1));
        property.setOnClickListener(view -> mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.property), mainActivity.getResources().getString(R.string.coming_soon)));
//        property.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.orange), getResources().getString(R.string.property), getResources().getDrawable(R.drawable.property_icon), 2));
        jobs.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.peach), getResources().getString(R.string.jobs), getResources().getDrawable(R.drawable.jobs_icon), 3));
        services.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.fairouz), getResources().getString(R.string.services), getResources().getDrawable(R.drawable.services_icon), 4));
        business.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.green), getResources().getString(R.string.business), getResources().getDrawable(R.drawable.business_icon),5));
        classifieds.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.blue), getResources().getString(R.string.classifieds), getResources().getDrawable(R.drawable.classifieds_icon), 6));
        numbers.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.light_purple_text), getResources().getString(R.string.numbers), getResources().getDrawable(R.drawable.numbers_icon), 7));
        electronics.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.yellow), getResources().getString(R.string.electronics), getResources().getDrawable(R.drawable.electronics_icon), 8));

        notifications.setOnClickListener(view -> navController.navigate(HomeFragmentDirections.actionHomeFragmentToNotificationsFragment()));

        profile.setOnClickListener(view -> navController.navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment()));
        chat.setOnClickListener(view -> navController.navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment()));
        notifications.setOnClickListener(view -> navController.navigate(HomeFragmentDirections.actionHomeFragmentToNotificationsFragment()));
        sellAnItem.setOnClickListener(view -> navController.navigate(HomeFragmentDirections.actionHomeFragmentToSellAnItemFragment()));

        viewMotors.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.foshia), getResources().getString(R.string.motors), getResources().getDrawable(R.drawable.motors_icon), 1));
        viewProperties.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.orange), getResources().getString(R.string.property), getResources().getDrawable(R.drawable.property_icon), 2));
        viewJobs.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.peach), getResources().getString(R.string.jobs), getResources().getDrawable(R.drawable.jobs_icon), 3));
        viewServices.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.fairouz), getResources().getString(R.string.services), getResources().getDrawable(R.drawable.services_icon), 4));
        viewBusiness.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.green), getResources().getString(R.string.business), getResources().getDrawable(R.drawable.business_icon),5));
        viewClassifieds.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.blue), getResources().getString(R.string.classifieds), getResources().getDrawable(R.drawable.classifieds_icon), 6));
        viewNumbers.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.light_purple_text), getResources().getString(R.string.numbers), getResources().getDrawable(R.drawable.numbers_icon), 7));
        viewElectronics.setOnClickListener(view -> showSubCategoriesPopUp(getResources().getColor(R.color.yellow), getResources().getString(R.string.electronics), getResources().getDrawable(R.drawable.electronics_icon), 8));
    }

    private void showSubCategoriesPopUp(int color, String title, Drawable icon, int id){
        View subCategoriesAlertView = LayoutInflater.from(getContext()).inflate(R.layout.sub_categories_pop_up, null);
        subCategoriesAlertBuilder = new AlertDialog.Builder(getContext()).setView(subCategoriesAlertView).show();
        subCategoriesAlertBuilder.show();
        subCategoriesAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ConstraintLayout header = subCategoriesAlertView.findViewById(R.id.header);
        ImageView closeImage = subCategoriesAlertView.findViewById(R.id.close_icon);
        TextView headerTitle = subCategoriesAlertBuilder.findViewById(R.id.pop_up_title);
        ImageView image = subCategoriesAlertBuilder.findViewById( R.id.icon);
        RecyclerView subCategoriesRV = subCategoriesAlertBuilder.findViewById(R.id.sub_categories_RV);
        getSubCategories(id, subCategoriesRV, title);

        header.setBackgroundColor(color);
        headerTitle.setText(title);
        Glide.with(mainActivity).load(icon).into(image);
        closeImage.setOnClickListener(view -> subCategoriesAlertBuilder.dismiss());

    }

    private void getSubCategories(int id, RecyclerView subCategoriesRV, String categoryName){
        subCategories.clear();
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        StringRequest getSubCategories = new StringRequest(Request.Method.GET, Urls.Categories_URL +"GetByTypeId?id="+id, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObject = subCategoriesArray.getJSONObject(i);
                    SubCategory subCategory = new SubCategory();
                    subCategory.setId(subCategoryObject.getInt("id"));
                    subCategory.setNameAr(subCategoryObject.getString("ar_Name"));
                    subCategory.setNameEn(subCategoryObject.getString("en_Name"));
                    subCategory.setNumberOfAds(subCategoryObject.getInt("numberOfAds"));
                    subCategory.setIcon(subCategoryObject.getString("firstImage"));
                    subCategory.setStatus(subCategoryObject.getInt("status"));
                    subCategory.setTypeId(subCategoryObject.getInt("typeId"));
                    subCategories.add(subCategory);
                }
                setSubCategoriesAdapter(id,subCategoriesRV, categoryName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(getResources().getString(R.string.network_error), getResources().getString(R.string.error_occured));
        });
        mainActivity.queue.add(getSubCategories);
    }

    private void setSubCategoriesAdapter(int id, RecyclerView subCategoriesRV, String categoryName){
        SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(this, categoryName, id, subCategories);
        subCategoriesRV.setAdapter(subCategoriesAdapter);
        subCategoriesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mainActivity.hideProgressDialog();
    }

    public void dismissDialog(){
        subCategoriesAlertBuilder.dismiss();
    }

    public void navigateToSubCategory(String categoryName, int position, String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToSubCategoryFragment2(categoryName, position, categoryId));
        dismissDialog();
    }

    public void navigateToSubCategory(String categoryName){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToSubCategoryFragment2(categoryName, 0, ""));
    }

    public void navigateToMotors(){
//        navController.navigate(HomeFragmentDirections.actionHomeFragmentToMotorDetailsFragment22());
    }

    public void navigateToBrands(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToBrandFragment2(categoryId));
        dismissDialog();
    }

    public void navigateToAges(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToFilter1Fragment(categoryId));
        dismissDialog();
    }

    public void navigateToCategories(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToMachinary1Fragment(categoryId));
        dismissDialog();
    }

    public void navigateToCategories1(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToPart1Fragment(categoryId));
        dismissDialog();
    }

    public void navigateToCategories2(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToBike1Fragment(categoryId));
        dismissDialog();
    }

    public void navigateToJobs(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToCareerLevelFragment(categoryId));
        dismissDialog();
    }

    public void navigateToClassifieds(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToSubCatFragment(categoryId));
        dismissDialog();
    }

    public void navigateToElectronics(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToBrandFragment(categoryId));
        dismissDialog();
    }

    public void navigateToPlateNumbers(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToEmirateFragment(categoryId));
        dismissDialog();
    }

    public void navigateToMobileNumbers(String categoryId){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToOperatorFragment(categoryId));
        dismissDialog();
    }

    private void setAdapters(){
        ProductsAdapter productsAdapter = new ProductsAdapter(this);
        mightLikeRV.setAdapter(productsAdapter);
        mightLikeRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        motorsRV.setAdapter(productsAdapter);
        motorsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        propertiesRV.setAdapter(productsAdapter);
        propertiesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        jobsRV.setAdapter(productsAdapter);
        jobsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        servicesRV.setAdapter(productsAdapter);
        servicesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        businessesRV.setAdapter(productsAdapter);
        businessesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        classifiedsRV.setAdapter(productsAdapter);
        classifiedsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        numbersRV.setAdapter(productsAdapter);
        numbersRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        electronicsRV.setAdapter(productsAdapter);
        electronicsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void navigateToMotorDetails(int position){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToMotorDetailsFragment22(position, true));
    }

    public void navigateToServicesDetails(int position){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToServicesDetailsFragment2(position, true));
    }

    public void navigateToJobOpeningDetails(int position){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToJobDetailsFragment2(position, true));
    }

    public void navigateToNumberDetails(int position){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToNumberDetailsFragment2(position, true));
    }

    public void navigateToBusinessDetails(int position){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToBusinessDetailsFragment2(position, true));
    }

    public void navigateToElectronicsDetails(int position, boolean isElectronics){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToElectronicsDetailsFragment2(position, true, isElectronics));
    }

    public void navigateToJobWantedDetails(int position){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToJobWantedDetailsFragment2(position, true));
    }

    private void setNewAds(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        getMotorsNewAds();
        getJobsNewAds();
        getServicesNewAds();
        getBusinessNewAds();
        getClassifiedsNewAds();
        getNumberAds();
        getElectronicsNewAds();
        mainActivity.hideProgressDialog();
    }

    private void getMotorsNewAds(){
        AppDefs.newMotorAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=1", response -> {
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
                    AppDefs.newMotorAds.add(usedCarAd);
                }
                setMotorsNewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setMotorsNewAdapter(){
        MotorProductsAdapter productsAdapter = new MotorProductsAdapter(AppDefs.newMotorAds, this);
        motorsRV.setAdapter(productsAdapter);
        motorsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newMotorAds.size() == 0){
            motorIcon.setVisibility(View.GONE);
            motorTitle.setVisibility(View.GONE);
            viewMotors.setVisibility(View.GONE);
        }
    }

    private void getJobsNewAds(){
        AppDefs.newJobAds.clear();
        AppDefs.newOpeningJobAds.clear();
        AppDefs.newWantedJobAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=3", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobsArray = new JSONArray(response);
                for (int i=0; i<jobsArray.length(); i++){
                    JSONObject jobObj = jobsArray.getJSONObject(i);
                    JobAd jobAd = new JobAd();
                    jobAd.setId(jobObj.getString("id"));
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
                    AppDefs.newJobAds.add(jobAd);
                    if (jobAd.getCategoryId().equals("3")){
                        AppDefs.newOpeningJobAds.add(jobAd);
                    }else {
                        AppDefs.newWantedJobAds.add(jobAd);
                    }
                }
                setJobsNewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setJobsNewAdapter(){
        JobsProductsAdapter productsAdapter = new JobsProductsAdapter(AppDefs.newJobAds, this);
        jobsRV.setAdapter(productsAdapter);
        jobsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newJobAds.size() == 0){
            jobIcon.setVisibility(View.GONE);
            jobTitle.setVisibility(View.GONE);
            viewJobs.setVisibility(View.GONE);
        }
    }

    private void getServicesNewAds(){
        AppDefs.newServiceAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=4", response -> {
            try {
                JSONArray serviceAdsArray = new JSONArray(response);
                for (int i=0; i<serviceAdsArray.length(); i++){
                    JSONObject serviceObj = serviceAdsArray.getJSONObject(i);
                    ServiceAd serviceAd = new ServiceAd();
                    serviceAd.setId(serviceObj.getString("id"));
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
                    AppDefs.newServiceAds.add(serviceAd);
                }
                setServicesNewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setServicesNewAdapter(){
        ServiceProductsAdapter productsAdapter = new ServiceProductsAdapter(AppDefs.newServiceAds, this);
        servicesRV.setAdapter(productsAdapter);
        servicesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newServiceAds.size() == 0){
            serviceIcon.setVisibility(View.GONE);
            serviceTitle.setVisibility(View.GONE);
            viewServices.setVisibility(View.GONE);
        }
    }

    private void getBusinessNewAds(){
        AppDefs.newBusinessAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=5", response -> {
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
                    AppDefs.newBusinessAds.add(businessAd);
                }
                setBusinessNewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setBusinessNewAdapter(){
        BusinessProductsAdapter productsAdapter = new BusinessProductsAdapter(AppDefs.newBusinessAds, this);
        businessesRV.setAdapter(productsAdapter);
        businessesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newBusinessAds.size() == 0){
            businessIcon.setVisibility(View.GONE);
            businessTitle.setVisibility(View.GONE);
            viewBusiness.setVisibility(View.GONE);
        }
    }

    private void getClassifiedsNewAds(){
        AppDefs.newClassifiedAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=6", response -> {
            try {
                JSONArray electronicsArray = new JSONArray(response);
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("id"));
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
                    AppDefs.newClassifiedAds.add(electronicAd);
                }
                setClassifiedsNewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setClassifiedsNewAdapter(){
        ClassifiedsProductsAdapter productsAdapter = new ClassifiedsProductsAdapter(AppDefs.newClassifiedAds, this);
        classifiedsRV.setAdapter(productsAdapter);
        classifiedsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newClassifiedAds.size() == 0){
            classifiedIcon.setVisibility(View.GONE);
            classifiedTitle.setVisibility(View.GONE);
            viewClassifieds.setVisibility(View.GONE);
        }
    }

    private void getElectronicsNewAds(){
        AppDefs.newElectronicAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=8", response -> {
            try {
                JSONArray electronicsArray = new JSONArray(response);
                for (int i=0; i<electronicsArray.length(); i++){
                    JSONObject electronicObj = electronicsArray.getJSONObject(i);
                    ElectronicAd electronicAd = new ElectronicAd();
                    electronicAd.setId(electronicObj.getString("id"));
                    electronicAd.setTitle(electronicObj.getString("title"));
                    electronicAd.setArLocation(electronicObj.getString("ar_Location"));
                    electronicAd.setEnLocation(electronicObj.getString("en_Location"));
                    electronicAd.setPrice(electronicObj.getString("price"));
                    electronicAd.setEnAge(electronicObj.getString("en_Age"));
                    electronicAd.setArAge(electronicObj.getString("ar_Age"));
                    electronicAd.setEnUsage(electronicObj.getString("en_Usage"));
                    electronicAd.setArUsage(electronicObj.getString("ar_Usage"));
//                    electronicAd.setEnBrand(electronicObj.getString("en_Brand"));
//                    electronicAd.setArBrand(electronicObj.getString("ar_Brand"));
//                    electronicAd.setEnCondition(electronicObj.getString("en_Condition"));
//                    electronicAd.setArCondition(electronicObj.getString("ar_Condition"));
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
                    AppDefs.newElectronicAds.add(electronicAd);
                }
                setElectronicsNewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setElectronicsNewAdapter(){
        ElectronicsProductsAdapter productsAdapter = new ElectronicsProductsAdapter(AppDefs.newElectronicAds, this);
        electronicsRV.setAdapter(productsAdapter);
        electronicsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newElectronicAds.size() == 0){
            electronicIcon.setVisibility(View.GONE);
            electronicTitle.setVisibility(View.GONE);
            viewElectronics.setVisibility(View.GONE);
        }
    }

    private void getNumberAds(){
        AppDefs.newNumberAds.clear();
        StringRequest newAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetLatestAds?typeId=7", response -> {
            try {
                JSONArray numbersArray = new JSONArray(response);
                for (int i=0; i<numbersArray.length(); i++){
                    JSONObject numberObj = numbersArray.getJSONObject(i);
                    NumberAd numberAd = new NumberAd();
                    numberAd.setId(numberObj.getString("id"));
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
                    AppDefs.newNumberAds.add(numberAd);
                }
                setNumbersAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.new_ads), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(newAdsRequest);
    }

    private void setNumbersAdapter(){
        NumbersAdapter numbersAdapter = new NumbersAdapter(AppDefs.newNumberAds, this);
        numbersRV.setAdapter(numbersAdapter);
        numbersRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (AppDefs.newNumberAds.size() == 0){
            numberIcon.setVisibility(View.GONE);
            numberTitle.setVisibility(View.GONE);
            viewNumbers.setVisibility(View.GONE);
        }
    }

}