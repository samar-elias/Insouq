package com.hudhud.insouqapplication.Views.Main.SellingItems.Motors;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

public class Sell5Fragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialTextView buyNow;
    TextView silver, gold, platinum;

    public Sell5Fragment() {
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
        return inflater.inflate(R.layout.fragment_sell5, container, false);
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
        buyNow = view.findViewById(R.id.buy_now);
        silver = view.findViewById(R.id.silver);
        gold = view.findViewById(R.id.gold);
        platinum = view.findViewById(R.id.platinum);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
//        buyNow.setOnClickListener(view -> navController.navigate(Sell5FragmentDirections.actionSell5FragmentToPaymentFragment()));
        silver.setOnClickListener(view -> {
            silver.setBackground(getResources().getDrawable(R.drawable.purple_radius_20));
            gold.setBackground(null);
            platinum.setBackground(null);
            silver.setTextColor(getResources().getColor(R.color.white));
            gold.setTextColor(getResources().getColor(R.color.gray));
            platinum.setTextColor(getResources().getColor(R.color.gray));
        });
        gold.setOnClickListener(view -> {
            gold.setBackground(getResources().getDrawable(R.drawable.purple_radius_20));
            silver.setBackground(null);
            platinum.setBackground(null);
            gold.setTextColor(getResources().getColor(R.color.white));
            silver.setTextColor(getResources().getColor(R.color.gray));
            platinum.setTextColor(getResources().getColor(R.color.gray));
        });
        platinum.setOnClickListener(view -> {
            platinum.setBackground(getResources().getDrawable(R.drawable.purple_radius_20));
            silver.setBackground(null);
            gold.setBackground(null);
            platinum.setTextColor(getResources().getColor(R.color.white));
            silver.setTextColor(getResources().getColor(R.color.gray));
            gold.setTextColor(getResources().getColor(R.color.gray));
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }
}