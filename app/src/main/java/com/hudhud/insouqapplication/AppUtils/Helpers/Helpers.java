package com.hudhud.insouqapplication.AppUtils.Helpers;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hudhud.insouqapplication.R;
import com.pixplicity.sharp.Sharp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Helpers {

    private static OkHttpClient httpClient;

    public static void setSliderTimer(int delay, ViewPager viewPager, PagerAdapter pagerAdapter){
        Handler handler = new Handler();
        final Runnable[] runnable = new Runnable[1];

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int pagerIndex = viewPager.getCurrentItem();
                pagerIndex++;
                if (pagerIndex >= pagerAdapter.getCount()){
                    pagerIndex = 0;
                }
                pagerAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(pagerIndex);
                pagerAdapter.notifyDataSetChanged();

                runnable[0] = this;
                handler.postDelayed(runnable[0],delay);
            }
        },delay);
    }

    public static void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        // here we are making HTTP call to fetch data from URL.
        okhttp3.Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // we are adding a default image if we gets any error.
                target.setImageResource(R.drawable.insouq_logo);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // sharp is a library which will load stream which we generated
                // from url in our target imageview.
                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                stream.close();
            }
        });
    }

    public static String getLocale(){
        return Locale.getDefault().getLanguage();
    }

}
