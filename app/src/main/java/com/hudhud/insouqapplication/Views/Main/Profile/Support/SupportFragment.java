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
import com.hudhud.insouqapplication.AppUtils.Responses.Question;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SupportFragment extends Fragment {

    ImageView backToPrevious, home, profile, chat, sellItem, list;
    NavController navController;
    MainActivity mainActivity;
    MediaPlayer mediaPlayer;
    RecyclerView questionsRV;
    ArrayList<Question> questions = new ArrayList<>();

    public SupportFragment() {
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
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getQuestions();
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
        backToPrevious = view.findViewById(R.id.back_arrow);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);
        questionsRV = view.findViewById(R.id.questions_RV);
    }

    private void onClick(){
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        list.setOnClickListener(view -> startActivity("profile"));
        profile.setOnClickListener(view -> startActivity("notifications"));
    }

    private void getQuestions(){
        questions.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest questionsRequest = new StringRequest(Request.Method.GET, Urls.StaticData_URL+"GetFAQ", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray questionsArray = new JSONArray(response);
                for (int i=0; i<questionsArray.length(); i++){
                    Question question = new Question();
                    JSONObject questionObj = questionsArray.getJSONObject(i);
                    question.setId(questionObj.getString("id"));
                    question.setQuestion(questionObj.getString("question"));
                    question.setAnswerImg(questionObj.getString("answerImg2"));
                    String img1 = questionObj.getString("answerImg").replace("\\", "/");
                    question.setAnswerImg2(img1);
                    question.setAnswerImg3(questionObj.getString("answerImg3"));
                    question.setArQuestion(questionObj.getString("ar_Question"));
                    question.setArAnswerImg(questionObj.getString("ar_AnswerImg2"));
                    String img2 = questionObj.getString("ar_AnswerImg").replace("\\", "/");
                    question.setArAnswerImg2(img2);
                    question.setArAnswerImg3(questionObj.getString("ar_AnswerImg3"));
                    question.setIsDeleted(questionObj.getString("isDeleted"));
                    questions.add(question);
                }
                setQuestionsRV();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            mainActivity.hideProgressDialog();
        });
        mainActivity.queue.add(questionsRequest);
    }

    private void setQuestionsRV(){
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(questions, this);
        questionsRV.setAdapter(questionsAdapter);
        questionsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
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