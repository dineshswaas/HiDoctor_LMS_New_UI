package com.swaas.kangle.LPCourse;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.swaas.kangle.R;
import com.swaas.kangle.utils.Constants;
import java.util.List;

public class CourseCategoryListAdapterGame extends RecyclerView.Adapter<CourseCategoryListAdapterGame.ViewHolder> {
    List<CourseModel> cat;
    GameActivity mActivity;
    boolean mShowunchecked;

    public CourseCategoryListAdapterGame(Context context, List<CourseModel> categoryList, boolean showunchecked) {
        this.mActivity = (GameActivity) context;
        this.cat = categoryList;
        this.mShowunchecked = showunchecked;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mActivity).inflate(R.layout.course_category_list_item, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CourseModel categories = this.cat.get(position);
        setthemeforView(holder);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (categories.iscatchecked) {
                    holder.checkboxselected.setChecked(false);
                    categories.setIscatchecked(false);
                } else {
                    holder.checkboxselected.setChecked(true);
                    categories.setIscatchecked(true);
                }
                CourseCategoryListAdapterGame.this.mActivity.filteredcatList(categories);
            }
        });
        if (categories.iscatchecked) {
            holder.checkboxselected.setVisibility(0);
            holder.checkboxselected.setChecked(true);
        } else if (this.mShowunchecked) {
            holder.checkboxselected.setVisibility(0);
            holder.checkboxselected.setChecked(false);
        } else {
            holder.checkboxselected.setVisibility(8);
        }
        holder.categoryname.setText(categories.getCategory_Name().toString());
        holder.checkboxselected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (categories.iscatchecked) {
                    holder.checkboxselected.setChecked(false);
                    categories.setIscatchecked(false);
                } else {
                    holder.checkboxselected.setChecked(true);
                    categories.setIscatchecked(true);
                }
                CourseCategoryListAdapterGame.this.mActivity.filteredcatList(categories);
            }
        });
    }

    public int getItemCount() {
        try {
            return this.cat.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void setthemeforView(ViewHolder holder) {
        holder.checkboxselected.setButtonTintList(ColorStateList.valueOf(Color.parseColor(Constants.COMPANY_COLOR)));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView categoryname;
        final CheckBox checkboxselected;
        final View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView.findViewById(R.id.category_item);
            this.categoryname = (TextView) itemView.findViewById(R.id.categorey_name);
            this.checkboxselected = (CheckBox) itemView.findViewById(R.id.checkboxselected);
        }
    }
}
