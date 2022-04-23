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
import com.hudhud.insouqapplication.AppUtils.Responses.BusinessAd;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;

import java.util.ArrayList;

public class BusinessProductsAdapter extends RecyclerView.Adapter<BusinessProductsAdapter.ViewHolder> {

    ArrayList<BusinessAd> newAds;
    HomeFragment homeFragment;
    Context context;

    public BusinessProductsAdapter(ArrayList<BusinessAd> newAds, HomeFragment homeFragment) {
        this.newAds = newAds;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productsView = inflater.inflate(R.layout.new_motor_layout, parent, false);
        return new ViewHolder(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BusinessAd newAd = newAds.get(position);

        if (newAd.getCategoryEnName().equals("null")){
            holder.title.setText(newAd.getOtherCategoryNAme());
        }else {
            holder.title.setText(newAd.getCategoryEnName());
        }
        holder.loc.setText(newAd.getEnLocation());
        if (!newAd.getPrice().isEmpty()){
            holder.price.setText(newAd.getPrice()+ " AED");
        }
        String pic = newAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+pic).into(holder.image);

        holder.item.setOnClickListener(view -> homeFragment.navigateToBusinessDetails(position));
    }

    @Override
    public int getItemCount() {
        return newAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price, loc;
        MaterialCardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            loc = itemView.findViewById(R.id.kilo_year);
            item = itemView.findViewById(R.id.item);
        }
    }
}
