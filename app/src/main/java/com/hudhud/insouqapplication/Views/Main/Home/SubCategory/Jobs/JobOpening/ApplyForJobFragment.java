package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createMultipartBodyPart;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

public class ApplyForJobFragment extends Fragment {

    private static final int REQUEST_CODE_FILE = 2;
    ImageView backToPrevious, home, chat, sellAnItem, profile, notifications;
    MaterialButton apply, cancel;
    NavController navController;
    MainActivity mainActivity;
    Spinner careerLevelSpinner, currentLocationSpinner, genderSpinner, nationalitySpinner, educationLevelSpinner, workExperienceSpinner, commitmentSpinner, noticePeriodSpinner, visaStatusSpinner;
    String currentGender = "", currentNationality = "", currentLocation = "", currentEducationLevel = "", currentWorkExperience = "", currentCommitment = "", currentNoticePeriod = "",
            currentVisaStatus = "", currentCareerLevel = "", filePath = "", id, date;
    TextView location, birthDate;
    ArrayList<String> careerLevelArTitles, careerLevelEnTitles, gendersArTitles, genderEnTitles, nationalityArTitles, nationalityEnTitles, currentLocationArTitles, currentLocationEnTitles, educationLevelArTitles,
            educationLevelEnTitles, workExperienceArTitles, workExperienceEnTitles, commitmentArTitles, commitmentEnTitles, noticePeriodArTitles, noticePeriodEnTitles,
            visaStatusArTitles, visaStatusEnTitles, professionalLevelArTitles, professionalLevelEnTitles, salaryValues, locationArTitles, locationEnTitles;
    CheckBox agreementCheckBox;
    EditText currentPosition, phoneNumberEdt, coverNoteEdt, currentCompany;
    ConstraintLayout uploadCV;
    DatePickerDialog.OnDateSetListener dateSetListener;

    public ApplyForJobFragment() {
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
        return inflater.inflate(R.layout.fragment_apply_for_job, container, false);
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
        onSpinnerClick();
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
            id = ApplyForJobFragmentArgs.fromBundle(getArguments()).getAdId();
        }

        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        location = view.findViewById(R.id.location);
        currentPosition = view.findViewById(R.id.current_position);
        agreementCheckBox = view.findViewById(R.id.sign_up_check_box);
        uploadCV = view.findViewById(R.id.add_your_cv);
        birthDate = view.findViewById(R.id.birth_date);
        phoneNumberEdt = view.findViewById(R.id.phone_edt);
        currentCompany = view.findViewById(R.id.current_company);
        coverNoteEdt = view.findViewById(R.id.cover_letter);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellAnItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);

        currentLocationSpinner = view.findViewById(R.id.location_spinner);
        nationalitySpinner = view.findViewById(R.id.nationality);
        educationLevelSpinner = view.findViewById(R.id.education_level);
        genderSpinner = view.findViewById(R.id.gender);
        workExperienceSpinner = view.findViewById(R.id.work_experience);
        commitmentSpinner = view.findViewById(R.id.commitment);
        noticePeriodSpinner = view.findViewById(R.id.notice_period);
        visaStatusSpinner = view.findViewById(R.id.visa_status);
        careerLevelSpinner = view.findViewById(R.id.career_level);
        apply = view.findViewById(R.id.apply);
        cancel = view.findViewById(R.id.cancel);

        careerLevelArTitles = new ArrayList<>();
        careerLevelEnTitles = new ArrayList<>();
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

        phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());

        apply.setOnClickListener(view -> {
            String phoneNum  = String.valueOf(phoneNumberEdt.getText());
            String coverNote  = String.valueOf(coverNoteEdt.getText());
            String position  = String.valueOf(currentPosition.getText());
            String company  = String.valueOf(currentCompany.getText());

            if (phoneNum.isEmpty() || coverNote.isEmpty() || position.isEmpty() || company.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                applyForJob(phoneNum, coverNote, position, company);
            }
        });
        cancel.setOnClickListener(view -> navController.popBackStack());

        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellAnItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));

        uploadCV.setOnClickListener(view -> uploadFile());

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mainActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                    , dateSetListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        dateSetListener = (datePicker, i, i1, i2) -> {
            i1 = month+1;
            date = day + "/" + i1 + "/" + i;
            birthDate.setText(date);
        };
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.job_details_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.job_details_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentGender = genderEnTitles.get(i)+"-"+gendersArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentNationality = nationalityEnTitles.get(i)+"-"+nationalityArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        currentLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentLocation = currentLocationEnTitles.get(i)+"-"+currentLocationArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workExperienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentWorkExperience = workExperienceEnTitles.get(i)+"-"+workExperienceArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        commitmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCommitment = commitmentEnTitles.get(i)+"-"+commitmentArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        noticePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentNoticePeriod = noticePeriodEnTitles.get(i)+"-"+noticePeriodArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        visaStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentVisaStatus = visaStatusEnTitles.get(i)+"-"+visaStatusArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        careerLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currentCareerLevel = professionalLevelEnTitles.get(i)+"-"+professionalLevelArTitles.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getGenders(){
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
                    setSpinner(currentLocationSpinner, currentLocationArTitles);
                }else {
                    setSpinner(currentLocationSpinner, currentLocationEnTitles);
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
        noticePeriodEnTitles.clear();
        noticePeriodArTitles.clear();
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
                    setSpinner(careerLevelSpinner, professionalLevelArTitles);
                }else {
                    setSpinner(careerLevelSpinner, professionalLevelEnTitles);
                }
                currentCareerLevel = professionalLevelEnTitles.get(0)+"-"+professionalLevelArTitles.get(0);
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

    private void applyForJob(String phoneNum, String coverLetter, String pos, String company){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.Ads_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody adId = createRequestBody(id);
        RequestBody defaultLanguage = createRequestBody(AppDefs.getLanguage());
        RequestBody DOB = createRequestBody(date);
        RequestBody coverNote = createRequestBody(coverLetter);
        RequestBody gender = createRequestBody(currentGender);
        RequestBody nationality = createRequestBody(currentNationality);
//        RequestBody currentLoc = createRequestBody(currentLocation);
        RequestBody educationLevel = createRequestBody(currentEducationLevel);
        RequestBody currentCompany = createRequestBody(company);
        RequestBody currentPosition = createRequestBody(pos);
        RequestBody workExperience = createRequestBody(currentWorkExperience);
        RequestBody commitment = createRequestBody(currentCommitment);
        RequestBody noticePeriod = createRequestBody(currentNoticePeriod);
        RequestBody visaStatus = createRequestBody(currentVisaStatus);
        RequestBody careerLevel = createRequestBody(currentCareerLevel);
        RequestBody phoneNumber = createRequestBody(phoneNum);
        RequestBody file = createRequestBody(filePath);
        RequestBody id = createRequestBody(AppDefs.user.getId());

        Call<AddAdsResponse> applyForJob = retrofit.create(RetrofitUrls.class).applyForJob(adId, defaultLanguage, DOB, coverNote, gender, currentCompany, nationality, educationLevel, currentPosition, workExperience, commitment, noticePeriod, visaStatus, careerLevel, phoneNumber, file, id);
        applyForJob.enqueue(new Callback<AddAdsResponse>() {
            @Override
            public void onResponse(Call<AddAdsResponse> call, Response<AddAdsResponse> response) {
                mainActivity.hideProgressDialog();
                Timber.tag("response").d(response.message());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.job_applied_successfully), Toast.LENGTH_SHORT).show();
                        startActivity("");
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.error_occured));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

    }

    private void uploadFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            filePath = mainActivity.fileUriToBase64(uri, mainActivity.getContentResolver());
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.file_uploaded), Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }


}