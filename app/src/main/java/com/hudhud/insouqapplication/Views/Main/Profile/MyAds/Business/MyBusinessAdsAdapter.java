package com.hudhud.insouqapplication.Views.Main.Profile.MyAds.Business;

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
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Profile.MyAds.MyAdsFragment;

import java.util.ArrayList;

public class MyBusinessAdsAdapter extends RecyclerView.Adapter<MyBusinessAdsAdapter.ViewHolder> {

    MyAdsFragment myAdsFragment;
    ArrayList<BusinessAd> businessAds;
    Context context;

    public MyBusinessAdsAdapter(MyAdsFragment myAdsFragment, ArrayList<BusinessAd> businessAds) {
        this.myAdsFragment = myAdsFragment;
        this.businessAds = businessAds;
    }

    @NonNull
    @Override
    public MyBusinessAdsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View myAdsView = inflater.inflate(R.layout.my_ads_layout, parent, false);
        return new ViewHolder(myAdsView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBusinessAdsAdapter.ViewHolder holder, int position) {
        BusinessAd businessAd = businessAds.get(position);

        if (businessAd.getStatus().equals("2")){
            holder.live.setVisibility(View.INVISIBLE);
            holder.review.setVisibility(View.VISIBLE);
        }else {
            holder.live.setVisibility(View.VISIBLE);
            holder.review.setVisibility(View.INVISIBLE);
        }

        holder.price.setText(businessAd.getPrice()+" AED");
        holder.title.setText(businessAd.getTitle());
        if (AppDefs.language.equals("ar")){
            holder.location.setText(businessAd.getArLocation());
        }else {
            holder.location.setText(businessAd.getEnLocation());
        }
        String newPic = businessAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);
        holder.options.setOnClickListener(view -> {
            myAdsFragment.showAdOptions("5", businessAd.getId());
            myAdsFragment.businessAd = businessAd;
        });
        holder.getMoreViews.setOnClickListener(view -> myAdsFragment.navigateToMoreViews("5", businessAd.getId()));
    }

    @Override
    public int getItemCount() {
        return businessAds.size();
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
