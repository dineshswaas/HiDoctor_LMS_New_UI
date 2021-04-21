package com.swaas.kangle.LPCourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lstresponse {

    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("Question_Id")
    @Expose
    private Integer questionId;
    @SerializedName("Is_Correct")
    @Expose
    private Integer isCorrect;
    @SerializedName("No_Of_Lives_Taken")
    @Expose
    private Integer noOfLivesTaken;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getNoOfLivesTaken() {
        return noOfLivesTaken;
    }

    public void setNoOfLivesTaken(Integer noOfLivesTaken) {
        this.noOfLivesTaken = noOfLivesTaken;
    }

}
