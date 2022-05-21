package com.hudhud.insouqapplication.Views.Registration.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Helpers.LocaleHelper;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

import timber.log.Timber;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class HomeRegistrationFragment extends Fragment{

    private static final int GOOGLE_CODE = 0;
    NavController navController;
    TextView signIn, register;
    ViewPager viewPager;
    TabLayout tabLayout;
    private FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    RegistrationActivity registrationActivity;
    CallbackManager mCallbackManager;
    ConstraintLayout continueGoogle;
    LoginButton continueFacebook;
    TextView switchLanguage;
    String lang = "en";
    CheckBox signUpCheckBox;

    public HomeRegistrationFragment() {
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
        return inflater.inflate(R.layout.fragment_home_registration, container, false);
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
        initSlider();
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        signIn = view.findViewById(R.id.sign_in_btn);
        register = view.findViewById(R.id.register_btn);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabDots);
        continueGoogle = view.findViewById(R.id.continue_google);
        continueFacebook = view.findViewById(R.id.facebook_login);
        switchLanguage = view.findViewById(R.id.switch_to_arabic);
        signUpCheckBox = view.findViewById(R.id.sign_up_check_box);

        lang = AppDefs.language;

        mCallbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(registrationActivity, gso);
    }

    private void onClick(){
        register.setOnClickListener(view -> navController.navigate(HomeRegistrationFragmentDirections.actionHomeRegistrationFragmentToSignUpFragment()));
        signIn.setOnClickListener(view -> navController.navigate(HomeRegistrationFragmentDirections.actionHomeRegistrationFragmentToSignInFragment()));
        continueGoogle.setOnClickListener(view -> {
            if (signUpCheckBox.isChecked()){
                signUpGoogle();
            }else {
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.check_terms));
            }
        });

        signUpCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    continueFacebook.setEnabled(true);
//                    continueFacebook.setBackgroundColor(getResources().getColor(R.color.white));
                    continueGoogle.setEnabled(true);
//                    continueGoogle.setBackground(registrationActivity.getResources().getDrawable(R.drawable.rounded_20_white));
                }else {
                    continueFacebook.setEnabled(false);
//                    continueFacebook.setBackgroundColor(getResources().getColor(R.color.gray_3));
                    continueGoogle.setEnabled(false);
//                    continueGoogle.setBackground(registrationActivity.getResources().getDrawable(R.drawable.rounded_gray));
                }
            }
        });

//        if (signUpCheckBox.isChecked()){
//            continueFacebook.setEnabled(true);
//            continueFacebook.setBackgroundColor(getResources().getColor(R.color.white));
//            continueGoogle.setEnabled(true);
//            continueGoogle.setBackground(registrationActivity.getResources().getDrawable(R.drawable.rounded_20_white));
//        }else {
//            continueFacebook.setEnabled(false);
//            continueFacebook.setBackgroundColor(getResources().getColor(R.color.gray_3));
//            continueGoogle.setEnabled(false);
//            continueGoogle.setBackground(registrationActivity.getResources().getDrawable(R.drawable.rounded_gray));
//        }

        continueFacebook.setOnClickListener(view -> {
            if (signUpCheckBox.isChecked()){
                continueFacebook.setReadPermissions("email", "public_profile");
                continueFacebook.setFragment(this);
                continueFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Token", String.valueOf(loginResult.getAccessToken().getToken()));
                        facebookSignIn(loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
            }else {
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.sign_up), registrationActivity.getResources().getString(R.string.check_terms));
            }
        });

        switchLanguage.setOnClickListener(view -> {
            if (lang.equals("en")){
                AppDefs.language = "ar" ;
            }else {
                AppDefs.language = "en" ;
            }
            LocaleHelper.setAppLocale(AppDefs.language, registrationActivity);
            Intent registrationIntent = new Intent(registrationActivity, RegistrationActivity.class);
            startActivity(registrationIntent);
            registrationActivity.finish();

        });
    }

    private void initSlider(){
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager, true);
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(this);
        mAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000,viewPager, mAdapter);
    }

//    public void registrationPopUp(){
//        View registrationAlertView = LayoutInflater.from(getContext()).inflate(R.layout.registration_pop_up, null);
//        AlertDialog registrationAlertBuilder = new AlertDialog.Builder(getContext()).setView(registrationAlertView).show();
//        registrationAlertBuilder.show();
//
//        registrationAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        MaterialButton continueEmail = registrationAlertBuilder.findViewById(R.id.continue_email);
//        LinearLayout continueGoogle = registrationAlertBuilder.findViewById(R.id.continue_google);
//        LoginButton continueFacebook = registrationAlertBuilder.findViewById(R.id.facebook_login);
//
//        continueEmail.setOnClickListener(view -> {
//
//            registrationAlertBuilder.dismiss();
//        });
//
//        continueGoogle.setOnClickListener(view -> {
//            signUpGoogle();
//            registrationAlertBuilder.dismiss();
//        });
//
//        continueFacebook.setReadPermissions("email", "public_profile");
//        continueFacebook.setFragment(this);
//        continueFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d("Token", String.valueOf(loginResult.getAccessToken().getToken()));
//                facebookSignIn(loginResult.getAccessToken().getToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//            }
//        });
//    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(registrationActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                    // ...
                });
    }

    private void signUpGoogle(){
        Intent googleIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleIntent, GOOGLE_CODE);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            googleSignIn(account.getIdToken());
            Log.d("googleToken", account.getIdToken());
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(registrationActivity, registrationActivity.getResources().getString(R.string.google_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void googleSignIn(String tokenId){
        registrationActivity.showProgressDialog(getResources().getString(R.string.signing_in));
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("token", tokenId);
            Timber.tag("userObject").d(String.valueOf(userObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest signInRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"GmailLogin", userObject, response -> {
            registrationActivity.hideProgressDialog();
            try {
                AppDefs.user.setToken(response.getString("token"));
                AppDefs.token = AppDefs.user.getToken();
                AppDefs.user.setId(response.getString("userId"));
                saveUserToSharedPreferences();
                Intent mainIntent = new Intent(registrationActivity, MainActivity.class);
                startActivity(mainIntent);
                registrationActivity.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Timber.tag("error").d(error);
            registrationActivity.hideProgressDialog();
            if (error instanceof ClientError){
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.wrong_user));
            }else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.error_network_timeout));
            }
        });
        signInRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        registrationActivity.queue.add(signInRequest);
    }

    private void facebookSignIn(String tokenId){
        registrationActivity.showProgressDialog(getResources().getString(R.string.signing_in));
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("token", tokenId);
            Timber.tag("userObject").d(String.valueOf(userObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest signInRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"FacebookLogin", userObject, response -> {
            registrationActivity.hideProgressDialog();
            try {
                AppDefs.user.setToken(response.getString("token"));
                AppDefs.token = AppDefs.user.getToken();
                AppDefs.user.setId(response.getString("userId"));
                saveUserToSharedPreferences();
                Intent mainIntent = new Intent(registrationActivity, MainActivity.class);
                startActivity(mainIntent);
                registrationActivity.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Timber.tag("error").d(error);
            registrationActivity.hideProgressDialog();
            if (error instanceof ClientError){
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.wrong_user));
            }else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.error_network_timeout));
            }
        });
        signInRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        registrationActivity.queue.add(signInRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GOOGLE_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveUserToSharedPreferences(){
        SharedPreferences sharedPreferences = registrationActivity.getSharedPreferences(AppDefs.SHARED_PREF_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(AppDefs.ID_KEY, AppDefs.user.getId());
        editor.putString(AppDefs.TOKEN_KEY, AppDefs.user.getToken());

        editor.apply();
    }
}