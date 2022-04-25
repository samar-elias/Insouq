package com.hudhud.insouqapplication.Views.Main.Chat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Constant;
import com.hudhud.insouqapplication.AppUtils.Helpers.FirebaseManger;
import com.hudhud.insouqapplication.AppUtils.Responses.Chats;
import com.hudhud.insouqapplication.AppUtils.Responses.Message;
import com.hudhud.insouqapplication.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatDetailesActivity extends AppCompatActivity {
    ImageView back_arrow;
    MessagesAdapter adapter;
    //ArrayList<Message> messages;
    FirebaseManger firebaseManger;
    //int senderRoom;
    // int receiverRoom;

    FirebaseDatabase database;
    FirebaseStorage storage;

    ProgressDialog dialog;


    RecyclerView recyclerView;
    RoundedImageView images;
    CircleImageView profile_image;
    TextView username, adds_detailes, tv_adds_price, tv_adds_time,tv_unblock;


    private final ArrayList<Message> messages = new ArrayList<>();

    private EditText messageEdt;
    private ImageView send, attach, location, block;
    String image = "";
    String chat_id = "";
    Double lat = 0.0;
    Double lng = 0.0;
    String firstName = "";
    String userImage = "";
    String description = "";
    String title = "";
    String price = "";

    String user_Id = "";
    Location currentLocation;
    LinearLayoutCompat message_layout, message_layout2;

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detailes);
        back_arrow = findViewById(R.id.back_arrow);
        recyclerView = findViewById(R.id.chat_messages_RV);
        attach = findViewById(R.id.attach);
        message_layout = findViewById(R.id.message_layout);
        message_layout2 = findViewById(R.id.message_layout2);
        messageEdt = findViewById(R.id.message_edt);
        profile_image = findViewById(R.id.profile_image);
        adds_detailes = findViewById(R.id.tv_adsdetailes);
        tv_adds_price = findViewById(R.id.tv_adds_price);
        tv_adds_time = findViewById(R.id.tv_adds_time);
        tv_unblock=findViewById(R.id.tv_unblock);
        send = findViewById(R.id.send);
        block = findViewById(R.id.block);
        images = findViewById(R.id.image);
        username = findViewById(R.id.user_name);
        location = findViewById(R.id.location);
        chat_id = getIntent().getStringExtra("chatId");
        user_Id = getIntent().getStringExtra("user_Id");
        firstName = getIntent().getStringExtra("firstName");
        userImage = getIntent().getStringExtra("userImage");
        description = getIntent().getStringExtra("description");
        firebaseManger = FirebaseManger.getInstance();
        username.setText(firstName);
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        price = getIntent().getStringExtra("price");
        adds_detailes.setText(description);
        tv_adds_time.setText(title);
        tv_adds_price.setText(price);


        /*String token = getIntent().getStringExtra("token");*/
        String newPic = image.replace("\\", "/");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        Glide.with(this).load(newPic).error(R.drawable.jobs_peach).into(images);
        Glide.with(this).load(userImage).error(R.drawable.jobs_peach).into(profile_image);

        Log.d("img", image);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                    Chats user = snapshot.getValue(Chats.class);
               if(user.getStatus() == 3) {
                   if (user.getWhoBlock().equals(Integer.valueOf(AppDefs.user.getId()))) {
                       message_layout.setVisibility(View.GONE);
                       message_layout2.setVisibility(View.VISIBLE);
                       tv_unblock.setText("Un Block");
                       message_layout2.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               message_layout2.setVisibility(View.GONE);
                               message_layout.setVisibility(View.VISIBLE);

                               final HashMap<String, Object> post1 = new HashMap<>();
                               post1.put("status", 1);
                               post1.put("whoBlock", 0);
                               firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).updateChildren(post1);


                           }
                       });


                   }else {

                       message_layout.setVisibility(View.GONE);
                       message_layout2.setVisibility(View.VISIBLE);
                       tv_unblock.setText("This contact hase block you");
                       block.setVisibility(View.GONE);


                   }

               }else {
                   message_layout.setVisibility(View.VISIBLE);
                   message_layout2.setVisibility(View.GONE);
               }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading image...");
        dialog.setCancelable(false);

        // messages = new ArrayList<>();
        adapter = new MessagesAdapter(this, this, messages);
        LinearLayoutManager llm = new LinearLayoutManager(this);
       /* llm.setReverseLayout(true);
        llm.setStackFromEnd(true);*/
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        /*Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
              changeSeen();

            }
        };
        handler.postDelayed(runnable, 5000);*/


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchLocation();
            }
        });
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_layout2.setVisibility(View.VISIBLE);
                message_layout.setVisibility(View.GONE);
                final HashMap<String, Object> post1 = new HashMap<>();
                post1.put("status", 3);
                post1.put("whoBlock", Integer.valueOf(AppDefs.user.getId()));
                firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).updateChildren(post1);


                Toast.makeText(ChatDetailesActivity.this, "block click", Toast.LENGTH_SHORT).show();
            }
        });


        loadMassage();

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 25);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt = messageEdt.getText().toString();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);

                String time = df.format(new Date());
                List<String> LocURL = new ArrayList<>();
                List<String> files = new ArrayList<>();
                String randomKey = database.getReference().push().getKey();
                Message message = new Message(Integer.valueOf(AppDefs.user.getId()), messageTxt, true, true, String.valueOf(time), 2, chat_id, randomKey, LocURL, files);



                //  String today = formatter.format(date2);
                HashMap<String, Object> post1 = new HashMap<>();
                post1.put("liveForCustamer", true);
                post1.put("liveForOwner", true);
                post1.put("lastMessage", messageTxt);
                post1.put("lastUpdate", String.valueOf(time));

                database.getReference().child("Chats").child(chat_id).updateChildren(post1);


                System.out.println("Today : " + time);
                database.getReference().child("Chats")
                        .child(chat_id)
                        .child("message")
                        .child(randomKey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
                messageEdt.setText("");
                //loadMassage();


            }
        });
    }

    public void showLocation(String location) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse(location);

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
        //  Toast.makeText(this, location, Toast.LENGTH_SHORT).show();


    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 25) {
            if (data != null) {
                if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    Calendar calendar = Calendar.getInstance();
                    StorageReference reference = storage.getReference().child("Chats").child(calendar.getTimeInMillis() + "");
                    dialog.show();
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String filePath = uri.toString();
                                        List<String> file = new ArrayList<>();
                                        List<String> LocURL = new ArrayList<>();

                                        String randomKey = database.getReference().push().getKey();

                                        String messageTxt = messageEdt.getText().toString();

                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);

                                        String time = df.format(new Date());
                                        //  Message message = new Message(messageTxt, senderUid, date.getTime());
                                        Message message = new Message(Integer.valueOf(AppDefs.user.getId()), messageTxt, true, true, String.valueOf(time), 2, chat_id, randomKey, LocURL, file);

                                        message.setMessage("photo");
                                        file.add(filePath);
                                        message.setFiles(file);
                                        messageEdt.setText("");



                                    /*    HashMap<String, Object> lastMsgObj = new HashMap<>();
                                        lastMsgObj.put("lastMsg", message.getMessage());
                                        lastMsgObj.put("lastMsgTime", date.getTime());

                                        database.getReference().child("Chats").child(senderRoom).updateChildren(lastMsgObj);
                                        database.getReference().child("Chats").child(receiverRoom).updateChildren(lastMsgObj);*/

                                        database.getReference().child("Chats")
                                                .child(chat_id)
                                                .child("message")
                                                .child(randomKey)
                                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                /*database.getReference().child("Chats")
                                                        .child(receiverRoom)
                                                        .child("message")
                                                        .child(randomKey)
                                                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });*/
                                            }
                                        });

                                        //Toast.makeText(ChatActivity.this, filePath, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    }


    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    lat = currentLocation.getLatitude();
                    lng = currentLocation.getLongitude();
                    String locURL = "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lng;
                    String urlmapImg = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=13&size=300x300&sensor=false&key=AIzaSyDtQgHRpKBnlCfmlFyIKhliytfCb9tHgJY";
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    List<String> file = new ArrayList<>();
                    List<String> LocURL = new ArrayList<>();

                    String randomKey = database.getReference().push().getKey();

                    String messageTxt = messageEdt.getText().toString();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);

                    String time = df.format(new Date());
                    //  Message message = new Message(messageTxt, senderUid, date.getTime());
                    Message message = new Message(Integer.valueOf(AppDefs.user.getId()), messageTxt, true, true, String.valueOf(time), 2, chat_id, randomKey, LocURL, file);

                    message.setMessage("photo");
                    LocURL.add(locURL);
                    LocURL.add(urlmapImg);
                    message.setLocURL(LocURL);
                    messageEdt.setText("");



                                    /*    HashMap<String, Object> lastMsgObj = new HashMap<>();
                                        lastMsgObj.put("lastMsg", message.getMessage());
                                        lastMsgObj.put("lastMsgTime", date.getTime());

                                        database.getReference().child("Chats").child(senderRoom).updateChildren(lastMsgObj);
                                        database.getReference().child("Chats").child(receiverRoom).updateChildren(lastMsgObj);*/

                    database.getReference().child("Chats")
                            .child(chat_id)
                            .child("message")
                            .child(randomKey)
                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                                                /*database.getReference().child("Chats")
                                                        .child(receiverRoom)
                                                        .child("message")
                                                        .child(randomKey)
                                                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });*/
                        }
                    });

                }
            }
        });
    }
    private void changeSeen(){
         firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).child("message").
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final HashMap<String, Object> post1 = new HashMap<>();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            Message message = snapshot1.getValue(Message.class);
                            if(message.getSender() != Integer.valueOf(AppDefs.user.getId())){
                                post1.put("seen", 1);
                                firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id)
                                        .child("message").child(message.getMessageId()).updateChildren(post1);

                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void loadMassage() {

        firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                changeSeen();
                if (dataSnapshot.exists()) {
                    Chats chats = dataSnapshot.getValue(Chats.class);
                    chats.getCustumerUserId();

                    firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).child("message").addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot1, @Nullable String previousChildName) {
                            // messages.clear();


                            //  adapter.notifyDataSetChanged();
                            // recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());

                            if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                                try {

                                    Message message = snapshot1.getValue(Message.class);
                                    boolean x = message.getLiveForCustamer();
                                    boolean y = message.getLiveForOwner();
                                    if (chats.getCustumerUserId().equals(Integer.valueOf(AppDefs.user.getId()))) {
                                        if (x) {
                                            message.setMessageId(snapshot1.getKey());
                                            messages.add(message);
                                        }

                                    } else {

                                        if (y) {
                                            message.setMessageId(snapshot1.getKey());
                                            messages.add(message);
                                        }

                                    }
                                    recyclerView.scrollToPosition(messages.size() - 1);
                                    adapter.notifyItemInserted(messages.size() - 1);
                                } catch (Exception ex) {
                                    //  Log.e(TAG, ex.getMessage());
                                }
                            }


                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            changeSeen();

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            changeSeen();
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }


}