package com.hudhud.insouqapplication.Views.Main.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.Responses.Picture;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.Home.HomeRegistrationFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ImageBuisnessViewPagerAdapter extends PagerAdapter {

    private final  ArrayList<String> contents;
    private final BusinessChatDetailsActivity mainActivity;

    public ImageBuisnessViewPagerAdapter( ArrayList<String> pictures, BusinessChatDetailsActivity mainActivity) {
        this.contents = pictures;
        this.mainActivity = mainActivity;
    }

    public ImageBuisnessViewPagerAdapter(BusinessChatDetailsActivity mainActivity) {
        this.mainActivity = mainActivity;
        contents = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup  container, int position) {

        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_slider_item,container,false);
        container.addView(view);
        String pic = contents.get(position);


        ImageView image = view.findViewById(R.id.slider_image);

        String newPic;
        newPic = pic.replace("\\", "/");

        Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic).into(image);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
