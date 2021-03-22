package com.swaas.kangle.LPCourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LeaderBoardModel implements Serializable {
    @SerializedName("Achieved_Percentage")
    @Expose
    private Double achievedPercentage;
    @SerializedName("Course_Id")
    @Expose
    private int courseId;
    @SerializedName("Course_Name")
    @Expose
    private String courseName;
    @SerializedName("Employee_Name")
    @Expose
    private String employeeName;
    @SerializedName("Is_Game_Course")
    @Expose
    private int isGameCourse;
    @SerializedName("Rank")
    @Expose
    private int rank;
    @SerializedName("TimeSpent")
    @Expose
    private int timeSpent;
    @SerializedName("Total_Attempts")
    @Expose
    private int totalAttempts;
    @SerializedName("User_Id")
    @Expose
    private int userId;
    @SerializedName("User_Name")
    @Expose
    private String userName;

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank2) {
        this.rank = rank2;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId2) {
        this.userId = userId2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName2) {
        this.employeeName = employeeName2;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId2) {
        this.courseId = courseId2;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName2) {
        this.courseName = courseName2;
    }

    public Double getAchievedPercentage() {
        return this.achievedPercentage;
    }

    public void setAchievedPercentage(Double achievedPercentage2) {
        this.achievedPercentage = achievedPercentage2;
    }

    public int getTotalAttempts() {
        return this.totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts2) {
        this.totalAttempts = totalAttempts2;
    }

    public int getTimeSpent() {
        return this.timeSpent;
    }

    public void setTimeSpent(int timeSpent2) {
        this.timeSpent = timeSpent2;
    }

    public int getIsGameCourse() {
        return this.isGameCourse;
    }

    public void setIsGameCourse(int isGameCourse2) {
        this.isGameCourse = isGameCourse2;
    }
}
