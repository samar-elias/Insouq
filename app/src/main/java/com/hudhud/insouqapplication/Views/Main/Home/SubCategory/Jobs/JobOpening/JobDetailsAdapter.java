package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.MotorDetailsAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import java.util.ArrayList;

public class JobDetailsAdapter extends RecyclerView.Adapter<JobDetailsAdapter.ViewHolder> {

    ArrayList<JobAd> jobAds;
    JobDetailsFragment jobDetailsFragment;
    Context context;
    boolean fav = false;

    public JobDetailsAdapter(ArrayList<JobAd> jobAds, JobDetailsFragment jobDetailsFragment) {
        this.jobAds = jobAds;
        this.jobDetailsFragment = jobDetailsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.job_opening_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobAd jobAd = jobAds.get(position);

        jobDetailsFragment.getUserInfo(jobAd.getUserId(), holder.img, holder.name, holder.member);

        holder.title.setText(jobAd.getTitle());
        holder.companyName.setText(jobAd.getCompanyName());
        holder.description.setText(jobAd.getDescription());
        holder.postDate.setText(context.getResources().getString(R.string.posted_on)+" "+jobAd.getPostedDate());

        if (jobAd.getAgentId().equals("null")){
            holder.chat.setEnabled(false);
            holder.chat.setAlpha(0.3F);
        }else {
            holder.chat.setEnabled(true);
            holder.chat.setAlpha(1);
        }

//        holder.member.setText(jobDetailsFragment.memberSince);
//        holder.name.setText(jobDetailsFragment.profileName);
//        String img = jobDetailsFragment.profileImg.replace("\\", "/");
//        Glide.with(context).load(Urls.IMAGE_URL2+img).into(holder.img);

        if (AppDefs.language.equals("ar")) {
            holder.location.setText(jobAd.getArLocation());
            holder.location2.setText(jobAd.getArLocation());
        }else {
            holder.location.setText(jobAd.getEnLocation());
            holder.location2.setText(jobAd.getEnLocation());
        }
        ArrayList<specificationModel> jobSpecifications = new ArrayList<>();

        if (!jobAd.getEnJobType().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.job_type), jobAd.getArJobType(), R.drawable.job_type_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.job_type), jobAd.getEnJobType(), R.drawable.job_type_img));
            }
        }
        if (!jobAd.getOtherJobType().equals("null")){
            jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.job_type), jobAd.getOtherJobType(), R.drawable.job_type_img));
        }

        if (!jobAd.getEnCareerLevel().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.career_level), jobAd.getArCareerLevel(), R.drawable.career_level_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.career_level), jobAd.getEnCareerLevel(), R.drawable.career_level_img));
            }
        }

        if (!jobAd.getEnEmploymentType().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.employment_type), jobAd.getArEmploymentType(), R.drawable.employment_type_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.employment_type), jobAd.getEnEmploymentType(), R.drawable.employment_type_img));
            }
        }

        if (!jobAd.getEnCommitment().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.commitment), jobAd.getArCommitment(), R.drawable.commitment_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.commitment), jobAd.getEnCommitment(), R.drawable.commitment_img));
            }
        }

        if (!jobAd.getEnMinWorkExperience().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.minimum_work_experience), jobAd.getArMinWorkExperience(), R.drawable.work_experience_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.minimum_work_experience), jobAd.getEnMinWorkExperience(), R.drawable.work_experience_img));
            }
        }

        if (!jobAd.getEnWorkExperience().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.work_experience), jobAd.getArWorkExperience(), R.drawable.work_experience_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.work_experience), jobAd.getEnWorkExperience(), R.drawable.work_experience_img));
            }
        }

        if (!jobAd.getEnEducationalLevel().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.education_level), jobAd.getArEducationalLevel(), R.drawable.education_level_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.education_level), jobAd.getEnEducationalLevel(), R.drawable.education_level_img));
            }
        }

        if (!jobAd.getEnMinEducationalLevel().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.education_level), jobAd.getArMinEducationalLevel(), R.drawable.education_level_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.education_level), jobAd.getEnMinEducationalLevel(), R.drawable.education_level_img));
            }
        }

        if (!jobAd.getEnCurrentLocation().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.current_location), jobAd.getArCurrentLocation(), R.drawable.location));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.current_location), jobAd.getEnCurrentLocation(), R.drawable.location));
            }
        }

        if (!jobAd.getEnNoticePeriod().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.notice_period), jobAd.getArNoticePeriod(), R.drawable.notice_period_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.notice_period), jobAd.getEnNoticePeriod(), R.drawable.notice_period_img));
            }
        }
        if (!jobAd.getEnVisaStatus().equals("null")){
            if (AppDefs.language.equals("ar")){
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.visa_status), jobAd.getArVisaStatus(), R.drawable.visa_status_img));
            }else {
                jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.visa_status), jobAd.getEnVisaStatus(), R.drawable.visa_status_img));
            }
        }

        if (!jobAd.getExpectedSalary().equals("null")){
            jobSpecifications.add(new specificationModel(context.getResources().getString(R.string.expected_monthly_salary), jobAd.getExpectedSalary(), R.drawable.salary_img));
        }

        SpecificationAdapter specificationAdapter = new SpecificationAdapter(jobSpecifications);
        holder.specificationsRV.setAdapter(specificationAdapter);
        holder.specificationsRV.setLayoutManager(new LinearLayoutManager(context));

        holder.direction.setOnClickListener(view -> jobDetailsFragment.openGoogleMaps(jobAd.getLat(), jobAd.getLng()));
        holder.call.setOnClickListener(view -> jobDetailsFragment.call(jobAd.getPhoneNumber()));
        holder.sms.setOnClickListener(view -> jobDetailsFragment.sendSMS(jobAd.getPhoneNumber()));
        holder.share.setOnClickListener(view -> jobDetailsFragment.share(jobAd.getId()));
        holder.reportAd.setOnClickListener(view -> jobDetailsFragment.setReportAd(jobAd.getId()));
        holder.descriptionLayout.setOnClickListener(view -> {
            if (holder.showDescription){
                holder.showDescription = false;
                holder.description.setVisibility(View.GONE);
                holder.descriptionLine.setVisibility(View.GONE);
                holder.descriptionArrow.setScaleY(1);
            }else {
                holder.showDescription = true;
                holder.description.setVisibility(View.VISIBLE);
                holder.descriptionLine.setVisibility(View.VISIBLE);
                holder.descriptionArrow.setScaleY(-1);
            }
        });

        holder.locationLayout.setOnClickListener(view -> {
            if (holder.showLocation){
                holder.locationImage.setVisibility(View.GONE);
                holder.directionsLayout.setVisibility(View.GONE);
                holder.showLocation = false;
                holder.locationArrow.setScaleY(1);
            }else {
                holder.locationImage.setVisibility(View.VISIBLE);
                holder.directionsLayout.setVisibility(View.VISIBLE);
                holder.showLocation = true;
                holder.locationArrow.setScaleY(-1);
            }
        });
//        if (jobDetailsFragment.getOfferApplied(jobAd.getId())){
//            holder.applyForJob.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob2.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob.setEnabled(false);
//            holder.applyForJob2.setEnabled(false);
//        }

        holder.applyForJob.setOnClickListener(view -> jobDetailsFragment.navigateToApply(jobAd.getId()));
        holder.applyForJob2.setOnClickListener(view ->jobDetailsFragment.navigateToApply(jobAd.getId()));

        if (jobAd.getIsFav().equals("true")){
            fav = true;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_red_24).into(holder.favourite);
        }else {
            fav = false;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_border_24).into(holder.favourite);
        }
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                fav = true;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                jobDetailsFragment.addToFavourite(jobAd.getId());
            }else {
                fav = false;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                jobDetailsFragment.removeFromFavourite(jobAd.getId());
            }
        });

        holder.next.setOnClickListener(view ->{
            if (position+1 != jobAds.size()){
                jobDetailsFragment.scrollToPosition(position+1);
            }
        });
        holder.previous.setOnClickListener(view -> {
            if (position!=0){
                jobDetailsFragment.scrollToPosition(position-1);
            }
        });

        if (AppDefs.language.equals("ar")){
            holder.next.setScaleX(-1);
            holder.previous.setScaleX(-1);
        }else {
            holder.next.setScaleX(1);
            holder.previous.setScaleX(1);
        }

        holder.backToPrevious.setOnClickListener(view -> jobDetailsFragment.backToPrevious());
    }

    @Override
    public int getItemCount() {
        return jobAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout descriptionLayout, locationLayout, directionsLayout;
        ImageView backToPrevious, descriptionArrow, locationArrow, locationImage, favourite, direction, share, img;
        TextView description, reportAd, descriptionLine, applyForJob2, applyForJob, title, companyName, location,
                location2, employmentType, workExperience, postDate, name, member;
        Boolean showDescription, showLocation;
        RecyclerView similarJobsRV;
        LinearLayout call, sms, chat;
        ImageView next, previous;
        RecyclerView specificationsRV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite);
            backToPrevious = itemView.findViewById(R.id.back_arrow);

            next = itemView.findViewById(R.id.next);
            postDate = itemView.findViewById(R.id.post_date);
            previous = itemView.findViewById(R.id.previous);
            img = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.profile_name);
            member = itemView.findViewById(R.id.member_since);
            share = itemView.findViewById(R.id.share);
            reportAd = itemView.findViewById(R.id.report);
            location = itemView.findViewById(R.id.location);
            title = itemView.findViewById(R.id.job_title);
            companyName = itemView.findViewById(R.id.company_name);
            location2 = itemView.findViewById(R.id.location2);
            direction = itemView.findViewById(R.id.directions);
            employmentType = itemView.findViewById(R.id.employment_type);
            workExperience = itemView.findViewById(R.id.work_experience);
            specificationsRV = itemView.findViewById(R.id.specifications_RV);

            call = itemView.findViewById(R.id.call);
            sms = itemView.findViewById(R.id.sms);
            chat = itemView.findViewById(R.id.chat);

            //description
            descriptionLayout = itemView.findViewById(R.id.description_layout);
            descriptionArrow = itemView.findViewById(R.id.show_description_arrow);
            description = itemView.findViewById(R.id.description);
            descriptionLine = itemView.findViewById(R.id.line2);
            showDescription = false;
            descriptionLine.setVisibility(View.GONE);
            description.setVisibility(View.GONE);

            //location
            locationLayout = itemView.findViewById(R.id.location_layout);
            directionsLayout = itemView.findViewById(R.id.directions_layout);
            showLocation = false;
            locationArrow = itemView.findViewById(R.id.show_location_arrow);
            locationImage = itemView.findViewById(R.id.location_image);
            locationImage.setVisibility(View.GONE);
            directionsLayout.setVisibility(View.GONE);

            //similar
            similarJobsRV = itemView.findViewById(R.id.similar_jobs_RV);

            //apply for a job
            applyForJob = itemView.findViewById(R.id.apply_job);
            applyForJob2 = itemView.findViewById(R.id.apply_job2);
        }
    }
}
