package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors;

import android.annotation.SuppressLint;
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
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.ProductsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.MakeOfferBottomSheetDialogFragment;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MotorDetailsFragment extends Fragment {

    RecyclerView motorItemsDetailsRV;
    ImageView home, profile, chat, sellItem, notifications;
    NavController navController;
    public MainActivity mainActivity;
    int position;
    boolean isApplied = false;
    boolean isNew = false;

    public MotorDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_motor_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            position = MotorDetailsFragmentArgs.fromBundle(getArguments()).getPosition();
            isNew = MotorDetailsFragmentArgs.fromBundle(getArguments()).getIsNew();
        }

        initViews(view);
        setAdapter();
        onClick();
        setSimilarCars();
        setSpecifications();
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

        //bottom nav bar
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);

        motorItemsDetailsRV = view.findViewById(R.id.motor_items_RV);

    }

    private void setAdapter(){
        MotorDetailsAdapter motorDetailsAdapter;
        if (isNew){
            motorDetailsAdapter = new MotorDetailsAdapter(this, AppDefs.newMotorAds);
            motorItemsDetailsRV.setAdapter(motorDetailsAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            motorItemsDetailsRV.setLayoutManager(manager);
        }else {
            motorDetailsAdapter = new MotorDetailsAdapter(this, AppDefs.motorAds);
            motorItemsDetailsRV.setAdapter(motorDetailsAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            motorItemsDetailsRV.setLayoutManager(manager);
        }
        motorItemsDetailsRV.scrollToPosition(position);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onClick(){

//        specificationLayout.setOnClickListener(view -> {
//            if (showSpecification){
//                specificationsRV.setVisibility(View.GONE);
//                showSpecification = false;
//                showSpecificationArrow.setScaleY(1);
//            }else {
//                specificationsRV.setVisibility(View.VISIBLE);
//                showSpecification = true;
//                showSpecificationArrow.setScaleY(-1);
//            }
//        });

//

        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));

//        favourite.setOnClickListener(view -> {
//            if (!fav){
//                favourite.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
//                fav = true;
//            }else {
//                favourite.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
//                fav = false;
//            }
//        });
//
//        next.setOnClickListener(view -> {
//            if (step<=count){
//                itemText.setText("Item "+step);
//                itemPrice.setText(String.valueOf(step));
//                switch (step){
//                    case 1:
//                        Glide.with(mainActivity).load(R.drawable.img1).into(itemImage);
//                        break;
//                    case 2:
//                        Glide.with(mainActivity).load(R.drawable.img2).into(itemImage);
//                        break;
//                    case 3:
//                        Glide.with(mainActivity).load(R.drawable.img3).into(itemImage);
//                        break;
//                    case 4:
//                        Glide.with(mainActivity).load(R.drawable.img4).into(itemImage);
//                        break;
//
//                }
//                step++;
//
//            }
//        });
//        previous.setOnClickListener(view -> {
//            if (step>=1){
//                itemText.setText("Item "+step);
//                itemPrice.setText(String.valueOf(step));
//                switch (step){
//                    case 1:
//                        Glide.with(mainActivity).load(R.drawable.img1).into(itemImage);
//                        break;
//                    case 2:
//                        Glide.with(mainActivity).load(R.drawable.img2).into(itemImage);
//                        break;
//                    case 3:
//                        Glide.with(mainActivity).load(R.drawable.img3).into(itemImage);
//                        break;
//                    case 4:
//                        Glide.with(mainActivity).load(R.drawable.img4).into(itemImage);
//                        break;
//
//                }
//                step--;
//            }
//        });
    }

    public void navigateBack(){
        navController.popBackStack();
    }

    public void setReportAd(String adId){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.report_ad_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        EditText reasonEdt = myAdsOptionsAlertView.findViewById(R.id.reason_edt);
        EditText descriptionEdt = myAdsOptionsAlertView.findViewById(R.id.description_edt);
        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.continue_btn);
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

    public void call(String call){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+call));
        startActivity(intent);
    }

    public void sendSMS(String phone){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("smsto:"));
        i.setType("vnd.android-dir/mms-sms");
        i.putExtra("address",phone);
        startActivity(Intent.createChooser(i, "Send sms via:"));
    }

    private void setSpecifications(){
//        specificationModel motorSpecification1 = new specificationModel( getResources().getString(R.string.mileage),"47,000");
//        specificationModel motorSpecification2 = new specificationModel( getResources().getString(R.string.year),"2017");
//        specificationModel motorSpecification3 = new specificationModel( getResources().getString(R.string.specs),"GCC");
//        specificationModel motorSpecification4 = new specificationModel( getResources().getString(R.string.color),"Orange");
//        specificationModel motorSpecification5 = new specificationModel( getResources().getString(R.string.doors),"2");
//        specificationModel motorSpecification6 = new specificationModel( getResources().getString(R.string.warranty),"Yes");
//        specificationModel motorSpecification7 = new specificationModel( getResources().getString(R.string.transmissions),"Automatic");
//        specificationModel motorSpecification8 = new specificationModel( getResources().getString(R.string.body_type),"Sedan");
//        specificationModel motorSpecification9 = new specificationModel( getResources().getString(R.string.fuel_type),"Gasoline");
//        specificationModel motorSpecification10 = new specificationModel( getResources().getString(R.string.no_of_cylinfers),"4");
//        specificationModel motorSpecification11 = new specificationModel( getResources().getString(R.string.horsepower),"300 HP");
//        motorSpecifications.add(motorSpecification1);
//        motorSpecifications.add(motorSpecification2);
//        motorSpecifications.add(motorSpecification3);
//        motorSpecifications.add(motorSpecification4);
//        motorSpecifications.add(motorSpecification5);
//        motorSpecifications.add(motorSpecification6);
//        motorSpecifications.add(motorSpecification7);
//        motorSpecifications.add(motorSpecification8);
//        motorSpecifications.add(motorSpecification9);
//        motorSpecifications.add(motorSpecification10);
//        motorSpecifications.add(motorSpecification11);
//        SpecificationAdapter specificationAdapter = new SpecificationAdapter(motorSpecifications);
//        specificationsRV.setAdapter(specificationAdapter);
//        specificationsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void share(String adId){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,adId);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }

    public void openGoogleMaps(String latitude, String longitude){
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(latitude),Float.parseFloat(longitude));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void scrollToPosition(int position){
        motorItemsDetailsRV.scrollToPosition(position);
    }

    public void setSimilarCars(){
//        MotorItemGridAdapter motorItemGridAdapter = new MotorItemGridAdapter(null);
//        similarCarsRV.setAdapter(motorItemGridAdapter);
//        similarCarsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void openMakeAnOffer(String adId, String price){
        MakeOfferBottomSheetDialogFragment makeOfferBottomSheetDialogFragment = new MakeOfferBottomSheetDialogFragment(adId, price);
        makeOfferBottomSheetDialogFragment.show(getChildFragmentManager(),
                "add_photo_dialog_fragment");
    }

    public boolean getOfferApplied(String adId){
        StringRequest offerAppliedRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"IsOfferMaked?adId="+adId, response -> {
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
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
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

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}