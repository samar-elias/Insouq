package com.hudhud.insouqapplication.Views.Main.Chat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Constant;
import com.hudhud.insouqapplication.AppUtils.Helpers.FirebaseManger;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Responses.Chats;
import com.hudhud.insouqapplication.AppUtils.Responses.Message;
import com.hudhud.insouqapplication.AppUtils.Responses.New;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BusinessChatDetailsActivity extends AppCompatActivity {


    ImageView home, profile, chat, sellItem, notifications;
    NavController navController;

    //{"chates":0,"packageId":0,"packageExpDate":"0001-01-01T00:00:00","packageStatus":0,"adPicturesss":null,"views":0,"id":2169,"title":"test","price":2555,"description":"test","en_Location":"Dubai","ar_Location":"دبي","lat":"31.8983297","lng":"35.8798502","status":1,"postDate":"2022-03-25T05:59:12.6047697","categoryId":57,"updatecount":0,"categoryArName":"مواد بناء للبيع","categoryEnName":"Building materials for sale ","otherCategoryName":null,"subCategoryId":null,"subCategoryArName":null,"subCategoryEnName":null,"otherSubCategoryName":"tes","typeId":5,"userId":78,"agentId":null,"pictures":[{"id":2049,"imageURL":"https:\/\/newapis.insouq.com\\images\\%D9%A2%D9%A0%D9%A2%D9%A2%D9%A0%D9%A3%D9%A2%D9%A5_%D9%A1%D9%A4%D9%A5%D9%A8%D9%A3%D9%A28f27e8be-d171-4bb4-af1e-a266dbb5c12f.jpg","mainPicture":false}],"userImage":null,"phoneNumber":"00962790960144","isFavorite":false}
    boolean fav = false;
    ConstraintLayout specificationLayout, descriptionLayout, locationLayout, directionsLayout, makeAnOffer, makeAnOffer2, contactLayout;
    ImageView backToPrevious, descriptionArrow, locationArrow, locationImage, favourite, direction, share, img, makeOfferImg;
    TextView description, reportAd, descriptionLine, applyForJob2, applyForJob, title, price, location, makeOfferTxt,
            location2, name, member;
    boolean showDescription, showLocation, showSpecification;
    ImageView showSpecificationArrow, showDescriptionArrow, showLocationArrow, directions;
    TextView contactTitle;
    FirebaseManger firebaseManger;
    RecyclerView similarBusinessRV;
    LinearLayout call, sms, chats;
    ImageView next, previous;
    RecyclerView specificationsRV;
    ViewPager viewPager;
    TabLayout tabLayout;
    String agentId;
    ProgressDialog pDialog;
    String ar_Location;
    String chatId = "";
    String description1;

    String title1;

    String packageExpDate;

    Integer packageStatus;

    //ArrayList<Picture> pictures;

    Integer chates;

    String subCategoryArName;

    String userImage;

    Integer price1;
    ArrayList<String> pictures1 = new ArrayList<String>();

    String categoryEnName;

    Integer id;

    Integer views;

    String lat;

    String lng;

    Integer packageId;

    String subCategoryId;

    Integer userId;

    Integer updatecount;

    String otherSubCategoryName;

    String categoryArName;

    String en_Location;

    String phoneNumber;

    String otherCategoryName;

    String adPicturesss;

    String subCategoryEnName;

    String postDate;

    Integer typeId;

    Integer categoryId;

    Integer status;

    Boolean isFavorite;


    public RequestQueue queue;
    String adsId = "";
    String type = "";

    public BusinessChatDetailsActivity() {
        // Required empty public constructor
    }

    String firstName;
    String lastName;
    String imageProfile;
    Boolean hideInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buisness_chat_detailes);
      /*  if (this != null) {
            position = BusinessDetailsFragmentArgs.fromBundle(this).getPosition();
            isNew = BusinessDetailsFragmentArgs.fromBundle(getArguments()).getIsNew();
        }*/


        queue = Volley.newRequestQueue(this);
        adsId = getIntent().getStringExtra("adsId");
        type = getIntent().getStringExtra("type");
        Log.d("thidge", adsId.toString() + type.toString());
        //Toast.makeText(mainActivity, String.valueOf(adsId+type), Toast.LENGTH_SHORT).show();
        //initViews();
        /*initViews();
        setAdapter();
        onClick();
        setSimilarCars();
        setSpecifications();*/
        initViews();
        getApi();


    }

    private void getApi() {
        StringRequest getUser = new StringRequest(Request.Method.GET, Urls.AdsDetailes + Integer.valueOf(adsId) + "&typeId=" + Integer.valueOf(type), response -> {
            try {
                JSONObject userObject = new JSONObject(response);
                agentId = userObject.getString("agentId");
                ar_Location = userObject.getString("ar_Location");
                description1 = userObject.getString("description");

                title1 = userObject.getString("title");

                packageExpDate = userObject.getString("packageExpDate");

                packageStatus = userObject.getInt("packageStatus");

                // List<PicturesItem> pictures;

                chates = userObject.getInt("chates");

                subCategoryArName = userObject.getString("subCategoryArName");

                userImage = userObject.getString("userImage");

                price1 = userObject.getInt("price");

                categoryEnName = userObject.getString("categoryEnName");

                id = userObject.getInt("id");

                views = userObject.getInt("views");

                lat = userObject.getString("lat");

                lng = userObject.getString("lng");

                packageId = userObject.getInt("packageId");

                subCategoryId = userObject.getString("subCategoryId");

                userId = userObject.getInt("userId");

                updatecount = userObject.getInt("updatecount");

                otherSubCategoryName = userObject.getString("otherSubCategoryName");

                categoryArName = userObject.getString("categoryArName");

                en_Location = userObject.getString("en_Location");

                phoneNumber = userObject.getString("phoneNumber");

                otherCategoryName = userObject.getString("otherCategoryName");

                adPicturesss = userObject.getString("adPicturesss");

                subCategoryEnName = userObject.getString("subCategoryEnName");

                postDate = userObject.getString("postDate");

                typeId = userObject.getInt("typeId");

                categoryId = userObject.getInt("categoryId");

                status = userObject.getInt("status");

                isFavorite = userObject.getBoolean("isFavorite");


                title.setText(title1);
                description.setText(description1);
                price.setText(String.valueOf(price1));


                if (AppDefs.language.equals("ar")) {
                    location.setText(ar_Location);
                    location2.setText(ar_Location);
                } else {
                    location.setText(en_Location);
                    location2.setText(en_Location);
                }

                // businessDetailsFragment.getUserInfo(businessAd.getUserId(), holder.img, holder.name, holder.member);

                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setupWithViewPager(viewPager, true);
                JSONArray pictures =  (JSONArray) userObject.get("pictures");

                for (int i = 0; i < pictures.length(); i++) {
                    JSONObject k = pictures.getJSONObject(i);

                    //pictures1.add(pictures.a);
                    pictures1.add(Urls.IMAGE_URL+k.getString("imageURL").replace("\\", "/"));

                }

                //pictures.get(0).getString("imageURL")
               Log.d("pic", pictures1.toString());



                ImageBuisnessViewPagerAdapter mAdapter = new ImageBuisnessViewPagerAdapter(pictures1, this);
                mAdapter.notifyDataSetChanged();
                viewPager.setOffscreenPageLimit(3);
                viewPager.setAdapter(mAdapter);

                Helpers.setSliderTimer(3000, viewPager, mAdapter);

                ArrayList<specificationModel> businessSpecifications = new ArrayList<>();

                if (categoryEnName.equals("null")) {
                    if (AppDefs.language.equals("ar")) {
                        businessSpecifications.add(new specificationModel(this.getResources().getString(R.string.category), categoryArName, R.drawable.category_name));
                    } else {
                        businessSpecifications.add(new specificationModel(this.getResources().getString(R.string.category), categoryEnName, R.drawable.category_name));
                    }
                }
                if (!otherCategoryName.equals("null")) {
                    businessSpecifications.add(new specificationModel(this.getResources().getString(R.string.category), otherCategoryName, R.drawable.category_name));
                }

                SpecificationAdapter specificationAdapter = new SpecificationAdapter(businessSpecifications);
                specificationsRV.setAdapter(specificationAdapter);
                specificationsRV.setLayoutManager(new LinearLayoutManager(this));

                direction.setOnClickListener(view -> openGoogleMaps(lat, lng));
                call.setOnClickListener(view -> call(phoneNumber));
                sms.setOnClickListener(view -> sendSMS(phoneNumber));
                share.setOnClickListener(view -> share(String.valueOf(id)));
                reportAd.setOnClickListener(view -> setReportAd(String.valueOf(id)));
                descriptionLayout.setOnClickListener(view -> {
                    if (showDescription) {
                        showDescription = false;
                        description.setVisibility(View.GONE);
                        descriptionLine.setVisibility(View.GONE);
                        descriptionArrow.setScaleY(1);
                    } else {
                        showDescription = true;
                        description.setVisibility(View.VISIBLE);
                        descriptionLine.setVisibility(View.VISIBLE);
                        descriptionArrow.setScaleY(-1);
                    }
                });

                locationLayout.setOnClickListener(view -> {
                    if (showLocation) {
                        locationImage.setVisibility(View.GONE);
                        directionsLayout.setVisibility(View.GONE);
                        showLocation = false;
                        locationArrow.setScaleY(1);
                    } else {
                        locationImage.setVisibility(View.VISIBLE);
                        directionsLayout.setVisibility(View.VISIBLE);
                        showLocation = true;
                        locationArrow.setScaleY(-1);
                    }
                });
//

                // holder.makeAnOffer.setOnClickListener(view -> businessDetailsFragment.openMakeAnOffer(businessAd.getId(), businessAd.getPrice()));

                if (favourite.equals("true")) {
                    fav = true;
                    Glide.with(this).load(R.drawable.ic_baseline_favorite_red_24).into(favourite);
                } else {
                    fav = false;
                    Glide.with(this).load(R.drawable.ic_baseline_favorite_border_24).into(favourite);
                }
                favourite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!fav) {
                            fav = true;
                            favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                            addToFavourite(String.valueOf(id));
                        } else {
                            fav = false;
                            favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                            // removeFromFavourite(String.valueOf(id));
                        }
                    }
                });
                favourite.setOnClickListener(view -> {
                    if (!fav) {
                        fav = true;
                        favourite.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                        addToFavourite(String.valueOf(id));
                    } else {
                        fav = false;
                        favourite.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                        removeFromFavourite(String.valueOf(id));
                    }
                });
        /*next.setOnClickListener(view ->{
            if (position+1 != businessAds.size()){
               scrollToPosition(position+1);
            }
        });
       previous.setOnClickListener(view -> {
            if (position!=0){
               scrollToPosition(position-1);
            }
        });*/
                chats.setOnClickListener(v -> {
                    checkAds(Integer.valueOf(id), Integer.valueOf(userId), userImage, postDate, title1, String.valueOf(price1), "5");

                });
                //   backToPrevious.setOnClickListener(view -> businessDetailsFragment.navigateBack());


                //  JSONArray x = userObject.getJSONArray("results");

                Log.d("myadd", updatecount.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("myadd", e.toString());
            }
        }, error -> {

            if (error instanceof ClientError) {
                //   showResponseMessage(getResources().getString(R.string.user), getResources().getString(R.string.wrong_id));
            }
        });
        queue.add(getUser);
    }


    private void initViews() {
        // navController = Navigation.findNavController(this);

        //bottom nav bar
      /*  home =findViewById(R.id.home);
        chat = findViewById(R.id.chat);
        sellItem = findViewById(R.id.sell_item);
        profile = findViewById(R.id.profile);
        notifications = findViewById(R.id.notification);*/

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabDots);
        makeOfferImg = findViewById(R.id.make_offer_img);
        makeOfferTxt = findViewById(R.id.make_offer_txt);
        backToPrevious = findViewById(R.id.back_arrow);
        favourite = findViewById(R.id.favourite);
        reportAd = findViewById(R.id.report);
        img = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_name);
        member = findViewById(R.id.member_since);
        direction = findViewById(R.id.directions);
        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        share = findViewById(R.id.share);

        title = findViewById(R.id.title);
        price = findViewById(R.id.price);
        chats = findViewById(R.id.chat);
        contactTitle = findViewById(R.id.contact_title);
        contactLayout = findViewById(R.id.contact_profile);

        pDialog = new ProgressDialog(this);
        //specifications
        specificationsRV = findViewById(R.id.specifications_RV);
        specificationLayout = findViewById(R.id.specification_layout);
        showSpecificationArrow = findViewById(R.id.show_specifications_arrow);
        showSpecification = false;
        specificationsRV.setVisibility(View.GONE);
        if (showSpecification) {
            specificationsRV.setVisibility(View.GONE);
            showSpecification = false;
            showSpecificationArrow.setScaleY(1);
        } else {
            specificationsRV.setVisibility(View.VISIBLE);
            showSpecification = true;
            showSpecificationArrow.setScaleY(-1);
        }

        //description
        descriptionLayout = findViewById(R.id.description_layout);
        descriptionArrow = findViewById(R.id.show_description_arrow);
        description = findViewById(R.id.description);
        descriptionLine = findViewById(R.id.line2);
        showDescription = false;
        descriptionLine.setVisibility(View.GONE);
        description.setVisibility(View.GONE);

        //location
        //location
        locationLayout = findViewById(R.id.location_layout);
        directionsLayout = findViewById(R.id.directions_layout);
        showLocation = false;
        locationArrow = findViewById(R.id.show_location_arrow);
        locationImage = findViewById(R.id.location_image);
        locationImage.setVisibility(View.GONE);
        directionsLayout.setVisibility(View.GONE);
        location = findViewById(R.id.location);
        location2 = findViewById(R.id.location2);
        locationLayout = findViewById(R.id.location_layout);
        directionsLayout = findViewById(R.id.directions_layout);
        showLocation = false;
        locationImage = findViewById(R.id.location_image);
        locationImage.setVisibility(View.GONE);
        directionsLayout.setVisibility(View.GONE);
        similarBusinessRV = findViewById(R.id.similar_business_RV);

        //make offer
        makeAnOffer = findViewById(R.id.make_an_offer);

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
    }

    public void addToFavourite(String adId) {
        showProgressDialog(getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
            favObj.put("UserId", AppDefs.user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL + "AddToFavorite", favObj, response -> {
            hideProgressDialog();
//            setData();
            // Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_success), Toast.LENGTH_SHORT).show();
        }, error -> {
            hideProgressDialog();
            //showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        queue.add(favRequest);
    }

    public void removeFromFavourite(String adId) {
        showProgressDialog(getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL + "RemoveFromFavorite", favObj, response -> {
            hideProgressDialog();
            //Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_remove), Toast.LENGTH_SHORT).show();
        }, error -> {
            hideProgressDialog();
            //mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        queue.add(favRequest);
    }

    public void showProgressDialog(String msg) {
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void hideProgressDialog() {
        pDialog.cancel();
    }

    public void openGoogleMaps(String latitude, String longitude) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(latitude), Float.parseFloat(longitude));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void setReportAd(String adId) {
        View myAdsOptionsAlertView = LayoutInflater.from(this).inflate(R.layout.report_ad_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(this).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        EditText reasonEdt = myAdsOptionsAlertView.findViewById(R.id.reason_edt);
        EditText descriptionEdt = myAdsOptionsAlertView.findViewById(R.id.description_edt);
        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.continue_btn);
        done.setOnClickListener(view -> {
            String reason = String.valueOf(reasonEdt.getText());
            String desc = String.valueOf(descriptionEdt.getText());
            if (reason.isEmpty() || desc.isEmpty()) {
                showResponseMessage(getResources().getString(R.string.report_ad), getResources().getString(R.string.fill_all_fields));
            } else {
                reportAd(adId, reason, desc);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void reportAd(String adId, String reason, String desc) {
        showProgressDialog(getResources().getString(R.string.loading));
        JSONObject obj = new JSONObject();
        try {
            obj.put("reason", reason);
            obj.put("description", desc);
            obj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL + "ReportAd", obj, response -> {
            hideProgressDialog();
            Toast.makeText(this, getResources().getString(R.string.reported), Toast.LENGTH_SHORT).show();
        }, error -> {
            hideProgressDialog();
            showResponseMessage(getResources().getString(R.string.report_ad), getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        queue.add(favRequest);
    }

    public void call(String call) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + call));
        startActivity(intent);
    }

    public void sendSMS(String phone) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("smsto:"));
        i.setType("vnd.android-dir/mms-sms");
        i.putExtra("address", phone);
        startActivity(Intent.createChooser(i, "Send sms via:"));
    }

    private void setSpecifications() {
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

    public void share(String adId) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, adId);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }

    public void showResponseMessage(String title, String msg) {
        AlertDialog.Builder msgDialog = new AlertDialog.Builder(this);
        msgDialog.setTitle(title);
        msgDialog.setMessage(msg);

        msgDialog.setPositiveButton(getResources().getString(R.string.okay), (dialogInterface, i) -> dialogInterface.dismiss());
        msgDialog.show();
    }

    public void checkAds(Integer adId, Integer userId, String image, String description, String title, String price, String type) {
        StringRequest getUser = new StringRequest(Request.Method.GET, Urls.GetChatsByAdId + "?AdId=" + Integer.valueOf(adId), response -> {
            try {
                JSONObject userObject = new JSONObject(response);
                JSONArray x = userObject.getJSONArray("results");
                if (x.length() == 0) {
                    addchat(adId, userId, image, description, title, price, type);

                } else {
                    for (int i = 0; i < x.length(); i++) {
                        JSONObject jsonObject = (JSONObject) x.get(i);
                        // int quantityValue = Integer.parseInt(jsonObject.getString("Quantity"));
                        //  jsonObject.put("Quantity", quantityValue);
                        if (jsonObject.getInt("UserId") == Integer.valueOf(AppDefs.user.getId()) || jsonObject.getInt("OwnerUserId") == Integer.valueOf(AppDefs.user.getId()) && jsonObject.getInt("UserId") == userId || jsonObject.getInt("OwnerUserId") == userId) {
                            //Toast.makeText(mainActivity, chats.getChatId(), Toast.LENGTH_SHORT).show();
                            chatId = jsonObject.getString("ChatId");

                            break;
                        }
                    }
                    Log.d("reshh", "New JSONArray: " + x); // [{"Code":"NV","Quantity":333},{"Code":"NV","Quantity":333}]



                   /* for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Chats chats = snapshot1.getValue(Chats.class);

                        if (chats.getCustumerUserId() == Integer.valueOf(AppDefs.user.getId()) || chats.getOwnerUserId() == Integer.valueOf(AppDefs.user.getId()) && chats.getCustumerUserId() == userId || chats.getOwnerUserId() == userId) {
                            //Toast.makeText(mainActivity, chats.getChatId(), Toast.LENGTH_SHORT).show();
                            chatId = chats.getChatId();

                            break;
                        }

                    }*/
                }


                Log.d("myadd", userObject.toString());

                //  navToChat();


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("myadd", e.toString());
            }
        }, error -> {

            if (error instanceof ClientError) {
                //   showResponseMessage(getResources().getString(R.string.user), getResources().getString(R.string.wrong_id));
            }
        });
        queue.add(getUser);

    }

    public void addchat(Integer adId, Integer userId, String image, String description, String title, String price, String type) {
     /*   mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest userCarsFilterRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL+"GetAd?adId="+adId+"&typeId=5", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONObject adsArray = new JSONObject(response);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            Log.d("errorFilterMotors", error.getMessage());
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.filters), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userCarsFilterRequest);*/

        DatabaseReference listKeyDatabaseReference = firebaseManger.getDatabaseReference().child(Constant.Chats).push();
        String key = listKeyDatabaseReference.getKey();


        Date date = new Date();
        New anew = new New(Integer.valueOf(AppDefs.user.getId()), "Start Converstsation", false, false, String.valueOf(date.getTime()), 2, key, "aNew");

        List<String> LocURL = new ArrayList<>();
        List<String> files = new ArrayList<>();
        // getUserInfo(String.valueOf(userId));

        StringRequest getUser = new StringRequest(Request.Method.GET, Urls.Users_URL + "GetById?id=" + String.valueOf(userId), response -> {
            try {
                JSONObject userObject = new JSONObject(response);
                firstName = userObject.getString("firstName");
                lastName = userObject.getString("lastName");
                hideInfo=userObject.getBoolean("hideInformation");

                String newPic = "https://insouq.com" + userObject.getString("profilePicture").replace("\\", "/");


                if (userObject.getString("profilePicture").equals("null")) {

                    imageProfile = "";
                } else {
                    imageProfile = newPic;

                }
                Message message = new Message(anew);
                Chats chats1 = new Chats(adId, 0, 0, Integer.valueOf(AppDefs.user.getId()), message,
                        "", true, "", key, userId, true, image, 1, "5", description, title, price, imageProfile, firstName, lastName, String.valueOf(AppDefs.user.getFirstName() + " " + AppDefs.user.getLastName()),hideInfo);


                firebaseManger.getDatabaseReference().child(Constant.Chats).child(key).setValue(chats1);
                saveChat(0,
                        adId,
                        Integer.valueOf(AppDefs.user.getId()),
                        key,
                        userId,
                        1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

            if (error instanceof ClientError) {
                //   showResponseMessage(getResources().getString(R.string.user), getResources().getString(R.string.wrong_id));
            }
        });
        queue.add(getUser);


    }

    public void saveChat(int id,
                         int adId,
                         int userId,
                         String chatId,
                         int ownerUserId,
                         int status) {


        JSONObject csaveChatinfoObject = new JSONObject();
        try {
            csaveChatinfoObject.put("id", id);
            csaveChatinfoObject.put("adId", adId);
            csaveChatinfoObject.put("userId", userId);
            csaveChatinfoObject.put("chatId", chatId);
            csaveChatinfoObject.put("ownerUserId", ownerUserId);
            csaveChatinfoObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.POST, Urls.SaveChat, csaveChatinfoObject, response -> {
            hideProgressDialog();
            try {
                if (response.getBoolean("results")) {
                    Toast.makeText(this, "add success", Toast.LENGTH_LONG).show();
                } else {
                    showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.password_not_changed_successfully));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();

            }
        }, error -> {
            hideProgressDialog();
            showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.wrong_current_password));
        });
        queue.add(changePasswordRequest);

    }


}