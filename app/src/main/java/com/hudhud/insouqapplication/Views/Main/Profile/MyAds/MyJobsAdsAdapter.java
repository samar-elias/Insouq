package com.hudhud.insouqapplication.Views.Main.Profile.MyAds;

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
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class MyJobsAdsAdapter extends RecyclerView.Adapter<MyJobsAdsAdapter.ViewHolder> {

    MyAdsFragment myAdsFragment;
    ArrayList<JobAd> jobAds;
    Context context;

    public MyJobsAdsAdapter(MyAdsFragment myAdsFragment, ArrayList<JobAd> jobAds) {
        this.myAdsFragment = myAdsFragment;
        this.jobAds = jobAds;
    }

    @NonNull
    @Override
    public MyJobsAdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View myAdsView = inflater.inflate(R.layout.my_ads_layout, parent, false);
        return new ViewHolder(myAdsView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyJobsAdsAdapter.ViewHolder holder, int position) {
        JobAd usedCarAd = jobAds.get(position);


        if (usedCarAd.getStatus().equals("2")){
            holder.live.setVisibility(View.INVISIBLE);
            holder.review.setVisibility(View.VISIBLE);
        }else {
            holder.live.setVisibility(View.VISIBLE);
            holder.review.setVisibility(View.INVISIBLE);
        }

        holder.price.setVisibility(View.GONE);
        holder.title.setText(usedCarAd.getTitle());
        if (AppDefs.language.equals("ar")){
            holder.location.setText(usedCarAd.getArLocation());
        }else {
            holder.location.setText(usedCarAd.getEnLocation());
        }
        Glide.with(context).load(R.drawable.job_logo).into(holder.image);
        holder.options.setOnClickListener(view -> myAdsFragment.showAdOptions("3", usedCarAd.getId()));
        holder.getMoreViews.setOnClickListener(view -> myAdsFragment.navigateToMoreViews("3", usedCarAd.getId()));
    }

    @Override
    public int getItemCount() {
        return jobAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView options, greenDot, image;
        LinearLayout availableAd;
        TextView liveAd, removedAd;
        ConstraintLayout live, review;
        TextView getMoreViews, title, price, location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.ad_title);
            price = itemView.findViewById(R.id.ad_price);
            location = itemView.findViewById(R.id.ad_location);
            options = itemView.findViewById(R.id.ad_options);
            greenDot = itemView.findViewById(R.id.green_dot);
            availableAd = itemView.findViewById(R.id.live_ad_layout);
            liveAd = itemView.findViewById(R.id.live_ad);
            removedAd = itemView.findViewById(R.id.removed_ad);
            getMoreViews = itemView.findViewById(R.id.more_views);

            live = itemView.findViewById(R.id.liveLayout);
            review = itemView.findViewById(R.id.reviewLayout);
        }
    }
}
