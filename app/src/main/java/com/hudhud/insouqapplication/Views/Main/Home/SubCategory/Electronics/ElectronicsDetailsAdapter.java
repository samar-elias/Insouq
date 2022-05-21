package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics;

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
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business.BusinessDetailsFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;

import java.util.ArrayList;

public class ElectronicsDetailsAdapter extends RecyclerView.Adapter<ElectronicsDetailsAdapter.ViewHolder> {

    ElectronicsDetailsFragment electronicsDetailsFragment;
    ArrayList<ElectronicAd> electronicAds;
    Context context;
    boolean fav = false;

    public ElectronicsDetailsAdapter(ElectronicsDetailsFragment electronicsDetailsFragment, ArrayList<ElectronicAd> electronicAds) {
        this.electronicsDetailsFragment = electronicsDetailsFragment;
        this.electronicAds = electronicAds;
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
        ElectronicAd electronicAd = electronicAds.get(position);

        holder.title.setText(electronicAd.getTitle());
        holder.description.setText(electronicAd.getDescription());
        holder.price.setText(electronicAd.getPrice());
        holder.postDate.setText(context.getResources().getString(R.string.posted_on)+" "+electronicAd.getPostedDate());

        if (electronicAd.getAgentId().equals("null")){
            holder.chat.setEnabled(false);
            holder.chat.setAlpha(0.3F);
        }else {
            holder.chat.setEnabled(true);
            holder.chat.setAlpha(1);
        }

        if (AppDefs.language.equals("ar")) {
            holder.location.setText(electronicAd.getArLocation());
            holder.location2.setText(electronicAd.getArLocation());
        }else {
            holder.location.setText(electronicAd.getEnLocation());
            holder.location2.setText(electronicAd.getEnLocation());
        }

        electronicsDetailsFragment.getUserInfo(electronicAd.getUserId(), holder.img, holder.name, holder.member);

        holder.viewPager.setVisibility(View.VISIBLE);
        holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        ElectronicsImageViewPagerAdapter mAdapter = new ElectronicsImageViewPagerAdapter(electronicAd.getPictures(), electronicsDetailsFragment.mainActivity, electronicsDetailsFragment, true);
        mAdapter.notifyDataSetChanged();
        holder.viewPager.setOffscreenPageLimit(3);
        holder.viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000, holder.viewPager, mAdapter);

        ArrayList<specificationModel> electronicsSpecifications = new ArrayList<>();

        if (!electronicAd.getEnBrand().equals("null")){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.brand), electronicAd.getArBrand(), R.drawable.maker_img));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.brand), electronicAd.getEnBrand(), R.drawable.maker_img));
            }
        }

        if (!electronicAd.getSubCatEnName().equals("null")){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.model), electronicAd.getSubCatArName(), R.drawable.sub_category_1));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.model), electronicAd.getSubCatEnName(), R.drawable.sub_category_1));
            }
        }
        if (!electronicAd.getOtherSubCat().equals("null")){
            electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.model), electronicAd.getOtherSubCat(), R.drawable.sub_category_1));
        }

        if (!electronicAd.getEnAge().equals("null")){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.age), electronicAd.getArAge(), R.drawable.age_list));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.age), electronicAd.getEnAge(), R.drawable.age_list));
            }
        }
        if (!electronicAd.getEnUsage().equals("null")){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.usage), electronicAd.getArUsage(), R.drawable.usage_1));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.usage), electronicAd.getEnUsage(), R.drawable.usage_1));
            }
        }

        if (!electronicAd.getEnColor().equals("null")){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.color), electronicAd.getArColor(), R.drawable.color));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.color), electronicAd.getEnColor(), R.drawable.color));
            }
        }

        if (!electronicAd.getEnCondition().equals("null")){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.condition), electronicAd.getArCondition(), R.drawable.condition_2));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.condition), electronicAd.getEnCondition(), R.drawable.condition_2));
            }
        }

        if (!electronicAd.getWarranty().equals("null")){
            switch (electronicAd.getWarranty()) {
                case "0":
                    electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.warranty), context.getResources().getString(R.string.no), R.drawable.warranty));
                    break;
                case "1":
                    electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.warranty), context.getResources().getString(R.string.yes), R.drawable.warranty));
                    break;
                case "2":
                    electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.warranty), context.getResources().getString(R.string.not_apply), R.drawable.warranty));
                    break;
            }
        }


        if (!electronicAd.getEnStorage().equals("null")  && !electronicAd.getEnStorage().isEmpty()){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.storage), electronicAd.getArStorage(), R.drawable.storage));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.storage), electronicAd.getEnStorage(), R.drawable.storage));
            }
        }

        if (!electronicAd.getEnRam().equals("null") && !electronicAd.getEnRam().isEmpty()){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.ram), electronicAd.getArStorage(), R.drawable.ram));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.ram), electronicAd.getEnStorage(), R.drawable.ram));
            }
        }

        if (!electronicAd.getEnVersion().equals("null") && !electronicAd.getEnVersion().isEmpty()){
            if (AppDefs.language.equals("ar")){
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.version), electronicAd.getArStorage(), R.drawable.version));
            }else {
                electronicsSpecifications.add(new specificationModel(context.getResources().getString(R.string.version), electronicAd.getEnStorage(), R.drawable.version));
            }
        }


        SpecificationAdapter specificationAdapter = new SpecificationAdapter(electronicsSpecifications);
        holder.specificationsRV.setAdapter(specificationAdapter);
        holder.specificationsRV.setLayoutManager(new LinearLayoutManager(context));

        holder.direction.setOnClickListener(view -> electronicsDetailsFragment.openGoogleMaps(electronicAd.getLat(), electronicAd.getLng()));
        holder.call.setOnClickListener(view -> electronicsDetailsFragment.call(electronicAd.getPhoneNumber()));
        holder.sms.setOnClickListener(view -> electronicsDetailsFragment.sendSMS(electronicAd.getPhoneNumber()));
        holder.share.setOnClickListener(view -> electronicsDetailsFragment.share(electronicAd.getId()));
        holder.reportAd.setOnClickListener(view -> electronicsDetailsFragment.setReportAd(electronicAd.getId()));
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
//        if (electronicsDetailsFragment.getOfferApplied(electronicAd.getId())){
//            holder.applyForJob.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob2.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob.setEnabled(false);
//            holder.applyForJob2.setEnabled(false);
//        }

        holder.makeAnOffer.setOnClickListener(view -> electronicsDetailsFragment.openMakeAnOffer(String.valueOf(electronicsDetailsFragment.getId()), electronicAd.getPrice()));

        if (electronicAd.getIsFav().equals("true")){
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
                electronicsDetailsFragment.addToFavourite(electronicAd.getId());
            }else {
                fav = false;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                electronicsDetailsFragment.removeFromFavourite(electronicAd.getId());
            }
        });

        holder.next.setOnClickListener(view ->{
            if (position+1 != electronicAds.size()){
                electronicsDetailsFragment.scrollToPosition(position+1);
            }
        });
        holder.previous.setOnClickListener(view -> {
            if (position!=0){
                electronicsDetailsFragment.scrollToPosition(position-1);
            }
        });

        if (AppDefs.language.equals("ar")){
            holder.next.setScaleX(-1);
            holder.previous.setScaleX(-1);
        }else {
            holder.next.setScaleX(1);
            holder.previous.setScaleX(1);
        }

        holder.backToPrevious.setOnClickListener(view -> electronicsDetailsFragment.navigateBack());    }

    @Override
    public int getItemCount() {
        return electronicAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout specificationLayout, descriptionLayout, locationLayout, directionsLayout, makeAnOffer, makeAnOffer2, contactLayout;
        ImageView backToPrevious, descriptionArrow, locationArrow, locationImage, favourite, direction, share, img, makeOfferImg;
        TextView description, reportAd, descriptionLine, postDate, applyForJob2, applyForJob, title, price, location, makeOfferTxt,
                location2, name, member;
        boolean showDescription, showLocation, showSpecification;
        ImageView showSpecificationArrow, showDescriptionArrow, showLocationArrow, directions;
        TextView contactTitle;
        RecyclerView similarBusinessRV;
        LinearLayout call, sms, chat;
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
            chat = itemView.findViewById(R.id.chat);
            share = itemView.findViewById(R.id.share);
            postDate = itemView.findViewById(R.id.post_date);

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
        }
    }
}
