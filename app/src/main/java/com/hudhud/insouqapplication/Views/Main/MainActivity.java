package com.hudhud.insouqapplication.Views.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    public static String fragName = "";
    ProgressDialog pDialog;
    public RequestQueue queue ;
    public DecimalFormat formatter = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getStringExtra("fragName") != null) {
            fragName = getIntent().getStringExtra("fragName");
        }
        pDialog = new ProgressDialog(this);
        queue =  Volley.newRequestQueue(this);

        getUserById();
    }

    private void getUserById(){
        showProgressDialog(getResources().getString(R.string.loading));
        StringRequest getUser = new StringRequest(Request.Method.GET, Urls.Users_URL+"GetById?id="+ AppDefs.user.getId(), response -> {
            hideProgressDialog();
            try {
                JSONObject userObject = new JSONObject(response);
                AppDefs.user.setId(userObject.getString("id"));
                AppDefs.user.setFirstName(userObject.getString("firstName"));
                AppDefs.user.setLastName(userObject.getString("lastName"));
                AppDefs.user.setEmail(userObject.getString("email"));
                AppDefs.user.setMobileNumber(userObject.getString("mobileNumber"));
                if (userObject.getString("gender").equals("null")){
                    AppDefs.user.setGender("");
                }else {
                    AppDefs.user.setGender(userObject.getString("gender"));
                }
                if(userObject.getString("dob").equals("null")){
                    AppDefs.user.setDob("");
                }else {
                    AppDefs.user.setDob(userObject.getString("dob"));
                }
                if (userObject.getString("nationality").equals("null")){
                    AppDefs.user.setNationality("");
                }else {
                    AppDefs.user.setNationality(userObject.getString("nationality"));
                }
                if(userObject.getString("defaultLocation").equals("null")){
                    AppDefs.user.setDefaultLocation("");
                }else {
                    AppDefs.user.setDefaultLocation(userObject.getString("defaultLocation"));
                }
                if(userObject.getString("defaultLanguage").equals("null")){
                    AppDefs.user.setDefaultLanguage("");
                }else {
                    AppDefs.user.setDefaultLanguage(userObject.getString("defaultLanguage"));
                }
                if(userObject.getString("careerLevel").equals("null")){
                    AppDefs.user.setCareerLevel("");
                }else {
                    AppDefs.user.setCareerLevel(userObject.getString("careerLevel"));
                }
                if(userObject.getString("education").equals("null")){
                    AppDefs.user.setEducation("");
                }else {
                    AppDefs.user.setEducation(userObject.getString("education"));
                }
                if(userObject.getString("currentLocation").equals("null")){
                    AppDefs.user.setCurrentLocation("");
                }else {
                    AppDefs.user.setCurrentLocation(userObject.getString("currentLocation"));
                }
                if(userObject.getString("currentPosition").equals("null")){
                    AppDefs.user.setCurrentPosition("");
                }else {
                    AppDefs.user.setCurrentPosition(userObject.getString("currentPosition"));
                }
                if(userObject.getString("currentCompany").equals("null")){
                    AppDefs.user.setCurrentCompany("");
                }else {
                    AppDefs.user.setCurrentCompany(userObject.getString("currentCompany"));
                }
                if(userObject.getString("cv").equals("null")){
                    AppDefs.user.setCv("");
                }else {
                    AppDefs.user.setCv(userObject.getString("cv"));
                }
                if(userObject.getString("coverNote").equals("null")){
                    AppDefs.user.setCoverNote("");
                }else {
                    AppDefs.user.setCoverNote(userObject.getString("coverNote"));
                }
                if(userObject.getString("profilePicture").equals("null")){
                    AppDefs.user.setProfilePicture("");
                }else {
                    AppDefs.user.setProfilePicture(userObject.getString("profilePicture"));
                }
                if(userObject.getString("industry").equals("null")){
                    AppDefs.user.setIndustry("");
                }else {
                    AppDefs.user.setIndustry(userObject.getString("industry"));
                }
                if(userObject.getString("city").equals("null")){
                    AppDefs.user.setCity("");
                }else {
                    AppDefs.user.setCity(userObject.getString("city"));
                }
                if(userObject.getString("hideInfromation").equals("null")){
                    AppDefs.user.setHideInformation("");
                }else {
                    AppDefs.user.setHideInformation(userObject.getString("hideInfromation"));
                }
                if(userObject.getString("externalLogin").equals("null")){
                    AppDefs.user.setExternalLogin("");
                }else {
                    AppDefs.user.setExternalLogin(userObject.getString("externalLogin"));
                }
                if(userObject.getString("memberSince").equals("null")){
                    AppDefs.user.setMemberSince("");
                }else {
                    AppDefs.user.setMemberSince(userObject.getString("memberSince"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            hideProgressDialog();
            if (error instanceof ClientError){
                showResponseMessage(getResources().getString(R.string.user), getResources().getString(R.string.wrong_id));
            }
        });
        queue.add(getUser);
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
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void hideProgressDialog() {
        pDialog.cancel();
    }

    public String convertDate(String time){
        String convTime = "";
         SimpleDateFormat parse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
         SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        try {
            Date before = parse.parse(time);
            convTime = format.format(before);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        String prefix = "";
//        String suffix = "Ago";
//
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            Date pasTime = dateFormat.parse(time);
//
//            Date nowTime = new Date();
//
//            long dateDiff = nowTime.getTime() - pasTime.getTime();
//
//            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
//            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
//            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
//            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);
//
//            if (second < 60) {
//                convTime = second + " Seconds " + suffix;
//            } else if (minute < 60) {
//                convTime = minute + " Minutes "+suffix;
//            } else if (hour < 24) {
//                convTime = hour + " Hours "+suffix;
//            } else if (day >= 7) {
//                if (day > 360) {
//                    convTime = (day / 360) + " Years " + suffix;
//                } else if (day > 30) {
//                    convTime = (day / 30) + " Months " + suffix;
//                } else {
//                    convTime = (day / 7) + " Week " + suffix;
//                }
//            } else if (day < 7) {
//                convTime = day+" Days "+suffix;
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.e("ConvTimeE", e.getMessage());
//        }

        return convTime;
    }

    public static RequestBody createRequestBody(String parameter){
        return RequestBody.create(MediaType.parse("multipart/form-data"), parameter);
    }

    public static MultipartBody.Part createMultipartBodyPart(String name, String imagePath){
        if (imagePath == null || !new File(imagePath).exists()){
            return null;
        }

        File imageFile = new File(imagePath);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

        // MultipartBody.Part is used to send also the actual file name
        try {
            return MultipartBody.Part.createFormData(name, URLEncoder.encode(imageFile.getName(), "utf-8"), requestBody);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String fileUriToBase64(Uri uri, ContentResolver resolver) {
        String encodedBase64 = "";
        try {
            byte[] bytes = readBytes(uri, resolver);
            encodedBase64 = Base64.encodeToString(bytes, 0);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return encodedBase64;
    }

    private static byte[] readBytes(Uri uri, ContentResolver resolver)
            throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = resolver.openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the
        // byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }
}