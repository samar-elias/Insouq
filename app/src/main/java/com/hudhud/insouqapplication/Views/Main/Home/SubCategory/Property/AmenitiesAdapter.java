package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.ViewHolder> {
    ArrayList<AmenitiesModel> amenities;
    Context context;

    public AmenitiesAdapter(ArrayList<AmenitiesModel> amenities) {
//        if (amenities.size()%2 != 0){
//            amenities.add("");
//        }
        this.amenities = amenities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View motorSpecificationsView = inflater.inflate(R.layout.amenity_layout, parent, false);
        return new ViewHolder(motorSpecificationsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AmenitiesModel amenity = amenities.get(position);
        holder.amenityTitle.setText(amenity.getName());
        Glide.with(context).load(context.getResources().getDrawable(amenity.getImage())).into(holder.amenityImage);
    }

    @Override
    public int getItemCount() {
        return amenities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amenityTitle;
        ImageView amenityImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amenityTitle = itemView.findViewById(R.id.amenity_title);
            amenityImage = itemView.findViewById(R.id.amenity_image);
        }
    }
}
