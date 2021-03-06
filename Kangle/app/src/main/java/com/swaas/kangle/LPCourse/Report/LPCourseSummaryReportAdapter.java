package com.swaas.kangle.LPCourse.Report;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swaas.kangle.LPCourse.model.LPCourseReportSummaryModel;
import com.swaas.kangle.R;
import com.swaas.kangle.utils.Constants;
import com.swaas.kangle.utils.DateHelper;

import java.util.List;

/**
 * Created by Sayellessh on 29-08-2017.
 */

public class LPCourseSummaryReportAdapter extends RecyclerView.Adapter<LPCourseSummaryReportAdapter.ViewHolder> {

    Context context;
    List<LPCourseReportSummaryModel> courseReportModelList;
    private static MyClickListener myClickListener;
    DateHelper datehelper;

    public LPCourseSummaryReportAdapter(Context context, List<LPCourseReportSummaryModel> courseModels) {
        this.context = context;
        this.courseReportModelList = courseModels;
        datehelper = new DateHelper();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lp_course_report_summary_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LPCourseReportSummaryModel courseReportModel = courseReportModelList.get(position);

        setthemeforView(holder);

        holder.questiontitle.setText(courseReportModel.getQuestion_Text());
        if (courseReportModel.is_Correct()) {
            //holder.result.setBackgroundColor(context.getResources().getColor(R.color.buttoncolor));
            holder.result_icon.setImageResource(R.drawable.ic_check_circle_black_48dp);
        } else {
            //holder.result.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.result_icon.setImageResource(R.drawable.ic_cancel_black_48dp);
        }

        if(!courseReportModel.getExplanation().equalsIgnoreCase("")){
            holder.showsummary.setVisibility(View.VISIBLE);
            holder.showsummary.setImageResource(R.drawable.ic_info_black_24dp);
        }

        holder.showsummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(courseReportModel.getExplanation());
            }
        });

        if(position == courseReportModelList.size()-1) {
            holder.end_line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return courseReportModelList.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void setthemeforView(final ViewHolder holder) {

        holder.questiontitle.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        holder.showsummary.setColorFilter(Color.parseColor(Constants.TEXT_COLOR));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView showsummary,result_icon;
        final TextView questiontitle;
        final View result,end_line;

        public ViewHolder(View itemView) {
            super(itemView);

            result = itemView.findViewById(R.id.viewresult);
            result_icon = (ImageView) itemView.findViewById(R.id.viewresult_icon);
            showsummary = (ImageView) itemView.findViewById(R.id.viewsummary_icon);
            questiontitle = (TextView) itemView.findViewById(R.id.attemptdate);
            end_line = itemView.findViewById(R.id.end_line);
        }
    }

    public interface MyClickListener {
        public void onItemClick(String explanation);
    }

    public LPCourseReportSummaryModel getItemAt(int position) {
        return courseReportModelList.get(position);
    }
}
