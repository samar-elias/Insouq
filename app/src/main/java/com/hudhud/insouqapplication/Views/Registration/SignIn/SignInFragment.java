package com.hudhud.insouqapplication.Views.Registration.SignIn;

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

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

public class SignInFragment extends Fragment {

    private static final int GOOGLE_CODE = 0;
    MaterialButton signIn;
    NavController navController;
    EditText emailOrPhoneEdt, passwordEdt;
    TextView forgetPassword, createAccount;
    RegistrationActivity registrationActivity;
    String emailOrPhone = "", password = "";
    ImageView showHidePassword;
    Boolean passwordVisible = false;
    ConstraintLayout layout;
    LinearLayout googleSignIn;
    GoogleSignInClient googleSignInClient;

    public SignInFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
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
        signIn = view.findViewById(R.id.sign_up_btn);
        emailOrPhoneEdt = view.findViewById(R.id.email_address_edt);
        passwordEdt = view.findViewById(R.id.password_edt);
        passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        forgetPassword = view.findViewById(R.id.forgot_password);
        createAccount = view.findViewById(R.id.create_account);
        showHidePassword = view.findViewById(R.id.show_hide_password);
        layout = view.findViewById(R.id.layout);
        googleSignIn = view.findViewById(R.id.google_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(registrationActivity, gso);

//        //Testing Login
//        emailOrPhoneEdt.setText("samarelias3@gmail.com");
//        passwordEdt.setText("Samar@123");
    }

    private void onClick(){
        signIn.setOnClickListener(v -> {
            emailOrPhone = String.valueOf(emailOrPhoneEdt.getText());
            password = String.valueOf(passwordEdt.getText());
            if (!emailOrPhone.isEmpty() && !password.isEmpty()){
                signIn();
            }else {
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.fill_all_fields));
            }
        });

        showHidePassword.setOnClickListener(view -> {
            if (passwordVisible){
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordVisible = false;
            }else {
                passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                passwordVisible = true;
            }
        });

        layout.setOnClickListener(view -> registrationActivity.hideKeyboard());

        googleSignIn.setOnClickListener(view -> signUpGoogle());

        forgetPassword.setOnClickListener(view -> navController.navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment()));

        createAccount.setOnClickListener(view -> navController.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()));
    }

    private void signIn(){
        registrationActivity.showProgressDialog(getResources().getString(R.string.signing_in));
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("emailOrPhone", emailOrPhone);
            userObject.put("password", password);
            Timber.tag("userObject").d(String.valueOf(userObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest signInRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"login", userObject, response -> {
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
                registrationActivity.showResponseMessage(getResources().getString(R.string.sign_up), getResources().getString(R.string.error_network_timeout));
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

    private void signIn(String tokenId){
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
        signInRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        registrationActivity.queue.add(signInRequest);
    }

    private void signUpGoogle(){
        Intent googleIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleIntent, GOOGLE_CODE);
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            signIn(account.getIdToken());
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(registrationActivity, registrationActivity.getResources().getString(R.string.google_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
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