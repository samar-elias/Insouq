package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Services.Filter;

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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.hudhud.insouqapplication.AppUtils.Responses.Brand;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragmentDirections;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCatFragment extends Fragment {

    NavController navController;
    MainActivity mainActivity;
    ImageView home, profile, chat, sellItem, notifications, backToPrevious;
    RecyclerView brandsRV;
    ArrayList<Brand> brands, filterBrands = new ArrayList<>();
    String categoryId;
    EditText searchEdt;
    String text = "";

    public SubCatFragment() {
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
        return inflater.inflate(R.layout.fragment_sub_cat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getBrands();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View view){
        if (getArguments() != null){
            categoryId = SubCatFragmentArgs.fromBundle(getArguments()).getCategoryId();
        }
        navController = Navigation.findNavController(view);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
        sellItem = view.findViewById(R.id.sell_item);
        backToPrevious = view.findViewById(R.id.back_arrow);
        brandsRV = view.findViewById(R.id.filter_list);
        brands = new ArrayList<>();
        searchEdt = view.findViewById(R.id.search_edt);
    }

    private void onClick(){
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = String.valueOf(searchEdt.getText());
                filterBrands();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterBrands(){
        filterBrands.clear();
        if (text.isEmpty()){
            getBrands();
        }else {
            for (int i=0; i<brands.size(); i++){
                if (brands.get(i).getTitleEn().contains(text)){
                    filterBrands.add(brands.get(i));
                }
            }
            setBrandsRVFilter();
        }
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    public void navigateToModels(String brandId){
        navController.navigate(SubCatFragmentDirections.actionSubCatFragmentToItemNameFragment(brandId, categoryId));
    }

    public void navigateToList(){
        navController.navigate(SubCatFragmentDirections.actionSubCatFragmentToSubCategoryFragment2("Classifieds",0, categoryId));
    }

    private void getBrands(){
        brands.clear();
        Brand all = new Brand();
        all.setId("-1");
        all.setTitleAr("الجميع");
        all.setTitleEn("All");
        brands.add(all);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest brandsRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+categoryId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray brandsArray = new JSONArray(response);
                for (int i=0; i<brandsArray.length(); i++){
                    JSONObject brandObj = brandsArray.getJSONObject(i);
                    Brand brand = new Brand();
                    brand.setId(brandObj.getString("id"));
                    brand.setTitleAr(brandObj.getString("ar_Name"));
                    brand.setTitleEn(brandObj.getString("en_Name"));
                    brands.add(brand);
                }
                setBrandsRV();
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

    private void setBrandsRVFilter(){
        AgesAdapter brandsAdapter = new AgesAdapter(this, filterBrands);
        brandsRV.setAdapter(brandsAdapter);
        brandsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    private void setBrandsRV(){
        AgesAdapter brandsAdapter = new AgesAdapter(this, brands);
        brandsRV.setAdapter(brandsAdapter);
        brandsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }
}