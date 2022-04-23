package com.hudhud.insouqapplication.Views.Registration.SignUp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.UserToSend;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.http.Url;

public class SignUpFragment extends Fragment {

    MaterialButton signUp;
    RegistrationActivity registrationActivity;
    NavController navController;
    EditText firstNameEdt, lastNameEdt, mobileNumberEdt, emailAddressEdt, passwordEdt;
    CountryCodePicker countryCodePicker ;
    CheckBox signUpCheckBox;
    String firstName = "", lastName = "", mobileNumber = "", emailAddress = "", password = "";
    ConstraintLayout layout;
    ImageView showHidePassword, correctForm;
    Boolean passwordVisible = false;

    public SignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegistrationActivity) {
            registrationActivity = (RegistrationActivity) context;
        }
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        signUp = view.findViewById(R.id.sign_up_btn);
        firstNameEdt = view.findViewById(R.id.first_name_edt);
        lastNameEdt = view.findViewById(R.id.last_name_edt);
        mobileNumberEdt = view.findViewById(R.id.mobile_number_edt);
        emailAddressEdt = view.findViewById(R.id.email_address_edt);
        passwordEdt = view.findViewById(R.id.password_edt);
        passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        countryCodePicker = view.findViewById(R.id.ccp);
        signUpCheckBox = view.findViewById(R.id.sign_up_check_box);
        layout = view.findViewById(R.id.layout);
        showHidePassword = view.findViewById(R.id.show_hide_password);
        correctForm = view.findViewById(R.id.correct_form);

        if (signUpCheckBox.isChecked()){
            signUp.setBackgroundColor(getResources().getColor(R.color.base_orange));
            signUp.setEnabled(true);
        }else {
            signUp.setBackgroundColor(getResources().getColor(R.color.gray_3));
            signUp.setEnabled(false);
        }
    }

    private void onClick(){
        signUp.setOnClickListener(view -> {
            firstName = String.valueOf(firstNameEdt.getText());
            lastName = String.valueOf(lastNameEdt.getText());
            String mobNum = countryCodePicker.getFullNumberWithPlus()+ mobileNumberEdt.getText();
            mobileNumber = mobNum.replace("+", "00");
            emailAddress = String.valueOf(emailAddressEdt.getText());
            password = String.valueOf(passwordEdt.getText());

            if (!firstName.isEmpty() && !lastName.isEmpty() && !mobileNumber.isEmpty() && !emailAddress.isEmpty() && !password.isEmpty()){
                if(password.length()<6 || !isValidPassword(password)){
                    registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.password_validation));
                }else {
                    checkUser();
                }
            }else {
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.fill_all_fields));
            }
        });

        signUpCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                signUp.setBackgroundColor(getResources().getColor(R.color.base_orange));
                signUp.setEnabled(true);
            }else {
                signUp.setBackgroundColor(getResources().getColor(R.color.gray_3));
                signUp.setEnabled(false);
            }
        });

        layout.setOnClickListener(view -> registrationActivity.hideKeyboard());

        showHidePassword.setOnClickListener(view -> {
            if (passwordVisible){
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordVisible = false;
            }else {
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                passwordVisible = true;
            }
        });

        emailAddressEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailAddress = String.valueOf(emailAddressEdt.getText());
                if (emailAddress.contains("@") && emailAddress.contains(".com")){
                    correctForm.setVisibility(View.VISIBLE);
                }else {
                    correctForm.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void signUp(){
        Send.user = new UserToSend(firstName, lastName, mobileNumber, emailAddress, password);
        navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToMobileVerificationFragment());
    }

    private void checkUser(){
        registrationActivity.showProgressDialog(registrationActivity.getResources().getString(R.string.loading));
        StringRequest checkUserRequest = new StringRequest(Request.Method.GET, Urls.Users_URL+"GetByEmailOrPhone?value="+mobileNumber, response -> {
            registrationActivity.hideProgressDialog();
            try {
                JSONObject userObj = new JSONObject(response);
                if (userObj.has("isSuccess")){
                    checkUser2();
                }else {
                    showLogin(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.account_existed));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.internet_connection_error));
        });
        checkUserRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        registrationActivity.queue.add(checkUserRequest);
    }

    private void checkUser2(){
        registrationActivity.showProgressDialog(registrationActivity.getResources().getString(R.string.loading));
        StringRequest checkUserRequest = new StringRequest(Request.Method.GET, Urls.Users_URL+"GetByEmailOrPhone?value="+emailAddress, response -> {
            registrationActivity.hideProgressDialog();
            try {
                JSONObject userObj = new JSONObject(response);
                if (userObj.has("isSuccess")){
                    signUp();
                }else {
                    showLogin(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.account_existed2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.internet_connection_error));
        });
        checkUserRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        registrationActivity.queue.add(checkUserRequest);
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void showLogin(String title, String msg){
        AlertDialog.Builder msgDialog = new AlertDialog.Builder(registrationActivity);
        msgDialog.setTitle(title);
        msgDialog.setMessage(msg);

        msgDialog.setPositiveButton(getResources().getString(R.string.sign_in), (dialogInterface, i) -> navController.navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()));
        msgDialog.setNegativeButton(registrationActivity.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        msgDialog.show();
    }
}