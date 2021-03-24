package com.swaas.kangle.LPCourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lstword {

    @SerializedName("Question_Id")
    @Expose
    private Integer questionId;
    @SerializedName("Company_Id")
    @Expose
    private Integer companyId;
    @SerializedName("Question_Category_Mapping_Id")
    @Expose
    private Integer questionCategoryMappingId;
    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Question_Text")
    @Expose
    private String questionText;
    @SerializedName("Question_Description")
    @Expose
    private String questionDescription;
    @SerializedName("Question_Image_URL")
    @Expose
    private Object questionImageURL;
    @SerializedName("Question_type")
    @Expose
    private Integer questionType;
    @SerializedName("No_Of_Lives")
    @Expose
    private Integer noOfLives;
    @SerializedName("No_Of_Letters")
    @Expose
    private Integer noOfLetters;
    @SerializedName("Is_Active")
    @Expose
    private Integer isActive;

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

    public Integer getQuestionCategoryMappingId() {
        return questionCategoryMappingId;
    }

    public void setQuestionCategoryMappingId(Integer questionCategoryMappingId) {
        this.questionCategoryMappingId = questionCategoryMappingId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public Object getQuestionImageURL() {
        return questionImageURL;
    }

    public void setQuestionImageURL(Object questionImageURL) {
        this.questionImageURL = questionImageURL;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getNoOfLives() {
        return noOfLives;
    }

    public void setNoOfLives(Integer noOfLives) {
        this.noOfLives = noOfLives;
    }

    public Integer getNoOfLetters() {
        return noOfLetters;
    }

    public void setNoOfLetters(Integer noOfLetters) {
        this.noOfLetters = noOfLetters;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}
