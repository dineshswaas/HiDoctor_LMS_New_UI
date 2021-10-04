package com.swaas.kangle.LPCourse;

import com.google.gson.Gson;
import com.swaas.kangle.CheckList.model.UserforCourseChecklist;
import com.swaas.kangle.LPCourse.model.AnwerUploadModel;
import com.swaas.kangle.LPCourse.model.GameCategory;
import com.swaas.kangle.LPCourse.model.GameCategoryWords;
import com.swaas.kangle.LPCourse.model.HangmanResponse;
import com.swaas.kangle.LPCourse.model.LPCourseReportModel;
import com.swaas.kangle.LPCourse.model.LPCourseReportSummaryModel;
import com.swaas.kangle.LPCourse.model.LeaderBoardModel;
import com.swaas.kangle.LPCourse.model.QuestionBaseModel;
import com.swaas.kangle.LPCourse.model.UserGameAccess;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Sayellessh on 10-08-2017.
 */

public interface LPCourseService {
    @POST("LPCourseAPI/CourseCheckListRestartUpdate/{companyId}/{courseId}/{userId}/{subdomainName}")
    Call<String> CourseCheckListRestartUpdate(@Path("companyId") int i, @Path("courseId") int i2, @Path("userId") int i3, @Path("subdomainName") String str);

    @POST("LPCourseAPI/LPCourseAutoExtentAttemptsForUsers")
    Call<String> LPCourseAutoExtentAttemptsForUsers(@Body CourseExtendModel courseExtendModel);

    @POST("LPCourseAPI/LPCourseAutoExtentDateForUsers")
    Call<String> LPCourseAutoExtentDateForUsers(@Body CourseExtendModel courseExtendModel);

    @GET("LPCourseAPI/GetAvailableCourses/{subdomain}/{companyId}/{userId}/{utcoffset}")
    Call<List<CourseModel>> getAvailableCourses(@Path("subdomain") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("utcoffset") String str2);

    @GET("LPCourseAPI/GetAvailableKACourses/{subdomain}/{companyId}/{userId}/{utcoffset}")
    Call<List<CourseModel>> getAvailableKACourses(@Path("subdomain") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("utcoffset") String str2);

    @GET("LPCourseAPI/GetCourseChecklistReportList/{subdomainName}/{companyId}/{userId}/{courseId}/{Section_Id}")
    Call<ArrayList<UserforCourseChecklist>> getCourseChecklistReportList(@Path("subdomainName") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("courseId") int i3, @Path("Section_Id") int i4);

    @GET("LPCourseAPI/TestEvaluation/{courseId}/{userId}/{companyId}")
    Call<ArrayList<LPCourseReportSummaryModel>> getCourseFullcourseReportList(@Path("courseId") int i, @Path("userId") int i2, @Path("companyId") int i3);

    @GET("LPCourseAPI/GetKASectionDetailsOfCourse/{subdomain}/{companyId}/{userId}/{courseId}/{PublishId}")
    Call<List<SectionModel>> getKASectionDetailsOfCourse(@Path("subdomain") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("courseId") int i3, @Path("PublishId") int i4);

    @GET("LPCourseAPI/GetLPAssetsByCourseId/{subdomain}/{companyId}/{courseId}/{sectionId}")
    Call<List<CourseAssetModel>> getLPAssetsByCourseId(@Path("subdomain") String str, @Path("companyId") int i, @Path("courseId") int i2, @Path("sectionId") int i3);

    @GET("LPCourseAPI/getLPQuestionAnswerDetails/{subdomain}/{companyId}/{userId}/{courseId}/{sectionId}/{PublishId}")
    Call<List<QuestionBaseModel>> getLPQuestionAnswerDetails(@Path("subdomain") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("courseId") int i3, @Path("sectionId") int i4, @Path("PublishId") int i5);

    @GET("LPCourseAPI/getLPSectionAttemptDetails/{subdomain}/{companyId}/{courseId}/{userId}/{PublishId}/{sectionId}/{utcoffset}")
    Call<ArrayList<LPCourseReportModel>> getLPSectionAttemptDetails(@Path("subdomain") String str, @Path("companyId") int i, @Path("courseId") int i2, @Path("userId") int i3, @Path("PublishId") int i4, @Path("sectionId") int i5, @Path("utcoffset") String str2);

    @GET("LPCourseAPI/GetLPSectionQuestionDetails/{subdomainName}/{courseSectionUserExamId}/{utcoffset}/{companyId}")
    Call<ArrayList<LPCourseReportSummaryModel>> getLPSectionQuestionDetails(@Path("subdomainName") String str, @Path("courseSectionUserExamId") int i, @Path("utcoffset") String str2, @Path("companyId") int i2);

    @GET("LPCourseAPI/GetSectionChecklistReportList/{subdomainName}/{companyId}/{userId}/{courseId}/{Section_Id}")
    Call<ArrayList<UserforCourseChecklist>> getSectionChecklistReportList(@Path("subdomainName") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("courseId") int i3, @Path("Section_Id") int i4);

    @GET("LPCourseAPI/GetSectionDetailsOfCourse/{subdomain}/{companyId}/{userId}/{courseId}/{PublishId}")
    Call<List<SectionModel>> getSectionDetailsOfCourse(@Path("subdomain") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("courseId") int i3, @Path("PublishId") int i4);

    @GET("LPCourseAPI/GetLevelWiseLeaderboard/{companyId}/{courseId}")
    Call<ArrayList<LeaderBoardModel>> getleaderboard(@Path("companyId") int i, @Path("courseId") int i2);

    @POST("LPCourseAPI/InsertAssetMappingMaterialLog/{subdomain}")
    Call<String> insertAssetMappingMaterialLog(@Path("subdomain") String str, @Body CourseAnalyticsModel courseAnalyticsModel);

    @POST("LPCourseAPI/insertLPCourseSectionUserExamHeader/{subdomainName}/{courseId}/{courseUserAssignMentId}/{couseUserSectionId}/{publishId}/{userId}/{companyId}/{sectionId}")
    Call<String> insertLPCourseSectionUserExamHeader(@Path("subdomainName") String str, @Path("courseId") int i, @Path("courseUserAssignMentId") int i2, @Path("couseUserSectionId") int i3, @Path("publishId") int i4, @Path("userId") int i5, @Path("companyId") int i6, @Path("sectionId") int i7);

    @POST("LPCourseAPI/InsertLPCourseViewAnalytics/{subdomain}")
    Call<String> insertLPCourseViewAnalytics(@Path("subdomain") String str, @Body CourseAnalyticsModel courseAnalyticsModel);

    @POST("LPCourseAPI/insertLPCourseResponse/{subdomainName}/{companyId}/{userId}/{questionLoadCount}/{isLastQuestion}/{isTimeOut}")
    Call<String> insertTestCourseResponse(@Path("subdomainName") String str, @Path("companyId") int i, @Path("userId") int i2, @Path("questionLoadCount") int i3, @Path("isLastQuestion") boolean z, @Path("isTimeOut") boolean z2, @Body AnwerUploadModel anwerUploadModel);

    // autopublish API

    @GET("LPCourseAPI/GetAutoPublishDetailsAPI/{company_id}/{User_Id}")
    Call<Integer> getautopublish(@Path("company_id") int i, @Path("User_Id") int i2);

    // coursesectionattemps

    @POST("LPCourseAPI/UpdateCourseSectionAttempts/{Course_Id}/{User_Id}/{Company_Id}")
    Call<Integer> updateattempt(@Path("Course_Id") int i, @Path("User_Id") int i2, @Path("Company_Id") int i3);

    @GET("GameAPI/UserGameAccessAPI/{Company_Id}/{User_Id}")
    Call<ArrayList<UserGameAccess>> getusergameaccess(@Path("Company_Id") int i, @Path("User_Id") int i2);

    @GET("GameAPI/HangmanUserCategoryAPI/{Company_Id}/{User_Id}/{Game_Id}")
    Call<ArrayList<GameCategory>> getgamecategory(@Path("Company_Id") int i, @Path("User_Id") int i2, @Path("Game_Id") int i3);


    @GET("GameAPI/HangmanCategoryQuestionsAPI/{Company_Id}/{User_Id}/{Category_Id}")
    Call<GameCategoryWords> getgamecategoryQuestions(@Path("Company_Id") int i, @Path("User_Id") int i2, @Path("Category_Id") int i3);

    @POST("GameAPI/HangmanUserResponseAPI/{Company_Id}")
    Call<String> posthangman(@Path("Company_Id") int str, @Body HangmanResponse hangmanResponse);
}
