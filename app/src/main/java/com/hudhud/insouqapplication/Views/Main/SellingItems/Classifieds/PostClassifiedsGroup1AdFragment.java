package com.hudhud.insouqapplication.Views.Main.SellingItems.Classifieds;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.map.MapsActivity;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createMultipartBodyPart;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

public class PostClassifiedsGroup1AdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    private DecimalFormat formatter = new DecimalFormat("#0.00");
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    private static final int REQUEST_CODE = 101;
    ImageView image1, image2, image3, image4, image5, image7, image6, image8, image9, image10, uploadImage, mainImage;
    ImageView closeImage1, closeImage2, closeImage3, closeImage4, closeImage5, closeImage7, closeImage6, closeImage8, closeImage9, closeImage10, closeMainImage;
    String image1Path = "", image2Path = "", image3Path = "", image4Path = "", image5Path = "", image7Path = "", image6Path = "", image8Path = "", image9Path = "", image10Path = "", mainPath = "", longitude = "", latitude = "", address = "";
    Bitmap image1Bitmap, image2Bitmap, image3Bitmap, image4Bitmap, image5Bitmap, image7Bitmap, image6Bitmap, image8Bitmap, image9Bitmap, image10Bitmap;
    EditText priceEdt, phoneNumberEdt, descriptionEdt;
    TextView location;
    Spinner agesSpinner, usagesSpinner, conditionsSpinner, brandsSpinner, locationsSpinner;
    ArrayList<String> ageArTitles, ageEnTitles, usageArTitles, usageEnTitles, conditionArTitle, conditionEnTitles, locationArTitles, locationEnTitles, brandArTitles, brandEnTitles;
    String currentAge = "", currentUsage = "", currentCondition = "", currentBrand = "", currentLocation = "", adId = "";
    ArrayList<String> pictures;
    CheckBox agreementCheckBox;
    boolean spinner1 = false, spinner2 = false, spinner3 = false, spinner4 = false, spinner5 = false;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;

    //Ad sample
    TextView adPrice, adCondition, adAge, adLocation, adTitle;
    ImageView adImage;

    public PostClassifiedsGroup1AdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_classifieds_group1_ad, container, false);
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
        getAges();
        getUsages();
        getConditions();
        getLocations();
        getBrands();
        onSpinnerSelected();
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);

        continueBtn = view.findViewById(R.id.continue_btn);
        adTitle = view.findViewById(R.id.electronics_title);
        adLocation = view.findViewById(R.id.ad_location);
        adPrice = view.findViewById(R.id.price);
        adImage = view.findViewById(R.id.electronics_image);
        priceEdt = view.findViewById(R.id.ad_price);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        descriptionEdt = view.findViewById(R.id.ad_short_description);
        location = view.findViewById(R.id.location);
        agreementCheckBox = view.findViewById(R.id.agreement_checkbox);
        adAge = view.findViewById(R.id.ad_age);
        adCondition = view.findViewById(R.id.ad_condition);

        agesSpinner = view.findViewById(R.id.ages_spinner);
        usagesSpinner = view.findViewById(R.id.usages_spinner);
        conditionsSpinner = view.findViewById(R.id.conditions_spinner);
        brandsSpinner = view.findViewById(R.id.brands_spinner);
        locationsSpinner = view.findViewById(R.id.locations_spinner);

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        image7 = view.findViewById(R.id.image7);
        image8 = view.findViewById(R.id.image8);
        image9 = view.findViewById(R.id.image9);
        image10 = view.findViewById(R.id.image10);
        uploadImage = view.findViewById(R.id.upload_pictures);
        mainImage = view.findViewById(R.id.main_image_);

        closeImage1 = view.findViewById(R.id.close_img1);
        closeImage2 = view.findViewById(R.id.close_img2);
        closeImage3 = view.findViewById(R.id.close_img3);
        closeImage4 = view.findViewById(R.id.close_img4);
        closeImage5 = view.findViewById(R.id.close_img5);
        closeImage6 = view.findViewById(R.id.close_img6);
        closeImage7 = view.findViewById(R.id.close_img7);
        closeImage8 = view.findViewById(R.id.close_img8);
        closeImage9 = view.findViewById(R.id.close_img9);
        closeImage10 = view.findViewById(R.id.close_img10);
        closeMainImage = view.findViewById(R.id.close_main_);

        ageArTitles = new ArrayList<>();
        ageEnTitles = new ArrayList<>();
        usageArTitles = new ArrayList<>();
        usageEnTitles = new ArrayList<>();
        conditionArTitle = new ArrayList<>();
        conditionEnTitles = new ArrayList<>();
        locationArTitles = new ArrayList<>();
        locationEnTitles = new ArrayList<>();
        brandArTitles = new ArrayList<>();
        brandEnTitles = new ArrayList<>();
        pictures = new ArrayList<>();

        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }
        adTitle.setText(Send.addClassifiedAd.getTitle());
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String price = String.valueOf(priceEdt.getText());
            String phoneNumber = String.valueOf(phoneNumberEdt.getText());
            String description = String.valueOf(descriptionEdt.getText());
            setData(price, description, phoneNumber);
        });

        priceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adPrice.setText("AED "+priceEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        uploadImage.setOnClickListener(view -> {
            if (image1Path.isEmpty()){
                pickImage(1);
            }else if (image2Path.isEmpty()){
                pickImage(2);
            }else if (image3Path.isEmpty()){
                pickImage(3);
            }else if (image4Path.isEmpty()){
                pickImage(4);
            }else if (image5Path.isEmpty()){
                pickImage(5);
            }else if (image6Path.isEmpty()){
                pickImage(6);
            }else if (image7Path.isEmpty()){
                pickImage(7);
            }else if (image8Path.isEmpty()){
                pickImage(8);
            }else if (image9Path.isEmpty()){
                pickImage(9);
            }else if (image10Path.isEmpty()){
                pickImage(10);
            }
        });

        location.setOnClickListener(view -> startMapsActivity());

        image1.setOnClickListener(view -> {
            mainImage.setImageBitmap(image1Bitmap);
            mainPath = image1Path.substring(image1Path.lastIndexOf("/")+1);
            adImage.setImageBitmap(image1Bitmap);
            changeImages(image1, image2, image3, image4, image5, image6, image7, image8, image9, image10);
        });
        image2.setOnClickListener(view -> {
            mainImage.setImageBitmap(image2Bitmap);
            mainPath = image2Path.substring(image2Path.lastIndexOf("/")+1);
            adImage.setImageBitmap(image2Bitmap);
            changeImages(image2, image1, image3, image4, image5, image6, image7, image8, image9, image10);
        });
        image3.setOnClickListener(view -> {
            mainImage.setImageBitmap(image3Bitmap);
            mainPath = image3Path.substring(image3Path.lastIndexOf("/")+1);
            adImage.setImageBitmap(image3Bitmap);
            changeImages(image3, image2, image1, image4, image5, image6, image7, image8, image9, image10);
        });
        image4.setOnClickListener(view -> {
            mainImage.setImageBitmap(image4Bitmap);
            mainPath = image4Path.substring(image4Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image4Bitmap);
            changeImages(image4, image2, image3, image1, image5, image6, image7, image8, image9, image10);
        });
        image5.setOnClickListener(view -> {
            mainImage.setImageBitmap(image5Bitmap);
            mainPath = image5Path.substring(image5Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image5Bitmap);
            changeImages(image5, image2, image3, image4, image1, image6, image7, image8, image9, image10);
        });
        image6.setOnClickListener(view -> {
            mainImage.setImageBitmap(image6Bitmap);
            mainPath = image6Path.substring(image6Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image6Bitmap);
            changeImages(image6, image2, image3, image4, image5, image1, image7, image8, image9, image10);
        });
        image7.setOnClickListener(view -> {
            mainImage.setImageBitmap(image7Bitmap);
            mainPath = image7Path.substring(image7Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image7Bitmap);
            changeImages(image7, image2, image3, image4, image5, image6, image1, image8, image9, image10);
        });
        image8.setOnClickListener(view -> {
            mainImage.setImageBitmap(image8Bitmap);
            mainPath = image8Path.substring(image8Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image8Bitmap);
            changeImages(image8, image2, image3, image4, image5, image6, image7, image1, image9, image10);
        });
        image9.setOnClickListener(view -> {
            mainImage.setImageBitmap(image9Bitmap);
            mainPath = image9Path.substring(image9Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image9Bitmap);
            changeImages(image9, image2, image3, image4, image5, image6, image7, image8, image1, image10);
        });
        image10.setOnClickListener(view -> {
            mainImage.setImageBitmap(image10Bitmap);
            mainPath = image10Path.substring(image10Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image10Bitmap);
            changeImages(image10, image2, image3, image4, image5, image6, image7, image8, image9, image1);
        });

        closeMainImage.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(mainImage);
            mainPath = "";
        });

        closeImage1.setOnClickListener(view -> {
            clearMainPath(image1Path, image1);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image1);
            image1Path = "";
        });

        closeImage2.setOnClickListener(view -> {
            clearMainPath(image2Path, image2);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image2);
            image2Path = "";
        });

        closeImage3.setOnClickListener(view -> {
            clearMainPath(image3Path, image3);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image3);
            image3Path = "";
        });

        closeImage4.setOnClickListener(view -> {
            clearMainPath(image4Path, image4);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image4);
            image4Path = "";
        });

        closeImage5.setOnClickListener(view -> {
            clearMainPath(image5Path, image5);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image5);
            image5Path = "";
        });

        closeImage6.setOnClickListener(view -> {
            clearMainPath(image6Path, image6);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image6);
            image6Path = "";
        });

        closeImage7.setOnClickListener(view -> {
            clearMainPath(image7Path, image7);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image7);
            image7Path = "";
        });

        closeImage8.setOnClickListener(view -> {
            clearMainPath(image8Path, image8);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image8);
            image8Path = "";
        });

        closeImage9.setOnClickListener(view -> {
            clearMainPath(image9Path, image9);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image9);
            image9Path = "";
        });

        closeImage10.setOnClickListener(view -> {
            clearMainPath(image10Path, image10);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image10);
            image10Path = "";
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }
    
    private void onSpinnerSelected(){
        agesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner1 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentAge = "-1";
                    adAge.setText(mainActivity.getResources().getString(R.string.age));
                }else {
                    currentAge = ageEnTitles.get(i)+"-"+ageArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adAge.setText(ageArTitles.get(i));
                    }else {
                        adAge.setText(ageEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        usagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner2 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentUsage = "-1";
                }else {
                    currentUsage = usageEnTitles.get(i)+"-"+usageArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner3 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentLocation = "-1";
                    adLocation.setText(mainActivity.getResources().getString(R.string.location));
                }else {
                    currentLocation = locationEnTitles.get(i)+"-"+locationArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adLocation.setText(locationArTitles.get(i));
                    }else {
                        adLocation.setText(locationEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        conditionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner4 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentCondition = "-1";
                    adCondition.setText(mainActivity.getResources().getString(R.string.condition));
                }else {
                    currentCondition = conditionEnTitles.get(i)+"-"+conditionArTitle.get(i);
                    if (AppDefs.language.equals("ar")){
                        adCondition.setText(conditionArTitle.get(i));
                    }else {
                        adCondition.setText(conditionEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        brandsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner5 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentBrand = "-1";
                }else {
                    currentBrand = brandEnTitles.get(i)+"-"+brandArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAges(){
        ageEnTitles.clear();
        ageArTitles.clear();
        ageEnTitles.add(mainActivity.getResources().getString(R.string.select_age));
        ageArTitles.add(mainActivity.getResources().getString(R.string.select_age));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllAgeByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    ageArTitles.add(ageObj.getString("ar_Text"));
                    ageEnTitles.add(ageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(agesSpinner, ageArTitles);
                }else {
                    setSpinner(agesSpinner, ageEnTitles);
                }
                currentAge = ageEnTitles.get(0)+"-"+ageArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getUsages(){
        usageEnTitles.clear();
        usageArTitles.clear();
        usageEnTitles.add(mainActivity.getResources().getString(R.string.select_usage));
        usageArTitles.add(mainActivity.getResources().getString(R.string.select_usage));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest usageRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllUsageByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray usagesArray = new JSONArray(response);
                for (int i=0; i<usagesArray.length(); i++){
                    JSONObject usageObj = usagesArray.getJSONObject(i);
                    usageArTitles.add(usageObj.getString("ar_Text"));
                    usageEnTitles.add(usageObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(usagesSpinner, usageArTitles);
                }else {
                    setSpinner(usagesSpinner, usageEnTitles);
                }
                currentUsage = usageEnTitles.get(0)+"-"+usageArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(usageRequest);
    }

    private void getConditions(){
        conditionEnTitles.clear();
        conditionArTitle.clear();
        conditionEnTitles.add(mainActivity.getResources().getString(R.string.select_condition));
        conditionArTitle.add(mainActivity.getResources().getString(R.string.select_condition));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest conditionRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllConditionByTypeId?typeId=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray conditionsArray = new JSONArray(response);
                for (int i=0; i<conditionsArray.length(); i++){
                    JSONObject conditionObj = conditionsArray.getJSONObject(i);
                    conditionArTitle.add(conditionObj.getString("ar_Text"));
                    conditionEnTitles.add(conditionObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(conditionsSpinner, conditionArTitle);
                }else {
                    setSpinner(conditionsSpinner, conditionEnTitles);
                }
                currentCondition = conditionEnTitles.get(0)+"-"+conditionArTitle.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.condition), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.condition), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(conditionRequest);
    }

    private void getLocations(){
        locationEnTitles.clear();
        locationArTitles.clear();
        locationEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    locationArTitles.add(locationObj.getString("ar_Text"));
                    locationEnTitles.add(locationObj.getString("en_Text"));
                }
                adLocation.setText(locationEnTitles.get(0));
                if (AppDefs.language.equals("ar")){
                    setSpinner(locationsSpinner, locationArTitles);
                }else {
                    setSpinner(locationsSpinner, locationEnTitles);
                }
                currentLocation = locationEnTitles.get(0)+"-"+locationArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.location), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(locationsRequest);
    }

    private void getBrands(){
        brandEnTitles.clear();
        brandArTitles.clear();
        brandEnTitles.add(mainActivity.getResources().getString(R.string.select_brands));
        brandArTitles.add(mainActivity.getResources().getString(R.string.select_brands));
        brandArTitles.add(mainActivity.getResources().getString(R.string.test));
        brandEnTitles.add(mainActivity.getResources().getString(R.string.test));
        if (AppDefs.language.equals("ar")){
            setSpinner(brandsSpinner, brandArTitles);
        }else {
            setSpinner(brandsSpinner, brandEnTitles);
        }
        currentBrand = brandEnTitles.get(0)+"-"+brandArTitles.get(0);
    }

    private void addInitialClassifiedAd(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject initialClassifiedObj = new JSONObject();
        try {
            initialClassifiedObj.put("title", Send.addClassifiedAd.getTitle());
            initialClassifiedObj.put("categoryId", Send.addClassifiedAd.getCategoryId());
            initialClassifiedObj.put("subCategoryId", Send.addClassifiedAd.getSubCategoryId());
            initialClassifiedObj.put("otherSubCategory", Send.addClassifiedAd.getOtherSubCategory());
            initialClassifiedObj.put("subTypeId", Send.addClassifiedAd.getSubTypeId());
            initialClassifiedObj.put("otherSubTypeId", Send.addClassifiedAd.getOtherSubType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest classifiedsRequest = new JsonObjectRequest(Request.Method.POST, Urls.ClassifiedAds_URL+"AddInitialClassified", initialClassifiedObj, response -> {
            try {
                Send.addClassifiedAd.setAdId(response.getString("id"));
                postFullClassifiedsAd();
//                packagesPopUp();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(classifiedsRequest);
    }

    private void postFullClassifiedsAd(){
//        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.ClassifiedAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody adId = createRequestBody(Send.addClassifiedAd.getAdId());
        RequestBody description = createRequestBody(Send.addClassifiedAd.getDescription());
        RequestBody location = createRequestBody(Send.addClassifiedAd.getLocation());
        RequestBody lat = createRequestBody(Send.addClassifiedAd.getLatitude());
        RequestBody lan = createRequestBody(Send.addClassifiedAd.getLongitude());
        RequestBody mainPhoto = createRequestBody(Send.addClassifiedAd.getMainPhone());
        RequestBody categoryId = createRequestBody(Send.addClassifiedAd.getCategoryId());
        RequestBody age = createRequestBody(Send.addClassifiedAd.getAge());
        RequestBody price = createRequestBody(Send.addClassifiedAd.getPrice());
        RequestBody usage = createRequestBody(Send.addClassifiedAd.getUsage());
        RequestBody brand = createRequestBody(Send.addClassifiedAd.getBrand());
        RequestBody condition = createRequestBody(Send.addClassifiedAd.getCondition());
        RequestBody phoneNumber = createRequestBody(Send.addClassifiedAd.getPhoneNumber());
        ArrayList<MultipartBody.Part> images = new ArrayList<>();

        for (int i=0; i<pictures.size(); i++){
            images.add(createMultipartBodyPart("pictures",pictures.get(i)));
        }

        Call<AddAdsResponse> addClassifiedsAd = retrofit.create(RetrofitUrls.class).addClassifiedsAd(adId, description, location, lat, lan, price, mainPhoto, categoryId, age, usage, brand, condition, phoneNumber, images);
        addClassifiedsAd.enqueue(new Callback<AddAdsResponse>() {
            @Override
            public void onResponse(Call<AddAdsResponse> call, Response<AddAdsResponse> response) {
                mainActivity.hideProgressDialog();
                Timber.tag("response").d(response.message());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                        startActivity("");
                        if (packageId.isEmpty()){
                            startActivity("");
                        }else {
                            navController.navigate(PostClassifiedsGroup1AdFragmentDirections.actionPostClassifiedsGroup1AdFragmentToPaymentFragment(packageId, ""));
                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.ad_posted_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

    }

    private void setData(String price, String description, String phoneNumber){
        if (price.isEmpty() || phoneNumber.isEmpty()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
        }else if(Integer.parseInt(price)>10000000){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds), mainActivity.getResources().getString(R.string.price_high));
        }else {
            pictures.clear();
            price = formatter.format(Double.parseDouble(price));
            Send.addClassifiedAd.setPrice(price);
            Send.addClassifiedAd.setAdId(adId);
            Send.addClassifiedAd.setLatitude(latitude);
            Send.addClassifiedAd.setLongitude(longitude);
            Send.addClassifiedAd.setLocation(currentLocation);
            Send.addClassifiedAd.setBrand(currentBrand);
            Send.addClassifiedAd.setCondition(currentCondition);
            Send.addClassifiedAd.setAge(currentAge);
            Send.addClassifiedAd.setUsage(currentUsage);
            Send.addClassifiedAd.setPhoneNumber(phoneNumber);
            Send.addClassifiedAd.setPrice(price);
            Send.addClassifiedAd.setDescription(description);
            if (!image1Path.isEmpty()){
                pictures.add(image1Path);
            }
            if (!image2Path.isEmpty()){
                pictures.add(image2Path);
            }
            if (!image3Path.isEmpty()){
                pictures.add(image3Path);
            }
            if (!image4Path.isEmpty()){
                pictures.add(image4Path);
            }
            if (!image5Path.isEmpty()){
                pictures.add(image5Path);
            }
            if (!image6Path.isEmpty()){
                pictures.add(image6Path);
            }
            if (!image7Path.isEmpty()){
                pictures.add(image7Path);
            }
            if (!image8Path.isEmpty()){
                pictures.add(image8Path);
            }
            if (!image9Path.isEmpty()){
                pictures.add(image9Path);
            }
            if (!image10Path.isEmpty()){
                pictures.add(image10Path);
            }
            if (pictures.size()==0){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.minimum_pic));
            }else if (mainPath.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors_ad), mainActivity.getResources().getString(R.string.select_main_image));
            }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.location_error));
            }else  if (!agreementCheckBox.isChecked()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.check_box));
            }else if (currentAge.equals("-1") || currentBrand.equals("-1") || currentUsage.equals("-1") || currentCondition.equals("-1") || currentLocation.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                Send.addClassifiedAd.setPictures(pictures);
                Send.addClassifiedAd.setMainPhone(mainPath);
                packagesPopUp();
            }
        }
    }

    private void pickImage(int img) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_CODE);
        } else {
            PickImageDialog.build(new PickSetup()).setOnPickResult(r -> {
                switch (img){
                    case 1:
                        image1.setImageBitmap(r.getBitmap());
                        image1Path = r.getPath();
                        image1Bitmap = r.getBitmap();
                        break;
                    case 2:
                        image2.setImageBitmap(r.getBitmap());
                        image2Path = r.getPath();
                        image2Bitmap = r.getBitmap();
                        break;
                    case 3:
                        image3.setImageBitmap(r.getBitmap());
                        image3Path = r.getPath();
                        image3Bitmap = r.getBitmap();
                        break;
                    case 4:
                        image4.setImageBitmap(r.getBitmap());
                        image4Path = r.getPath();
                        image4Bitmap = r.getBitmap();
                        break;
                    case 5:
                        image5.setImageBitmap(r.getBitmap());
                        image5Path = r.getPath();
                        image5Bitmap = r.getBitmap();
                        break;
                    case 6:
                        image6.setImageBitmap(r.getBitmap());
                        image6Path = r.getPath();
                        image6Bitmap = r.getBitmap();
                        break;
                    case 7:
                        image7.setImageBitmap(r.getBitmap());
                        image7Path = r.getPath();
                        image7Bitmap = r.getBitmap();
                        break;
                    case 8:
                        image8.setImageBitmap(r.getBitmap());
                        image8Path = r.getPath();
                        image8Bitmap = r.getBitmap();
                        break;
                    case 9:
                        image9.setImageBitmap(r.getBitmap());
                        image9Path = r.getPath();
                        image9Bitmap = r.getBitmap();
                        break;
                    case 10:
                        image10.setImageBitmap(r.getBitmap());
                        image10Path = r.getPath();
                        image10Bitmap = r.getBitmap();
                        break;
                }
            }).show(getActivity());
        }
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    private void startMapsActivity(){
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MAPS_ACTIVITY);
    }

    private void changeImages(ImageView img1,ImageView img2,ImageView img3,ImageView img4,ImageView img5,ImageView img6,ImageView img7,ImageView img8,ImageView img9,ImageView img10){
        img1.setBackground(mainActivity.getResources().getDrawable(R.drawable.orange_border));
        img2.setBackground(null);
        img3.setBackground(null);
        img4.setBackground(null);
        img5.setBackground(null);
        img6.setBackground(null);
        img7.setBackground(null);
        img8.setBackground(null);
        img9.setBackground(null);
        img10.setBackground(null);
    }

    public void packagesPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.packages_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.next);
        TextView close = myAdsOptionsAlertView.findViewById(R.id.close);
        RecyclerView packagesRV = myAdsOptionsAlertView.findViewById(R.id.packages_RV);
        ConstraintLayout freeLayout = myAdsOptionsAlertView.findViewById(R.id.free_layout);
        freeCB = myAdsOptionsAlertView.findViewById(R.id.free_CB);

        freeCB.setChecked(true);
        freeLayout.setOnClickListener(v -> freeCB.setChecked(true));
        freeCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                setPackagesAdapter(packagesRV);
            }
        });
        getPackages(packagesRV);
        close.setOnClickListener(v -> myAdsOptionsAlertBuilder.dismiss());
        done.setOnClickListener(v -> {
            addInitialClassifiedAd();
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void getPackages(RecyclerView recyclerView){
        packages.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest packagesRequest = new StringRequest(Request.Method.GET, Urls.Payment_URL+"GetPackages", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray packagesArray = new JSONArray(response);
                for (int i=1; i<packagesArray.length(); i++){
                    JSONObject packageObj = packagesArray.getJSONObject(i);
                    PackageFS packageFS = new PackageFS();
                    packageFS.setId(packageObj.getString("id"));
                    packageFS.setDays(packageObj.getString("numberOfDays"));
                    packageFS.setPrice(packageObj.getString("price"));
                    packages.add(packageFS);
                }
                setPackagesAdapter(recyclerView);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(packagesRequest);

    }

    private void setPackagesAdapter(RecyclerView recyclerView){
        PackagesAdapter businessPackagesAdapter = new PackagesAdapter(this,packages, -1);
        recyclerView.setAdapter(businessPackagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    private void clearMainPath(String imagePath, ImageView image){
        String subPath = imagePath.substring(imagePath.lastIndexOf("/")+1);
        if (subPath.equals(mainPath)){
            Glide.with(mainActivity).load(R.drawable.ic_classified).into(adImage);
            image.setBackground(null);
        }
        mainPath = "";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAPS_ACTIVITY && resultCode == RESULT_OK){
            assert data != null;
            address = data.getStringExtra("address");
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
            Send.electronicsAd.setLat(latitude);
            Send.electronicsAd.setLan(longitude);
            location.setText(address);
        }
    }
}