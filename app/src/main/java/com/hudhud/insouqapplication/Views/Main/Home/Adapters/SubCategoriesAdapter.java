package com.hudhud.insouqapplication.Views.Main.Home.Adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.caverock.androidsvg.SVG;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Responses.SubCategory;
import com.hudhud.insouqapplication.AppUtils.SVG.SvgDecoder;
import com.hudhud.insouqapplication.AppUtils.SVG.SvgDrawableTranscoder;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;

import java.io.InputStream;
import java.util.ArrayList;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {

    HomeFragment homeFragment;
    String categoryName;
    int id;
    ArrayList<SubCategory> subCategories;
    Context context;

    public SubCategoriesAdapter(HomeFragment homeFragment, String categoryName, int id, ArrayList<SubCategory> subCategories) {
        this.homeFragment = homeFragment;
        this.categoryName = categoryName;
        this.id = id;
        this.subCategories = subCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View subCategoriesView = inflater.inflate(R.layout.sub_categories_layout, parent, false);
        return new ViewHolder(subCategoriesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);

        if (AppDefs.language.equals("ar")){
            holder.subCategoryTitle.setText(subCategory.getNameAr());
        }else {
            holder.subCategoryTitle.setText(subCategory.getNameEn());
        }
        holder.numOfAds.setText(subCategory.getNumberOfAds()+" "+ context.getResources().getString(R.string.ads));

        String newPic = subCategory.getIcon().replace("\\", "/");
        Glide.with(context).load(newPic).into(holder.icon);
//        Helpers.fetchSvg(context, newPic, holder.icon);

//        holder.itemView.setOnClickListener(view -> homeFragment.navigateToSubCategory(categoryName, position, String.valueOf(subCategory.getId())));

        holder.itemView.setOnClickListener(view -> {
            if (AppDefs.language.equals("ar")){
                AppDefs.subCatName = subCategory.getNameAr();
            }else{
                AppDefs.subCatName = subCategory.getNameEn();
            }
            if (id == 1){
                if (subCategory.getId() == 2 || subCategory.getId() == 8) {
                    homeFragment.navigateToBrands(String.valueOf(subCategory.getId()));
                }else if (subCategory.getId() == 5){
                    homeFragment.navigateToAges(String.valueOf(subCategory.getId()));
                } else if (subCategory.getId() == 6) {
                    homeFragment.navigateToCategories(String.valueOf(subCategory.getId()));
                } else if (subCategory.getId() == 7) {
                    homeFragment.navigateToCategories1(String.valueOf(subCategory.getId()));
                } else if (subCategory.getId() == 9) {
                    homeFragment.navigateToCategories2(String.valueOf(subCategory.getId()));
                } else {
                    homeFragment.navigateToSubCategory(categoryName, position, String.valueOf(subCategory.getId()));
                }
            }else if (id == 3){
                homeFragment.navigateToJobs(String.valueOf(subCategory.getId()));
            } else if (id == 6) {
                homeFragment.navigateToClassifieds(String.valueOf(subCategory.getId()));
            }  else if (id == 7) {
                if (subCategory.getId() == 17){
                    homeFragment.navigateToPlateNumbers(String.valueOf(subCategory.getId()));
                }else {
                    homeFragment.navigateToMobileNumbers(String.valueOf(subCategory.getId()));
                }

            } else if (id == 8) {
                homeFragment.navigateToElectronics(String.valueOf(subCategory.getId()));
            } else {
                homeFragment.navigateToSubCategory(categoryName, position, String.valueOf(subCategory.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subCategoryTitle, numOfAds;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryTitle = itemView.findViewById(R.id.sub_category_title);
            numOfAds = itemView.findViewById(R.id.number_of_ads);
            icon = itemView.findViewById(R.id.sub_category_icon);
        }
    }
}
