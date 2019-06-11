package com.swaas.kangle.LPCourse.questionbuilder.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.swaas.kangle.LPCourse.model.QuestionAndAnswerModel;
import com.swaas.kangle.LPCourse.model.QuestionAnswerListModel;
import com.swaas.kangle.LPCourse.questionbuilder.CustomDialogClass;
import com.swaas.kangle.LPCourse.questionbuilder.QuestionActivity;
import com.swaas.kangle.R;
import com.swaas.kangle.utils.Constants;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by Hariharan on 17/8/17.
 */

public class MultiPleQuestionPerPage extends RecyclerView.Adapter<MultiPleQuestionPerPage.MyMultiPleQuestionViewHolder> {

    private Context mContext;
    private ArrayList<QuestionAndAnswerModel> questionAndAnswerModels;
    private QuestionActivity questionActivity;
    public MultiPleQuestionPerPage(Context context, ArrayList<QuestionAndAnswerModel> questionAndAnswerModels, QuestionActivity questionActivity){

       this.mContext = context;
       this.questionAndAnswerModels = questionAndAnswerModels;
        this.questionActivity = questionActivity;
    }

    @Override
    public MyMultiPleQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.multiplequestion_row_view,parent,false);
        return new MyMultiPleQuestionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyMultiPleQuestionViewHolder holder,final int position) {


        final QuestionAndAnswerModel questionAndAnswerModel = questionAndAnswerModels.get(position);

        holder.mQuestion_text.setText(questionAndAnswerModel.getQuestionModel().Question_Text);
        if(questionAndAnswerModel.getQuestionModel().getQuestion_Description().length() > 0) {
            holder.mDescriptionTextQuestion.setVisibility(View.VISIBLE);
            holder.mDescriptionTextQuestion.setText(questionAndAnswerModel.getQuestionModel().getQuestion_Description());
        }else{
            holder.mDescriptionTextQuestion.setVisibility(View.GONE);
        }

        if (questionAndAnswerModel.getQuestionModel().getQuestion_Image_Url()!=null && questionAndAnswerModel.getQuestionModel().getQuestion_Image_Url().length()>0){

            holder.mQuestionImageview.setVisibility(View.VISIBLE);
            Ion.with(mContext).load(questionAndAnswerModel.getQuestionModel().getQuestion_Image_Url()).intoImageView(holder.mQuestionImageview);

            holder.mQuestionImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomDialogClass cdd=new CustomDialogClass(questionActivity,questionAndAnswerModel.getQuestionModel().getQuestion_Image_Url());
                    cdd.show();

                }
            });


        }else {

            holder.mQuestionImageview.setVisibility(View.GONE);


        }

        if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == Constants.SINGLE_CHOICE_BUTTON_TYPE){

            final LinearLayout l2 = new LinearLayout(mContext);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            float sizenew = questionAndAnswerModel.getLstAnswer().size();
            l2.setWeightSum(sizenew);

            l2.removeAllViews();
            holder.mMutiple_question_option_holder.removeAllViews(); //duplication removed when binding 09-03-2018
            for (final QuestionAnswerListModel answermodel : questionAndAnswerModel.getLstAnswer()) {
                final TextView singlechoicebutton = new TextView(mContext);
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int size = questionAndAnswerModel.getLstAnswer().size();
                int width = display.getWidth()/size;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        width,LinearLayout.LayoutParams.MATCH_PARENT,1.0f
                );
                params.setMargins(10, 0, 10, 10);
                singlechoicebutton.setLayoutParams(params);
                singlechoicebutton.setPadding(30, 30, 30, 30);
                singlechoicebutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                singlechoicebutton.setId(answermodel.Answer_Id);
                singlechoicebutton.setGravity(Gravity.CENTER);
                singlechoicebutton.setText(answermodel.Answer_Text);
                singlechoicebutton.setTextColor(mContext.getResources().getColor(R.color.black));
                singlechoicebutton.setTypeface(singlechoicebutton.getTypeface(), Typeface.BOLD);
                if (answermodel.getIs_Correct_Answer() == 1) {
                    questionAndAnswerModel.setCorrectAnswer(answermodel.Answer_Text);
                    questionAndAnswerModel.setCorrectAnswerId(answermodel.Answer_Id + "");
                }
                if (answermodel.isChosenbooleanAnswer() != 0) {
                    if (Integer.parseInt(questionAndAnswerModel.getChoosenAnswerId()) == answermodel.isChosenbooleanAnswer()) {
                        singlechoicebutton.setBackgroundResource(R.drawable.tags_rounded_corners);
                        GradientDrawable drawable = (GradientDrawable) singlechoicebutton.getBackground();
                        drawable.setColor(Color.parseColor(answermodel.getAnswer_Colour()));
                        singlechoicebutton.setTextColor(mContext.getResources().getColor(R.color.white));
                    } else {
                        singlechoicebutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                        singlechoicebutton.setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                }
                singlechoicebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answermodel.setChosenbooleanAnswer(answermodel.Answer_Id);
                        questionAndAnswerModel.setChoosenAnswer(singlechoicebutton.getText().toString());
                        questionAndAnswerModel.setChoosenAnswerId(singlechoicebutton.getId()+"");
                        singlechoicebutton.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                        questionActivity.loaditem(position);
                        //notifyItemChanged(position);
                    }
                });

                l2.addView(singlechoicebutton);
            }

            holder.mMutiple_question_option_holder.addView(l2);

        } else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == Constants.YES_NO_NA_TYPE){

            final LinearLayout l2 = new LinearLayout(mContext);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            float sizenew = questionAndAnswerModel.getLstAnswer().size();
            l2.setWeightSum(sizenew);

            l2.removeAllViews();
            holder.mMutiple_question_option_holder.removeAllViews(); //duplication removed when binding 09-03-2018
            int pos = 0;
            for (final QuestionAnswerListModel answermodel : questionAndAnswerModel.getLstAnswer()) {
                final TextView yesnobutton = new TextView(mContext);
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int size = questionAndAnswerModel.getLstAnswer().size();
                int width = display.getWidth()/size;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        width,LinearLayout.LayoutParams.MATCH_PARENT,1.0f
                );
                params.setMargins(10, 0, 10, 10);
                yesnobutton.setLayoutParams(params);
                yesnobutton.setPadding(30,30,30,30);
                yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                yesnobutton.setId(answermodel.Answer_Id);
                yesnobutton.setGravity(Gravity.CENTER);
                yesnobutton.setText(answermodel.Answer_Text);
                yesnobutton.setTextColor(mContext.getResources().getColor(R.color.black));
                yesnobutton.setTypeface(yesnobutton.getTypeface(), Typeface.BOLD);
                if (answermodel.getIs_Correct_Answer() == 1){
                    questionAndAnswerModel.setCorrectAnswer(answermodel.Answer_Text);
                    questionAndAnswerModel.setCorrectAnswerId(answermodel.Answer_Id+"");
                }
                if(answermodel.isChosenbooleanAnswer() != 0){
                    if(pos == 0){
                        if(Integer.parseInt(questionAndAnswerModel.getChoosenAnswerId()) == answermodel.isChosenbooleanAnswer()){
                            yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_green));
                            yesnobutton.setTextColor(mContext.getResources().getColor(R.color.white));
                        }else{
                            yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                            yesnobutton.setTextColor(mContext.getResources().getColor(R.color.black));
                        }
                    }else if(pos == 1){
                        if(Integer.parseInt(questionAndAnswerModel.getChoosenAnswerId()) == answermodel.isChosenbooleanAnswer()){
                            yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_amber));
                            yesnobutton.setTextColor(mContext.getResources().getColor(R.color.white));
                        }else{
                            yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                            yesnobutton.setTextColor(mContext.getResources().getColor(R.color.black));
                        }
                    }else if(pos == 2){
                        if(Integer.parseInt(questionAndAnswerModel.getChoosenAnswerId()) == answermodel.isChosenbooleanAnswer()){
                            yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_red));
                            yesnobutton.setTextColor(mContext.getResources().getColor(R.color.white));
                        }else{
                            yesnobutton.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_allcorners_grey));
                            yesnobutton.setTextColor(mContext.getResources().getColor(R.color.black));
                        }
                    }else {
                        yesnobutton.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                        yesnobutton.setTextColor(mContext.getResources().getColor(R.color.black));
                    }
                }

                yesnobutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        answermodel.setChosenbooleanAnswer(answermodel.Answer_Id);
                        questionAndAnswerModel.setChoosenAnswer(yesnobutton.getText().toString());
                        questionAndAnswerModel.setChoosenAnswerId(yesnobutton.getId()+"");
                        yesnobutton.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
                        questionActivity.loaditem(position);

                    }
                });

                l2.addView(yesnobutton);
                pos = pos+1;
            }

            holder.mMutiple_question_option_holder.addView(l2);

        } else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 2){

            final RadioGroup ll = new RadioGroup(mContext);
            ll.setOrientation(LinearLayout.VERTICAL);

            holder.mMutiple_question_option_holder.removeAllViews(); //duplication removed when binding 09-03-2018

            for (final QuestionAnswerListModel answermodel : questionAndAnswerModel.getLstAnswer()) {
                final RadioButton rdbtn = new RadioButton(mContext);
                rdbtn.setPadding(30,30,30,30);
                rdbtn.setId(answermodel.Answer_Id);
                rdbtn.setText(answermodel.Answer_Text);
                if (answermodel.getIs_Correct_Answer() == 1){
                    questionAndAnswerModel.setCorrectAnswer(answermodel.Answer_Text);
                    questionAndAnswerModel.setCorrectAnswerId(answermodel.Answer_Id+"");
                }
                if(answermodel.isChosenRadioanswer()){
                    rdbtn.setChecked(true);
                }

                rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked){
                            answermodel.setChosenRadioanswer(true);
                            questionAndAnswerModel.setChoosenAnswer(rdbtn.getText().toString());
                            questionAndAnswerModel.setChoosenAnswerId(rdbtn.getId()+"");
                        }

                    }
                });

                ll.addView(rdbtn);
            }
            holder.mMutiple_question_option_holder.addView(ll);



        } else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 1){

            holder.mMutiple_question_option_holder.removeAllViews();

            for(QuestionAnswerListModel answermodel : questionAndAnswerModel.getLstAnswer()) {

                final CheckBox[] cb = {new CheckBox(mContext)};
                cb[0].setPadding(30,30,30,30);
                cb[0].setId(answermodel.Answer_Id);
                cb[0].setText(answermodel.Answer_Text);

                if (answermodel.getIs_Correct_Answer() == 1){

                    if (questionAndAnswerModel.getCorrectAnswer()!=null){

                        questionAndAnswerModel.setCorrectAnswerId(questionAndAnswerModel.getCorrectAnswerId()+answermodel.Answer_Id+",");
                        questionAndAnswerModel.setCorrectAnswer(questionAndAnswerModel.getCorrectAnswer()+answermodel.Answer_Text+",");

                    }else {

                        questionAndAnswerModel.setCorrectAnswer(answermodel.Answer_Text+",");
                        questionAndAnswerModel.setCorrectAnswerId(answermodel.Answer_Id+",");

                    }
                }

                cb[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            StringBuilder str = new StringBuilder();
                            str.append(buttonView.getText().toString()).append(",");
                            if (questionAndAnswerModel.getChoosenAnswer() !=null&& questionAndAnswerModel.getChoosenAnswer().length()>0){

                                questionAndAnswerModel.setChoosenAnswer(questionAndAnswerModel.getChoosenAnswer() + str.toString());
                                questionAndAnswerModel.setChoosenAnswerId(questionAndAnswerModel.getChoosenAnswerId()+buttonView.getId()+",");

                            }else {

                                questionAndAnswerModel.setChoosenAnswer(str.toString());
                                questionAndAnswerModel.setChoosenAnswerId(buttonView.getId()+",");

                            }

                        }else {

                            if (questionAndAnswerModel.getChoosenAnswer() !=null && questionAndAnswerModel.getChoosenAnswer().length()>0) {
                                //StringTokenizer st = new StringTokenizer(savedString, ",");
                                String [] answerlist = questionAndAnswerModel.getChoosenAnswer().split(",");
                                String [] choosenid = questionAndAnswerModel.getChoosenAnswerId().split(",");
                                for (int i = 0; i < answerlist.length; i++)
                                {
                                    if (answerlist[i].equals(buttonView.getText().toString())) {
                                        answerlist[i] = null;
                                        choosenid[i] = null;
                                        break;
                                    }
                                }
                                StringBuilder str = new StringBuilder();
                                StringBuilder strone = new StringBuilder();
                                for (int j=0; j<answerlist.length;j++) {
                                    if (answerlist[j]!=null)
                                        str.append(answerlist[j]).append(",");
                                    if (choosenid[j]!=null){
                                        strone.append(choosenid[j]).append(",");
                                    }
                                }
                                questionAndAnswerModel.setChoosenAnswer(str.toString());
                                questionAndAnswerModel.setChoosenAnswerId(strone.toString());
                            }

                        }
                    }
                });

                if (questionAndAnswerModel.getChoosenAnswerId()!=null)
                Log.d("==>testchoosenid",questionAndAnswerModel.getChoosenAnswerId());
                holder.mMutiple_question_option_holder.addView(cb[0]);
            }

        }
       else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 6)
        {
            final EditText editText = new EditText(mContext);
            // editText.setPadding(30,30,30,30);
            editText.setSingleLine(false);
            editText.setHint(R.string.defaulttext);
            editText.setBackgroundColor(Color.parseColor("#d1d1d3"));
            editText.setScroller(new Scroller(mContext));
            editText.setVerticalScrollBarEnabled(true);
            //editText.setCursorVisible(true);
            //editText.requestFocus();
            //editText.setSelection(0);
            editText.setMinLines(7);
            editText.setGravity(Gravity.TOP);
            editText.setLongClickable(false);


            holder.mMutiple_question_option_holder.removeAllViews();

            for(QuestionAnswerListModel answermodel : questionAndAnswerModel.getLstAnswer()) {
                questionAndAnswerModel.setCorrectAnswer(answermodel.Answer_Text);
            }

            if (questionAndAnswerModel.getChoosenAnswer()!=null && questionAndAnswerModel.getChoosenAnswer().length()>0){

                editText.setText(questionAndAnswerModel.getChoosenAnswer());
            }



            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    questionAndAnswerModel.setChoosenAnswer(editText.getText().toString());

                }
            });


            holder.mMutiple_question_option_holder.addView(editText);
        }
        else  {

            EditText editText = new EditText(mContext);
            editText.setPadding(30,30,30,30);

            holder.mMutiple_question_option_holder.removeAllViews();

            for(QuestionAnswerListModel answermodel : questionAndAnswerModel.getLstAnswer()) {
                questionAndAnswerModel.setCorrectAnswer(answermodel.Answer_Text);
            }

            if (questionAndAnswerModel.getChoosenAnswer()!=null && questionAndAnswerModel.getChoosenAnswer().length()>0){

                editText.setText(questionAndAnswerModel.getChoosenAnswer());
            }



            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    questionAndAnswerModel.setChoosenAnswer(s.toString());

                }
            });


            holder.mMutiple_question_option_holder.addView(editText);

        }

        if (questionAndAnswerModel.getQuestionModel().getHint()!= null && questionAndAnswerModel.getQuestionModel().getHint().length()>0){

            holder.mHintLayoutHolder.setVisibility(View.VISIBLE);

        }else {

            holder.mHintLayoutHolder.setVisibility(View.GONE);

        }

            holder.mHintLayoutHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowHintDilogue(questionAndAnswerModel.getQuestionModel().getHint());

            }
        });

    }

    @Override
    public int getItemCount() {
        return questionAndAnswerModels.size();
    }


    public class MyMultiPleQuestionViewHolder extends RecyclerView.ViewHolder{

        private TextView mQuestion_text,mDescriptionTextQuestion;
        private LinearLayout mMutiple_question_option_holder;
        private RelativeLayout mHintLayoutHolder;
        private ImageView mQuestionImageview;
        public MyMultiPleQuestionViewHolder(View itemView) {
            super(itemView);

            mQuestion_text = (TextView) itemView.findViewById(R.id.question_text);
            mMutiple_question_option_holder = (LinearLayout) itemView.findViewById(R.id.mutilple_question_option_holder);
            mHintLayoutHolder = (RelativeLayout) itemView.findViewById(R.id.hint_layout_holder);
            mQuestionImageview = (ImageView) itemView.findViewById(R.id.question_image);
            mDescriptionTextQuestion = (TextView) itemView.findViewById(R.id.question_description_text);

        }

    }


    private void ShowHintDilogue(String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(mContext.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
