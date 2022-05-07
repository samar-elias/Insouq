package com.hudhud.insouqapplication.Views.Main.Chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

public class ChatDetailsFragment extends Fragment {

    ImageView home, chat, sellAnItem, profile, notifications;
    NavController navController;
    MainActivity mainActivity;

    private static final String TAG = "MainActivityTAG";

    private static final String MESSAGE_TYPE_TEXT = "text";
    private static final String MESSAGE_TYPE_IMAGE = "image";

//    private String currentUserFirebaseId;
//    private String currentUserImageUrl;
//    private String currentUserName;
//    private Conversation conversation;
//
//    private ChatsAdapter mChatsAdapter;

    private RecyclerView chatsRV;
    private EditText messageEdt;
    private ImageView send, attach, location;
    private DatabaseReference mChatMessagesDbReference;
    private ChildEventListener mChildEventListener;

    public ChatDetailsFragment() {
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
        return inflater.inflate(R.layout.fragment_chat_details, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



//        getChatMessages(currentUserFirebaseId, conversation.getOtherUserId());
//
//        send.setOnClickListener(v -> {
//            String message = messageEdt.getText().toString();
//            if (!message.isEmpty()){
//                updateConversations(conversation,message,MESSAGE_TYPE_TEXT);
//            }
//        });
//
//        attachFileAppCompatButton.setOnClickListener(v -> PickImageDialog.build(new PickSetup())
//                .setOnPickResult(r -> {
//                    final String uuid = UUID.randomUUID().toString().replace("-", "");
//                    FirebaseStorage storage = FirebaseStorage.getInstance();
//                    StorageReference storageRef = storage.getReference();
//                    StorageReference imagesRef = storageRef.child(uuid+".jpg");
//                    uploadImage(imagesRef,r.getUri());
//                }).show(mainActivity));
    }

    private void initViews(View view){

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellAnItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);

        chatsRV = view.findViewById(R.id.chat_messages_RV);
        attach = view.findViewById(R.id.attach);
        messageEdt = view.findViewById(R.id.message_edt);
        send = view.findViewById(R.id.send);

        chatsRV.setHasFixedSize(true);
        chatsRV.setLayoutManager(new LinearLayoutManager(mainActivity));
    }
}