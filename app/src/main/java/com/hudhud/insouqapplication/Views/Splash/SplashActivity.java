package com.hudhud.insouqapplication.Views.Splash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.LocaleHelper;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                getUserFromSharedPreferences();
            }
        }, 1000);
    }

    @SuppressLint("NewApi")
    private void getUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(AppDefs.SHARED_PREF_KEY, MODE_PRIVATE);
        String id = sharedPreferences.getString(AppDefs.ID_KEY, null);
        String token = sharedPreferences.getString(AppDefs.TOKEN_KEY, null);

        String language = sharedPreferences.getString(AppDefs.LANGUAGE_KEY, null);
        if (language != null) {
            if (language.equals("ar")) {
                AppDefs.language = "ar";
                LocaleHelper.setAppLocale("ar", this);
            } else {
                AppDefs.language = "en";
                LocaleHelper.setAppLocale("en", this);
            }
        }else {
            AppDefs.language = AppDefs.getLanguage();
            LocaleHelper.setAppLocale(AppDefs.language, this);
        }

        if (id!=null){
            AppDefs.user.setId(sharedPreferences.getString(AppDefs.ID_KEY, null));
            AppDefs.user.setToken(sharedPreferences.getString(AppDefs.TOKEN_KEY, null));

            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }else {
            Intent registrationIntent = new Intent(SplashActivity.this, RegistrationActivity.class);
            startActivity(registrationIntent);
        }
        finish();
    }
}