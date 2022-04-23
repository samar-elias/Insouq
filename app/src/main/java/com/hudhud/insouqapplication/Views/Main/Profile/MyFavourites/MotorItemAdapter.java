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
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class MotorItemAdapter extends RecyclerView.Adapter<MotorItemAdapter.ViewHolder> {

    MyFavouritesFragment subCategoryFragment;
    ArrayList<UsedCarAd> usedCarAds;
    String categoryId;
    Context context;
    boolean fav = false;

    public MotorItemAdapter(MyFavouritesFragment subCategoryFragment, ArrayList<UsedCarAd> usedCarAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.usedCarAds = usedCarAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View motorItemView = inflater.inflate(R.layout.motor_item_layout, parent, false);
        return new ViewHolder(motorItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsedCarAd usedCarAd = usedCarAds.get(position);

        holder.postDate.setText(usedCarAd.getPostDate());
        holder.title.setText(usedCarAd.getTitle());
        if (usedCarAd.getIsFav().equals("true")){
            fav = true;
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
        }else {
            fav = true;
            holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
        }
        holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
        String newPic = usedCarAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);
        if (AppDefs.language.equals("ar")){
            holder.location.setText(usedCarAd.getArLocation());
        }else {
            holder.location.setText(usedCarAd.getEnLocation());
        }
        switch (usedCarAd.getCategoryId()){
            case "2":
            case "8":
            case "9":
                holder.kilos.setText(usedCarAd.getKiloMeters()+" KM");
                break;
            case "5":
                if (AppDefs.language.equals("ar")){
                    holder.kilos.setText(usedCarAd.getArLength());
                }else {
                    holder.kilos.setText(usedCarAd.getEnLength());
                }
                break;
            case "7":
                if (AppDefs.language.equals("ar")){
                    holder.kilos.setText(usedCarAd.getArPartName());
                }else {
                    holder.kilos.setText(usedCarAd.getEnPartName());
                }
                break;
        }
        holder.price.setText(usedCarAd.getPrice()+" AED");
        holder.year.setText(usedCarAd.getYear());
//        String postDate = usedCarAd.getPostDate().substring(0, usedCarAd.getPostDate().indexOf("T"));
//        holder.postDate.setText(postDate);

        holder.itemView.setOnClickListener(v -> subCategoryFragment.navigateToMotorsDetails(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(usedCarAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(usedCarAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return usedCarAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite, image;
        TextView title, location, kilos, year, price, postDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite_motor);
            image = itemView.findViewById(R.id.motor_image);
            title = itemView.findViewById(R.id.motor_title);
            location = itemView.findViewById(R.id.ad_location);
            kilos = itemView.findViewById(R.id.kilos_amount);
            year = itemView.findViewById(R.id.motor_year);
            price = itemView.findViewById(R.id.motor_price);
            postDate = itemView.findViewById(R.id.posted_date);
        }
    }
}
