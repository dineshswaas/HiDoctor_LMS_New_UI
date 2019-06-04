package com.swaas.kangle.UploadService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.swaas.kangle.API.model.User;
import com.swaas.kangle.API.service.AssetService;
import com.swaas.kangle.db.DigitalAssetTransactionChildRepository;
import com.swaas.kangle.db.DigitalAssetTransactionRepository;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.models.ChildAnalyticsModel;
import com.swaas.kangle.models.DigitalAssetTransactionChild;
import com.swaas.kangle.models.TagAnalytics;
import com.swaas.kangle.playerPart.DigitalAssets;
import com.swaas.kangle.preferences.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Sayellessh on 20-09-2017.
 */

public class DigitalAssetAnalyticsUpSyncService extends IntentService {


    //Context mContext;
    int index = 0;
    int childno = 0;
    List<DigitalAssets> digitalAssetslocalList;
    List<DigitalAssetTransactionChild> uploadchildassets;
    DigitalAssetTransactionChildRepository digitalAssetTransactionChildRepository;

    public DigitalAssetAnalyticsUpSyncService() {
        super("DigitalAssetAnalyticsUpSyncService");
        //mContext = getApplicationContext();

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        getDigitalAssetAnalyticsFromLocalDB();
    }

    private void getDigitalAssetAnalyticsFromLocalDB() {
        DigitalAssetTransactionRepository digitalAssetRepository = new DigitalAssetTransactionRepository(DigitalAssetAnalyticsUpSyncService.this);
        digitalAssetRepository.setmGetDA(new DigitalAssetTransactionRepository.GetDA() {
            @Override
            public void getDASuccess(ArrayList<DigitalAssets> digitalAssetsList) {
                if(digitalAssetsList != null && digitalAssetsList.size() > 0){
                    for (DigitalAssets digitalAssets : digitalAssetsList){
                        digitalAssetslocalList = digitalAssetsList;
                        digitalAssets.setSource_Of_Entry(1);
                        insertTaganalyticstoServer(digitalAssetsList,index);
                    }
                }else{
                    uploadChildAnalytics();
                }
            }

            @Override
            public void getDAFailure(String message) {

            }
        });

        digitalAssetRepository.getUnSyncedDigitalAssetAnalytics();
    }

    private void insertTaganalyticstoServer(List<DigitalAssets> digitalAsset,int index) {
        final DigitalAssets digitalAssets = digitalAsset.get(index);
        final DigitalAssetTransactionRepository digitalAssetRepository = new DigitalAssetTransactionRepository(DigitalAssetAnalyticsUpSyncService.this);
        Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
        final AssetService assetService = retrofitAPI.create(AssetService.class);
        Gson gsonget = new Gson();
        String subdomain = PreferenceUtils.getSubdomainName(DigitalAssetAnalyticsUpSyncService.this);
        User userobj = gsonget.fromJson(PreferenceUtils.getUser(DigitalAssetAnalyticsUpSyncService.this), User.class);
        TagAnalytics tag = new TagAnalytics();
        if(digitalAssets != null){
            tag.setCompany_Code(userobj.getCompany_Code());
            tag.setCompany_Id(userobj.getCompany_Id());
            tag.setCorrelationId("1");
            tag.setAppPlatform("Android");
            tag.setAppSuiteId("1");
            tag.setAppId("2");
            tag.setDACode(Long.parseLong(String.valueOf(digitalAssets.getDA_Code())));
            tag.setUser_Code(userobj.getUser_Code());
            tag.setUser_Name(userobj.getEmployee_Name());
            tag.setRegion_Code(userobj.getRegion_Code());
            tag.setRegion_Name(userobj.getRegion_Name());
            tag.setLike(digitalAssets.getUser_Like());
            tag.setDislike(0);
            tag.setRating(digitalAssets.getUser_Rating());
        }
        Call call = assetService.insertTaganalytics(subdomain,tag);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String apiResponse = response.body();
                if (apiResponse != null && apiResponse.equalsIgnoreCase("true")) {
                    try {
                        insertAssetAnalyticstoServer(digitalAssets);
                    }catch (Exception e){
                        Log.d("",e.toString());
                    }
                } else {
                    Log.d("retrofit","error 2");
                    //error
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("parmDAToServerFailure",t.getMessage());
            }
        });
    }

    private void insertAssetAnalyticstoServer(final DigitalAssets digitalAssets){
        final DigitalAssetTransactionRepository digAssetRepository = new DigitalAssetTransactionRepository(DigitalAssetAnalyticsUpSyncService.this);
        Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
        final AssetService assetService = retrofitAPI.create(AssetService.class);
        Gson gsonget = new Gson();
        String subdomain = PreferenceUtils.getSubdomainName(DigitalAssetAnalyticsUpSyncService.this);
        User userobj = gsonget.fromJson(PreferenceUtils.getUser(DigitalAssetAnalyticsUpSyncService.this), User.class);
        TagAnalytics tagAnalytics = new TagAnalytics();
        if(digitalAssets != null) {
            tagAnalytics.setCompany_Code(userobj.getCompany_Code());
            tagAnalytics.setCompany_Id(userobj.getCompany_Id());
            tagAnalytics.setCorrelationId("1");
            tagAnalytics.setAppPlatform("Android");
            tagAnalytics.setAppSuiteId("1");
            tagAnalytics.setAppId("2");
            tagAnalytics.setDACode(Long.parseLong(String.valueOf(digitalAssets.getDA_Code())));
            tagAnalytics.setUser_Code(userobj.getUser_Code());
            tagAnalytics.setUser_Name(userobj.getEmployee_Name());
            tagAnalytics.setRegion_Code(userobj.getRegion_Code());
            tagAnalytics.setRegion_Name(userobj.getRegion_Name());
            if (userobj.getDivision_Code() == null
                    || userobj.getDivision_Code().equalsIgnoreCase("")) {
                tagAnalytics.setDivisionCode("0");
            } else {
                tagAnalytics.setDivisionCode(userobj.getDivision_Code());
            }
            tagAnalytics.setDivisionName(userobj.getDivision_Name());
            if (digitalAssets.getPlayMode() == 0) {
                tagAnalytics.setOfflinePlay("1");
                tagAnalytics.setOnlinePlay("0");
            } else {
                tagAnalytics.setOnlinePlay("1");
                tagAnalytics.setOfflinePlay("0");
            }
            tagAnalytics.setDownload(String.valueOf(digitalAssets.getIs_Downloaded()));
            tagAnalytics.setPlayTime(String.valueOf(digitalAssets.getPlayed_Time_Duration()));
            tagAnalytics.setLattitude(String.valueOf(digitalAssets.getLattitude()));
            tagAnalytics.setLongitude(String.valueOf(digitalAssets.getLongitude()));
        }

        Call call = assetService.insertELItemizedBilling(subdomain,tagAnalytics);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                String apiResponse = response.body();
                if (apiResponse != null && apiResponse.equalsIgnoreCase("true")) {
                    try {
                        digAssetRepository.updateSyncedRecord(digitalAssets.getId());
                        index = index+1;
                        //digAssetRepository.RecordDelete(digitalAssets.getId());
                        if(index < digitalAssetslocalList.size()){
                            insertTaganalyticstoServer(digitalAssetslocalList,index);
                        }else{
                            digAssetRepository.RecordDelete();
                            uploadChildAnalytics();
                        }
                    }catch (Exception e){
                        Log.d("",e.toString());
                    }
                } else {
                    Log.d("retrofit","error 2");
                    //error
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("parmDAToServerFailure",t.getMessage());
            }
        });
    }

    public void uploadChildAnalytics(){
        digitalAssetTransactionChildRepository = new DigitalAssetTransactionChildRepository(getApplicationContext());
        uploadchildassets = digitalAssetTransactionChildRepository.getAllValues();
        if(uploadchildassets.size()>0){
            functionChildIndex(childno);
        }
    }

    public void functionChildIndex(int index){
        final DigitalAssetTransactionChild analyticsasset = uploadchildassets.get(index);
        Gson gsonget = new Gson();
        String subdomain = PreferenceUtils.getSubdomainName(DigitalAssetAnalyticsUpSyncService.this);
        User userobj = gsonget.fromJson(PreferenceUtils.getUser(DigitalAssetAnalyticsUpSyncService.this), User.class);

        ChildAnalyticsModel CHILDanalytics = new ChildAnalyticsModel();
        if(analyticsasset != null){
            CHILDanalytics.setCompany_Id(userobj.getCompany_Id());
            CHILDanalytics.setAsset_Id(Integer.parseInt(analyticsasset.getDAID()));
            CHILDanalytics.setImage_Id(analyticsasset.getSlideNo());
            CHILDanalytics.setDuration(analyticsasset.getPlaytime());
            CHILDanalytics.setViewed_By(userobj.getUserID());
            CHILDanalytics.setLocal_Time_Zone(analyticsasset.getRecordDate());
            Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
            retrofitAPI = RetrofitAPIBuilder.getInstance();
            AssetService assetService = retrofitAPI.create(AssetService.class);
            Call call = assetService.insertChildAssetAnalytics(subdomain,CHILDanalytics);
            call.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    String apiResponse= response.body();
                    if (apiResponse != null && apiResponse.equalsIgnoreCase("true")) {
                        try{
                            digitalAssetTransactionChildRepository.deleteRecord(analyticsasset.getSlNo());
                            childno++;
                            if(childno < uploadchildassets.size()){
                                functionChildIndex(childno);
                            }
                        }catch(Exception e){
                            Log.d("error",e.toString());
                        }
                    } else {
                        Log.d("retrofit","error 2");
                        //error
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("retrofit","error 2");
                }
            });
        }
    }
}
