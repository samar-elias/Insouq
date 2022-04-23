package com.hudhud.insouqapplication.Views.Main.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.NewAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class JobsProductsAdapter extends RecyclerView.Adapter<JobsProductsAdapter.ViewHolder> {

    ArrayList<JobAd> newAds;
    HomeFragment homeFragment;
    Context context;

    public JobsProductsAdapter(ArrayList<JobAd> newAds, HomeFragment homeFragment) {
        this.newAds = newAds;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productsView = inflater.inflate(R.layout.new_jobs_layout, parent, false);
        return new ViewHolder(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobAd newAd = newAds.get(position);

        if (newAd.getEnJobType().equals("null")){
            holder.title.setText(newAd.getOtherJobType());
        }else {
            holder.title.setText(newAd.getEnJobType());
        }
        Glide.with(context).load(R.drawable.job_logo).into(holder.image);
        holder.loc.setText(newAd.getEnLocation());
        if (newAd.getEnWorkExperience().equals("null")){
            holder.price.setText(newAd.getEnMinWorkExperience());
        }else {
            holder.price.setText(newAd.getEnWorkExperience());
        }

        holder.item.setOnClickListener(view -> {
            if (newAd.getCategoryId().equals("3")){
                homeFragment.navigateToJobOpeningDetails(position);
            }else {
                homeFragment.navigateToJobWantedDetails(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price, loc;
        MaterialCardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            loc = itemView.findViewById(R.id.loc);
            item = itemView.findViewById(R.id.item);
        }
    }
}
