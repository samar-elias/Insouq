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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class PasswordVerificationFragment extends Fragment {

    MaterialButton confirm;
    NavController navController;
    OtpTextView otpEdt;
    ImageView backToPrevious;
    ConstraintLayout verificationLayout;
    RegistrationActivity registrationActivity;
    String codeBySystem, phoneNum = "", resetPasswordToken = "";
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public PasswordVerificationFragment() {
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
        return inflater.inflate(R.layout.fragment_password_verification, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegistrationActivity) {
            registrationActivity = (RegistrationActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        if (getArguments() != null){
            phoneNum = PasswordVerificationFragmentArgs.fromBundle(getArguments()).getPhoneNumber();
        }
        confirm = view.findViewById(R.id.confirm_btn);
        otpEdt = view.findViewById(R.id.otp_view);
        backToPrevious = view.findViewById(R.id.back_arrow);
        verificationLayout = view.findViewById(R.id.verification_layout);
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
                    otpEdt.setOTP(code);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        sendVerificationCodeToUser(phoneNum);
    }

    private void onClick(){
        confirm.setOnClickListener(view -> {
            String code = otpEdt.getOTP();
            if (!code.isEmpty()){
                verifyCode(code);
            }
        });
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        verificationLayout.setOnClickListener(view -> registrationActivity.hideKeyboard());
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Verification completed", Toast.LENGTH_LONG).show();
                getResetPasswordToken();
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

    private void getResetPasswordToken(){
        registrationActivity.showProgressDialog(getResources().getString(R.string.loading));
        JSONObject tokenObject = new JSONObject();
        phoneNum = phoneNum.replace("+","00");
        try {
            tokenObject.put("mobileNumber", phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest tokenRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"RequestForgotPasswordToken", tokenObject, response -> {
            registrationActivity.hideProgressDialog();
            try {
                resetPasswordToken = response.getString("resetPasswordToken");
                navController.navigate(PasswordVerificationFragmentDirections.actionPasswordVerificationFragmentToNewPasswordFragment(resetPasswordToken, phoneNum));
            } catch (JSONException e) {
                e.printStackTrace();
                registrationActivity.showResponseMessage(getResources().getString(R.string.forgot_password), getResources().getString(R.string.user_not_found));
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            registrationActivity.showResponseMessage(getResources().getString(R.string.forgot_password), getResources().getString(R.string.internet_connection_error));
        });
        registrationActivity.queue.add(tokenRequest);
    }
}