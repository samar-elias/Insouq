package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Property;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.MakeOfferBottomSheetDialogFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import java.util.ArrayList;

public class PropertyDetailsFragment extends Fragment {

    ConstraintLayout propertyInfoLayout, amenitiesLayout, descriptionLayout, locationLayout, directionsLayout, makeAnOffer, makeAnOffer2;
    ImageView propertyInfoArrow, amenitiesArrow, descriptionArrow, showLocationArrow, favourite;
    boolean showPropertyInfo, showAmenities, showDescription, showLocation, fav = false;
    RecyclerView propertyInfoRV, similarPropertiesRV;
    RecyclerView amenitiesRV;
    TextView description, descriptionLine, reportAd, itemText, itemPrice;
    ImageView locationImage, backToPrevious, home, profile, chat, sellItem, notifications;
    NavController navController;
    MainActivity mainActivity;
    int count = 4, step = 1;
    ImageView next, previous, itemImage;


    public PropertyDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_property_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        setPropertyInfo();
        setAmenities();
        setSimilarProperties();
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
        favourite = view.findViewById(R.id.favourite);
        reportAd = view.findViewById(R.id.report);

        itemText = view.findViewById(R.id.motor_title);
        itemPrice = view.findViewById(R.id.motor_price);
        itemImage = view.findViewById(R.id.slider_image);
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.previous);

        //property info
        propertyInfoLayout = view.findViewById(R.id.property_info_layout);
        propertyInfoArrow = view.findViewById(R.id.show_property_info_arrow);
        showPropertyInfo = false;
        propertyInfoRV = view.findViewById(R.id.property_info_RV);
        propertyInfoRV.setVisibility(View.GONE);
        if (showPropertyInfo){
            propertyInfoRV.setVisibility(View.GONE);
            showPropertyInfo = false;
            propertyInfoArrow.setScaleY(1);
        }else {
            propertyInfoRV.setVisibility(View.VISIBLE);
            showPropertyInfo = true;
            propertyInfoArrow.setScaleY(-1);
        }

        //amenities
        amenitiesLayout = view.findViewById(R.id.amenities_layout);
        amenitiesArrow = view.findViewById(R.id.show_amenities_arrow);
        showAmenities = false;
        amenitiesRV = view.findViewById(R.id.amenities_RV);
        amenitiesRV.setVisibility(View.GONE);
        if (showAmenities){
            amenitiesRV.setVisibility(View.GONE);
            showAmenities = false;
            amenitiesArrow.setScaleY(1);
        }else {
            amenitiesRV.setVisibility(View.VISIBLE);
            showAmenities = true;
            amenitiesArrow.setScaleY(-1);
        }

        //description
        descriptionLayout = view.findViewById(R.id.description_layout);
        descriptionArrow = view.findViewById(R.id.show_description_arrow);
        showDescription = false;
        description = view.findViewById(R.id.description);
        description.setVisibility(View.GONE);
        descriptionLine = view.findViewById(R.id.line2);

        //location
        locationLayout = view.findViewById(R.id.location_layout);
        directionsLayout = view.findViewById(R.id.directions_layout);
        showLocation = false;
        showLocationArrow = view.findViewById(R.id.show_location_arrow);
        locationImage = view.findViewById(R.id.location_image);
        locationImage.setVisibility(View.GONE);
        directionsLayout.setVisibility(View.GONE);

        //similar
        similarPropertiesRV = view.findViewById(R.id.similar_properties_RV);

        //make offer
        makeAnOffer = view.findViewById(R.id.make_an_offer);
        makeAnOffer2 = view.findViewById(R.id.make_an_offer2);

        //bottom nav bar
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        reportAd.setOnClickListener(view -> setReportAd());

        descriptionLayout.setOnClickListener(view -> {
            if (showDescription){
                description.setVisibility(View.GONE);
                showDescription = false;
                descriptionArrow.setScaleY(1);
                descriptionLine.setVisibility(View.GONE);
            }else {
                description.setVisibility(View.VISIBLE);
                showDescription = true;
                descriptionArrow.setScaleY(-1);
                descriptionLine.setVisibility(View.VISIBLE);
            }
        });
        locationLayout.setOnClickListener(view -> {
            if (showLocation){
                locationImage.setVisibility(View.GONE);
                directionsLayout.setVisibility(View.GONE);
                showLocation = false;
                showLocationArrow.setScaleY(1);
            }else {
                locationImage.setVisibility(View.VISIBLE);
                directionsLayout.setVisibility(View.VISIBLE);
                showLocation = true;
                showLocationArrow.setScaleY(-1);
            }
        });
//        propertyInfoLayout.setOnClickListener(view -> {
//            if (showPropertyInfo){
//                propertyInfoRV.setVisibility(View.GONE);
//                showPropertyInfo = false;
//                propertyInfoArrow.setScaleY(1);
//            }else {
//                propertyInfoRV.setVisibility(View.VISIBLE);
//                showPropertyInfo = true;
//                propertyInfoArrow.setScaleY(-1);
//            }
//        });
//        amenitiesLayout.setOnClickListener(view -> {
//            if (showAmenities){
//                amenitiesRV.setVisibility(View.GONE);
//                showAmenities = false;
//                amenitiesArrow.setScaleY(1);
//            }else {
//                amenitiesRV.setVisibility(View.VISIBLE);
//                showAmenities = true;
//                amenitiesArrow.setScaleY(-1);
//            }
//        });

        favourite.setOnClickListener(view -> {
            if (!fav){
                favourite.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
            }else {
                favourite.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
            }
        });

        makeAnOffer.setOnClickListener(view -> openMakeAnOffer());
        makeAnOffer2.setOnClickListener(view -> openMakeAnOffer());

        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));

        next.setOnClickListener(view -> {
            if (step<=count){
                itemText.setText("Item "+step);
                itemPrice.setText(String.valueOf(step));
                switch (step){
                    case 1:
                        Glide.with(mainActivity).load(R.drawable.img1).into(itemImage);
                        break;
                    case 2:
                        Glide.with(mainActivity).load(R.drawable.img2).into(itemImage);
                        break;
                    case 3:
                        Glide.with(mainActivity).load(R.drawable.img3).into(itemImage);
                        break;
                    case 4:
                        Glide.with(mainActivity).load(R.drawable.img4).into(itemImage);
                        break;

                }
                step++;

            }
        });
        previous.setOnClickListener(view -> {
            if (step>=1){
                itemText.setText("Item "+step);
                itemPrice.setText(String.valueOf(step));
                switch (step){
                    case 1:
                        Glide.with(mainActivity).load(R.drawable.img1).into(itemImage);
                        break;
                    case 2:
                        Glide.with(mainActivity).load(R.drawable.img2).into(itemImage);
                        break;
                    case 3:
                        Glide.with(mainActivity).load(R.drawable.img3).into(itemImage);
                        break;
                    case 4:
                        Glide.with(mainActivity).load(R.drawable.img4).into(itemImage);
                        break;

                }
                step--;
            }
        });
    }

    private void setPropertyInfo(){
//        ArrayList<specificationModel> propertyInfo = new ArrayList<>();
//        specificationModel motorSpecification1 = new specificationModel("\uf2bd", "Type","47,000");
//        specificationModel motorSpecification2 = new specificationModel("\uf2bd", "Price","2017");
//        specificationModel motorSpecification3 = new specificationModel("\uf2bd","Bedrooms","5");
//        specificationModel motorSpecification4 = new specificationModel("\uf2bd", "Bathrooms","4");
//        specificationModel motorSpecification5 = new specificationModel("\uf2bd", "Plot Area","4.250 sq.ft");
//        specificationModel motorSpecification6 = new specificationModel("\uf2bd", "Built-up Area","3.480 sq.ft");
//        specificationModel motorSpecification7 = new specificationModel("\uf2bd", "Year Built","2013");
//        specificationModel motorSpecification8 = new specificationModel("\uf2bd", "Car Park","2");
//        specificationModel motorSpecification9 = new specificationModel("\uf2bd", "Furnished","Yes");
//        specificationModel motorSpecification10 = new specificationModel("\uf2bd", "Status","Ready");
//        propertyInfo.add(motorSpecification1);
//        propertyInfo.add(motorSpecification2);
//        propertyInfo.add(motorSpecification3);
//        propertyInfo.add(motorSpecification4);
//        propertyInfo.add(motorSpecification5);
//        propertyInfo.add(motorSpecification6);
//        propertyInfo.add(motorSpecification7);
//        propertyInfo.add(motorSpecification8);
//        propertyInfo.add(motorSpecification9);
//        propertyInfo.add(motorSpecification10);
//        SpecificationAdapter specificationAdapter = new SpecificationAdapter(propertyInfo);
//        propertyInfoRV.setAdapter(specificationAdapter);
//        propertyInfoRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setAmenities(){

        ArrayList<AmenitiesModel> amenities = new ArrayList<>();

        amenities.add(new AmenitiesModel("Swimming pool", R.drawable.swimming_pool));
        amenities.add(new AmenitiesModel("Central AC", R.drawable.central_ac));
        amenities.add(new AmenitiesModel("Balcony", R.drawable.balcony));
        amenities.add(new AmenitiesModel("Built-in wardrobes", R.drawable.wardrobe));
        amenities.add(new AmenitiesModel("Kid's play area", R.drawable.slide));
        amenities.add(new AmenitiesModel("Covered parking", R.drawable.parking));
        amenities.add(new AmenitiesModel("Park view", R.drawable.park));
        amenities.add(new AmenitiesModel("Maids room", R.drawable.maid));
        amenities.add(new AmenitiesModel("24/7 security", R.drawable.guard));
        amenities.add(new AmenitiesModel("Gym", R.drawable.dumbbell));
        amenities.add(new AmenitiesModel("Spa & sauna", R.drawable.spa));

//        ArrayList<String> amenities = new ArrayList<>();
//        amenities.add("Swimming pool");
//        amenities.add("Park view");
//        amenities.add("Central AC");
//        amenities.add("Maids room");
//        amenities.add("Balcony");
//        amenities.add("24/7 security");
//        amenities.add("Built-in wardrobes");
//        amenities.add("Gym");
//        amenities.add("Kid's play area");
//        amenities.add("Spa & sauna");
//        amenities.add("Covered parking");

        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(amenities);
        amenitiesRV.setAdapter(amenitiesAdapter);
        amenitiesRV.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    private void setSimilarProperties(){
        ProductsAdapter productsAdapter = new ProductsAdapter();
        similarPropertiesRV.setAdapter(productsAdapter);
        similarPropertiesRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void openMakeAnOffer(){
//        MakeOfferBottomSheetDialogFragment makeOfferBottomSheetDialogFragment =
//                MakeOfferBottomSheetDialogFragment.newInstance();
//        makeOfferBottomSheetDialogFragment.show(getChildFragmentManager(),
//                "add_photo_dialog_fragment");
    }

    public void setReportAd(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.report_ad_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.continue_btn);
        done.setOnClickListener(view -> {
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}