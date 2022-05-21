package com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.Bikes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.Boats.InitialBoatsFragmentDirections;
import com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.Parts.InitialPartFragmentDirections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Typeface.BOLD;

public class InitialBikeFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText titleEdt, otherSubCategoryEdt, otherSubTypeEdt, yearsEdt;
    Spinner subCategorySpinner, subTypeSpinner;
    ArrayList<String> subCategoryArTitles, subCategoryEnTitles, subCategoryIds, subTypeArTitles, subTypeEnTitles, subTypeIds, years;
    String currentSubCat = "", currentSubType = "", currentYear = "", brand = "", year = "";
    int thisYear;
    boolean spinner1 = false, spinner2 = false;

    //Ad sample
    TextView adTitle, adYear;


    public InitialBikeFragment() {
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
        return inflater.inflate(R.layout.fragment_initial_bike, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getSubCategories();
        onSpinnerClick();
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

        continueBtn = view.findViewById(R.id.continue_btn);
        titleEdt = view.findViewById(R.id.motor_title_edt);
        otherSubCategoryEdt = view.findViewById(R.id.other_brand_edt);
        otherSubTypeEdt = view.findViewById(R.id.other_model_edt);
        yearsEdt = view.findViewById(R.id.years_edt);
        adTitle = view.findViewById(R.id.motor_title);
        adYear = view.findViewById(R.id.motor_year);
        subCategorySpinner = view.findViewById(R.id.brand_spinner);
        subTypeSpinner = view.findViewById(R.id.model_spinner);

        subCategoryArTitles = new ArrayList<>();
        subCategoryEnTitles = new ArrayList<>();
        subCategoryIds = new ArrayList<>();
        subTypeArTitles = new ArrayList<>();
        subTypeEnTitles = new ArrayList<>();
        subTypeIds = new ArrayList<>();

        thisYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String motorTitle = String.valueOf(titleEdt.getText());
            String otherSubCat = String.valueOf(otherSubCategoryEdt.getText());
            String otherSubType = String.valueOf(otherSubTypeEdt.getText());
            String year = String.valueOf(yearsEdt.getText());
            if (motorTitle.isEmpty() || year.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (!year.startsWith("20") && !year.startsWith("19")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.year_old));
            }else if (otherSubCategoryEdt.getVisibility() == View.VISIBLE && otherSubCat.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherSubTypeEdt.getVisibility() == View.VISIBLE && otherSubType.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (currentSubCat.equals("-1") ||currentSubType.equals("-1") || currentYear.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                Send.bikeTitle = String.valueOf(adTitle.getText());
                Send.bikeYear = String.valueOf(adYear.getText());
                currentYear = year;
                setData(motorTitle, otherSubCat, otherSubType);
            }

        });

        otherSubCategoryEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                brand = String.valueOf(otherSubCategoryEdt.getText());
                if (!String.valueOf(yearsEdt.getText()).isEmpty()){
                    adTitle.setText(brand+", "+year);
                }else {
                    adTitle.setText(brand);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        yearsEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                year = String.valueOf(yearsEdt.getText());
                if (!String.valueOf(yearsEdt.getText()).isEmpty()){
                    adTitle.setText(brand+", "+year);
                }else {
                    adTitle.setText(brand);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        titleEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                adTitle.setText(titleEdt.getText());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }

    private void onSpinnerClick(){
        subCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentSubCat = "-1";
                    brand = mainActivity.getResources().getString(R.string.brand);
                    getSubTypes();
                }else {
                    if (subCategoryEnTitles.size() == 1){
                        otherSubCategoryEdt.setVisibility(View.VISIBLE);
                        currentSubCat = "0";
                    }else if (i == subCategoryEnTitles.size()-1){
                        otherSubCategoryEdt.setVisibility(View.VISIBLE);
                        currentSubCat = "0";
                    }else {
                        otherSubCategoryEdt.setVisibility(View.GONE);
                        currentSubCat = subCategoryIds.get(i-1);
                        if (AppDefs.language.equals("ar")){
                            brand = subCategoryArTitles.get(i);
                        }else {
                            brand = subCategoryEnTitles.get(i);
                        }
                        getSubTypes();
                    }
                    if (!String.valueOf(yearsEdt.getText()).isEmpty()){
                        adTitle.setText(brand+", "+year);
                    }else {
                        adTitle.setText(brand);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentSubType = "-1";
                }else if (subTypeEnTitles.size() == 1){
                    otherSubTypeEdt.setVisibility(View.VISIBLE);
                    currentSubType = "0";
                }else if (i == subTypeEnTitles.size()-1){
                    otherSubTypeEdt.setVisibility(View.VISIBLE);
                    currentSubType = "0";
                }else {
                    otherSubTypeEdt.setVisibility(View.GONE);
                    currentSubType = subTypeIds.get(i-1);
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

    private void getSubCategories(){
        subCategoryArTitles.clear();
        subCategoryEnTitles.clear();
        subCategoryArTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_bike_category));
        subCategoryEnTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_bike_category));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoryRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId=9", response -> {
            try {
                JSONArray subCategoryArray = new JSONArray(response);
                for (int i=0; i<subCategoryArray.length(); i++){
                    JSONObject subCategoryObj = subCategoryArray.getJSONObject(i);
                    subCategoryArTitles.add(subCategoryObj.getString("ar_Name"));
                    subCategoryEnTitles.add(subCategoryObj.getString("en_Name"));
                    subCategoryIds.add(subCategoryObj.getString("id"));
                }
                subCategoryArTitles.add(mainActivity.getResources().getString(R.string.other));
                subCategoryEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(subCategorySpinner, subCategoryArTitles);
                }else {
                    setSpinner(subCategorySpinner, subCategoryEnTitles);
                }
                if (subCategoryIds.size() > 0){
                    currentSubCat = subCategoryIds.get(0);
                }else {
                    currentSubCat = "0";
                }
                getSubTypes();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoryRequest);
    }

    private void getSubTypes(){
        subTypeArTitles.clear();
        subTypeEnTitles.clear();
        subTypeArTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_bike_sub_category));
        subTypeEnTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_bike_sub_category));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypeRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+currentSubCat, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subTypeArray = new JSONArray(response);
                for (int i=0; i<subTypeArray.length(); i++){
                    JSONObject subTypeObj = subTypeArray.getJSONObject(i);
                    subTypeArTitles.add(subTypeObj.getString("ar_Name"));
                    subTypeEnTitles.add(subTypeObj.getString("en_Name"));
                    subTypeIds.add(subTypeObj.getString("id"));
                }
                subTypeEnTitles.add(mainActivity.getResources().getString(R.string.other));
                subTypeArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(subTypeSpinner,subTypeArTitles);
                }else {
                    setSpinner(subTypeSpinner, subTypeEnTitles);
                }
                if(subTypeIds.size() > 0){
                    currentSubType = subTypeIds.get(0);
                }else {
                    currentSubType = "0";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subTypeRequest);
    }

    private void setData(String title, String otherSubCat, String otherType){
        Send.addBikeAd.setTitle(title);
        Send.addBikeAd.setCategoryId("9");
        Send.addBikeAd.setSubCategoryId(currentSubCat);
        Send.addBikeAd.setOtherSubCategory(otherSubCat);
        Send.addBikeAd.setSubTypeId(currentSubType);
        Send.addBikeAd.setOtherSubType(otherType);
        Send.addBikeAd.setYear(currentYear);
        navController.navigate(InitialBikeFragmentDirections.actionInitialBikeFragmentToFullBikeFragment());
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }
}