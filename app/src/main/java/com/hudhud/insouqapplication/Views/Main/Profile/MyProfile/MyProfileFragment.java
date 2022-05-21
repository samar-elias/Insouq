package com.hudhud.insouqapplication.Views.Main.Profile.MyProfile;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.UserFS;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Splash.SplashActivity;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
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
import static android.content.Context.MODE_PRIVATE;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createMultipartBodyPart;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

public class MyProfileFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_CODE = 101;
    private static final int REQUEST_CODE_FILE = 2;
    boolean isCV = false;
    String codeBySystem, newPhone = "", newEmail = "";
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ImageView  backToPrevious, home, profile, chat, sellItem, list, cvUploaded, industryUploaded;
    ConstraintLayout addCV, addIndustry;
    NavController navController;
    MainActivity mainActivity;
    MaterialButton changePassword, changeEmail, changePhone, updateProfile;
    TextView fullName, emailAddress, mobileNumber, password, birthDate, changePicture;
    EditText firstName, lastName, currentLocationEdt, currentPosition, currentCompany, covetNoteEdt;
//    DatePickerDialog.OnDateSetListener dateSetListener;
    Spinner locationSpinner, educationSpinner, languageSpinner, careerSpinner;
    ImageView profilePicture;
    CountryCodePicker codePicker;
    String currentPassword = "", newPassword = "", confirmNewPassword = "", profileImagePath = "";
    Spinner careerLevelSpinner, currentLocationSpinner, genderSpinner, nationalitySpinner, educationLevelSpinner, workExperienceSpinner, commitmentSpinner, noticePeriodSpinner, visaStatusSpinner;
    String currentGender = "", currentNationality = "", currentLocation = "", currentEducationLevel = "", currentWorkExperience = "", currentCommitment = "", currentNoticePeriod = "",
            currentVisaStatus = "", currentCareerLevel = "", filePath = "", industryPath = "", id, date = "";
    TextView location;
    ArrayList<String> careerLevelArTitles, careerLevelEnTitles, gendersArTitles, languages, genderEnTitles, nationalityArTitles, nationalityEnTitles, currentLocationArTitles, currentLocationEnTitles, educationLevelArTitles,
            educationLevelEnTitles, workExperienceArTitles, workExperienceEnTitles, commitmentArTitles, commitmentEnTitles, noticePeriodArTitles, noticePeriodEnTitles,
            visaStatusArTitles, visaStatusEnTitles, professionalLevelArTitles, professionalLevelEnTitles, salaryValues, locationArTitles, locationEnTitles;
    CheckBox agreementCheckBox, privacyCheck;
//    EditText currentPosition, phoneNumberEdt, coverNoteEdt, currentCompany;

    public MyProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
//        setSpinner(locationSpinner);
//        setSpinner(genderSpinner);
//        setSpinner(nationalitySpinner);
//        setSpinner(educationSpinner);
//        setSpinner(languageSpinner);
//        setSpinner(careerSpinner);
        setData();
        getGenders();
        getNationality();
        getCurrentLocation();
        getEducationLevel();
        getLanguages();
//        getWorkExperienceLevel();
//        getCommitment();
//        getNoticePeriod();
//        getVisaStatus();
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
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);
        changePassword = view.findViewById(R.id.change_password);
        changeEmail = view.findViewById(R.id.change_email);
        changePhone = view.findViewById(R.id.change_phone);
        birthDate = view.findViewById(R.id.birth_date);
        genderSpinner = view.findViewById(R.id.gender_spinner);
        nationalitySpinner = view.findViewById(R.id.nationality_spinner);
        currentLocationSpinner = view.findViewById(R.id.location_spinner);
        languageSpinner = view.findViewById(R.id.language_spinner);
//        careerSpinner = view.findViewById(R.id.career_spinner);
        educationLevelSpinner = view.findViewById(R.id.education_spinner);
        updateProfile = view.findViewById(R.id.update_profile);
        fullName = view.findViewById(R.id.user_name);
        emailAddress = view.findViewById(R.id.email_text);
        mobileNumber = view.findViewById(R.id.mobile_number);
        password = view.findViewById(R.id.password);
        firstName = view.findViewById(R.id.first_name_edt);
        lastName = view.findViewById(R.id.last_name_edt);
        currentPosition = view.findViewById(R.id.current_position);
        currentLocationEdt = view.findViewById(R.id.current_location);
        currentCompany = view.findViewById(R.id.current_company);
        covetNoteEdt = view.findViewById(R.id.cover_letter);
        profilePicture = view.findViewById(R.id.profile_image);
        codePicker = view.findViewById(R.id.ccp);
        changePicture = view.findViewById(R.id.change_profile);

        addCV = view.findViewById(R.id.add_your_cv);
        addIndustry = view.findViewById(R.id.add_industry);

//        workExperienceSpinner = view.findViewById(R.id.work_experience);
        commitmentSpinner = view.findViewById(R.id.commitment);
        noticePeriodSpinner = view.findViewById(R.id.notice_period);
        visaStatusSpinner = view.findViewById(R.id.visa_status);
        careerLevelSpinner = view.findViewById(R.id.career_spinner);

        cvUploaded = view.findViewById(R.id.cv_checked);
        industryUploaded = view.findViewById(R.id.industry_checked);

        privacyCheck = view.findViewById(R.id.privacy_checkbox);

        languages = new ArrayList<>();
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
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        changePassword.setOnClickListener(view -> changePasswordPopUp());
        changePhone.setOnClickListener(view -> {
            if (AppDefs.user.getExternalLogin().equals("false")) {
                changeMobilePopUp();
            }
        });
        changeEmail.setOnClickListener(view -> {
            if (AppDefs.user.getExternalLogin().equals("false")) {
                changeEmailPopUp();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        addIndustry.setOnClickListener(view -> {
            uploadFile();
            isCV = false;
        });

        addCV.setOnClickListener(view -> {
            uploadFile();
            isCV = true;
        });

//        birthDate.setOnClickListener(view -> {
//            DatePickerDialog datePickerDialog = new DatePickerDialog(mainActivity, android.R.style.Theme_Holo_Light_Dialog_MinWidth
//                    , dateSetListener, year, month, day);
//            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            datePickerDialog.show();
//        });
//
//        dateSetListener = (datePicker, i, i1, i2) -> {
//            i1 = month+1;
//            date = day + "/" + i1 + "/" + i;
//            date = i1+"/"+day+"/"+i;
//            birthDate.setText(date);
//
//        };


        final Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
        final DatePickerDialog  StartTime = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        birthDate.setOnClickListener(v -> StartTime.show());

        changePicture.setOnClickListener(view -> pickImage());

        updateProfile.setOnClickListener(view -> {
            String fName = String.valueOf(firstName.getText());
            String lName = String.valueOf(lastName.getText());
            String location = String.valueOf(currentLocationEdt.getText());
            String position = String.valueOf(currentPosition.getText());
            String company = String.valueOf(currentCompany.getText());
            String note = String.valueOf(covetNoteEdt.getText());
            if (fName.isEmpty() || lName.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.update_profile), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                if (privacyCheck.isChecked()){
                    updateProfile(fName, lName, location, position, company, note, "true");
                }else {
                    updateProfile(fName, lName, location, position, company, note, "false");
                }
            }
        });
    }

    private void showDateDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(mainActivity, this,
                Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR));
        datePickerDialog.show();
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    public void changePasswordPopUp(){
        View passwordChangeAlertView = LayoutInflater.from(getContext()).inflate(R.layout.change_password_pop_up, null);
        AlertDialog passwordChangeAlertBuilder = new AlertDialog.Builder(getContext()).setView(passwordChangeAlertView).show();
        passwordChangeAlertBuilder.show();

        passwordChangeAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton changePassword = passwordChangeAlertView.findViewById(R.id.change_password);
        ImageView close = passwordChangeAlertView.findViewById(R.id.close);
        EditText currentPasswordEdt = passwordChangeAlertView.findViewById(R.id.current_password);
        EditText newPasswordEdt = passwordChangeAlertView.findViewById(R.id.new_password);
        EditText confirmNewPasswordEdt = passwordChangeAlertView.findViewById(R.id.confirm_new_password);


        changePassword.setOnClickListener(view -> {
            currentPassword = String.valueOf(currentPasswordEdt.getText());
            newPassword = String.valueOf(newPasswordEdt.getText());
            confirmNewPassword = String.valueOf(confirmNewPasswordEdt.getText());
            if (currentPassword.isEmpty() || newPassword.isEmpty() ||confirmNewPassword.isEmpty()){
                mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.fill_all_fields));
            }else if (!newPassword.equals(confirmNewPassword)){
                mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.not_matching_passwords));
            }else {
                changePassword();
                passwordChangeAlertBuilder.dismiss();
            }
        });
        close.setOnClickListener(v -> passwordChangeAlertBuilder.dismiss());
    }

    private void changePassword(){
        mainActivity.showProgressDialog(getResources().getString(R.string.changing_password));
        JSONObject changePasswordObject = new JSONObject();
        try {
            changePasswordObject.put("currentPassword", currentPassword);
            changePasswordObject.put("newPassword", newPassword);
            changePasswordObject.put("confirmNewPassword", confirmNewPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"ChangePassword", changePasswordObject, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getBoolean("isSuccess")){
                    Toast.makeText(mainActivity, getResources().getString(R.string.password_changed_successfully), Toast.LENGTH_LONG).show();
                }else {
                    mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.password_not_changed_successfully));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.wrong_current_password));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(changePasswordRequest);
    }

    public void changeMobilePopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.change_mobile_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton changePassword = myAdsOptionsAlertView.findViewById(R.id.change_password);
        ImageView close = myAdsOptionsAlertView.findViewById(R.id.close);
        CountryCodePicker countryCodePicker = myAdsOptionsAlertView.findViewById(R.id.ccp);
        EditText phone = myAdsOptionsAlertView.findViewById(R.id.phone_edt);
        changePassword.setOnClickListener(view -> {
            if (phone.getText().toString().isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.change_mobile_number), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                newPhone = countryCodePicker.getFullNumberWithPlus()+ phone.getText();
                checkUser(newPhone);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
        close.setOnClickListener(v -> myAdsOptionsAlertBuilder.dismiss());
    }

    private void checkUser(String mobileNumber){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest checkUserRequest = new StringRequest(Request.Method.GET, Urls.Users_URL+"GetByEmailOrPhone?value="+mobileNumber, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONObject userObj = new JSONObject(response);
                if (userObj.has("isSuccess")){
                    mobileVerificationPopUp(mobileNumber);
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.my_profile), mainActivity.getResources().getString(R.string.account_existed));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.my_profile), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.my_profile), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        checkUserRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mainActivity.queue.add(checkUserRequest);
    }

    public void mobileVerificationPopUp(String phone){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.verify_mobile_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        MaterialButton changePassword = myAdsOptionsAlertView.findViewById(R.id.change_password);
        OtpTextView codeEdt = myAdsOptionsAlertView.findViewById(R.id.otp_view);
        TextView resendBtn = myAdsOptionsAlertView.findViewById(R.id.resend_btn);
        ImageView close = myAdsOptionsAlertView.findViewById(R.id.close);

        close.setOnClickListener(v -> myAdsOptionsAlertBuilder.dismiss());

        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeBySystem = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    codeEdt.setOTP(code);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        sendVerificationCodeToUser(phone);
        resendBtn.setOnClickListener(view -> sendVerificationCodeToUser(phone));
        changePassword.setOnClickListener(view ->{
            String code = String.valueOf(codeEdt.getOTP());
            if (!code.isEmpty()){
                verifyCode(code);
                myAdsOptionsAlertBuilder.dismiss();
            }

        } );
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
//                Toast.makeText(getContext(), "Verification completed", Toast.LENGTH_LONG).show();
                verifyPhone();

            } else {
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getContext(), "Verification not completed! Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendVerificationCodeToUser(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(2L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyPhone(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("mobileNumber", newPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest verifyPhoneRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"VerifyChangeMobileNumberOTP", phoneObject, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getBoolean("isSuccess")){
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.phone_change), Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(mainActivity, SplashActivity.class);
                    startActivity(mainIntent);
                    mainActivity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            if (error instanceof AuthFailureError){
                mainActivity.showResponseMessage(getResources().getString(R.string.change_mobile_number), getResources().getString(R.string.error));
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        verifyPhoneRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mainActivity.queue.add(verifyPhoneRequest);
    }

    public void changeEmailPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.change_email_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        EditText newEmailEdt = myAdsOptionsAlertView.findViewById(R.id.new_email_edt);
        ImageView close = myAdsOptionsAlertView.findViewById(R.id.close);
        MaterialButton changePassword = myAdsOptionsAlertView.findViewById(R.id.change_password);
        changePassword.setOnClickListener(view -> {
            newEmail = String.valueOf(newEmailEdt.getText());
            if (newEmail.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.change_email_address), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                verifyEmail(newEmail);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
        close.setOnClickListener(v -> myAdsOptionsAlertBuilder.dismiss());
    }

    private void verifyEmail(String email){
        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("newEmail", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest verifyPhoneRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"ChangeEmail", phoneObject, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getBoolean("isSuccess")){
                    verifyPopUp();
//                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.phone_change), Toast.LENGTH_SHORT).show();
//                    Intent mainIntent = new Intent(mainActivity, SplashActivity.class);
//                    startActivity(mainIntent);
//                    mainActivity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            if (error instanceof AuthFailureError){
                mainActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.error));
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        verifyPhoneRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mainActivity.queue.add(verifyPhoneRequest);
    }

    public void verifyPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.verification_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        EditText codeEdt = myAdsOptionsAlertView.findViewById(R.id.code_edt);
        MaterialButton changePassword = myAdsOptionsAlertView.findViewById(R.id.change_password);
        changePassword.setOnClickListener(view -> {
            String code = String.valueOf(codeEdt.getText());
            if (code.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.change_email_address), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                verifyEmailCode(code);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void verifyEmailCode(String code){
        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("email", newEmail);
            phoneObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest verifyPhoneRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"VerifyChangeEmailOTP", phoneObject, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getBoolean("isSuccess")){
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.email_changed), Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(mainActivity, SplashActivity.class);
                    startActivity(mainIntent);
                    mainActivity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(getResources().getString(R.string.change_email_address), getResources().getString(R.string.wrong_code));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        verifyPhoneRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mainActivity.queue.add(verifyPhoneRequest);
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

//        workExperienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                currentWorkExperience = workExperienceEnTitles.get(i)+"-"+workExperienceArTitles.get(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        commitmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                currentCommitment = commitmentEnTitles.get(i)+"-"+commitmentArTitles.get(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        noticePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                currentNoticePeriod = noticePeriodEnTitles.get(i)+"-"+noticePeriodArTitles.get(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        visaStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                currentVisaStatus = visaStatusEnTitles.get(i)+"-"+visaStatusArTitles.get(i);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

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

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.job_details_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.job_details_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void setData(){
        if (!AppDefs.user.getProfilePicture().isEmpty()){
            String newPic = AppDefs.user.getProfilePicture().replace("\\", "/");
            Glide.with(mainActivity).load(newPic).into(profilePicture);
        }
        fullName.setText(AppDefs.user.getFirstName()+" "+AppDefs.user.getLastName());
        firstName.setText(AppDefs.user.getFirstName());
        lastName.setText(AppDefs.user.getLastName());
        emailAddress.setText(AppDefs.user.getEmail());
        if (!AppDefs.user.getMobileNumber().equals("null") && !AppDefs.user.getMobileNumber().isEmpty()){
            StringBuffer tempPhoneNumber = new StringBuffer(AppDefs.user.getMobileNumber());
            StringBuffer reversedPhoneNumber = tempPhoneNumber.reverse();
            String subPhoneNumber = reversedPhoneNumber.substring(0,9);
            mobileNumber.setText(new StringBuffer(subPhoneNumber).reverse());
        }
        if (!AppDefs.user.getDob().isEmpty()){
            String dateOFBirth = AppDefs.user.getDob().substring(0, AppDefs.user.getDob().indexOf("T"));
            birthDate.setText(dateOFBirth);
        }
        currentLocationEdt.setText(AppDefs.user.getDefaultLocation());
        currentPosition.setText(AppDefs.user.getCurrentPosition());
        currentCompany.setText(AppDefs.user.getCurrentCompany());
        covetNoteEdt.setText(AppDefs.user.getCoverNote());
        if (filePath.isEmpty() && AppDefs.user.getCv().isEmpty()){
            cvUploaded.setVisibility(View.INVISIBLE);
        }else {
            cvUploaded.setVisibility(View.VISIBLE);
        }

        if (industryPath.isEmpty() && AppDefs.user.getIndustry().isEmpty()){
            industryUploaded.setVisibility(View.INVISIBLE);
        }else {
            industryUploaded.setVisibility(View.VISIBLE);
        }
    }

    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_CODE);
        } else {
            PickImageDialog.build(new PickSetup()).setOnPickResult(r -> {
                profileImagePath = r.getPath();
                Glide.with(mainActivity).load(r.getUri()).into(profilePicture);
                profilePicture.setImageBitmap(r.getBitmap());
            }).show(getActivity());
        }
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

    private void getLanguages(){
        languages.add("English");
        languages.add("Arabic");
        setSpinner(languageSpinner, languages);
        currentGender = genderEnTitles.get(0)+"-"+gendersArTitles.get(0);
    }

//    private void getWorkExperienceLevel(){
//        workExperienceEnTitles.clear();
//        workExperienceArTitles.clear();
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
//        StringRequest workExperienceRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllWorkExperience", response -> {
//            try {
//                JSONArray workExperienceArray = new JSONArray(response);
//                for (int i=0; i<workExperienceArray.length(); i++){
//                    JSONObject workExperienceObj = workExperienceArray.getJSONObject(i);
//                    workExperienceEnTitles.add(workExperienceObj.getString("en_Text"));
//                    workExperienceArTitles.add(workExperienceObj.getString("ar_Text"));
//                }
//                if (AppDefs.language.equals("ar")){
//                    setSpinner(workExperienceSpinner, workExperienceArTitles);
//                }else {
//                    setSpinner(workExperienceSpinner, workExperienceEnTitles);
//                }
//                currentWorkExperience = workExperienceEnTitles.get(0)+"-"+workExperienceArTitles.get(0);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                mainActivity.hideProgressDialog();
//                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.error_occured));
//            }
//
//        }, error -> {
//            mainActivity.hideProgressDialog();
//            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.internet_connection_error));
//        });
//        mainActivity.queue.add(workExperienceRequest);
//    }

//    private void getCommitment(){
//        noticePeriodEnTitles.clear();
//        noticePeriodArTitles.clear();
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
//        StringRequest commitmentRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllCommitment", response -> {
//            try {
//                JSONArray commitmentArray = new JSONArray(response);
//                for (int i=0; i<commitmentArray.length(); i++){
//                    JSONObject commitmentObj = commitmentArray.getJSONObject(i);
//                    commitmentEnTitles.add(commitmentObj.getString("en_Text"));
//                    commitmentArTitles.add(commitmentObj.getString("ar_Text"));
//                }
//                if (AppDefs.language.equals("ar")){
//                    setSpinner(commitmentSpinner, commitmentArTitles);
//                }else {
//                    setSpinner(commitmentSpinner, commitmentEnTitles);
//                }
//                currentCommitment = commitmentEnTitles.get(0)+"-"+commitmentArTitles.get(0);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                mainActivity.hideProgressDialog();
//                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.commitment), mainActivity.getResources().getString(R.string.error_occured));
//            }
//
//        }, error -> {
//            mainActivity.hideProgressDialog();
//            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.commitment), mainActivity.getResources().getString(R.string.internet_connection_error));
//        });
//        mainActivity.queue.add(commitmentRequest);
//    }
//
//    private void getNoticePeriod(){
//        noticePeriodEnTitles.clear();
//        noticePeriodArTitles.clear();
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
//        StringRequest noticePeriodRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllNoticePeriod", response -> {
//            try {
//                JSONArray noticePeriodArray = new JSONArray(response);
//                for (int i=0; i<noticePeriodArray.length(); i++){
//                    JSONObject noticePeriodObj = noticePeriodArray.getJSONObject(i);
//                    noticePeriodEnTitles.add(noticePeriodObj.getString("en_Text"));
//                    noticePeriodArTitles.add(noticePeriodObj.getString("ar_Text"));
//                }
//                if (AppDefs.language.equals("ar")){
//                    setSpinner(noticePeriodSpinner, noticePeriodArTitles);
//                }else {
//                    setSpinner(noticePeriodSpinner, noticePeriodEnTitles);
//                }
//                currentNoticePeriod = noticePeriodEnTitles.get(0)+"-"+noticePeriodArTitles.get(0);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                mainActivity.hideProgressDialog();
//                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.notice_period), mainActivity.getResources().getString(R.string.error_occured));
//            }
//
//        }, error -> {
//            mainActivity.hideProgressDialog();
//            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.notice_period), mainActivity.getResources().getString(R.string.internet_connection_error));
//        });
//        mainActivity.queue.add(noticePeriodRequest);
//    }
//
//    private void getVisaStatus(){
//        visaStatusArTitles.clear();
//        visaStatusEnTitles.clear();
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
//        StringRequest visaStatusRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllVisaStatus", response -> {
//
//            try {
//                JSONArray visaStatusArray = new JSONArray(response);
//                for (int i=0; i<visaStatusArray.length(); i++){
//                    JSONObject visaStatusObj = visaStatusArray.getJSONObject(i);
//                    visaStatusEnTitles.add(visaStatusObj.getString("en_Text"));
//                    visaStatusArTitles.add(visaStatusObj.getString("ar_Text"));
//                }
//                if (AppDefs.language.equals("ar")){
//                    setSpinner(visaStatusSpinner, visaStatusArTitles);
//                }else {
//                    setSpinner(visaStatusSpinner, visaStatusEnTitles);
//                }
//                currentVisaStatus = visaStatusEnTitles.get(0)+"-"+visaStatusArTitles.get(0);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                mainActivity.hideProgressDialog();
//                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.visa_status), mainActivity.getResources().getString(R.string.error_occured));
//            }
//
//        }, error -> {
//            mainActivity.hideProgressDialog();
//            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.visa_status), mainActivity.getResources().getString(R.string.internet_connection_error));
//        });
//        mainActivity.queue.add(visaStatusRequest);
//    }

    private void updateProfile(String fName, String lName, String loc, String position, String company, String note, String isPrivate){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.Users_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody firstName = createRequestBody(fName);
        RequestBody lastName = createRequestBody(lName);
        RequestBody gender = createRequestBody(currentGender);
        RequestBody DOB = createRequestBody(date);
        RequestBody nationality = createRequestBody(currentNationality);
        RequestBody defaultLocation = createRequestBody(loc);
        RequestBody defaultLanguage = createRequestBody("English");
        RequestBody careerLevel = createRequestBody(currentCareerLevel);
        RequestBody education = createRequestBody(currentEducationLevel);
        RequestBody currentPosition = createRequestBody(position);
        RequestBody currentCompany = createRequestBody(company);
        RequestBody coverNote = createRequestBody(note);
        RequestBody hideInformation = createRequestBody(isPrivate);
        RequestBody file = createRequestBody(filePath);
        RequestBody industry = createRequestBody(industryPath);
        MultipartBody.Part image = createMultipartBodyPart("ProfilePictureFile", profileImagePath);

        Call<UserFS> updateProfile = retrofit.create(RetrofitUrls.class).updateProfile(firstName, lastName, gender, DOB, nationality, defaultLocation, defaultLanguage, careerLevel, education, currentPosition, currentCompany, coverNote, hideInformation, file, industry, image);
        updateProfile.enqueue(new Callback<UserFS>() {
            @Override
            public void onResponse(Call<UserFS> call, Response<UserFS> response) {
                mainActivity.hideProgressDialog();
                if (response.isSuccessful()){
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mainActivity, SplashActivity.class);
                    startActivity(intent);
                    mainActivity.finish();
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.update_profile), mainActivity.getResources().getString(R.string.error_occured));
                }
            }

            @Override
            public void onFailure(Call<UserFS> call, Throwable t) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.update_profile), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });
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
            if (isCV){
                filePath = mainActivity.fileUriToBase64(uri, mainActivity.getContentResolver());
                cvUploaded.setVisibility(View.VISIBLE);
            }else {
                industryPath = mainActivity.fileUriToBase64(uri, mainActivity.getContentResolver());
                industryUploaded.setVisibility(View.VISIBLE);
            }

            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.file_uploaded), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String d = String.valueOf(dayOfMonth), m = String.valueOf(month+1);
        if (d.length()==1){
            d = "0"+ d;
        }
        if (m.length()==1){
            m = "0"+ m;
        }
        date = m+"-"+d+"-"+year;
        birthDate.setText(date);
    }
}