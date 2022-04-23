package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobWanted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class JobWantedItemGridAdapter extends RecyclerView.Adapter<JobWantedItemGridAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<JobAd> jobAds;

    public JobWantedItemGridAdapter(SubCategoryFragment subCategoryFragment, ArrayList<JobAd> jobAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.jobAds = jobAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View motorItemView = inflater.inflate(R.layout.job_wanted_grid_item_layout, parent, false);
        return new ViewHolder(motorItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobAd jobAd = jobAds.get(position);

        holder.title.setText(jobAd.getTitle());
        holder.postedDate.setText(jobAd.getPostedDate());
        holder.experience.setText(jobAd.getEnMinWorkExperience());
        if (jobAd.getOtherJobType().equals("null")){
            if (AppDefs.language.equals("ar")){
                holder.type.setText(jobAd.getArJobType());
            }else {
                holder.type.setText(jobAd.getEnJobType());
            }
        }else {
            holder.type.setText(jobAd.getOtherJobType());
        }

        if (jobAd.getIsFav().equals("true")){
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
            fav = true;
        }else {
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
            fav = false;
        }

        holder.value.setText(jobAd.getCurrentPosition());
        if (AppDefs.language.equals("ar")){
            holder.time.setText(jobAd.getArEducationalLevel());
            holder.type.setText(jobAd.getArLocation());
        }else {
            holder.time.setText(jobAd.getArEducationalLevel());
            holder.type.setText(jobAd.getEnLocation());
        }

        holder.itemView.setOnClickListener(v -> subCategoryFragment.navigateToJobWantedDetails(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(jobAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(jobAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite;
        TextView title, type, time, experience, postedDate, value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            title = itemView.findViewById(R.id.job_title);
            type = itemView.findViewById(R.id.job_type);
            time = itemView.findViewById(R.id.job_time);
            experience = itemView.findViewById(R.id.job_experience);
            postedDate = itemView.findViewById(R.id.posted_date);
            value = itemView.findViewById(R.id.value);
        }
    }
}
