package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Classifieds;

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

public class ClassifiedsGridItemAdapter extends RecyclerView.Adapter<ClassifiedsGridItemAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<ElectronicAd> electronicAds;

    public ClassifiedsGridItemAdapter(SubCategoryFragment subCategoryFragment, ArrayList<ElectronicAd> electronicAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.electronicAds = electronicAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View numbersView = inflater.inflate(R.layout.classified_grid_item_layout, parent, false);
        return new ViewHolder(numbersView);
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
            holder.title.setText(electronicAd.getTitle()+", "+ electronicAd.getSubTypeArName());
            holder.location.setText(electronicAd.getArLocation());
            holder.condition.setText(electronicAd.getArCondition());
            holder.usage.setText(electronicAd.getArUsage());
            holder.age.setText(electronicAd.getArAge());
        }else {
            holder.title.setText(electronicAd.getTitle()+", "+ electronicAd.getSubTypeEnName());
            holder.location.setText(electronicAd.getEnLocation());
            holder.condition.setText(electronicAd.getEnCondition());
            holder.usage.setText(electronicAd.getEnUsage());
            holder.age.setText(electronicAd.getEnAge());
        }
        holder.price.setText("AED "+electronicAd.getPrice());

        if (electronicAd.getIsFav().equals("true")){
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
            fav = true;
        }else {
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
            fav = false;
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
        ImageView favourite, image;
        TextView title, location, price, postedDate, usage, age, condition;
        ViewPager viewPager;
        TabLayout tabLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            postedDate = itemView.findViewById(R.id.posted_date);
            usage = itemView.findViewById(R.id.usage);
            age = itemView.findViewById(R.id.age);
            condition = itemView.findViewById(R.id.condition);
            viewPager = itemView.findViewById(R.id.viewPager);
            tabLayout = itemView.findViewById(R.id.tabDots);
        }
    }
}
