package com.hudhud.insouqapplication.Views.Main.Profile.MyAds;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.Picture;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UserFS;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.Profile.MyAds.Business.MyBusinessAdsAdapter;
import com.hudhud.insouqapplication.Views.Splash.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdsFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    RecyclerView myadsRV;
    MainActivity mainActivity;
    ArrayList<UsedCarAd> usedCars = new ArrayList<>();
    ArrayList<JobAd> jobAds = new ArrayList<>();
    ArrayList<ServiceAd> serviceAds = new ArrayList<>();
    ArrayList<BusinessAd> businessAds = new ArrayList<>();
    ArrayList<NumberAd> numberAds = new ArrayList<>();
    ArrayList<ElectronicAd> electronicAds = new ArrayList<>();
    ArrayList<ElectronicAd> classifiedsAds = new ArrayList<>();
    TextView noAds;
    public BusinessAd businessAd = new BusinessAd();
    ConstraintLayout motors, jobs, services, business, classifieds, numbers, electronics;
    CircleImageView motorsBackground, jobsBackground, servicesBackground, businessBackground, classifiedsBackground, numbersBackground, electronicsBackground;

    public MyAdsFragment() {
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
        return inflater.inflate(R.layout.fragment_my_ads, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getUsedCarsAds();
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

        backToPrevious = view.findViewById(R.id.back_arrow);
        myadsRV = view.findViewById(R.id.my_ads_RV);
        noAds = view.findViewById(R.id.no_ads);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        motors = view.findViewById(R.id.motors);
        jobs = view.findViewById(R.id.jobs);
        services = view.findViewById(R.id.services);
        business = view.findViewById(R.id.business);
        classifieds = view.findViewById(R.id.classifieds);
        numbers = view.findViewById(R.id.numbers);
        electronics = view.findViewById(R.id.electronics);

        motorsBackground = view.findViewById(R.id.motors_background);
        jobsBackground = view.findViewById(R.id.jobs_background);
        servicesBackground = view.findViewById(R.id.services_background);
        businessBackground = view.findViewById(R.id.business_background);
        classifiedsBackground = view.findViewById(R.id.classifieds_background);
        numbersBackground = view.findViewById(R.id.numbers_background);
        electronicsBackground = view.findViewById(R.id.electronics_background);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        motors.setOnClickListener(view -> {
            getUsedCarsAds();
            Glide.with(mainActivity).load(R.drawable.foshia_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(electronicsBackground);
        });
        jobs.setOnClickListener(view -> {
            getJobAds();
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.peach_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(electronicsBackground);
        });
        services.setOnClickListener(view -> {
            getServicesAds();
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.fairouz_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(electronicsBackground);
        });
        business.setOnClickListener(view -> {
            getBusinessAds();
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.green_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(electronicsBackground);
        });
        classifieds.setOnClickListener(view -> {
            getClassifiedsAds();
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.blue_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(electronicsBackground);
        });
        numbers.setOnClickListener(view -> {
            getNumbersAds();
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.purple_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(electronicsBackground);
        });
        electronics.setOnClickListener(view -> {
            getElectronicsAds();
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.gray_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.yellow_circle).into(electronicsBackground);
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void showAdOptions(String typeId, String adId){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.my_ad_options_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout statistics = myAdsOptionsAlertView.findViewById(R.id.statistics);
        LinearLayout deleteAd = myAdsOptionsAlertView.findViewById(R.id.delete_ad);
        LinearLayout editAd = myAdsOptionsAlertView.findViewById(R.id.edit_ad);
        statistics.setOnClickListener(view -> {
            navController.navigate(MyAdsFragmentDirections.actionMyAdsFragmentToAdStatisticsFragment(adId));
            myAdsOptionsAlertBuilder.dismiss();
        });

        deleteAd.setOnClickListener(view -> {
            showDeleteMessage(adId);
            myAdsOptionsAlertBuilder.dismiss();
        });
        editAd.setOnClickListener(view -> {
            switch (typeId){
                case "5":
                    navigateToEditBusinessAd(businessAd);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });

    }

    private void showDeleteMessage(String adId){
        AlertDialog.Builder msgDialog = new AlertDialog.Builder(mainActivity);
        msgDialog.setTitle(this.getResources().getString(R.string.delete_ad));
        msgDialog.setMessage(mainActivity.getResources().getString(R.string.delete_message));

        msgDialog.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
            deleteAd(adId);
        });

        msgDialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        msgDialog.show();
    }

    private void deleteAd(String adId){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest deleteAd = new StringRequest(Request.Method.POST, Urls.Ads_URL+"DeleteAd", response -> {
            mainActivity.hideProgressDialog();
            Toast.makeText(mainActivity, "Post deleted successfully!", Toast.LENGTH_SHORT).show();
            navController.popBackStack();
        }, error -> {
            mainActivity.hideProgressDialog();
            Toast.makeText(mainActivity, "Post deleted successfully!", Toast.LENGTH_SHORT).show();
            navController.popBackStack();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("adId", adId);
                return params;
            }
        };
        mainActivity.queue.add(deleteAd);
    }

    public void setReportAd(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.delete_ad_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.continue_btn);
        done.setOnClickListener(view -> {
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    public void navigateToMoreViews(String categoryId, String adId){
        navController.navigate(MyAdsFragmentDirections.actionMyAdsFragmentToMoreViewsFragment(categoryId, adId));
    }

    public void navigateToEditAd(){
        navController.navigate(MyAdsFragmentDirections.actionMyAdsFragmentToEditAdFragment());
    }

    private void getUsedCarsAds(){
        usedCars.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest getAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=1", response -> {
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
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.internet_connection_error));
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
        MyMotorsAdsAdapter myMotorsAdsAdapter = new MyMotorsAdsAdapter(this, usedCars);
        myadsRV.setAdapter(myMotorsAdsAdapter);
        myadsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        if (usedCars.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getJobAds(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        jobAds.clear();
        StringRequest jobAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=3", response -> {
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
                    jobAd.setStatus(jobObj.getString("status"));
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
                setWantedJobsAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.internet_connection_error));
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
        MyJobsAdsAdapter myJobsAdsAdapter = new MyJobsAdsAdapter(this, jobAds);
        myadsRV.setAdapter(myJobsAdsAdapter);
        myadsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (jobAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getServicesAds(){
        serviceAds.clear();
        StringRequest serviceAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=4", response -> {
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
                    serviceAd.setStatus(serviceObj.getString("status"));
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
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.error_occured));

            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.internet_connection_error));

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
        MyServicesAdsAdapter myJobsAdsAdapter = new MyServicesAdsAdapter(this, serviceAds);
        myadsRV.setAdapter(myJobsAdsAdapter);
        myadsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (serviceAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getBusinessAds(){
        businessAds.clear();
        StringRequest businessAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=5", response -> {
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
                    businessAd.setStatus(businessObj.getString("status"));
                    businessAd.setCategoryId(businessObj.getString("categoryId"));
                    businessAd.setCategoryArName(businessObj.getString("categoryArName"));
                    businessAd.setCategoryEnName(businessObj.getString("categoryEnName"));
                    businessAd.setOtherCategoryNAme(businessObj.getString("otherCategoryName"));
                    businessAd.setSubCategoryId(businessObj.getString("subCategoryId"));
                    businessAd.setSubCategoryArName(businessObj.getString("subCategoryArName"));
                    businessAd.setSubCategoryEnName(businessObj.getString("subCategoryEnName"));
                    businessAd.setOtherSubCategoryNAme(businessObj.getString("otherSubCategoryName"));
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
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.internet_connection_error));
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
        MyBusinessAdsAdapter myJobsAdsAdapter = new MyBusinessAdsAdapter(this, businessAds);
        myadsRV.setAdapter(myJobsAdsAdapter);
        myadsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (businessAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getNumbersAds(){
        numberAds.clear();
        StringRequest numberAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=7", response -> {
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
                    numberAd.setStatus(numberObj.getString("status"));
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
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number), mainActivity.getResources().getString(R.string.internet_connection_error))){
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
        MyNumbersAdsAdapter myJobsAdsAdapter = new MyNumbersAdsAdapter(this, numberAds);
        myadsRV.setAdapter(myJobsAdsAdapter);
        myadsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (numberAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    private void getElectronicsAds(){
        electronicAds.clear();
        StringRequest electronicAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=8", response -> {
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
                    electronicAd.setStatus(electronicObj.getString("status"));
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
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number), mainActivity.getResources().getString(R.string.internet_connection_error))){
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
        StringRequest electronicAdsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetMyAds?typeId=6", response -> {
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
                    electronicAd.setStatus(electronicObj.getString("status"));
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
                setElectronicAdsAdapter(classifiedsAds);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.ads), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.ads), mainActivity.getResources().getString(R.string.internet_connection_error))){
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

    private void setElectronicAdsAdapter(ArrayList<ElectronicAd> ads){
        MyClassifiedsAdsAdapter myJobsAdsAdapter = new MyClassifiedsAdsAdapter(this, ads);
        myadsRV.setAdapter(myJobsAdsAdapter);
        myadsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (ads.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    public void navigateToEditBusinessAd(BusinessAd businessAd){
        navController.navigate(MyAdsFragmentDirections.actionMyAdsFragmentToEditBusinessAdFragment(businessAd));
    }
}