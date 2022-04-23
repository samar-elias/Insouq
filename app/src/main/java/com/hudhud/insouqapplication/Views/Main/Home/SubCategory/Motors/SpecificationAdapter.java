package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors;

import android.content.Context;
import android.graphics.Typeface;
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

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.ViewHolder> {

    Context context;
    ArrayList<specificationModel> motorSpecifications;

    public SpecificationAdapter(ArrayList<specificationModel> motorSpecifications) {
        this.motorSpecifications = motorSpecifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View motorSpecificationsView = inflater.inflate(R.layout.motor_specification_layout, parent, false);
        return new ViewHolder(motorSpecificationsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        specificationModel motorSpecification = motorSpecifications.get(position);
        holder.specificationTitle.setText(motorSpecification.getSpecificationTitle());
        holder.specificationValue.setText(motorSpecification.getSpecificationValue());
        if (motorSpecification.getImage()!=0){
            Glide.with(context).load(motorSpecification.getImage()).into(holder.specificationIcon);
        }
    }

    @Override
    public int getItemCount() {
        return motorSpecifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView specificationTitle, specificationValue;
        ImageView specificationIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            specificationIcon = itemView.findViewById(R.id.specification_icon);
            specificationTitle = itemView.findViewById(R.id.specification_title);
            specificationValue = itemView.findViewById(R.id.specification_value);

        }
    }
}
