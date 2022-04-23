package com.hudhud.insouqapplication.Views.Main.SellingItems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.SellingItems.SellAnItemFragmentDirections;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellAnItemFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    ConstraintLayout motors, property, jobs, services, business, classifieds, numbers, electronics;
    CircleImageView motorsBackground, propertyBackground, jobsBackground, servicesBackground, businessBackground, classifiedsBackground, numbersBackground, electronicsBackground;
    ImageView motorsIcon, propertyIcon, jobsIcon, servicesIcon, businessIcon, classifiedsIcon, numbersIcon, electronicsIcon;

    public SellAnItemFragment() {
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
        return inflater.inflate(R.layout.fragment_sell_an_item, container, false);
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
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);
        motors = view.findViewById(R.id.motors);
        electronics = view.findViewById(R.id.electronics);
        numbers = view.findViewById(R.id.numbers);
        classifieds = view.findViewById(R.id.classifieds);
        business = view.findViewById(R.id.business);
        services = view.findViewById(R.id.services);
        jobs = view.findViewById(R.id.jobs);
        property = view.findViewById(R.id.property);
        motorsBackground = view.findViewById(R.id.motors_background);
        propertyBackground = view.findViewById(R.id.property_background);
        jobsBackground = view.findViewById(R.id.jobs_background);
        servicesBackground = view.findViewById(R.id.services_background);
        businessBackground = view.findViewById(R.id.business_background);
        classifiedsBackground = view.findViewById(R.id.classifieds_background);
        numbersBackground = view.findViewById(R.id.numbers_background);
        electronicsBackground = view.findViewById(R.id.electronics_background);
        motorsIcon = view.findViewById(R.id.motors_icon);
        propertyIcon = view.findViewById(R.id.property_icon);
        jobsIcon = view.findViewById(R.id.jobs_icon);
        servicesIcon = view.findViewById(R.id.services_icon);
        businessIcon = view.findViewById(R.id.business_icon);
        classifiedsIcon = view.findViewById(R.id.classifieds_icon);
        numbersIcon = view.findViewById(R.id.numbers_icon);
        electronicsIcon = view.findViewById(R.id.electronics_icon);
    }

    @SuppressLint("NewApi")
    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        motors.setOnClickListener(view ->{
            Glide.with(mainActivity).load(R.drawable.foshia_circle).into(motorsBackground);
            Glide.with(mainActivity).load(R.drawable.motors_icon).into(motorsIcon);
            postAd(1);
        } );
        property.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.orange_circle).into(propertyBackground);
            Glide.with(mainActivity).load(R.drawable.property_icon).into(propertyIcon);
            postAd(2);
        });
        jobs.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.peach_circle).into(jobsBackground);
            Glide.with(mainActivity).load(R.drawable.jobs_icon).into(jobsIcon);
            postAd(3);
        });
        services.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.fairouz_circle).into(servicesBackground);
            Glide.with(mainActivity).load(R.drawable.services_icon).into(servicesIcon);
            postAd(4);
        });
        business.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.green_circle).into(businessBackground);
            Glide.with(mainActivity).load(R.drawable.business_icon).into(businessIcon);
            postAd(5);
        });
        classifieds.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.blue_circle).into(classifiedsBackground);
            Glide.with(mainActivity).load(R.drawable.classifieds_icon).into(classifiedsIcon);
            navController.navigate(SellAnItemFragmentDirections.actionSellAnItemFragmentToPostClassifiedsAdFragment());
        });
        numbers.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.purp_circle).into(numbersBackground);
            Glide.with(mainActivity).load(R.drawable.numbers_icon).into(numbersIcon);
            postAd(7);
        });
        electronics.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.yellow_circle).into(electronicsBackground);
            Glide.with(mainActivity).load(R.drawable.electronics_icon).into(electronicsIcon);
            postAd(8);
        });

    }

    private void postAd(int categoryId){
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                navController.navigate(SellAnItemFragmentDirections.actionSellAnItemFragmentToSubCategoriesPostAdFragment(categoryId));
                switch (categoryId){
                    case 1:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(motorsBackground);
                        Glide.with(mainActivity).load(R.drawable.motors_pink).into(motorsIcon);
                        break;
                    case 2:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(propertyBackground);
                        Glide.with(mainActivity).load(R.drawable.property_orange).into(propertyIcon);
                        break;
                    case 3:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(jobsBackground);
                        Glide.with(mainActivity).load(R.drawable.jobs_peach).into(jobsIcon);
                        break;
                    case 4:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(servicesBackground);
                        Glide.with(mainActivity).load(R.drawable.services_fairouz).into(servicesIcon);
                        break;
                    case 5:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(businessBackground);
                        Glide.with(mainActivity).load(R.drawable.business_green).into(businessIcon);
                        break;
                    case 6:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(classifiedsBackground);
                        Glide.with(mainActivity).load(R.drawable.classifieds_blue).into(classifiedsIcon);
                        break;
                    case 7:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(numbersBackground);
                        Glide.with(mainActivity).load(R.drawable.numbers_purple).into(numbersIcon);
                        break;
                    case 8:
                        Glide.with(mainActivity).load(R.drawable.white_circle_shadow).into(electronicsBackground);
                        Glide.with(mainActivity).load(R.drawable.mobile_yellow).into(electronicsIcon);
                        break;
                }
            }
        }, 300);
    }


    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}