package com.hudhud.insouqapplication.Views.Registration.Verification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
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
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

import static android.content.Context.MODE_PRIVATE;

public class MobileVerificationFragment extends Fragment {

    MaterialButton confirm;
    NavController navController;
    OtpTextView otpEdt;
    ImageView backToPrevious;
    ConstraintLayout verificationLayout;
    RegistrationActivity registrationActivity;
    String codeBySystem;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phone = "";

    public MobileVerificationFragment() {
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
        return inflater.inflate(R.layout.fragment_mobile_verification, container, false);
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
        confirm = view.findViewById(R.id.confirm_btn);
        otpEdt = view.findViewById(R.id.otp_view);
        backToPrevious = view.findViewById(R.id.back_arrow);
        verificationLayout = view.findViewById(R.id.verification_layout);
        phone = Send.user.getPhoneNumber().replace("00", "+");
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
        sendVerificationCodeToUser(phone);
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
                signUp();

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
        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("mobileNumber", AppDefs.user.getMobileNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest verifyPhoneRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"ConfirmMobileNumber", phoneObject, response -> {
            registrationActivity.hideProgressDialog();
            try {
                if (response.getBoolean("isSuccess")){
                    Intent mainIntent = new Intent(registrationActivity, MainActivity.class);
                    startActivity(mainIntent);
                    registrationActivity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            if (error instanceof AuthFailureError){
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.error));
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
        registrationActivity.queue.add(verifyPhoneRequest);
    }

    private void signUp(){
        registrationActivity.showProgressDialog(getResources().getString(R.string.loading));
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("firstName", Send.user.getFirstName());
            userObject.put("lastName", Send.user.getLastName());
            String phoneNum = Send.user.getPhoneNumber().replace("+", "00");
            userObject.put("mobileNumber", phoneNum);
            userObject.put("email", Send.user.getEmailAddress());
            userObject.put("password", Send.user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest signUpRequest = new JsonObjectRequest(Request.Method.POST, Urls.Users_URL+"Add", userObject, response -> {
            try {
                AppDefs.user.setToken(response.getString("token"));
                AppDefs.token = AppDefs.user.getToken();
                AppDefs.user.setId(response.getString("userId"));
                AppDefs.user.setMobileNumber(response.getString("phoneNumber"));
                saveUserToSharedPreferences();
                verifyPhone();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.signup_not_successfull));
        });
        signUpRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        registrationActivity.queue.add(signUpRequest);
    }

    private void saveUserToSharedPreferences(){
        SharedPreferences sharedPreferences = registrationActivity.getSharedPreferences(AppDefs.SHARED_PREF_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(AppDefs.ID_KEY, AppDefs.user.getId());
        editor.putString(AppDefs.TOKEN_KEY, AppDefs.user.getToken());

        editor.apply();
    }
}