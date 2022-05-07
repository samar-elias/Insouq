package com.hudhud.insouqapplication.Views.Main.Profile.Support;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Question;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {

    ImageView backToPrevious, home, profile, chat, sellItem, list;
    NavController navController;
    MainActivity mainActivity;
    MediaPlayer mediaPlayer;
    TextView questionTitle;
    Question question;
    RecyclerView answersRV;

    public QuestionFragment() {
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
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        setData();
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
        
        question = QuestionFragmentArgs.fromBundle(getArguments()).getQuestion();
        
        backToPrevious = view.findViewById(R.id.back_arrow);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);
        questionTitle = view.findViewById(R.id.faq_title);
        answersRV = view.findViewById(R.id.answers_RV);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        list.setOnClickListener(view -> startActivity("profile"));
        profile.setOnClickListener(view -> startActivity("notifications"));
    }
    
    private void setData(){
        if (AppDefs.language.equals("ar")){
            questionTitle.setText(question.getArQuestion());
        }else {
            questionTitle.setText(question.getQuestion());
        }
        AnswersAdapter answersAdapter = new AnswersAdapter(question.getAnswers());
        answersRV.setAdapter(answersAdapter);
        answersRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    public void setVideo(String video, VideoView videoBG){
        Uri uri = Uri.parse(video);
        videoBG.setVideoURI(uri);
        MediaController mediaController = new MediaController(mainActivity);
        videoBG.setMediaController(mediaController);
        mediaController.setAnchorView(videoBG);
        videoBG.start();
        videoBG.setOnPreparedListener(mp -> {
            mediaPlayer = mp;
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}