package com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.Parts;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.graphics.Typeface.BOLD;

public class InitialPartFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText titleEdt, otherSubCategoryEdt, otherSubTypeEdt, otherPartNameEdt, yearEdt;
    Spinner subCategorySpinner, subTypeSpinner, partSpinner;
    ArrayList<String> subCategoryArTitles, subCategoryEnTitles, subCategoryIds, subTypeArTitles, subTypeEnTitles, subTypeIds, partArTitles, partEnTitles, years;
    String currentSubCat = "", currentSubType = "", currentPart = "", currentYear = "";
    int thisYear;
    boolean spinner1 = false, spinner2 = false, spinner3 = false;

    public InitialPartFragment() {
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
        return inflater.inflate(R.layout.fragment_initial_part, container, false);
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
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        continueBtn = view.findViewById(R.id.continue_btn);
        titleEdt = view.findViewById(R.id.motor_title_edt);
        otherSubCategoryEdt = view.findViewById(R.id.other_sub_cat);
        otherSubTypeEdt = view.findViewById(R.id.other_sub_type);
        otherPartNameEdt = view.findViewById(R.id.other_part_edt);
        subCategorySpinner = view.findViewById(R.id.sub_category_spinner);
        subTypeSpinner = view.findViewById(R.id.sub_type_spinner);
        partSpinner = view.findViewById(R.id.part_spinner);
        yearEdt = view.findViewById(R.id.years_edt);

        subCategoryArTitles = new ArrayList<>();
        subCategoryEnTitles = new ArrayList<>();
        subCategoryIds = new ArrayList<>();
        subTypeArTitles = new ArrayList<>();
        subTypeEnTitles = new ArrayList<>();
        subTypeIds = new ArrayList<>();
        partArTitles = new ArrayList<>();
        partEnTitles = new ArrayList<>();
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
            String motorTitle = String.valueOf(titleEdt.getText());
            String otherSubCat = String.valueOf(otherSubCategoryEdt.getText());
            String otherSubType = String.valueOf(otherSubTypeEdt.getText());
            String otherPart = String.valueOf(otherPartNameEdt.getText());
            String year = String.valueOf(yearEdt.getText());
            if (motorTitle.isEmpty() || year.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (!year.startsWith("20") && !year.startsWith("19")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.year_old));
            }else if (otherSubCategoryEdt.getVisibility() == View.VISIBLE && otherSubCat.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherSubTypeEdt.getVisibility() == View.VISIBLE && otherSubType.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherPartNameEdt.getVisibility() == View.VISIBLE && otherPart.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (currentYear.equals("-1") || currentPart.equals("-1") || currentSubCat.equals("-1") || currentSubType.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                currentYear = year;
                setData(motorTitle, otherSubCat, otherSubType, otherPart);
            }

        });

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
                        getSubTypes();
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
                }else {
                    if (subTypeEnTitles.size() == 1){
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        partSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentPart = "-1";
                }else {
                    if (partEnTitles.size() == 1){
                        otherPartNameEdt.setVisibility(View.VISIBLE);
                        currentPart = "0";
                    }else if (i == subTypeEnTitles.size()){
                        otherPartNameEdt.setVisibility(View.VISIBLE);
                        currentPart = "0";
                    }else {
                        otherPartNameEdt.setVisibility(View.GONE);
                        currentPart = partEnTitles.get(i)+"-"+partArTitles.get(i);
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

    private void getSubCategories(){
        subCategoryArTitles.clear();
        subCategoryEnTitles.clear();
        subCategoryArTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_part_category));
        subCategoryEnTitles.add(mainActivity.getResources().getString(R.string.what_type_is_your_part_category));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoryRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId=7", response -> {
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
        subTypeArTitles.add(mainActivity.getResources().getString(R.string.what_make_is_it));
        subTypeEnTitles.add(mainActivity.getResources().getString(R.string.what_make_is_it));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypeRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+currentSubCat, response -> {
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
                getParts();
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

    private void getParts(){
        partArTitles.clear();
        partEnTitles.clear();
        partArTitles.add(mainActivity.getResources().getString(R.string.name_of_part));
        partEnTitles.add(mainActivity.getResources().getString(R.string.name_of_part));
        StringRequest partsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllParts?subTypeId="+currentSubType, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray partsArray = new JSONArray(response);
                for (int i=0; i<partsArray.length(); i++){
                    JSONObject partObj = partsArray.getJSONObject(i);
                    partArTitles.add(partObj.getString("ar_Text"));
                    partEnTitles.add(partObj.getString("en_Text"));
                }
                partEnTitles.add(mainActivity.getResources().getString(R.string.other));
                partArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(partSpinner, partArTitles);
                }else {
                    setSpinner(partSpinner, partEnTitles);
                }
                currentPart = partEnTitles.get(0)+"-"+partArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.parts), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.parts), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(partsRequest);
    }

    private void setData(String title, String otherSubCat, String otherType, String otherPart){
        Send.addPartAd.setTitle(title);
        Send.addPartAd.setCategoryId("7");
        Send.addPartAd.setSubCategoryId(currentSubCat);
        Send.addPartAd.setOtherSubCategory(otherSubCat);
        Send.addPartAd.setSubTypeId(currentSubType);
        Send.addPartAd.setOtherSubType(otherType);
        Send.addPartAd.setPartName(currentPart);
        Send.addPartAd.setOtherPartName(otherPart);
        Send.addPartAd.setYear(currentYear);
        navController.navigate(InitialPartFragmentDirections.actionInitialPartFragmentToFullPartFragment());
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}