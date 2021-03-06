package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class ServicesGridAdapter extends RecyclerView.Adapter<ServicesGridAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<ServiceAd> serviceAds;
    int image = R.drawable.others_services;

    public ServicesGridAdapter(SubCategoryFragment subCategoryFragment, ArrayList<ServiceAd> serviceAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.serviceAds = serviceAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View servicesView = inflater.inflate(R.layout.services_wanted_item_layout, parent, false);
        return new ViewHolder(servicesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceAd serviceAd = serviceAds.get(position);

        holder.type.setText(serviceAd.getTitle());
        holder.date.setText(serviceAd.getPostedDate());

        if (AppDefs.language.equals("ar")){
            holder.subCat.setText(serviceAd.getCategoryArName());
            holder.location.setText(serviceAd.getArLocation());
        }else {
            holder.subCat.setText(serviceAd.getCategoryEnName());
            holder.location.setText(serviceAd.getEnLocation());
        }

        switch (serviceAd.getCategoryId()){
            case "21":
                image = R.drawable.domestic;
                break;
            case "24":
                image = R.drawable.others_services;
                break;
            case "25":
                image = R.drawable.movers;
                break;
            case "26":
                image = R.drawable.web_computers;
                break;
            case "27":
                image = R.drawable.corporate;
                break;
            case "28":
                image = R.drawable.home_maintenance;
                break;
            case "29":
                image = R.drawable.events;
                break;
            case "30":
                image = R.drawable.tutors;
                break;
            case "31":
                image = R.drawable.others_services;
                break;
            case "32":
                image = R.drawable.healthservices;
                break;
        }

        Glide.with(context).load(image).into(holder.subIcon);

        if (serviceAd.getIsFav().equals("true")){
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
            fav = true;
        }else {
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
            fav = false;
        }

        if (serviceAd.getOtherServiceType().isEmpty()|| serviceAd.getOtherServiceType().equals("null")){
            if (AppDefs.language.equals("ar")){
                holder.subType.setText(serviceAd.getServiceTypeArName());
            }else {
                holder.subType.setText(serviceAd.getServiceTypeEnName());
            }
        }else {
            holder.subType.setText(serviceAd.getOtherServiceType());
        }

        holder.itemView.setOnClickListener(view -> subCategoryFragment.navigateToServicesDetails(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(serviceAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(serviceAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite, subIcon;
        TextView type, location, date, subCat, subType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            type = itemView.findViewById(R.id.job_title);
            location = itemView.findViewById(R.id.ad_location);
            date = itemView.findViewById(R.id.posted_date);
            subCat = itemView.findViewById(R.id.sub_cat);
            subType = itemView.findViewById(R.id.sub_type);
            subIcon = itemView.findViewById(R.id.sub_cat_icon);
        }
    }
}
