package com.hudhud.insouqapplication.Views.Main.SellingItems;

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
import com.hudhud.insouqapplication.AppUtils.Responses.SubCategory;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    SubCategoriesPostAdFragment subCategoriesPostAdFragment;
    ArrayList<SubCategory> subCategories;
    Context context;
    int categoryId;
    String title = "";

    public SubCategoryAdapter(SubCategoriesPostAdFragment subCategoriesPostAdFragment, ArrayList<SubCategory> subCategories, int categoryId) {
        this.subCategoriesPostAdFragment = subCategoriesPostAdFragment;
        this.subCategories = subCategories;
        this.categoryId = categoryId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View subCategoriesView = inflater.inflate(R.layout.sub_category_layout, parent, false);
        return new ViewHolder(subCategoriesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);

        if (AppDefs.language.equals("ar")){
            holder.subCategoryTitle.setText(subCategory.getNameAr());
            title = subCategory.getNameAr();
        }else {
            holder.subCategoryTitle.setText(subCategory.getNameEn());
            title = subCategory.getNameEn();
        }
        holder.numOfAds.setText(subCategory.getNumberOfAds()+ context.getString(R.string.ads));
        String newPic = subCategory.getIcon().replace("\\", "/");
        Glide.with(context).load(newPic).into(holder.subCategoryIcon);
        holder.item.setOnClickListener(view -> {
            if (AppDefs.language.equals("ar")){
                title = subCategory.getNameAr();
            }else {
                title = subCategory.getNameEn();
            }
            switch (categoryId){
                case 1:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.foshia));
                    break;
                case 2:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.orange));
                    break;
                case 3:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.peach));
                    break;
                case 4:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.fairouz));
                    break;
                case 5:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.green));
                    break;
                case 6:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.blue));
                    break;
                case 7:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.purple));
                    break;
                case 8:
                    holder.item.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                    break;
            }
            subCategoriesPostAdFragment.navigateSell(subCategory.getId(), position, title);
        });
        if (categoryId == 5){
            Glide.with(context).load(subCategory.getIcon()).into(holder.subCategoryIcon);
        }
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView item;
        TextView subCategoryTitle, numOfAds;
        ImageView subCategoryIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.materialCardView);
            subCategoryTitle = itemView.findViewById(R.id.sub_category_title);
            subCategoryIcon = itemView.findViewById(R.id.sub_category_icon);
            numOfAds = itemView.findViewById(R.id.number_of_ads);
        }
    }
}
