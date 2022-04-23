package com.hudhud.insouqapplication.Views.Main.Home.Notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.Responses.Notification;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    NotificationsFragment notificationsFragment;
    ArrayList<Notification> notifications;
    Context context;

    public NotificationsAdapter(NotificationsFragment notificationsFragment, ArrayList<Notification> notifications) {
        this.notificationsFragment = notificationsFragment;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View activeOrdersView = inflater.inflate(R.layout.notification_item_layout, parent, false);
        return new ViewHolder(activeOrdersView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        String newPic = notification.getImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);
//        String date =
//        holder.postDate.setText(notification.getPostedDate());
        holder.title.setText(notification.getTitle());
//        holder.category.setText(notification.getCategory());

        holder.itemView.setOnClickListener(view -> notificationsFragment.navigateToMotors());
        holder.close.setOnClickListener(view -> notificationsFragment.deleteNotification(notification.getAdId()));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, close;
        TextView title, category, postDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            close = itemView.findViewById(R.id.close);
            image = itemView.findViewById(R.id.notification_image);
            title = itemView.findViewById(R.id.notification_title);
            category = itemView.findViewById(R.id.notification_type);
            postDate = itemView.findViewById(R.id.notification_time);
        }
    }
}
