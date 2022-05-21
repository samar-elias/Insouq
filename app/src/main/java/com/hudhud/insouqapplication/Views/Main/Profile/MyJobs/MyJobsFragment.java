package com.hudhud.insouqapplication.Views.Main.Profile.MyJobs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.Profile.MyAds.MyAdsFragmentDirections;

public class MyJobsFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list, options;
    MainActivity mainActivity;
    MaterialButton companyProfile;
    TextView moreViews;

    public MyJobsFragment() {
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
        return inflater.inflate(R.layout.fragment_my_jobs, container, false);
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
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);
        options = view.findViewById(R.id.options);
        companyProfile = view.findViewById(R.id.company_profile);
        moreViews = view.findViewById(R.id.get_views);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        options.setOnClickListener(view -> showAdOptions());
        moreViews.setOnClickListener(view -> navController.navigate(MyJobsFragmentDirections.actionMyJobsFragmentToMoreViewsFragment("", "")));
        companyProfile.setOnClickListener(view -> navController.navigate(MyJobsFragmentDirections.actionMyJobsFragmentToCompanyProfileFragment()));
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    public void showAdOptions(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.my_ad_options_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout statistics = myAdsOptionsAlertView.findViewById(R.id.statistics);
        statistics.setOnClickListener(view -> {
//            navController.navigate(MyJobsFragmentDirections.actionMyJobsFragmentToAdStatisticsFragment());
            myAdsOptionsAlertBuilder.dismiss();
        });
    }
}