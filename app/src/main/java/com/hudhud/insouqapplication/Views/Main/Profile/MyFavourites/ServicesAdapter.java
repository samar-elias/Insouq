package com.hudhud.insouqapplication.Views.Main.Profile.MyFavourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    MyFavouritesFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<ServiceAd> serviceAds;

    public ServicesAdapter(MyFavouritesFragment subCategoryFragment, ArrayList<ServiceAd> serviceAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.serviceAds = serviceAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View servicesView = inflater.inflate(R.layout.services_item_layout, parent, false);
        return new ViewHolder(servicesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceAd serviceAd = serviceAds.get(position);

        holder.type.setText(serviceAd.getTitle());
        holder.date.setText(serviceAd.getPostedDate());

        if (AppDefs.language.equals("ar")){
            holder.subCat.setText(serviceAd.getCategoryArName());
            holder.location.setText(context.getResources().getText(R.string.located)+" "+serviceAd.getArLocation());
        }else {
            holder.subCat.setText(serviceAd.getCategoryEnName());
            holder.location.setText(context.getResources().getText(R.string.located)+" "+serviceAd.getEnLocation());
        }

        if (serviceAd.getOtherServiceType().isEmpty()|| serviceAd.getOtherServiceType().equals("null")){
            if (AppDefs.language.equals("ar")){
                holder.subType.setText(serviceAd.getServiceTypeArName());
            }else {
                holder.subType.setText(serviceAd.getServiceTypeEnName());
            }
        }else {
            holder.subType.setText(serviceAd.getOtherServiceType());
        }

        if (serviceAd.getIsFav().equals("true")){
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
        }else {
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
        }
        holder.itemView.setOnClickListener(view -> subCategoryFragment.navigateToServicesDetails(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(serviceAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(serviceAd.getId());
            }
        });
        holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));

    }

    @Override
    public int getItemCount() {
        return serviceAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite;
        TextView type, location, date, subCat, subType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite);
            type = itemView.findViewById(R.id.service_type);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.posted_date);
            subCat = itemView.findViewById(R.id.ad_sub_category);
            subType = itemView.findViewById(R.id.ad_sub_type);
        }
    }
}
