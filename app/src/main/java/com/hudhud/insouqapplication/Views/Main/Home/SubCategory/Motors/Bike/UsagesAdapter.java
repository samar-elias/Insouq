package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.Bike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Model;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.Boats.Filter2Fragment;

import java.util.ArrayList;

public class UsagesAdapter extends RecyclerView.Adapter<UsagesAdapter.ViewHolder> {

    Bike2Fragment bike2Fragment;
    ArrayList<Model> models;

    public UsagesAdapter(Bike2Fragment bike2Fragment, ArrayList<Model> models) {
        this.bike2Fragment = bike2Fragment;
        this.models = models;
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
        Model model = models.get(position);

        if (AppDefs.language.equals("ar")){
            holder.modelTitle.setText(model.getTitleAr());
        }else {
            holder.modelTitle.setText(model.getTitleEn());
        }
        holder.itemView.setOnClickListener(view -> {
            AppDefs.model = model.getId();
            bike2Fragment.navigateToSubCategories();
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView modelTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modelTitle = itemView.findViewById(R.id.brand_title);
        }
    }
}
