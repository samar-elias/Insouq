package com.hudhud.insouqapplication.Views.Main.Profile.ContactUs;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactUsFragment extends Fragment {

    NavController navController;
    MainActivity mainActivity;
    ImageView home, profile, chat, sellItem, list, backToPrevious;
    Spinner reasonsSpinner;
    MaterialButton submit;
    EditText nameEdt, emailAddressEdt, descriptionEdt;
    ArrayList<String> reasons = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    String name = "", emailAddress = "", description = "", id = "", path = "";
    ConstraintLayout upload;

    public ContactUsFragment() {
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
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onClick();
        getReasons();
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
        home = view.findViewById(R.id.home);
        chat = view.findViewById(R.id.chat);
        profile = view.findViewById(R.id.notification);
        list = view.findViewById(R.id.profile);
        sellItem = view.findViewById(R.id.sell_item);
        backToPrevious = view.findViewById(R.id.back_arrow);

        reasonsSpinner = view.findViewById(R.id.reasons_spinner);
        submit = view.findViewById(R.id.submit);
        nameEdt = view.findViewById(R.id.name_edt);
        emailAddressEdt = view.findViewById(R.id.email_address_edt);
        descriptionEdt = view.findViewById(R.id.description_edt);
        upload = view.findViewById(R.id.add_your_cv);
    }

    private void onClick(){
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        home.setOnClickListener(view -> startActivity("home"));
        chat.setOnClickListener(view -> startActivity("chat"));
        sellItem.setOnClickListener(view -> startActivity("sellItem"));
        profile.setOnClickListener(view -> startActivity("profile"));
        list.setOnClickListener(view -> startActivity("notifications"));
        backToPrevious.setOnClickListener(view -> navController.popBackStack());

        upload.setOnClickListener(view -> uploadFile());

        submit.setOnClickListener(view -> {
            name = String.valueOf(nameEdt.getText());
            emailAddress = String.valueOf(emailAddressEdt.getText());
            description = String.valueOf(descriptionEdt.getText());

            if (name.isEmpty() || emailAddress.isEmpty() || description.isEmpty()){
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.advertising), mainActivity.getResources().getString(R.string.fill_all_fields));
            }else {
                setContact();
            }
        });

        reasonsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id = ids.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void startActivity(String fragName){
        Intent intent = new Intent(mainActivity, MainActivity.class);
        intent.putExtra("fragName", fragName);
        startActivity(intent);
        mainActivity.finish();
    }

    private void setSpinner(Spinner spinner, ArrayList<String> items){
        ArrayAdapter sortAdapter = new ArrayAdapter(getContext(),R.layout.sell_spinner,items);
        sortAdapter.setDropDownViewResource(R.layout.sell_spinner);
        spinner.setAdapter(sortAdapter);
    }

    private void getReasons(){
        reasons.clear();
        StringRequest budgetRequest = new StringRequest(Request.Method.GET, Urls.StaticData_URL+"GetContactUsInquery", response -> {
            try {
                JSONArray budgets = new JSONArray(response);
                for (int i=0; i<budgets.length(); i++){
                    JSONObject budget = budgets.getJSONObject(i);
                    if (AppDefs.language.equals("ar")){
                        reasons.add(budget.getString("ar_Title"));
                    }else {
                        reasons.add(budget.getString("en_Title"));
                    }
                    ids.add(budget.getString("id"));
                }
                setSpinner(reasonsSpinner, reasons);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        mainActivity.queue.add(budgetRequest);
    }

    private void setContact(){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        StringRequest adRequest = new StringRequest(Request.Method.POST, Urls.StaticData_URL+"ContactUs", response -> {
            mainActivity.hideProgressDialog();

            Toast.makeText(mainActivity, "Thank you for contacting us!", Toast.LENGTH_SHORT).show();
            navController.popBackStack();

        }, error -> {
            mainActivity.hideProgressDialog();
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Id", AppDefs.user.getId());
                params.put("Name", name);
                params.put("Email", emailAddress);
                params.put("CV", path);
                params.put("Description", description);
                params.put("Inquery", "0");
                return params;
            }
        };
        mainActivity.queue.add(adRequest);
    }

    private void uploadFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            path = uri.getPath();
            Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.file_uploaded), Toast.LENGTH_SHORT).show();
        }
    }
}