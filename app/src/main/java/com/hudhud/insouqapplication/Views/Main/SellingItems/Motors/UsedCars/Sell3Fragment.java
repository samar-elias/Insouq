package com.hudhud.insouqapplication.Views.Main.SellingItems.Motors.UsedCars;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.map.MapsActivity;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
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
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createMultipartBodyPart;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

public class Sell3Fragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    private static final int REQUEST_CODE = 101;
    ImageView image1, image2, image3, image4, image5, image7, image6, image8, image9, image10, uploadImage, mainImage;
    ImageView closeImage1, closeImage2, closeImage3, closeImage4, closeImage5, closeImage7, closeImage6, closeImage8, closeImage9, closeImage10, closeMainImage;
    String image1Path = "", image2Path = "", image3Path = "", image4Path = "", image5Path = "", image7Path = "", image6Path = "", image8Path = "", image9Path = "",
            image10Path = "", mainPath = "", longitude = "", latitude = "", address = "";
    Bitmap image1Bitmap, image2Bitmap, image3Bitmap, image4Bitmap, image5Bitmap, image7Bitmap, image6Bitmap, image8Bitmap, image9Bitmap, image10Bitmap;
    EditText priceEdt, phoneNumberEdt, descriptionEdt, mileageEdt;
    TextView location;
    ArrayList<String> pictures, specsArTitles, specsEnTitles, colorArTitles, colorEnTitles, doorArTitles, doorEnTitles, warrantyTitles,
            transmissionArTitles, transmissionEnTitles, bodyTypeArTitles, bodyTypeEnTitles, fuelTypeArTitles, fuelTypeEnTitles, cylinderArTitles,
            cylinderEnTitles, steeringArTitles, steeringEnTitles, horsepowerArTitles, horsepowerEnTitles, locationArTitles, locationEnTitles;
    Spinner regionalSpecificationsSpinner, colorSpinner, doorsSpinner, warrantySpinner, transmissionSpinner, bodyTypeSpinner, fuelTypeSpinner,
            noOfCylindersSpinner, steeringSideSpinner, horsePowerSpinner, locationSpinner;
    String currentSpec = "", currentColor = "", currentDoor = "", currentWarranty = "", currentTransmission = "", currentBody = "", currentFuel = "",
            currentCylinder = "", currentSteering = "", currentHorsePower = "", currentLocation = "";
    CheckBox agreementCheckBox;
    boolean spinner1 = false, spinner2 = false, spinner3 = false, spinner4 = false, spinner5 = false, spinner6 = false, spinner7 = false, spinner8 = false, spinner9 = false, spinner10 = false, spinner11 = false;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;

    //Ad sample
    TextView adTitle, adPrice, adLocation, adKilo, adYear;
    ImageView adImage;

    public Sell3Fragment() {
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
        return inflater.inflate(R.layout.fragment_sell3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getRegionalSpecs();
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
        adTitle = view.findViewById(R.id.motor_title);
        adLocation = view.findViewById(R.id.ad_location);
        adPrice = view.findViewById(R.id.motor_price);
        adImage = view.findViewById(R.id.motor_image);
        adKilo = view.findViewById(R.id.kilos_amount);
        adYear = view.findViewById(R.id.motor_year);
        location = view.findViewById(R.id.location);
        priceEdt = view.findViewById(R.id.ad_price);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        descriptionEdt = view.findViewById(R.id.ad_short_description);
        mileageEdt = view.findViewById(R.id.mileage_edt);
        agreementCheckBox = view.findViewById(R.id.agreement_checkbox);

        regionalSpecificationsSpinner = view.findViewById(R.id.specs_spinner);
        colorSpinner = view.findViewById(R.id.color_spinner);
        doorsSpinner = view.findViewById(R.id.doors_spinner);
        warrantySpinner = view.findViewById(R.id.warranty_spinner);
        transmissionSpinner = view.findViewById(R.id.transmission_spinner);
        bodyTypeSpinner = view.findViewById(R.id.body_spinner);
        fuelTypeSpinner = view.findViewById(R.id.fuel_spinner);
        noOfCylindersSpinner = view.findViewById(R.id.cylinders_spinner);
        steeringSideSpinner = view.findViewById(R.id.steering_side_spinner);
        horsePowerSpinner = view.findViewById(R.id.horsepower_spinner);
        locationSpinner = view.findViewById(R.id.locations_spinner);

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        image7 = view.findViewById(R.id.image7);
        image8 = view.findViewById(R.id.image8);
        image9 = view.findViewById(R.id.image9);
        image10 = view.findViewById(R.id.image10);
        uploadImage = view.findViewById(R.id.upload_pictures);
        mainImage = view.findViewById(R.id.main_image_);

        closeImage1 = view.findViewById(R.id.close_img1);
        closeImage2 = view.findViewById(R.id.close_img2);
        closeImage3 = view.findViewById(R.id.close_img3);
        closeImage4 = view.findViewById(R.id.close_img4);
        closeImage5 = view.findViewById(R.id.close_img5);
        closeImage6 = view.findViewById(R.id.close_img6);
        closeImage7 = view.findViewById(R.id.close_img7);
        closeImage8 = view.findViewById(R.id.close_img8);
        closeImage9 = view.findViewById(R.id.close_img9);
        closeImage10 = view.findViewById(R.id.close_img10);
        closeMainImage = view.findViewById(R.id.close_main_);

        pictures = new ArrayList<>();
        specsArTitles = new ArrayList<>();
        specsEnTitles = new ArrayList<>();
        colorArTitles = new ArrayList<>();
        colorEnTitles = new ArrayList<>();
        doorArTitles = new ArrayList<>();
        doorEnTitles = new ArrayList<>();
        warrantyTitles = new ArrayList<>();
        transmissionArTitles = new ArrayList<>();
        transmissionEnTitles = new ArrayList<>();
        bodyTypeArTitles = new ArrayList<>();
        bodyTypeEnTitles = new ArrayList<>();
        fuelTypeArTitles = new ArrayList<>();
        fuelTypeEnTitles = new ArrayList<>();
        cylinderArTitles = new ArrayList<>();
        cylinderEnTitles = new ArrayList<>();
        steeringArTitles = new ArrayList<>();
        steeringEnTitles = new ArrayList<>();
        horsepowerArTitles = new ArrayList<>();
        horsepowerEnTitles = new ArrayList<>();
        locationArTitles = new ArrayList<>();
        locationEnTitles = new ArrayList<>();

        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }
        adTitle.setText(Send.exportTitle);
        adYear.setText(Send.addUsedCarsAd.getYear());
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String price = String.valueOf(priceEdt.getText());
            String phoneNumber = String.valueOf(phoneNumberEdt.getText());
            String description = String.valueOf(descriptionEdt.getText());
            String mileage = String.valueOf(mileageEdt.getText());
            if (price.isEmpty() || phoneNumber.isEmpty() || mileage.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if(Integer.parseInt(price)>10000000){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.price_high));
            }else {
                pictures.clear();
                if (!image1Path.isEmpty()){
                    pictures.add(image1Path);
                }
                if (!image2Path.isEmpty()){
                    pictures.add(image2Path);
                }
                if (!image3Path.isEmpty()){
                    pictures.add(image3Path);
                }
                if (!image4Path.isEmpty()){
                    pictures.add(image4Path);
                }
                if (!image5Path.isEmpty()){
                    pictures.add(image5Path);
                }
                if (!image6Path.isEmpty()){
                    pictures.add(image6Path);
                }
                if (!image7Path.isEmpty()){
                    pictures.add(image7Path);
                }
                if (!image8Path.isEmpty()){
                    pictures.add(image8Path);
                }
                if (!image9Path.isEmpty()){
                    pictures.add(image9Path);
                }
                if (!image10Path.isEmpty()){
                    pictures.add(image10Path);
                }
                if (pictures.size()==0){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.minimum_pic));
                }else if (mainPath.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors_ad), mainActivity.getResources().getString(R.string.select_main_image));
                }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.location_error));
                }else  if (!agreementCheckBox.isChecked()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.check_box));
                }else if (currentSpec.equals("-1") || currentBody.equals("-1") || currentColor.equals("-1") || currentDoor.equals("-1") || currentFuel.equals("-1")
                        || currentCylinder.equals("-1") ||currentHorsePower.equals("-1") || currentLocation.equals("-1") || currentSteering.equals("-1")
                        || currentTransmission.equals("-1") || currentWarranty.equals("-1")) {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                        setData(price, phoneNumber, description, mileage);
                    }
            }
        });

        priceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adPrice.setText(priceEdt.getText() +" AED");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mileageEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adKilo.setText(mileageEdt.getText() +" KM");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        uploadImage.setOnClickListener(view -> {
            if (image1Path.isEmpty()){
                pickImage(1);
            }else if (image2Path.isEmpty()){
                pickImage(2);
            }else if (image3Path.isEmpty()){
                pickImage(3);
            }else if (image4Path.isEmpty()){
                pickImage(4);
            }else if (image5Path.isEmpty()){
                pickImage(5);
            }else if (image6Path.isEmpty()){
                pickImage(6);
            }else if (image7Path.isEmpty()){
                pickImage(7);
            }else if (image8Path.isEmpty()){
                pickImage(8);
            }else if (image9Path.isEmpty()){
                pickImage(9);
            }else if (image10Path.isEmpty()){
                pickImage(10);
            }
        });

        location.setOnClickListener(view -> startMapsActivity());

        image1.setOnClickListener(view -> {
            mainImage.setImageBitmap(image1Bitmap);
            mainPath = image1Path.substring(image1Path.lastIndexOf("/")+1);
            adImage.setImageBitmap(image1Bitmap);
            changeImages(image1, image2, image3, image4, image5, image6, image7, image8, image9, image10);
        });
        image2.setOnClickListener(view -> {
            mainImage.setImageBitmap(image2Bitmap);
            mainPath = image2Path.substring(image2Path.lastIndexOf("/")+1);
            adImage.setImageBitmap(image2Bitmap);
            changeImages(image2, image1, image3, image4, image5, image6, image7, image8, image9, image10);
        });
        image3.setOnClickListener(view -> {
            mainImage.setImageBitmap(image3Bitmap);
            mainPath = image3Path.substring(image3Path.lastIndexOf("/")+1);
            adImage.setImageBitmap(image3Bitmap);
            changeImages(image3, image2, image1, image4, image5, image6, image7, image8, image9, image10);
        });
        image4.setOnClickListener(view -> {
            mainImage.setImageBitmap(image4Bitmap);
            mainPath = image4Path.substring(image4Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image4Bitmap);
            changeImages(image4, image2, image3, image1, image5, image6, image7, image8, image9, image10);
        });
        image5.setOnClickListener(view -> {
            mainImage.setImageBitmap(image5Bitmap);
            mainPath = image5Path.substring(image5Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image5Bitmap);
            changeImages(image5, image2, image3, image4, image1, image6, image7, image8, image9, image10);
        });
        image6.setOnClickListener(view -> {
            mainImage.setImageBitmap(image6Bitmap);
            mainPath = image6Path.substring(image6Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image6Bitmap);
            changeImages(image6, image2, image3, image4, image5, image1, image7, image8, image9, image10);
        });
        image7.setOnClickListener(view -> {
            mainImage.setImageBitmap(image7Bitmap);
            mainPath = image7Path.substring(image7Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image7Bitmap);
            changeImages(image7, image2, image3, image4, image5, image6, image1, image8, image9, image10);
        });
        image8.setOnClickListener(view -> {
            mainImage.setImageBitmap(image8Bitmap);
            mainPath = image8Path.substring(image8Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image8Bitmap);
            changeImages(image8, image2, image3, image4, image5, image6, image7, image1, image9, image10);
        });
        image9.setOnClickListener(view -> {
            mainImage.setImageBitmap(image9Bitmap);
            mainPath = image9Path.substring(image9Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image9Bitmap);
            changeImages(image9, image2, image3, image4, image5, image6, image7, image8, image1, image10);
        });
        image10.setOnClickListener(view -> {
            mainImage.setImageBitmap(image10Bitmap);
            mainPath = image10Path.substring(image10Path.lastIndexOf("/")+1);;
            adImage.setImageBitmap(image10Bitmap);
            changeImages(image10, image2, image3, image4, image5, image6, image7, image8, image9, image1);
        });

        closeMainImage.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(mainImage);
            mainPath = "";
        });

        closeImage1.setOnClickListener(view -> {
            clearMainPath(image1Path, image1);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image1);
            image1Path = "";
        });

        closeImage2.setOnClickListener(view -> {
            clearMainPath(image2Path, image2);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image2);
            image2Path = "";
        });

        closeImage3.setOnClickListener(view -> {
            clearMainPath(image3Path, image3);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image3);
            image3Path = "";
        });

        closeImage4.setOnClickListener(view -> {
            clearMainPath(image4Path, image4);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image4);
            image4Path = "";
        });

        closeImage5.setOnClickListener(view -> {
            clearMainPath(image5Path, image5);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image5);
            image5Path = "";
        });

        closeImage6.setOnClickListener(view -> {
            clearMainPath(image6Path, image6);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image6);
            image6Path = "";
        });

        closeImage7.setOnClickListener(view -> {
            clearMainPath(image7Path, image7);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image7);
            image7Path = "";
        });

        closeImage8.setOnClickListener(view -> {
            clearMainPath(image8Path, image8);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image8);
            image8Path = "";
        });

        closeImage9.setOnClickListener(view -> {
            clearMainPath(image9Path, image9);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image9);
            image9Path = "";
        });

        closeImage10.setOnClickListener(view -> {
            clearMainPath(image10Path, image10);
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image10);
            image10Path = "";
        });
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        regionalSpecificationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentSpec = "-1";
                }else {
                    currentSpec = specsEnTitles.get(i)+"-"+specsArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentColor = "-1";
                }else {
                    currentColor = colorEnTitles.get(i)+"-"+colorArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        doorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentDoor = "-1";
                }else {
                    currentDoor = doorEnTitles.get(i)+"-"+doorArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        warrantySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentWarranty = "-1";
                }else {
                    if (i==1){
                        currentWarranty = "0";
                    }else if (i==2){
                        currentWarranty = "1";
                    }else if (i==3){
                        currentWarranty = "2";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        transmissionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner5 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentTransmission = "-1";
                }else {
                    currentTransmission = transmissionEnTitles.get(i)+"-"+transmissionArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bodyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner6 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentBody = "-1";
                }else {
                    currentBody = bodyTypeEnTitles.get(i)+"-"+bodyTypeArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fuelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner7 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentFuel ="-1";
                }else {
                    currentFuel = fuelTypeEnTitles.get(i)+"-"+fuelTypeArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        noOfCylindersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner8 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentCylinder = "-1";
                }else {
                    currentCylinder = cylinderEnTitles.get(i)+"-"+cylinderArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        steeringSideSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner9 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentSteering = "-1";
                }else {
                    currentSteering = steeringEnTitles.get(i)+"-"+specsArTitles.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        horsePowerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner10 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentHorsePower = "-1";
                }else {
                    currentHorsePower = horsepowerEnTitles.get(i)+"-"+horsepowerArTitles.get(i);
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
                    spinner11 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentLocation = "-1";
                    adLocation.setText(mainActivity.getResources().getString(R.string.location));
                }else {
                    currentLocation = locationEnTitles.get(i)+"-"+locationArTitles.get(i);
                    if (AppDefs.language.equals("ar")){
                        adLocation.setText(locationArTitles.get(i));
                    }else {
                        adLocation.setText(locationEnTitles.get(i));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getRegionalSpecs(){
        specsEnTitles.clear();
        specsArTitles.clear();
        specsArTitles.add(mainActivity.getResources().getString(R.string.select_specs));
        specsEnTitles.add(mainActivity.getResources().getString(R.string.select_specs));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest specsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorRegionalSpecs", response -> {
            try {
                JSONArray specsArray = new JSONArray(response);
                for (int i=0; i<specsArray.length(); i++){
                    JSONObject specObj = specsArray.getJSONObject(i);
                    specsArTitles.add(specObj.getString("ar_Text"));
                    specsEnTitles.add(specObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(regionalSpecificationsSpinner, specsArTitles);
                }else {
                    setSpinner(regionalSpecificationsSpinner, specsEnTitles);
                }
                currentSpec = specsEnTitles.get(0)+"-"+specsArTitles.get(0);
                getColors();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.specs), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.specs), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(specsRequest);
    }

    private void getColors(){
        colorArTitles.clear();
        colorEnTitles.clear();
        colorArTitles.add(mainActivity.getResources().getString(R.string.select_color));
        colorEnTitles.add(mainActivity.getResources().getString(R.string.select_color));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest colorRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllColor", response -> {
            try {
                JSONArray colorArray = new JSONArray(response);
                for (int i=0; i<colorArray.length(); i++){
                    JSONObject colorObj = colorArray.getJSONObject(i);
                    colorArTitles.add(colorObj.getString("ar_Text"));
                    colorEnTitles.add(colorObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(colorSpinner, colorArTitles);
                }else {
                    setSpinner(colorSpinner, colorEnTitles);
                }
                currentColor = colorEnTitles.get(0)+"-"+colorArTitles.get(0);
                getDoors();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.color), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.color), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(colorRequest);
    }

    private void getDoors(){
        doorArTitles.clear();
        doorEnTitles.clear();
        doorArTitles.add(mainActivity.getResources().getString(R.string.select_doors));
        doorEnTitles.add(mainActivity.getResources().getString(R.string.select_doors));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest doorRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllDoor", response -> {
            try {
                JSONArray doorArray = new JSONArray(response);
                for (int i=0; i<doorArray.length(); i++){
                    JSONObject doorObj = doorArray.getJSONObject(i);
                    doorArTitles.add(doorObj.getString("ar_Text"));
                    doorEnTitles.add(doorObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(doorsSpinner, doorArTitles);
                }else {
                    setSpinner(doorsSpinner, doorEnTitles);
                }
                currentDoor = doorEnTitles.get(0)+"-"+doorArTitles.get(0);
                getWarranty();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.doors), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.doors), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(doorRequest);
    }

    private void getWarranty(){
        warrantyTitles.add(mainActivity.getResources().getString(R.string.under_warranty));
        warrantyTitles.add(mainActivity.getResources().getString(R.string.no));
        warrantyTitles.add(mainActivity.getResources().getString(R.string.yes));
        warrantyTitles.add(mainActivity.getResources().getString(R.string.not_apply));

        setSpinner(warrantySpinner, warrantyTitles);
        getTransmission();
    }

    private void getTransmission(){
        transmissionArTitles.clear();
        transmissionEnTitles.clear();
        transmissionArTitles.add(mainActivity.getResources().getString(R.string.select_transmissions));
        transmissionEnTitles.add(mainActivity.getResources().getString(R.string.select_transmissions));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest transmissionRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllTransmission", response -> {
            try {
                JSONArray transmissionArray = new JSONArray(response);
                for (int i=0; i<transmissionArray.length(); i++){
                    JSONObject transmissionObj = transmissionArray.getJSONObject(i);
                    transmissionArTitles.add(transmissionObj.getString("ar_Text"));
                    transmissionEnTitles.add(transmissionObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(transmissionSpinner, transmissionArTitles);
                }else {
                    setSpinner(transmissionSpinner, transmissionEnTitles);
                }
                currentTransmission = transmissionEnTitles.get(0)+"-"+transmissionArTitles.get(0);
                getBodyType();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.transmissions), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.transmissions), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(transmissionRequest);
    }

    private void getBodyType(){
        bodyTypeArTitles.clear();
        bodyTypeEnTitles.clear();
        bodyTypeArTitles.add(mainActivity.getResources().getString(R.string.select_body_type));
        bodyTypeEnTitles.add(mainActivity.getResources().getString(R.string.select_body_type));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest bodyTypeRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllBodyType", response -> {
            try {
                JSONArray bodyTypeArray = new JSONArray(response);
                for (int i=0; i<bodyTypeArray.length(); i++){
                    JSONObject bodyTypeObj = bodyTypeArray.getJSONObject(i);
                    bodyTypeArTitles.add(bodyTypeObj.getString("ar_Text"));
                    bodyTypeEnTitles.add(bodyTypeObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(bodyTypeSpinner, bodyTypeArTitles);
                }else {
                    setSpinner(bodyTypeSpinner, bodyTypeEnTitles);
                }
                currentBody = bodyTypeEnTitles.get(0)+"-"+bodyTypeArTitles.get(0);
                getFuelType();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.body_type), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.body_type), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(bodyTypeRequest);
    }

    private void getFuelType(){
        fuelTypeEnTitles.clear();
        fuelTypeArTitles.clear();
        fuelTypeArTitles.add(mainActivity.getResources().getString(R.string.select_fuel_type));
        fuelTypeEnTitles.add(mainActivity.getResources().getString(R.string.select_fuel_type));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest fuelTypeRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllFuelType", response -> {
            try {
                JSONArray fuelTypeArray = new JSONArray(response);
                for (int i=0; i<fuelTypeArray.length(); i++){
                    JSONObject fuelTypeObj = fuelTypeArray.getJSONObject(i);
                    fuelTypeArTitles.add(fuelTypeObj.getString("ar_Text"));
                    fuelTypeEnTitles.add(fuelTypeObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(fuelTypeSpinner, fuelTypeArTitles);
                }else {
                    setSpinner(fuelTypeSpinner, fuelTypeEnTitles);
                }
                currentFuel = fuelTypeEnTitles.get(0)+"-"+fuelTypeArTitles.get(0);
                getCylinders();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.fuel_type), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.fuel_type), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(fuelTypeRequest);
    }

    private void getCylinders(){
        cylinderEnTitles.clear();
        cylinderArTitles.clear();
        cylinderArTitles.add(mainActivity.getResources().getString(R.string.select_no_of_cylinders));
        cylinderEnTitles.add(mainActivity.getResources().getString(R.string.select_no_of_cylinders));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest cylinderRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllNoOfCylinders", response -> {
            try {
                JSONArray cylinderArray = new JSONArray(response);
                for (int i=0; i<cylinderArray.length(); i++){
                    JSONObject cylinderObj = cylinderArray.getJSONObject(i);
                    cylinderArTitles.add(cylinderObj.getString("ar_Text"));
                    cylinderEnTitles.add(cylinderObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(noOfCylindersSpinner, cylinderArTitles);
                }else {
                    setSpinner(noOfCylindersSpinner, cylinderEnTitles);
                }
                currentCylinder = cylinderEnTitles.get(0)+"-"+cylinderArTitles.get(0);
                getSteering();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.no_of_cylinders), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.no_of_cylinders), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(cylinderRequest);
    }

    private void getSteering(){
        steeringEnTitles.clear();
        steeringArTitles.clear();
        steeringArTitles.add(mainActivity.getResources().getString(R.string.select_steering_side));
        steeringEnTitles.add(mainActivity.getResources().getString(R.string.select_steering_side));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest steeringRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllSteeringSide", response -> {
            try {
                JSONArray steeringArray = new JSONArray(response);
                for (int i=0; i<steeringArray.length(); i++){
                    JSONObject steeringObj = steeringArray.getJSONObject(i);
                    steeringArTitles.add(steeringObj.getString("ar_Text"));
                    steeringEnTitles.add(steeringObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(steeringSideSpinner, steeringArTitles);
                }else {
                    setSpinner(steeringSideSpinner, steeringEnTitles);
                }
                currentSteering = steeringEnTitles.get(0)+"-"+steeringArTitles.get(0);
                getHorsepower();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.steering_side), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.steering_side), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(steeringRequest);
    }

    private void getHorsepower(){
        horsepowerEnTitles.clear();
        horsepowerArTitles.clear();
        horsepowerEnTitles.add(mainActivity.getResources().getString(R.string.select_horsepower));
        horsepowerArTitles.add(mainActivity.getResources().getString(R.string.select_horsepower));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest horsepowerRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllHorsepower?categoryId=2", response -> {
            try {
                JSONArray horsepowerArray = new JSONArray(response);
                for (int i=0; i<horsepowerArray.length(); i++){
                    JSONObject horsepowerObj = horsepowerArray.getJSONObject(i);
                    horsepowerArTitles.add(horsepowerObj.getString("ar_Text"));
                    horsepowerEnTitles.add(horsepowerObj.getString("en_Text"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(horsePowerSpinner, horsepowerArTitles);
                }else {
                    setSpinner(horsePowerSpinner, horsepowerEnTitles);
                }
                currentHorsePower = horsepowerEnTitles.get(0)+"-"+horsepowerArTitles.get(0);
                getLocations();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.horsepower), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.horsepower), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(horsepowerRequest);
    }

    private void getLocations(){
        locationEnTitles.clear();
        locationArTitles.clear();
        locationEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
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
                    setSpinner(locationSpinner, locationArTitles);
                }else {
                    setSpinner(locationSpinner, locationEnTitles);
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

    private void setData(String price, String phoneNumber, String description, String mileage){
        price = mainActivity.formatter.format(Double.parseDouble(price));
        Send.addUsedCarsAd.setPrice(price);
        Send.addUsedCarsAd.setPhoneNumber(phoneNumber);
        Send.addUsedCarsAd.setDescription(description);
        Send.addUsedCarsAd.setKilometers(mileage);
        Send.addUsedCarsAd.setRegionalSpecs(currentSpec);
        Send.addUsedCarsAd.setColor(currentColor);
        Send.addUsedCarsAd.setDoors(currentDoor);
        Send.addUsedCarsAd.setWarranty(currentWarranty);
        Send.addUsedCarsAd.setTransmission(currentTransmission);
        Send.addUsedCarsAd.setBodyType(currentBody);
        Send.addUsedCarsAd.setFuelType(currentFuel);
        Send.addUsedCarsAd.setNoOfCylinders(currentCylinder);
        Send.addUsedCarsAd.setSteeringSide(currentSteering);
        Send.addUsedCarsAd.setHorsePower(currentHorsePower);
        Send.addUsedCarsAd.setLocation(currentLocation);
        Send.addUsedCarsAd.setMainPhoto(pictures.get(0));
        Send.addUsedCarsAd.setPictures(pictures);
        Send.addUsedCarsAd.setLatitude(latitude);
        Send.addUsedCarsAd.setLongitude(longitude);
//        setInitialUsedCars();
        packagesPopUp();
    }

    private void setInitialUsedCars(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject initialObj = new JSONObject();
        try {
            initialObj.put("title", Send.addUsedCarsAd.getTitle());
            initialObj.put("categoryId", Send.addUsedCarsAd.getCategoryId());
            initialObj.put("maker", Send.addUsedCarsAd.getBrand());
            initialObj.put("otherMaker", Send.addUsedCarsAd.getOtherBrand());
            initialObj.put("model", Send.addUsedCarsAd.getModel());
            initialObj.put("otherModel", Send.addUsedCarsAd.getOtherModel());
            initialObj.put("trim", Send.addUsedCarsAd.getTrim());
            initialObj.put("otherTrim", Send.addUsedCarsAd.getOtherTrim());
            initialObj.put("year", Send.addUsedCarsAd.getYear());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest initialRequest = new JsonObjectRequest(Request.Method.POST, Urls.MotorAds_URL+"AddInitialMotor", initialObj, response -> {
            try {
                Send.addUsedCarsAd.setAdId(response.getString("id"));
                postFullUsedCarsAd();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(initialRequest);
    }

    private void postFullUsedCarsAd(){
//        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.MotorAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody adId = createRequestBody(Send.addUsedCarsAd.getAdId());
        RequestBody description = createRequestBody(Send.addUsedCarsAd.getDescription());
        RequestBody location = createRequestBody(Send.addUsedCarsAd.getLocation());
        RequestBody lat = createRequestBody(Send.addUsedCarsAd.getLatitude());
        RequestBody lan = createRequestBody(Send.addUsedCarsAd.getLongitude());
        RequestBody mainPhoto = createRequestBody(Send.addUsedCarsAd.getMainPhoto());
        RequestBody categoryId = createRequestBody(Send.addUsedCarsAd.getCategoryId());
        RequestBody kilometers = createRequestBody(Send.addUsedCarsAd.getKilometers());
        RequestBody price = createRequestBody(Send.addUsedCarsAd.getPrice());
        RequestBody regionalSpecs = createRequestBody(Send.addUsedCarsAd.getRegionalSpecs());
        RequestBody color = createRequestBody(Send.addUsedCarsAd.getColor());
        RequestBody doors = createRequestBody(Send.addUsedCarsAd.getDoors());
        RequestBody warranty = createRequestBody(Send.addUsedCarsAd.getWarranty());
        RequestBody transmission = createRequestBody(Send.addUsedCarsAd.getTransmission());
        RequestBody bodyType = createRequestBody(Send.addUsedCarsAd.getBodyType());
        RequestBody fuelType = createRequestBody(Send.addUsedCarsAd.getFuelType());
        RequestBody noOfCylinders = createRequestBody(Send.addUsedCarsAd.getNoOfCylinders());
        RequestBody steeringSide = createRequestBody(Send.addUsedCarsAd.getSteeringSide());
        RequestBody horsepower = createRequestBody(Send.addUsedCarsAd.getHorsePower());
        RequestBody phoneNumber = createRequestBody(Send.addUsedCarsAd.getPhoneNumber());
        ArrayList<MultipartBody.Part> images = new ArrayList<>();

        for (int i=0; i<pictures.size(); i++){
            images.add(createMultipartBodyPart("pictures",pictures.get(i)));
        }

        Call<AddAdsResponse> addUsedCarsAd = retrofit.create(RetrofitUrls.class).addUsedCarsAd(adId, description, location, lat, lan, price,
                mainPhoto, categoryId, kilometers, regionalSpecs, color, doors, warranty, transmission, bodyType, fuelType, noOfCylinders, steeringSide, horsepower, phoneNumber, images);
        addUsedCarsAd.enqueue(new Callback<AddAdsResponse>() {
            @Override
            public void onResponse(Call<AddAdsResponse> call, Response<AddAdsResponse> response) {
                mainActivity.hideProgressDialog();
                Timber.tag("response").d(response.message());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_posted_successfully), Toast.LENGTH_SHORT).show();
//                        startActivity("");
                        if (packageId.isEmpty()){
                            startActivity("home");
                        }else {
                            navController.navigate(Sell3FragmentDirections.actionSell3FragmentToPaymentFragment(packageId, ""));
                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.ad_posted_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

    }

    private void pickImage(int img) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    REQUEST_CODE);
        } else {
            PickImageDialog.build(new PickSetup()).setOnPickResult(r -> {
                switch (img){
                    case 1:
                        image1.setImageBitmap(r.getBitmap());
                        image1Path = r.getPath();
                        image1Bitmap = r.getBitmap();
                        break;
                    case 2:
                        image2.setImageBitmap(r.getBitmap());
                        image2Path = r.getPath();
                        image2Bitmap = r.getBitmap();
                        break;
                    case 3:
                        image3.setImageBitmap(r.getBitmap());
                        image3Path = r.getPath();
                        image3Bitmap = r.getBitmap();
                        break;
                    case 4:
                        image4.setImageBitmap(r.getBitmap());
                        image4Path = r.getPath();
                        image4Bitmap = r.getBitmap();
                        break;
                    case 5:
                        image5.setImageBitmap(r.getBitmap());
                        image5Path = r.getPath();
                        image5Bitmap = r.getBitmap();
                        break;
                    case 6:
                        image6.setImageBitmap(r.getBitmap());
                        image6Path = r.getPath();
                        image6Bitmap = r.getBitmap();
                        break;
                    case 7:
                        image7.setImageBitmap(r.getBitmap());
                        image7Path = r.getPath();
                        image7Bitmap = r.getBitmap();
                        break;
                    case 8:
                        image8.setImageBitmap(r.getBitmap());
                        image8Path = r.getPath();
                        image8Bitmap = r.getBitmap();
                        break;
                    case 9:
                        image9.setImageBitmap(r.getBitmap());
                        image9Path = r.getPath();
                        image9Bitmap = r.getBitmap();
                        break;
                    case 10:
                        image10.setImageBitmap(r.getBitmap());
                        image10Path = r.getPath();
                        image10Bitmap = r.getBitmap();
                        break;
                }
            }).show(getActivity());
        }
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
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
        packageId = "" ;
        freeLayout.setOnClickListener(v -> freeCB.setChecked(true));
        freeCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                setPackagesAdapter(packagesRV);
            }
        });
        getPackages(packagesRV);
        close.setOnClickListener(v -> myAdsOptionsAlertBuilder.dismiss());
        done.setOnClickListener(v -> {
            setInitialUsedCars();
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
        PackagesAdapter businessPackagesAdapter = new PackagesAdapter(this,packages, -1);
        recyclerView.setAdapter(businessPackagesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    private void changeImages(ImageView img1,ImageView img2,ImageView img3,ImageView img4,ImageView img5,ImageView img6,ImageView img7,ImageView img8,ImageView img9,ImageView img10){
        img1.setBackground(mainActivity.getResources().getDrawable(R.drawable.orange_border));
        img2.setBackground(null);
        img3.setBackground(null);
        img4.setBackground(null);
        img5.setBackground(null);
        img6.setBackground(null);
        img7.setBackground(null);
        img8.setBackground(null);
        img9.setBackground(null);
        img10.setBackground(null);
    }

    private void clearMainPath(String imagePath, ImageView image){
        String subPath = imagePath.substring(imagePath.lastIndexOf("/")+1);
        if (subPath.equals(mainPath)){
            Glide.with(mainActivity).load(R.drawable.ic_motors).into(adImage);
            image.setBackground(null);
        }
        mainPath = "";
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