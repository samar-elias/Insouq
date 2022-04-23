package com.hudhud.insouqapplication.Views.Main.SellingItems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.Responses.SubCategory;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCategoriesPostAdFragment extends Fragment {

    RecyclerView subCategoryRV;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list, categoryIcon;
    MainActivity mainActivity;
    ArrayList<SubCategory> subCategories;
    TextView categoryTitle, sellCategoryTitle, categoryDescription;
    int categoryId;
    public int jobSubId = 0;

    public SubCategoriesPostAdFragment() {
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
        return inflater.inflate(R.layout.fragment_sub_categories_post_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
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
        subCategories = new ArrayList<>();
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);
        subCategoryRV = view.findViewById(R.id.sub_categories_RV);
        categoryIcon = view.findViewById(R.id.sell_icon);
        categoryTitle = view.findViewById(R.id.category_title);
        sellCategoryTitle = view.findViewById(R.id.sell_item_title);
        categoryDescription = view.findViewById(R.id.category_name_description);
        getData();
    }

    private void getData(){
        if (getArguments() != null){
            categoryId = SubCategoriesPostAdFragmentArgs.fromBundle(getArguments()).getCategoryId();
        }
        switch (categoryId){
            case 1:
                categoryTitle.setText(mainActivity.getString(R.string.motors));
                sellCategoryTitle.setText(mainActivity.getString(R.string.motors));
                categoryDescription.setText(mainActivity.getString(R.string.motors)+"?");
                Glide.with(mainActivity).load(R.drawable.motors_pink).into(categoryIcon);
                break;
            case 2:
                categoryTitle.setText(mainActivity.getString(R.string.property));
                sellCategoryTitle.setText(mainActivity.getString(R.string.property));
                categoryDescription.setText(mainActivity.getString(R.string.property)+"?");
                Glide.with(mainActivity).load(R.drawable.property_orange).into(categoryIcon);
                break;
            case 3:
                categoryTitle.setText(mainActivity.getString(R.string.jobs));
                sellCategoryTitle.setText(mainActivity.getString(R.string.jobs));
                categoryDescription.setText(mainActivity.getString(R.string.jobs)+"?");
                Glide.with(mainActivity).load(R.drawable.jobs_peach).into(categoryIcon);
                break;
            case 4:
                categoryTitle.setText(mainActivity.getString(R.string.services));
                sellCategoryTitle.setText(mainActivity.getString(R.string.services));
                categoryDescription.setText(mainActivity.getString(R.string.services)+"?");
                Glide.with(mainActivity).load(R.drawable.services_fairouz).into(categoryIcon);
                break;
            case 5:
                categoryTitle.setText(mainActivity.getString(R.string.business));
                sellCategoryTitle.setText(mainActivity.getString(R.string.business));
                categoryDescription.setText(mainActivity.getString(R.string.business)+"?");
                Glide.with(mainActivity).load(R.drawable.business_green).into(categoryIcon);
                break;
            case 6:
                categoryTitle.setText(mainActivity.getString(R.string.classifieds));
                sellCategoryTitle.setText(mainActivity.getString(R.string.classifieds));
                categoryDescription.setText(mainActivity.getString(R.string.classifieds)+"?");
                Glide.with(mainActivity).load(R.drawable.classifieds_blue).into(categoryIcon);
                break;
            case 7:
                categoryTitle.setText(mainActivity.getString(R.string.numbers));
                sellCategoryTitle.setText(mainActivity.getString(R.string.numbers));
                categoryDescription.setText(mainActivity.getString(R.string.numbers)+"?");
                Glide.with(mainActivity).load(R.drawable.numbers_purple).into(categoryIcon);
                break;
            case 8:
                categoryTitle.setText(mainActivity.getString(R.string.electronics));
                sellCategoryTitle.setText(mainActivity.getString(R.string.electronics));
                categoryDescription.setText(mainActivity.getString(R.string.electronics)+"?");
                Glide.with(mainActivity).load(R.drawable.mobile_yellow).into(categoryIcon);
                break;
        }
        if (categoryId == 5){
            subCategories.clear();
            subCategories.add(new SubCategory(0, 0, 0, 0, mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.business), "https://insouq.com/images/BusinessForSale1.png"));
            setSubCategoryRV();
        }else {
            getSubCategories(categoryId);
        }
    }

    private void getSubCategories(int categoryId){
        subCategories.clear();
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        StringRequest getSubCategories = new StringRequest(Request.Method.GET, Urls.Categories_URL +"GetByTypeId?id="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObject = subCategoriesArray.getJSONObject(i);
                    SubCategory subCategory = new SubCategory();
                    subCategory.setId(subCategoryObject.getInt("id"));
                    subCategory.setNameAr(subCategoryObject.getString("ar_Name"));
                    subCategory.setNameEn(subCategoryObject.getString("en_Name"));
                    subCategory.setNumberOfAds(subCategoryObject.getInt("numberOfAds"));
                    subCategory.setIcon(subCategoryObject.getString("firstImage"));
                    subCategory.setStatus(subCategoryObject.getInt("status"));
                    subCategory.setTypeId(subCategoryObject.getInt("typeId"));
                    subCategories.add(subCategory);
                }
                setSubCategoryRV();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(getResources().getString(R.string.network_error), getResources().getString(R.string.error_occured));
        });
        mainActivity.queue.add(getSubCategories);
    }

    private void setSubCategoryRV(){
        SubCategoryAdapter subCategoriesAdapter = new SubCategoryAdapter(this, subCategories, categoryId);
        subCategoryRV.setAdapter(subCategoriesAdapter);
        subCategoryRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void navigateSell(int subCatId, int position){
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (categoryId){
                    case 1:
                       switch (subCatId){
                           case 2:
                               navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToSell2Fragment());
                               break;
                           case 5:
                               navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToInitialBoatsFragment());
                               break;
                           case 7:
                               navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToInitialPartFragment());
                               break;
                           case 8:
                               navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToInitialExportCarFragment());
                               break;
                           case 9:
                               navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToInitialBikeFragment());
                               break;
                           case 6:
                               navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToInitialMachinaryFragment());
                               break;
                       }
                        break;
                    case 2:
                        navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostPropertyAdFragment());
                        break;
                    case 3:
                        if (subCatId == 3){
                            navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostJobOpeningAdFragment());
                            break;
                        }else if (subCatId == 4){
                            navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostJobWantedAdFragment2());
                            break;
                        }
                    case 4:
                        if (subCatId == 31){
                            navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostServiceAdCarFragment(String.valueOf(subCatId)));
                        }else {
                            navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostServiceAdFragment(String.valueOf(subCatId)));
                        }
                        break;
                    case 5:
                        navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostBusinessAdFragment());
                        break;
                    case 7:
                        if (position == 0){
                            navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostNumbersAdFragment(subCatId));
                            break;
                        }else if (position == 1){
                            navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostMobileNumberAdFragment(subCatId));
                            break;
                        }
                    case 6:
                    case 8:
                        navController.navigate(SubCategoriesPostAdFragmentDirections.actionSubCategoriesPostAdFragmentToPostElectronicsAdFragment(subCatId));
                        break;
                }
            }
        }, 300);
    }
    
}