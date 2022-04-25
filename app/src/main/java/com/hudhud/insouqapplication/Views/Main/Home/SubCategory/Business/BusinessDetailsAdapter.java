package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business;

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
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.MotorDetailsFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;

import java.util.ArrayList;

public class BusinessDetailsAdapter extends RecyclerView.Adapter<BusinessDetailsAdapter.ViewHolder> {

    BusinessDetailsFragment businessDetailsFragment;
    ArrayList<BusinessAd> businessAds;
    Context context;
    boolean fav = false;

    public BusinessDetailsAdapter(BusinessDetailsFragment businessDetailsFragment, ArrayList<BusinessAd> businessAds) {
        this.businessDetailsFragment = businessDetailsFragment;
        this.businessAds = businessAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.business_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusinessAd businessAd = businessAds.get(position);

        holder.title.setText(businessAd.getTitle());
        holder.description.setText(businessAd.getDescription());
        holder.price.setText(businessAd.getPrice());

        if (AppDefs.language.equals("ar")) {
            holder.location.setText(businessAd.getArLocation());
            holder.location2.setText(businessAd.getArLocation());
        }else {
            holder.location.setText(businessAd.getEnLocation());
            holder.location2.setText(businessAd.getEnLocation());
        }

        businessDetailsFragment.getUserInfo(businessAd.getUserId(), holder.img, holder.name, holder.member);

        holder.viewPager.setVisibility(View.VISIBLE);
        holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        ImageViewPagerAdapter mAdapter = new ImageViewPagerAdapter(businessAd.getPictures(), businessDetailsFragment.mainActivity);
        mAdapter.notifyDataSetChanged();
        holder.viewPager.setOffscreenPageLimit(3);
        holder.viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000, holder.viewPager, mAdapter);

        ArrayList<specificationModel> businessSpecifications = new ArrayList<>();

        if (!businessAd.getCategoryEnName().equals("null")){
            if (AppDefs.language.equals("ar")){
                businessSpecifications.add(new specificationModel(context.getResources().getString(R.string.category), businessAd.getCategoryArName(), R.drawable.category_name));
            }else {
                businessSpecifications.add(new specificationModel(context.getResources().getString(R.string.category), businessAd.getCategoryEnName(), R.drawable.category_name));
            }
        }
        if (!businessAd.getOtherCategoryNAme().equals("null")){
            businessSpecifications.add(new specificationModel(context.getResources().getString(R.string.category), businessAd.getOtherCategoryNAme(), R.drawable.category_name));
        }

        SpecificationAdapter specificationAdapter = new SpecificationAdapter(businessSpecifications);
        holder.specificationsRV.setAdapter(specificationAdapter);
        holder.specificationsRV.setLayoutManager(new LinearLayoutManager(context));

        holder.direction.setOnClickListener(view -> businessDetailsFragment.openGoogleMaps(businessAd.getLat(), businessAd.getLng()));
        holder.call.setOnClickListener(view -> businessDetailsFragment.call(businessAd.getPhoneNumber()));
        holder.sms.setOnClickListener(view -> businessDetailsFragment.sendSMS(businessAd.getPhoneNumber()));
        holder.share.setOnClickListener(view -> businessDetailsFragment.share(businessAd.getId()));
        holder.reportAd.setOnClickListener(view -> businessDetailsFragment.setReportAd(businessAd.getId()));
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
//        if (businessDetailsFragment.getOfferApplied(businessAd.getId())){
//            holder.applyForJob.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob2.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob.setEnabled(false);
//            holder.applyForJob2.setEnabled(false);
//        }

        holder.makeAnOffer.setOnClickListener(view -> businessDetailsFragment.openMakeAnOffer(businessAd.getId(), businessAd.getPrice()));

        if (businessAd.getIsFav().equals("true")){
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
                businessDetailsFragment.addToFavourite(businessAd.getId());
            }else {
                fav = false;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                businessDetailsFragment.removeFromFavourite(businessAd.getId());
            }
        });

        holder.next.setOnClickListener(view ->{
            if (position+1 != businessAds.size()){
                businessDetailsFragment.scrollToPosition(position+1);
            }
        });
        holder.previous.setOnClickListener(view -> {
            if (position!=0){
                businessDetailsFragment.scrollToPosition(position-1);
            }
        });
        holder.chats.setOnClickListener(v -> {
            businessDetailsFragment.checkAds(Integer.valueOf(businessAd.getId()),Integer.valueOf(businessAd.getUserId()),businessAd.getMainImage(),businessAd.getPostedDate(),businessAd.getTitle(),businessAd.getPrice(),"5");

        });
        holder.backToPrevious.setOnClickListener(view -> businessDetailsFragment.navigateBack());    }

    @Override
    public int getItemCount() {
        return businessAds.size();
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
        LinearLayout call, sms,chats;
        ImageView next, previous;
        RecyclerView specificationsRV;
        ViewPager viewPager;
        TabLayout tabLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            tabLayout = itemView.findViewById(R.id.tabDots);
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

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            chats = itemView.findViewById(R.id.chat);
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
        }
    }
}
