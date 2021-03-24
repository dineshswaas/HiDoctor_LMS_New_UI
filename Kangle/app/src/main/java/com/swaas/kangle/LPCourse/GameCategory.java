package com.swaas.kangle.LPCourse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.swaas.kangle.CheckList.model.UserforCourseChecklist;
import com.swaas.kangle.EmptyRecyclerView;
import com.swaas.kangle.R;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.NetworkUtils;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GameCategory extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    Retrofit retrofitAPI;
    LPCourseService lpService;
    RecyclerView categorylist;
    Context mContext;
    CateoryGameAdapter cateoryGameAdapter;
    ArrayList<com.swaas.kangle.LPCourse.model.GameCategory> gameCategory = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_category);
        categorylist = (RecyclerView) findViewById(R.id.catergorylist);
        mContext = this;
        categorylist.setLayoutManager(new LinearLayoutManager(mContext, 1, false));
        cateoryGameAdapter = new CateoryGameAdapter(mContext, gameCategory);
        categorylist.setAdapter(cateoryGameAdapter);
        getcategory();

    }

    public void getcategory() {

        if(NetworkUtils.checkIfNetworkAvailable(getApplicationContext())){
            if(!isFinishing()){
                showProgressDialog();
            }
            retrofitAPI = RetrofitAPIBuilder.getInstance();
            lpService = retrofitAPI.create(LPCourseService.class);
            int CompanyId  = PreferenceUtils.getCompnayId(getApplicationContext());
             int gameid = 1;
            int userId = PreferenceUtils.getUserId(getApplicationContext());
            Call call = lpService.getgamecategory(99,30667,gameid);


            call.enqueue(new Callback<ArrayList<com.swaas.kangle.LPCourse.model.GameCategory>>() {

                @Override
                public void onResponse(Response<ArrayList<com.swaas.kangle.LPCourse.model.GameCategory>> response, Retrofit retrofit) {
                    ArrayList<com.swaas.kangle.LPCourse.model.GameCategory> gameCategories= response.body();
                    if (gameCategories != null && gameCategories.size() > 0) {
                        gameCategory = gameCategories;

                        if(!isFinishing())
                            dismissProgressDialog();

                        categorylist.setLayoutManager(new LinearLayoutManager(mContext, 1, false));
                        cateoryGameAdapter = new CateoryGameAdapter(mContext, gameCategory);
                        categorylist.setAdapter(cateoryGameAdapter);
                    } else {
                        //courseListModel is null
                        dismissProgressDialog();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    dismissProgressDialog();
                    Log.d(t.toString(),"Error");
                }
            });

        }

    }
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
