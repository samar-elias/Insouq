package com.hudhud.insouqapplication.Views.Main.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.UserFS;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.RegistrationActivity;
import com.hudhud.insouqapplication.Views.Splash.SplashActivity;

import static android.content.Context.MODE_PRIVATE;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ConstraintLayout myProfile, myAds, myFavourites, mySavedSearches, myJobs, countries, advertising, insouqListing, logout;
    LinearLayout listing;
    NavController navController;
    MainActivity mainActivity;
    ImageView notifications, home, chat, profile, sellAnItem;
    TextView userName, country, language, support, termsConditions, aboutUs, howItWorks, sellingGuidelines, contactUs, privacyPolicy;
    boolean isEnglish = true;
    boolean visibleListing = false;
    CircleImageView profileImage;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        myProfile = view.findViewById(R.id.my_profile_layout);
        myAds = view.findViewById(R.id.my_ads_layout);
        myFavourites = view.findViewById(R.id.my_favourites_layout);
        mySavedSearches = view.findViewById(R.id.my_saved_searches_layout);
        logout = view.findViewById(R.id.log_out_layout);
        myJobs = view.findViewById(R.id.my_jobs_layout);
        countries = view.findViewById(R.id.countries_layout);
        advertising = view.findViewById(R.id.advertising_layout);
        insouqListing = view.findViewById(R.id.insouq_app_layout);
        listing = view.findViewById(R.id.insouq_listing);
        profileImage = view.findViewById(R.id.profile_image);

        userName = view.findViewById(R.id.user_name);
        userName.setText(AppDefs.user.getFirstName()+" "+AppDefs.user.getLastName());

        support = view.findViewById(R.id.support);
        termsConditions = view.findViewById(R.id.terms_conditions);
        aboutUs = view.findViewById(R.id.about_us);
        howItWorks = view.findViewById(R.id.how_it_works);
        sellingGuidelines = view.findViewById(R.id.selling_guidelines);
        contactUs = view.findViewById(R.id.contact_us);
        privacyPolicy = view.findViewById(R.id.privacy_policy);

        //bottom nav bar
        notifications = view.findViewById(R.id.notification);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        profile = view.findViewById(R.id.profile);
        sellAnItem = view.findViewById(R.id.sell_item);

        country = view.findViewById(R.id.country);
        language = view.findViewById(R.id.language);

        if (!AppDefs.user.getProfilePicture().isEmpty()){
            String newPic = AppDefs.user.getProfilePicture().replace("\\", "/");
            Glide.with(mainActivity).load(newPic).into(profileImage);
        }
    }

    private void onClick(){
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellAnItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));

        myProfile.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToMyProfileFragment()));
        myAds.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToMyAdsFragment()));
        myJobs.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToMyJobsFragment()));
        mySavedSearches.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToMySavedSearchesFragment()));
        myFavourites.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToMyFavouritesFragment()));
        advertising.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToAdvertisingFragment()));

        support.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToSupportFragment()));
        termsConditions.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToTermsConditionsFragment()));
        aboutUs.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToAboutUsFragment()));
        howItWorks.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToHowItWorksFragment()));
        sellingGuidelines.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToSellingGuidlinesFragment()));
        contactUs.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToContactUsFragment()));
        privacyPolicy.setOnClickListener(view -> navController.navigate(ProfileFragmentDirections.actionProfileFragmentToPrivacyPolicyFragment()));

        countries.setOnClickListener(view -> changeCountry());
        insouqListing.setOnClickListener(view -> {
            if (visibleListing){
                listing.setVisibility(View.GONE);
                visibleListing = false;
            }else {
                listing.setVisibility(View.VISIBLE);
                visibleListing = true;
            }
        });

        logout.setOnClickListener(view -> {
            showLogoutMessage();
        });

        language.setOnClickListener(view -> {
            if (isEnglish){
                language.setText("Arabic");
                isEnglish = false;
            }else {
                language.setText("English");
                isEnglish = true;
            }
//            changeLanguage();
        });
    }

    public void changeCountry(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.countriesl_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.done);
        RadioButton rb1 = myAdsOptionsAlertView.findViewById(R.id.uae);
        RadioButton rb2 = myAdsOptionsAlertView.findViewById(R.id.jordan);
        RadioButton rb3 = myAdsOptionsAlertView.findViewById(R.id.iraq);
        RadioButton rb4 = myAdsOptionsAlertView.findViewById(R.id.kuwait);
        ImageView close = myAdsOptionsAlertView.findViewById(R.id.close);
        rb1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rb2.setChecked(false);
                rb3.setChecked(false);
            }
        });
        rb2.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rb1.setChecked(false);
                rb3.setChecked(false);
            }
        });
        rb3.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rb2.setChecked(false);
                rb1.setChecked(false);
            }
        });
        done.setOnClickListener(view -> {
            String countryName = "";
            if (rb1.isChecked()){
                countryName = "UAE";
                country.setText(countryName);
                myAdsOptionsAlertBuilder.dismiss();
            }else {
                Toast.makeText(mainActivity, "Coming Soon", Toast.LENGTH_LONG).show();
            }
        });
        close.setOnClickListener(view -> myAdsOptionsAlertBuilder.dismiss());
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void changeLanguage(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.languages_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.done);
        ImageView close = myAdsOptionsAlertView.findViewById(R.id.close);
        RadioButton rb1 = myAdsOptionsAlertView.findViewById(R.id.english);
        RadioButton rb2 = myAdsOptionsAlertView.findViewById(R.id.arabic);
        done.setOnClickListener(view -> {
            String countryName = "";
            if (rb1.isChecked()){
                countryName = String.valueOf(rb1.getText());
            }else if (rb2.isChecked()){
                countryName = String.valueOf(rb2.getText());
            }
            language.setText(countryName);
            myAdsOptionsAlertBuilder.dismiss();
        });
        close.setOnClickListener(view -> myAdsOptionsAlertBuilder.dismiss());
    }

    private void showLogoutMessage(){
        AlertDialog.Builder msgDialog = new AlertDialog.Builder(mainActivity);
        msgDialog.setTitle(this.getResources().getString(R.string.logout));
        msgDialog.setMessage(mainActivity.getResources().getString(R.string.logout_message));

        msgDialog.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
            SharedPreferences preferences = mainActivity.getSharedPreferences(AppDefs.SHARED_PREF_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            AppDefs.user = new UserFS();
            Intent splashIntent = new Intent(mainActivity, SplashActivity.class);
            startActivity(splashIntent);
            mainActivity.finish();
        });

        msgDialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        msgDialog.show();
    }
}