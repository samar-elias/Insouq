package com.hudhud.insouqapplication.Views.Main.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

public class ChatFragment extends Fragment {

    ImageView home, chat, sellAnItem, profile, notifications;
    NavController navController;
    MainActivity mainActivity;
    RecyclerView activeChatsRV, archiveChatsRV;
    TextView editChats, deleteChats;
    FirebaseFirestore db;

    public ChatFragment() {
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
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
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

        db = FirebaseFirestore.getInstance();

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellAnItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
        activeChatsRV = view.findViewById(R.id.active_chats_RV);
        archiveChatsRV = view.findViewById(R.id.archive_chats_RV);
        editChats = view.findViewById(R.id.edit_chats);
        deleteChats = view.findViewById(R.id.delete_chats);

        ChatItemAdapter chatItemAdapter = new ChatItemAdapter(this, false);
        activeChatsRV.setAdapter(chatItemAdapter);
        archiveChatsRV.setAdapter(chatItemAdapter);
        activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    private void onClick(){
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellAnItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));
        editChats.setOnClickListener(view -> {
            editChats.setVisibility(View.INVISIBLE);
            deleteChats.setVisibility(View.VISIBLE);
            ChatItemAdapter chatItemAdapter = new ChatItemAdapter(this, true);
            activeChatsRV.setAdapter(chatItemAdapter);
            archiveChatsRV.setAdapter(chatItemAdapter);
            activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
            archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        });
        deleteChats.setOnClickListener(view -> {
            editChats.setVisibility(View.VISIBLE);
            deleteChats.setVisibility(View.GONE);
            ChatItemAdapter chatItemAdapter = new ChatItemAdapter(this, false);
            activeChatsRV.setAdapter(chatItemAdapter);
            archiveChatsRV.setAdapter(chatItemAdapter);
            activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
            archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void showOptions(){
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.delete_chat_item_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton cancel = myAdsOptionsAlertView.findViewById(R.id.cancel);
        MaterialButton delete = myAdsOptionsAlertView.findViewById(R.id.delete);

        cancel.setOnClickListener(view -> myAdsOptionsAlertBuilder.dismiss());
        delete.setOnClickListener(view -> myAdsOptionsAlertBuilder.dismiss());
    }
}