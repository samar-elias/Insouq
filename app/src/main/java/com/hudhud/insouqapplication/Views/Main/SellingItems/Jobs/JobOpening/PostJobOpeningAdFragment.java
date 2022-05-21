package com.hudhud.insouqapplication.Views.Main.SellingItems.Jobs.JobOpening;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.SellingItems.Classifieds.PostClassifiedsGroup1AdFragmentDirections;
import com.hudhud.insouqapplication.Views.map.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;

public class PostJobOpeningAdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText adTitleEdt, companyNameEdt, descriptionEdt, phoneNumberEdt, otherJobTypeEdt;
    Spinner jobTypesSpinner, locationsSpinner, careerLevelSpinner, employmentTypeSpinner, workExperienceSpinner, educationLevelSpinner;
    TextView location;
    CheckBox agreementCheckBox;
    String currentJobType = "", currentLocation = "", currentCareerLevel = "", currentEmploymentType = "", currentEducationLevel = "", currentWorkExperience = ""
            , latitude = "", longitude = "", address = "";
    ArrayList<String> jobTypeArTitles, jobTypeEnTitles, locationArTitles, locationEnTitles, careerLevelArTitles, careerLevelEnTitles, employmentTypeArTitles, employmentTypeEnTitles,
            educationLevelArTitles, educationLevelEnTitles, workExperienceArTitles, workExperienceEnTitles;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;
    String title, companyName, description, phoneNumber, otherJobType;

    //Ad sample
    TextView adTitle, adLocation, adEmployment, adExperience;

    public PostJobOpeningAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_job_opening_ad, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        onSpinnerClick();
        getCareerLevel();
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);
        location = view.findViewById(R.id.location);

        continueBtn = view.findViewById(R.id.continue_btn);
        adTitleEdt = view.findViewById(R.id.job_ad_title);
        descriptionEdt = view.findViewById(R.id.ad_short_description);
        companyNameEdt = view.findViewById(R.id.company_name_edt);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        otherJobTypeEdt = view.findViewById(R.id.other_job_type);

        adTitle = view.findViewById(R.id.job_title);
        adLocation = view.findViewById(R.id.job_company);
        adEmployment = view.findViewById(R.id.job_time);
        adExperience = view.findViewById(R.id.job_experience);

        jobTypesSpinner = view.findViewById(R.id.job_types_spinner);
        locationsSpinner = view.findViewById(R.id.locations_spinner);
        careerLevelSpinner = view.findViewById(R.id.career_level_spinner);
        employmentTypeSpinner = view.findViewById(R.id.employment_types_spinner);
        workExperienceSpinner = view.findViewById(R.id.work_experience_spinner);
        educationLevelSpinner = view.findViewById(R.id.education_level_spinner);
        location = view.findViewById(R.id.location);
        agreementCheckBox = view.findViewById(R.id.agreement_checkbox);

        jobTypeArTitles = new ArrayList<>();
        jobTypeEnTitles = new ArrayList<>();
        locationArTitles = new ArrayList<>();
        locationEnTitles = new ArrayList<>();
        careerLevelArTitles = new ArrayList<>();
        careerLevelEnTitles = new ArrayList<>();
        employmentTypeArTitles = new ArrayList<>();
        employmentTypeEnTitles = new ArrayList<>();
        educationLevelArTitles = new ArrayList<>();
        educationLevelEnTitles = new ArrayList<>();
        workExperienceArTitles = new ArrayList<>();
        workExperienceEnTitles = new ArrayList<>();

        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }
    }

    private void onClick() {
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        location.setOnClickListener(view -> startMapsActivity());

        continueBtn.setOnClickListener(view -> {
             title = String.valueOf(adTitleEdt.getText());
             companyName = String.valueOf(companyNameEdt.getText());
             phoneNumber = String.valueOf(phoneNumberEdt.getText());
             description = String.valueOf(descriptionEdt.getText());
             otherJobType = String.valueOf(otherJobTypeEdt.getText());
            if (title.isEmpty() || companyName.isEmpty() || phoneNumber.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
            } else if (otherJobTypeEdt.getVisibility() == View.VISIBLE && otherJobType.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
            } else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.location_error));
            }else if (!agreementCheckBox.isChecked()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.check_box));
            }else {
//                addJobAd();
                packagesPopUp();
            }
        });

        adTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adTitle.setText(adTitleEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        workExperienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentWorkExperience = "-1";
                    adExperience.setText(mainActivity.getResources().getString(R.string.work_experience));
                }else {
                    currentWorkExperience = workExperienceEnTitles.get(i)+"-"+workExperienceArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adExperience.setText(workExperienceArTitles.get(i));
                    }else {
                        adExperience.setText(workExperienceEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jobTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i == jobTypeEnTitles.size()-1){
                    currentJobType = "";
                    otherJobTypeEdt.setVisibility(View.VISIBLE);
                }else {
                    currentJobType = jobTypeEnTitles.get(i)+"-"+jobTypeArTitles.get(i);
                    otherJobTypeEdt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentLocation = "-1";
                    adLocation.setText(mainActivity.getResources().getString(R.string.location));
                }else {
                    currentLocation = locationEnTitles.get(i)+"-"+locationArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adLocation.setText(locationArTitles.get(i));
                    }else {
                        adLocation.setText(locationEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        educationLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentEducationLevel = "-1";
                }else {
                    currentEducationLevel = educationLevelEnTitles.get(i)+"-"+educationLevelArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        careerLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                currentCareerLevel = careerLevelEnTitles.get(i)+"-"+careerLevelArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        employmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentEmploymentType = "-1";
                    adEmployment.setText(mainActivity.getResources().getString(R.string.employment_type));
                }else {
                    currentEmploymentType = employmentTypeEnTitles.get(i)+"-"+employmentTypeArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adEmployment.setText(employmentTypeArTitles.get(i));
                    }else {
                        adEmployment.setText(employmentTypeEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCareerLevel(){
        careerLevelArTitles.clear();
        careerLevelEnTitles.clear();
        careerLevelEnTitles.add(mainActivity.getResources().getString(R.string.select_career_level));
        careerLevelArTitles.add(mainActivity.getResources().getString(R.string.select_career_level));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest professionalLevelRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllCareerLevel", response -> {
            try {
                JSONArray professionalLevelArray = new JSONArray(response);
                for (int i=0; i<professionalLevelArray.length(); i++){
                    JSONObject professionalLevelObj = professionalLevelArray.getJSONObject(i);
                    careerLevelEnTitles.add(professionalLevelObj.getString("en_Text"));
                    careerLevelArTitles.add(professionalLevelObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(careerLevelSpinner, careerLevelArTitles);
                }else {
                    setSpinner(careerLevelSpinner, careerLevelEnTitles);
                }
                currentCareerLevel = careerLevelEnTitles.get(0)+"-"+careerLevelArTitles.get(0);
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
        employmentTypeEnTitles.add(mainActivity.getResources().getString(R.string.select_employment_type));
        employmentTypeArTitles.add(mainActivity.getResources().getString(R.string.select_employment_type));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest commitmentRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllEmploymentType", response -> {
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
                currentEmploymentType = employmentTypeEnTitles.get(0)+"-"+employmentTypeArTitles.get(0);
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

    private void getWorkExperienceLevel(){
        workExperienceEnTitles.clear();
        workExperienceArTitles.clear();
        workExperienceEnTitles.add(mainActivity.getResources().getString(R.string.select_work_experience));
        workExperienceArTitles.add(mainActivity.getResources().getString(R.string.select_work_experience));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest workExperienceRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllWorkExperience", response -> {
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
                currentWorkExperience = workExperienceEnTitles.get(0)+"-"+workExperienceArTitles.get(0);
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
        educationLevelEnTitles.add(mainActivity.getResources().getString(R.string.select_education_level));
        educationLevelArTitles.add(mainActivity.getResources().getString(R.string.select_education_level));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest educationLevelRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllEducationLevel", response -> {
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
                currentEducationLevel = educationLevelEnTitles.get(0)+"-"+educationLevelArTitles.get(0);
                getLocation();
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

    private void getJobTypes(){
        jobTypeEnTitles.clear();
        jobTypeArTitles.clear();
        jobTypeEnTitles.add(mainActivity.getResources().getString(R.string.select_job_type));
        jobTypeArTitles.add(mainActivity.getResources().getString(R.string.select_job_type));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest jobTypesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllJobType", response -> {
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
                    setSpinner(jobTypesSpinner, jobTypeArTitles);
                }else {
                    setSpinner(jobTypesSpinner, jobTypeEnTitles);
                }
                currentJobType = jobTypeEnTitles.get(0)+"-"+jobTypeArTitles.get(0);
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

    private void getLocation(){
        locationArTitles.clear();
        locationEnTitles.clear();
        locationArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    locationArTitles.add(locationObj.getString("ar_Text"));
                    locationEnTitles.add(locationObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(locationsSpinner, locationArTitles);
                }else {
                    setSpinner(locationsSpinner, locationEnTitles);
                }
                currentLocation = locationArTitles.get(0)+"-"+locationEnTitles.get(0);
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

    private void addJobAd(){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.JobAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        Call<AddAdsResponse> addJobOpeningAd = retrofit.create(RetrofitUrls.class).addOpeningJob(title, description, currentLocation, latitude, longitude, "3", currentJobType, otherJobType, phoneNumber, currentCareerLevel, currentEmploymentType, currentWorkExperience, currentEducationLevel, companyName);
        addJobOpeningAd.enqueue(new Callback<AddAdsResponse>() {
            @Override
            public void onResponse(Call<AddAdsResponse> call, Response<AddAdsResponse> response) {
                mainActivity.hideProgressDialog();
                Timber.tag("response").d(response.message());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                        startActivity("");
                        if (packageId.isEmpty()){
                            startActivity("");
                        }else {
                            navController.navigate(PostJobOpeningAdFragmentDirections.actionPostJobOpeningAdFragmentToPaymentFragment(packageId, ""));
                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.ad_posted_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });


//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
//        StringRequest jobAdRequest = new StringRequest(Request.Method.POST, Urls.JobAds_URL+"AddJobOpening", response -> {
//            mainActivity.hideProgressDialog();
//            try {
//                JSONObject jobObj = new JSONObject(response);
//                Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                startActivity("");
//            } catch (JSONException e) {
//                e.printStackTrace();
//                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.error_occured));
//            }
//        }, error -> {
//            mainActivity.hideProgressDialog();
//            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.internet_connection_error));
//        }){
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
//                return params;
//            }
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("title", title);
//                params.put("description", description);
//                params.put("location", currentLocation);
//                params.put("lat", latitude);
//                params.put("lng", longitude);
//                params.put("categoryId", "3");
//                params.put("jobType", currentJobType);
//                params.put("otherJobType", jobType);
//                params.put("phoneNumber", phoneNumber);
//                params.put("careerLevel", currentCareerLevel);
//                params.put("employmentType", currentEmploymentType);
//                params.put("minWorkExperience", currentWorkExperience);
//                params.put("minEducationLevel", currentEducationLevel);
//                params.put("companyName", companyName);
//                return params;
//            }
//        };
//        mainActivity.queue.add(jobAdRequest);
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    private void startMapsActivity(){
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MAPS_ACTIVITY);
    }

    public void packagesPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.packages_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.next);
        TextView close = myAdsOptionsAlertView.findViewById(R.id.close);
        RecyclerView packagesRV = myAdsOptionsAlertView.findViewById(R.id.packages_RV);
        ConstraintLayout freeLayout = myAdsOptionsAlertView.findViewById(R.id.free_layout);
        freeCB = myAdsOptionsAlertView.findViewById(R.id.free_CB);

        freeCB.setChecked(true);
        freeLayout.setOnClickListener(v -> freeCB.setChecked(true));
        freeCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                setPackagesAdapter(packagesRV);
            }
        });
        getPackages(packagesRV);
        close.setOnClickListener(v -> myAdsOptionsAlertBuilder.dismiss());
        done.setOnClickListener(v -> {
            addJobAd();
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void getPackages(RecyclerView recyclerView){
        packages.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest packagesRequest = new StringRequest(Request.Method.GET, Urls.Payment_URL+"GetPackages", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray packagesArray = new JSONArray(response);
                for (int i=1; i<packagesArray.length(); i++){
                    JSONObject packageObj = packagesArray.getJSONObject(i);
                    PackageFS packageFS = new PackageFS();
                    packageFS.setId(packageObj.getString("id"));
                    packageFS.setDays(packageObj.getString("numberOfDays"));
                    packageFS.setPrice(packageObj.getString("price"));
                    packages.add(packageFS);
                }
                setPackagesAdapter(recyclerView);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(packagesRequest);

    }

    private void setPackagesAdapter(RecyclerView recyclerView){
        PackagesAdapter businessPackagesAdapter = new PackagesAdapter(this,packages, -1);
        recyclerView.setAdapter(businessPackagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAPS_ACTIVITY && resultCode == RESULT_OK){
            assert data != null;
            address = data.getStringExtra("address");
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
            Send.electronicsAd.setLat(latitude);
            Send.electronicsAd.setLan(longitude);
            location.setText(address);
        }
    }
}