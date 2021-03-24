package com.swaas.kangle.LPCourse.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameCategoryWords {

    @SerializedName("Attempt_Number")
    @Expose
    private Integer attemptNumber;
    @SerializedName("lstwords")
    @Expose
    private List<Lstword> lstwords = null;
    @SerializedName("lsthints")
    @Expose
    private List<Lsthint> lsthints = null;

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public List<Lstword> getLstwords() {
        return lstwords;
    }

    public void setLstwords(List<Lstword> lstwords) {
        this.lstwords = lstwords;
    }

    public List<Lsthint> getLsthints() {
        return lsthints;
    }

    public void setLsthints(List<Lsthint> lsthints) {
        this.lsthints = lsthints;
    }

}

