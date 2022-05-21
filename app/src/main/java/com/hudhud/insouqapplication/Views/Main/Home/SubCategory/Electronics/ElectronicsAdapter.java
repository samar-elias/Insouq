package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics;

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
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class ElectronicsAdapter extends RecyclerView.Adapter<ElectronicsAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    String categoryId;
    boolean fav = false;
    ArrayList<ElectronicAd> electronicAds;

    public ElectronicsAdapter(SubCategoryFragment subCategoryFragment, ArrayList<ElectronicAd> electronicAds, String categoryId) {
        this.subCategoryFragment = subCategoryFragment;
        this.electronicAds = electronicAds;
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View electronicsView = inflater.inflate(R.layout.electronic_item_layout, parent, false);
        return new ViewHolder(electronicsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ElectronicAd electronicAd = electronicAds.get(position);

        String newPic = electronicAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);

        holder.viewPager.setVisibility(View.VISIBLE);
        holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        ImageViewPagerAdapter mAdapter = new ImageViewPagerAdapter(electronicAd.getPictures(), subCategoryFragment.mainActivity);
        mAdapter.notifyDataSetChanged();
        holder.viewPager.setOffscreenPageLimit(3);
        holder.viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000, holder.viewPager, mAdapter);

        holder.postedDate.setText(electronicAd.getPostedDate());
        if (AppDefs.language.equals("ar")){
            holder.title.setText(electronicAd.getSubCatArName()+", "+ electronicAd.getSubTypeArName());
        }else {
            holder.title.setText(electronicAd.getSubCatEnName()+", "+ electronicAd.getSubTypeEnName());
        }
        holder.price.setText("AED "+electronicAd.getPrice());

        if (AppDefs.language.equals("ar")){
            holder.location.setText(electronicAd.getArLocation());
        }else {
            holder.location.setText(electronicAd.getEnLocation());
        }

        if (electronicAd.getIsFav().equals("true")){
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
            fav = true;
        }else {
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
            fav = false;
        }

        if (categoryId.equals("19") || categoryId.equals("33")){
            Glide.with(context).load(R.drawable.color).into(holder.icon1);
            Glide.with(context).load(R.drawable.usage_1).into(holder.icon2);
            Glide.with(context).load(R.drawable.age_list).into(holder.icon3);
            Glide.with(context).load(R.drawable.storage).into(holder.icon4);

            holder.value1.setText(electronicAd.getEnColor());
            holder.value2.setText(electronicAd.getEnUsage());
            holder.value3.setText(electronicAd.getEnAge());
            holder.value4.setText(electronicAd.getEnStorage());

            holder.value2.setVisibility(View.GONE);
            holder.icon2.setVisibility(View.GONE);
        }else if  (categoryId.equals("35") || categoryId.equals("36")){
            Glide.with(context).load(R.drawable.sub_category_list).into(holder.icon1);
            holder.icon2.setVisibility(View.GONE);
            holder.icon3.setVisibility(View.GONE);
            holder.icon4.setVisibility(View.GONE);

            holder.value1.setText(electronicAd.getSubCatEnName());
            holder.value2.setVisibility(View.GONE);
            holder.value3.setVisibility(View.GONE);
            holder.value4.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(R.drawable.sub_category_list).into(holder.icon1);
            Glide.with(context).load(R.drawable.usage_1).into(holder.icon2);
            Glide.with(context).load(R.drawable.age_list).into(holder.icon3);
            holder.icon4.setVisibility(View.GONE);

            holder.value1.setText(electronicAd.getSubCatEnName());
            holder.value2.setText(electronicAd.getEnUsage());
            holder.value3.setText(electronicAd.getEnAge());
            holder.value4.setVisibility(View.GONE);

            holder.value2.setVisibility(View.GONE);
            holder.icon2.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> subCategoryFragment.navigateToElectronicsDetails(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(electronicAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(electronicAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return electronicAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite, image, icon1,icon2, icon3, icon4;
        TextView title, location, price, postedDate, value1, value2, value3, value4;
        ViewPager viewPager;
        TabLayout tabLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite);
            image = itemView.findViewById(R.id.electronics_image);
            title = itemView.findViewById(R.id.electronics_title);
            location = itemView.findViewById(R.id.ad_location);
            price = itemView.findViewById(R.id.price);
            postedDate = itemView.findViewById(R.id.posted_since);
            icon1 = itemView.findViewById(R.id.icon1);
            icon2 = itemView.findViewById(R.id.icon2);
            icon3 = itemView.findViewById(R.id.icon3);
            icon4 = itemView.findViewById(R.id.icon4);
            value1 = itemView.findViewById(R.id.value1);
            value2 = itemView.findViewById(R.id.value2);
            value3 = itemView.findViewById(R.id.value3);
            value4 = itemView.findViewById(R.id.value4);
            viewPager = itemView.findViewById(R.id.viewPager);
            tabLayout = itemView.findViewById(R.id.tabDots);
        }
    }
}
