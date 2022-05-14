package com.hudhud.insouqapplication.Views.Main.Chat;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Chats;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ChatItemAdapter extends RecyclerView.Adapter<ChatItemAdapter.ViewHolder> {

    ChatFragment chatFragment;
    boolean edit;
    Context context;
    ArrayList<Chats> chats;


    // Define listener member variable
    private static OnRecyclerViewItemClickListener mListener;

    // Define the listener interface
    public interface OnRecyclerViewItemClickListener {

        void onItemCheckBoxChecked(boolean isChecked, int position,String key);
    }

    // Define the method that allows the parent activity or fragment to define the listener.
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mListener = listener;
    }
    public ChatItemAdapter(ChatFragment chatFragment, boolean edit,Context context, ArrayList<Chats> chats) {
        this.chatFragment = chatFragment;
        this.edit = edit;
        this.context = context;
        this.chats=chats;

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
        Chats chat = chats.get(position);
        if (edit){
            holder.checked.setVisibility(View.VISIBLE);
        }else {
            holder.checked.setVisibility(View.GONE);
        }
        if(chat.getOwnerUserId().equals(Integer.valueOf(AppDefs.user.getId()))) {
            holder.dec.setText(chat.getCasomerUserName());

        }else {
            holder.dec.setText(chat.getFirstName()+" "+chat.getLastName());
        }


        holder.last_massage.setText(chat.getLastMessage());
      //  DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date);

       /* try {
            String originalString = chat.getLastUpdate();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            String newstr = new SimpleDateFormat("H:mm").format(date);
            holder.time.setText(String.valueOf(newstr));
        }
        catch (ParseException e) {
            //Handle exception here
            e.printStackTrace();

    }*/
        String originalString = chat.getLastUpdate();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String timeAgo=covertTimeToText(newstr);
        holder.time.setText(String.valueOf(timeAgo));

        String newPic = chat.getAdMainImage().replace("\\", "/");
        Glide.with(context).load(Urls.IMAGE_URL+newPic).into(holder.image);

        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (((CheckBox) v).isChecked()) {
                    holder.checked.setChecked(false);
                } else {
                    holder.checked.setChecked(true);
                }*/
                // Inform to Activity or the Fragment where the RecyclerView reside.
                mListener.onItemCheckBoxChecked(((CheckBox) v).isChecked(), holder.getLayoutPosition(),chat.getChatId());

            }
        });

        holder.options.setOnClickListener(view -> chatFragment.showOptions(chat.getChatId(),chat.getStatus(),chat.getWhoArchive(),chat.getCustumerUserId(),chat.getOwnerUserId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailesActivity.class);
                intent.putExtra("userId", String.valueOf(chat.getOwnerUserId()));
                intent.putExtra("chatId", chat.getChatId());
                intent.putExtra("adsId",String.valueOf(chat.getAdId()));
                intent.putExtra("type",String.valueOf(chat.getType()));



                //intent.putParcelableArrayListExtra("Message",  user.getMessage());
                 intent.putExtra("image", chat.getAdMainImage());
                intent.putExtra("firstName",chat.getFirstName()+" "+chat.getLastName());
                intent.putExtra("userImage",chat.getUserImage());
                intent.putExtra("castomer_name",chat.getCasomerUserName());


                intent.putExtra("description",chat.getDescription());
                intent.putExtra("title",chat.getTitle());
                intent.putExtra("price",chat.getPrice());

              //  intent.putExtra("OwnerUserId", user.getOwnerUserId());

                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checked;
        ImageView options;
        RoundedImageView image;
        TextView last_massage,dec;
        TextView time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checked = itemView.findViewById(R.id.check);
            options = itemView.findViewById(R.id.options);
            image=itemView.findViewById(R.id.image);
            last_massage=itemView.findViewById(R.id.lastmassage);
            dec=itemView.findViewById(R.id.description);
            time=itemView.findViewById(R.id.time);

        }

    }
    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes "+suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day+" Days "+suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }


}

