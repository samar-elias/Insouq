package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.Parts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Brand;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.Bike.Bike1Fragment;

import java.util.ArrayList;

public class AgesAdapter extends RecyclerView.Adapter<AgesAdapter.ViewHolder> {

    Part1Fragment part1Fragment;
    ArrayList<Brand> brands;

    public AgesAdapter(Part1Fragment part1Fragment, ArrayList<Brand> brands) {
        this.part1Fragment = part1Fragment;
        this.brands = brands;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View subCategoriesView = inflater.inflate(R.layout.brands_layout, parent, false);
        return new ViewHolder(subCategoriesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Brand brand = brands.get(position);

        if (AppDefs.language.equals("ar")){
            holder.brandTitle.setText(brand.getTitleAr());
        }else {
            holder.brandTitle.setText(brand.getTitleEn());
        }

        holder.itemView.setOnClickListener(view -> {
            AppDefs.brand = brand.getId();
            if (brand.getId().equals("-1")){
                part1Fragment.navigateToList();
            }else {
                part1Fragment.navigateToModels(brand.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView brandTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTitle = itemView.findViewById(R.id.brand_title);
        }
    }
}
