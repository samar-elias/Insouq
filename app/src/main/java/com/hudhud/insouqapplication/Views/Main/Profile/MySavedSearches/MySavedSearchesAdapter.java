package com.hudhud.insouqapplication.Views.Main.Profile.MySavedSearches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.SavedSearchAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Profile.MyAds.MyAdsFragment;

import java.util.ArrayList;

public class MySavedSearchesAdapter extends RecyclerView.Adapter<MySavedSearchesAdapter.ViewHolder> {

    MySavedSearchesFragment mySavedSearchesFragment;
    ArrayList<SavedSearchAd> savedSearchAds;
    Context context;

    public MySavedSearchesAdapter(MySavedSearchesFragment mySavedSearchesFragment, ArrayList<SavedSearchAd> savedSearchAds) {
        this.mySavedSearchesFragment = mySavedSearchesFragment;
        this.savedSearchAds = savedSearchAds;
    }

    @NonNull
    @Override
    public MySavedSearchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View myAdsView = inflater.inflate(R.layout.my_saved_searches_layout, parent, false);
        return new ViewHolder(myAdsView);
    }

    @Override
    public void onBindViewHolder(@NonNull MySavedSearchesAdapter.ViewHolder holder, int position) {
        SavedSearchAd savedSearchAd = savedSearchAds.get(position);

        switch (savedSearchAd.getTypeId()){
            case "1":
                Glide.with(context).load(R.drawable.foshia_circle).into(holder.background);
                Glide.with(context).load(R.drawable.motors_icon).into(holder.img);
                break;
            case "3":
                Glide.with(context).load(R.drawable.peach_circle).into(holder.background);
                Glide.with(context).load(R.drawable.jobs_icon).into(holder.img);
                break;
            case "4":
                Glide.with(context).load(R.drawable.fairouz_circle).into(holder.background);
                Glide.with(context).load(R.drawable.services_icon).into(holder.img);
                break;
            case "5":
                Glide.with(context).load(R.drawable.green_circle).into(holder.background);
                Glide.with(context).load(R.drawable.business_icon).into(holder.img);
                break;
            case "6":
                Glide.with(context).load(R.drawable.blue_circle).into(holder.background);
                Glide.with(context).load(R.drawable.classifieds_icon).into(holder.img);
                break;
            case "7":
                Glide.with(context).load(R.drawable.purp_circle).into(holder.background);
                Glide.with(context).load(R.drawable.numbers_icon).into(holder.img);
                break;
            case "8":
                Glide.with(context).load(R.drawable.yellow_circle).into(holder.background);
                Glide.with(context).load(R.drawable.electronics_icon).into(holder.img);
                break;
        }

        String date = mySavedSearchesFragment.mainActivity.convertDate(savedSearchAd.getDate());
        holder.date.setText(date);

        if (savedSearchAd.getAlert().equals("true")){
            holder.push.setChecked(true);
        }else {
            holder.push.setChecked(false);
        }
        if (savedSearchAd.getEmailAlert().equals("true")){
            holder.email.setChecked(true);
        }else {
            holder.email.setChecked(false);
        }
        if (AppDefs.language.equals("ar")) {
            holder.title.setText(savedSearchAd.getTypeAr()+", "+savedSearchAd.getCategoryAr()+", "+savedSearchAd.getLocation());
        }else {
            holder.title.setText(savedSearchAd.getTypeEn()+", "+savedSearchAd.getCategoryEn()+", "+savedSearchAd.getLocation());

        }
        holder.goBtn.setOnClickListener(view -> {
            AppDefs.isSaved = true;
            AppDefs.savedSearchAd = savedSearchAd;
            mySavedSearchesFragment.navigateToSubCategory(savedSearchAd.getTypeEn(), position, savedSearchAd.getTypeId());
        });

        holder.delete.setOnClickListener(view -> mySavedSearchesFragment.deleteAd(savedSearchAd.getId(), savedSearchAd.getTypeId()));

    }

    @Override
    public int getItemCount() {
        return savedSearchAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView goBtn;
        ImageView background, img, delete;
        TextView title, date;
        SwitchCompat email, push;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goBtn = itemView.findViewById(R.id.go_btn);
            background = itemView.findViewById(R.id.motors_background);
            img = itemView.findViewById(R.id.motors_icon);
            title = itemView.findViewById(R.id.title);
            push = itemView.findViewById(R.id.push);
            email = itemView.findViewById(R.id.email);
            delete = itemView.findViewById(R.id.delete);
            date = itemView.findViewById(R.id.date);
        }
    }
}
