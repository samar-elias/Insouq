package com.hudhud.insouqapplication.Views.Main.Profile.MySavedSearches;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.SavedSearchAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragmentDirections;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MySavedSearchesFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    RecyclerView mySearchesRV;
    MainActivity mainActivity;
    TextView noAds;
    ConstraintLayout motors, jobs, services, business, classifieds, numbers, electronics;
    TextView motorsCount, jobsCount, servicesCount, businessCount, classifiedsCount, numbersCount, electronicsCount;
    ArrayList<SavedSearchAd> savedSearchAds = new ArrayList<>();

    public MySavedSearchesFragment() {
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
        return inflater.inflate(R.layout.fragment_my_saved_searches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getSavedSearchCount();
//        getSavedSearch("1");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);
        mySearchesRV = view.findViewById(R.id.my_searches_RV);

        AppDefs.isSaved = false;
        noAds = view.findViewById(R.id.no_ads);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        motors = view.findViewById(R.id.motors);
        jobs = view.findViewById(R.id.jobs);
        services = view.findViewById(R.id.services);
        business = view.findViewById(R.id.business);
        classifieds = view.findViewById(R.id.classifieds);
        numbers = view.findViewById(R.id.numbers);
        electronics = view.findViewById(R.id.electronics);

        motorsCount = view.findViewById(R.id.motor_count);
        jobsCount = view.findViewById(R.id.job_count);
        servicesCount = view.findViewById(R.id.services_count);
        businessCount = view.findViewById(R.id.business_count);
        classifiedsCount = view.findViewById(R.id.classifieds_count);
        numbersCount = view.findViewById(R.id.numbers_count);
        electronicsCount = view.findViewById(R.id.electronics_count);

    }

    private void getSavedSearchCount(){
        StringRequest getCountsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetSavedSearchCount", response -> {
            try {
                JSONArray counts = new JSONArray(response);
                for (int i=0; i<counts.length(); i++){
                    JSONObject obj = counts.getJSONObject(i);
                    switch (i){
                        case 0:
                            motorsCount.setText(obj.getString("count"));
                            break;
                        case 2:
                            jobsCount.setText(obj.getString("count"));
                            break;
                        case 3:
                            servicesCount.setText(obj.getString("count"));
                            break;
                        case 4:
                            businessCount.setText(obj.getString("count"));
                            break;
                        case 5:
                            classifiedsCount.setText(obj.getString("count"));
                            break;
                        case 6:
                            numbersCount.setText(obj.getString("count"));
                            break;
                        case 7:
                            electronicsCount.setText(obj.getString("count"));
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.save_search), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.save_search), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(getCountsRequest);
    }

    private void getSavedSearch(String typeId){
        savedSearchAds.clear();
        StringRequest saveSearchRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetSavedSearchesList?typeId="+typeId, response -> {
            try {
                JSONArray saveSearchesArray = new JSONArray(response);
                for (int i=0; i<saveSearchesArray.length(); i++){
                    JSONObject obj  = saveSearchesArray.getJSONObject(i);
                    SavedSearchAd savedSearchAd = new SavedSearchAd();
                    savedSearchAd.setId(obj.getString("id"));
                    savedSearchAd.setAlert(obj.getString("alert"));
                    savedSearchAd.setEmailAlert(obj.getString("emailAlert"));
                    savedSearchAd.setLocation(obj.getString("location"));
                    savedSearchAd.setTypeId(obj.getString("adTypeId"));
                    savedSearchAd.setCategoryId(obj.getString("adCategoryId"));
                    savedSearchAd.setDate(obj.getString("date"));
                    JSONObject adObj = obj.getJSONObject("adCategory");
                    savedSearchAd.setCategoryAr(adObj.getString("ar_Name"));
                    savedSearchAd.setCategoryEn(adObj.getString("en_Name"));
                    JSONObject typeObj = obj.getJSONObject("adType");
                    savedSearchAd.setTypeAr(typeObj.getString("ar_Name"));
                    savedSearchAd.setTypeEn(typeObj.getString("en_Name"));
                    savedSearchAds.add(savedSearchAd);
                }
                setAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.save_search), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.save_search), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(saveSearchRequest);
    }

    private void setAdapter(){
        MySavedSearchesAdapter mySavedSearchesAdapter = new MySavedSearchesAdapter(this, savedSearchAds);
        mySearchesRV.setAdapter(mySavedSearchesAdapter);
        mySearchesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        if (savedSearchAds.size()>0){
            noAds.setVisibility(View.GONE);
        }else {
            noAds.setVisibility(View.VISIBLE);
        }
    }

    public void deleteAd(String id, String typeId){
        JSONObject obj = new JSONObject();
        try {
            obj.put("searchId", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest deleteSearchRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"DeleteSavedSearch", obj, response -> {
            getSavedSearch(typeId);
            getSavedSearchCount();
        }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(deleteSearchRequest);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        motors.setOnClickListener(view -> getSavedSearch("1"));
        jobs.setOnClickListener(view -> getSavedSearch("3"));
        services.setOnClickListener(view -> getSavedSearch("4"));
        business.setOnClickListener(view -> getSavedSearch("5"));
        classifieds.setOnClickListener(view -> getSavedSearch("6"));
        numbers.setOnClickListener(view -> getSavedSearch("7"));
        electronics.setOnClickListener(view -> getSavedSearch("8"));
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void navigateToSubCategory(String categoryName, int position, String categoryId){
        navController.navigate(MySavedSearchesFragmentDirections.actionMySavedSearchesFragmentToSubCategoryFragment2(categoryName, position, categoryId));
    }
}