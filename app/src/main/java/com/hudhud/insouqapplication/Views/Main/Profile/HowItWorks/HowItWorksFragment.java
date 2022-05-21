package com.hudhud.insouqapplication.Views.Main.Profile.HowItWorks;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

public class HowItWorksFragment extends Fragment {

    NavController navController;
    MainActivity mainActivity;
    ImageView home, profile, chat, sellItem, list, backToPrevious;
    VideoView videoBG;
    MediaPlayer mediaPlayer;

    public HowItWorksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_how_it_works, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
//        setVideo();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    private void initViews(View view){
        navController = Navigation.findNavController(view);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);
        sellItem = view.findViewById(R.id.sell_item);
        backToPrevious = view.findViewById(R.id.back_arrow);
//        videoBG = view.findViewById(R.id.video);
    }

    private void onClick(){
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
    }

    private void setVideo(){
        Uri uri = Uri.parse("android.resource://"+ mainActivity.getPackageName() +"/"+R.raw.insouq_video);
        videoBG.setVideoURI(uri);
        MediaController mediaController = new MediaController(mainActivity);
        videoBG.setMediaController(mediaController);
        mediaController.setAnchorView(videoBG);
        videoBG.start();
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }
}