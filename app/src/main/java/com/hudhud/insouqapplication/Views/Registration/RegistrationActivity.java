package com.hudhud.insouqapplication.Views.Registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

public class RegistrationActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    public RequestQueue queue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pDialog = new ProgressDialog(this);
        queue =  Volley.newRequestQueue(this);

    }

    public void showResponseMessage(String title, String msg) {
        AlertDialog.Builder msgDialog = new AlertDialog.Builder(this);
        msgDialog.setTitle(title);
        msgDialog.setMessage(msg);

        msgDialog.setPositiveButton(getResources().getString(R.string.okay), (dialogInterface, i) -> dialogInterface.dismiss());
        msgDialog.show();
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showProgressDialog(String msg) {
        pDialog.setMessage(msg);
        pDialog.show();
    }

    public void hideProgressDialog() {
        pDialog.cancel();
    }


}