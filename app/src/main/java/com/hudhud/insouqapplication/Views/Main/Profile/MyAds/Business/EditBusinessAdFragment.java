package com.hudhud.insouqapplication.Views.Main.Profile.MyAds.Business;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Typeface.BOLD;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createMultipartBodyPart;
import static com.hudhud.insouqapplication.Views.Main.MainActivity.createRequestBody;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
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

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Urls.RetrofitUrls;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Main.SellingItems.Business.BusinessPackagesAdapter;
import com.hudhud.insouqapplication.Views.Main.SellingItems.Business.PostBusinessAdFragmentDirections;
import com.hudhud.insouqapplication.Views.map.MapsActivity;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class EditBusinessAdFragment extends Fragment {

    private static final int REQUEST_CODE_MAPS_ACTIVITY = 1;
    private DecimalFormat formatter = new DecimalFormat("#0.00");
    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    private static final int REQUEST_CODE = 101;
    ImageView image1, image2, image3, image4, image5, image7, image6, image8, image9, image10, uploadImage, mainImage;
    ImageView closeImage1, closeImage2, closeImage3, closeImage4, closeImage5, closeImage7, closeImage6, closeImage8, closeImage9, closeImage10, closeMainImage, adImage;
    String image1Path = "", image2Path = "", image3Path = "", image4Path = "", image5Path = "", image7Path = "", image6Path = "", image8Path = "", image9Path = "", image10Path = "", mainPath = "", longitude = "", latitude = "", address = "";
    Bitmap image1Bitmap, image2Bitmap, image3Bitmap, image4Bitmap, image5Bitmap, image7Bitmap, image6Bitmap, image8Bitmap, image9Bitmap, image10Bitmap;
    Spinner categorySpinner, subCategorySpinner, locationSpinner;
    EditText titleEdt, priceEdt, phoneNumberEdt, descriptionEdt, otherSubCategory;
    TextView title, adLocation, adPrice, location;
    CheckBox agreementCheckBok;
    ArrayList<String> categoriesArTitles, categoriesEnTitles, categoriesIds, subCategoriesArTitles, subCategoriesEnTitles, subCategoriesIds, locationsArTitles, locationsEnTitles;
    String currentCategoryId = "", currentSubCategoryId = "", currentLocation = "";
    ArrayList<String> pictures;
    boolean spinner1 = false, spinner2 = false, spinner3 = false;
    ArrayList<PackageFS> packages = new ArrayList<>();
    public String packageId = "";
    public CheckBox freeCB;
    BusinessAd businessAd;

    public EditBusinessAdFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_business_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getCategories();
        getLocations();
        onSpinnerClick();
        setAddress();
        setData();
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
        if (getArguments() != null){
            businessAd = EditBusinessAdFragmentArgs.fromBundle(getArguments()).getBusinessAd();
        }

        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        location = view.findViewById(R.id.location);
        categorySpinner = view.findViewById(R.id.category_spinner);
        subCategorySpinner = view.findViewById(R.id.sub_category_spinner);
        locationSpinner = view.findViewById(R.id.locations_spinner);
        titleEdt = view.findViewById(R.id.business_ad_title);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        descriptionEdt = view.findViewById(R.id.ad_short_description);
        otherSubCategory = view.findViewById(R.id.other_sub_cat);
        priceEdt = view.findViewById(R.id.ad_price);
        title = view.findViewById(R.id.business_title);
        adImage = view.findViewById(R.id.business_image);
        adLocation = view.findViewById(R.id.ad_location);
        adPrice = view.findViewById(R.id.price);
        agreementCheckBok = view.findViewById(R.id.agreement_checkbox);
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

        categoriesArTitles = new ArrayList<>();
        categoriesEnTitles = new ArrayList<>();
        categoriesIds = new ArrayList<>();
        subCategoriesArTitles = new ArrayList<>();
        subCategoriesEnTitles = new ArrayList<>();
        subCategoriesIds = new ArrayList<>();
        locationsArTitles = new ArrayList<>();
        locationsEnTitles = new ArrayList<>();
        pictures = new ArrayList<>();

        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        titleEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                title.setText(String.valueOf(titleEdt.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        priceEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adPrice.setText(String.valueOf(priceEdt.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        continueBtn.setOnClickListener(view -> {
            String adTitle = String.valueOf(titleEdt.getText());
            String adDescription = String.valueOf(descriptionEdt.getText());
            String adPrice = String.valueOf(priceEdt.getText());
            String phoneNumber = String.valueOf(phoneNumberEdt.getText());
            String otherSubCat = String.valueOf(otherSubCategory.getText());

            if (adTitle.isEmpty() || adPrice.isEmpty() || phoneNumber.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else if (otherSubCategory.getVisibility() == View.VISIBLE){
                if (otherSubCat.isEmpty()){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else if (currentCategoryId.equals("-1") || currentSubCategoryId.equals("-1") || currentLocation.equals("-1")){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.fill_all_fields));
                }else {
                    setData(adTitle, adPrice, otherSubCat, adDescription, phoneNumber);
                }
            }else if (currentCategoryId.equals("-1") || currentSubCategoryId.equals("-1") || currentLocation.equals("-1")){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                adPrice = formatter.format(Double.parseDouble(adPrice));
                setData(adTitle, adPrice, "", adDescription, phoneNumber);
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

    private void setAddress(){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mainActivity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(businessAd.getLat()), Double.parseDouble(businessAd.getLng()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            this.address = address;
            latitude = businessAd.getLat();
            longitude = businessAd.getLng();
            Send.electronicsAd.setLat(latitude);
            Send.electronicsAd.setLan(longitude);
            location.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setData(){
        titleEdt.setText(businessAd.getTitle());
        priceEdt.setText(businessAd.getPrice());
        phoneNumberEdt.setText(businessAd.getPhoneNumber());
        descriptionEdt.setText(businessAd.getDescription());
//        for (int i=0; i<businessAd.getPictures().size(); i++){
//            switch (i){
//                case 0:
//                    String newPic1 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image1Path = newPic1.substring(newPic1.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic1).into(image1);
//                    break;
//                case 1:
//                    String newPic2 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image2Path = newPic2.substring(newPic2.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic2).into(image2);
//                    break;
//                case 2:
//                    String newPic3 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image3Path = newPic3.substring(newPic3.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic3).into(image3);
//                    break;
//                case 3:
//                    String newPic4 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image4Path = newPic4.substring(newPic4.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic4).into(image4);
//                    break;
//                case 4:
//                    String newPic5 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image5Path = newPic5.substring(newPic5.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic5).into(image5);
//                    break;
//                case 5:
//                    String newPic6 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image6Path = newPic6.substring(newPic6.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic6).into(image6);
//                    break;
//                case 6:
//                    String newPic7 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image7Path = newPic7.substring(newPic7.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic7).into(image7);
//                    break;
//                case 7:
//                    String newPic8 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image8Path = newPic8.substring(newPic8.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic8).into(image8);
//                    break;
//                case 8:
//                    String newPic9 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image9Path = newPic9.substring(newPic9.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic9).into(image9);
//                    break;
//                case 9:
//                    String newPic10 = businessAd.getPictures().get(i).getImageURL().replace("\\", "/");
//                    image10Path = newPic10.substring(newPic10.indexOf("/images"));
//                    Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic10).into(image10);
//                    break;
//            }
//        }
//        mainPath = businessAd.getMainImage();

        currentCategoryId = businessAd.getCategoryId();
        currentSubCategoryId = businessAd.getSubCategoryId();
        currentLocation = businessAd.getEnLocation()+"-"+businessAd.getArLocation();


    }

    private void setData(String title, String price, String otherSubCat, String description, String phoneNumber){
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
//        if (mainPath.isEmpty() && pictures.size()>0){
//            mainPath = image1Path.substring(image1Path.lastIndexOf("/")+1);;
//        }
        if (pictures.size()==0){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.minimum_pic));
        }else if (mainPath.isEmpty()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.motors_ad), mainActivity.getResources().getString(R.string.select_main_image));
        }else if (latitude.isEmpty() || longitude.isEmpty() || address.isEmpty()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.location_error));
        }else  if (!agreementCheckBok.isChecked()){
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.check_box));
        }else {
            Send.addBusinessAd.setCategoryId(currentCategoryId);
            Send.addBusinessAd.setDescription(description);
            Send.addBusinessAd.setLatitude(latitude);
            Send.addBusinessAd.setLongitude(longitude);
            Send.addBusinessAd.setLocation(currentLocation);
            Send.addBusinessAd.setMainPhoto(mainPath);
            Send.addBusinessAd.setOtherCategoryName("");
            Send.addBusinessAd.setOtherSubCategoryName(otherSubCat);
            Send.addBusinessAd.setPhoneNumber(phoneNumber);
            Send.addBusinessAd.setPrice(price);
            Send.addBusinessAd.setSubCategoryId(currentSubCategoryId);
            Send.addBusinessAd.setTitle(title);
            Send.addBusinessAd.setPictures(pictures);
            updateBusinessAd();
        }
    }

    private void updateBusinessAd(){
        mainActivity.showProgressDialog(getResources().getString(R.string.loading));
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request newRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer "+AppDefs.user.getToken()).build();
            return chain.proceed(newRequest);
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Urls.BusinessAds_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        RequestBody title = createRequestBody(Send.addBusinessAd.getTitle());
        RequestBody description = createRequestBody(Send.addBusinessAd.getDescription());
        RequestBody location = createRequestBody(Send.addBusinessAd.getLocation());
        RequestBody lat = createRequestBody(Send.addBusinessAd.getLatitude());
        RequestBody lan = createRequestBody(Send.addBusinessAd.getLongitude());
        RequestBody mainPhoto = createRequestBody(Send.addBusinessAd.getMainPhoto());
        RequestBody categoryId = createRequestBody(Send.addBusinessAd.getCategoryId());
        RequestBody otherCategoryId = createRequestBody(Send.addBusinessAd.getOtherCategoryName());
        RequestBody price = createRequestBody(Send.addBusinessAd.getPrice());
        RequestBody subCategoryId = createRequestBody(Send.addBusinessAd.getSubCategoryId());
        RequestBody otherSubCategory = createRequestBody(Send.addBusinessAd.getOtherSubCategoryName());
        RequestBody phoneNumber = createRequestBody(Send.addBusinessAd.getPhoneNumber());
        RequestBody adId = createRequestBody(businessAd.getId());
        ArrayList<MultipartBody.Part> images = new ArrayList<>();

        for (int i=0; i<pictures.size(); i++){
            images.add(createMultipartBodyPart("pictures",pictures.get(i)));
        }

        Call<AddAdsResponse> addBusinessAd = retrofit.create(RetrofitUrls.class).updateBusinessAd(title, description, location, lat, lan, price, mainPhoto, categoryId, otherCategoryId, subCategoryId, otherSubCategory, phoneNumber, adId, images);
        addBusinessAd.enqueue(new Callback<AddAdsResponse>() {
            @Override
            public void onResponse(Call<AddAdsResponse> call, Response<AddAdsResponse> response) {
                mainActivity.hideProgressDialog();
                Timber.tag("response").d(response.message());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.ad_updated_successfully), Toast.LENGTH_SHORT).show();
                        startActivity("");
//                        if (packageId.isEmpty()){
//                            startActivity("");
//                        }else {
//                            navController.navigate(PostBusinessAdFragmentDirections.actionPostBusinessAdFragmentToPaymentFragment(packageId, ""));
//                        }
                    }
                }else {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.electronics_ad), mainActivity.getResources().getString(R.string.ad_updated_unsuccessfully));
                }
            }

            @Override
            public void onFailure(Call<AddAdsResponse> call, Throwable t) {
                mainActivity.hideProgressDialog();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.business), mainActivity.getResources().getString(R.string.internet_connection_error));
            }
        });

    }

    private void getCategories(){
        categoriesArTitles.clear();
        categoriesEnTitles.clear();
        categoriesIds.clear();
        categoriesArTitles.add(mainActivity.getResources().getString(R.string.select_business_category));
        categoriesEnTitles.add(mainActivity.getResources().getString(R.string.select_business_category));
        categoriesIds.add("-1");
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest categoriesRequest = new StringRequest(Request.Method.GET, Urls.Categories_URL+"GetByTypeId?id=5", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray categoriesArray = new JSONArray(response);
                for (int i=0; i<categoriesArray.length(); i++){
                    JSONObject categoryObj = categoriesArray.getJSONObject(i);
                    categoriesArTitles.add(categoryObj.getString("ar_Name"));
                    categoriesEnTitles.add(categoryObj.getString("en_Name"));
                    categoriesIds.add(categoryObj.getString("id"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(categorySpinner, categoriesArTitles);
                }else {
                    setSpinner(categorySpinner, categoriesEnTitles);
                }
                currentCategoryId = categoriesIds.get(0);
                for (int i=0; i<categoriesIds.size(); i++){
                    if (businessAd.getCategoryId().equals(categoriesIds.get(i))){
                        categorySpinner.setSelection(i-1);
                    }
                }
                currentCategoryId = businessAd.getCategoryId();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.category), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.category), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(categoriesRequest);
    }

    private void getSubCategories(){
        subCategoriesArTitles.clear();
        subCategoriesEnTitles.clear();
        subCategoriesIds.clear();
        subCategoriesArTitles.add(mainActivity.getResources().getString(R.string.select_sub_business));
        subCategoriesEnTitles.add(mainActivity.getResources().getString(R.string.select_sub_business));
        subCategoriesIds.add("-1");
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest subCategoriesRequest = new StringRequest(Request.Method.GET, Urls.SubCategories_URL+"GetByCategoryId?categoryId="+currentCategoryId,response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray subCategoriesArray = new JSONArray(response);
                for (int i=0; i<subCategoriesArray.length(); i++) {
                    JSONObject subCategoryObj = subCategoriesArray.getJSONObject(i);
                    subCategoriesArTitles.add(subCategoryObj.getString("ar_Name"));
                    subCategoriesEnTitles.add(subCategoryObj.getString("en_Name"));
                    subCategoriesIds.add(subCategoryObj.getString("id"));
                }
                subCategoriesEnTitles.add(mainActivity.getResources().getString(R.string.other));
                categoriesArTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(subCategorySpinner, subCategoriesArTitles);
                }else {
                    setSpinner(subCategorySpinner, subCategoriesEnTitles);
                }
//                for (int i=0; i<subCategoriesIds.size(); i++){
//                    if (businessAd.getSubCategoryId().equals(subCategoriesIds.get(i))){
//                        subCategorySpinner.setSelection(i);
//                    }
//                }

                if (subCategoriesIds.size()>0) {
                    currentSubCategoryId = subCategoriesIds.get(0);
                }
                currentSubCategoryId = businessAd.getSubCategoryId();
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

    private void getLocations(){
        locationsEnTitles.clear();
        locationsArTitles.clear();
        locationsEnTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
        locationsArTitles.add(mainActivity.getResources().getString(R.string.select_your_location));
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
                adLocation.setText(locationsEnTitles.get(0));
                if (AppDefs.language.equals("ar")){
                    setSpinner(locationSpinner, locationsArTitles);
                }else {
                    setSpinner(locationSpinner, locationsEnTitles);
                }
//                for (int i=0; i<locationsEnTitles.size(); i++){
//                    if (businessAd.getEnLocation().equals(locationsEnTitles.get(i))){
//                        locationSpinner.setSelection(i);
//                    }
//                }
                currentLocation = locationsEnTitles.get(0)+"-"+locationsArTitles.get(0);
                currentLocation = businessAd.getEnLocation()+"-"+businessAd.getArLocation();
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

    private void onSpinnerClick(){
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                currentCategoryId = categoriesIds.get(i);
                getSubCategories();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        subCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    currentSubCategoryId = "-1";
                }else {
                    if (subCategoriesEnTitles.size() == 1){
                        otherSubCategory.setVisibility(View.VISIBLE);
                        currentSubCategoryId = "0";
                    }else if (i == subCategoriesEnTitles.size()-1){
                        otherSubCategory.setVisibility(View.VISIBLE);
                        currentSubCategoryId = "0";
                    }else {
                        otherSubCategory.setVisibility(View.GONE);
                        currentSubCategoryId = subCategoriesIds.get(i);
                    }
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
                    spinner3 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i==0){
                    currentLocation = "-1";
                }else {
                    currentLocation = locationsEnTitles.get(i)+"-"+locationsArTitles.get(i);
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

    private void startMapsActivity(){
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivityForResult(intent,REQUEST_CODE_MAPS_ACTIVITY);
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