package com.hudhud.insouqapplication.Views.Main.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Message;
import com.hudhud.insouqapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;
    ChatDetailesActivity chatDetailesActivity;



    FirebaseRemoteConfig remoteConfig;

    public MessagesAdapter(ChatDetailesActivity chatDetailesActivity ,Context context, ArrayList<Message> messages) {
        this.chatDetailesActivity = chatDetailesActivity;
        remoteConfig = FirebaseRemoteConfig.getInstance();
        this.context = context;
        this.messages = messages;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(message.getSender().equals(Integer.valueOf(AppDefs.user.getId()))) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);




        if(holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder)holder;
            if(message.getFiles() !=null) {
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.message.setVisibility(View.GONE);

                Glide.with(context)
                        .load(message.getFiles().get(0))
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.image);
            }else if (message.getLocURL() != null){
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.message.setVisibility(View.GONE);
                Glide.with(context)
                        .load(message.getLocURL().get(1))
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.image);
                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chatDetailesActivity.showLocation(message.getLocURL().get(0));



                    }
                });
            }else {
                viewHolder.image.setVisibility(View.GONE);
                viewHolder.message.setVisibility(View.VISIBLE);
                viewHolder.message.setText(message.getMessage());

            }


            try {
                String originalString = message.getMessageTime();
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                String newstr = new SimpleDateFormat("H:mm").format(date);
                viewHolder.tv_time.setText(String.valueOf(newstr));
            }
            catch (ParseException e) {
                //Handle exception here
                e.printStackTrace();

            }
            if (message.getSeen() == 1){
                viewHolder.status.setText("Seen");
            }else {
                viewHolder.status.setText("Deliverd");
            }





        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            if(message.getFiles() !=null) {
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.message.setVisibility(View.GONE);
                Glide.with(context)
                        .load(message.getFiles().get(0))
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.image);
            }else if (message.getLocURL() != null){
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.message.setVisibility(View.GONE);
                Glide.with(context)
                        .load(message.getLocURL().get(1))
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.image);
                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chatDetailesActivity.showLocation(message.getLocURL().get(0));
                    }
                });

            }else {
                viewHolder.image.setVisibility(View.GONE);
                viewHolder.message.setVisibility(View.VISIBLE);
                viewHolder.message.setText(message.getMessage());

            }
            try {
                String originalString = message.getMessageTime();
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                String newstr = new SimpleDateFormat("H:mm").format(date);
                viewHolder.tv_time.setText(String.valueOf(newstr));
            }
            catch (ParseException e) {
                //Handle exception here
                e.printStackTrace();

            }
            viewHolder.status.setVisibility(View.GONE);
          /*  if (message.getSeen() == 1){
                viewHolder.status.setText("Seen");
            }else {
                viewHolder.status.setText("Deliverd");
            }*/



        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        TextView message,tv_time,status;
        ImageView image;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            image=itemView.findViewById(R.id.image);
            tv_time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView message,tv_time,status;
        ImageView image;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message);
            image=itemView.findViewById(R.id.image);
            tv_time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);

        }
    }


}
