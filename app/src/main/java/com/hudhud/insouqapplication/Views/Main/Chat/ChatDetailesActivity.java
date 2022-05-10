package com.hudhud.insouqapplication.Views.Main.Chat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public RequestQueue queue;

    RecyclerView recyclerView;
    AppCompatImageView images;
    AppCompatImageView profile_image;
    TextView username, adds_detailes, tv_adds_price, tv_adds_time,tv_unblock,view_add;
    private ArrayList<Integer> mKeys ;


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

    String userId ;
    String castomer_name="";
    Location currentLocation;
    String adsId="";
    String type="";
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
        view_add=findViewById(R.id.view_add);
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
        userId = getIntent().getStringExtra("userId");
        firstName = getIntent().getStringExtra("firstName");
        userImage = getIntent().getStringExtra("userImage");
        description = getIntent().getStringExtra("description");
        castomer_name=getIntent().getStringExtra("castomer_name");
        adsId=getIntent().getStringExtra("adsId");
        type=getIntent().getStringExtra("type");

        firebaseManger = FirebaseManger.getInstance();
        queue = Volley.newRequestQueue(this);
        mKeys = new ArrayList<>();
        mKeys.clear();

        if(Integer.valueOf(userId).equals(Integer.valueOf(AppDefs.user.getId()))) {
            username.setText(castomer_name);

        }else {
            username.setText(firstName);
        }

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
        view_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInfo();
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {

            }
        }, 1000);

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


        adapter = new MessagesAdapter(this, this, messages,userImage);
        LinearLayoutManager llm = new LinearLayoutManager(this);
       /* llm.setReverseLayout(true);
        llm.setStackFromEnd(true);*/
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);




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
                changeSeen();

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
                mKeys.clear();
               /* changeSeen();

                loadMassage();*/


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
                    String filePath = selectedImage.toString();
                    Uri selectedfile = data.getData(); //The uri with the location of the file
                    if (selectedfile != null) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedfile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        byte[] byteArray = outputStream.toByteArray();

                        //Use your Base64 String as you wish
                        String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Log.d("myfile",encodedString);

                        JSONObject csaveChatinfoObject = new JSONObject();
                        try {
                            csaveChatinfoObject.put("image64", encodedString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        List<String> file = new ArrayList<>();
                        List<String> LocURL = new ArrayList<>();
                        JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.POST, Urls.AddImageChat, csaveChatinfoObject, response -> {
                            //mainActivity.hideProgressDialog();
                            dialog.dismiss();
                            try {

                                Log.d("img",response.getString("results"));


                                String randomKey = database.getReference().push().getKey();

                                String messageTxt = messageEdt.getText().toString();

                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);

                                String time = df.format(new Date());
                                //  Message message = new Message(messageTxt, senderUid, date.getTime());
                                Message message = new Message(Integer.valueOf(AppDefs.user.getId()), messageTxt, true, true, String.valueOf(time), 2, chat_id, randomKey, LocURL, file);

                                message.setMessage("photo");
                                file.add(response.getString("results"));
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
                    /*reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        //Toast.makeText(ChatActivity.this, filePath, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });*/
                                Toast.makeText(this, "add success", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Toast.makeText(mainActivity, e.toString(), Toast.LENGTH_LONG).show();

                            }
                        }, error -> {
                            //  mainActivity.hideProgressDialog();
                            //  mainActivity.showResponseMessage(getResources().getString(R.string.change_password), getResources().getString(R.string.wrong_current_password));
                        });
                        queue.add(changePasswordRequest);
                    }




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
        Query query = firebaseManger.getDatabaseReference().child(Constant.Chats).child(chat_id).child("message");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final HashMap<String, Object> post1 = new HashMap<>();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            Message message = snapshot1.getValue(Message.class);
                            if(!message.getSender().equals(Integer.valueOf(AppDefs.user.getId()))){
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

                if (dataSnapshot.exists()) {
                    changeSeen();
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
                           // loadMassage();


                            Message chatModel = snapshot.getValue(Message.class);
                            Integer location = chatModel.getSeen();
                            mKeys.add(location);
                            if (mKeys.size() != -1){
                                for (int i=mKeys.size();i>=1;i--){
                                    messages.set(messages.size()-i, chatModel);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
    public  void showInfo(){
        if (type.equals("5")){
            Intent intent=new Intent(this,BusinessChatDetailsActivity.class);
            intent.putExtra("adsId",String.valueOf(adsId));
            intent.putExtra("type",String.valueOf(type));
            startActivity(intent);

        }

    }

    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {/*from   w w w .  ja  va  2s  .  c om*/
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }


}