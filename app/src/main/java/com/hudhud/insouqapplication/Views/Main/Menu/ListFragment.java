package com.hudhud.insouqapplication.Views.Main.Menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.Profile.ProfileFragmentDirections;

public class ListFragment extends Fragment {

    ConstraintLayout support, termsConditions, aboutUs, howItWorks, sellingGuidlines, contactUs, privacyPolicy;
    NavController navController;
    MainActivity mainActivity;
    ImageView notifications, home, profile, chat, sellItem, list;

    public ListFragment() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
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
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
        sellItem = view.findViewById(R.id.sell_item);
        support = view.findViewById(R.id.support);
        termsConditions = view.findViewById(R.id.terms_conditions);
        aboutUs = view.findViewById(R.id.about_us);
        howItWorks = view.findViewById(R.id.how_it_works);
        sellingGuidlines = view.findViewById(R.id.selling_guidelines);
        contactUs = view.findViewById(R.id.contact_us);
        privacyPolicy = view.findViewById(R.id.privacy_policy);

    }

    private void onClick(){
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));


//        notifications.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToNotificationsFragment()));
//        support.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToSupportFragment()));
//        termsConditions.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToTermsConditionsFragment()));
//        privacyPolicy.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToPrivacyPolicyFragment()));
//        howItWorks.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToHowItWorksFragment()));
//        contactUs.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToContactUsFragment()));
//        aboutUs.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToAboutUsFragment()));
//        sellingGuidlines.setOnClickListener(view -> navController.navigate(ListFragmentDirections.actionMainFragmentToSellingGuidlinesFragment()));
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}