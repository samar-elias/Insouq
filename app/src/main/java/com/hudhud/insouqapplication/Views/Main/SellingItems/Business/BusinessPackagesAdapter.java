package com.hudhud.insouqapplication.Views.Main.SellingItems.Business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.Responses.PackageFS;
import com.hudhud.insouqapplication.AppUtils.Responses.SubCategory;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.HomeFragment;

import java.util.ArrayList;

public class BusinessPackagesAdapter extends RecyclerView.Adapter<BusinessPackagesAdapter.ViewHolder> {

    PostBusinessAdFragment businessAdFragment;
    ArrayList<PackageFS> packages;
    int selectedPosition;
    Context context;

    public BusinessPackagesAdapter(PostBusinessAdFragment businessAdFragment, ArrayList<PackageFS> packages, int selectedPosition) {
        this.businessAdFragment = businessAdFragment;
        this.packages = packages;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View subCategoriesView = inflater.inflate(R.layout.package_layout, parent, false);
        return new ViewHolder(subCategoriesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PackageFS packageFS = packages.get(position);

        holder.price.setText(packageFS.getPrice());
        holder.checkBox.setText(context.getResources().getString(R.string.features_your_ad_for)+" "+packageFS.getDays()+" days");

        switch (position){
            case 0:
                Glide.with(context).load(R.drawable.plus).into(holder.packageImg);
                break;
            case 1:
                Glide.with(context).load(R.drawable.pro).into(holder.packageImg);
                break;
            case 2:
                Glide.with(context).load(R.drawable.elite).into(holder.packageImg);
                break;
        }

        holder.checkBox.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });

        if (selectedPosition==position){
            holder.checkBox.setChecked(true);
            businessAdFragment.freeCB.setChecked(false);
            businessAdFragment.packageId = packageFS.getId();
        }else {
            holder.checkBox.setChecked(false);
//            businessAdFragment.packageId = packageFS.getId();
        }
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price;
        CheckBox checkBox;
        ConstraintLayout layout;
        ImageView packageImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            checkBox = itemView.findViewById(R.id.cb);
            layout = itemView.findViewById(R.id.layout);
            packageImg = itemView.findViewById(R.id.package_img);
        }
    }
}
