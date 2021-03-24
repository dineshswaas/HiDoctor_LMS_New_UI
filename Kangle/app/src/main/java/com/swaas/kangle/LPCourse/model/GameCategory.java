package com.swaas.kangle.LPCourse.model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class GameCategory {

    @SerializedName("Game_Id")
    @Expose
    private Integer gameId;
    @SerializedName("Company_Id")
    @Expose
    private Integer companyId;
    @SerializedName("Category_User_Mapping_Id")
    @Expose
    private Integer categoryUserMappingId;
    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("Game_Name")
    @Expose
    private String gameName;
    @SerializedName("Category_Image_URL")
    @Expose
    private String categoryImageURL;
    @SerializedName("Category_Description")
    @Expose
    private String categoryDescription;
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

    public Integer getCategoryUserMappingId() {
        return categoryUserMappingId;
    }

    public void setCategoryUserMappingId(Integer categoryUserMappingId) {
        this.categoryUserMappingId = categoryUserMappingId;
    }

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public void setCategoryImageURL(String categoryImageURL) {
        this.categoryImageURL = categoryImageURL;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

}