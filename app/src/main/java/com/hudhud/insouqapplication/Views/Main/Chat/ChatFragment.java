package com.hudhud.insouqapplication.Views.Main.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Constant;
import com.hudhud.insouqapplication.AppUtils.Helpers.FirebaseManger;
import com.hudhud.insouqapplication.AppUtils.Responses.Chats;
import com.hudhud.insouqapplication.AppUtils.Responses.Message;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ChatFragment extends Fragment {

    ImageView home, chat, sellAnItem, profile, notifications;
    NavController navController;
    MainActivity mainActivity;
    RecyclerView activeChatsRV, archiveChatsRV;
    TextView deleteChats;
    FirebaseFirestore db;
    ArrayList<Chats> chats;
    ArrayList<Chats> archifechats;

    ChatItemAdapter usersAdapter;
    ChatItemAdapter archiveAdapter;
    FirebaseManger firebaseManger;
    Boolean isDelete = false;
    ArrayList<String> chatsKey;

    Boolean isfound = false;

    SearchView searchView;

    public ChatFragment() {
        // Required empty public constructor
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
        firebaseManger = FirebaseManger.getInstance();
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

    private void initViews(View view) {
        navController = Navigation.findNavController(view);

        db = FirebaseFirestore.getInstance();
        searchView = view.findViewById(R.id.searchView);
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellAnItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
        activeChatsRV = view.findViewById(R.id.active_chats_RV);
        archiveChatsRV = view.findViewById(R.id.archive_chats_RV);

        deleteChats = view.findViewById(R.id.delete_chats);

        chats = new ArrayList<>();
        archifechats = new ArrayList<>();
        chatsKey = new ArrayList<String>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        usersAdapter = new ChatItemAdapter(this, false, requireContext(), chats);
        activeChatsRV.setAdapter(usersAdapter);
        activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        usersAdapter.notifyDataSetChanged();


        archiveAdapter = new ChatItemAdapter(this, false, requireContext(), archifechats);
        archiveChatsRV.setAdapter(archiveAdapter);
        archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        archiveAdapter.notifyDataSetChanged();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Chats> resultChat = (ArrayList<Chats>) chats.stream()
                        .filter(c -> String.valueOf(c.getFirstName()).contains(query))
                        .collect(Collectors.toList());

                ArrayList<Chats> resultarchive = (ArrayList<Chats>) archifechats.stream()
                        .filter(c -> String.valueOf(c.getFirstName()).contains(query))
                        .collect(Collectors.toList());


                usersAdapter = new ChatItemAdapter(ChatFragment.this, false, requireContext(), resultChat);
                activeChatsRV.setAdapter(usersAdapter);
                activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                usersAdapter.notifyDataSetChanged();
                archiveAdapter = new ChatItemAdapter(ChatFragment.this, false, requireContext(), resultarchive);
                archiveChatsRV.setAdapter(archiveAdapter);
                archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                archiveAdapter.notifyDataSetChanged();
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Chats> resultChat = (ArrayList<Chats>) chats.stream()
                        .filter(c -> String.valueOf(c.getFirstName()).contains(newText))
                        .collect(Collectors.toList());

                ArrayList<Chats> resultarchive = (ArrayList<Chats>) archifechats.stream()
                        .filter(c -> String.valueOf(c.getFirstName()).contains(newText))
                        .collect(Collectors.toList());


                usersAdapter = new ChatItemAdapter(ChatFragment.this, false, requireContext(), resultChat);
                activeChatsRV.setAdapter(usersAdapter);
                activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                usersAdapter.notifyDataSetChanged();
                archiveAdapter = new ChatItemAdapter(ChatFragment.this, false, requireContext(), resultarchive);
                archiveChatsRV.setAdapter(archiveAdapter);
                archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                archiveAdapter.notifyDataSetChanged();
                return false;
            }
        });


        getData();

    }

    private void getData() {
        firebaseManger.getDatabaseReference().child(Constant.Chats).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                archifechats.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Chats user = snapshot1.getValue(Chats.class);

                    if (user.getCustumerUserId().equals(Integer.valueOf(AppDefs.user.getId())) || user.getOwnerUserId().equals(Integer.valueOf(AppDefs.user.getId()))) {
                        if (user.getCustumerUserId().equals(Integer.valueOf(AppDefs.user.getId()))) {
                            if (user.getLiveForCustamer()) {
                                if ((user.getStatus() == 2 && !user.getWhoArchive().equals(Integer.valueOf(AppDefs.user.getId()))) || user.getStatus() == 1) {
                                    // user.setStatus(1);
                                    chats.add(user);
                                } else {
                                    archifechats.add(user);
                                }
                            }

                        } else {
                            if (user.getLiveForOwner()) {
                                if ((user.getStatus() == 2 && user.getWhoArchive() != Integer.valueOf(AppDefs.user.getId())) || user.getStatus() == 1) {
                                    // user.setStatus(1);
                                    chats.add(user);
                                } else {
                                    archifechats.add(user);
                                }
                            }

                        }


                    }

                }
                //binding.recyclerView.hideShimmerAdapter();
                usersAdapter.notifyDataSetChanged();
                archiveAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void onClick() {
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellAnItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));
      /* editChats.setOnClickListener(view -> {
            editChats.setVisibility(View.INVISIBLE);
            deleteChats.setVisibility(View.VISIBLE);
            ChatItemAdapter chatItemAdapter = new ChatItemAdapter(this, true,requireContext(),chats);
            activeChatsRV.setAdapter(chatItemAdapter);
            archiveChatsRV.setAdapter(chatItemAdapter);
            activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
            archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        });*/
        deleteChats.setOnClickListener(view -> {
            // editChats.setVisibility(View.VISIBLE);
            if (!isDelete) {
                isDelete = true;
                deleteChats.setVisibility(View.VISIBLE);
                usersAdapter = new ChatItemAdapter(this, true, requireContext(), chats);
                activeChatsRV.setAdapter(usersAdapter);
                activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                usersAdapter.notifyDataSetChanged();


                archiveAdapter = new ChatItemAdapter(this, true, requireContext(), archifechats);
                archiveChatsRV.setAdapter(archiveAdapter);
                archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                archiveAdapter.notifyDataSetChanged();

            } else {
                isDelete = false;
                deleteChats.setVisibility(View.VISIBLE);
                if (!chatsKey.isEmpty()) {
                    for (String num : chatsKey) {
                        firebaseManger.getDatabaseReference().child(Constant.Chats).child(num).
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Chats user = snapshot.getValue(Chats.class);
                                        Toast.makeText(mainActivity, user.getCustumerUserId().toString(), Toast.LENGTH_SHORT).show();
                                        final HashMap<String, Object> post1 = new HashMap<>();


                                        if (user.getCustumerUserId().equals(Integer.valueOf(AppDefs.user.getId()))) {

                                            post1.put("liveForCustamer", false);
                                            firebaseManger.getDatabaseReference().child(Constant.Chats).child(num).updateChildren(post1);
                                            firebaseManger.getDatabaseReference().child(Constant.Chats)
                                                    .child(num)
                                                    .child("message")
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            final HashMap<String, Object> post2 = new HashMap<>();
                                                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                                                Message message = snapshot1.getValue(Message.class);
                                                                post2.put("liveForCustamer", false);
                                                                // String key=snapshot1.getKey();
                                                                //firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid)
                                                                // .child("message").child(message.getChatId()).updateChildren(post1);

                                                                firebaseManger.getDatabaseReference().child(Constant.Chats).child(num)
                                                                        .child("message").child(message.getMessageId()).updateChildren(post2);


                                                            }
                                                            return;
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });


                                        } else {


                                            post1.put("liveForOwner", false);
                                            firebaseManger.getDatabaseReference().child(Constant.Chats).child(num).updateChildren(post1);
                                            firebaseManger.getDatabaseReference().child(Constant.Chats)
                                                    .child(num)
                                                    .child("message")
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            final HashMap<String, Object> post2 = new HashMap<>();
                                                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                                                Message message = snapshot1.getValue(Message.class);
                                                                String key = snapshot1.getKey();
                                                                post2.put("liveForOwner", false);
                                                                firebaseManger.getDatabaseReference().child(Constant.Chats).child(num)
                                                                        .child("message").child(message.getMessageId()).updateChildren(post2);

                                                            }
                                                            return;
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });


                                        }

                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }



                    // chats.clear();
                    // archifechats.clear();
                }


                usersAdapter = new ChatItemAdapter(this, false, requireContext(), chats);
                activeChatsRV.setAdapter(usersAdapter);
                activeChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                usersAdapter.notifyDataSetChanged();

                archiveAdapter = new ChatItemAdapter(this, false, requireContext(), archifechats);
                archiveChatsRV.setAdapter(archiveAdapter);
                archiveChatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
                archiveAdapter.notifyDataSetChanged();


            }


            usersAdapter.setOnRecyclerViewItemClickListener(new ChatItemAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemCheckBoxChecked(boolean isChecked, int position, String key) {
                    for (String x : chatsKey) {

                        if (x.equals(key)) {
                            isfound = true;
                        }
                    }

                    if (isfound == true) {
                        chatsKey.remove(key);
                        isfound = false;
                        Log.d("myadd", chatsKey.toString());
                    } else {
                        chatsKey.add(key);
                        Log.d("myadd", chatsKey.toString());

                    }


                }
            });
            archiveAdapter.setOnRecyclerViewItemClickListener(new ChatItemAdapter.OnRecyclerViewItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onItemCheckBoxChecked(boolean isChecked, int position, String key) {

                    for (String x : chatsKey) {

                        if (x.equals(key)) {
                            isfound = true;
                        }
                    }

                    if (isfound == true) {
                        chatsKey.remove(key);
                        isfound = false;
                        Log.d("myadd", chatsKey.toString());
                    } else {
                        chatsKey.add(key);
                        Log.d("myadd", chatsKey.toString());

                    }

                }
            });

        });

    }

    private void startActivity(String fragName) {
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    public void showOptions(String chatid, Integer status, Integer whoArcive, Integer
            castomerUserId, Integer ownerUserId) {
        View myAdsOptionsAlertView = LayoutInflater.from(getContext()).inflate(R.layout.delete_chat_item_pop_up, null);
        AlertDialog myAdsOptionsAlertBuilder = new AlertDialog.Builder(getContext()).setView(myAdsOptionsAlertView).show();
        myAdsOptionsAlertBuilder.show();

        myAdsOptionsAlertBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        MaterialButton cancel = myAdsOptionsAlertView.findViewById(R.id.cancel);
        MaterialButton delete = myAdsOptionsAlertView.findViewById(R.id.delete);
        MaterialButton archive = myAdsOptionsAlertView.findViewById(R.id.archive);

        cancel.setOnClickListener(view -> myAdsOptionsAlertBuilder.dismiss());
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final HashMap<String, Object> post1 = new HashMap<>();
                if (Integer.valueOf(AppDefs.user.getId()).equals(castomerUserId)) {
                    post1.put("liveForCustamer", false);
                    firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid).updateChildren(post1);

                    firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid)
                            .child("message").addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                Message message = snapshot1.getValue(Message.class);
                                post1.put("liveForCustamer", false);
                                // String key=snapshot1.getKey();
                                //firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid)
                                // .child("message").child(message.getChatId()).updateChildren(post1);

                                firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid)
                                        .child("message").child(message.getMessageId()).updateChildren(post1);


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }});


                    } else {

                    post1.put("liveForOwner", false);
                    firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid).updateChildren(post1);
                 /*   firebaseManger.getDatabaseReference().child(Constant.Chats)
                            .child(chatid)
                            .child("message")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                        Message message = snapshot1.getValue(Message.class);
                                        String key = snapshot1.getKey();
                                        post1.put("liveForOwner", false);
                                        firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid)
                                                .child("message").child(message.getMessageId()).updateChildren(post1);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });*/
                    firebaseManger.getDatabaseReference().child(Constant.Chats) .child(chatid)
                            .child("message").addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                Message message = snapshot1.getValue(Message.class);
                                String key = snapshot1.getKey();
                                post1.put("liveForOwner", false);
                                firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid)
                                        .child("message").child(message.getMessageId()).updateChildren(post1);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }});

                }
                myAdsOptionsAlertBuilder.dismiss();
            }

        });
        if (status == 1) {
            archive.setText("arcive");
        }else if (status == 2) {
            archive.setText("Unarcive");
        }else if (status == 3) {
            archive.setVisibility(View.GONE);
        }else if (status == 5) {
            archive.setText("Unarcive");
        }

        archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, Object> post1 = new HashMap<>();
                if (status == 1) {
                    post1.put("status", Integer.valueOf(2));
                    post1.put("whoArchive", Integer.valueOf(AppDefs.user.getId()));

                } else if (status == 2) {

                    if (Integer.valueOf(whoArcive).equals(Integer.valueOf(AppDefs.user.getId()))) {
                        post1.put("status", Integer.valueOf(1));
                    } else {
                        post1.put("status", Integer.valueOf(5));
                    }



                } else if (status == 5) {

                    post1.put("status", Integer.valueOf(2));
                    if (String.valueOf(ownerUserId) == AppDefs.user.getId()) {
                        post1.put("whoArchive", Integer.valueOf(castomerUserId));
                    } else {
                        post1.put("whoArchive", Integer.valueOf(ownerUserId));
                    }
                }

                firebaseManger.getDatabaseReference().child(Constant.Chats).child(chatid).updateChildren(post1);


                myAdsOptionsAlertBuilder.dismiss();
                chats.clear();
                archifechats.clear();


            }
        });
        // archive.setOnClickListener(view -> myAdsOptionsAlertBuilder.dismiss());
    }

    @Override
    public void onResume() {
        super.onResume();
        archiveAdapter.notifyDataSetChanged();
        usersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        archiveAdapter.notifyDataSetChanged();
        usersAdapter.notifyDataSetChanged();
    }
}