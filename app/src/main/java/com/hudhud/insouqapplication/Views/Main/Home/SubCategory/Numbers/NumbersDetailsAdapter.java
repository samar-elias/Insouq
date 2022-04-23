package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NumberAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics.ElectronicsDetailsFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;

import java.util.ArrayList;

public class NumbersDetailsAdapter extends RecyclerView.Adapter<NumbersDetailsAdapter.ViewHolder> {

    NumberDetailsFragment numberDetailsFragment;
    ArrayList<NumberAd> numberAds;
    Context context;
    boolean fav = false;

    public NumbersDetailsAdapter(NumberDetailsFragment numberDetailsFragment, ArrayList<NumberAd> numberAds) {
        this.numberDetailsFragment = numberDetailsFragment;
        this.numberAds = numberAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.number_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberAd newAd = numberAds.get(position);

        if (newAd.getCategory().equals("Mobile Numbers")){
            holder.viewInCar.setVisibility(View.GONE);
        }else {
            holder.viewInCar.setVisibility(View.VISIBLE);
        }

        if (newAd.getCategory().equals("Mobile Numbers")){
            holder.mobileNumberLayout.setVisibility(View.VISIBLE);
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

        holder.title.setText(newAd.getTitle());
        holder.description.setText(newAd.getDescription());
        holder.price.setText(newAd.getPrice());

        if (AppDefs.language.equals("ar")) {
            holder.location.setText(newAd.getArLocation());
            holder.location2.setText(newAd.getArLocation());
        }else {
            holder.location.setText(newAd.getEnLocation());
            holder.location2.setText(newAd.getEnLocation());
        }

        numberDetailsFragment.getUserInfo(newAd.getUserId(), holder.img, holder.name, holder.member);

        ArrayList<specificationModel> numbersSpecifications = new ArrayList<>();

        if (!newAd.getEmirate().equals("null")){
            if (AppDefs.language.equals("ar")){
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.emirate), newAd.getArEmirate(), R.drawable.emirate_img));
            }else {
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.emirate), newAd.getEmirate(), R.drawable.emirate_img));
            }
        }
        if (!newAd.getPlateType().equals("null")){
            if (AppDefs.language.equals("ar")){
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.plate_type), newAd.getArPlateType(), R.drawable.plate_type_img));
            }else {
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.plate_type), newAd.getPlateType(), R.drawable.plate_type_img));
            }
        }
        if (!newAd.getOperator().equals("null")){
            if (AppDefs.language.equals("ar")){
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.operator), newAd.getArOperator(), R.drawable.operator_img));
            }else {
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.operator), newAd.getOperator(), R.drawable.operator_img));
            }
        }
        if (!newAd.getEnMobilePlan().equals("null")){
            if (AppDefs.language.equals("ar")){
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.mobile_plan), newAd.getArMobilePlan(), R.drawable.number_plan_img));
            }else {
                numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.mobile_plan), newAd.getEnMobilePlan(), R.drawable.number_plan_img));
            }
        }
        if (!newAd.getPlateCode().equals("null")){
            numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.plate_code), newAd.getPlateCode(), R.drawable.plate_code_img));
        }
        if (!newAd.getNumber().equals("null")){
            numbersSpecifications.add(new specificationModel(context.getResources().getString(R.string.number), newAd.getNumber(), R.drawable.mobile_number_img));
        }

        SpecificationAdapter specificationAdapter = new SpecificationAdapter(numbersSpecifications);
        holder.specificationsRV.setAdapter(specificationAdapter);
        holder.specificationsRV.setLayoutManager(new LinearLayoutManager(context));

        holder.direction.setOnClickListener(view -> numberDetailsFragment.openGoogleMaps(newAd.getLat(), newAd.getLng()));
        holder.call.setOnClickListener(view -> numberDetailsFragment.call(newAd.getPhoneNumber()));
        holder.sms.setOnClickListener(view -> numberDetailsFragment.sendSMS(newAd.getPhoneNumber()));
        holder.share.setOnClickListener(view -> numberDetailsFragment.share(newAd.getId()));
        holder.reportAd.setOnClickListener(view -> numberDetailsFragment.setReportAd(newAd.getId()));
        holder.descriptionLayout.setOnClickListener(view -> {
            if (holder.showDescription){
                holder.showDescription = false;
                holder.description.setVisibility(View.GONE);
                holder.descriptionLine.setVisibility(View.GONE);
                holder.descriptionArrow.setScaleY(1);
            }else {
                holder.showDescription = true;
                holder.description.setVisibility(View.VISIBLE);
                holder.descriptionLine.setVisibility(View.VISIBLE);
                holder.descriptionArrow.setScaleY(-1);
            }
        });

        holder.locationLayout.setOnClickListener(view -> {
            if (holder.showLocation){
                holder.locationImage.setVisibility(View.GONE);
                holder.directionsLayout.setVisibility(View.GONE);
                holder.showLocation = false;
                holder.locationArrow.setScaleY(1);
            }else {
                holder.locationImage.setVisibility(View.VISIBLE);
                holder.directionsLayout.setVisibility(View.VISIBLE);
                holder.showLocation = true;
                holder.locationArrow.setScaleY(-1);
            }
        });
//        if (numberDetailsFragment.getOfferApplied(newAd.getId())){
//            holder.applyForJob.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob2.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob.setEnabled(false);
//            holder.applyForJob2.setEnabled(false);
//        }

        holder.makeAnOffer.setOnClickListener(view -> numberDetailsFragment.openMakeAnOffer(String.valueOf(numberDetailsFragment.getId()), newAd.getPrice()));

        if (newAd.getIsFav().equals("true")){
            fav = true;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_red_24).into(holder.favourite);
        }else {
            fav = false;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_border_24).into(holder.favourite);
        }
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                fav = true;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                numberDetailsFragment.addToFavourite(newAd.getId());
            }else {
                fav = false;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                numberDetailsFragment.removeFromFavourite(newAd.getId());
            }
        });

        holder.next.setOnClickListener(view ->{
            if (position+1 != numberAds.size()){
                numberDetailsFragment.scrollToPosition(position+1);
            }
        });
        holder.previous.setOnClickListener(view -> {
            if (position!=0){
                numberDetailsFragment.scrollToPosition(position-1);
            }
        });

        holder.viewInCar.setOnClickListener(view -> numberDetailsFragment.navigateToViewInCar(newAd));

        holder.backToPrevious.setOnClickListener(view -> numberDetailsFragment.navigateBack());
    }

    @Override
    public int getItemCount() {
        return numberAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout specificationLayout, descriptionLayout, locationLayout, directionsLayout, makeAnOffer, makeAnOffer2, contactLayout;
        ImageView backToPrevious, descriptionArrow, locationArrow, locationImage, favourite, direction, share, img, makeOfferImg;
        TextView description, reportAd, descriptionLine, applyForJob2, applyForJob, title, price, location, makeOfferTxt,
                location2, name, member;
        boolean showDescription, showLocation, showSpecification;
        ImageView showSpecificationArrow, showDescriptionArrow, showLocationArrow, directions;
        TextView contactTitle;
        RecyclerView similarBusinessRV;
        LinearLayout call, sms;
        ImageView next, previous;
        RecyclerView specificationsRV;
        ImageView viewInCar;
        ImageView mobileNumberImage;
        ConstraintLayout mobileNumberLayout, abuDhabiBikeLayout, abuDhabiClassicLayout, abuDhabiPrivateLayout, ajmanPrivateLayout, dubaiBikeLayout, dubaiClassicLayout, dubaiPrivateLayout, fujairahPrivateLayout, rasAlKhaimahClassicLayout, rasAlKhaimahPrivateLayout, sharjahPrivateLayout, sharjahClassicLayout, ummAlQuwainPrivateLayout;
        TextView abuDhabiBikePlateCode, abuDhabiBikePlateNumber, abuDhabiClassicPlateCode, abuDhabiClassicPlateNumber, abuDhabiPrivatePlateCode, abuDhabiPrivatePlateNumber, ajmanPrivatePlateCode, ajmanPrivatePlateNumber, dubaiBikePlateCode, dubaiBikePlateNumber, dubaiClassicPlateNumber, dubaiPrivatePlateCode, dubaiPrivatePlateNumber, fujairahPrivatePlateCode, fujairahPrivatePlateNumber, rasAlkhaimahClassicPlateNumber, rasAlkhaimahPrivatePlateCode, rasAlkhaimahPrivatePlateNumber, sharjahPrivatePlateCode, sharjahPrivatePlateNumber, sharjahClassicPlateNumber, ummAlQuwainPrivatePlateCode, ummAlQuwainPrivatePlateNumber;
        TextView mobileNumberCode, mobileNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            makeOfferImg = itemView.findViewById(R.id.make_offer_img);
            makeOfferTxt = itemView.findViewById(R.id.make_offer_txt);
            backToPrevious = itemView.findViewById(R.id.back_arrow);
            favourite = itemView.findViewById(R.id.favourite);
            reportAd = itemView.findViewById(R.id.report);
            img = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.profile_name);
            member = itemView.findViewById(R.id.member_since);
            direction = itemView.findViewById(R.id.directions);
            call = itemView.findViewById(R.id.call);
            sms = itemView.findViewById(R.id.sms);
            share = itemView.findViewById(R.id.share);

            viewInCar = itemView.findViewById(R.id.view_in_car);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);

            contactTitle = itemView.findViewById(R.id.contact_title);
            contactLayout = itemView.findViewById(R.id.contact_profile);

            //specifications
            specificationsRV = itemView.findViewById(R.id.specifications_RV);
            specificationLayout = itemView.findViewById(R.id.specification_layout);
            showSpecificationArrow = itemView.findViewById(R.id.show_specifications_arrow);
            showSpecification = false;
            specificationsRV.setVisibility(View.GONE);
            if (showSpecification){
                specificationsRV.setVisibility(View.GONE);
                showSpecification = false;
                showSpecificationArrow.setScaleY(1);
            }else {
                specificationsRV.setVisibility(View.VISIBLE);
                showSpecification = true;
                showSpecificationArrow.setScaleY(-1);
            }

            //description
            descriptionLayout = itemView.findViewById(R.id.description_layout);
            descriptionArrow = itemView.findViewById(R.id.show_description_arrow);
            description = itemView.findViewById(R.id.description);
            descriptionLine = itemView.findViewById(R.id.line2);
            showDescription = false;
            descriptionLine.setVisibility(View.GONE);
            description.setVisibility(View.GONE);

            //location
            //location
            locationLayout = itemView.findViewById(R.id.location_layout);
            directionsLayout = itemView.findViewById(R.id.directions_layout);
            showLocation = false;
            locationArrow = itemView.findViewById(R.id.show_location_arrow);
            locationImage = itemView.findViewById(R.id.location_image);
            locationImage.setVisibility(View.GONE);
            directionsLayout.setVisibility(View.GONE);
            location = itemView.findViewById(R.id.location);
            location2 = itemView.findViewById(R.id.location2);
            locationLayout = itemView.findViewById(R.id.location_layout);
            directionsLayout = itemView.findViewById(R.id.directions_layout);
            showLocation = false;
            locationImage = itemView.findViewById(R.id.location_image);
            locationImage.setVisibility(View.GONE);
            directionsLayout.setVisibility(View.GONE);
            similarBusinessRV = itemView.findViewById(R.id.similar_business_RV);

            //make offer
            makeAnOffer = itemView.findViewById(R.id.make_an_offer);

            next = itemView.findViewById(R.id.next);
            previous = itemView.findViewById(R.id.previous);

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
        }
    }
}
