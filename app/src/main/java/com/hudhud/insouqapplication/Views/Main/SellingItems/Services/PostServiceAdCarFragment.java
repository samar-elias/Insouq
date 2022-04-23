package com.hudhud.insouqapplication.Views.Main.SellingItems.Services;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
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

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

public class PostServiceAdCarFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText servicesTitleEdt, phoneNumberEdt, descriptionsEdt, carLiftFromEdt, carLiftToEdt;
    Spinner locationsSpinner;
    TextView location, adTitle, adLocation, adServiceType;
    String currentLocation = "", latitude = "", longitude = "", address = "";
    ArrayList<String> locationArTitles, locationEnTitles;
    String categoryId;
    CheckBox agreementCheckBox;
    boolean spinner1 = false;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;

    public PostServiceAdCarFragment() {
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
        return inflater.inflate(R.layout.fragment_post_service_ad_car, container, false);
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
        getLocations();
        onSpinnerClick();
        onClick();
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
        servicesTitleEdt = view.findViewById(R.id.service_ad_title);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        descriptionsEdt = view.findViewById(R.id.ad_short_description);
        carLiftFromEdt = view.findViewById(R.id.car_lift_from);
        carLiftToEdt = view.findViewById(R.id.car_lift_to);
        locationsSpinner = view.findViewById(R.id.locations_spinner);
        location = view.findViewById(R.id.services_location);
        adTitle = view.findViewById(R.id.service_type);
        adLocation = view.findViewById(R.id.ad_location);
//        adServiceType = view.findViewById(R.id.service_type);
        agreementCheckBox = view.findViewById(R.id.agreement_checkbox);

        locationArTitles = new ArrayList<>();
        locationEnTitles = new ArrayList<>();

        if (getArguments() != null){
            categoryId = PostServiceAdFragmentArgs.fromBundle(getArguments()).getCatId();
            Send.addServicesAd.setCategoryId(categoryId);
        }

        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }
    }

    private void getLocations(){
        locationEnTitles.clear();
        locationArTitles.clear();
        locationArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    locationArTitles.add(locationObj.getString("ar_Text"));
                    locationEnTitles.add(locationObj.getString("en_Text"));
                }
                adLocation.setText(locationEnTitles.get(0));
                if (AppDefs.language.equals("ar")){
                    setSpinner(locationsSpinner, locationArTitles);
                }else {
                    setSpinner(locationsSpinner, locationEnTitles);
                }
                currentLocation = locationEnTitles.get(0)+"-"+locationArTitles.get(0);
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

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){

        locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner1 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                currentLocation = locationEnTitles.get(i)+"-"+locationArTitles.get(i);
                if (AppDefs.language.equals("ar")){
                    adLocation.setText(locationArTitles.get(i));
                }else {
                    adLocation.setText(locationEnTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String adTitle = String.valueOf(servicesTitleEdt.getText());
            String phoneNumber = String.valueOf(phoneNumberEdt.getText());
            String description = String.valueOf(descriptionsEdt.getText());
            String carLiftFrom = String.valueOf(carLiftFromEdt.getText());
            String carLiftTo = String.valueOf(carLiftToEdt.getText());
            setData(adTitle, carLiftFrom, carLiftTo, description, phoneNumber);
        });

        servicesTitleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adTitle.setText(String.valueOf(servicesTitleEdt.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        location.setOnClickListener(view -> startMapsActivity());
    }

    private void setData(String title, String carLiftFrom, String carLiftTo, String description, String phoneNumber){
        if (title.isEmpty() || phoneNumber.isEmpty()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.fill_all_fields));
        }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.location_error));
        }else  if (!agreementCheckBox.isChecked()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.check_box));
        }else {
            Send.addServicesAd.setTitle(title);
            Send.addServicesAd.setSubCategoryId("");
            Send.addServicesAd.setPhoneNumber(phoneNumber);
            Send.addServicesAd.setOtherSubCategory("");
            Send.addServicesAd.setLongitude(longitude);
            Send.addServicesAd.setLocation(currentLocation);
            Send.addServicesAd.setLatitude(latitude);
            Send.addServicesAd.setDescription(description);
            Send.addServicesAd.setCarLiftTo(carLiftTo);
            Send.addServicesAd.setCarLiftFrom(carLiftFrom);
//            postServicesAd();
            packagesPopUp();
        }
    }

    private void postServicesAd(){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.ServicesAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody title = createRequestBody(Send.addServicesAd.getTitle());
        RequestBody description = createRequestBody(Send.addServicesAd.getDescription());
        RequestBody location = createRequestBody(Send.addServicesAd.getLocation());
        RequestBody lat = createRequestBody(Send.addServicesAd.getLatitude());
        RequestBody lan = createRequestBody(Send.addServicesAd.getLongitude());
        RequestBody categoryId = createRequestBody(Send.addServicesAd.getCategoryId());
        RequestBody subCategoryId = createRequestBody(Send.addServicesAd.getSubCategoryId());
        RequestBody otherSubCategory = createRequestBody(Send.addServicesAd.getOtherSubCategory());
        RequestBody carLiftFrom = createRequestBody(Send.addServicesAd.getCarLiftFrom());
        RequestBody carLiftTo = createRequestBody(Send.addServicesAd.getCarLiftTo());
        RequestBody phoneNumber = createRequestBody(Send.addServicesAd.getPhoneNumber());

        Call<AddAdsResponse> addServicesAd = retrofit.create(RetrofitUrls.class).addServicesAd(title, description, location, lat, lan, subCategoryId, categoryId, otherSubCategory, carLiftFrom, carLiftTo, phoneNumber);
        addServicesAd.enqueue(new Callback<AddAdsResponse>() {
            @Override
            public void onResponse(Call<AddAdsResponse> call, Response<AddAdsResponse> response) {
                mainActivity.hideProgressDialog();
                Timber.tag("response").d(response.message());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                        startActivity("");
                        if (packageId.isEmpty()){
                            startActivity("");
                        }else {
                            navController.navigate(PostServiceAdCarFragmentDirections.actionPostServiceAdCarFragmentToPaymentFragment(packageId, ""));
                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.ad_posted_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.services), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

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
            postServicesAd();
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
            Send.electronicsAd.setLat(latitude);
            Send.electronicsAd.setLan(longitude);
            location.setText(address);
        }
    }
}