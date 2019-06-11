package com.swaas.kangle.LPCourse.Report;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swaas.kangle.R;
import com.swaas.kangle.utils.Constants;

public class AnswerPage extends AppCompatActivity {

    ImageView companylogo;
    Context mContext;
    TextView coursename1,question,questiontext,asnwer,answertext,SectionName;
    CoordinatorLayout activity_list;
    View header;
    RelativeLayout answerblock;
    String coursename,questionname,answername,sectioname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answers_page);
        mContext = AnswerPage.this;
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if(getIntent()!= null){
            coursename =  getIntent().getStringExtra("course");
            questionname =  getIntent().getStringExtra("question");
            answername =  getIntent().getStringExtra("answer");
            sectioname = getIntent().getStringExtra("section");
        }

        initialiseviews();
        setthemeforView();
        coursename1.setText(getString(R.string.course_name) + " " + coursename);
        questiontext.setText(questionname);
        answertext.setText(answername);
        SectionName.setText(sectioname);
        companylogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initialiseviews() {

        activity_list = (CoordinatorLayout) findViewById(R.id.parent_layout);
        SectionName = (TextView) findViewById(R.id.SectionName);
        coursename1 = (TextView) findViewById(R.id.coursename1);
        header = findViewById(R.id.header);
        companylogo = (ImageView) findViewById(R.id.companylogo);
        question = (TextView) findViewById(R.id.question);
        questiontext = (TextView) findViewById(R.id.questiontext);
        asnwer = (TextView) findViewById(R.id.asnwer);
        answertext = (TextView) findViewById(R.id.answertext);
        answerblock = (RelativeLayout) findViewById(R.id.answerblock);

    }
    public void setthemeforView(){

        header.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
        activity_list.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
        companylogo.setColorFilter(Color.parseColor(Constants.TEXT_COLOR));
        SectionName.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        coursename1.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        question.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        asnwer.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        questiontext.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        answertext.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        //answerblock.setBackground(getDrawable(R.drawable.background_corner_radius));
        answerblock.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
        shape.setCornerRadius(25);
        answerblock.setBackground(shape);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
