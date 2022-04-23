package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.ViewHolder> {

    ArrayList<String> services;

    public ServicesListAdapter(ArrayList<String> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View servicesView = inflater.inflate(R.layout.service_item_layout, parent, false);
        return new ViewHolder(servicesView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String service = services.get(position);
        holder.serviceTitle.setText(service);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceTitle = itemView.findViewById(R.id.services_title);
        }
    }
}
