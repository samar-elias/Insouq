package com.hudhud.insouqapplication.Views.Registration.Verification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.aabhasjindal.otptextview.OtpTextView;
import timber.log.Timber;

public class VerificationEmailFragment extends Fragment {

    MaterialButton confirmBtn;
    NavController navController;
    OtpTextView otpEdt;
    ImageView backToPrevious;
    RegistrationActivity registrationActivity;
    String comingFrom, emailAddress;

    public VerificationEmailFragment() {
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
        return inflater.inflate(R.layout.fragment_verification_email, container, false);
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
        confirmBtn = view.findViewById(R.id.confirm_btn);
        otpEdt = view.findViewById(R.id.otp_view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        if (getArguments()!=null){
//            comingFrom = VerificationEmailFragmentArgs.fromBundle(getArguments()).getComingFrom();
//            emailAddress = VerificationEmailFragmentArgs.fromBundle(getArguments()).getEmailAddress();
        }
    }

    private void onClick(){
        confirmBtn.setOnClickListener(view -> {
//            navController.navigate(VerificationEmailFragmentDirections.actionVerificationEmailFragmentToNewPasswordFragment());
        });

        backToPrevious.setOnClickListener(view -> navController.popBackStack());
    }

    private void sendEmailCode(){
        registrationActivity.showProgressDialog(getResources().getString(R.string.signing_in));
        JSONObject emailObject = new JSONObject();
        try {
            emailObject.put("email", Send.user.getEmailAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest sendEmailCode =  new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"SendEmailCode", emailObject, response -> {

        }, error -> {

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
    }

    private void forgotPassword(){
        registrationActivity.showProgressDialog(getResources().getString(R.string.sending_code));
        JSONObject emailObject = new JSONObject();
        try {
            emailObject.put("email", Send.user.getEmailAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}