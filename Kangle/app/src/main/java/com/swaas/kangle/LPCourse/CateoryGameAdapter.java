package com.swaas.kangle.LPCourse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.swaas.kangle.EmptyRecyclerView;
import com.swaas.kangle.LPCourse.model.GameCategory;
import com.swaas.kangle.LPCourse.model.GameCategoryWords;
import com.swaas.kangle.LPCourse.model.LeaderBoardModel;
import com.swaas.kangle.R;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CateoryGameAdapter extends RecyclerView.Adapter<CateoryGameAdapter.GameRecyclerHolder> {
    private Context context;
    List<GameCategory> gameCategoryList = new ArrayList();
    ProgressDialog mProgressDialog;
    Retrofit retrofitAPI;
    LPCourseService lpService;
    public static GameCategoryWords gameCategoryWords = new GameCategoryWords();
    public CateoryGameAdapter(Context ctx, List<GameCategory> gameCategoryList) {
        this.context = ctx;
        this.gameCategoryList = gameCategoryList;
    }

    @NonNull
    public CateoryGameAdapter.GameRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.game_adapter, parent, false);
        return new CateoryGameAdapter.GameRecyclerHolder(view,context);
    }

    public void onBindViewHolder(GameRecyclerHolder holder, int position) {
        holder.logo.setImageResource(R.mipmap.hangmanlogo);
        if(gameCategoryList.get(position).getCategoryImageURL()!=null) {
            Ion.with(holder.logo).load(gameCategoryList.get(position).getCategoryImageURL());
        }
        holder.name.setText(String.valueOf(this.gameCategoryList.get(position).getCategoryName()));
        holder.touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getquestions();

            }
        });
    }
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getResources().getString(R.string.loading));
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
    private void getquestions() {

        if(NetworkUtils.checkIfNetworkAvailable(context)){

                showProgressDialog();
            retrofitAPI = RetrofitAPIBuilder.getInstance();
            lpService = retrofitAPI.create(LPCourseService.class);
            int CompanyId  = PreferenceUtils.getCompnayId(context);
            int gameid = 5;
            int userId = PreferenceUtils.getUserId(context);
            Call call = lpService.getgamecategoryQuestions(99,30667,gameid);


            call.enqueue(new Callback<GameCategoryWords>() {

                @Override
                public void onResponse(Response<GameCategoryWords> response, Retrofit retrofit) {
                    GameCategoryWords gameCategories= response.body();
                    if (gameCategories != null && gameCategories.getLstwords().size() > 0) {
                        gameCategoryWords = gameCategories;
                        dismissProgressDialog();
                        Intent intent = new Intent(context,HangmanGame.class);
                        context.startActivity(intent);
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

    public int getItemCount() {
        return this.gameCategoryList.size();
    }

    public class GameRecyclerHolder extends RecyclerView.ViewHolder {
        TextView name;
        Context ctxt;


        ImageView logo;
        RelativeLayout touch;

        public GameRecyclerHolder(View itemView, Context context) {
            super(itemView);
            this.ctxt = context;
            this.logo = (ImageView) itemView.findViewById(R.id.category_image);
            this.name = (TextView) itemView.findViewById(R.id.category_text);
            this.touch = itemView.findViewById(R.id.touchview);
        }
    }
}
