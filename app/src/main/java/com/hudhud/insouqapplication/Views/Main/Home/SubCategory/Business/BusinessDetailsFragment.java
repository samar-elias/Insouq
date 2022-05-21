package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Constant;
import com.hudhud.insouqapplication.AppUtils.Helpers.FirebaseManger;
import com.hudhud.insouqapplication.AppUtils.Responses.Chats;
import com.hudhud.insouqapplication.AppUtils.Responses.Message;
import com.hudhud.insouqapplication.AppUtils.Responses.New;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.MakeOfferBottomSheetDialogFragment;
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

public class BusinessDetailsFragment extends Fragment {

    RecyclerView businessRV;
    ImageView home, profile, chat, sellItem, notifications;
    NavController navController;
    public MainActivity mainActivity;
    int position;
    boolean isApplied = false, isNew = false;
    String profileImg = "", profileName = "", memberSince = "";
    FirebaseManger firebaseManger;
    String key;
    String chatId = "";
    public RequestQueue queue;

    public BusinessDetailsFragment() {
        // Required empty public constructor
    }

    String firstName;
    String lastName;
    String imageProfile;
    Boolean hideInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_business_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            position = BusinessDetailsFragmentArgs.fromBundle(getArguments()).getPosition();
            isNew = BusinessDetailsFragmentArgs.fromBundle(getArguments()).getIsNew();
        }
        firebaseManger = FirebaseManger.getInstance();
        key = firebaseManger.getKey();
        queue = Volley.newRequestQueue(requireContext());

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

    private void initViews(View view) {
        navController = Navigation.findNavController(view);

        //bottom nav bar
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);

        businessRV = view.findViewById(R.id.business_RV);

    }

    private void setAdapter() {
        if (isNew) {
            BusinessDetailsAdapter businessDetailsAdapter = new BusinessDetailsAdapter(this, AppDefs.newBusinessAds);
            businessRV.setAdapter(businessDetailsAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            businessRV.setLayoutManager(manager);
            businessRV.scrollToPosition(position);
        } else {
            BusinessDetailsAdapter businessDetailsAdapter = new BusinessDetailsAdapter(this, AppDefs.businessAds);
            businessRV.setAdapter(businessDetailsAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            };
            businessRV.setLayoutManager(manager);
            businessRV.scrollToPosition(position);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onClick() {

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

    public void navToChat(){
        navController.navigate(BusinessDetailsFragmentDirections.actionBusinessDetailsFragment2ToChatFragment());
    }

    public void getUserInfo(String userId, ImageView img, TextView name, TextView members) {
        StringRequest userInfoRequest = new StringRequest(Request.Method.GET, Urls.Users_URL + "GetById?id=" + userId, response -> {
            try {
                JSONObject userObj = new JSONObject(response);
                profileName = userObj.getString("firstName") + " " + userObj.getString("lastName");
                profileImg = userObj.getString("profilePicture");
                String member = userObj.getString("memberSince");
                memberSince = member.substring(0, member.indexOf("T"));
                members.setText(mainActivity.getResources().getString(R.string.member_since) + " " + memberSince);
                name.setText(profileName);
                String img2 = profileImg.replace("\\", "/");
                Glide.with(mainActivity).load(Urls.IMAGE_URL2 + img2).into(img);

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(userInfoRequest);
    }

    public void navigateBack() {
        navController.popBackStack();
    }

    public void setReportAd(String adId) {
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
            if (reason.isEmpty() || desc.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.report_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            } else {
                reportAd(adId, reason, desc);
            }
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void reportAd(String adId, String reason, String desc) {
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject obj = new JSONObject();
        try {
            obj.put("reason", reason);
            obj.put("description", desc);
            obj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL + "ReportAd", obj, response -> {
            mainActivity.hideProgressDialog();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.reported), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.report_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
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

    public void openGoogleMaps(String latitude, String longitude) {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(latitude), Float.parseFloat(longitude));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void scrollToPosition(int position) {
        businessRV.scrollToPosition(position);
    }

    public void setSimilarCars() {
//        MotorItemGridAdapter motorItemGridAdapter = new MotorItemGridAdapter(null);
//        similarCarsRV.setAdapter(motorItemGridAdapter);
//        similarCarsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void openMakeAnOffer(String adId, String price) {
        MakeOfferBottomSheetDialogFragment makeOfferBottomSheetDialogFragment = new MakeOfferBottomSheetDialogFragment(adId, price);
        makeOfferBottomSheetDialogFragment.show(getChildFragmentManager(),
                "add_photo_dialog_fragment");
    }

    public boolean getOfferApplied(String adId) {
        StringRequest offerAppliedRequest = new StringRequest(Request.Method.GET, Urls.Ads_URL + "IsOfferMaked?adId=" + adId, response -> {
            try {
                JSONObject offerApplied = new JSONObject(response);
                isApplied = offerApplied.getBoolean("result");

            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.make_an_offer), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.make_an_offer), mainActivity.getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(offerAppliedRequest);
        return isApplied;
    }

    public void addToFavourite(String adId) {
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
            favObj.put("UserId", AppDefs.user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL + "AddToFavorite", favObj, response -> {
            mainActivity.hideProgressDialog();
//            setData();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_success), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }

    public void removeFromFavourite(String adId) {
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject favObj = new JSONObject();
        try {
            favObj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest favRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL + "RemoveFromFavorite", favObj, response -> {
            mainActivity.hideProgressDialog();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fav_remove), Toast.LENGTH_SHORT).show();
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.favorite), mainActivity.getResources().getString(R.string.internet_connection_error));
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(favRequest);
    }

    private void startActivity(String fragName) {
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
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
                        "", true, "", key, userId, true, image, 1, "5", description, title, price, imageProfile, firstName, lastName,String.valueOf(AppDefs.user.getFirstName()+" "+AppDefs.user.getLastName()),hideInfo);


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

    public void checkAds(Integer adId, Integer userId, String image, String description, String title, String price, String type) {
        StringRequest getUser = new StringRequest(Request.Method.GET, Urls.GetChatsByAdId + "?AdId=" + Integer.valueOf(AppDefs.user.getId()), response -> {
            try {
                JSONObject userObject = new JSONObject(response);
                JSONArray x = userObject.getJSONArray("results");
                if (x.length() == 0) {
                    addchat(adId, userId, image, description, title, price, type);

                }else {
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

               navToChat();


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
            mainActivity.hideProgressDialog();
            try {
                if (response.getBoolean("results")){
                    Toast.makeText(mainActivity, "add success", Toast.LENGTH_LONG).show();
                }else {
                    mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.password_not_changed_successfully));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mainActivity, e.toString(), Toast.LENGTH_LONG).show();

            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.wrong_current_password));
        });
        mainActivity.queue.add(changePasswordRequest);

    }


}