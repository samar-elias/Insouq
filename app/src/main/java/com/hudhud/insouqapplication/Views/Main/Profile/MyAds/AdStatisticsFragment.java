package com.hudhud.insouqapplication.Views.Main.Profile.MyAds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdStatisticsFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list, filter, closeFilter;
    LinearLayout filterStatistics;
    MainActivity mainActivity;
    TextView searchNum, visitorsNum, chatNum, contactNum;
    String adId = "";
    int search = 0, visitors = 0, chats = 0, contact = 0;
    RadioButton all, today, week, month;
    MaterialButton filterResults;

    public AdStatisticsFragment() {
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
        return inflater.inflate(R.layout.fragment_ad_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getStatistics("1");
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

        if (getArguments() != null){
            adId = AdStatisticsFragmentArgs.fromBundle(getArguments()).getAdId();
        }

        backToPrevious = view.findViewById(R.id.back_arrow);
        filter = view.findViewById(R.id.filter_statistics);
        closeFilter = view.findViewById(R.id.close_filter_statistics);
        filterStatistics = view.findViewById(R.id.ad_statistics_filter);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);
        searchNum = view.findViewById(R.id.number1);
        visitorsNum = view.findViewById(R.id.number2);
        chatNum = view.findViewById(R.id.number3);
        contactNum = view.findViewById(R.id.number4);
        all = view.findViewById(R.id.all);
        today = view.findViewById(R.id.today);
        week = view.findViewById(R.id.week);
        month = view.findViewById(R.id.month);
        filterResults = view.findViewById(R.id.filter_results);

        all.setChecked(true);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        filter.setOnClickListener(view -> openFilters(filterStatistics));
        closeFilter.setOnClickListener(view -> closeFilter(filterStatistics));

        filterResults.setOnClickListener(view -> {
            if (all.isChecked()){
                getStatistics("1");
                closeFilter(filterStatistics);
            }
            if (today.isChecked()){
                getStatistics("2");
                closeFilter(filterStatistics);
            }
            if (week.isChecked()){
                getStatistics("3");
                closeFilter(filterStatistics);
            }
            if (month.isChecked()){
                getStatistics("4");
                closeFilter(filterStatistics);
            }
        });
    }

    private void getStatistics(String period){
        search = 0;
        visitors = 0;
        chats = 0;
        contact = 0;
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest statisticsRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetAdStatisticsByAdId?adId="+adId+"&period="+period, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray statisticsArray = new JSONArray(response);
                for (int i=0; i<statisticsArray.length(); i++){
                    JSONObject statisticObj = statisticsArray.getJSONObject(i);
                    switch (statisticObj.getString("type")){
                        case "1":
                            chats++;
                            break;
                        case "2":
                            visitors++;
                            break;
                        case "3":
                            contact++;
                            break;
                        case "4":
                            search++;
                            break;
                    }
                }
                searchNum.setText(String.valueOf(search));
                chatNum.setText(String.valueOf(chats));
                visitorsNum.setText(String.valueOf(visitors));
                contactNum.setText(String.valueOf(contact));
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.ad_statistics), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.ad_statistics), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(statisticsRequest);
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    private void openFilters(LinearLayout filterLayout){
        filterLayout.setVisibility(View.VISIBLE);
        TranslateAnimation animation = new TranslateAnimation(
                filterLayout.getWidth(),0, 0,0
        );
        int shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
        animation.setDuration(shortAnimationDuration);
        filterLayout.setAnimation(animation);
    }

    private void closeFilter(LinearLayout filterLayout){
        filterLayout.setVisibility(View.GONE);
        TranslateAnimation animation = new TranslateAnimation(
                0,filterLayout.getWidth(), 0,0
        );
        int shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
        animation.setDuration(shortAnimationDuration);
        filterLayout.setAnimation(animation);
    }
}