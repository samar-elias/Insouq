package com.hudhud.insouqapplication.Views.Registration.ForgotPassword;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordFragment extends Fragment {

    NavController navController;
    MaterialButton continueBtn;
    ConstraintLayout layout;
    EditText phoneEdt;
    TextView backToLogin;
    CountryCodePicker countryCodePicker;
    String phoneNumber;
    RegistrationActivity registrationActivity;

    public ForgotPasswordFragment() {
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
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
        continueBtn = view.findViewById(R.id.continue_btn);
        layout = view.findViewById(R.id.layout);
        phoneEdt = view.findViewById(R.id.mobile_number_edt);
        backToLogin = view.findViewById(R.id.back_to_login);
        countryCodePicker = view.findViewById(R.id.ccp);
    }

    private void onClick(){
        continueBtn.setOnClickListener(view -> {
            phoneNumber = countryCodePicker.getFullNumberWithPlus()+ phoneEdt.getText();
            if (phoneNumber.isEmpty()){
                registrationActivity.showResponseMessage(getResources().getString(R.string.forgot_password), getResources().getString(R.string.fill_all_fields));
            }else {
                getAccount(phoneNumber);
            }
        });

        backToLogin.setOnClickListener(view -> navController.popBackStack());

        layout.setOnClickListener(view -> registrationActivity.hideKeyboard());
    }

    private void getAccount(String phoneNumber){
        String phoneNum = phoneNumber.replace("+", "00");
        registrationActivity.showProgressDialog(registrationActivity.getResources().getString(R.string.loading));
        StringRequest accountRequest = new StringRequest(Request.Method.GET, Urls.Users_URL+"GetByEmailOrPhone?value="+phoneNum, response -> {
            registrationActivity.hideProgressDialog();
            try {
                JSONObject userObj = new JSONObject(response);
                AppDefs.user.setId(userObj.getString("id"));
                navController.navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToPasswordVerificationFragment(phoneNumber));
            } catch (JSONException e) {
                e.printStackTrace();
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.forgot_password), registrationActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.wrong_user));
        });
        registrationActivity.queue.add(accountRequest);
    }
}