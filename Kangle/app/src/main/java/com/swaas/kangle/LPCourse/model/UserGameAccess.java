package com.swaas.kangle.LPCourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGameAccess {

    @SerializedName("Game_Id")
    @Expose
    private Integer gameId;
    @SerializedName("Company_Id")
    @Expose
    private Integer companyId;
    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("Game_User_Mapping_Id")
    @Expose
    private Integer gameUserMappingId;
    @SerializedName("Game_Name")
    @Expose
    private String gameName;
    @SerializedName("Game_Description")
    @Expose
    private String gameDescription;
    @SerializedName("Game_Thumbnail_URL")
    @Expose
    private Object gameThumbnailURL;
    @SerializedName("Is_Active")
    @Expose
    private Integer isActive;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGameUserMappingId() {
        return gameUserMappingId;
    }

    public void setGameUserMappingId(Integer gameUserMappingId) {
        this.gameUserMappingId = gameUserMappingId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public Object getGameThumbnailURL() {
        return gameThumbnailURL;
    }

    public void setGameThumbnailURL(Object gameThumbnailURL) {
        this.gameThumbnailURL = gameThumbnailURL;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}