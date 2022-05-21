package com.hudhud.insouqapplication.Views.Main.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.NewAd;
import com.hudhud.insouqapplication.AppUtils.Responses.ServiceAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class ServiceProductsAdapter extends RecyclerView.Adapter<ServiceProductsAdapter.ViewHolder> {

    ArrayList<ServiceAd> newAds;
    HomeFragment homeFragment;
    Context context;

    public ServiceProductsAdapter(ArrayList<ServiceAd> newAds, HomeFragment homeFragment) {
        this.newAds = newAds;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productsView = inflater.inflate(R.layout.adv_layout, parent, false);
        return new ViewHolder(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceAd newAd = newAds.get(position);

        if (!newAd.getServiceTypeEnName().equals("null")){
            if (AppDefs.language.equals("ar")){
                holder.title.setText(newAd.getServiceTypeArName());
            }else {
                holder.title.setText(newAd.getServiceTypeEnName());
            }
        } else if (!newAd.getOtherServiceType().equals("null")){
            holder.title.setText(newAd.getOtherServiceType());
        }else {
            holder.title.setText(context.getResources().getString(R.string.other));
        }
        if (AppDefs.language.equals("ar")){
            holder.price.setText(newAd.getArLocation());
        }else {
            holder.price.setText(newAd.getEnLocation());
        }
        Glide.with(context).load(R.drawable.job_logo).into(holder.image);

        holder.item.setOnClickListener(view -> homeFragment.navigateToServicesDetails(position));
    }

    @Override
    public int getItemCount() {
        return newAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price;
        MaterialCardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            item = itemView.findViewById(R.id.item);
        }
    }
}
