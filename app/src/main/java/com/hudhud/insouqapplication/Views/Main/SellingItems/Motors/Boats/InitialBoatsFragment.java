package com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.Boats;

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
import com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.UsedCars.Sell2FragmentDirections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Typeface.BOLD;

public class InitialBoatsFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText motorTitleEdt, otherBrandEdt, otherModelEdt, yearEdt;
    TextView motorsAdTitle, motorsAdYear;
    Spinner brandsSpinner, modelsSpinner;
    ArrayList<String> brandArTitles, brandEnTitles, brandIds, modelArTitles, modelEnTitles, modelIds, years;
    String currentBrand = "", currentModel = "", currentYear = "";
    int thisYear;
    boolean spinner1 = false, spinner2 = false;

    public InitialBoatsFragment() {
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
        return inflater.inflate(R.layout.fragment_initial_boats, container, false);
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
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        continueBtn = view.findViewById(R.id.continue_btn);
        motorTitleEdt = view.findViewById(R.id.motor_title_edt);
        otherBrandEdt = view.findViewById(R.id.other_brand_edt);
        otherModelEdt = view.findViewById(R.id.other_model_edt);
        motorsAdTitle = view.findViewById(R.id.motor_title);
        motorsAdYear = view.findViewById(R.id.motor_year);
        brandsSpinner = view.findViewById(R.id.brand_spinner);
        modelsSpinner = view.findViewById(R.id.model_spinner);
        yearEdt = view.findViewById(R.id.years_edt);

        brandArTitles = new ArrayList<>();
        brandEnTitles = new ArrayList<>();
        brandIds = new ArrayList<>();
        modelArTitles = new ArrayList<>();
        modelEnTitles = new ArrayList<>();
        modelIds = new ArrayList<>();
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
            String year = String.valueOf(yearEdt.getText());
            if (motorTitle.isEmpty() || year.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (!year.startsWith("20") && !year.startsWith("19")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.year_old));
            }else if (otherBrandEdt.getVisibility() == View.VISIBLE && otherBrand.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherModelEdt.getVisibility() == View.VISIBLE && otherModel.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (currentBrand.equals("-1") || currentModel.equals("-1") || currentYear.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                currentYear = year;
                setData(motorTitle, otherBrand, otherModel);
            }

        });
        motorTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                motorsAdTitle.setText(String.valueOf(motorTitleEdt.getText()));
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
                    spinner1 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentBrand = "-1";
                }else {
                    if (brandEnTitles.size() == 1){
                        otherBrandEdt.setVisibility(View.VISIBLE);
                        currentBrand = "0";
                    }else if (i == brandEnTitles.size()-1){
                        otherBrandEdt.setVisibility(View.VISIBLE);
                        currentBrand = "0";
                    }else {
                        otherBrandEdt.setVisibility(View.GONE);
                        currentBrand = brandIds.get(i-1);
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
                    spinner2 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i == 0){
                    currentModel = "-1";
                }else{
                    if (modelEnTitles.size() == 1){
                        otherModelEdt.setVisibility(View.VISIBLE);
                        currentModel = "0";
                    }else if (i == modelEnTitles.size()-1){
                        otherModelEdt.setVisibility(View.VISIBLE);
                        currentModel = "0";
                    }else {
                        otherModelEdt.setVisibility(View.GONE);
                        currentModel = modelIds.get(i);
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
        brandArTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_boat_category));
        brandEnTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_boat_category));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest brandsRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId=5", response -> {
            try {
                JSONArray brandsArray = new JSONArray(response);
                for (int i=0; i<brandsArray.length(); i++){
                    JSONObject brandObj = brandsArray.getJSONObject(i);
                    brandArTitles.add(brandObj.getString("ar_Name"));
                    brandEnTitles.add(brandObj.getString("en_Name"));
                    brandIds.add(brandObj.getString("id"));
                }
                brandArTitles.add(mainActivity.getResources().getString(R.string.other));
                brandEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(brandsSpinner, brandArTitles);
                }else {
                    setSpinner(brandsSpinner, brandEnTitles);
                }
                if (brandIds.size() > 0){
                    currentBrand = brandIds.get(0);
                }else {
                    currentBrand = "0";
                }
                getModels();
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
        modelArTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_boat_sub_category));
        modelEnTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_boat_sub_category));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest modelsRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+currentBrand, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray modelsArray = new JSONArray(response);
                for (int i=0; i<modelsArray.length(); i++){
                    JSONObject modelObj = modelsArray.getJSONObject(i);
                    modelArTitles.add(modelObj.getString("ar_Name"));
                    modelEnTitles.add(modelObj.getString("en_Name"));
                    modelIds.add(modelObj.getString("id"));
                }
                modelEnTitles.add(mainActivity.getResources().getString(R.string.other));
                modelArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(modelsSpinner, modelArTitles);
                }else {
                    setSpinner(modelsSpinner, modelEnTitles);
                }
                if(modelIds.size() > 0){
                    currentModel = modelIds.get(0);
                }else {
                    currentModel = "0";
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
        mainActivity.queue.add(modelsRequest);
    }

    private void setData(String title, String otherBrand, String otherModel){
        Send.addBoatsAd.setTitle(title);
        Send.addBoatsAd.setCategoryId("5");
        Send.addBoatsAd.setSubCategoryId(currentBrand);
        Send.addBoatsAd.setOtherSubCategory(otherBrand);
        Send.addBoatsAd.setSubTypeId(currentModel);
        Send.addBoatsAd.setOtherSubType(otherModel);
        Send.addBoatsAd.setYear(currentYear);
        navController.navigate(InitialBoatsFragmentDirections.actionInitialBoatsFragmentToFullBoatsFragment());
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

}