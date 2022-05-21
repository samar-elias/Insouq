package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class BusinessesGridAdapter extends RecyclerView.Adapter<BusinessesGridAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<BusinessAd> businessAds;
    int image = R.drawable.other;

    public BusinessesGridAdapter(SubCategoryFragment subCategoryFragment, ArrayList<BusinessAd> businessAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.businessAds = businessAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View businessView = inflater.inflate(R.layout.business_grid_item_layout, parent, false);
        return new ViewHolder(businessView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusinessAd businessAd = businessAds.get(position);

        String newPic = businessAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);

        holder.viewPager.setVisibility(View.VISIBLE);
        holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        ImageViewPagerAdapter mAdapter = new ImageViewPagerAdapter(businessAd.getPictures(), subCategoryFragment.mainActivity);
        mAdapter.notifyDataSetChanged();
        holder.viewPager.setOffscreenPageLimit(3);
        holder.viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000, holder.viewPager, mAdapter);

        holder.title.setText(businessAd.getTitle());
        holder.price.setText("AED "+businessAd.getPrice());

        holder.date.setText(businessAd.getPostedDate());

        switch (businessAd.getCategoryId()){
            case "23":
                image = R.drawable.business_for_sale;
                break;
            case "56":
                image = R.drawable.trade_license_for_sale ;
                break;
            case "57":
                image = R.drawable.building_materials ;
                break;
            case "58":
                image = R.drawable.food_for_sale ;
                break;
            case "59":
                image = R.drawable.general_items ;
                break;
            case "60":
                image = R.drawable.shops_restaurants ;
                break;
            case "61":
                image = R.drawable.scarp_materials ;
                break;
        }

        Glide.with(context).load(image).into(holder.categoryIcon);

        if (businessAd.getIsFav().equals("true")){
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
            fav = true;
        }else {
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
            fav = false;
        }

        if (AppDefs.language.equals("ar")){
            holder.location.setText(businessAd.getArLocation());
        }else {
            holder.location.setText(businessAd.getEnLocation());
        }

        if (businessAd.getOtherCategoryNAme().equals("null") || businessAd.getOtherCategoryNAme().isEmpty()){
            if (AppDefs.language.equals("ar")){
                holder.categoryName.setText(businessAd.getCategoryArName());
            }else {
                holder.categoryName.setText(businessAd.getCategoryEnName());
            }
        }else {
            holder.categoryName.setText(businessAd.getOtherCategoryNAme());
        }

        holder.itemView.setOnClickListener(view -> subCategoryFragment.navigateToBusinessDetails(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(businessAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(businessAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return businessAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite, image, categoryIcon;
        TextView title, location, price, date, categoryName;
        ViewPager viewPager;
        TabLayout tabLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            image = itemView.findViewById(R.id.business_image);
            title = itemView.findViewById(R.id.business_title);
            location = itemView.findViewById(R.id.ad_location);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.posted_date);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryIcon = itemView.findViewById(R.id.cat_icon);
            viewPager = itemView.findViewById(R.id.viewPager);
            tabLayout = itemView.findViewById(R.id.tabDots);
        }
    }
}
