package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

public class PropertyItemAdapter extends RecyclerView.Adapter<PropertyItemAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    boolean fav = false;

    public PropertyItemAdapter(SubCategoryFragment subCategoryFragment) {
        this.subCategoryFragment = subCategoryFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View propertyView = inflater.inflate(R.layout.property_item_layout, parent, false);
        return new ViewHolder(propertyView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0){
            holder.propertyType.setText("Villa");
        }else {
            holder.propertyType.setText("Apartment");
        }
        holder.itemView.setOnClickListener(view -> subCategoryFragment.navigateToPropertyDetails());
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView propertyType;
        ConstraintLayout villaInfo;
        ImageView favourite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyType = itemView.findViewById(R.id.property_type);
            villaInfo = itemView.findViewById(R.id.villa_layout);
            favourite = itemView.findViewById(R.id.favourite_property);
        }
    }
}
