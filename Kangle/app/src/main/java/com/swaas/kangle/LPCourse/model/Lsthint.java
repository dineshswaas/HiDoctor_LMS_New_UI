package com.swaas.kangle.LPCourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lsthint {

    @SerializedName("Question_Hint_Id")
    @Expose
    private Integer questionHintId;
    @SerializedName("Question_Id")
    @Expose
    private Integer questionId;
    @SerializedName("Company_Id")
    @Expose
    private Integer companyId;
    @SerializedName("Hint_Description")
    @Expose
    private String hintDescription;

    public Integer getQuestionHintId() {
        return questionHintId;
    }

    public void setQuestionHintId(Integer questionHintId) {
        this.questionHintId = questionHintId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getHintDescription() {
        return hintDescription;
    }

    public void setHintDescription(String hintDescription) {
        this.hintDescription = hintDescription;
    }

}
