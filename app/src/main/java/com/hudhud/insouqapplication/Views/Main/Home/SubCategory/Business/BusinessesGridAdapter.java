package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Business;

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
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class BusinessesGridAdapter extends RecyclerView.Adapter<BusinessesGridAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<BusinessAd> businessAds;

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

        holder.title.setText(businessAd.getTitle());
        holder.price.setText("AED "+businessAd.getPrice());

        holder.date.setText(businessAd.getPostedDate());

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
        ImageView favourite, image;
        TextView title, location, price, date, categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            image = itemView.findViewById(R.id.business_image);
            title = itemView.findViewById(R.id.business_title);
            location = itemView.findViewById(R.id.ad_location);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.posted_date);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
