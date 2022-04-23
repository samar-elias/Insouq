package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.JobAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Jobs.JobOpening.JobDetailsFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.SpecificationAdapter;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors.specificationModel;

import java.util.ArrayList;

public class ServicesDetailsAdapter extends RecyclerView.Adapter<ServicesDetailsAdapter.ViewHolder> {

    ArrayList<ServiceAd> serviceAds;
    ServicesDetailsFragment servicesDetailsFragment;
    Context context;
    boolean fav = false;

    public ServicesDetailsAdapter(ArrayList<ServiceAd> serviceAds, ServicesDetailsFragment servicesDetailsFragment) {
        this.serviceAds = serviceAds;
        this.servicesDetailsFragment = servicesDetailsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.service_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceAd serviceAd = serviceAds.get(position);

        servicesDetailsFragment.getUserInfo(serviceAd.getUserId(), holder.img, holder.name, holder.member);

        holder.title.setText(serviceAd.getTitle());
        holder.description.setText(serviceAd.getDescription());
//        holder.member.setText(jobDetailsFragment.memberSince);
//        holder.name.setText(jobDetailsFragment.profileName);
//        String img = jobDetailsFragment.profileImg.replace("\\", "/");
//        Glide.with(context).load(Urls.IMAGE_URL2+img).into(holder.img);

        if (AppDefs.language.equals("ar")) {
            holder.location.setText(serviceAd.getArLocation());
            holder.location2.setText(serviceAd.getArLocation());
        }else {
            holder.location.setText(serviceAd.getEnLocation());
            holder.location2.setText(serviceAd.getEnLocation());
        }
        ArrayList<specificationModel> serviceSpecifications = new ArrayList<>();
        if (!serviceAd.getServiceTypeEnName().equals("null")){
            if (AppDefs.language.equals("ar")){
                serviceSpecifications.add(new specificationModel(context.getResources().getString(R.string.service_type), serviceAd.getServiceTypeArName(), R.drawable.service_type_img));
            }else {
                serviceSpecifications.add(new specificationModel(context.getResources().getString(R.string.service_type), serviceAd.getServiceTypeEnName(), R.drawable.service_type_img));
            }
        }

        if (!serviceAd.getOtherServiceType().equals("null")){
            serviceSpecifications.add(new specificationModel(context.getResources().getString(R.string.service_type), serviceAd.getOtherServiceType(), R.drawable.service_type_img));
        }

        SpecificationAdapter specificationAdapter = new SpecificationAdapter(serviceSpecifications);
        holder.specificationsRV.setAdapter(specificationAdapter);
        holder.specificationsRV.setLayoutManager(new LinearLayoutManager(context));

        holder.direction.setOnClickListener(view -> servicesDetailsFragment.openGoogleMaps(serviceAd.getLat(), serviceAd.getLng()));
        holder.call.setOnClickListener(view -> servicesDetailsFragment.call(serviceAd.getPhoneNumber()));
        holder.sms.setOnClickListener(view -> servicesDetailsFragment.sendSMS(serviceAd.getPhoneNumber()));
        holder.share.setOnClickListener(view -> servicesDetailsFragment.share(serviceAd.getId()));
        holder.reportAd.setOnClickListener(view -> servicesDetailsFragment.setReportAd(serviceAd.getId()));
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
//        if (servicesDetailsFragment.getOfferApplied(serviceAd.getId())){
//            holder.applyForJob.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob2.setText(context.getResources().getString(R.string.job_applied));
//            holder.applyForJob.setEnabled(false);
//            holder.applyForJob2.setEnabled(false);
//        }

        if (serviceAd.getIsFav().equals("true")){
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
                servicesDetailsFragment.addToFavourite(serviceAd.getId());
            }else {
                fav = false;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                servicesDetailsFragment.removeFromFavourite(serviceAd.getId());
            }
        });

        holder.next.setOnClickListener(view ->{
            if (position+1 != serviceAds.size()){
                servicesDetailsFragment.scrollToPosition(position+1);
            }
        });
        holder.previous.setOnClickListener(view -> {
            if (position!=0){
                servicesDetailsFragment.scrollToPosition(position-1);
            }
        });

        holder.backToPrevious.setOnClickListener(view -> servicesDetailsFragment.backToPrevious());
    }

    @Override
    public int getItemCount() {
        return serviceAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout descriptionLayout, locationLayout, directionsLayout;
        ImageView backToPrevious, descriptionArrow, locationArrow, locationImage, favourite, direction, share, img;
        TextView description, reportAd, descriptionLine, applyForJob2, applyForJob, title, companyName, location,
                location2, employmentType, workExperience, name, member;
        Boolean showDescription, showLocation;
        RecyclerView similarJobsRV;
        LinearLayout call, sms;
        ImageView next, previous;
        RecyclerView specificationsRV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite);
            backToPrevious = itemView.findViewById(R.id.back_arrow);

            next = itemView.findViewById(R.id.next);
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
