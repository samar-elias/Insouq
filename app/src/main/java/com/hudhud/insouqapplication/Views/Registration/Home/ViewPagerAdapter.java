package com.hudhud.insouqapplication.Views.Registration.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.hudhud.insouqapplication.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private final ArrayList<String> contents;
    private final HomeRegistrationFragment homeRegistrationFragment;

    public ViewPagerAdapter(ArrayList<String> contents, HomeRegistrationFragment homeRegistrationFragment) {
        this.contents = contents;
        this.homeRegistrationFragment = homeRegistrationFragment;
    }

    public ViewPagerAdapter(HomeRegistrationFragment homeRegistrationFragment) {
        this.homeRegistrationFragment = homeRegistrationFragment;
        contents = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup  container, int position) {

        LayoutInflater inflater = (LayoutInflater) homeRegistrationFragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_into_slider,container,false);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
