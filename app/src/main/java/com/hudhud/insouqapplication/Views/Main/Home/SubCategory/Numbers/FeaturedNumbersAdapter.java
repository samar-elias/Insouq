package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Numbers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

public class FeaturedNumbersAdapter extends RecyclerView.Adapter<FeaturedNumbersAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;

    public FeaturedNumbersAdapter(SubCategoryFragment subCategoryFragment) {
        this.subCategoryFragment = subCategoryFragment;
    }

    public FeaturedNumbersAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View featuredNumbersView = inflater.inflate(R.layout.number_featured_layout, parent, false);
        return new ViewHolder(featuredNumbersView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
//            if (subCategoryFragment!=null){
//                subCategoryFragment.navigateToNumberDetails();
//            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
