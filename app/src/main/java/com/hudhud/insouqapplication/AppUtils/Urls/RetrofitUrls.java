package com.hudhud.insouqapplication.AppUtils.Urls;

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs;
import com.hudhud.insouqapplication.AppUtils.Responses.AddAdsResponse;
import com.hudhud.insouqapplication.AppUtils.Responses.UserFS;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitUrls {

    //Electronics.
    @Multipart
    @POST("Add" )
    Call<AddAdsResponse> addElectronicAd(
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("age") RequestBody age,
            @Part("usage") RequestBody usage,
            @Part("price") RequestBody price,
            @Part("color") RequestBody color,
            @Part("warranty") RequestBody warranty,
            @Part("subCategoryId") RequestBody subCategoryId,
            @Part("otherSubCategory") RequestBody otherSubCategory,
            @Part("subTypeId") RequestBody subTypeId,
            @Part("otherSubType") RequestBody otherSubType,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    @Multipart
    @POST("Add" )
    Call<AddAdsResponse> addElectronicAd2(
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("age") RequestBody age,
            @Part("usage") RequestBody usage,
            @Part("price") RequestBody price,
            @Part("color") RequestBody color,
            @Part("warranty") RequestBody warranty,
            @Part("subCategoryId") RequestBody subCategoryId,
            @Part("otherSubCategory") RequestBody otherSubCategory,
            @Part("subTypeId") RequestBody subTypeId,
            @Part("otherSubType") RequestBody otherSubType,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part("Version") RequestBody version,
            @Part("Ram") RequestBody ram,
            @Part("Storage") RequestBody storage,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Business.
    @Multipart
    @POST("Add" )
    Call<AddAdsResponse> addBusinessAd(
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("otherCategoryName") RequestBody otherCategoryId,
            @Part("subCategoryId") RequestBody subCategoryId,
            @Part("OtherSubCategoryName") RequestBody otherSubCategory,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    @Multipart
    @POST("Add" )
    Call<AddAdsResponse> updateBusinessAd(
            @Part("Title") RequestBody title,
            @Part("Description") RequestBody description,
            @Part("Location") RequestBody location,
            @Part("Lat") RequestBody lat,
            @Part("Lng") RequestBody lng,
            @Part("Price") RequestBody price,
            @Part("MainPhoto") RequestBody mainPhoto,
            @Part("CategoryId") RequestBody categoryId,
            @Part("OtherCategoryName") RequestBody otherCategoryId,
            @Part("SubCategoryId") RequestBody subCategoryId,
            @Part("OtherSubCategoryName") RequestBody otherSubCategory,
            @Part("PhoneNumber") RequestBody phoneNumber,
            @Part("AdId") RequestBody adId,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Classifieds.
    @Multipart
    @POST("AddFullClassified" )
    Call<AddAdsResponse> addClassifiedsAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("age") RequestBody age,
            @Part("usage") RequestBody usage,
            @Part("brand") RequestBody brand,
            @Part("condition") RequestBody condition,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Services.
    @Multipart
    @POST("Add" )
    Call<AddAdsResponse> addServicesAd(
            @Part("title") RequestBody title,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("subCategoryId") RequestBody subCategoryId,
            @Part("categoryId") RequestBody categoryId,
            @Part("otherSubCategory") RequestBody otherSubCategory,
            @Part("carLiftFrom") RequestBody carLiftFrom,
            @Part("carLiftTo") RequestBody carLiftTo,
            @Part("phoneNumber") RequestBody phoneNumber
    );

    //Used cars.
    @Multipart
    @POST("AddFullMotor" )
    Call<AddAdsResponse> addUsedCarsAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("kilometers") RequestBody kilometers,
            @Part("regionalSpecs") RequestBody regionalSpecs,
            @Part("color") RequestBody color,
            @Part("doors") RequestBody doors,
            @Part("warranty") RequestBody warranty,
            @Part("transmission") RequestBody transmission,
            @Part("bodyType") RequestBody bodyType,
            @Part("fuelType") RequestBody fuelType,
            @Part("noOfCylinders") RequestBody noOfCylinders,
            @Part("steeringSide") RequestBody steeringSide,
            @Part("horsepower") RequestBody horsepower,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Boats.
    @Multipart
    @POST("AddFullMotor" )
    Call<AddAdsResponse> addBoatsAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("age") RequestBody age,
            @Part("usage") RequestBody usage,
            @Part("condition") RequestBody condition,
            @Part("length") RequestBody length,
            @Part("warranty") RequestBody warranty,
            @Part("horsepower") RequestBody horsepower,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Parts.
    @Multipart
    @POST("AddFullMotor" )
    Call<AddAdsResponse> addPartsAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("nameOfPart") RequestBody nameOfPart,
            @Part("age") RequestBody age,
            @Part("condition") RequestBody condition,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Bikes.
    @Multipart
    @POST("AddFullMotor" )
    Call<AddAdsResponse> addBikesAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("kilometers") RequestBody kilometers,
            @Part("engineSize") RequestBody engineSize,
            @Part("color") RequestBody color,
            @Part("wheels") RequestBody wheels,
            @Part("warranty") RequestBody warranty,
            @Part("usage") RequestBody usage,
            @Part("sellerType") RequestBody sellerType,
            @Part("fuelType") RequestBody fuelType,
            @Part("noOfCylinders") RequestBody noOfCylinders,
            @Part("finalDriveSystem") RequestBody finalDriveSystem,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Job Wanted.
    @Multipart
    @POST("AddFullJobWanted" )
    Call<AddAdsResponse> addJobWantedAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("gender") RequestBody gender,
            @Part("categoryId") RequestBody categoryId,
            @Part("nationality") RequestBody nationality,
            @Part("currentLocation") RequestBody currentLocation,
            @Part("educationLevel") RequestBody educationLevel,
            @Part("currentPosition") RequestBody currentPosition,
            @Part("workExperience") RequestBody workExperience,
            @Part("commitment") RequestBody commitment,
            @Part("noticePeriod") RequestBody noticePeriod,
            @Part("careerLevel") RequestBody careerLevel,
            @Part("visaStatus") RequestBody visaStatus,
            @Part("expectedMonthlySalary") RequestBody expectedMonthlySalary,
            @Part("CvFile64") RequestBody CVFile
    );

    //Machinery.
    @Multipart
    @POST("AddFullMotor" )
    Call<AddAdsResponse> addMachineryAd(
            @Part("adId") RequestBody adId,
            @Part("description") RequestBody description,
            @Part("location") RequestBody location,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("price") RequestBody price,
            @Part("mainPhoto") RequestBody mainPhoto,
            @Part("categoryId") RequestBody categoryId,
            @Part("kilometers") RequestBody kilometers,
            @Part("capacity") RequestBody capacity,
            @Part("warranty") RequestBody warranty,
            @Part("fuelType") RequestBody fuelType,
            @Part("noOfCylinders") RequestBody noOfCylinders,
            @Part("horsepower") RequestBody horsepower,
            @Part("condition") RequestBody condition,
            @Part("sellerType") RequestBody sellerType,
            @Part("mechanicalCondition") RequestBody mechanicalCondition,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part ArrayList<MultipartBody.Part> pictures
    );

    //Apply.
    @Multipart
    @POST("ApplyForJob" )
    Call<AddAdsResponse> applyForJob(
            @Part("AdId") RequestBody adId,
            @Part("DefaultLanguage") RequestBody defaultLanguage,
            @Part("DOB") RequestBody DOB,
            @Part("CoverNote") RequestBody coverNote,
            @Part("Gender") RequestBody gender,
            @Part("CurrentCompany") RequestBody currentCompany,
            @Part("Nationality") RequestBody nationality,
            @Part("EducationLevel") RequestBody educationLevel,
            @Part("CurrentPosition") RequestBody currentPosition,
            @Part("WorkExperience") RequestBody workExperience,
            @Part("Commitment") RequestBody commitment,
            @Part("NoticePeriod") RequestBody noticePeriod,
            @Part("VisaStatus") RequestBody visaStatus,
            @Part("CareerLevel") RequestBody careerLevel,
            @Part("PhoneNumber") RequestBody phoneNumber,
            @Part("CvFile64") RequestBody CVFile,
            @Part("Id") RequestBody id
    );

    //Update profile
    @Multipart
    @POST("UpdateProfile" )
    Call<UserFS> updateProfile(
            @Part("FirstName") RequestBody firstName,
            @Part("LastName") RequestBody lastName,
            @Part("Gender") RequestBody gender,
            @Part("DOB") RequestBody DOB,
            @Part("Nationality") RequestBody nationality,
            @Part("DefaultLocation") RequestBody defaultLocation,
            @Part("DefaultLanguage") RequestBody defaultLanguage,
            @Part("CareerLevel") RequestBody careerLevel,
            @Part("Education") RequestBody education,
            @Part("CurrentPosition") RequestBody currentPosition,
            @Part("CurrentCompany") RequestBody currentCompany,
            @Part("CoverNote") RequestBody coverNote,
            @Part("CideInfromation") RequestBody hideInformation,
            @Part("CvFile64") RequestBody CVFile,
            @Part("IndustryFile64") RequestBody industryFile,
            @Part MultipartBody.Part profilePic
    );

    //Add opening job
    @FormUrlEncoded
    @POST("AddJobOpening")
    Call<AddAdsResponse> addOpeningJob(
            @Field("title") String title,
            @Field("description") String description,
            @Field("location") String location,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("categoryId") String categoryId,
            @Field("jobType") String jobType,
            @Field("otherJobType") String otherJobType,
            @Field("phoneNumber") String phoneNumber,
            @Field("careerLevel") String careerLevel,
            @Field("employmentType") String employmentType,
            @Field("minWorkExperience") String minWorkExperience,
            @Field("minEducationLevel") String minEducationLevel,
            @Field("companyName") String companyName
    );


}
