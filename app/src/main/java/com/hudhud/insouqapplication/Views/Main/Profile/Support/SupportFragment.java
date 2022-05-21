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
import com.hudhud.insouqapplication.AppUtils.Responses.Answer;
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
        profile = view.findViewById(R.id.profile);
        list = view.findViewById(R.id.notification);
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

    public void navigateToQuestion(Question question){
        navController.navigate(SupportFragmentDirections.actionSupportFragmentToQuestionFragment(question));
    }

    private void getQuestions(){
        questions.clear();
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest questionsRequest = new StringRequest(Request.Method.GET, Urls.StaticData_URL+"GetFAQ", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONObject questionObject = new JSONObject(response);
                JSONArray questionsArray = questionObject.getJSONArray("results");
                for (int i=0; i<questionsArray.length(); i++){
                    Question question = new Question();
                    JSONObject questionObj = questionsArray.getJSONObject(i);
                    question.setQuestion(questionObj.getString("Question_En"));
                    question.setArQuestion(questionObj.getString("Question_Ar"));
                    JSONArray answersArray = questionObj.getJSONArray("Answers");
                    ArrayList<Answer> answers = new ArrayList<>();
                    for (int j=0; j<answersArray.length(); j++){
                        JSONObject answerObj = answersArray.getJSONObject(j);
                        Answer answer = new Answer();
                        answer.setArAnswer(answerObj.getString("AnsAR"));
                        answer.setEnAnswer(answerObj.getString("AnsEN"));
                        answers.add(answer);
                    }
                    question.setAnswers(answers);
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
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }
}