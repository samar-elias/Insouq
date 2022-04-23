package com.hudhud.insouqapplication.Views.Main.Profile.MyAds;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.util.ArrayList;

public class EditAdFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton submit, cancel;
    Spinner specsSpinner, yearSpinner, colorSpinner, doorsSpinner, warrantySpinner, transmissionSpinner, bodySpinner, fuelSpinner, cylinderSpinner
            , powerSpinner, carConditionSpinner, mechanicalConditionSpinner, trimSpinner, locationSpinner;
    private static final int REQUEST_CODE = 101;
    int count = 1;
    ImageView image1, image2, image3, image4, image5, image7, image6, image8, image9, image10, uploadImage, mainImage;
    Uri image1Path, image2Path, image3Path, image4Path, image5Path, image7Path, image6Path, image8Path, image9Path, image10Path, mainPath;


    public EditAdFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        setSpinner(specsSpinner);
        setSpinner(yearSpinner);
        setSpinner(colorSpinner);
        setSpinner(doorsSpinner);
        setSpinner(warrantySpinner);
        setSpinner(transmissionSpinner);
        setSpinner(bodySpinner);
        setSpinner(fuelSpinner);
        setSpinner(cylinderSpinner);
        setSpinner(carConditionSpinner);
        setSpinner(powerSpinner);
        setSpinner(mechanicalConditionSpinner);
        setSpinner2(trimSpinner);
        setSpinner2(locationSpinner);
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
        submit = view.findViewById(R.id.submit);
        cancel = view.findViewById(R.id.cancel);
        specsSpinner = view.findViewById(R.id.specs_spinner);
        yearSpinner = view.findViewById(R.id.year_spinner);
        colorSpinner = view.findViewById(R.id.color_spinner);
        doorsSpinner = view.findViewById(R.id.doors_spinner);
        warrantySpinner = view.findViewById(R.id.warranty_spinner);
        transmissionSpinner = view.findViewById(R.id.transmission_spinner);
        bodySpinner = view.findViewById(R.id.body_spinner);
        fuelSpinner = view.findViewById(R.id.fuel_spinner);
        cylinderSpinner = view.findViewById(R.id.cylinders_spinner);
        powerSpinner = view.findViewById(R.id.power_spinner);
        carConditionSpinner = view.findViewById(R.id.car_condition_spinner);
        mechanicalConditionSpinner = view.findViewById(R.id.mechanical_condition_spinner);
        trimSpinner = view.findViewById(R.id.trim_spinner);
        locationSpinner = view.findViewById(R.id.brand_spinner);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        image4 = view.findViewById(R.id.image4);
        image5 = view.findViewById(R.id.image5);
        image6 = view.findViewById(R.id.image6);
        image7 = view.findViewById(R.id.image7);
        image8 = view.findViewById(R.id.image8);
        image9 = view.findViewById(R.id.image9);
        image10 = view.findViewById(R.id.main_image);
        uploadImage = view.findViewById(R.id.upload_pictures);
        mainImage = view.findViewById(R.id.selected_image);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        submit.setOnClickListener(view -> {
            confirmationEditAdPopUp();
            navController.popBackStack();
        });
        cancel.setOnClickListener(view -> navController.popBackStack());
        uploadImage.setOnClickListener(view -> pickImage());
        image1.setOnClickListener(view -> {
            mainPath = image1Path;
            mainImage.setImageURI(image1Path);
        });
        image2.setOnClickListener(view ->{
            mainPath = image2Path;
            mainImage.setImageURI(image2Path);
        });
        image3.setOnClickListener(view -> {
            mainPath = image3Path;
            mainImage.setImageURI(image3Path);
        });
        image4.setOnClickListener(view -> {
            mainPath = image4Path;
            mainImage.setImageURI(image4Path);
        });
        image5.setOnClickListener(view -> {
            mainPath = image5Path;
            mainImage.setImageURI(image5Path);
        });
        image6.setOnClickListener(view -> {
            mainPath = image6Path;
            mainImage.setImageURI(image6Path);
        });
        image7.setOnClickListener(view -> {
            mainPath = image7Path;
            mainImage.setImageURI(image7Path);
        });
        image8.setOnClickListener(view -> {
            mainPath = image8Path;
            mainImage.setImageURI(image8Path);
        });
        image9.setOnClickListener(view -> {
            mainPath = image9Path;
            mainImage.setImageURI(image9Path);
        });
        image10.setOnClickListener(view -> {
            mainPath = image10Path;
            mainImage.setImageURI(image10Path);
        });

        mainImage.setOnClickListener(view -> {
            if (mainPath.equals(image1Path)) {
                image1.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image2Path)){
                image2.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image3Path)){
                image3.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image4Path)){
                image4.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image5Path)){
                image5.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image6Path)){
                image6.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image7Path)){
                image7.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image8Path)){
                image8.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image9Path)){
                image9.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }else if (mainPath.equals(image10Path)){
                image10.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
            }
            mainImage.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.gray_image));
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    private void setSpinner(Spinner spinner){
        ArrayList<String> items = new ArrayList<>();
        items.add("Option 1");
        items.add("Option 2");
        items.add("Option 3");
        items.add("Option 4");

        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell2_spinner,items);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void setSpinner2(Spinner spinner){
        ArrayList<String> items = new ArrayList<>();
        items.add("Option 1");
        items.add("Option 2");
        items.add("Option 3");
        items.add("Option 4");

        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell_spinner,items);
        sortAdapter.setDropDownViewResource(R.layout.sell_spinner);
        spinner.setAdapter(sortAdapter);
    }

    public void confirmationEditAdPopUp(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_ad_confirmation_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton done = myAdsOptionsAlertView.findViewById(R.id.continue_btn);
        done.setOnClickListener(view -> {
            myAdsOptionsAlertBuilder.dismiss();
        });
    }

    private void pickImage() {
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
                switch (count){
                    case 1:
                        image1.setImageBitmap(r.getBitmap());
                        image1Path = r.getUri();
                        count +=1;
                        break;
                    case 2:
                        image2.setImageBitmap(r.getBitmap());
                        image2Path = r.getUri();
                        count +=1;
                        break;
                    case 3:
                        image3.setImageBitmap(r.getBitmap());
                        image3Path = r.getUri();
                        count +=1;
                        break;
                    case 4:
                        image4.setImageBitmap(r.getBitmap());
                        image4Path = r.getUri();
                        count +=1;
                        break;
                    case 5:
                        image5.setImageBitmap(r.getBitmap());
                        image5Path = r.getUri();
                        count +=1;
                        break;
                    case 6:
                        image6.setImageBitmap(r.getBitmap());
                        image6Path = r.getUri();
                        count +=1;
                        break;
                    case 7:
                        image7.setImageBitmap(r.getBitmap());
                        image7Path = r.getUri();
                        count +=1;
                        break;
                    case 8:
                        image8.setImageBitmap(r.getBitmap());
                        image8Path = r.getUri();
                        count +=1;
                        break;
                    case 9:
                        image9.setImageBitmap(r.getBitmap());
                        image9Path = r.getUri();
                        count +=1;
                        break;
                    case 10:
                        image10.setImageBitmap(r.getBitmap());
                        image10Path = r.getUri();
                        count =1;
                        break;
                }
            }).show(getActivity());
        }
    }
}