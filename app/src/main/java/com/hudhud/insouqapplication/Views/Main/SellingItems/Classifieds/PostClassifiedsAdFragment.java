package com.hudhud.insouqapplication.Views.Main.SellingItems.Classifieds;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.graphics.Typeface.BOLD;

public class PostClassifiedsAdFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText classifiedsTitleEdt, otherSubCategoryEdt, otherSubTypeEdt;
    Spinner categoriesSpinner, subCategoriesSpinner, itemNamesSpinner;
    ArrayList<String> categoriesArTitles, categoriesEnTitles, subCategoriesArTitles, subCategoriesEnTitles, subTypesArTitles, subTypesEnTitles;
    ArrayList<Integer> categoriesIds, subCategoriesIds, subTypesIds;
    String currentSubCategoryId = "", currentCategoryId = "", currentSubTypeId = "";
    boolean spinner1 = false, spinner2 = false, spinner3 = false;

    // Ad sample
    TextView adTitleTV;

    public PostClassifiedsAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_classifieds_ad, container, false);
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
        onSpinnerClick();
        onClick();
        getCategories();

    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);

        adTitleTV = view.findViewById(R.id.electronics_title);
        otherSubCategoryEdt = view.findViewById(R.id.other_sub_cat);
        otherSubTypeEdt = view.findViewById(R.id.other_sub_type);
        continueBtn = view.findViewById(R.id.continue_btn);
        classifiedsTitleEdt = view.findViewById(R.id.classifieds_ad_title);
        categoriesSpinner = view.findViewById(R.id.categories_spinner);
        subCategoriesSpinner = view.findViewById(R.id.sub_categories_spinner);
        itemNamesSpinner = view.findViewById(R.id.item_names_spinner);

        categoriesArTitles = new ArrayList<>();
        categoriesEnTitles = new ArrayList<>();
        categoriesIds = new ArrayList<>();
        subCategoriesArTitles = new ArrayList<>();
        subCategoriesEnTitles = new ArrayList<>();
        subCategoriesIds = new ArrayList<>();
        subTypesArTitles = new ArrayList<>();
        subTypesEnTitles = new ArrayList<>();
        subTypesIds = new ArrayList<>();
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String adTitle = String.valueOf(classifiedsTitleEdt.getText());
            String otherSubCategory = String.valueOf(otherSubCategoryEdt.getText());
            String otherSubType = String.valueOf(otherSubTypeEdt.getText());
            if (adTitle.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherSubCategoryEdt.getVisibility() == View.VISIBLE){
                if (otherSubCategory.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else if (otherSubTypeEdt.getVisibility() == View.VISIBLE){
                    if (otherSubType.isEmpty()){
                        mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                    }else if (currentCategoryId.equals("-1") || currentSubCategoryId.equals("-1") || currentSubTypeId.equals("-1")){
                        mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                    }else {
                        setData(adTitle, otherSubCategory, otherSubType);
                    }
                }else if (currentCategoryId.equals("-1") || currentSubCategoryId.equals("-1") || currentSubTypeId.equals("-1")){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                    setData(adTitle, otherSubCategory, otherSubType);
                }
            }else if (otherSubTypeEdt.getVisibility() == View.VISIBLE){
                if (otherSubType.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else if (currentCategoryId.equals("-1") || currentSubCategoryId.equals("-1") || currentSubTypeId.equals("-1")){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                    setData(adTitle, otherSubCategory, otherSubType);
                }
            }else if (currentCategoryId.equals("-1") || currentSubCategoryId.equals("-1") || currentSubTypeId.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.classifieds_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                setData(adTitle, otherSubCategory, otherSubType);
            }
        });

        classifiedsTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adTitleTV.setText(String.valueOf(classifiedsTitleEdt.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setData(String adTitle, String otherSubCategory, String otherSubType){
        Send.addClassifiedAd.setTitle(adTitle);
        Send.addClassifiedAd.setCategoryId(currentCategoryId);
        Send.addClassifiedAd.setSubCategoryId(currentSubCategoryId);
        Send.addClassifiedAd.setOtherSubCategory(otherSubCategory);
        Send.addClassifiedAd.setSubTypeId(currentSubTypeId);
        Send.addClassifiedAd.setOtherSubType(otherSubType);
        switch (currentCategoryId){
            case "39":
            case "40":
            case "41":
            case "42":
            case "43":
            case "44":
            case "45":
                navController.navigate(PostClassifiedsAdFragmentDirections.actionPostClassifiedsAdFragmentToPostClassifiedsGroup1AdFragment());
                break;
            case "46":
            case "47":
            case "48":
            case "49":
            case "51":
            case "52":
                navController.navigate(PostClassifiedsAdFragmentDirections.actionPostClassifiedsAdFragmentToPostClassifieds2AdFragment());
                break;
            case "53":
            case "54":
            case "55":
                navController.navigate(PostClassifiedsAdFragmentDirections.actionPostClassifiedsAdFragmentToPostClassifiedsGroup3AdFragment());
        }
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner1 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                currentCategoryId = String.valueOf(categoriesIds.get(i));
                getSubCategories(currentCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner2 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (subCategoriesEnTitles.size() == 1){
                    currentSubCategoryId = "0";
                    otherSubCategoryEdt.setVisibility(View.VISIBLE);
                    getSubTypes(currentSubCategoryId);
                }else {
                    if (i == subCategoriesEnTitles.size()-1){
                        currentSubCategoryId = "0";
                        otherSubCategoryEdt.setVisibility(View.VISIBLE);
                        getSubTypes(currentSubCategoryId);
                    }else {
                        currentSubCategoryId = String.valueOf(subCategoriesIds.get(i));
                        getSubTypes(currentSubCategoryId);
                        otherSubCategoryEdt.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        itemNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner3 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (subTypesIds.size() == 0){
                    currentSubTypeId = "0";
                    otherSubTypeEdt.setVisibility(View.VISIBLE);
                }else {
                    if (i == subTypesEnTitles.size()-1){
                        currentSubTypeId = "0";
                        otherSubTypeEdt.setVisibility(View.VISIBLE);
                    }else {
                        currentSubTypeId = String.valueOf(subTypesIds.get(i));
                        otherSubTypeEdt.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCategories(){
        categoriesArTitles.clear();
        categoriesEnTitles.clear();
        categoriesIds.clear();
        categoriesArTitles.add(mainActivity.getResources().getString(R.string.select_category));
        categoriesEnTitles.add(mainActivity.getResources().getString(R.string.select_category));
        categoriesIds.add(-1);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest categoriesRequest = new StringRequest(Request.Method.GET, Urls.Categories_URL+"GetByTypeId?id=6", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray categoriesArray = new JSONArray(response);
                for (int i=0; i<categoriesArray.length(); i++){
                    JSONObject categoryObj = categoriesArray.getJSONObject(i);
                    categoriesArTitles.add(categoryObj.getString("ar_Name"));
                    categoriesEnTitles.add(categoryObj.getString("en_Name"));
                    categoriesIds.add(categoryObj.getInt("id"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(categoriesSpinner, categoriesArTitles);
                }else {
                    setSpinner(categoriesSpinner, categoriesEnTitles);
                }
                currentCategoryId = String.valueOf(categoriesIds.get(0));
//                getSubCategories(currentCategoryId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(categoriesRequest);
    }

    private void getSubCategories(String categoryId){
        subCategoriesEnTitles.clear();
        subCategoriesArTitles.clear();
        subCategoriesIds.clear();
        subCategoriesArTitles.add(mainActivity.getResources().getString(R.string.select_sub_category));
        subCategoriesEnTitles.add(mainActivity.getResources().getString(R.string.select_sub_category));
        subCategoriesIds.add(-1);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    subCategoriesIds.add(subCategoryObj.getInt("id"));
                    subCategoriesArTitles.add(subCategoryObj.getString("ar_Name"));
                    subCategoriesEnTitles.add(subCategoryObj.getString("en_Name"));
                }
                subCategoriesEnTitles.add(mainActivity.getResources().getString(R.string.other));
                subCategoriesArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(subCategoriesSpinner, subCategoriesArTitles);
                }else {
                    setSpinner(subCategoriesSpinner, subCategoriesEnTitles);
                }
                if (subCategoriesIds.size() == 0){
                    currentSubCategoryId = "0";
                }else {
                    currentSubCategoryId = String.valueOf(subCategoriesIds.get(0));
                }
//                getSubTypes(currentSubCategoryId);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.sub_category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subCategoriesRequest);
    }

    private void getSubTypes(String subCategoryId){
        subTypesEnTitles.clear();
        subTypesArTitles.clear();
        subTypesIds.clear();

        subTypesArTitles.add(mainActivity.getResources().getString(R.string.select_item_name));
        subTypesEnTitles.add(mainActivity.getResources().getString(R.string.select_item_name));
        subTypesIds.add(-1);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subTypesRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+subCategoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subTypesArray = new JSONArray(response);
                for (int i=0; i<subTypesArray.length(); i++){
                    JSONObject subTypeObj = subTypesArray.getJSONObject(i);
                    subTypesIds.add(subTypeObj.getInt("id"));
                    subTypesArTitles.add(subTypeObj.getString("ar_Name"));
                    subTypesEnTitles.add(subTypeObj.getString("en_Name"));
                }
                subTypesEnTitles.add(mainActivity.getResources().getString(R.string.other));
                subTypesArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(itemNamesSpinner, subTypesArTitles);
                }else {
                    setSpinner(itemNamesSpinner, subTypesEnTitles);
                }
                if (subTypesIds.size() == 0){
                    currentSubTypeId = "0";
                }else {
                    currentSubTypeId = String.valueOf(subTypesIds.get(0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.item_name), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.item_name), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(subTypesRequest);
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }
}