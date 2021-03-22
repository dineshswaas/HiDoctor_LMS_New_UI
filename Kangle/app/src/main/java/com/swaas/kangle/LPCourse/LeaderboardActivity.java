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
import android.widget.ImageView;
import android.widget.TextView;
import com.swaas.kangle.LPCourse.model.LeaderBoardModel;
import com.swaas.kangle.R;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LeaderboardActivity extends AppCompatActivity {
    ImageView back;
    int courseID;
    TextView gamename;
    List<LeaderBoardModel> leaderBoardModelList1 = new ArrayList();
    Leaderadapter leaderadapter;
    RecyclerView listView;
    Context mContext;
    ProgressDialog mProgressDialog;
    TextView name;
    TextView percentage;
    TextView rank;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.leaderboard);
        this.mContext = this;
        this.rank = (TextView) findViewById(R.id.rank);
        this.name = (TextView) findViewById(R.id.name);
        this.percentage = (TextView) findViewById(R.id.perecentagetext1);
        this.gamename = (TextView) findViewById(R.id.gamename);
        this.listView = (RecyclerView) findViewById(R.id.listleader);
        this.back = (ImageView) findViewById(R.id.companylogo);
        this.courseID = getIntent().getIntExtra("courseid", 0);
        getleaderboard();
        this.listView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.leaderadapter = new Leaderadapter(this.mContext, this.leaderBoardModelList1);
        this.listView.setAdapter(this.leaderadapter);
        this.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LeaderboardActivity.this.onBackPressed();
            }
        });
    }

    public void getleaderboard() {
        if (NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            showProgressDialog();
            ((LPCourseService) RetrofitAPIBuilder.getInstance().create(LPCourseService.class)).getleaderboard(PreferenceUtils.getCompnayId(this.mContext), this.courseID).enqueue(new Callback<ArrayList<LeaderBoardModel>>() {
                public void onResponse(Response<ArrayList<LeaderBoardModel>> response, Retrofit retrofit3) {
                    List<LeaderBoardModel> leaderBoardModelList = response.body();
                    if (leaderBoardModelList == null || leaderBoardModelList.size() <= 0) {
                        LeaderboardActivity.this.dismissProgressDialog();
                        return;
                    }
                    LeaderboardActivity.this.leaderBoardModelList1 = leaderBoardModelList;
                    for (int i = 0; i < leaderBoardModelList.size(); i++) {
                        if (leaderBoardModelList.get(i).getUserId() == PreferenceUtils.getUserId(LeaderboardActivity.this.mContext)) {
                            LeaderboardActivity.this.rank.setText(String.valueOf(leaderBoardModelList.get(i).getRank()));
                            LeaderboardActivity.this.name.setText(String.valueOf(leaderBoardModelList.get(i).getEmployeeName()));
                            LeaderboardActivity.this.percentage.setText(String.valueOf(leaderBoardModelList.get(i).getAchievedPercentage()));
                            LeaderboardActivity.this.gamename.setText(leaderBoardModelList.get(i).getCourseName());
                        }
                    }
                    LeaderboardActivity.this.leaderadapter = new Leaderadapter(LeaderboardActivity.this.mContext, LeaderboardActivity.this.leaderBoardModelList1);
                    LeaderboardActivity.this.listView.setAdapter(LeaderboardActivity.this.leaderadapter);
                    LeaderboardActivity.this.dismissProgressDialog();
                }

                public void onFailure(Throwable t) {
                    Log.d(t.toString(), "Error");
                    LeaderboardActivity.this.dismissProgressDialog();
                }
            });
        }
    }

    public void showProgressDialog() {
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage(getResources().getString(R.string.loading));
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setProgressStyle(16973854);
        this.mProgressDialog.setIndeterminate(false);
        this.mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (!isFinishing() && this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }
}
