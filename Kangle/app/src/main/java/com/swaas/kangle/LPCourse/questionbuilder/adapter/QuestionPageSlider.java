package com.swaas.kangle.LPCourse.questionbuilder.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.swaas.kangle.LPCourse.model.QuestionAndAnswerModel;
import com.swaas.kangle.LPCourse.questionbuilder.fragment.OptionQuestionFragment;
import com.swaas.kangle.LPCourse.questionbuilder.fragment.SingleChoiceButtonTypeQuestionFragment;
import com.swaas.kangle.LPCourse.questionbuilder.fragment.SingleOptionQuestionFragment;
import com.swaas.kangle.LPCourse.questionbuilder.fragment.TextOptionQuestionFragment;
import com.swaas.kangle.LPCourse.questionbuilder.fragment.YesNoNaTypeQuestionFragment;
import com.swaas.kangle.playerPart.customviews.pdf.util.Constants;

import java.util.ArrayList;

/**
 * Created by Hariharan on 16/8/17.
 */

public class QuestionPageSlider extends FragmentStatePagerAdapter {

    public ArrayList<QuestionAndAnswerModel> questionAndAnswerModels;

    public QuestionPageSlider(FragmentManager fm, ArrayList<QuestionAndAnswerModel> questionAndAnswerModels) {
        super(fm);
        this.questionAndAnswerModels = questionAndAnswerModels;

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION,position);
        QuestionAndAnswerModel questionAndAnswerModel = questionAndAnswerModels.get(position);

        if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 5){
            fragment = new SingleChoiceButtonTypeQuestionFragment();
            fragment.setArguments(bundle);
            bundle.putSerializable("val",questionAndAnswerModel);

        }else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 4){
            fragment = new YesNoNaTypeQuestionFragment();
            fragment.setArguments(bundle);
            bundle.putSerializable("val",questionAndAnswerModel);

        } else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 2){
            fragment = new SingleOptionQuestionFragment();
            fragment.setArguments(bundle);
            bundle.putSerializable("val",questionAndAnswerModel);

        } else if (questionAndAnswerModel.getQuestionModel().getQuestion_Type() == 1){

            fragment = new OptionQuestionFragment();
            fragment.setArguments(bundle);
            bundle.putSerializable("val",questionAndAnswerModel);


        }else  {

            fragment = new TextOptionQuestionFragment();
            fragment.setArguments(bundle);
            bundle.putSerializable("val",questionAndAnswerModel);


        }

        return fragment;
    }

    @Override
    public int getCount() {
        return questionAndAnswerModels.size();
    }
}
