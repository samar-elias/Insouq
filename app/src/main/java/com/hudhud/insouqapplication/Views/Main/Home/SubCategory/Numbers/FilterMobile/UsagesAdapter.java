package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers.FilterMobile;

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
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers.FilterPlates.PlateTypeFragment;

import java.util.ArrayList;

public class UsagesAdapter extends RecyclerView.Adapter<UsagesAdapter.ViewHolder> {

    NumberFragment numberFragment;
    ArrayList<Model> models;

    public UsagesAdapter(NumberFragment numberFragment, ArrayList<Model> models) {
        this.numberFragment = numberFragment;
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

        holder.modelTitle.setText(model.getTitleAr());
        holder.itemView.setOnClickListener(view -> {
            if (model.getId().equals("-1")){
                AppDefs.model = model.getId();
            }else {
                AppDefs.model = model.getTitleAr();
            }
            numberFragment.navigateToSubCategories();
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
