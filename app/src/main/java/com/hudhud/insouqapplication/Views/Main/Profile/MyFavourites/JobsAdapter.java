package com.hudhud.insouqapplication.Views.Main.Profile.MyFavourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    MyFavouritesFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<JobAd> jobAds;

    public JobsAdapter(MyFavouritesFragment subCategoryFragment, ArrayList<JobAd> jobAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.jobAds = jobAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View jobsView = inflater.inflate(R.layout.job_item_layout, parent, false);
        return new ViewHolder(jobsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobAd jobAd = jobAds.get(position);

        holder.date.setText(jobAd.getPostedDate());
        if (AppDefs.language.equals("ar")){
            holder.jobTime.setText(jobAd.getArEmploymentType());
            holder.jobExperience.setText(jobAd.getArMinWorkExperience());
        }else {
            holder.jobTime.setText(jobAd.getEnEmploymentType());
            holder.jobExperience.setText(jobAd.getEnMinWorkExperience());
        }
        holder.jobTitle.setText(jobAd.getTitle());
        holder.jobCompany.setText(jobAd.getCompanyName());

        holder.itemView.setOnClickListener(view -> {
            if (jobAd.getCategoryId().equals("3")){
                subCategoryFragment.navigateToJobsDetails(position);
            }else {
                subCategoryFragment.navigateToJobWantedDetails(position);
            }
        });
        if (jobAd.getIsFav().equals("true")){
            fav = true;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_red_24).into(holder.favourite);
        }else {
            Glide.with(context).load(R.drawable.ic_baseline_favorite_border_24).into(holder.favourite);
            fav = false;
        }
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
        holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));

    }

    @Override
    public int getItemCount() {
        return jobAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite;
        TextView jobTitle, jobCompany, jobTime, jobExperience, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite);
            jobTitle = itemView.findViewById(R.id.job_title);
            jobCompany = itemView.findViewById(R.id.job_company);
            jobTime = itemView.findViewById(R.id.job_time);
            jobExperience = itemView.findViewById(R.id.job_experience);
            date = itemView.findViewById(R.id.posted_date);
        }
    }
}
