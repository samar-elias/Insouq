package com.hudhud.insouqapplication.Views.Main.SellingItems.Numbers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.SellingItems.Classifieds.PostClassifiedsGroup1AdFragmentDirections;
import com.hudhud.insouqapplication.Views.map.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Url;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;

public class PostMobileNumberAdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    private static final int REQUEST_CODE = 101;
    EditText mobileNumberTitleEdt, mobileNumberPriceEdt, phoneNumberEdt, descriptionEdt, mobileNumberEdt;
    Spinner operatorsSpinner, codesSpinner, numberPlanSpinner, locationsSpinner;
    TextView location, adTitle, adPrice, adLocation, mobileNumberCode, mobileNumber;
    ImageView mobileNumberImage;
    int subCatId;
    ArrayList<String> operatorsIds, operatorsArTitles, operatorsEnTitles, mobileCodes, plansArTitles, plansEnTitles, locationsArTitles, locationsEnTitles;
    String currentOperator = "", currentCode = "", currentPlan = "", currentLocation = "", longitude = "", latitude = "", address = ""
            , currentOperatorId = "";
    CheckBox agreementCheckBok;
    boolean spinner1 = false, spinner2 = false, spinner3 = false, spinner4 = false;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;

    public PostMobileNumberAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_mobile_number_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onSpinnerClick();
        onClick();
        getAllOperators();
        getNumberPlans();
        getLocations();
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

        continueBtn = view.findViewById(R.id.continue_btn);
        agreementCheckBok = view.findViewById(R.id.agreement_checkbox);
        mobileNumberTitleEdt = view.findViewById(R.id.mob_number_ad_title);
        mobileNumberPriceEdt = view.findViewById(R.id.ad_price);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        descriptionEdt = view.findViewById(R.id.ad_short_description);
        mobileNumberEdt = view.findViewById(R.id.number_edt);

        operatorsSpinner = view.findViewById(R.id.operators_spinner);
        codesSpinner = view.findViewById(R.id.codes_spinner);
        numberPlanSpinner = view.findViewById(R.id.number_plan_spinner);
        locationsSpinner = view.findViewById(R.id.locations_spinner);

        adTitle = view.findViewById(R.id.number_title);
        adLocation = view.findViewById(R.id.number_location);
        adPrice = view.findViewById(R.id.number_price);
        location = view.findViewById(R.id.location);
        mobileNumberCode = view.findViewById(R.id.mobile_number_code);
        mobileNumber = view.findViewById(R.id.mobile_number);
        mobileNumberImage = view.findViewById(R.id.mobile_image);
        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }

        operatorsIds = new ArrayList<>();
        operatorsArTitles = new ArrayList<>();
        operatorsEnTitles = new ArrayList<>();
        mobileCodes = new ArrayList<>();
        plansArTitles = new ArrayList<>();
        plansEnTitles = new ArrayList<>();
        locationsArTitles = new ArrayList<>();
        locationsEnTitles = new ArrayList<>();

        if (getArguments() != null){
            subCatId = PostMobileNumberAdFragmentArgs.fromBundle(getArguments()).getSubCatId();
            Send.mobileNumberAd.setCategoryId(String.valueOf(subCatId));
        }
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        location.setOnClickListener(view -> startMapsActivity());

        mobileNumberTitleEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    adTitle.setText(String.valueOf(mobileNumberTitleEdt.getText()));
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        });

        mobileNumberPriceEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    adPrice.setText(String.valueOf(mobileNumberPriceEdt.getText()));
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        });

        mobileNumberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobileNumber.setText(String.valueOf(mobileNumberEdt.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobileNumberEdt.setOnEditorActionListener((textView, i, keyEvent) ->{
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        });

        continueBtn.setOnClickListener(view -> {
            String adTitle  = String.valueOf(mobileNumberTitleEdt.getText());
            String adPrice  = String.valueOf(mobileNumberPriceEdt.getText());
            String phoneNum  = String.valueOf(phoneNumberEdt.getText());
            String number  = String.valueOf(mobileNumberEdt.getText());
            String description = String.valueOf(descriptionEdt.getText());
            if (adTitle.isEmpty() || adPrice.isEmpty() || phoneNum.isEmpty() || number.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.mobile_number), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.location_error));
            }else  if (!agreementCheckBok.isChecked()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.check_box));
            }else if (currentOperator.equals("-1") || currentLocation.equals("-1") || currentCode.equals("-1") || currentPlan.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.mobile_number), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                Send.mobileNumberAd.setCode(currentCode);
                Send.mobileNumberAd.setDescription(description);
                Send.mobileNumberAd.setLatitude(latitude);
                Send.mobileNumberAd.setLocation(currentLocation);
                Send.mobileNumberAd.setLongitude(longitude);
                Send.mobileNumberAd.setMobileNumberPlan(currentPlan);
                Send.mobileNumberAd.setNumber(number);
                Send.mobileNumberAd.setOperator(currentOperator);
                Send.mobileNumberAd.setPhoneNumber(phoneNum);
                adPrice = mainActivity.formatter.format(Double.parseDouble(adPrice));
                Send.mobileNumberAd.setPrice(adPrice);
                Send.mobileNumberAd.setTitle(adTitle);
//                postPlateNumberAd();
                packagesPopUp();
            }
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        operatorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentOperator = "";
                }else {
                    currentOperator = operatorsEnTitles.get(i)+"-"+operatorsArTitles.get(i);
                    currentOperatorId = operatorsIds.get(i);
                    getMobileNumberCodes(currentOperator);
                    setNumberImage();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        codesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentCode = "-1";
                    mobileNumberCode.setText("000");
                }else {
                    currentCode = mobileCodes.get(i);
                    mobileNumberCode.setText(currentCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        numberPlanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentPlan = "-1";
                }else {
                    currentPlan = plansEnTitles.get(i)+"-"+plansArTitles.get(i);
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
                    spinner4 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                   currentLocation = "-1";
                }else {
                    currentLocation = locationsEnTitles.get(i)+"-"+locationsArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adLocation.setText(locationsArTitles.get(0));
                    }else {
                        adLocation.setText(locationsEnTitles.get(0));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setNumberImage(){
        switch (currentOperatorId){
            case "1":
                Glide.with(mainActivity).load(R.drawable.etisalat).into(mobileNumberImage);
                break;
            case "2":
                Glide.with(mainActivity).load(R.drawable.du).into(mobileNumberImage);
                break;
        }
    }

    private void getAllOperators(){
        operatorsArTitles.clear();
        operatorsEnTitles.clear();
        operatorsIds.clear();
        operatorsArTitles.add(mainActivity.getResources().getString(R.string.select_operator));
        operatorsEnTitles.add(mainActivity.getResources().getString(R.string.select_operator));
        operatorsIds.add(String.valueOf(-1));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest getOperatorsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllOperator", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray operatorsArray = new JSONArray(response);
                for (int i=0; i<operatorsArray.length(); i++){
                    JSONObject operatorObj = operatorsArray.getJSONObject(i);
                    operatorsIds.add(operatorObj.getString("id"));
                    operatorsArTitles.add(operatorObj.getString("ar_Text"));
                    operatorsEnTitles.add(operatorObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(operatorsSpinner, operatorsArTitles);
                }else {
                    setSpinner(operatorsSpinner, operatorsEnTitles);
                }
                currentOperatorId = operatorsIds.get(0);
                currentOperator = operatorsEnTitles.get(0)+"-"+operatorsArTitles.get(0);
                getMobileNumberCodes(currentOperator);
                setNumberImage();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.operator), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.operator), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(getOperatorsRequest);
    }

    private void getMobileNumberCodes(String operator){
        mobileCodes.clear();
        mobileCodes.add(mainActivity.getResources().getString(R.string.select_code));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest mobileCodesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMobileNumberCodeByOperator?operatorName="+operator, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray codesArray = new JSONArray(response);
                for (int i=0; i< codesArray.length(); i++){
                    JSONObject codeObj = codesArray.getJSONObject(i);
                    mobileCodes.add(codeObj.getString("value"));
                }
                currentCode = mobileCodes.get(0);
                mobileNumberCode.setText(currentCode);
                setSpinner(codesSpinner, mobileCodes);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.code), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
           mainActivity.hideProgressDialog();
           mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.code), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(mobileCodesRequest);
    }

    private void getNumberPlans(){
        plansEnTitles.clear();
        plansArTitles.clear();
        plansArTitles.add(mainActivity.getResources().getString(R.string.select_mobile_plan));
        plansEnTitles.add(mainActivity.getResources().getString(R.string.select_mobile_plan));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest plansRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllNumberPlans", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray plansArray = new JSONArray(response);
                for (int i=0; i<plansArray.length(); i++){
                    JSONObject planObj = plansArray.getJSONObject(i);
                    plansArTitles.add(planObj.getString("ar_Text"));
                    plansEnTitles.add(planObj.getString("en_Text"));
                }
                currentPlan = plansArTitles.get(0)+"-"+plansEnTitles.get(0);
                if (AppDefs.language.equals("ar")){
                    setSpinner(numberPlanSpinner, plansArTitles);
                }else {
                    setSpinner(numberPlanSpinner, plansEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number_plan), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.number_plan), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(plansRequest);
    }

    private void getLocations(){
        locationsEnTitles.clear();
        locationsArTitles.clear();
        locationsArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationsEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    locationsArTitles.add(locationObj.getString("ar_Text"));
                    locationsEnTitles.add(locationObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(locationsSpinner, locationsArTitles);
                    adLocation.setText(locationsArTitles.get(0));
                }else {
                    setSpinner(locationsSpinner, locationsEnTitles);
                    adLocation.setText(locationsEnTitles.get(0));
                }
                currentLocation = locationsEnTitles.get(0)+"-"+locationsArTitles.get(0);

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

    private void  postPlateNumberAd(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject mobileNumberObj = new JSONObject();
        try {
            mobileNumberObj.put("title", Send.mobileNumberAd.getTitle());
            mobileNumberObj.put("description", Send.mobileNumberAd.getDescription());
            mobileNumberObj.put("location", Send.mobileNumberAd.getLocation());
            mobileNumberObj.put("lat", Send.mobileNumberAd.getLatitude());
            mobileNumberObj.put("lng", Send.mobileNumberAd.getLocation());
            mobileNumberObj.put("categoryId", Send.mobileNumberAd.getCategoryId());
            mobileNumberObj.put("operator", Send.mobileNumberAd.getOperator());
            mobileNumberObj.put("code", Send.mobileNumberAd.getCode());
            mobileNumberObj.put("mobileNumberPlan", Send.mobileNumberAd.getMobileNumberPlan());
            mobileNumberObj.put("number", Send.mobileNumberAd.getNumber());
            mobileNumberObj.put("price", Send.mobileNumberAd.getPrice());
            mobileNumberObj.put("phoneNumber", Send.mobileNumberAd.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest plateNumberRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"Add", mobileNumberObj, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getString("isSuccess").equals("true")){
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                    startActivity("");
                    if (packageId.isEmpty()){
                        startActivity("");
                    }else {
                        navController.navigate(PostMobileNumberAdFragmentDirections.actionPostMobileNumberAdFragmentToPaymentFragment(packageId, ""));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.mobile_number), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.mobile_number), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(plateNumberRequest);
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    private void startMapsActivity(){
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MAPS_ACTIVITY);
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
            postPlateNumberAd();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAPS_ACTIVITY && resultCode == RESULT_OK){
            assert data != null;
            address = data.getStringExtra("address");
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");

            location.setText(address);
        }
    }
}