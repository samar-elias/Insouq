package com.hudhud.insouqapplication.Views.Main.Home.SubCategory.Motors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Helpers.Helpers;
import com.hudhud.insouqapplication.AppUtils.Responses.UsedCarAd;
import com.hudhud.insouqapplication.R;
import com.hudhud.insouqapplication.Views.Main.Home.SubCategory.ImageViewPagerAdapter;
import com.hudhud.insouqapplication.Views.Main.MainActivity;
import com.hudhud.insouqapplication.Views.Registration.Home.ViewPagerAdapter;

import java.util.ArrayList;

public class MotorDetailsAdapter extends RecyclerView.Adapter<MotorDetailsAdapter.ViewHolder> {

    MotorDetailsFragment motorDetailsFragment;
    ArrayList<UsedCarAd> motorAds;
    Context context;
    boolean fav = false;

    public MotorDetailsAdapter(MotorDetailsFragment motorDetailsFragment, ArrayList<UsedCarAd> motorAds) {
        this.motorDetailsFragment = motorDetailsFragment;
        this.motorAds = motorAds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.motor_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsedCarAd motorAd = motorAds.get(position);

        holder.viewPager.setVisibility(View.VISIBLE);
        holder.tabLayout.setupWithViewPager(holder.viewPager, true);
        ImageViewPagerAdapter mAdapter = new ImageViewPagerAdapter(motorAd.getPictures(), motorDetailsFragment.mainActivity);
        mAdapter.notifyDataSetChanged();
        holder.viewPager.setOffscreenPageLimit(3);
        holder.viewPager.setAdapter(mAdapter);

        Helpers.setSliderTimer(3000, holder.viewPager, mAdapter);

        ArrayList<specificationModel> motorSpecifications = new ArrayList<>();
        if (!motorAd.getEnModel().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.brand), motorAd.getArMaker(), R.drawable.brand_img_));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.brand), motorAd.getEnMaker(), R.drawable.brand_img_));
            }
        }
        if (!motorAd.getEnModel().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.model), motorAd.getArModel(), R.drawable.cars_model));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.model), motorAd.getEnModel(), R.drawable.cars_model));
            }
        }
        if (!motorAd.getEnTrim().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.trim), motorAd.getArTrim(), R.drawable.trim));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.trim), motorAd.getEnTrim(), R.drawable.trim));
            }
        }
        if (!motorAd.getEnColor().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.color), motorAd.getArColor(), R.drawable.color));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.color), motorAd.getEnColor(), R.drawable.color));
            }
        }
        if (!motorAd.getEnDoors().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.doors), motorAd.getArDoors(), R.drawable.doors));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.doors), motorAd.getEnDoors(), R.drawable.doors));
            }
        }
        if (!motorAd.getWarranty().equals("null")){
            switch (motorAd.getWarranty()) {
                case "0":
                    motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.warranty), context.getResources().getString(R.string.no), R.drawable.warranty));
                    break;
                case "1":
                    motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.warranty), context.getResources().getString(R.string.yes), R.drawable.warranty));
                    break;
                case "2":
                    motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.warranty), context.getResources().getString(R.string.not_apply), R.drawable.warranty));
                    break;
            }
        }
        if (!motorAd.getEnTransmission().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.transmissions), motorAd.getArTransmission(), R.drawable.transmission_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.transmissions), motorAd.getEnTransmission(), R.drawable.transmission_img));
            }
        }
        if (!motorAd.getEnRegionalSpecs().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.specs), motorAd.getArRegionalSpecs(), R.drawable.specs_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.specs), motorAd.getEnRegionalSpecs(), R.drawable.specs_img));
            }
        }
        if (!motorAd.getEnBodyType().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.body_type), motorAd.getArBodyType(), R.drawable.body_type_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.body_type), motorAd.getEnBodyType(), R.drawable.body_type_img));
            }
        }
        if (!motorAd.getEnFuelType().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.fuel_type), motorAd.getArFuelType(), R.drawable.fuel_type_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.fuel_type), motorAd.getEnFuelType(), R.drawable.fuel_type_img));
            }
        }
        if (!motorAd.getEnNoOfCylinders().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.no_of_cylinders), motorAd.getArNoOfCylinders(), R.drawable.cylinder_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.no_of_cylinders), motorAd.getEnNoOfCylinders(), R.drawable.cylinder_img));
            }
        }
        if (!motorAd.getEnHorsepower().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.horsepower), motorAd.getArHorsepower(), R.drawable.horse_power_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.horsepower), motorAd.getEnHorsepower(), R.drawable.horse_power_img));
            }
        }
        if (!motorAd.getEnCondition().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.condition), motorAd.getArCondition(), R.drawable.condition_2));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.condition), motorAd.getEnCondition(), R.drawable.condition_2));
            }
        }
        if (!motorAd.getEnMechanicalCondition().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.mechanical_condition), motorAd.getArMechanicalCondition(), R.drawable.mechanical_condition_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.mechanical_condition), motorAd.getEnMechanicalCondition(), R.drawable.mechanical_condition_img));
            }
        }
        if (!motorAd.getEnCapacity().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.capacity), motorAd.getArCapacity(), R.drawable.capacity));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.capacity), motorAd.getEnCapacity(), R.drawable.capacity));
            }
        }
        if (!motorAd.getEnEngineSize().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.engine_size), motorAd.getArEngineSize(), R.drawable.engine_size_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.engine_size), motorAd.getEnEngineSize(), R.drawable.engine_size_img));
            }
        }
        if (!motorAd.getEnAge().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.age), motorAd.getArAge(), R.drawable.age_1));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.age), motorAd.getEnAge(), R.drawable.age_1));
            }
        }
        if (!motorAd.getEnUsage().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.usage), motorAd.getArUsage(), R.drawable.usage_1));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.usage), motorAd.getEnUsage(), R.drawable.usage_1));
            }
        }
        if (!motorAd.getEnLength().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.length), motorAd.getArLength(), R.drawable.lenght_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.length), motorAd.getEnLength(), R.drawable.lenght_img));
            }
        }
        if (!motorAd.getEnWheels().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.wheels), motorAd.getArWheels(), R.drawable.wheels));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.wheels), motorAd.getEnWheels(), R.drawable.wheels));
            }
        }
        if (!motorAd.getEnSellerType().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.seller_type), motorAd.getArSellerType(), R.drawable.seller_type_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.seller_type), motorAd.getEnSellerType(), R.drawable.seller_type_img));
            }
        }
        if (!motorAd.getEnDriveSystem().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.final_drive_system), motorAd.getArDriveSystem(), R.drawable.final_drive_system_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.final_drive_system), motorAd.getEnDriveSystem(), R.drawable.final_drive_system_img));
            }
        }
        if (!motorAd.getEnSteeringSide().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.steering_side), motorAd.getArSteeringSide(), R.drawable.steering_side_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.steering_side), motorAd.getEnSteeringSide(), R.drawable.steering_side_img));
            }
        }

//        if (!motorAd.getEnPartName().equals("null")){
//            if (AppDefs.language.equals("ar")){
//                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.name_of_part), motorAd.getArPartName(), R.drawable.name_of_part_img));
//            }else {
//                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.name_of_part), motorAd.getEnPartName(), R.drawable.name_of_part_img));
//            }
//        }

        if (!motorAd.getEnEngineSize().equals("null")){
            if (AppDefs.language.equals("ar")){
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.engine_size), motorAd.getArEngineSize(), R.drawable.engine_size_img));
            }else {
                motorSpecifications.add(new specificationModel(context.getResources().getString(R.string.engine_size), motorAd.getEnEngineSize(), R.drawable.engine_size_img));
            }
        }

        SpecificationAdapter specificationAdapter = new SpecificationAdapter(motorSpecifications);
        holder.specificationsRV.setAdapter(specificationAdapter);
        holder.specificationsRV.setLayoutManager(new LinearLayoutManager(context));


        holder.itemText.setText(motorAd.getTitle());
        holder.itemText2.setText(motorAd.getTitle());
        holder.itemPrice.setText(motorAd.getPrice());
        holder.description.setText(motorAd.getDescription());
        holder.year.setText(motorAd.getYear());

        switch (motorAd.getCategoryId()){
            case "2":
            case "8":
            case "9":
                holder.kilo.setText(motorAd.getKiloMeters());
                break;
            case "5":
                if (AppDefs.language.equals("ar")){
                    holder.kilo.setText(motorAd.getArLength());
                }else {
                    holder.kilo.setText(motorAd.getEnLength());
                }
                break;
            case "7":
                if (AppDefs.language.equals("ar")){
                    holder.kilo.setText(motorAd.getArPartName());
                }else {
                    holder.kilo.setText(motorAd.getEnPartName());
                }
                break;
        }

        if (AppDefs.language.equals("ar")){
            holder.location.setText(motorAd.getArLocation());
            holder.location2.setText(motorAd.getArLocation());
            holder.color.setText(motorAd.getArColor());
        }else {
            holder.location.setText(motorAd.getEnLocation());
            holder.location2.setText(motorAd.getEnLocation());
            holder.color.setText(motorAd.getEnColor());
        }

        holder.backToPrevious.setOnClickListener(view -> motorDetailsFragment.navigateBack());
        holder.reportAd.setOnClickListener(view -> motorDetailsFragment.setReportAd(motorAd.getId()));

        holder.contactTitle.setOnClickListener(view -> {
            if (holder.showProfile){
                holder.contactLayout.setVisibility(View.GONE);
                holder.contactTitle.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_baseline_keyboard_arrow_down_24_white, 0);
                holder.showProfile = false;
            }else {
                holder.contactLayout.setVisibility(View.VISIBLE);
                holder.contactTitle.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_baseline_keyboard_arrow_up_24_white, 0);
                holder.showProfile = true;
            }
        });

//        if (motorDetailsFragment.getOfferApplied(motorAd.getId())){
//            holder.makeOfferTxt.setText(context.getResources().getString(R.string.offer_applied));
//            holder.makeOfferImg.setVisibility(View.GONE);
//            holder.makeAnOffer.setEnabled(false);
//        }else {
//            holder.makeOfferTxt.setText(context.getResources().getString(R.string.make_an_offer));
//            holder.makeOfferImg.setVisibility(View.VISIBLE);
//        }
        holder.makeOfferTxt.setText(context.getResources().getString(R.string.make_an_offer));
        holder.makeOfferImg.setVisibility(View.VISIBLE);
        if (motorAd.getIsFav().equals("true")){
            fav = true;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_red_24).into(holder.favourite);
        }else {
            fav = false;
            Glide.with(context).load(R.drawable.ic_baseline_favorite_border_24).into(holder.favourite);
        }
        holder.favourite.setOnClickListener(view -> {
            if (!fav){
                fav = true;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_red_24));
                motorDetailsFragment.addToFavourite(motorAd.getId());
            }else {
                fav = false;
                holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                motorDetailsFragment.removeFromFavourite(motorAd.getId());
            }
        });
        holder.share.setOnClickListener(view -> motorDetailsFragment.share(motorAd.getId()));
        holder.next.setOnClickListener(view ->{
            if (position+1 != motorAds.size()){
                motorDetailsFragment.scrollToPosition(position+1);
            }
        });
        holder.previous.setOnClickListener(view -> {
            if (position!=0){
                motorDetailsFragment.scrollToPosition(position-1);
            }
        });
        holder.descriptionLayout.setOnClickListener(view -> {
            if (holder.showDescription){
                holder.description.setVisibility(View.GONE);
                holder.showDescription = false;
                holder.showDescriptionArrow.setScaleY(1);
                holder.descriptionLine.setVisibility(View.GONE);
            }else {
                holder.description.setVisibility(View.VISIBLE);
                holder.showDescription = true;
                holder.showDescriptionArrow.setScaleY(-1);
                holder.descriptionLine.setVisibility(View.VISIBLE);
            }
        });
        holder.locationLayout.setOnClickListener(view -> {
            if (holder.showLocation){
                holder.locationImage.setVisibility(View.GONE);
                holder.directionsLayout.setVisibility(View.GONE);
                holder.showLocation = false;
                holder.showLocationArrow.setScaleY(1);
            }else {
                holder.locationImage.setVisibility(View.VISIBLE);
                holder.directionsLayout.setVisibility(View.VISIBLE);
                holder.showLocation = true;
                holder.showLocationArrow.setScaleY(-1);
            }
        });
        holder.sms.setOnClickListener(view -> motorDetailsFragment.sendSMS(motorAd.getPhoneNumber()));
        holder.call.setOnClickListener(view -> motorDetailsFragment.call(motorAd.getPhoneNumber()));
        holder.makeAnOffer.setOnClickListener(view -> {
            motorDetailsFragment.openMakeAnOffer(motorAd.getId(), motorAd.getPrice());
            notifyDataSetChanged();
        });
        holder.makeAnOffer2.setOnClickListener(view -> {
            motorDetailsFragment.openMakeAnOffer(motorAd.getId(), motorAd.getPrice());
            notifyDataSetChanged();
        });
        holder.directions.setOnClickListener(view -> motorDetailsFragment.openGoogleMaps(motorAd.getLatitude(), motorAd.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return motorAds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView specificationsRV, similarCarsRV;
        ConstraintLayout specificationLayout, descriptionLayout, locationLayout, directionsLayout, makeAnOffer, makeAnOffer2;
        boolean showSpecification, showDescription, showLocation, fav = false, showProfile = false;
        ImageView showSpecificationArrow, showDescriptionArrow, showLocationArrow, favourite, directions, makeOfferImg;
        TextView description, descriptionLine, makeOfferTxt;
        ImageView locationImage, backToPrevious, share;
        ImageView next, previous, itemImage;
        TextView  reportAd, itemText, itemText2, itemPrice, location, location2, kilo, year, color;
        TextView contactTitle;
        ConstraintLayout contactLayout;
        int count = 4, step = 1;
        ViewPager viewPager;
        TabLayout tabLayout;
        LinearLayout call, sms;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            tabLayout = itemView.findViewById(R.id.tabDots);
            makeOfferImg = itemView.findViewById(R.id.make_offer_img);
            makeOfferTxt = itemView.findViewById(R.id.make_offer_txt);
            backToPrevious = itemView.findViewById(R.id.back_arrow);
            favourite = itemView.findViewById(R.id.favourite);
            reportAd = itemView.findViewById(R.id.report);

            call = itemView.findViewById(R.id.call);
            sms = itemView.findViewById(R.id.sms);
            share = itemView.findViewById(R.id.share);

            itemText = itemView.findViewById(R.id.motor_title);
            itemText2 = itemView.findViewById(R.id.motor_title_2);
            itemPrice = itemView.findViewById(R.id.motor_price);
            itemImage = itemView.findViewById(R.id.slider_image);
            kilo = itemView.findViewById(R.id.kilos_amount);
            year = itemView.findViewById(R.id.year);
            color = itemView.findViewById(R.id.color);
            directions = itemView.findViewById(R.id.directions);

            contactTitle = itemView.findViewById(R.id.contact_title);
            contactLayout = itemView.findViewById(R.id.contact_profile);

            //specifications
            specificationsRV = itemView.findViewById(R.id.specifications_RV);
            specificationLayout = itemView.findViewById(R.id.specification_layout);
            showSpecificationArrow = itemView.findViewById(R.id.show_specifications_arrow);
            showSpecification = false;
            specificationsRV.setVisibility(View.GONE);
            if (showSpecification){
                specificationsRV.setVisibility(View.GONE);
                showSpecification = false;
                showSpecificationArrow.setScaleY(1);
            }else {
                specificationsRV.setVisibility(View.VISIBLE);
                showSpecification = true;
                showSpecificationArrow.setScaleY(-1);
            }

            //description
            descriptionLayout = itemView.findViewById(R.id.description_layout);
            showDescriptionArrow = itemView.findViewById(R.id.show_description_arrow);
            showDescription = false;
            description = itemView.findViewById(R.id.description);
            descriptionLine = itemView.findViewById(R.id.line2);
            description.setVisibility(View.GONE);
            descriptionLine.setVisibility(View.GONE);

            //location
            location = itemView.findViewById(R.id.location);
            location2 = itemView.findViewById(R.id.location2);
            locationLayout = itemView.findViewById(R.id.location_layout);
            directionsLayout = itemView.findViewById(R.id.directions_layout);
            showLocation = false;
            showLocationArrow = itemView.findViewById(R.id.show_location_arrow);
            locationImage = itemView.findViewById(R.id.location_image);
            locationImage.setVisibility(View.GONE);
            directionsLayout.setVisibility(View.GONE);
            similarCarsRV = itemView.findViewById(R.id.similar_cars_RV);

            //make offer
            makeAnOffer = itemView.findViewById(R.id.make_an_offer);
            makeAnOffer2 = itemView.findViewById(R.id.make_an_offer2);


            next = itemView.findViewById(R.id.next);
            previous = itemView.findViewById(R.id.previous);
        }
    }
}
