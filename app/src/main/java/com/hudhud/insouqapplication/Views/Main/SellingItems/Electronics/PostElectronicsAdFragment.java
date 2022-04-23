package com.hudhud.insouqapplication.Views.Main.SellingItems.Electronics;

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
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AdSubCategory;
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

public class PostElectronicsAdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    private static final int REQUEST_CODE = 101;
    int subCatId;
    ImageView image1, image2, image3, image4, image5, image7, image6, image8, image9, image10, uploadImage, mainImage;
    ImageView closeImage1, closeImage2, closeImage3, closeImage4, closeImage5, closeImage7, closeImage6, closeImage8, closeImage9, closeImage10, closeMainImage, adImage;
    String image1Path = "", image2Path = "", image3Path = "", image4Path = "", image5Path = "", image7Path = "", image6Path = "", image8Path = "", image9Path = "", image10Path = "", mainPath = "", longitude = "", latitude = "", address = "";
    Bitmap image1Bitmap, image2Bitmap, image3Bitmap, image4Bitmap, image5Bitmap, image7Bitmap, image6Bitmap, image8Bitmap, image9Bitmap, image10Bitmap;
    Spinner subCategoriesSpinner, subTypesSpinner, versionSpinner, ramSpinner, storageSpinner, agesSpinner, usagesSpinner, colorsSpinner, underWarrantySpinner, locationsSpinner;
    LinearLayoutCompat versions, ram, storage;
    EditText electronicsAdEdt, electronicsAdPriceEdt, electronicsAdDescEdt, otherSubCat, otherTrim, phoneNum;
    TextView electronicsTitle, adLocation, adPrice, location;
    ArrayList<Integer> adSubCategoriesIds, trimIds;
    ArrayList<String> adSubCategoriesTitles, trimTitles, versionEnTitles, versionArTitles, ramEnTitles, ramArTitles, storageEnTitles, storageArTitles, agesEnTitles, agesArTitles, usageEnTitles, usageArTitles, underWarranty, colorsEnTitles, colorsArTitles, locationsEnTitles, locationsArTitles;
    ArrayList<String> pictures = new ArrayList<>();
    CheckBox agreementCheckBok;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;
    boolean spinner1 = false, spinner2 = false, spinner3 = false, spinner4 = false, spinner5 = false, spinner6 = false, spinner7 = false, spinner8 = false, spinner9 = false, spinner10 = false;

    public PostElectronicsAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_electronics_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        onSpinnerSelected();
        getSubCategories();
        getWarranty();
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

        agreementCheckBok = view.findViewById(R.id.agreement_checkbox);
        electronicsAdEdt = view.findViewById(R.id.electronics_ad_edt);
        electronicsTitle = view.findViewById(R.id.electronics_title);
        electronicsAdPriceEdt = view.findViewById(R.id.ad_price);
        adPrice = view.findViewById(R.id.price);
        otherSubCat = view.findViewById(R.id.other_sub_cat);
        otherTrim = view.findViewById(R.id.other_trim);
        electronicsAdDescEdt = view.findViewById(R.id.ad_short_description);
        adLocation = view.findViewById(R.id.ad_location);
        location = view.findViewById(R.id.location);
        phoneNum = view.findViewById(R.id.phone_number);
        adImage = view.findViewById(R.id.electronics_image);

        adSubCategoriesIds = new ArrayList<>();
        adSubCategoriesTitles = new ArrayList<>();
        trimIds = new ArrayList<>();
        trimTitles = new ArrayList<>();
        agesEnTitles = new ArrayList<>();
        agesArTitles = new ArrayList<>();
        usageEnTitles = new ArrayList<>();
        usageArTitles = new ArrayList<>();
        underWarranty = new ArrayList<>();
        colorsEnTitles = new ArrayList<>();
        colorsArTitles = new ArrayList<>();
        locationsEnTitles = new ArrayList<>();
        locationsArTitles = new ArrayList<>();
        versionArTitles = new ArrayList<>();
        versionEnTitles = new ArrayList<>();
        ramArTitles = new ArrayList<>();
        ramEnTitles = new ArrayList<>();
        storageArTitles = new ArrayList<>();
        storageEnTitles = new ArrayList<>();

        continueBtn = view.findViewById(R.id.continue_btn);
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

        subCategoriesSpinner = view.findViewById(R.id.sub_category_spinner);
        subTypesSpinner = view.findViewById(R.id.sub_type_spinner);
        agesSpinner = view.findViewById(R.id.ages_spinner);
        usagesSpinner = view.findViewById(R.id.usages_spinner);
        colorsSpinner = view.findViewById(R.id.colors_spinner);
        underWarrantySpinner = view.findViewById(R.id.warranty_spinner);
        locationsSpinner = view.findViewById(R.id.locations_spinner);
        versionSpinner = view.findViewById(R.id.versions_spinner);
        ramSpinner = view.findViewById(R.id.ram_spinner);
        storageSpinner = view.findViewById(R.id.storage_spinner);
        versions = view.findViewById(R.id.versions_layout);
        ram = view.findViewById(R.id.ram_layout);
        storage = view.findViewById(R.id.storage_layout);

        if (getArguments() != null){
            subCatId = PostElectronicsAdFragmentArgs.fromBundle(getArguments()).getSubCatId();
            getAge();
            getVersion();
            getRAM();
            getStorage();
            getUsage();
            getColors();
            getLocations();
            if (!AppDefs.user.getMobileNumber().equals("null")) {
                phoneNum.setText(AppDefs.user.getMobileNumber());
            }
            if (subCatId == 19 || subCatId == 33){
                versions.setVisibility(View.VISIBLE);
                ram.setVisibility(View.VISIBLE);
                storage.setVisibility(View.VISIBLE);
            }else {
                versions.setVisibility(View.GONE);
                ram.setVisibility(View.GONE);
                storage.setVisibility(View.GONE);
            }
            Send.electronicsAd.setCategoryId(String.valueOf(subCatId));
        }
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String adTitle = String.valueOf(electronicsAdEdt.getText());
            String adPrice = String.valueOf(electronicsAdPriceEdt.getText());
            String adDescriptions = String.valueOf(electronicsAdDescEdt.getText());
            String otherSubCategory = String.valueOf(otherSubCat.getText());
            String otherTrimStr = String.valueOf(otherTrim.getText());
            if (adPrice.isEmpty() || adTitle.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherSubCat.getVisibility() == View.VISIBLE){
                if (otherSubCategory.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else if (otherTrim.getVisibility() == View.VISIBLE){
                    if (otherTrimStr.isEmpty()){
                        mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                    }else {
                        setData(adTitle, adPrice, otherSubCategory, otherTrimStr, adDescriptions);
                    }
                }else {
                    setData(adTitle, adPrice, otherSubCategory, otherTrimStr, adDescriptions);
                }
            }else if (otherTrim.getVisibility() == View.VISIBLE){
                if (otherTrimStr.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                    setData(adTitle, adPrice, otherSubCategory, otherTrimStr, adDescriptions);
                }
            }else {
                setData(adTitle, adPrice, otherSubCategory, otherTrimStr, adDescriptions);
            }
        });

        electronicsAdEdt.setOnEditorActionListener(((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    electronicsTitle.setText(String.valueOf(electronicsAdEdt.getText()));
                    Send.electronicsAd.setTitle(String.valueOf(electronicsAdEdt.getText()));
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        }));

        electronicsAdPriceEdt.setOnEditorActionListener(((textView, i, keyEvent) -> {
            switch (i) {
                case EditorInfo.IME_ACTION_DONE:
                case EditorInfo.IME_ACTION_NEXT:
                case EditorInfo.IME_ACTION_PREVIOUS:
                    adPrice.setText(electronicsAdPriceEdt.getText() +" AED");
                    Send.electronicsAd.setPrice(String.valueOf(electronicsAdPriceEdt.getText()));
                    mainActivity.hideKeyboard();
                    return true;
            }
            return false;
        }));

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
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image1);
            image1Path = "";
        });

        closeImage2.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image2);
            image2Path = "";
        });

        closeImage3.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image3);
            image3Path = "";
        });

        closeImage4.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image4);
            image4Path = "";
        });

        closeImage5.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image5);
            image5Path = "";
        });

        closeImage6.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image6);
            image6Path = "";
        });

        closeImage7.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image7);
            image7Path = "";
        });

        closeImage8.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image8);
            image8Path = "";
        });

        closeImage9.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image9);
            image9Path = "";
        });

        closeImage10.setOnClickListener(view -> {
            Glide.with(mainActivity).load(R.drawable.gray_image).into(image10);
            image10Path = "";
        });
    }

    private void setData(String adTitle, String adPrice, String otherSubCategory, String otherTrimStr, String adDescriptions){
        Send.electronicsAd.setTitle(adTitle);
        adPrice = mainActivity.formatter.format(Double.parseDouble(adPrice));
        Send.electronicsAd.setPrice(adPrice);
        Send.electronicsAd.setOtherSubCategory(otherSubCategory);
        Send.electronicsAd.setOtherSubTypeId(otherTrimStr);
        Send.electronicsAd.setDescription(adDescriptions);
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
        }else  if (!agreementCheckBok.isChecked()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.check_box));
        }else  {
            Send.electronicsAd.setPictures(pictures);
            Send.electronicsAd.setMainPhoto(pictures.get(0));
            Send.electronicsAd.setPhoneNumber(String.valueOf(phoneNum.getText()));
            if (subCatId == 19 || subCatId == 33){
                if (Send.electronicsAd.getSubCategoryId().equals("-1") || Send.electronicsAd.getSubTypeId().equals("-1") || Send.electronicsAd.getAge().equals("-1")
                        || Send.electronicsAd.getUsage().equals("-1") || Send.electronicsAd.getWarranty().equals("-1") || Send.electronicsAd.getColor().equals("-1")
                        || Send.electronicsAd.getLocation().equals("-1") || Send.electronicsAd.getVersion().equals("-1")|| Send.electronicsAd.getRam().equals("-1")
                        || Send.electronicsAd.getStorage().equals("-1")){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                    packagesPopUp();
                }
            }else {
                if (Send.electronicsAd.getSubCategoryId().equals("-1") || Send.electronicsAd.getSubTypeId().equals("-1") || Send.electronicsAd.getAge().equals("-1")
                        || Send.electronicsAd.getUsage().equals("-1") || Send.electronicsAd.getWarranty().equals("-1") || Send.electronicsAd.getColor().equals("-1")
                        || Send.electronicsAd.getLocation().equals("-1")){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                    packagesPopUp();
                }
            }

        }

    }

    private void onSpinnerSelected(){
        subCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setSubCategoryId("-1");
                }else {
                    if (adSubCategoriesTitles.size() == 1){
                        otherSubCat.setVisibility(View.VISIBLE);
                        getTrim(0);
                        Send.electronicsAd.setSubCategoryId("0");
                    }else {
                        if (i == adSubCategoriesTitles.size()-1){
                            otherSubCat.setVisibility(View.VISIBLE);
                            getTrim(0);
                            Send.electronicsAd.setSubCategoryId(String.valueOf(adSubCategoriesIds.get(adSubCategoriesIds.size()-1)));
                        }else {
                            getTrim(adSubCategoriesIds.get(i));
                            otherSubCat.setVisibility(View.GONE);
                            Send.electronicsAd.setSubCategoryId(String.valueOf(adSubCategoriesIds.get(i)));
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setSubTypeId("-1");
                }else {
                    if (trimIds.size() == 0){
                        otherTrim.setVisibility(View.VISIBLE);
                        Send.electronicsAd.setSubTypeId("0");
                    }else {
                        if (i == trimTitles.size()-1){
                            otherTrim.setVisibility(View.VISIBLE);
                            Send.electronicsAd.setSubTypeId("0");
                        }else {
                            otherTrim.setVisibility(View.GONE);
                            Send.electronicsAd.setSubTypeId(String.valueOf(trimIds.get(i)));
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        agesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setAge("-1");
                }else {
                    Send.electronicsAd.setAge(agesEnTitles.get(i)+"-"+agesArTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        versionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setVersion("-1");
                }else {
                    Send.electronicsAd.setVersion(versionEnTitles.get(i)+"-"+versionArTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ramSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setRam("-1");
                }else {
                    Send.electronicsAd.setRam(ramEnTitles.get(i)+"-"+ramArTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        storageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setStorage("-1");
                }else {
                    Send.electronicsAd.setStorage(storageEnTitles.get(i)+"-"+storageArTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        usagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner4 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if(i==0){
                    Send.electronicsAd.setUsage("-1");
                }else {
                    Send.electronicsAd.setUsage(usageEnTitles.get(i)+"-"+usageArTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        underWarrantySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setWarranty("-1");
                }else {
                    if (i==1){
                        Send.electronicsAd.setWarranty("0");
                    }else if (i==2){
                        Send.electronicsAd.setWarranty("1");
                    }else if (i==3){
                        Send.electronicsAd.setWarranty("2");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        colorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setColor("-1");
                }else {
                    Send.electronicsAd.setColor(colorsEnTitles.get(i)+"-"+colorsArTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Send.electronicsAd.setLocation("-1");
                }else {
                    Send.electronicsAd.setLocation(locationsEnTitles.get(i)+"-"+locationsArTitles.get(i));
                    adLocation.setText(locationsEnTitles.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
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

    private void getSubCategories(){
        adSubCategoriesTitles.clear();
        adSubCategoriesIds.clear();
        adSubCategoriesTitles.add(mainActivity.getResources().getString(R.string.select_brands));
        adSubCategoriesIds.add(-1);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+subCatId,response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++){
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    AdSubCategory adSubCategory = new AdSubCategory();
                    adSubCategory.setId(subCategoryObj.getInt("id"));
                    adSubCategory.setArName(subCategoryObj.getString("ar_Name"));
                    adSubCategory.setEnName(subCategoryObj.getString("en_Name"));
                    adSubCategory.setCategoryId(subCategoryObj.getString("categoryId"));
                    adSubCategory.setStatus(subCategoryObj.getString("status"));
                    adSubCategoriesIds.add(adSubCategory.getId());
                    if (AppDefs.language.equals("ar")){
                        adSubCategoriesTitles.add(adSubCategory.getArName());
                    }else {
                        adSubCategoriesTitles.add(adSubCategory.getEnName());
                    }
                }
                adSubCategoriesTitles.add(mainActivity.getResources().getString(R.string.other));
                setSpinner(subCategoriesSpinner, adSubCategoriesTitles);
                Send.electronicsAd.setSubCategoryId(String.valueOf(adSubCategoriesIds.get(0)));
                getTrim(adSubCategoriesIds.get(0));
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

    private void getTrim(int id){
        trimTitles.clear();
        trimIds.clear();
        trimTitles.add(mainActivity.getResources().getString(R.string.select_model));
        trimIds.add(-1);
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest getTrimsRequest = new StringRequest(Request.Method.GET, Urls.SubType_URL+"GetBySubCategoryId?subCategoryId="+id, response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray trimsArray = new JSONArray(response);
                for (int i=0; i<trimsArray.length(); i++){
                    JSONObject trimObj = trimsArray.getJSONObject(i);
                    trimIds.add(trimObj.getInt("id"));
                    if (AppDefs.language.equals("ar")){
                        trimTitles.add(trimObj.getString("ar_Name"));
                    }else {
                        trimTitles.add(trimObj.getString("en_Name"));
                    }
                }
                trimTitles.add(mainActivity.getResources().getString(R.string.other));
                setSpinner(subTypesSpinner, trimTitles);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.trim), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.trim), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(getTrimsRequest);
    }

    private void getAge(){
        agesEnTitles.clear();
        agesArTitles.clear();
        agesArTitles.add(mainActivity.getResources().getString(R.string.select_age));
        agesEnTitles.add(mainActivity.getResources().getString(R.string.select_age));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllAgeByTypeId?typeId=8", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    agesArTitles.add(ageObj.getString("ar_Text"));
                    agesEnTitles.add(ageObj.getString("en_Text"));
                }
                Send.electronicsAd.setAge("-1");
                if (AppDefs.language.equals("ar")){
                    setSpinner(agesSpinner, agesArTitles);
                }else {
                    setSpinner(agesSpinner, agesEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.age), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getVersion(){
        versionEnTitles.clear();
        versionArTitles.clear();
        versionArTitles.add(mainActivity.getResources().getString(R.string.select_version));
        versionEnTitles.add(mainActivity.getResources().getString(R.string.select_version));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMobileVersion", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    versionArTitles.add(ageObj.getString("ar_Text"));
                    versionEnTitles.add(ageObj.getString("en_Text"));
                }
                Send.electronicsAd.setVersion("-1");
                if (AppDefs.language.equals("ar")){
                    setSpinner(versionSpinner, versionArTitles);
                }else {
                    setSpinner(versionSpinner, versionEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.version), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.version), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getRAM(){
        ramEnTitles.clear();
        ramArTitles.clear();
        ramArTitles.add(mainActivity.getResources().getString(R.string.select_RAM));
        ramEnTitles.add(mainActivity.getResources().getString(R.string.select_RAM));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMobileRam", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    ramArTitles.add(ageObj.getString("ar_Text"));
                    ramEnTitles.add(ageObj.getString("en_Text"));
                }
                Send.electronicsAd.setRam("-1");
                if (AppDefs.language.equals("ar")){
                    setSpinner(ramSpinner, ramArTitles);
                }else {
                    setSpinner(ramSpinner, ramEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.ram), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.ram), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getStorage(){
        storageEnTitles.clear();
        storageArTitles.clear();
        storageEnTitles.add(mainActivity.getResources().getString(R.string.select_storage));
        storageArTitles.add(mainActivity.getResources().getString(R.string.select_storage));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest agesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMobileStarage", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray agesArray = new JSONArray(response);
                for (int i=0; i<agesArray.length(); i++){
                    JSONObject ageObj = agesArray.getJSONObject(i);
                    storageArTitles.add(ageObj.getString("ar_Text"));
                    storageEnTitles.add(ageObj.getString("en_Text"));
                }
                Send.electronicsAd.setStorage("-1");
                if (AppDefs.language.equals("ar")){
                    setSpinner(storageSpinner, storageArTitles);
                }else {
                    setSpinner(storageSpinner, storageEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.storage), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.storage), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(agesRequest);
    }

    private void getUsage(){
        usageEnTitles.clear();
        usageArTitles.clear();
        usageArTitles.add(mainActivity.getResources().getString(R.string.select_usage));
        usageEnTitles.add(mainActivity.getResources().getString(R.string.select_usage));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest usageRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllUsageByTypeId?typeId=8", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray usagesArray = new JSONArray(response);
                for (int i=0; i<usagesArray.length(); i++){
                    JSONObject usageObj = usagesArray.getJSONObject(i);
                    usageArTitles.add(usageObj.getString("ar_Text"));
                    usageEnTitles.add(usageObj.getString("en_Text"));
                }
                Send.electronicsAd.setUsage(usageEnTitles.get(0)+"-"+usageArTitles.get(0));
                if (AppDefs.language.equals("ar")){
                    setSpinner(usagesSpinner, usageArTitles);
                }else {
                    setSpinner(usagesSpinner, usageEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.usage), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(usageRequest);
    }

    private void getWarranty(){
        underWarranty.add(mainActivity.getResources().getString(R.string.under_warranty));
        underWarranty.add(mainActivity.getResources().getString(R.string.no));
        underWarranty.add(mainActivity.getResources().getString(R.string.yes));
        underWarranty.add(mainActivity.getResources().getString(R.string.not_apply));

        setSpinner(underWarrantySpinner, underWarranty);
    }

    private void getColors(){
        colorsArTitles.clear();
        colorsEnTitles.clear();
        colorsArTitles.add(mainActivity.getResources().getString(R.string.select_color));
        colorsEnTitles.add(mainActivity.getResources().getString(R.string.select_color));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest colorsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllColor", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray colorsArray = new JSONArray(response);
                for (int i=0; i<colorsArray.length(); i++){
                    JSONObject colorObj = colorsArray.getJSONObject(i);
                    colorsArTitles.add(colorObj.getString("ar_Text"));
                    colorsEnTitles.add(colorObj.getString("en_Text"));
                }
                Send.electronicsAd.setColor(colorsEnTitles.get(0)+"-"+colorsArTitles.get(0));
                if (AppDefs.language.equals("ar")){
                    setSpinner(colorsSpinner, colorsArTitles);
                }else {
                    setSpinner(colorsSpinner, colorsEnTitles);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.color), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.color), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(colorsRequest);
    }

    private void getLocations(){
        locationsEnTitles.clear();
        locationsArTitles.clear();
        locationsArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationsEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest locationsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllLocation", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray locationsArray = new JSONArray(response);
                for (int i=0; i<locationsArray.length(); i++){
                    JSONObject locationObj = locationsArray.getJSONObject(i);
                    locationsArTitles.add(locationObj.getString("ar_Text"));
                    locationsEnTitles.add(locationObj.getString("en_Text"));
                }
                Send.electronicsAd.setLocation(locationsEnTitles.get(0)+"-"+locationsArTitles.get(0));
                adLocation.setText(locationsEnTitles.get(0));
                if (AppDefs.language.equals("ar")){
                    setSpinner(locationsSpinner, locationsArTitles);
                }else {
                    setSpinner(locationsSpinner, locationsEnTitles);
                }
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

    private void postElectronicAd(){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.ElectronicAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody title = createRequestBody(Send.electronicsAd.getTitle());
        RequestBody description = createRequestBody(Send.electronicsAd.getDescription());
        RequestBody location = createRequestBody(Send.electronicsAd.getLocation());
        RequestBody lat = createRequestBody(Send.electronicsAd.getLat());
        RequestBody lan = createRequestBody(Send.electronicsAd.getLan());
        RequestBody mainPhoto = createRequestBody(Send.electronicsAd.getMainPhoto());
        RequestBody categoryId = createRequestBody(Send.electronicsAd.getCategoryId());
        RequestBody age = createRequestBody(Send.electronicsAd.getAge());
        RequestBody usage = createRequestBody(Send.electronicsAd.getUsage());
        RequestBody price = createRequestBody(Send.electronicsAd.getPrice());
        RequestBody color = createRequestBody(Send.electronicsAd.getColor());
        RequestBody warranty = createRequestBody(Send.electronicsAd.getWarranty());
        RequestBody subCategoryId = createRequestBody(Send.electronicsAd.getSubCategoryId());
        RequestBody otherSubCategory = createRequestBody(Send.electronicsAd.getOtherSubCategory());
        RequestBody subTypeId = createRequestBody(Send.electronicsAd.getSubTypeId());
        RequestBody otherSubType = createRequestBody(Send.electronicsAd.getOtherSubTypeId());
        RequestBody phoneNumber = createRequestBody(Send.electronicsAd.getPhoneNumber());
        ArrayList<MultipartBody.Part> images = new ArrayList<>();

        for (int i=0; i<pictures.size(); i++){
            images.add(createMultipartBodyPart("pictures",pictures.get(i)));
        }

        Call<AddAdsResponse> addElectronicAd = retrofit.create(RetrofitUrls.class).addElectronicAd(title, description, location, lat, lan, mainPhoto, categoryId, age, usage, price, color, warranty, subCategoryId, otherSubCategory, subTypeId, otherSubType, phoneNumber, images);
        addElectronicAd.enqueue(new Callback<AddAdsResponse>() {
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
                            navController.navigate(PostElectronicsAdFragmentDirections.actionPostElectronicsAdFragmentToPaymentFragment(packageId, ""));
                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.ad_posted_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

    }

    private void postElectronicAd2(){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.ElectronicAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody title = createRequestBody(Send.electronicsAd.getTitle());
        RequestBody description = createRequestBody(Send.electronicsAd.getDescription());
        RequestBody location = createRequestBody(Send.electronicsAd.getLocation());
        RequestBody lat = createRequestBody(Send.electronicsAd.getLat());
        RequestBody lan = createRequestBody(Send.electronicsAd.getLan());
        RequestBody mainPhoto = createRequestBody(Send.electronicsAd.getMainPhoto());
        RequestBody categoryId = createRequestBody(Send.electronicsAd.getCategoryId());
        RequestBody age = createRequestBody(Send.electronicsAd.getAge());
        RequestBody usage = createRequestBody(Send.electronicsAd.getUsage());
        RequestBody price = createRequestBody(Send.electronicsAd.getPrice());
        RequestBody color = createRequestBody(Send.electronicsAd.getColor());
        RequestBody warranty = createRequestBody(Send.electronicsAd.getWarranty());
        RequestBody subCategoryId = createRequestBody(Send.electronicsAd.getSubCategoryId());
        RequestBody otherSubCategory = createRequestBody(Send.electronicsAd.getOtherSubCategory());
        RequestBody subTypeId = createRequestBody(Send.electronicsAd.getSubTypeId());
        RequestBody otherSubType = createRequestBody(Send.electronicsAd.getOtherSubTypeId());
        RequestBody phoneNumber = createRequestBody(Send.electronicsAd.getPhoneNumber());
        RequestBody version = createRequestBody(Send.electronicsAd.getVersion());
        RequestBody ram = createRequestBody(Send.electronicsAd.getRam());
        RequestBody storage = createRequestBody(Send.electronicsAd.getStorage());
        ArrayList<MultipartBody.Part> images = new ArrayList<>();

        for (int i=0; i<pictures.size(); i++){
            images.add(createMultipartBodyPart("pictures",pictures.get(i)));
        }

        Call<AddAdsResponse> addElectronicAd = retrofit.create(RetrofitUrls.class).addElectronicAd2(title, description, location, lat, lan, mainPhoto, categoryId, age, usage, price, color, warranty, subCategoryId, otherSubCategory, subTypeId, otherSubType, phoneNumber, version, ram, storage, images);
        addElectronicAd.enqueue(new Callback<AddAdsResponse>() {
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
                            navController.navigate(PostElectronicsAdFragmentDirections.actionPostElectronicsAdFragmentToPaymentFragment(packageId, ""));
                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.ad_posted_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

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
            if (subCatId == 19 || subCatId == 33){
                postElectronicAd2();
            }else {
                postElectronicAd();
            }
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