package com.hudhud.insouqapplication.Views.Main.Home.SubCategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.City;
import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class SelectedLocationsAdapter extends RecyclerView.Adapter<SelectedLocationsAdapter.ViewHolder> {

    SubCategoryFragment subCategoryFragment;
    ArrayList<City> cities;
    Context context;

    public SelectedLocationsAdapter(SubCategoryFragment subCategoryFragment, ArrayList<City> cities) {
        this.subCategoryFragment = subCategoryFragment;
        this.cities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.selected_location_item, parent, false);
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

        holder.remove.setOnClickListener(view -> {
            subCategoryFragment.selectedCities.remove(city);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, remove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
