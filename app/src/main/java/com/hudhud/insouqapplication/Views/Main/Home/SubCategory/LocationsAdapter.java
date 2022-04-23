package com.hudhud.insouqapplication.Views.Main.Home.SubCategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.City;
import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    ArrayList<City> cities;
    Context context;

    public LocationsAdapter(SubCategoryFragment subCategoryFragment, ArrayList<City> cities) {
        this.subCategoryFragment = subCategoryFragment;
        this.cities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.location_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        City city = cities.get(position);

        if (AppDefs.language.equals("ar")){
            holder.location.setText(city.getTitleAr());
        }else {
            holder.location.setText(city.getTitleEn());
        }

        holder.location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    subCategoryFragment.selectedCities.add(city);
                }else {
                    subCategoryFragment.selectedCities.remove(city);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
        }
    }
}
