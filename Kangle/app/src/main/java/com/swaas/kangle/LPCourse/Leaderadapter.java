package com.swaas.kangle.LPCourse;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.swaas.kangle.LPCourse.model.LeaderBoardModel;
import com.swaas.kangle.R;

import java.util.ArrayList;
import java.util.List;

public class Leaderadapter extends RecyclerView.Adapter<Leaderadapter.LeaderRecyclerHolder> {
    private Context context;
    List<LeaderBoardModel> leaderBoardModelList = new ArrayList();

    public Leaderadapter(Context ctx, List<LeaderBoardModel> leaderBoardModels) {
        this.context = ctx;
        this.leaderBoardModelList = leaderBoardModels;
    }

    @NonNull
    public LeaderRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LeaderRecyclerHolder(LayoutInflater.from(this.context).inflate(R.layout.leaderlistitem, parent, false), this.context);
    }

    public void onBindViewHolder(@NonNull LeaderRecyclerHolder holder, int position) {
        holder.rank.setText(String.valueOf(this.leaderBoardModelList.get(position).getRank()));
        holder.name.setText(String.valueOf(this.leaderBoardModelList.get(position).getEmployeeName()));
        holder.percentage.setText(String.valueOf(this.leaderBoardModelList.get(position).getAchievedPercentage()));
    }

    public int getItemCount() {
        return this.leaderBoardModelList.size();
    }

    public class LeaderRecyclerHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView percentage;
        TextView rank;

        public LeaderRecyclerHolder(View itemView, Context context) {
            super(itemView);
            this.rank = (TextView) itemView.findViewById(R.id.rank);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.percentage = (TextView) itemView.findViewById(R.id.perecentage);
        }
    }
}
