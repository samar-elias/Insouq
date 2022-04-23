package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.Boats;

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
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.Cars.BrandFragment;

import java.util.ArrayList;

public class AgesAdapter extends RecyclerView.Adapter<AgesAdapter.ViewHolder> {

    Filter1Fragment filter1Fragment;
    ArrayList<Brand> brands;

    public AgesAdapter(Filter1Fragment filter1Fragment, ArrayList<Brand> brands) {
        this.filter1Fragment = filter1Fragment;
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
            if (brand.getId().equals("-1")){
                AppDefs.brand = brand.getId();
                filter1Fragment.navigateToList();
            }else {
                AppDefs.brand = brand.getTitleEn()+"^"+brand.getTitleAr();
                filter1Fragment.navigateToModels(brand.getTitleEn()+"^"+brand.getTitleAr());
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
