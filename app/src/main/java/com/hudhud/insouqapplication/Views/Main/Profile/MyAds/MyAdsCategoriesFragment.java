package com.hudhud.insouqapplication.Views.Main.Profile.MyAds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdsCategoriesFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    ConstraintLayout motors, jobs, services, business, classifieds, numbers, electronics;
    TextView motorsCount, jobsCount, servicesCount, businessCount, classifiedsCount, numbersCount, electronicsCount;


    public MyAdsCategoriesFragment() {
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
        return inflater.inflate(R.layout.fragment_my_ads_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getCount();
//        getUsedCarsAds();
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

    private void getCount(){
        StringRequest getCountsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetUserAdsCount?userId="+ AppDefs.user.getId(), response -> {
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
        });
        mainActivity.queue.add(getCountsRequest);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

//        motors.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("1")));
//        jobs.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("3")));
//        services.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("4")));
//        business.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("5")));
//        classifieds.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("6")));
//        numbers.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("7")));
//        electronics.setOnClickListener(view -> navController.navigate(MyAdsCategoriesFragmentDirections.actionMyAdsCategoriesFragmentToMyAdsFragment("8")));

    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}