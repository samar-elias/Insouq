package com.hudhud.insouqapplication.Views.Main.Home.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.AppUtils.Responses.Search;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.Adapters.MotorProductsAdapter;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    SearchFragment searchFragment;
   ArrayList<Search> searches;
   Context context;

    public SearchAdapter(SearchFragment searchFragment, ArrayList<Search> searches) {
        this.searchFragment = searchFragment;
        this.searches = searches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productsView = inflater.inflate(R.layout.search_item_layout, parent, false);
        return new ViewHolder(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
