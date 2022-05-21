package com.hudhud.insouqapplication.Views.Main.SellingItems.Jobs.JobWanted;

import android.Manifest;
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
import com.android.volley.toolbox.JsonObjectRequest;
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

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createMultipartBodyPart;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

public class PostFullJobWantedAdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    private static final int REQUEST_CODE_FILE = 2;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list, uploaded;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    Spinner genderSpinner, nationalitySpinner, currentLocationsSpinner, educationLevelSpinner, workExperienceSpinner, commitmentSpinner, noticePeriodSpinner,
            visaStatusSpinner, professionalLevelSpinner, salarySpinner, locationsSpinner;
    String currentGender = "", currentNationality = "", currentLocation = "", currentEducationLevel = "", currentWorkExperience = "", currentCommitment = "", currentNoticePeriod = "",
            currentVisaStatus = "", currentProfessionalLevel = "", currentSalary = "", locationCurrent = "", latitude = "", longitude = "", address = "", filePath = "";
    TextView location;
    ArrayList<String> gendersArTitles, genderEnTitles, nationalityArTitles, nationalityEnTitles, currentLocationArTitles, currentLocationEnTitles, educationLevelArTitles,
            educationLevelEnTitles, workExperienceArTitles, workExperienceEnTitles, commitmentArTitles, commitmentEnTitles, noticePeriodArTitles, noticePeriodEnTitles,
            visaStatusArTitles, visaStatusEnTitles, professionalLevelArTitles, professionalLevelEnTitles, salaryValues, locationArTitles, locationEnTitles;
    CheckBox agreementCheckBox;
    EditText currentPosition, descriptionEdt;
    ConstraintLayout uploadCV;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;
    boolean spinner1 = false, spinner2 = false, spinner3 = false, spinner4 = false, spinner5 = false, spinner6 = false, spinner7 = false, spinner8 = false, spinner9 = false, spinner10 = false, spinner11 = false;

    //Ad sample
    TextView jobType, adLocation, adEducation, adExperience, adPosition;

    public PostFullJobWantedAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_full_job_wanted_ad, container, false);
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
        getGenders();
        getNationality();
        getCurrentLocation();
        getEducationLevel();
        getWorkExperienceLevel();
        getCommitment();
        getNoticePeriod();
        getVisaStatus();
        getProfessionalLevel();
        getSalary();
        getLocation();
        onSpinnerClick();
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);

        uploaded = view.findViewById(R.id.checked);
        continueBtn = view.findViewById(R.id.continue_btn);
        location = view.findViewById(R.id.location);
        currentPosition = view.findViewById(R.id.current_position);
        agreementCheckBox = view.findViewById(R.id.agreement_checkbox);
        uploadCV = view.findViewById(R.id.add_your_cv);

        genderSpinner = view.findViewById(R.id.gender_spinner);
        nationalitySpinner = view.findViewById(R.id.nationality_spinner);
        currentLocationsSpinner = view.findViewById(R.id.current_location_spinner);
        educationLevelSpinner = view.findViewById(R.id.education_level_spinner);
        workExperienceSpinner = view.findViewById(R.id.work_experience_spinner);
        commitmentSpinner = view.findViewById(R.id.commitment_spinner);
        noticePeriodSpinner = view.findViewById(R.id.notice_period_spinner);
        visaStatusSpinner = view.findViewById(R.id.visa_status_spinner);
        professionalLevelSpinner = view.findViewById(R.id.career_level_spinner);
        salarySpinner = view.findViewById(R.id.expected_monthly_salary_spinner);
        locationsSpinner = view.findViewById(R.id.locations_spinner);
        descriptionEdt = view.findViewById(R.id.ad_short_description);

        jobType = view.findViewById(R.id.job_position);
        adLocation = view.findViewById(R.id.job_type);
        adEducation = view.findViewById(R.id.job_time);
        adExperience = view.findViewById(R.id.job_experience);
        adPosition = view.findViewById(R.id.value);

        genderEnTitles = new ArrayList<>();
        gendersArTitles = new ArrayList<>();
        nationalityArTitles = new ArrayList<>();
        nationalityEnTitles = new ArrayList<>();
        currentLocationArTitles = new ArrayList<>();
        currentLocationEnTitles = new ArrayList<>();
        educationLevelArTitles = new ArrayList<>();
        educationLevelEnTitles = new ArrayList<>();
        workExperienceArTitles = new ArrayList<>();
        workExperienceEnTitles = new ArrayList<>();
        commitmentArTitles = new ArrayList<>();
        commitmentEnTitles = new ArrayList<>();
        noticePeriodArTitles = new ArrayList<>();
        noticePeriodEnTitles = new ArrayList<>();
        visaStatusArTitles = new ArrayList<>();
        visaStatusEnTitles = new ArrayList<>();
        professionalLevelArTitles = new ArrayList<>();
        professionalLevelEnTitles = new ArrayList<>();
        salaryValues = new ArrayList<>();
        locationArTitles = new ArrayList<>();
        locationEnTitles = new ArrayList<>();

        if (!filePath.isEmpty()){
            uploaded.setVisibility(View.VISIBLE);
        }else {
            uploaded.setVisibility(View.GONE);
        }

        jobType.setText(Send.jobType);
    }

    private void onClick() {
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        location.setOnClickListener(view -> startMapsActivity());

        uploadCV.setOnClickListener(view -> uploadFile());

        currentPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adPosition.setText(currentPosition.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        continueBtn.setOnClickListener(view -> {
            String currentPos = String.valueOf(currentPosition.getText());
            String description = String.valueOf(descriptionEdt.getText());
            if (currentPos.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (filePath.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.upload_file));
            }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.location_error));
            }else if (!agreementCheckBox.isChecked()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.check_box));
            }else if (currentCommitment.equals("-1") || currentLocation.equals("-1")|| currentGender.equals("-1") || currentEducationLevel.equals("-1") || currentNationality.equals("-1") || currentNoticePeriod.equals("-1")
                    || currentProfessionalLevel.equals("-1") || locationCurrent.equals("-1") || currentSalary.equals("-1") || currentVisaStatus.equals("-1") || currentWorkExperience.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                setData(description, currentPos);
            }
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner1 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentGender = "-1";
                }else {
                    currentGender = genderEnTitles.get(i)+"-"+gendersArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner2 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentNationality = "-1";
                }else {
                    currentNationality = nationalityEnTitles.get(i)+"-"+nationalityArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        currentLocationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner3 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentLocation = "-1";
                }else {
                    currentLocation = currentLocationEnTitles.get(i)+"-"+currentLocationArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workExperienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner4 = true;
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

        commitmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner5 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentCommitment = "-1";
                }else {
                    currentCommitment = commitmentEnTitles.get(i)+"-"+commitmentArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        noticePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner6 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentNoticePeriod = "-1";
                }else {
                    currentNoticePeriod = noticePeriodEnTitles.get(i)+"-"+noticePeriodArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        visaStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner7 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentVisaStatus = "-1";
                }else {
                    currentVisaStatus = visaStatusEnTitles.get(i)+"-"+visaStatusArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        professionalLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner8 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentProfessionalLevel = "-1";
                }else {
                    currentProfessionalLevel = professionalLevelEnTitles.get(i)+"-"+professionalLevelArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        salarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner9 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentSalary = "-1";
                }else {
                    currentSalary = salaryValues.get(i);
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
                    spinner10 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    locationCurrent = "-1";
                    adLocation.setText(mainActivity.getResources().getString(R.string.location));
                }else {
                    locationCurrent = locationEnTitles.get(i)+"-"+locationArTitles.get(i);
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
                    spinner11 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentEducationLevel = "-1";
                    adEducation.setText(mainActivity.getResources().getString(R.string.education_level));
                }else {
                    currentEducationLevel = educationLevelEnTitles.get(i)+"-"+educationLevelArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adEducation.setText(educationLevelArTitles.get(i));
                    }else {
                        adEducation.setText(educationLevelEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getGenders(){
        gendersArTitles.add(mainActivity.getResources().getString(R.string.select_gender));
        genderEnTitles.add(mainActivity.getResources().getString(R.string.select_gender));
        gendersArTitles.add("ذكر");
        gendersArTitles.add("أنثى");
        genderEnTitles.add("Male");
        genderEnTitles.add("Female");
        if (AppDefs.language.equals("ar")){
            setSpinner(genderSpinner, gendersArTitles);
        }else {
            setSpinner(genderSpinner, genderEnTitles);
        }
        currentGender = genderEnTitles.get(0)+"-"+gendersArTitles.get(0);
    }

    private void getNationality(){
        nationalityArTitles.clear();
        nationalityEnTitles.clear();
        nationalityArTitles.add(mainActivity.getResources().getString(R.string.select_nationality));
        nationalityEnTitles.add(mainActivity.getResources().getString(R.string.select_nationality));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest nationalityRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllNationality", response -> {
            try {
                JSONArray nationalityArray = new JSONArray(response);
                for (int i=0; i<nationalityArray.length(); i++){
                    JSONObject nationalityObj = nationalityArray.getJSONObject(i);
                    nationalityArTitles.add(nationalityObj.getString("ar_Text"));
                    nationalityEnTitles.add(nationalityObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(nationalitySpinner, nationalityArTitles);
                }else {
                    setSpinner(nationalitySpinner, nationalityEnTitles);
                }
                currentNationality = nationalityEnTitles.get(0)+"-"+nationalityArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.nationality), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
           mainActivity.hideProgressDialog();
           mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.nationality), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(nationalityRequest);
    }

    private void getCurrentLocation(){
        currentLocationArTitles.clear();
        currentLocationEnTitles.clear();
        currentLocationArTitles.add(mainActivity.getResources().getString(R.string.select_current_location));
        currentLocationEnTitles.add(mainActivity.getResources().getString(R.string.select_current_location));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    currentLocationArTitles.add(locationObj.getString("ar_Text"));
                    currentLocationEnTitles.add(locationObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(currentLocationsSpinner, currentLocationArTitles);
                }else {
                    setSpinner(currentLocationsSpinner, currentLocationEnTitles);
                }
                currentLocation = currentLocationArTitles.get(0)+"-"+currentLocationEnTitles.get(0);
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

    private void getEducationLevel(){
        educationLevelArTitles.clear();
        educationLevelEnTitles.clear();
        educationLevelArTitles.add(mainActivity.getResources().getString(R.string.select_education_level));
        educationLevelEnTitles.add(mainActivity.getResources().getString(R.string.select_education_level));
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

    private void getCommitment(){
        commitmentArTitles.clear();
        commitmentEnTitles.clear();
        commitmentArTitles.add(mainActivity.getResources().getString(R.string.select_commitment));
        commitmentEnTitles.add(mainActivity.getResources().getString(R.string.select_commitment));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest commitmentRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllCommitment", response -> {
            try {
                JSONArray commitmentArray = new JSONArray(response);
                for (int i=0; i<commitmentArray.length(); i++){
                    JSONObject commitmentObj = commitmentArray.getJSONObject(i);
                    commitmentEnTitles.add(commitmentObj.getString("en_Text"));
                    commitmentArTitles.add(commitmentObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(commitmentSpinner, commitmentArTitles);
                }else {
                    setSpinner(commitmentSpinner, commitmentEnTitles);
                }
                currentCommitment = commitmentEnTitles.get(0)+"-"+commitmentArTitles.get(0);
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

    private void getNoticePeriod(){
        noticePeriodEnTitles.clear();
        noticePeriodArTitles.clear();
        noticePeriodEnTitles.add(mainActivity.getResources().getString(R.string.select_notice_period));
        noticePeriodArTitles.add(mainActivity.getResources().getString(R.string.select_notice_period));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest noticePeriodRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllNoticePeriod", response -> {
            try {
                JSONArray noticePeriodArray = new JSONArray(response);
                for (int i=0; i<noticePeriodArray.length(); i++){
                    JSONObject noticePeriodObj = noticePeriodArray.getJSONObject(i);
                    noticePeriodEnTitles.add(noticePeriodObj.getString("en_Text"));
                    noticePeriodArTitles.add(noticePeriodObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(noticePeriodSpinner, noticePeriodArTitles);
                }else {
                    setSpinner(noticePeriodSpinner, noticePeriodEnTitles);
                }
                currentNoticePeriod = noticePeriodEnTitles.get(0)+"-"+noticePeriodArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.notice_period), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.notice_period), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(noticePeriodRequest);
    }

    private void getVisaStatus(){
        visaStatusArTitles.clear();
        visaStatusEnTitles.clear();
        visaStatusArTitles.add(mainActivity.getResources().getString(R.string.select_visa_status));
        visaStatusEnTitles.add(mainActivity.getResources().getString(R.string.select_visa_status));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest visaStatusRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllVisaStatus", response -> {
            try {
                JSONArray visaStatusArray = new JSONArray(response);
                for (int i=0; i<visaStatusArray.length(); i++){
                    JSONObject visaStatusObj = visaStatusArray.getJSONObject(i);
                    visaStatusEnTitles.add(visaStatusObj.getString("en_Text"));
                    visaStatusArTitles.add(visaStatusObj.getString("ar_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(visaStatusSpinner, visaStatusArTitles);
                }else {
                    setSpinner(visaStatusSpinner, visaStatusEnTitles);
                }
                currentVisaStatus = visaStatusEnTitles.get(0)+"-"+visaStatusArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.visa_status), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.visa_status), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(visaStatusRequest);
    }

    private void getProfessionalLevel(){
        professionalLevelEnTitles.clear();
        professionalLevelArTitles.clear();
        professionalLevelArTitles.add(mainActivity.getResources().getString(R.string.select_professional_level));
        professionalLevelEnTitles.add(mainActivity.getResources().getString(R.string.select_professional_level));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest professionalLevelRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllCareerLevel", response -> {
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
                currentProfessionalLevel = professionalLevelEnTitles.get(0)+"-"+professionalLevelArTitles.get(0);
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

    private void getSalary(){
        salaryValues.clear();
        salaryValues.add(mainActivity.getResources().getString(R.string.select_expected_monthly_salary));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest salaryRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMonthlySalary", response -> {
            try {
                JSONArray salaryArray = new JSONArray(response);
                for (int i=0; i<salaryArray.length(); i++){
                    JSONObject salaryObj = salaryArray.getJSONObject(i);
                    salaryValues.add(salaryObj.getString("value"));
                }
                setSpinner(salarySpinner, salaryValues);
                currentSalary = salaryValues.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.expected_monthly_salary), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.expected_monthly_salary), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(salaryRequest);
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
                locationCurrent = locationArTitles.get(0)+"-"+locationEnTitles.get(0);
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

    private void setData(String description, String position){
        Send.addJobWantedAd.setDescription(description);
        Send.addJobWantedAd.setLocation(locationCurrent);
        Send.addJobWantedAd.setLatitude(latitude);
        Send.addJobWantedAd.setLongitude(longitude);
        Send.addJobWantedAd.setCategoryId("4");
        Send.addJobWantedAd.setGender(currentGender);
        Send.addJobWantedAd.setNationality(currentNationality);
        Send.addJobWantedAd.setCurrentLocation(currentLocation);
        Send.addJobWantedAd.setEducationLevel(currentEducationLevel);
        Send.addJobWantedAd.setCurrentPosition(position);
        Send.addJobWantedAd.setWorkExperience(currentWorkExperience);
        Send.addJobWantedAd.setCommitment(currentCommitment);
        Send.addJobWantedAd.setNoticePeriod(currentNoticePeriod);
        Send.addJobWantedAd.setVisaStatus(currentVisaStatus);
        Send.addJobWantedAd.setExpectedMonthlySalary(currentSalary);
        Send.addJobWantedAd.setCVFile(filePath);
        Send.addJobWantedAd.setCareerLevel(currentProfessionalLevel);
        packagesPopUp();
    }

    private void addInitialJobWantedAd(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject initialObj = new JSONObject();
        try {
            initialObj.put("title", Send.addJobWantedAd.getTitle());
            initialObj.put("jobType", Send.addJobWantedAd.getJobType());
            if (!Send.addJobWantedAd.getOtherJobType().isEmpty()) {
                initialObj.put("otherJobType", Send.addJobWantedAd.getOtherJobType());
            }
            initialObj.put("jobTitle", Send.addJobWantedAd.getJobTitle());
            initialObj.put("phoneNumber", Send.addJobWantedAd.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest classifiedsRequest = new JsonObjectRequest(Request.Method.POST, Urls.JobAds_URL+"AddInitialJobWanted", initialObj, response -> {
            try {
                Send.addJobWantedAd.setAdId(response.getString("adId"));
                postFullJobWantedAd();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
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
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(classifiedsRequest);
    }

    private void postFullJobWantedAd(){
//        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.JobAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody adId = createRequestBody(Send.addJobWantedAd.getAdId());
        RequestBody description = createRequestBody(Send.addJobWantedAd.getDescription());
        RequestBody location = createRequestBody(Send.addJobWantedAd.getLocation());
        RequestBody lat = createRequestBody(Send.addJobWantedAd.getLatitude());
        RequestBody lan = createRequestBody(Send.addJobWantedAd.getLongitude());
        RequestBody categoryId = createRequestBody(Send.addJobWantedAd.getCategoryId());
        RequestBody gender = createRequestBody(Send.addJobWantedAd.getGender());
        RequestBody nationality = createRequestBody(Send.addJobWantedAd.getNationality());
        RequestBody currentLocation = createRequestBody(Send.addJobWantedAd.getCurrentLocation());
        RequestBody educationLevel = createRequestBody(Send.addJobWantedAd.getEducationLevel());
        RequestBody currentPosition = createRequestBody(Send.addJobWantedAd.getCurrentPosition());
        RequestBody workExperience = createRequestBody(Send.addJobWantedAd.getWorkExperience());
        RequestBody commitment = createRequestBody(Send.addJobWantedAd.getCommitment());
        RequestBody noticePeriod = createRequestBody(Send.addJobWantedAd.getNoticePeriod());
        RequestBody currentProfessionalLevel = createRequestBody(Send.addJobWantedAd.getCareerLevel());
        RequestBody visaStatus = createRequestBody(Send.addJobWantedAd.getVisaStatus());
        RequestBody expectedMonthlySalary = createRequestBody(Send.addJobWantedAd.getExpectedMonthlySalary());
        RequestBody file = createRequestBody(Send.addJobWantedAd.getCVFile());

        Call<AddAdsResponse> addJobWantedAd = retrofit.create(RetrofitUrls.class).addJobWantedAd(adId, description, location, lat, lan, gender, categoryId, nationality, currentLocation, educationLevel, currentPosition, workExperience, commitment, noticePeriod, currentProfessionalLevel, visaStatus, expectedMonthlySalary, file);
        addJobWantedAd.enqueue(new Callback<AddAdsResponse>() {
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
                            navController.navigate(PostFullJobWantedAdFragmentDirections.actionPostFullJobWantedAdFragmentToPaymentFragment(packageId, ""));
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

    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    private void uploadFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_FILE);
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
            addInitialJobWantedAd();
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
        }else if (requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            filePath = mainActivity.fileUriToBase64(uri, mainActivity.getContentResolver());
            uploaded.setVisibility(View.VISIBLE);
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.file_uploaded), Toast.LENGTH_SHORT).show();
        }
    }

}