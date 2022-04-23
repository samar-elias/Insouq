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
import com.hudhud.insouqapplication.AppUtils.Responses.NewAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    ArrayList<NewAd> newAds;
    HomeFragment homeFragment;
    String categoryName;
    Context context;

    public ProductsAdapter(SubCategoryFragment subCategoryFragment, String categoryName) {
        this.subCategoryFragment = subCategoryFragment;
        this.categoryName = categoryName;
    }

    public ProductsAdapter(ArrayList<NewAd> newAds, HomeFragment homeFragment, String typeId) {
        this.newAds = newAds;
        this.homeFragment = homeFragment;
        categoryName = typeId;
    }

    public ProductsAdapter(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public ProductsAdapter() {
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
        NewAd newAd = newAds.get(position);

        holder.title.setText(newAd.getTitle());
        if (!newAd.getPrice().isEmpty()){
            holder.price.setText(newAd.getPrice()+ " AED");
        }
        String pic = newAd.getImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+pic).into(holder.image);
        switch (categoryName){
            case "3":
            case "4":
                Glide.with(context).load(R.drawable.job_logo).into(holder.image);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return newAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
        }
    }
}
