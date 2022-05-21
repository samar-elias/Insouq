package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors;

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
import com.google.android.material.tabs.TabLayout;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Responses.Picture;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class MotorItemGridAdapter extends RecyclerView.Adapter<MotorItemGridAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    ArrayList<UsedCarAd> usedCarAds;
    String categoryId;
    Context context;
    boolean fav = false;

    public MotorItemGridAdapter(SubCategoryFragment subCategoryFragment, ArrayList<UsedCarAd> usedCarAds, String categoryId) {
        this.subCategoryFragment = subCategoryFragment;
        this.usedCarAds = usedCarAds;
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View motorItemView = inflater.inflate(R.layout.motors_grid_layout, parent, false);
        return new ViewHolder(motorItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsedCarAd usedCarAd = usedCarAds.get(position);

        holder.postDate.setText(usedCarAd.getPostDate());
//        holder.title.setText(usedCarAd.getTitle());
        String newPic = usedCarAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);

        holder.viewPager.setVisibility(View.VISIBLE);
        holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        ImageViewPagerAdapter mAdapter = new ImageViewPagerAdapter(usedCarAd.getPictures(), subCategoryFragment.mainActivity);
        mAdapter.notifyDataSetChanged();
        holder.viewPager.setOffscreenPageLimit(3);
        holder.viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000, holder.viewPager, mAdapter);

        if (AppDefs.language.equals("ar")){
            holder.location.setText(usedCarAd.getArLocation());
        }else {
            holder.location.setText(usedCarAd.getEnLocation());
        }

        switch (categoryId){
            case "2":
            case "8":
            case "9":
            case "6":
                holder.kilos.setText(usedCarAd.getKiloMeters()+" KM");
                if (AppDefs.language.equals("ar")){
                    holder.value.setText(usedCarAd.getArColor());
                }else {
                    holder.value.setText(usedCarAd.getEnColor());
                }
                Glide.with(context).load(R.drawable.color).into(holder.icon);
                Glide.with(context).load(R.drawable.kilometer_list).into(holder.kiloIcon);
                break;
            case "5":
                if (AppDefs.language.equals("ar")){
                    holder.kilos.setText(usedCarAd.getArLength());
                    holder.value.setText(usedCarAd.getArAge());
                }else {
                    holder.kilos.setText(usedCarAd.getEnLength());
                    holder.value.setText(usedCarAd.getEnAge());
                }
                Glide.with(context).load(R.drawable.age_list).into(holder.icon);
                Glide.with(context).load(R.drawable.lenght_img).into(holder.kiloIcon);
                break;
            case "7":
                if (AppDefs.language.equals("ar")){
                    holder.kilos.setText(usedCarAd.getSubCategoryArName());
                    holder.year.setText(usedCarAd.getArPartName());
                }else {
                    holder.year.setText(usedCarAd.getEnPartName());
                    holder.kilos.setText(usedCarAd.getSubCategoryEnName());
                }
                holder.icon.setVisibility(View.GONE);
                holder.value.setVisibility(View.GONE);
                Glide.with(context).load(R.drawable.age_list).into(holder.kiloIcon);
                Glide.with(context).load(R.drawable.part_name).into(holder.yearIcon);
                break;
        }

        switch (categoryId){
            case "2":
            case "8":
                if (AppDefs.language.equals("ar")){
                    holder.title.setText(usedCarAd.getArMaker()+", "+usedCarAd.getArModel()+", "+usedCarAd.getYear());
                }else {
                    holder.title.setText(usedCarAd.getEnMaker()+", "+usedCarAd.getEnModel()+", "+usedCarAd.getYear());
                }
                break;
            case "6":
            case "9":
                if (AppDefs.language.equals("ar")){
                    holder.title.setText(usedCarAd.getSubCategoryArName()+", "+usedCarAd.getYear());
                }else {
                    holder.title.setText(usedCarAd.getSubCategoryEnName()+", "+usedCarAd.getYear());
                }
                break;
            case "5":
                if (AppDefs.language.equals("ar")){
                    holder.title.setText(usedCarAd.getCategoryArName()+", "+usedCarAd.getYear());
                }else {
                    holder.title.setText(usedCarAd.getCategoryEnName()+", "+usedCarAd.getYear());
                }
                break;
            case "7":
                holder.title.setText(usedCarAd.getTitle());
                break;
        }

        holder.price.setText("AED "+usedCarAd.getPrice());
        holder.year.setText(usedCarAd.getYear());
        if (usedCarAd.getIsFav().equals("true")){
            fav = true;
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
        }else {
            fav = false;
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
        }
//        String postDate = usedCarAd.getPostDate().substring(0, usedCarAd.getPostDate().indexOf("T"));
//        holder.postDate.setText(postDate);

        holder.itemView.setOnClickListener(v -> {
            if (subCategoryFragment != null){
                subCategoryFragment.navigateToMotorsDetails(position);
            }
        });
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(usedCarAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(usedCarAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return usedCarAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite, image, icon, kiloIcon, yearIcon;
        TextView title, location, kilos, year, price, postDate, value;
        ViewPager viewPager;
        TabLayout tabLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.job_title);
            location = itemView.findViewById(R.id.ad_location);
            kilos = itemView.findViewById(R.id.kilos_amount);
            year = itemView.findViewById(R.id.motor_year);
            price = itemView.findViewById(R.id.motor_price);
            postDate = itemView.findViewById(R.id.posted_date);
            icon = itemView.findViewById(R.id.icon);
            value = itemView.findViewById(R.id.value);
            kiloIcon = itemView.findViewById(R.id.kilos_icon);
            yearIcon = itemView.findViewById(R.id.motor_year_icon);
            viewPager = itemView.findViewById(R.id.viewPager);
            tabLayout = itemView.findViewById(R.id.tabDots);
        }
    }
}
