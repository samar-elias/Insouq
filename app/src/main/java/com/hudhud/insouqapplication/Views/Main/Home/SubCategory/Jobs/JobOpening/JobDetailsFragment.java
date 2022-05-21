package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.MotorDetailsAdapter;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JobDetailsFragment extends Fragment {

    ImageView  home, profile, chat, sellItem, notifications;
    MainActivity mainActivity;
    NavController navController;
    boolean isApplied = false, isNew = false;
    RecyclerView jobsRV;
    int position;
    String profileImg = "", profileName = "", memberSince = "";

    public JobDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_job_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        setSimilarJobs();
        setAdapter();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View view){
        if (getArguments() != null){
            position = JobDetailsFragmentArgs.fromBundle(getArguments()).getPosition();
            isNew = JobDetailsFragmentArgs.fromBundle(getArguments()).getIsNew();
        }
        navController = Navigation.findNavController(view);
        jobsRV = view.findViewById(R.id.job_RV);

        //bottom nav bar
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
    }

    private void onClick(){


        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));


    }

    public void scrollToPosition(int position){
        jobsRV.scrollToPosition(position);
    }

    public void getUserInfo(String userId, ImageView img, TextView name, TextView members){
        StringRequest userInfoRequest = new StringRequest(Request.Method.GET, Urls.Users_URL+"GetById?id="+userId, response -> {
            try {
                JSONObject userObj = new JSONObject(response);
                profileName = userObj.getString("firstName")+" "+userObj.getString("lastName");
                profileImg = userObj.getString("profilePicture");
                String member = userObj.getString("memberSince");
                memberSince = member.substring(0, member.indexOf("T"));
                members.setText(mainActivity.getResources().getString(R.string.member_since)+" "+memberSince);
                name.setText(profileName);
                String img2 = profileImg.replace("\\", "/");
                Glide.with(mainActivity).load(Urls.IMAGE_URL2+img2).into(img);

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userInfoRequest);
    }

    private void setAdapter(){
        if (isNew){
            JobDetailsAdapter motorDetailsAdapter = new JobDetailsAdapter(AppDefs.newOpeningJobAds, this);
            jobsRV.setAdapter(motorDetailsAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            jobsRV.setLayoutManager(manager);
            jobsRV.scrollToPosition(position);
        }else {
            JobDetailsAdapter motorDetailsAdapter = new JobDetailsAdapter(AppDefs.jobAds, this);
            jobsRV.setAdapter(motorDetailsAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            jobsRV.setLayoutManager(manager);
            jobsRV.scrollToPosition(position);
        }
    }

    public void navigateToApply(String adId){
        navController.navigate(JobDetailsFragmentDirections.actionJobDetailsFragment2ToApplyForJobFragment(adId));
    }

    public void call(String call){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+call));
        startActivity(intent);
    }

    public void sendSMS(String phone){
        Uri uri = Uri.parse("smsto:"+phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(intent);
    }

    public void openGoogleMaps(String latitude, String longitude){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(latitude),Float.parseFloat(longitude));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public boolean getOfferApplied(String adId){
        StringRequest offerAppliedRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"IsJobApplicationApplied?adId="+adId, response -> {
            try {
                JSONObject offerApplied = new JSONObject(response);
                isApplied = offerApplied.getBoolean("result");

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.make_an_offer), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.make_an_offer), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(offerAppliedRequest);
        return isApplied;
    }

    public void addToFavourite(String adId){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
            favObj.put("UserId", AppDefs.user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"AddToFavorite", favObj, response -> {
            mainActivity.hideProgressDialog();
//            setData();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_success), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }
    public void removeFromFavourite(String adId){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"RemoveFromFavorite", favObj, response -> {
            mainActivity.hideProgressDialog();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_remove), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }

    public void backToPrevious(){
        navController.popBackStack();
    }

    private void setSimilarJobs(){
//        JobsFeaturedAdapter jobsFeaturedAdapter = new JobsFeaturedAdapter();
//        similarJobsRV.setAdapter(jobsFeaturedAdapter);
//        similarJobsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    public void share(String adId){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,adId);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }

    public void setReportAd(String adId){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.report_ad_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.continue_btn);
        EditText reasonEdt = myAdsOptionsAlertView.findViewById(R.id.reason_edt);
        EditText descriptionEdt = myAdsOptionsAlertView.findViewById(R.id.description_edt);
        done.setOnClickListener(view -> {
            String reason = String.valueOf(reasonEdt.getText());
            String desc = String.valueOf(descriptionEdt.getText());
            if (reason.isEmpty() || desc.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.report_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                reportAd(adId, reason, desc);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void reportAd(String adId, String reason, String desc){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject obj = new JSONObject();
        try {
            obj.put("reason", reason);
            obj.put("description", desc);
            obj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"ReportAd", obj, response -> {
            mainActivity.hideProgressDialog();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.reported), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.report_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }
}