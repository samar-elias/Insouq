package com.hudhud.insouqapplication.Views.Main.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudhud.insouqapplication.R;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ViewHolder> {

    ChatFragment chatFragment;
    boolean edit;

    public ChatItemAdapter(ChatFragment chatFragment, boolean edit) {
        this.chatFragment = chatFragment;
        this.edit = edit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View productsView = inflater.inflate(R.layout.chat_item_layout, parent, false);
        return new ViewHolder(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (edit){
            holder.checked.setVisibility(View.VISIBLE);
        }else {
            holder.checked.setVisibility(View.GONE);
        }
        holder.options.setOnClickListener(view -> chatFragment.showOptions());
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checked;
        ImageView options;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checked = itemView.findViewById(R.id.check);
            options = itemView.findViewById(R.id.options);
        }
    }
}
