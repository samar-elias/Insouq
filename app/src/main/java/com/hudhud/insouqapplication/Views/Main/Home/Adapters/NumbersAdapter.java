package com.hudhud.insouqapplication.Views.Main.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.hudhud.insouqapplication.AppUtils.Responses.NewAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NewNumberAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    ArrayList<NumberAd> newAds;
    HomeFragment homeFragment;
    String categoryName;
    Context context;

    public NumbersAdapter(ArrayList<NumberAd> newAds, HomeFragment homeFragment) {
        this.newAds = newAds;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productsView = inflater.inflate(R.layout.number_item_product, parent, false);
        return new ViewHolder(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberAd newAd = newAds.get(position);

        holder.loc.setText(newAd.getEnLocation());
        if (!newAd.getPrice().isEmpty()){
            holder.price.setText("AED "+newAd.getPrice());
        }

        if (newAd.getCategory().equals("Mobile Numbers")){
            holder.mobileNumberLayout.setVisibility(View.VISIBLE);
            holder.title.setText(newAd.getOperator()+"-"+newAd.getEnMobilePlan());
            switch (newAd.getOperator()){
                case "Etisalat":
                    holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                    holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                    holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                    holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                    holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                    holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                    holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.mobileNumberImage.setVisibility(View.VISIBLE);
                    holder.mobileNumberCode.setText(newAd.getCode());
                    holder.mobileNumber.setText(newAd.getNumber());
                    Glide.with(context).load(R.drawable.etisalat).into(holder.mobileNumberImage);
                    break;
                case "DU":
                    holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                    holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                    holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                    holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                    holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                    holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                    holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                    holder.mobileNumberImage.setVisibility(View.VISIBLE);
                    holder.mobileNumberCode.setText(newAd.getCode());
                    holder.mobileNumber.setText(newAd.getNumber());
                    Glide.with(context).load(R.drawable.du).into(holder.mobileNumberImage);
                    break;
            }
        }else if (newAd.getCategory().equals("Plate Numbers")){
            holder.title.setText(newAd.getPlateType());
            holder.mobileNumberLayout.setVisibility(View.INVISIBLE);
            if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Dubai")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.VISIBLE);
                holder.dubaiPrivatePlateCode.setText(newAd.getPlateCode());
                holder.dubaiPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Abu Dhabi")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.VISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivatePlateCode.setText(newAd.getPlateCode());
                holder.abuDhabiPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Sharjah")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.VISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivatePlateCode.setText(newAd.getPlateCode());
                holder.sharjahPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Classic") && newAd.getEmirate().equals("Abu Dhabi")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.VISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicPlateCode.setText(newAd.getPlateCode());
                holder.abuDhabiClassicPlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Fujairah")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.VISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivatePlateCode.setText(newAd.getPlateCode());
                holder.fujairahPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Umm al Quwain")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.VISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivatePlateCode.setText(newAd.getPlateCode());
                holder.ummAlQuwainPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Ras al Khaimah")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.VISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlkhaimahPrivatePlateCode.setText(newAd.getPlateCode());
                holder.rasAlkhaimahPrivatePlateNumber.setText(newAd.getNumber());
            }else if (newAd.getPlateType().equals("Private Car") && newAd.getEmirate().equals("Ajman")){
                holder.abuDhabiBikeLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.abuDhabiClassicLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivateLayout.setVisibility(View.VISIBLE);
                holder.dubaiBikeLayout.setVisibility(View.INVISIBLE);
                holder.dubaiClassicLayout.setVisibility(View.INVISIBLE);
                holder.fujairahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.rasAlKhaimahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahClassicLayout.setVisibility(View.INVISIBLE);
                holder.sharjahPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ummAlQuwainPrivateLayout.setVisibility(View.INVISIBLE);
                holder.dubaiPrivateLayout.setVisibility(View.INVISIBLE);
                holder.ajmanPrivatePlateCode.setText(newAd.getPlateCode());
                holder.ajmanPrivatePlateNumber.setText(newAd.getNumber());
            }
        }

        holder.item.setOnClickListener(view -> homeFragment.navigateToNumberDetails(position));
    }

    @Override
    public int getItemCount() {
        return newAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mobileNumberImage;
        ConstraintLayout mobileNumberLayout, abuDhabiBikeLayout, abuDhabiClassicLayout, abuDhabiPrivateLayout, ajmanPrivateLayout, dubaiBikeLayout, dubaiClassicLayout, dubaiPrivateLayout, fujairahPrivateLayout, rasAlKhaimahClassicLayout, rasAlKhaimahPrivateLayout, sharjahPrivateLayout, sharjahClassicLayout, ummAlQuwainPrivateLayout;
        TextView abuDhabiBikePlateCode, abuDhabiBikePlateNumber, abuDhabiClassicPlateCode, abuDhabiClassicPlateNumber, abuDhabiPrivatePlateCode, abuDhabiPrivatePlateNumber, ajmanPrivatePlateCode, ajmanPrivatePlateNumber, dubaiBikePlateCode, dubaiBikePlateNumber, dubaiClassicPlateNumber, dubaiPrivatePlateCode, dubaiPrivatePlateNumber, fujairahPrivatePlateCode, fujairahPrivatePlateNumber, rasAlkhaimahClassicPlateNumber, rasAlkhaimahPrivatePlateCode, rasAlkhaimahPrivatePlateNumber, sharjahPrivatePlateCode, sharjahPrivatePlateNumber, sharjahClassicPlateNumber, ummAlQuwainPrivatePlateCode, ummAlQuwainPrivatePlateNumber;
        TextView title, price, mobileNumberCode, mobileNumber, loc;
        MaterialCardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            loc = itemView.findViewById(R.id.kilo_year);
            item = itemView.findViewById(R.id.item);
        }
    }
}
