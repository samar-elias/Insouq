package com.hudhud.insouqapplication.Views.Main.Home.Notifications;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.Notification;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    MainActivity mainActivity;
    ImageView backToPrevious, filterNotifications;
    LinearLayout filterNotificationLayout;
    View transView;
    MaterialButton filterResults;
    NavController navController;
    TextView closeFilter, noNoti;
    RecyclerView notificationsRV;
    ImageView home, profile, chat, sellItem, notifications;
    ArrayList<Notification> notificationsArray;
    RequestQueue queue ;

    public NotificationsFragment() {
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
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getNotifications();
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
        queue =  Volley.newRequestQueue(mainActivity);
        backToPrevious = view.findViewById(R.id.back_arrow);
        filterNotifications = view.findViewById(R.id.filter_notifications);
        filterNotificationLayout = view.findViewById(R.id.notification_filter_layout);
        transView = view.findViewById(R.id.trans_layout);
        filterResults = view.findViewById(R.id.filter_results_btn);
        closeFilter = view.findViewById(R.id.close_btn);
        notificationsRV = view.findViewById(R.id.notifications_RV);
        notificationsArray = new ArrayList<>();
        noNoti = view.findViewById(R.id.no_noti);

        //bottom nav bar
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.profile);
        notifications = view.findViewById(R.id.notification);
    }

    private void onClick(){
        filterNotifications.setOnClickListener(view -> filterNotificationLayout.setVisibility(View.VISIBLE));
        transView.setOnClickListener(view -> filterNotificationLayout.setVisibility(View.GONE));
        filterResults.setOnClickListener(view -> filterNotificationLayout.setVisibility(View.GONE));
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        closeFilter.setOnClickListener(view -> filterNotificationLayout.setVisibility(View.GONE));

        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        notifications.setOnClickListener(view -> startActivity("notifications"));
    }

    private void setNotificationAdapter(){
        if (notificationsArray.size() == 0){
            noNoti.setVisibility(View.VISIBLE);
        }else {
            noNoti.setVisibility(View.GONE);
            NotificationsAdapter notificationsAdapter = new NotificationsAdapter(this, notificationsArray);
            notificationsRV.setAdapter(notificationsAdapter);
            notificationsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }

    public void navigateToMotors(){
//        navController.navigate(NotificationsFragmentDirections.actionNotificationsFragmentToMotorDetailsFragment2());
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        MainActivity.fragName = fragName;
        startActivity(intent);
        mainActivity.finish();
    }

    private void getNotifications(){
        notificationsArray.clear();
        StringRequest notificationsRequest = new StringRequest(Request.Method.GET, Urls.Notifications_URL+"GetNotifications", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray notificationsArr = new JSONArray(response);
                for (int i=0; i<notificationsArr.length(); i++){
                    JSONObject notificationObj = notificationsArr.getJSONObject(i);
                    Notification notification = new Notification();
                    notification.setAdId(notificationObj.getString("adId"));
                    notification.setCategory(notificationObj.getString("category"));
                    notification.setImage(notificationObj.getString("imageUrl"));
                    notification.setPostedDate(notificationObj.getString("date"));
//                    JSONObject adObj = notificationObj.getJSONObject("Ad");
                    notification.setTitle(notificationObj.getString("message"));
                    notificationsArray.add(notification);
                }
                setNotificationAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.notifications), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.notifications), mainActivity.getResources().getString(R.string.internet_connection_error));
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        queue.add(notificationsRequest);
    }

    public void deleteNotification(String adId){
        JSONObject notification = new JSONObject();
        try {
            notification.put("notificationId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest deleteNotification = new JsonObjectRequest(Request.Method.POST, Urls.Notifications_URL+"DeleteNotification", notification, response -> {
            getNotifications();
        }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(deleteNotification);
    }
}