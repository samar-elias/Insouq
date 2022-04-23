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
import com.hudhud.insouqapplication.AppUtils.Responses.Model;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.EmploymentTypeFragmentArgs;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.EmploymentTypeFragmentDirections;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemNameFragment extends Fragment {

    NavController navController;
    MainActivity mainActivity;
    ImageView home, profile, chat, sellItem, notifications, backToPrevious;
    RecyclerView modelsRV;
    ArrayList<Model> models, filterModels = new ArrayList<>();
    String brandId, categoryId;
    EditText searchEdt;
    String text = "";

    public ItemNameFragment() {
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
        return inflater.inflate(R.layout.fragment_item_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getModels();
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
            brandId = EmploymentTypeFragmentArgs.fromBundle(getArguments()).getBrandId();
            categoryId = EmploymentTypeFragmentArgs.fromBundle(getArguments()).getCategoryId();
        }

        navController = Navigation.findNavController(view);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
        sellItem = view.findViewById(R.id.sell_item);
        backToPrevious = view.findViewById(R.id.back_arrow);
        modelsRV = view.findViewById(R.id.filter_list);
        searchEdt = view.findViewById(R.id.search_edt);
        models = new ArrayList<>();
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
                filterModels();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterModels(){
        filterModels.clear();
        if (text.isEmpty()){
            getModels();
        }else {
            for (int i=0; i<models.size(); i++){
                if (models.get(i).getTitleEn().contains(text)){
                    filterModels.add(models.get(i));
                }
            }
            setModelsRVFilter();
        }
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void navigateToSubCategories(){
        navController.navigate(ItemNameFragmentDirections.actionItemNameFragmentToSubCategoryFragment2("Classifieds",0, categoryId));
    }

    private void getModels(){
        models.clear();
        Model all = new Model();
        all.setId("-1");
        all.setTitleAr("الجميع");
        all.setTitleEn("All");
        models.add(all);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest modelsRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+brandId, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray modelsArray = new JSONArray(response);
                for (int i=0; i<modelsArray.length(); i++){
                    JSONObject modelObj = modelsArray.getJSONObject(i);
                    Model model = new Model();
                    model.setId(modelObj.getString("id"));
                    model.setTitleAr(modelObj.getString("ar_Name"));
                    model.setTitleEn(modelObj.getString("en_Name"));
                    models.add(model);
                }
                setModelsRV();
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

    private void setModelsRVFilter(){
        UsagesAdapter modeslAdapter = new UsagesAdapter(this, filterModels);
        modelsRV.setAdapter(modeslAdapter);
        modelsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    private void setModelsRV(){
        UsagesAdapter modeslAdapter = new UsagesAdapter(this, models);
        modelsRV.setAdapter(modeslAdapter);
        modelsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }
}