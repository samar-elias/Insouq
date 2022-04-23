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
import com.hudhud.insouqapplication.AppUtils.Responses.ElectronicAd;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.SubCategoryFragment;

import java.util.ArrayList;

public class ElectronicsAdapter extends RecyclerView.Adapter<ElectronicsAdapter.ViewHolder> {

    MyFavouritesFragment subCategoryFragment;
    Context context;
    boolean fav = false;
    ArrayList<ElectronicAd> electronicAds;

    public ElectronicsAdapter(MyFavouritesFragment subCategoryFragment, ArrayList<ElectronicAd> electronicAds) {
        this.subCategoryFragment = subCategoryFragment;
        this.electronicAds = electronicAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View electronicsView = inflater.inflate(R.layout.electronic_item_layout, parent, false);
        return new ViewHolder(electronicsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ElectronicAd electronicAd = electronicAds.get(position);

        String newPic = electronicAd.getMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);

        holder.title.setText(electronicAd.getTitle());
        holder.price.setText(electronicAd.getPrice()+" AED");
        holder.postedDate.setText(electronicAd.getPostedDate());

        if (AppDefs.language.equals("ar")){
            holder.location.setText(electronicAd.getArLocation());
        }else {
            holder.location.setText(electronicAd.getEnLocation());
        }

        holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));


        holder.itemView.setOnClickListener(view -> subCategoryFragment.navigateToElectronics(position));
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                fav = true;
                subCategoryFragment.addToFavourite(electronicAd.getId());
            }else {
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                fav = false;
                subCategoryFragment.removeFromFavourite(electronicAd.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return electronicAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favourite, image;
        TextView title, location, price, postedDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favourite = itemView.findViewById(R.id.favourite);
            image = itemView.findViewById(R.id.electronics_image);
            title = itemView.findViewById(R.id.electronics_title);
            location = itemView.findViewById(R.id.ad_location);
            price = itemView.findViewById(R.id.price);
            postedDate = itemView.findViewById(R.id.posted_since);
        }
    }
}
