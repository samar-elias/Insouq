package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers.ViewOnCar;

import static android.graphics.Typeface.BOLD;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Car;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ViewOnCarFragment extends Fragment {

    NavController navController;
    MainActivity mainActivity;
    ImageView backToPrevious;
    Spinner cars, models;

    ImageView mobileNumberImage, carImage;
    ConstraintLayout mobileNumberLayout, abuDhabiBikeLayout, abuDhabiClassicLayout, abuDhabiPrivateLayout, ajmanPrivateLayout, dubaiBikeLayout, dubaiClassicLayout, dubaiPrivateLayout, fujairahPrivateLayout, rasAlKhaimahClassicLayout, rasAlKhaimahPrivateLayout, sharjahPrivateLayout, sharjahClassicLayout, ummAlQuwainPrivateLayout;
    TextView abuDhabiBikePlateCode, abuDhabiBikePlateNumber, abuDhabiClassicPlateCode, abuDhabiClassicPlateNumber, abuDhabiPrivatePlateCode, abuDhabiPrivatePlateNumber, ajmanPrivatePlateCode, ajmanPrivatePlateNumber, dubaiBikePlateCode, dubaiBikePlateNumber, dubaiClassicPlateNumber, dubaiPrivatePlateCode, dubaiPrivatePlateNumber, fujairahPrivatePlateCode, fujairahPrivatePlateNumber, rasAlkhaimahClassicPlateNumber, rasAlkhaimahPrivatePlateCode, rasAlkhaimahPrivatePlateNumber, sharjahPrivatePlateCode, sharjahPrivatePlateNumber, sharjahClassicPlateNumber, ummAlQuwainPrivatePlateCode, ummAlQuwainPrivatePlateNumber;
    TextView mobileNumberCode, mobileNumber;
    NumberAd newAd;

    ArrayList<String> brandArTitles = new ArrayList<>(), brandEnTitles = new ArrayList<>(), modelArTitles = new ArrayList<>(), modelEnTitles = new ArrayList<>(), brandsIds = new ArrayList<>(), modelsIds = new ArrayList<>();
    String brand = "", model = "", currentBrand = "";
    ConstraintLayout mobileNumberLayout2, abuDhabiBikeLayout2, abuDhabiClassicLayout2, abuDhabiPrivateLayout2, ajmanPrivateLayout2, dubaiBikeLayout2, dubaiClassicLayout2, dubaiPrivateLayout2, fujairahPrivateLayout2, rasAlKhaimahClassicLayout2, rasAlKhaimahPrivateLayout2, sharjahPrivateLayout2, sharjahClassicLayout2, ummAlQuwainPrivateLayout2;
    TextView abuDhabiBikePlateCode2, abuDhabiBikePlateNumber2, abuDhabiClassicPlateCode2, abuDhabiClassicPlateNumber2, abuDhabiPrivatePlateCode2, abuDhabiPrivatePlateNumber2, ajmanPrivatePlateCode2, ajmanPrivatePlateNumber2, dubaiBikePlateCode2, dubaiBikePlateNumber2, dubaiClassicPlateNumber2, dubaiPrivatePlateCode2, dubaiPrivatePlateNumber2, fujairahPrivatePlateCode2, fujairahPrivatePlateNumber2, rasAlkhaimahClassicPlateNumber2, rasAlkhaimahPrivatePlateCode2, rasAlkhaimahPrivatePlateNumber2, sharjahPrivatePlateCode2, sharjahPrivatePlateNumber2, sharjahClassicPlateNumber2, ummAlQuwainPrivatePlateCode2, ummAlQuwainPrivatePlateNumber2;
    Car car;

    public ViewOnCarFragment() {
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
        return inflater.inflate(R.layout.fragment_view_on_car, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            newAd = ViewOnCarFragmentArgs.fromBundle(getArguments()).getNumberItem();
        }
        initViews(view);
        onClick();
        getBrands();
        onSpinnerClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View itemView){
        navController = Navigation.findNavController(itemView);
        cars = itemView.findViewById(R.id.cars_spinner);
        models = itemView.findViewById(R.id.models_spinner);
        backToPrevious = itemView.findViewById(R.id.back_arrow);
        carImage = itemView.findViewById(R.id.car);

        abuDhabiBikeLayout = itemView.findViewById(R.id.abu_dhabi_bike_layout);
        abuDhabiClassicLayout = itemView.findViewById(R.id.abu_dhabi_classic_layout);
        abuDhabiPrivateLayout = itemView.findViewById(R.id.abu_dhabi_private_layout);
        ajmanPrivateLayout = itemView.findViewById(R.id.ajman_private_layout);
        dubaiBikeLayout = itemView.findViewById(R.id.dubai_bike_layout);
        dubaiClassicLayout = itemView.findViewById(R.id.dubai_classic_layout);
        dubaiPrivateLayout = itemView.findViewById(R.id.dubai_private_layout);
        fujairahPrivateLayout = itemView.findViewById(R.id.fujairah_private_layout);
        rasAlKhaimahClassicLayout = itemView.findViewById(R.id.ras_alkhaimah_classic_layout);
        rasAlKhaimahPrivateLayout = itemView.findViewById(R.id.ras_alkhaimah_private_layout);
        sharjahPrivateLayout = itemView.findViewById(R.id.sharjah_private_layout);
        sharjahClassicLayout = itemView.findViewById(R.id.sharjah_classic_layout);
        ummAlQuwainPrivateLayout = itemView.findViewById(R.id.umm_alqwain_private_layout);
        mobileNumberLayout = itemView.findViewById(R.id.mobile_number_plate);

        abuDhabiBikePlateCode = itemView.findViewById(R.id.adb_plate_code);
        abuDhabiClassicPlateCode = itemView.findViewById(R.id.adc_plate_code);
        abuDhabiPrivatePlateCode = itemView.findViewById(R.id.adp_plate_code);
        ajmanPrivatePlateCode = itemView.findViewById(R.id.ap_plate_code);
        dubaiBikePlateCode = itemView.findViewById(R.id.db_plate_code);
        dubaiPrivatePlateCode = itemView.findViewById(R.id.dp_plate_code);
        fujairahPrivatePlateCode = itemView.findViewById(R.id.fp_plate_code);
        rasAlkhaimahPrivatePlateCode = itemView.findViewById(R.id.rkp_plate_code);
        sharjahPrivatePlateCode = itemView.findViewById(R.id.sp_plate_code);
        ummAlQuwainPrivatePlateCode = itemView.findViewById(R.id.uqp_plate_code);

        abuDhabiBikePlateNumber = itemView.findViewById(R.id.adb_plate_number);
        abuDhabiClassicPlateNumber = itemView.findViewById(R.id.adc_plate_number);
        abuDhabiPrivatePlateNumber = itemView.findViewById(R.id.adp_plate_number);
        ajmanPrivatePlateNumber = itemView.findViewById(R.id.ap_plate_number);
        dubaiBikePlateNumber = itemView.findViewById(R.id.db_plate_number);
        dubaiClassicPlateNumber = itemView.findViewById(R.id.dc_plate_number);
        dubaiPrivatePlateNumber = itemView.findViewById(R.id.dp_plate_number);
        fujairahPrivatePlateNumber = itemView.findViewById(R.id.fp_plate_number);
        rasAlkhaimahClassicPlateNumber = itemView.findViewById(R.id.rkc_plate_number);
        rasAlkhaimahPrivatePlateNumber = itemView.findViewById(R.id.rkp_plate_number);
        sharjahPrivatePlateNumber = itemView.findViewById(R.id.sp_plate_number);
        sharjahClassicPlateNumber = itemView.findViewById(R.id.sc_plate_number);
        ummAlQuwainPrivatePlateNumber = itemView.findViewById(R.id.uqp_plate_number);

        mobileNumberCode = itemView.findViewById(R.id.mobile_number_code);
        mobileNumber = itemView.findViewById(R.id.mobile_number);
        mobileNumberImage = itemView.findViewById(R.id.mobile_image);

        abuDhabiBikeLayout2 = itemView.findViewById(R.id.abu_dhabi_bike_layout2);
        abuDhabiClassicLayout2 = itemView.findViewById(R.id.abu_dhabi_classic_layout2);
        abuDhabiPrivateLayout2 = itemView.findViewById(R.id.abu_dhabi_private_layout2);
        ajmanPrivateLayout2 = itemView.findViewById(R.id.ajman_private_layout2);
        dubaiBikeLayout2 = itemView.findViewById(R.id.dubai_bike_layout2);
        dubaiClassicLayout2 = itemView.findViewById(R.id.dubai_classic_layout2);
        dubaiPrivateLayout2 = itemView.findViewById(R.id.dubai_private_layout2);
        fujairahPrivateLayout2 = itemView.findViewById(R.id.fujairah_private_layout2);
        rasAlKhaimahClassicLayout2 = itemView.findViewById(R.id.ras_alkhaimah_classic_layout2);
        rasAlKhaimahPrivateLayout2 = itemView.findViewById(R.id.ras_alkhaimah_private_layout2);
        sharjahPrivateLayout2 = itemView.findViewById(R.id.sharjah_private_layout2);
        sharjahClassicLayout2 = itemView.findViewById(R.id.sharjah_classic_layout2);
        ummAlQuwainPrivateLayout2 = itemView.findViewById(R.id.umm_alqwain_private_layout2);

        abuDhabiBikePlateCode2 = itemView.findViewById(R.id.adb_plate_code2);
        abuDhabiClassicPlateCode2 = itemView.findViewById(R.id.adc_plate_code2);
        abuDhabiPrivatePlateCode2 = itemView.findViewById(R.id.adp_plate_code2);
        ajmanPrivatePlateCode2 = itemView.findViewById(R.id.ap_plate_code2);
        dubaiBikePlateCode2 = itemView.findViewById(R.id.db_plate_code2);
        dubaiPrivatePlateCode2 = itemView.findViewById(R.id.dp_plate_code2);
        fujairahPrivatePlateCode2 = itemView.findViewById(R.id.fp_plate_code2);
        rasAlkhaimahPrivatePlateCode2 = itemView.findViewById(R.id.rkp_plate_code2);
        sharjahPrivatePlateCode2 = itemView.findViewById(R.id.sp_plate_code2);
        ummAlQuwainPrivatePlateCode2 = itemView.findViewById(R.id.uqp_plate_code2);

        abuDhabiBikePlateNumber2 = itemView.findViewById(R.id.adb_plate_number2);
        abuDhabiClassicPlateNumber2 = itemView.findViewById(R.id.adc_plate_number2);
        abuDhabiPrivatePlateNumber2 = itemView.findViewById(R.id.adp_plate_number2);
        ajmanPrivatePlateNumber2 = itemView.findViewById(R.id.ap_plate_number2);
        dubaiBikePlateNumber2 = itemView.findViewById(R.id.db_plate_number2);
        dubaiClassicPlateNumber2 = itemView.findViewById(R.id.dc_plate_number2);
        dubaiPrivatePlateNumber2 = itemView.findViewById(R.id.dp_plate_number2);
        fujairahPrivatePlateNumber2 = itemView.findViewById(R.id.fp_plate_number2);
        rasAlkhaimahClassicPlateNumber2 = itemView.findViewById(R.id.rkc_plate_number2);
        rasAlkhaimahPrivatePlateNumber2 = itemView.findViewById(R.id.rkp_plate_number2);
        sharjahPrivatePlateNumber2 = itemView.findViewById(R.id.sp_plate_number2);
        sharjahClassicPlateNumber2 = itemView.findViewById(R.id.sc_plate_number2);
        ummAlQuwainPrivatePlateNumber2 = itemView.findViewById(R.id.uqp_plate_number2);






        setImage();
    }
    
    private void setImage(){
        if (newAd.getCategory().equals("Plate Numbers")){
            mobileNumberLayout.setVisibility(View.INVISIBLE);
            if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Dubai")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.VISIBLE);
                dubaiPrivatePlateCode.setText(newAd.getPlateCode());
                dubaiPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Abu Dhabi")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.VISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivatePlateCode.setText(newAd.getPlateCode());
                abuDhabiPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Sharjah")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.VISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                sharjahPrivatePlateCode.setText(newAd.getPlateCode());
                sharjahPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Classic") && newAd.getEmirate().equals("Abu Dhabi")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.VISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicPlateCode.setText(newAd.getPlateCode());
                abuDhabiClassicPlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Fujairah")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.VISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                fujairahPrivatePlateCode.setText(newAd.getPlateCode());
                fujairahPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Umm al Quwain")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.VISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivatePlateCode.setText(newAd.getPlateCode());
                ummAlQuwainPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Ras al Khaimah")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.VISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlkhaimahPrivatePlateCode.setText(newAd.getPlateCode());
                rasAlkhaimahPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Ajman")){
                abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout.setVisibility(View.VISIBLE);
                dubaiBikeLayout.setVisibility(View.INVISIBLE);
                dubaiClassicLayout.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahClassicLayout.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                ajmanPrivatePlateCode.setText(newAd.getPlateCode());
                ajmanPrivatePlateNumber.setText(newAd.getNumber());
            }
        }


        if (newAd.getCategory().equals("Plate Numbers")){
            mobileNumberLayout.setVisibility(View.INVISIBLE);
            if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Dubai")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.VISIBLE);
                dubaiPrivatePlateCode2.setText(newAd.getPlateCode());
                dubaiPrivatePlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Abu Dhabi")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.VISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivatePlateCode2.setText(newAd.getPlateCode());
                abuDhabiPrivatePlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Sharjah")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.VISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivatePlateCode2.setText(newAd.getPlateCode());
                sharjahPrivatePlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Classic") && newAd.getEmirate().equals("Abu Dhabi")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.VISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicPlateCode2.setText(newAd.getPlateCode());
                abuDhabiClassicPlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Fujairah")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.VISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivatePlateCode2.setText(newAd.getPlateCode());
                fujairahPrivatePlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Umm al Quwain")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.VISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivatePlateCode2.setText(newAd.getPlateCode());
                ummAlQuwainPrivatePlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Ras al Khaimah")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.VISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlkhaimahPrivatePlateCode2.setText(newAd.getPlateCode());
                rasAlkhaimahPrivatePlateNumber2.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Ajman")){
                abuDhabiBikeLayout2.setVisibility(View.INVISIBLE);
                abuDhabiPrivateLayout2.setVisibility(View.INVISIBLE);
                abuDhabiClassicLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivateLayout2.setVisibility(View.VISIBLE);
                dubaiBikeLayout2.setVisibility(View.INVISIBLE);
                dubaiClassicLayout2.setVisibility(View.INVISIBLE);
                fujairahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahPrivateLayout2.setVisibility(View.INVISIBLE);
                rasAlKhaimahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahClassicLayout2.setVisibility(View.INVISIBLE);
                sharjahPrivateLayout2.setVisibility(View.INVISIBLE);
                ummAlQuwainPrivateLayout2.setVisibility(View.INVISIBLE);
                dubaiPrivateLayout2.setVisibility(View.INVISIBLE);
                ajmanPrivatePlateCode2.setText(newAd.getPlateCode());
                ajmanPrivatePlateNumber2.setText(newAd.getNumber());
            }
        }
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
    }

    private void onSpinnerClick(){
        cars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brand = brandsIds.get(i);
                currentBrand = brandEnTitles.get(i)+"-"+brandArTitles.get(i);
                getModels();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        models.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model = modelsIds.get(i);
                getImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getBrands(){
        brandArTitles.clear();
        brandEnTitles.clear();
        brandsIds.clear();
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest brandsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorMaker", response -> {
            try {
                JSONArray brandsArray = new JSONArray(response);
                for (int i=0; i<brandsArray.length(); i++){
                    JSONObject brandObj = brandsArray.getJSONObject(i);
                    brandArTitles.add(brandObj.getString("ar_Text"));
                    brandEnTitles.add(brandObj.getString("en_Text"));
                    brandsIds.add(brandObj.getString("id"));
                }
                brandArTitles.add(mainActivity.getResources().getString(R.string.other));
                brandEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(cars, brandArTitles);
                }else {
                    setSpinner(cars, brandEnTitles);
                }
                brand = brandsIds.get(0);
                currentBrand = brandEnTitles.get(0)+"-"+brandArTitles.get(0);
//                getModels();
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

    private void getModels(){
        modelArTitles.clear();
        modelEnTitles.clear();
        modelsIds.clear();
//        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest modelsRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllMotorModelByMakerId?makerId="+brand, response -> {
            try {
                JSONArray modelsArray = new JSONArray(response);
                for (int i=0; i<modelsArray.length(); i++){
                    JSONObject modelObj = modelsArray.getJSONObject(i);
                    modelArTitles.add(modelObj.getString("ar_Text"));
                    modelEnTitles.add(modelObj.getString("en_Text"));
                    modelsIds.add(modelObj.getString("id"));
                }
                if (AppDefs.language.equals("ar")){
                    setSpinner(models, modelArTitles);
                }else {
                    setSpinner(models, modelEnTitles);
                }
                model = modelsIds.get(0);
//                getTrims();

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

    private void setSpinner(Spinner spinner, ArrayList<String> items){
//        ArrayList<String> items = new ArrayList<>();
//        items.add(name);

        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell_spinner,items);
        sortAdapter.setDropDownViewResource(R.layout.sell_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void getImage(){
        StringRequest imageRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetPictureOfPlateOnCar?makerId="+brand+"&modelId="+model, response -> {
            try {
                JSONObject imageObj = new JSONObject(response);
                car = new Car(imageObj.getString("carImageURL"));
                car.setUrl(car.getUrl().replace("\\", "/"));
                if (!car.getUrl().isEmpty()){
                    Glide.with(mainActivity).load(car.getUrl()).into(carImage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (!Objects.equals(e.getMessage(), "End of input at character 0 of ")){
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.view_in_car), mainActivity.getResources().getString(R.string.error_occured));
                }

            }
        }, error -> {
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.view_in_car), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(imageRequest);
    }
}