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
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;

import java.util.ArrayList;

public class ElectronicsProductsAdapter extends RecyclerView.Adapter<ElectronicsProductsAdapter.ViewHolder> {

    ArrayList<ElectronicAd> newAds;
    HomeFragment homeFragment;
    Context context;

    public ElectronicsProductsAdapter(ArrayList<ElectronicAd> newAds, HomeFragment homeFragment) {
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
        ElectronicAd newAd = newAds.get(position);

        if (!newAd.getPrice().isEmpty()){
            holder.price.setText("AED "+newAd.getPrice());
        }else {
            holder.price.setText(context.getResources().getString(R.string.unknown));
        }
        if (AppDefs.language.equals("ar")){
            holder.loc.setText(newAd.getArLocation());
            holder.title.setText(newAd.getSubCatArName());
        }else {
            holder.loc.setText(newAd.getEnLocation());
            holder.title.setText(newAd.getSubCatEnName());
        }
        String pic = newAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+pic).into(holder.image);

        holder.item.setOnClickListener(view -> homeFragment.navigateToElectronicsDetails(position, true));
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
