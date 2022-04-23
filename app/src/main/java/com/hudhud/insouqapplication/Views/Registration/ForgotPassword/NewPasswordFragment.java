package com.hudhud.insouqapplication.Views.Registration.ForgotPassword;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class NewPasswordFragment extends Fragment {

    NavController navController;
    MaterialButton changePasswordBtn;
    RegistrationActivity registrationActivity;
    ConstraintLayout layout;
    ImageView backToPrevious;
    EditText newPassEdt, confirmNewPassEdt;
    String resetToken = "", phoneNum = "", newPassword = "", confirmNewPassword = "";

    public NewPasswordFragment() {
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
        return inflater.inflate(R.layout.fragment_new_password, container, false);
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
            resetToken = NewPasswordFragmentArgs.fromBundle(getArguments()).getResetPasswordToken();
            phoneNum = NewPasswordFragmentArgs.fromBundle(getArguments()).getPhoneNum();
        }
        layout = view.findViewById(R.id.layout);
        newPassEdt = view.findViewById(R.id.new_password);
        confirmNewPassEdt = view.findViewById(R.id.confirm_new_password);
        backToPrevious = view.findViewById(R.id.back_arrow);
        changePasswordBtn = view.findViewById(R.id.change_password_btn);
    }

    private void onClick(){
        changePasswordBtn.setOnClickListener(view -> {
            newPassword = String.valueOf(newPassEdt.getText());
            confirmNewPassword = String.valueOf(confirmNewPassEdt.getText());
            if (newPassword.isEmpty() || confirmNewPassword.isEmpty()){
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.forgot_password), registrationActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                resetPassword();
            }
        });

        backToPrevious.setOnClickListener(view -> navController.popBackStack());

        layout.setOnClickListener(view -> registrationActivity.hideKeyboard());
    }

    private void resetPassword(){
        registrationActivity.showProgressDialog(registrationActivity.getResources().getString(R.string.loading));
        JSONObject resetPassObject = new JSONObject();
        try {
            resetPassObject.put("mobileNumber", phoneNum);
            resetPassObject.put("resetPasswordToken", resetToken);
            resetPassObject.put("newPassword", newPassword);
            resetPassObject.put("confirmNewPassword", confirmNewPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest resetPassRequest = new JsonObjectRequest(Request.Method.POST, Urls.Account_URL+"ResetPasswordMobile", resetPassObject, response -> {
            registrationActivity.hideProgressDialog();
            try {
                if (response.getString("isSuccess").equals("true")){
                    Toast.makeText(registrationActivity, registrationActivity.getResources().getString(R.string.new_password_set), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registrationActivity, MainActivity.class);
                    startActivity(intent);
                    registrationActivity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.forgot_password), registrationActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            registrationActivity.hideProgressDialog();
            registrationActivity.showResponseMessage(registrationActivity.getResources().getString(R.string.forgot_password), registrationActivity.getResources().getString(R.string.internet_connection_error));
        });
        registrationActivity.queue.add(resetPassRequest);
    }

    private void saveUserToSharedPreferences(){
        SharedPreferences sharedPreferences = registrationActivity.getSharedPreferences(AppDefs.SHARED_PREF_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(AppDefs.ID_KEY, AppDefs.user.getId());
        editor.putString(AppDefs.TOKEN_KEY, AppDefs.user.getToken());

        editor.apply();
    }
}