package com.swaas.kangle.LPCourse.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HangmanResponse {

    @SerializedName("lstattempts")
    @Expose
    private List<Lstattempt> lstattempts = null;
    @SerializedName("lstresponse")
    @Expose
    private List<Lstresponse> lstresponse = null;

    public List<Lstattempt> getLstattempts() {
        return lstattempts;
    }

    public void setLstattempts(List<Lstattempt> lstattempts) {
        this.lstattempts = lstattempts;
    }

    public List<Lstresponse> getLstresponse() {
        return lstresponse;
    }

    public void setLstresponse(List<Lstresponse> lstresponse) {
        this.lstresponse = lstresponse;
    }

}


