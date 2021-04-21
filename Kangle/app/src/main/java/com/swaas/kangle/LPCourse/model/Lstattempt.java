package com.swaas.kangle.LPCourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lstattempt {

    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Attempt_No")
    @Expose
    private Integer attemptNo;
    @SerializedName("Game_Start_Time")
    @Expose
    private String gameStartTime;
    @SerializedName("Game_End_Time")
    @Expose
    private String gameEndTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(Integer attemptNo) {
        this.attemptNo = attemptNo;
    }

    public String getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(String gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public String getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(String gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

}
