package com.hudhud.insouqapplication.Views.Main.Home.SubCategory;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Urls.Urls;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MakeOfferBottomSheetDialogFragment extends BottomSheetDialogFragment {

    TextView makeOffer, adPrice;
    MainActivity mainActivity;
    String adId, price;
    EditText priceEdt;
    ImageView close;

//    public static MakeOfferBottomSheetDialogFragment newInstance() {
//        return new MakeOfferBottomSheetDialogFragment();
//    }

    public MakeOfferBottomSheetDialogFragment(String adId, String price) {
        this.adId = adId;
        this.price = price;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_make_offer_bottom_sheet_dialog, container, false);
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
        makeOffer = view.findViewById(R.id.make_offer);
        adPrice = view.findViewById(R.id.ad_price);
        priceEdt = view.findViewById(R.id.price_edt);
        adPrice.setText(price);
        priceEdt.setHint(price);
        close = view.findViewById(R.id.close_btn);
        makeOffer.setOnClickListener(view1 -> {
            String priceOffered = String.valueOf(priceEdt.getText());
            if (priceOffered.isEmpty()) {
                Toast.makeText(mainActivity, mainActivity.getResources().getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            }else {
                makeAnOffer(priceOffered);
            }
        });
        close.setOnClickListener(view1 -> dismiss());
    }

    public void makeAnOffer( String price){
        mainActivity.showProgressDialog(mainActivity.getResources().getString(R.string.loading));
        JSONObject obj = new JSONObject();
        try {
            obj.put("price", price);
            obj.put("adId", adId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest makeOfferRequest = new JsonObjectRequest(Request.Method.POST, Urls.Ads_URL+"MakeAnOffer", obj, response -> {
            mainActivity.hideProgressDialog();
            try {
                Toast.makeText(mainActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.make_an_offer), mainActivity.getResources().getString(R.string.error_occured));
            }

        }, error -> {
            mainActivity.hideProgressDialog();
            mainActivity.showResponseMessage(mainActivity.getResources().getString(R.string.make_an_offer), mainActivity.getResources().getString(R.string.internet_connection_error));

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer "+ AppDefs.user.getToken());
                return params;
            }
        };
        mainActivity.queue.add(makeOfferRequest);
    }
}