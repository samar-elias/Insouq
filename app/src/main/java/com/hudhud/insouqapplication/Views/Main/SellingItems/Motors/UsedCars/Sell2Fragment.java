package com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.UsedCars;

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
import android.view.inputmethod.EditorInfo;
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
import com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.UsedCars.Sell2FragmentDirections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Typeface.BOLD;

public class Sell2Fragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText motorTitleEdt, otherBrandEdt, otherModelEdt, otherTrimEdt, yearEdt;
    Spinner brandsSpinner, modelsSpinner, trimsSpinner;
    ArrayList<String> brandArTitles, brandEnTitles, brandIds = new ArrayList<>(), modelArTitles, modelEnTitles, trimArTitles, trimEnTitles, years;
    String brandCurrent = "", currentBrand = "", currentModel = "", currentTrim = "", currentYear = "";
    int thisYear;
    boolean brandSpinner = false, modelSpinner = false, trimSpinner = false;

    //Ad sample
    TextView motorsAdTitle, motorsAdYear;
    String brand = "", model = "";

    public Sell2Fragment() {
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
        return inflater.inflate(R.layout.fragment_sell2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getBrands();
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
        motorTitleEdt = view.findViewById(R.id.motor_title_edt);
        otherBrandEdt = view.findViewById(R.id.other_brand_edt);
        otherModelEdt = view.findViewById(R.id.other_model_edt);
        otherTrimEdt = view.findViewById(R.id.other_trim_edt);
        motorsAdTitle = view.findViewById(R.id.motor_title);
        motorsAdYear = view.findViewById(R.id.motor_year);
        brandsSpinner = view.findViewById(R.id.brand_spinner);
        modelsSpinner = view.findViewById(R.id.model_spinner);
        trimsSpinner = view.findViewById(R.id.trim_spinner);
        yearEdt = view.findViewById(R.id.years_edt);

        brandArTitles = new ArrayList<>();
        brandEnTitles = new ArrayList<>();
        modelArTitles = new ArrayList<>();
        modelEnTitles = new ArrayList<>();
        trimArTitles = new ArrayList<>();
        trimEnTitles = new ArrayList<>();
        years = new ArrayList<>();

        thisYear = Calendar.getInstance().get(Calendar.YEAR);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String motorTitle = String.valueOf(motorTitleEdt.getText());
            String otherBrand = String.valueOf(otherBrandEdt.getText());
            String otherModel = String.valueOf(otherModelEdt.getText());
            String otherTrim = String.valueOf(otherTrimEdt.getText());
            String year = String.valueOf(yearEdt.getText());
            if (motorTitle.isEmpty() || year.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (!year.startsWith("20") && !year.startsWith("19")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.year_old));
            }else if (otherBrandEdt.getVisibility() == View.VISIBLE && otherBrand.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherModelEdt.getVisibility() == View.VISIBLE && otherModel.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherTrimEdt.getVisibility() == View.VISIBLE && otherTrim.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (currentTrim.equals("-1") || currentBrand.equals("-1") || currentModel.equals("-1") || currentYear.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                currentYear = year;
                setData(motorTitle, otherBrand, otherModel, otherTrim);
            }

        });

        yearEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                motorsAdYear.setText(yearEdt.getText());
                motorsAdTitle.setText(brand+", "+model+", "+ yearEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otherBrandEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                brand = String.valueOf(otherBrandEdt.getText());
                motorsAdTitle.setText(brand+", "+model+", "+ yearEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otherModelEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                model = String.valueOf(motorTitleEdt.getText());
                motorsAdTitle.setText(brand+", "+model+", "+ yearEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void onSpinnerClick(){
        brandsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentBrand = "-1";
                    motorsAdTitle.setText(mainActivity.getResources().getString(R.string.ad_title));
                }else {
                    if (brandEnTitles.size() == 1){
                        otherBrandEdt.setVisibility(View.VISIBLE);
                        currentBrand = "";
                    }else if (i == brandEnTitles.size()-1){
                        otherBrandEdt.setVisibility(View.VISIBLE);
                        currentBrand = "";
                    }else {
                        otherBrandEdt.setVisibility(View.GONE);
                        currentBrand = brandIds.get(i);
                        if (AppDefs.language.equals("ar")){
                            brand = brandArTitles.get(i);
                        }else {
                            brand = brandEnTitles.get(i);
                        }
                        brandCurrent = brandEnTitles.get(i)+"-"+brandArTitles.get(i);
                        motorsAdTitle.setText(brand+", "+model+", "+ yearEdt.getText());
                    }
                }
                getModels();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        modelsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i == 0){
                    currentModel = "-1";
//                    motorsAdTitle.setText(mainActivity.getResources().getString(R.string.ad_title));
                }else {
                    if (modelEnTitles.size() == 1){
                        otherModelEdt.setVisibility(View.VISIBLE);
                        currentModel = "";
                    }else if (i == modelEnTitles.size()-1){
                        otherModelEdt.setVisibility(View.VISIBLE);
                        currentModel = "";
                    }else {
                        otherModelEdt.setVisibility(View.GONE);
                        currentModel = modelEnTitles.get(i)+"-"+modelArTitles.get(i);
                        if (AppDefs.language.equals("ar")){
                            model = modelArTitles.get(i);
                        }else {
                            model = modelEnTitles.get(i);
                        }
                        motorsAdTitle.setText(brand+", "+model+", "+ yearEdt.getText());
                    }
                }
                getTrims();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        trimsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentTrim = "-1";
                }else {
                    if (trimEnTitles.size() == 1){
                        otherTrimEdt.setVisibility(View.VISIBLE);
                        currentTrim = "";
                    }else if (i == trimEnTitles.size()-1){
                        otherTrimEdt.setVisibility(View.VISIBLE);
                        currentTrim = "";
                    }else {
                        otherTrimEdt.setVisibility(View.GONE);
                        currentTrim = trimEnTitles.get(i)+"-"+trimArTitles.get(i);
                    }
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

    private void getBrands(){
        brandArTitles.clear();
        brandEnTitles.clear();
        brandIds.clear();
        brandEnTitles.add(mainActivity.getResources().getString(R.string.what_brand_is_your_car));
        brandArTitles.add(mainActivity.getResources().getString(R.string.what_brand_is_your_car));
        brandIds.add("-1");
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest brandsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorMaker", response -> {
            try {
                JSONArray brandsArray = new JSONArray(response);
                for (int i=0; i<brandsArray.length(); i++){
                    JSONObject brandObj = brandsArray.getJSONObject(i);
                    brandArTitles.add(brandObj.getString("ar_Text"));
                    brandEnTitles.add(brandObj.getString("en_Text"));
                    brandIds.add(brandObj.getString("id"));
                }
                brandArTitles.add(mainActivity.getResources().getString(R.string.other));
                brandEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(brandsSpinner, brandArTitles);
                }else {
                    setSpinner(brandsSpinner, brandEnTitles);
                }
                currentBrand = brandIds.get(0);
                brandCurrent = brandEnTitles.get(0)+" - "+brandArTitles.get(0);
//                getModels();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
           mainActivity.hideProgressDialog();
           mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.brands), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(brandsRequest);
    }

    private void getModels(){
        modelArTitles.clear();
        modelEnTitles.clear();
        modelArTitles.add(mainActivity.getResources().getString(R.string.what_model_is_it));
        modelEnTitles.add(mainActivity.getResources().getString(R.string.what_model_is_it));
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest modelsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorModelByMakerId?makerId="+currentBrand, response -> {
            try {
                JSONArray modelsArray = new JSONArray(response);
                for (int i=0; i<modelsArray.length(); i++){
                    JSONObject modelObj = modelsArray.getJSONObject(i);
                    modelArTitles.add(modelObj.getString("ar_Text"));
                    modelEnTitles.add(modelObj.getString("en_Text"));
                }
                modelEnTitles.add(mainActivity.getResources().getString(R.string.other));
                modelArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(modelsSpinner, modelArTitles);
                }else {
                    setSpinner(modelsSpinner, modelEnTitles);
                }
                currentModel = modelEnTitles.get(0)+" - "+modelArTitles.get(0);
//                getTrims();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.model), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(modelsRequest);
    }

    private void getTrims(){
        trimArTitles.clear();
        trimEnTitles.clear();
        trimArTitles.add(mainActivity.getResources().getString(R.string.what_is_the_trim));
        trimEnTitles.add(mainActivity.getResources().getString(R.string.what_is_the_trim));
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest trimsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorTrimByModel?model="+currentModel, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray trimsArray = new JSONArray(response);
                for (int i=0; i<trimsArray.length(); i++){
                    JSONObject trimObj = trimsArray.getJSONObject(i);
                    trimArTitles.add(trimObj.getString("ar_Text"));
                    trimEnTitles.add(trimObj.getString("en_Text"));
                }
                trimArTitles.add(mainActivity.getResources().getString(R.string.other));
                trimEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(trimsSpinner, trimArTitles);
                }else {
                    setSpinner(trimsSpinner, trimEnTitles);
                }
                currentTrim = trimEnTitles.get(0)+" - "+trimArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.trim), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.trim), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(trimsRequest);
    }

    private void setData(String title, String otherBrand, String otherModel, String otherTrim){
        Send.addUsedCarsAd.setTitle(title);
        Send.addUsedCarsAd.setCategoryId("2");
        Send.addUsedCarsAd.setBrand(brandCurrent);
        Send.addUsedCarsAd.setOtherBrand(otherBrand);
        Send.addUsedCarsAd.setModel(currentModel);
        Send.addUsedCarsAd.setOtherModel(otherModel);
        Send.addUsedCarsAd.setTrim(currentTrim);
        Send.addUsedCarsAd.setOtherTrim(otherTrim);
        Send.addUsedCarsAd.setYear(currentYear);
        Send.exportTitle = String.valueOf(motorsAdTitle.getText());
        navController.navigate(Sell2FragmentDirections.actionSell2FragmentToSell3Fragment());
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }
}