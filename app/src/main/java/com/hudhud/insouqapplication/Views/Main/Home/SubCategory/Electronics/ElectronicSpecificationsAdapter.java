package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Electronics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class ElectronicSpecificationsAdapter extends RecyclerView.Adapter<ElectronicSpecificationsAdapter.ViewHolder> {

    ArrayList<ElectronicSpecificationModel> electronicSpecifications;

    public ElectronicSpecificationsAdapter(ArrayList<ElectronicSpecificationModel> electronicSpecifications) {
        this.electronicSpecifications = electronicSpecifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View electronicsSpecificationsView = inflater.inflate(R.layout.electronics_specification_layout, parent, false);
        return new ViewHolder(electronicsSpecificationsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ElectronicSpecificationModel electronicSpecification = electronicSpecifications.get(position);
        holder.specificationTitle.setText(electronicSpecification.getSpecificationTitle());
        holder.specificationValue.setText(electronicSpecification.getSpecificationValue());
    }

    @Override
    public int getItemCount() {
        return electronicSpecifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView specificationTitle, specificationValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            specificationTitle = itemView.findViewById(R.id.specification_title);
            specificationValue = itemView.findViewById(R.id.specification_value);

        }
    }
}
