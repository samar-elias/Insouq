package com.hudhud.insouqapplication.Views.Main.SellingItems.Jobs.JobWanted;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.AppUtils.objectsToSend.Send;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.graphics.Typeface.BOLD;

public class PostJobWantedAdFragment extends Fragment {

    NavController navController;
    ImageView backToPrevious, home, profile, chat, sellItem, list;
    MainActivity mainActivity;
    MaterialButton continueBtn;
    EditText titleEdt, phoneNumberEdt, otherJobTypeEdt, jobTitleEdt;
    Spinner jobTypesSpinner;
    ArrayList<String> jobTypeArTitles, jobTypeEnTitles;
    String currentJobType = "";
    boolean spinner1 = false;

    public PostJobWantedAdFragment() {
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
        return inflater.inflate(R.layout.fragment_post_job_wanted_ad, container, false);
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
        initViews(view);
        onClick();
        getJobTypes();
        onSpinnerClick();
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        backToPrevious = view.findViewById(R.id.back_arrow);

        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        sellItem = view.findViewById(R.id.sell_item);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);

        continueBtn = view.findViewById(R.id.continue_btn);
        titleEdt = view.findViewById(R.id.job_ad_title);
        phoneNumberEdt = view.findViewById(R.id.phone_number);
        otherJobTypeEdt = view.findViewById(R.id.other_job_type);
        jobTitleEdt = view.findViewById(R.id.job_title);
        jobTypesSpinner = view.findViewById(R.id.job_types_spinner);

        jobTypeArTitles = new ArrayList<>();
        jobTypeEnTitles = new ArrayList<>();

        if (!AppDefs.user.getMobileNumber().equals("null")) {
            phoneNumberEdt.setText(AppDefs.user.getMobileNumber());
        }
    }

    private void onClick() {
        backToPrevious.setOnClickListener(view -> navController.popBackStack());
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));

        continueBtn.setOnClickListener(view -> {
            String title = String.valueOf(titleEdt.getText());
            String phoneNumber = String.valueOf(phoneNumberEdt.getText());
            String jobTitle = String.valueOf(jobTitleEdt.getText());
            String otherJobType = String.valueOf(otherJobTypeEdt.getText());
            if (title.isEmpty() || phoneNumber.isEmpty() || jobTitle.isEmpty()) {
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
            } else if (otherJobTypeEdt.getVisibility() == View.VISIBLE) {
                if (otherJobType.isEmpty()) {
                    mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.jobs), mainActivity.getResources().getString(R.string.fill_all_fields));
                } else {
                    setData(title, phoneNumber, jobTitle, otherJobType);
                }
            } else {
                setData(title, phoneNumber, jobTitle, otherJobType);
            }
        });
    }

    private void setData(String title, String phoneNumber, String jobTitle, String otherJobType){
        Send.addJobWantedAd.setTitle(title);
        Send.addJobWantedAd.setJobType(currentJobType);
        Send.addJobWantedAd.setJobTitle(jobTitle);
        Send.addJobWantedAd.setPhoneNumber(phoneNumber);
        Send.addJobWantedAd.setOtherJobType(otherJobType);
        navController.navigate(PostJobWantedAdFragmentDirections.actionPostJobWantedAdFragmentToPostFullJobWantedAdFragment());
    }

    private void setSpinner(Spinner spinner, ArrayList<String> arrayList){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell3_spinner,arrayList);
        sortAdapter.setDropDownViewResource(R.layout.sell2_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void onSpinnerClick(){
        jobTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.purple_text));
                    ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.defaultFromStyle(BOLD));
                }else {
                    spinner1 = true;
                    ((TextView) adapterView.getChildAt(0)).setTextColor(mainActivity.getResources().getColor(R.color.gray_3));
                }
                if (i == jobTypeEnTitles.size()-1){
                    currentJobType = "";
                    otherJobTypeEdt.setVisibility(View.VISIBLE);
                }else {
                    currentJobType = jobTypeEnTitles.get(i)+"-"+jobTypeArTitles.get(i);
                    otherJobTypeEdt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getJobTypes(){
        jobTypeEnTitles.clear();
        jobTypeArTitles.clear();
        jobTypeEnTitles.add(mainActivity.getResources().getString(R.string.select_job_type));
        jobTypeArTitles.add(mainActivity.getResources().getString(R.string.select_job_type));
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest jobTypesRequest = new StringRequest(Request.Method.GET, Urls.DropDowns_URL+"GetAllJobType", response -> {
            mainActivity.hideProgressDialog();
            try {
                JSONArray jobTypesArray = new JSONArray(response);
                for (int i=0; i<jobTypesArray.length(); i++){
                    JSONObject jobTypeObj = jobTypesArray.getJSONObject(i);
                    jobTypeArTitles.add(jobTypeObj.getString("ar_Text"));
                    jobTypeEnTitles.add(jobTypeObj.getString("en_Text"));
                }
                jobTypeArTitles.add(mainActivity.getResources().getString(R.string.other));
                jobTypeEnTitles.add(mainActivity.getResources().getString(R.string.other));
                if (AppDefs.language.equals("ar")){
                    setSpinner(jobTypesSpinner, jobTypeArTitles);
                }else {
                    setSpinner(jobTypesSpinner, jobTypeEnTitles);
                }
                currentJobType = jobTypeEnTitles.get(0)+"-"+jobTypeArTitles.get(0);
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.job_type), mainActivity.getResources().getString(R.string.error_occured));
            }
        }, error -> {
           mainActivity.hideProgressDialog();
           mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.job_type), mainActivity.getResources().getString(R.string.internet_connection_error));
        });
        mainActivity.queue.add(jobTypesRequest);
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }
}