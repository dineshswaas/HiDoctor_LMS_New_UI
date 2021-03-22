package com.swaas.kangle.LPCourse;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.jackandphantom.circularprogressbar.CircleProgressbar;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;

import com.swaas.kangle.R;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.Constants;
import com.swaas.kangle.utils.DateHelper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

import static java.lang.System.load;

public class CourseListAdapterGame extends RecyclerView.Adapter<CourseListAdapterGame.CoursesRecyclerHolder> {
    /* access modifiers changed from: private */
    public static MyClickListener myClickListener;
    Context context;
    List<CourseModel> courseModelList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    public interface MyClickListener {
        void onItemClick(int i);
    }

    public CourseListAdapterGame(Context context2, List<CourseModel> courseModels) {
        this.context = context2;
        this.courseModelList = courseModels;
    }

    public void setCourseListadapter(List<CourseModel> courseModelList2) {
        this.courseModelList = courseModelList2;
        notifyDataSetChanged();
    }

    public CoursesRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CoursesRecyclerHolder(LayoutInflater.from(this.context).inflate(R.layout.course_recycler, parent, false), this.context);
    }

    public void onBindViewHolder(CoursesRecyclerHolder holder, int position) {
        int progresspercent;
        int progresspercent2;
        int progresspercent3;
        CoursesRecyclerHolder coursesRecyclerHolder = holder;
        final CourseModel courseModel = this.courseModelList.get(position);
        setthemeforView(holder);
        coursesRecyclerHolder.mTitle.setText(courseModel.getCourse_Name());
        TextView textView = coursesRecyclerHolder.mSubTitle;
        textView.setText(courseModel.getSectionDetails().size() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.section_s));
        TextView textView2 = coursesRecyclerHolder.mSubTitle1;
        textView2.setText(courseModel.getNo_of_Assets() + "");
        TextView textView3 = coursesRecyclerHolder.mSubTitle2;
        textView3.setText(courseModel.getNo_of_Questions() + "");
        TextView textView4 = coursesRecyclerHolder.mSubTitle3;
        textView4.setText((courseModel.getNo_Course_Checklist() + courseModel.getNo_Section_Checklist()) + "");
        int i = 0;
        if (courseModel.getPrerequisite().equalsIgnoreCase("") || courseModel.getCourse_Status_Value() != 0) {
            coursesRecyclerHolder.blockcourse.setVisibility(View.GONE);
        } else {
            coursesRecyclerHolder.blockcourse.setVisibility(View.VISIBLE);
        }
        coursesRecyclerHolder.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CourseListAdapterGame.myClickListener != null) {
                    CourseListAdapterGame.myClickListener.onItemClick(courseModel.getCourse_Id());
                }
            }
        });
        TextView textView5 = coursesRecyclerHolder.mCourseStartTime;
        textView5.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + courseModel.getValid_From());
        if (PreferenceUtils.getSubdomainName(this.context).contains("tacobell")) {
            coursesRecyclerHolder.mProgressStatus.setForegroundProgressColor(this.context.getResources().getColor(R.color.tacobellbackground));
            boolean isEmpty = TextUtils.isEmpty(courseModel.getCourse_Image_URL());
            Ion.with(coursesRecyclerHolder.mThumbnail).fitXY().placeholder((int) R.drawable.tacobell_default).fitXY().error((int) R.drawable.tacobell_default).fitXY().load(courseModel.getCourse_Image_URL());
            coursesRecyclerHolder.mThumbnail.setBackgroundTintList(ColorStateList.valueOf(this.context.getResources().getColor(R.color.black)));
            coursesRecyclerHolder.mThumbnail.setBackgroundColor(this.context.getResources().getColor(R.color.black));
        } else {
            coursesRecyclerHolder.mProgressStatus.setForegroundProgressColor(this.context.getResources().getColor(R.color.loginbutton));
            boolean isEmpty2 = TextUtils.isEmpty(courseModel.getCourse_Image_URL());
            Ion.with(coursesRecyclerHolder.mThumbnail).fitXY().placeholder((int) R.drawable.courses).fitXY().error((int) R.drawable.courses).fitXY().load(courseModel.getCourse_Image_URL());
            coursesRecyclerHolder.mThumbnail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Constants.ICON_COLOR)));
            coursesRecyclerHolder.mThumbnail.setBackgroundColor(Color.parseColor(Constants.ICON_COLOR));
        }
        if (courseModel.getCourse_Status_Value() == 2) {
            coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
            coursesRecyclerHolder.mCompletionString.setText("100%");
            coursesRecyclerHolder.mCourseStartTime.setVisibility(View.GONE);
            coursesRecyclerHolder.mCourseEndTime.setVisibility(View.VISIBLE);
            TextView textView6 = coursesRecyclerHolder.mCourseEndTime;
            textView6.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.completed_course));
            coursesRecyclerHolder.mProgressStatus.setProgress(100.0f);
            coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
            coursesRecyclerHolder.coursedirect.setText(this.context.getResources().getString(R.string.view));
            coursesRecyclerHolder.new_tag.setVisibility(View.GONE);
            if (courseModel.getEvaluation_Type() != 1) {
                TextView textView7 = coursesRecyclerHolder.mCourseEndTime;
                textView7.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.completed_course));
                coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.COMPLETED_COLOR));
            } else if (courseModel.getEvaluation_Type() == 1 && courseModel.getEvaluation_Status() == 1) {
                TextView textView8 = coursesRecyclerHolder.mCourseEndTime;
                textView8.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.completed_course));
                coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.COMPLETED_COLOR));
            } else {
                TextView textView9 = coursesRecyclerHolder.mCourseEndTime;
                textView9.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.pending_for_evaluation));
                coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.PENDING_APPROVAl_COLOR));
            }
        } else if (courseModel.getCourse_Status_Value() == 1) {
            double coursePercentage = (double) Math.round((Double.parseDouble(String.valueOf(courseModel.getNo_Of_Sections_Completed())) / Double.parseDouble(String.valueOf(courseModel.getTotal_Sections()))) * 100.0d);
            if (coursePercentage > 0.0d) {
                int progresspercent4 = Integer.parseInt(String.valueOf(Math.round(coursePercentage)));
                coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
                coursesRecyclerHolder.mProgressStatus.setProgress((float) progresspercent4);
                coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
                coursesRecyclerHolder.mProgressStatus.setProgress((float) progresspercent4);
                TextView textView10 = coursesRecyclerHolder.mCompletionString;
                textView10.setText(progresspercent4 + this.context.getResources().getString(R.string.complete_percent));
            } else if (coursePercentage == 100.0d) {
                coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
                coursesRecyclerHolder.mProgressStatus.setProgress(100.0f);
                coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
                coursesRecyclerHolder.mCompletionString.setText("100%");
            } else {
                coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
                coursesRecyclerHolder.mCompletionString.setText("1%");
                coursesRecyclerHolder.mProgressStatus.setProgress(5.0f);
                coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
            }
            coursesRecyclerHolder.mCourseStartTime.setVisibility(View.GONE);
            coursesRecyclerHolder.mCourseEndTime.setVisibility(View.VISIBLE);
            coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
            coursesRecyclerHolder.coursedirect.setText(this.context.getResources().getString(R.string.resume_course));
            coursesRecyclerHolder.new_tag.setVisibility(View.GONE);
            if (courseModel.getIsCourseRestart() == 1) {
                coursesRecyclerHolder.mCourseEndTime.setText(this.context.getResources().getString(R.string.reassigned_by));
                coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.COMPLETED_COLOR));
            } else {
                TextView textView11 = coursesRecyclerHolder.mCourseEndTime;
                textView11.setText(this.context.getResources().getString(R.string.expires_on) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + courseModel.getValid_To());
                coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.COMPANY_COLOR));
                String displayFormat = DateHelper.getDisplayFormat(courseModel.getValid_To(), "dd-MMM-yyyy ");
                DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                Date curdate = new Date();
                Date Valid_To = new Date();
                try {
                    Valid_To = formatter.parse(courseModel.getValid_To());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int diffInDays = (int) ((Valid_To.getTime() - curdate.getTime()) / 86400000);
                if (diffInDays > 0 && diffInDays < 5) {
                    if (diffInDays == 0) {
                        coursesRecyclerHolder.mCourseEndTime.setText(R.string.expires_today);
                    } else if (diffInDays == 1) {
                        coursesRecyclerHolder.mCourseEndTime.setText(R.string.expires_tomo);
                    } else {
                        TextView textView12 = coursesRecyclerHolder.mCourseEndTime;
                        textView12.setText("2131689786\n" + diffInDays + R.string.days);
                    }
                    coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.EXPIRED_COLOR));
                }
            }
        } else if (courseModel.getCourse_Status_Value() == 0) {
            coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
            coursesRecyclerHolder.mCompletionString.setText(HelpFormatter.DEFAULT_OPT_PREFIX);
            coursesRecyclerHolder.mCourseStartTime.setVisibility(View.GONE);
            coursesRecyclerHolder.mCourseEndTime.setVisibility(View.VISIBLE);
            coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.COMPANY_COLOR));
            coursesRecyclerHolder.mProgressStatus.setProgress(0.0f);
            coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
            coursesRecyclerHolder.coursedirect.setText(this.context.getResources().getString(R.string.Begin));
            coursesRecyclerHolder.new_tag.setVisibility(View.GONE);
            if (courseModel.getIsCourseRestart() == 1) {
                coursesRecyclerHolder.mCourseEndTime.setText(this.context.getResources().getString(R.string.reassigned_by));
            } else {
                TextView textView13 = coursesRecyclerHolder.mCourseEndTime;
                textView13.setText(this.context.getResources().getString(R.string.expires_on) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + courseModel.getValid_To());
                String displayFormat2 = DateHelper.getDisplayFormat(courseModel.getValid_From(), "dd-MMM-yyyy");
                DateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
                Date curdate2 = new Date();
                Date Valid_To2 = new Date();
                try {
                    Valid_To2 = formatter2.parse(courseModel.getValid_To());
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
                int diffInDays2 = (int) ((Valid_To2.getTime() - curdate2.getTime()) / 86400000);
                if (diffInDays2 > 0 && diffInDays2 < 5) {
                    if (diffInDays2 == 0) {
                        coursesRecyclerHolder.mCourseEndTime.setText(R.string.expires_today);
                    } else if (diffInDays2 == 1) {
                        coursesRecyclerHolder.mCourseEndTime.setText(R.string.expires_tomo);
                    } else {
                        TextView textView14 = coursesRecyclerHolder.mCourseEndTime;
                        textView14.setText("2131689786\n" + diffInDays2 + R.string.days);
                    }
                    coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.EXPIRED_COLOR));
                }
            }
        } else if (courseModel.getCourse_Status_Value() == 3) {
            if (courseModel.getNo_Of_Sections_Completed() > 0) {
                progresspercent3 = Integer.parseInt(String.valueOf(Math.round((double) Math.round(100.0d * (Double.parseDouble(String.valueOf(courseModel.getNo_Of_Sections_Completed())) / Double.parseDouble(String.valueOf(courseModel.getTotal_Sections())))))));
            } else {
                progresspercent3 = 100;
            }
            coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
            coursesRecyclerHolder.mCompletionString.setText(HelpFormatter.DEFAULT_OPT_PREFIX);
            coursesRecyclerHolder.mCourseStartTime.setVisibility(View.GONE);
            coursesRecyclerHolder.mCourseEndTime.setVisibility(View.VISIBLE);
            TextView textView15 = coursesRecyclerHolder.mCourseEndTime;
            textView15.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.max_attempts_reached_shortened));
            coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.EXPIRED_COLOR));
            coursesRecyclerHolder.mProgressStatus.setProgress((float) progresspercent3);
            coursesRecyclerHolder.mProgressStatus.setForegroundProgressColor(this.context.getResources().getColor(R.color.topbar));
            coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
            coursesRecyclerHolder.coursedirect.setText(this.context.getResources().getString(R.string.Report));
            coursesRecyclerHolder.new_tag.setVisibility(View.GONE);
        } else if (courseModel.getCourse_Status_Value() == 4) {
            if (courseModel.getNo_Of_Sections_Completed() > 0) {
                progresspercent2 = Integer.parseInt(String.valueOf(Math.round((double) Math.round(100.0d * (Double.parseDouble(String.valueOf(courseModel.getNo_Of_Sections_Completed())) / Double.parseDouble(String.valueOf(courseModel.getTotal_Sections())))))));
            } else {
                progresspercent2 = 100;
            }
            coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
            coursesRecyclerHolder.mCompletionString.setText(HelpFormatter.DEFAULT_OPT_PREFIX);
            coursesRecyclerHolder.mCourseStartTime.setVisibility(View.GONE);
            coursesRecyclerHolder.mCourseEndTime.setVisibility(View.VISIBLE);
            TextView textView16 = coursesRecyclerHolder.mCourseEndTime;
            textView16.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.expired_shortened));
            coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.EXPIRED_COLOR));
            coursesRecyclerHolder.mProgressStatus.setProgress((float) progresspercent2);
            coursesRecyclerHolder.mProgressStatus.setForegroundProgressColor(this.context.getResources().getColor(R.color.topbar));
            coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
            coursesRecyclerHolder.coursedirect.setText(this.context.getResources().getString(R.string.Report));
            coursesRecyclerHolder.new_tag.setVisibility(View.GONE);
        } else if (courseModel.getCourse_Status_Value() == 5) {
            if (courseModel.getNo_Of_Sections_Completed() > 0) {
                progresspercent = Integer.parseInt(String.valueOf(Math.round((double) Math.round(100.0d * (Double.parseDouble(String.valueOf(courseModel.getNo_Of_Sections_Completed())) / Double.parseDouble(String.valueOf(courseModel.getTotal_Sections())))))));
            } else {
                progresspercent = 60;
            }
            coursesRecyclerHolder.mCompletionString.setVisibility(View.VISIBLE);
            TextView textView17 = coursesRecyclerHolder.mCompletionString;
            textView17.setText(progresspercent + "%");
            coursesRecyclerHolder.mCourseStartTime.setVisibility(View.GONE);
            coursesRecyclerHolder.mCourseEndTime.setVisibility(View.VISIBLE);
            TextView textView18 = coursesRecyclerHolder.mCourseEndTime;
            textView18.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.partialy_shortened));
            coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.PARTIALLY_COMPLETED_COLOR));
            coursesRecyclerHolder.mProgressStatus.setProgress((float) progresspercent);
            coursesRecyclerHolder.mProgressStatus.setScaleY(1.1f);
            coursesRecyclerHolder.coursedirect.setText(this.context.getResources().getString(R.string.view));
            coursesRecyclerHolder.new_tag.setVisibility(View.GONE);
        }
        coursesRecyclerHolder.coursedirect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CourseListAdapterGame.myClickListener.onItemClick(courseModel.getCourse_Id());
            }
        });
        if (courseModel.getEvaluation_Mode() != null && courseModel.getManual_Evaluation_Status() == 0 && courseModel.getEvaluation_Mode().equalsIgnoreCase("MANUAL") && courseModel.getCourse_Status_Value() != 0) {
            TextView textView19 = coursesRecyclerHolder.mCourseEndTime;
            textView19.setText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.context.getResources().getString(R.string.pending_for_evaluation));
            coursesRecyclerHolder.mCourseEndTime.setTextColor(Color.parseColor(Constants.PENDING_APPROVAl_COLOR));
        }
        List<CourseSectionProgressModel> sectionModelList = courseModel.getSectionDetails();
        if (sectionModelList != null) {
            LinearLayout l3 = new LinearLayout(this.context);
            l3.setOrientation(LinearLayout.VERTICAL);
            l3.setWeightSum((float) sectionModelList.size());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 12, 1.0f);
            int i2 = 10;
            params.setMargins(10, 0, 5, 0);
            for (CourseSectionProgressModel secmodel : sectionModelList) {
                View labl = new View(this.context);
                labl.setLayoutParams(params);
                labl.setPadding(i2, i, i2, i2);
                if (courseModel.getEvaluation_Mode() != null && courseModel.getEvaluation_Mode().equalsIgnoreCase("MANUAL") && !secmodel.getSection_Status_Value().equals(String.valueOf(i)) && courseModel.getManual_Evaluation_Status() == 0) {
                    labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_orange));
                } else if (secmodel.getSection_Status_Value().equals(String.valueOf(1))) {
                    labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_allcorners_bluecolor));
                } else {
                    if (secmodel.getSection_Status_Value().equals(String.valueOf(2))) {
                        labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_allcorners_green));
                    } else if (secmodel.getSection_Status_Value().equals(String.valueOf(3))) {
                        labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_allcorners_red));
                    } else if (secmodel.getSection_Status_Value().equals(String.valueOf(4))) {
                        labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_allcorners_red));
                    } else {
                        if (secmodel.getSection_Status_Value().equals(String.valueOf(5))) {
                            labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_allcorners_green));
                        } else {
                            labl.setBackgroundDrawable(this.context.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                        }
                        if(labl.getParent() != null) {
                            ((ViewGroup)labl.getParent()).removeView(labl); // <- fix
                        }
                        l3.addView(labl);
                        i = 0;
                        i2 = 10;
                    }
                    if(labl.getParent() != null) {
                        ((ViewGroup)labl.getParent()).removeView(labl); // <- fix
                    }
                    l3.addView(labl);
                    i = 0;
                    i2 = 10;
                }
                if(labl.getParent() != null) {
                    ((ViewGroup)labl.getParent()).removeView(labl); // <- fix
                }
                l3.addView(labl);
                i = 0;
                i2 = 10;
            }
            coursesRecyclerHolder.segmentprogresslayout.addView(l3);
        }
        coursesRecyclerHolder.mThumbnail.setImageResource(R.drawable.game_image);
    }

    public int getItemCount() {
        return this.courseModelList.size();
    }

    public int getItemViewType(int position) {
        return position;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void setOnItemClickListener(MyClickListener myClickListener2) {
        myClickListener = myClickListener2;
    }

    public void setthemeforView(CoursesRecyclerHolder holder) {
        holder.mTitle.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        holder.mSubTitle.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        holder.mSubTitle1.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        holder.mSubTitle2.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        holder.mSubTitle3.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        holder.cardViewLayout.setCardBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
    }

    public class CoursesRecyclerHolder extends RecyclerView.ViewHolder {
        View blockcourse;
        CardView cardViewLayout;
        TextView coursedirect;
        Context ctxt;
        TextView mCompletionString;
        TextView mCourseEndTime;
        TextView mCourseStartTime;
        CircleProgressbar mProgressStatus;
        TextView mSubTitle;
        TextView mSubTitle1;
        TextView mSubTitle2;
        TextView mSubTitle3;
        ImageView mThumbnail;
        TextView mTitle;
        View mView;
        View new_tag;
        LinearLayout segmentprogresslayout;

        public CoursesRecyclerHolder(View itemView, Context context) {
            super(itemView);
            this.ctxt = context;
            this.cardViewLayout = (CardView) itemView.findViewById(R.id.cardviewLayout);
            this.mTitle = (TextView) itemView.findViewById(R.id.texttitle);
            this.mSubTitle = (TextView) itemView.findViewById(R.id.textSubtitle);
            this.mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.mProgressStatus = (CircleProgressbar) itemView.findViewById(R.id.progress_status);
            this.mCompletionString = (TextView) itemView.findViewById(R.id.completion_string);
            this.mCourseStartTime = (TextView) itemView.findViewById(R.id.start_time);
            this.mCourseEndTime = (TextView) itemView.findViewById(R.id.end_time);
            this.blockcourse = itemView.findViewById(R.id.blockcourse);
            this.coursedirect = (TextView) itemView.findViewById(R.id.coursedirect);
            this.new_tag = itemView.findViewById(R.id.new_tag);
            this.segmentprogresslayout = (LinearLayout) itemView.findViewById(R.id.segmentprogresslayout);
            this.mSubTitle1 = (TextView) itemView.findViewById(R.id.textSubtitle1);
            this.mSubTitle2 = (TextView) itemView.findViewById(R.id.textSubtitle2);
            this.mSubTitle3 = (TextView) itemView.findViewById(R.id.textSubtitle3);
        }
    }

    public CourseModel getItemAt(int position) {
        return this.courseModelList.get(position);
    }
}
