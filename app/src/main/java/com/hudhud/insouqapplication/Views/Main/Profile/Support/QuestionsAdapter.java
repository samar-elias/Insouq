package com.hudhud.insouqapplication.Views.Main.Profile.Support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Question;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Profile.MyAds.MyNumbersAdsAdapter;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    ArrayList<Question> questions;
    SupportFragment supportFragment;
    Context context;
    boolean flag = false;

    public QuestionsAdapter(ArrayList<Question> questions, SupportFragment supportFragment) {
        this.questions = questions;
        this.supportFragment = supportFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);

        if (AppDefs.language.equals("ar")){
            holder.questionTitle.setText(question.getArQuestion());
        }else {
            holder.questionTitle.setText(question.getQuestion());
        }

        holder.itemView.setOnClickListener(view -> {
//            question.setVisible(!question.isVisible());
//            notifyDataSetChanged();
            supportFragment.navigateToQuestion(question);
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle, answerText, line;
        ImageView answerImage, viewAnswer;
        VideoView answerVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTitle = itemView.findViewById(R.id.faq_title);
            answerText = itemView.findViewById(R.id.text1);
            line = itemView.findViewById(R.id.line);
            answerImage = itemView.findViewById(R.id.image1);
            viewAnswer = itemView.findViewById(R.id.add_icon);
            answerVideo = itemView.findViewById(R.id.video);
        }
    }
}
