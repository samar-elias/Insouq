package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobWanted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;


public class JobsWantedFeaturedAdapter extends RecyclerView.Adapter<JobsWantedFeaturedAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;

    public JobsWantedFeaturedAdapter(SubCategoryFragment subCategoryFragment) {
        this.subCategoryFragment = subCategoryFragment;
    }

    public JobsWantedFeaturedAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View featuredJobView = inflater.inflate(R.layout.job_wanted_featured_layout, parent, false);
        return new ViewHolder(featuredJobView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> {
            if (subCategoryFragment != null){
                subCategoryFragment.navigateToJobWantedDetails(position);
            }
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
