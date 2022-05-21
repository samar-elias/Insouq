package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors;

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

import java.util.ArrayList;

public class MotorsImageViewPagerAdapter extends PagerAdapter {

    private final ArrayList<Picture> contents;
    private final MainActivity mainActivity;
    MotorDetailsFragment motorDetailsFragment;
    boolean isBase;

    public MotorsImageViewPagerAdapter(ArrayList<Picture> contents, MainActivity mainActivity, MotorDetailsFragment motorDetailsFragment, boolean isBase) {
        this.contents = contents;
        this.mainActivity = mainActivity;
        this.motorDetailsFragment = motorDetailsFragment;
        this.isBase = isBase;
    }

    public MotorsImageViewPagerAdapter(MainActivity mainActivity) {
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
        Picture pic = contents.get(position);

        ImageView image = view.findViewById(R.id.slider_image);

        String newPic = pic.getImageURL().replace("\\", "/");

        Glide.with(mainActivity).load(Urls.IMAGE_URL+newPic).into(image);

        if (isBase){
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        image.setOnClickListener(view1 -> {
            if (isBase){
                motorDetailsFragment.initSlider(contents);
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
