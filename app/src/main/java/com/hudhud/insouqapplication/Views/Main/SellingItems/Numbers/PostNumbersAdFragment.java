package com.hudhud.insouqapplication.Views.Main.SellingItems.Numbers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;

public class PostNumbersAdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    private static final int REQUEST_CODE = 101;
    EditText plateNumberTitleEdt, plateNumberPriceEdt, phoneNumberEdt, descriptionEdt, plateNumberEdt;
    Spinner emiratesSpinner, plateTypeSpinner, plateCodeSpinner, locationSpinner;
    TextView location, adTitle, adPrice, adLocation;
    ArrayList<String> emiratesArTitles, emiratesEnTitles, platesTypesAr, plateTypesEn, plateCodes, locationsArTitles, locationsEnTitles;
    ArrayList<Integer> platesIds;
    ImageView adImage;
    String currentEmirate = "", currentPlate = "", currentPlateCode = "", currentPlateNumber = "", latitude = "", longitude = "", address = "", currentLocation = "";
    int currentPlateId = 0, subCatId;
    ConstraintLayout abuDhabiBikeLayout, abuDhabiClassicLayout, abuDhabiPrivateLayout, ajmanPrivateLayout, dubaiBikeLayout, dubaiClassicLayout, dubaiPrivateLayout, fujairahPrivateLayout, rasAlKhaimahClassicLayout, rasAlKhaimahPrivateLayout, sharjahPrivateLayout, sharjahClassicLayout, ummAlQuwainPrivateLayout;
    TextView abuDhabiBikePlateCode, abuDhabiBikePlateNumber, abuDhabiClassicPlateCode, abuDhabiClassicPlateNumber, abuDhabiPrivatePlateCode, abuDhabiPrivatePlateNumber, ajmanPrivatePlateCode, ajmanPrivatePlateNumber, dubaiBikePlateCode, dubaiBikePlateNumber, dubaiClassicPlateNumber, dubaiPrivatePlateCode, dubaiPrivatePlateNumber, fujairahPrivatePlateCode, fujairahPrivatePlateNumber, rasAlkhaimahClassicPlateNumber, rasAlkhaimahPrivatePlateCode, rasAlkhaimahPrivatePlateNumber, sharjahPrivatePlateCode, sharjahPrivatePlateNumber, sharjahClassicPlateNumber, ummAlQuwainPrivatePlateCode, ummAlQuwainPrivatePlateNumber;
    CheckBox agreementCheckBok;
    boolean spinner1 = false, spinner2 = false, spinner3 = false, spinner4 = false;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;

    public PostNumbersAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_numbers_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getEmirates();
        getLocations();
        onSpinnerClick();
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
        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        continueBtn = view.findViewById(R.id.continue_btn);
        agreementCheckBok = view.findViewById(R.id.agreement_checkbox);
        plateNumberTitleEdt = view.findViewById(R.id.numbers_ad_title_edt);
        plateNumberPriceEdt = view.findViewById(R.id.ad_price_edt);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        descriptionEdt = view.findViewById(R.id.ad_short_description);
        plateNumberEdt = view.findViewById(R.id.number_edt);
        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }

        emiratesSpinner = view.findViewById(R.id.emirates_spinner);
        plateTypeSpinner = view.findViewById(R.id.plate_type_spinner);
        plateCodeSpinner = view.findViewById(R.id.plate_code_spinner);
        locationSpinner = view.findViewById(R.id.locations_spinner);

        location = view.findViewById(R.id.location);
        adTitle = view.findViewById(R.id.number_title);
        adLocation = view.findViewById(R.id.ad_location);
        adPrice = view.findViewById(R.id.number_price);
        adImage = view.findViewById(R.id.plate_image);

        emiratesArTitles = new ArrayList<>();
        emiratesEnTitles = new ArrayList<>();
        platesTypesAr = new ArrayList<>();
        plateTypesEn = new ArrayList<>();
        plateCodes = new ArrayList<>();
        platesIds = new ArrayList<>();
        locationsArTitles = new ArrayList<>();
        locationsEnTitles = new ArrayList<>();

        abuDhabiBikeLayout = view.findViewById(R.id.abu_dhabi_bike_layout);
        abuDhabiClassicLayout = view.findViewById(R.id.abu_dhabi_classic_layout);
        abuDhabiPrivateLayout = view.findViewById(R.id.abu_dhabi_private_layout);
        ajmanPrivateLayout = view.findViewById(R.id.ajman_private_layout);
        dubaiBikeLayout = view.findViewById(R.id.dubai_bike_layout);
        dubaiClassicLayout = view.findViewById(R.id.dubai_classic_layout);
        dubaiPrivateLayout = view.findViewById(R.id.dubai_private_layout);
        fujairahPrivateLayout = view.findViewById(R.id.fujairah_private_layout);
        rasAlKhaimahClassicLayout = view.findViewById(R.id.ras_alkhaimah_classic_layout);
        rasAlKhaimahPrivateLayout = view.findViewById(R.id.ras_alkhaimah_private_layout);
        sharjahPrivateLayout = view.findViewById(R.id.sharjah_private_layout);
        sharjahClassicLayout = view.findViewById(R.id.sharjah_classic_layout);
        ummAlQuwainPrivateLayout = view.findViewById(R.id.umm_alqwain_private_layout);

        abuDhabiBikePlateCode = view.findViewById(R.id.adb_plate_code);
        abuDhabiClassicPlateCode = view.findViewById(R.id.adc_plate_code);
        abuDhabiPrivatePlateCode = view.findViewById(R.id.adp_plate_code);
        ajmanPrivatePlateCode = view.findViewById(R.id.ap_plate_code);
        dubaiBikePlateCode = view.findViewById(R.id.db_plate_code);
        dubaiPrivatePlateCode = view.findViewById(R.id.dp_plate_code);
        fujairahPrivatePlateCode = view.findViewById(R.id.fp_plate_code);
        rasAlkhaimahPrivatePlateCode = view.findViewById(R.id.rkp_plate_code);
        sharjahPrivatePlateCode = view.findViewById(R.id.sp_plate_code);
        ummAlQuwainPrivatePlateCode = view.findViewById(R.id.uqp_plate_code);

        abuDhabiBikePlateNumber = view.findViewById(R.id.adb_plate_number);
        abuDhabiClassicPlateNumber = view.findViewById(R.id.adc_plate_number);
        abuDhabiPrivatePlateNumber = view.findViewById(R.id.adp_plate_number);
        ajmanPrivatePlateNumber = view.findViewById(R.id.ap_plate_number);
        dubaiBikePlateNumber = view.findViewById(R.id.db_plate_number);
        dubaiClassicPlateNumber = view.findViewById(R.id.dc_plate_number);
        dubaiPrivatePlateNumber = view.findViewById(R.id.dp_plate_number);
        fujairahPrivatePlateNumber = view.findViewById(R.id.fp_plate_number);
        rasAlkhaimahClassicPlateNumber = view.findViewById(R.id.rkc_plate_number);
        rasAlkhaimahPrivatePlateNumber = view.findViewById(R.id.rkp_plate_number);
        sharjahPrivatePlateNumber = view.findViewById(R.id.sp_plate_number);
        sharjahClassicPlateNumber = view.findViewById(R.id.sc_plate_number);
        ummAlQuwainPrivatePlateNumber = view.findViewById(R.id.uqp_plate_number);

        if (getArguments() != null){
            subCatId = PostNumbersAdFragmentArgs.fromBundle(getArguments()).getSubCatId();
            Send.plateNumberAd.setCategoryId(String.valueOf(subCatId));
        }
        phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

//        plateNumberEdt.setOnEditorActionListener((textView, i, keyEvent) -> {
//            switch (i) {
//                case EditorInfo.IME_ACTION_DONE:
//                case EditorInfo.IME_ACTION_NEXT:
//                case EditorInfo.IME_ACTION_PREVIOUS:
//                    currentPlateNumber = String.valueOf(plateNumberEdt.getText());
//                    setPlateImage();
//                    mainActivity.hideKeyboard();
//                    return true;
//            }
//            return false;
//        });

        plateNumberTitleEdt.setOnEditorActionListener(((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    adTitle.setText(String.valueOf(plateNumberTitleEdt.getText()));
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        }));

        plateNumberPriceEdt.setOnEditorActionListener(((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    adPrice.setText(String.valueOf(plateNumberPriceEdt.getText()));
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        }));

        location.setOnClickListener(view -> startMapsActivity());

        continueBtn.setOnClickListener(view -> {
            String adTitle = String.valueOf(plateNumberTitleEdt.getText());
            String description  = String.valueOf(descriptionEdt.getText());
            String price = String.valueOf(plateNumberPriceEdt.getText());
            String phoneNum = String.valueOf(phoneNumberEdt.getText());
            String plateNumber = String.valueOf(plateNumberEdt.getText());
            if (adTitle.isEmpty() || price.isEmpty() || phoneNum.isEmpty() || plateNumber.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_number_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.location_error));
            }else  if (!agreementCheckBok.isChecked()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.check_box));
            }else if (currentEmirate.equals("-1") || currentLocation.equals("-1") || currentPlate.equals("-1") || currentPlateCode.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_number_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                Send.plateNumberAd.setDescription(description);
                Send.plateNumberAd.setEmirate(currentEmirate);
                Send.plateNumberAd.setLan(longitude);
                Send.plateNumberAd.setLat(latitude);
                Send.plateNumberAd.setLocation(currentLocation);
                Send.plateNumberAd.setNumber(plateNumber);
                Send.plateNumberAd.setPhoneNumber(phoneNum);
                Send.plateNumberAd.setPlateCode(currentPlateCode);
                Send.plateNumberAd.setPlateType(currentPlate);
                price = mainActivity.formatter.format(Double.parseDouble(price));
                Send.plateNumberAd.setPrice(price);
                Send.plateNumberAd.setTitle(adTitle);
//                postPlateNumberAd();
                packagesPopUp();
            }
        });

        plateNumberEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentPlateNumber = String.valueOf(plateNumberEdt.getText());
                setPlateImage();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void onSpinnerClick(){
        emiratesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentEmirate = "-1";
                }else {
                    currentEmirate = emiratesEnTitles.get(i)+"-"+emiratesArTitles.get(i);
                }
                getPlateType(currentEmirate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        plateTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentPlate = "-1";
                }else {
                    currentPlate = plateTypesEn.get(i)+"-"+platesTypesAr.get(i);
                    currentPlateId = platesIds.get(i);

                    setPlateImage();
                }
                getPlateCodes(currentEmirate, currentPlate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        plateCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentPlateCode = "-1";
                }else {
                    if (plateCodes.size()>0){
                        currentPlateCode = plateCodes.get(i);
                    }
                    setPlateImage();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentLocation = locationsEnTitles.get(i)+"-"+ locationsArTitles.get(i);
                    adLocation.setText(currentLocation);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void setPlateImage(){
        switch (currentPlateId){
            case 3:
                abuDhabiBikeLayout.setVisibility(View.VISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiBikePlateCode.setText(currentPlateCode);
                abuDhabiBikePlateNumber.setText(currentPlateNumber);
                break;
            case 2:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.VISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicPlateCode.setText(currentPlateCode);
                abuDhabiClassicPlateNumber.setText(currentPlateNumber);
                break;
            case 1:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.VISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivatePlateCode.setText(currentPlateCode);
                abuDhabiPrivatePlateNumber.setText(currentPlateNumber);
                break;
            case 8:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.VISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                ajmanPrivatePlateCode.setText(currentPlateCode);
                ajmanPrivatePlateNumber.setText(currentPlateNumber);
                break;
            case 13:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.VISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikePlateCode.setText(currentPlateCode);
                dubaiBikePlateNumber.setText(currentPlateNumber);
                break;
            case 5:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.VISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiClassicPlateNumber.setText(currentPlateNumber);
                break;
            case 4:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.VISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivatePlateCode.setText(currentPlateCode);
                dubaiPrivatePlateNumber.setText(currentPlateNumber);
                break;
            case 9:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.VISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivatePlateCode.setText(currentPlateCode);
                fujairahPrivatePlateNumber.setText(currentPlateNumber);
                break;
            case 6:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.VISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlkhaimahClassicPlateNumber.setText(currentPlateNumber);
                break;
            case 11:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.VISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlkhaimahPrivatePlateCode.setText(currentPlateCode);
                rasAlkhaimahPrivatePlateNumber.setText(currentPlateNumber);
                break;
            case 12:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.VISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                sharjahClassicPlateNumber.setText(currentPlateNumber);
                break;
            case 7:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.VISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                sharjahPrivatePlateCode.setText(currentPlateCode);
                sharjahPrivatePlateNumber.setText(currentPlateNumber);
                break;
            case 10:
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.VISIBLE);
                ummAlQuwainPrivatePlateCode.setText(currentPlateCode);
                ummAlQuwainPrivatePlateNumber.setText(currentPlateNumber);
                break;

        }
    }

    private void getEmirates(){
        emiratesEnTitles.clear();
        emiratesArTitles.clear();
        emiratesArTitles.add(mainActivity.getResources().getString(R.string.select_emirate));
        emiratesEnTitles.add(mainActivity.getResources().getString(R.string.select_emirate));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest emiratesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllEmirate", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray emiratesArray = new JSONArray(response);
                for (int i=0; i<emiratesArray.length(); i++){
                    JSONObject emirateObj = emiratesArray.getJSONObject(i);
                    emiratesArTitles.add(emirateObj.getString("ar_Text"));
                    emiratesEnTitles.add(emirateObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(emiratesSpinner, emiratesArTitles);
                }else {
                    setSpinner(emiratesSpinner, emiratesEnTitles);
                }
                currentEmirate = emiratesEnTitles.get(0)+"-"+emiratesArTitles.get(0);
//                getPlateType(currentEmirate);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.emirate), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.emirate), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(emiratesRequest);
    }

    private void getPlateType(String emirate){
        platesTypesAr.clear();
        plateTypesEn.clear();
        platesIds.clear();
        platesTypesAr.add(mainActivity.getResources().getString(R.string.select_plate_type));
        plateTypesEn.add(mainActivity.getResources().getString(R.string.select_plate_type));
        platesIds.add(-1);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest plateTypesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllPlateTypeByEmirate?emirate="+emirate, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray platesArray = new JSONArray(response);
                for (int i=0; i<platesArray.length(); i++){
                    JSONObject plateObj = platesArray.getJSONObject(i);
                    platesIds.add(plateObj.getInt("id"));
                    platesTypesAr.add(plateObj.getString("ar_Text"));
                    plateTypesEn.add(plateObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(plateTypeSpinner, platesTypesAr);
                }else {
                    setSpinner(plateTypeSpinner, plateTypesEn);
                }
                currentPlate = plateTypesEn.get(0)+"-"+platesTypesAr.get(0);
                currentPlateId = platesIds.get(0);
                setPlateImage();
//                getPlateCodes(currentEmirate, currentPlate);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_type), mainActivity.getResources().getString(R.string.error_occured));
            }

            if (AppDefs.language.equals("ar")){
                setSpinner(plateTypeSpinner, platesTypesAr);
            }else {
                setSpinner(plateTypeSpinner, plateTypesEn);
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_type), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(plateTypesRequest);
    }

    private void getPlateCodes(String emirate, String plateType){
        plateCodes.clear();
        plateCodes.add(mainActivity.getResources().getString(R.string.select_plate_code));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest plateCodeRequest = new StringRequest(Request.Method.POST, Urls.DropDowns_URL+"GetAllPlateCode", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray array = new JSONArray(response);
                for (int i=0; i<array.length(); i++){
                    JSONObject plateCodeObj = array.getJSONObject(i);
                    plateCodes.add(plateCodeObj.getString("value"));
                }
                setSpinner(plateCodeSpinner, plateCodes);
                if (plateCodes.size() > 0) {
                    currentPlateCode = plateCodes.get(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_code), mainActivity.getResources().getString(R.string.error_occured));
            }
            setSpinner(plateCodeSpinner, plateCodes);
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_code), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("plateType", plateType);
                params.put("emirate", emirate);
                return params;
            }
        };
        mainActivity.queue.add(plateCodeRequest);
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
                    setSpinner(locationSpinner, locationsArTitles);
                }else {
                    setSpinner(locationSpinner, locationsEnTitles);
                }
                currentLocation = locationsEnTitles.get(0)+"-"+locationsArTitles.get(0);
                adLocation.setText(currentLocation);
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
        JSONObject plateNumberObj = new JSONObject();
        try {
            plateNumberObj.put("title", Send.plateNumberAd.getTitle());
            plateNumberObj.put("description", Send.plateNumberAd.getDescription());
            plateNumberObj.put("location", Send.plateNumberAd.getLocation());
            plateNumberObj.put("lat", Send.plateNumberAd.getLat());
            plateNumberObj.put("lng", Send.plateNumberAd.getLan());
            plateNumberObj.put("categoryId", Send.plateNumberAd.getCategoryId());
            plateNumberObj.put("number", Send.plateNumberAd.getNumber());
            plateNumberObj.put("emirate", Send.plateNumberAd.getEmirate());
            plateNumberObj.put("plateType", Send.plateNumberAd.getPlateType());
            plateNumberObj.put("plateCode", Send.plateNumberAd.getPlateCode());
            plateNumberObj.put("price", Send.plateNumberAd.getPrice());
            plateNumberObj.put("phoneNumber", Send.plateNumberAd.getPhoneNumber());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest plateNumberRequest = new JsonObjectRequest(Request.Method.POST, Urls.NumberAds_URL+"Add", plateNumberObj, response -> {
            mainActivity.hideProgressDialog();
            try {
                if (response.getString("isSuccess").equals("true")){
                    Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                    startActivity("");
                    if (packageId.isEmpty()){
                        startActivity("");
                    }else {
                        navController.navigate(PostNumbersAdFragmentDirections.actionPostNumbersAdFragmentToPaymentFragment(packageId, ""));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_number_ad), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.plate_number_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
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

    private void startMapsActivity(){
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MAPS_ACTIVITY);
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
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
        PackagesAdapter2 businessPackagesAdapter = new PackagesAdapter2(this,packages, -1);
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