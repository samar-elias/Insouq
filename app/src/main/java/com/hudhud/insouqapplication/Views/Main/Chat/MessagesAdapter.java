package com.hudhud.insouqapplication.Views.Main.Chat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Chats;
import com.hudhud.insouqapplication.AppUtils.Responses.Message;
import com.hudhud.insouqapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messages;

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVE = 2;
    ChatDetailesActivity chatDetailesActivity;
    List<String> type = new ArrayList<>();


    FirebaseRemoteConfig remoteConfig;
    Boolean isReciver = true;
    String image;

    public MessagesAdapter(ChatDetailesActivity chatDetailesActivity, Context context, ArrayList<Message> messages,String image) {
        this.chatDetailesActivity = chatDetailesActivity;
        remoteConfig = FirebaseRemoteConfig.getInstance();
        this.context = context;
        this.messages = messages;
        this.image=image;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receive, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSender().equals(Integer.valueOf(AppDefs.user.getId()))) {
            type.add("sender");
            Log.d("typeuser", "sender");
            return ITEM_SENT;
        } else {
            type.add("reciverd");
            Log.d("typeuser", "reciverd");
            return ITEM_RECEIVE;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);


        if (holder.getClass() == SentViewHolder.class) {
            SentViewHolder viewHolder = (SentViewHolder) holder;


            if (message.getFiles() != null) {
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.message.setVisibility(View.GONE);

                Glide.with(context)
                        .load(message.getFiles().get(0))
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.image);
            } else if (message.getLocURL() != null) {
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
            } else {
                viewHolder.image.setVisibility(View.GONE);
                viewHolder.message.setVisibility(View.VISIBLE);
                viewHolder.message.setText(message.getMessage());
                viewHolder.liner.setBackgroundResource(R.drawable.sent_drawable);


            }


            try {
                String originalString = message.getMessageTime();
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                String newstr = new SimpleDateFormat("H:mm").format(date);
                viewHolder.tv_time.setText(String.valueOf(newstr));
            } catch (ParseException e) {
                //Handle exception here
                e.printStackTrace();

            }



            for (int n = 1; n <= type.size() - 1; n++) {

                if (type.get(n).equals("sender") && type.get(n - 1).equals("reciverd")) {
                    // Log.d("typeuser",type.toString());4
                    viewHolder.status.setVisibility(View.VISIBLE);
                    if (message.getSeen() == 1) {
                        viewHolder.status.setText("Seen");
                    } else {
                        viewHolder.status.setText("Deliverd");
                    }


                } else {
                    viewHolder.status.setVisibility(View.GONE);
                }

                //System.out.println(n);

            }

            if (position == messages.size()-1){
                viewHolder.status.setVisibility(View.VISIBLE);
                if (message.getSeen() == 1) {
                    viewHolder.status.setText("Seen");
                } else {
                    viewHolder.status.setText("Deliverd");
                }
            }


        } else {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;

            if (message.getFiles() != null) {
                viewHolder.image.setVisibility(View.VISIBLE);
                viewHolder.message.setVisibility(View.GONE);
                Glide.with(context)
                        .load(message.getFiles().get(0))
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.image);
            } else if (message.getLocURL() != null) {
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

            } else {
                viewHolder.image.setVisibility(View.GONE);
                viewHolder.message.setVisibility(View.VISIBLE);
                viewHolder.message.setText(message.getMessage());
                viewHolder.liner.setBackgroundResource(R.drawable.receive_drawable);


            }
            try {
                String originalString = message.getMessageTime();
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                String newstr = new SimpleDateFormat("H:mm").format(date);
                viewHolder.tv_time.setText(String.valueOf(newstr));
            } catch (ParseException e) {
                //Handle exception here
                e.printStackTrace();

            }
            viewHolder.status.setVisibility(View.GONE);
          /*  if (message.getSeen() == 1){
                viewHolder.status.setText("Seen");
            }else {
                viewHolder.status.setText("Deliverd");
            }*/

            for (int n = 1; n <= type.size() - 1; n++) {

                if (type.get(n).equals("reciverd") && type.get(n - 1).equals("sender")) {
                    // Log.d("typeuser",type.toString());4
                    viewHolder.cardView.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(image)
                            .placeholder(R.drawable.placeholder)
                            .into(viewHolder.image_profile);

                } else {
                    viewHolder.cardView.setVisibility(View.GONE);

                }
                //System.out.println(n);

            }

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {

        TextView message, tv_time, status;
        ImageView image;
        LinearLayout liner;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            image = itemView.findViewById(R.id.image);
            tv_time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            liner = itemView.findViewById(R.id.linear);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView message, tv_time, status;
        ImageView image,image_profile;
        CardView cardView;
        LinearLayout liner;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            image = itemView.findViewById(R.id.image);
            tv_time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.card);
            liner = itemView.findViewById(R.id.linear);
            image_profile=itemView.findViewById(R.id.image_profile);

        }
    }


}
