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
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class MotorProductsAdapter extends RecyclerView.Adapter<MotorProductsAdapter.ViewHolder> {

    ArrayList<UsedCarAd> newAds;
    HomeFragment homeFragment;
    Context context;

    public MotorProductsAdapter(ArrayList<UsedCarAd> newAds, HomeFragment homeFragment) {
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
        UsedCarAd newAd = newAds.get(position);

//        holder.title.setText(newAd.getEnMaker()+", "+newAd.getEnModel());
        if (newAd.getKiloMeters().equals("null")){
            if(newAd.getYear().equals("0")){
                holder.kiloYear.setText("Unknown");
            }else {
                holder.kiloYear.setText(newAd.getYear());
            }
        }else {
            holder.kiloYear.setText(newAd.getKiloMeters() + "KM");
        }
//        holder.title.setText(newAd.getTitle());
        if (!newAd.getPrice().isEmpty()){
            holder.price.setText(newAd.getPrice()+ " AED");
        }
        String pic = newAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+pic).into(holder.image);

        switch (newAd.getCategoryId()){
            case "2":
            case "8":
                if (AppDefs.language.equals("ar")){
                    holder.title.setText(newAd.getArMaker()+", "+newAd.getArModel());
                }else {
                    holder.title.setText(newAd.getEnMaker()+", "+newAd.getEnModel());
                }
                break;
            case "6":
            case "9":
                if (AppDefs.language.equals("ar")){
                    holder.title.setText(newAd.getSubCategoryArName());
                }else {
                    holder.title.setText(newAd.getSubCategoryEnName());
                }
                break;
            case "5":
                if (AppDefs.language.equals("ar")){
                    holder.title.setText(newAd.getCategoryArName());
                }else {
                    holder.title.setText(newAd.getCategoryEnName());
                }
                break;
            case "7":
                holder.title.setText(newAd.getTitle());
                break;
        }
        
        holder.item.setOnClickListener(view -> {
            homeFragment.navigateToMotorDetails(position);
        });
    }

    @Override
    public int getItemCount() {
        return newAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price, kiloYear;
        MaterialCardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            kiloYear = itemView.findViewById(R.id.kilo_year);
            item = itemView.findViewById(R.id.item);
        }
    }
}
