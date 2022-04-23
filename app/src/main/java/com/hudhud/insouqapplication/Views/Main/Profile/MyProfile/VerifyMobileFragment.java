package com.hudhud.insouqapplication.Views.Main.Profile.MyProfile;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
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
import com.hudhud.insouqapplication.Views.Splash.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class VerifyMobileFragment extends Fragment {

    MaterialButton confirm;
    NavController navController;
    OtpTextView otpEdt;
    ImageView backToPrevious;
    ConstraintLayout verificationLayout;
    MainActivity mainActivity;
    String codeBySystem;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phone = "";

    public VerifyMobileFragment() {
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
        return inflater.inflate(R.layout.fragment_verify_mobile, container, false);
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

    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        confirm = view.findViewById(R.id.confirm_btn);
        otpEdt = view.findViewById(R.id.otp_view);
        backToPrevious = view.findViewById(R.id.back_arrow);
        verificationLayout = view.findViewById(R.id.verification_layout);
//        if (getArguments() != null){
//            phone = VerifyMobileFragmentArgs.fromBundle(getArguments()).getPhoneNum();
//        }

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
        verificationLayout.setOnClickListener(view -> mainActivity.hideKeyboard());
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Verification completed", Toast.LENGTH_LONG).show();
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
        JSONObject phoneObject = new JSONObject();
        try {
            phoneObject.put("mobileNumber", AppDefs.user.getMobileNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest verifyPhoneRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"ConfirmMobileNumber", phoneObject, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getBoolean("isSuccess")){
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
}