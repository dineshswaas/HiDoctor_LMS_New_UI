package com.swaas.kangle.LPCourse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcove.player.event.AbstractEvent;
import com.google.android.gms.location.LocationListener;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.swaas.kangle.API.model.AppInfo;
import com.swaas.kangle.API.model.LandingPageAccess;
import com.swaas.kangle.API.model.User;
import com.swaas.kangle.API.service.AppInfoService;
import com.swaas.kangle.AssetListActivity;
import com.swaas.kangle.CheckList.ChecklistLandingActivity;
import com.swaas.kangle.CourseWebView;
import com.swaas.kangle.DashboardActivity;
import com.swaas.kangle.EmptyRecyclerView;
import com.swaas.kangle.LPCourse.model.LeaderBoardModel;
import com.swaas.kangle.LPCourse.model.UserGameAccess;
import com.swaas.kangle.MoreMenuActivity;
import com.swaas.kangle.Notification.NotificationActivity;
import com.swaas.kangle.Notification.NotificationModel;
import com.swaas.kangle.Notification.NotificationTempRepository;
import com.swaas.kangle.R;
import com.swaas.kangle.TaskModule.TaskListActivity;
import com.swaas.kangle.UploadActivity;
import com.swaas.kangle.db.CourseListTempRepository;
import com.swaas.kangle.db.Filters.CourseCatTagFilterRepository;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.db.UserTrackertableRepository;
import com.swaas.kangle.models.APIResponse;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.CommonUtils;
import com.swaas.kangle.utils.Constants;
import com.swaas.kangle.utils.MessageDialog;
import com.swaas.kangle.utils.NetworkUtils;
import com.swaas.kangle.utils.iOSDialog;
import com.swaas.kangle.utils.iOSDialogBuilder;
import com.swaas.kangle.utils.iOSDialogClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GameActivity extends AppCompatActivity implements LocationListener {
    private static int firstVisibleInListview;
    String CATS = "";
    String TAGS = "";
    RelativeLayout activity_list;
    TextView applyfilters;
    AssetAnalyticsUpsynctoServer assetAnalyticsUpsynctoServer;
    View assetfilterheading;
    View assetpage;
    RelativeLayout bottomheader;
    LinearLayout bottommenus;
    View bottommenusection;
    boolean catFiltered;
    TextView cat_filtered_count;
    CourseCategoryListAdapterGame categoryListRecyclerAdapter;
    List<String> catlist;
    View catselection;
    EmptyRecyclerView cattag_recyclerView;
    View cattagmenus;
    TextView chatcount;
    ImageView chatview;
    View chatviewsec;
    RelativeLayout checkBoxGroupView;
    View checkInProgress;
    View checkcompleted;
    View checkexpired;
    View checkmax_attempts;
    View checkyettostart;
    View chklistpage;
    TextView clear_assetfilter;
    View clear_assetfilter_img;
    TextView clearfilters;
    ImageView close_filter;
    TextView closehelp;
    ImageView closesearch;
    ImageView companylogo;
    boolean completed_checked;
    CourseCatTagFilterRepository courseCatTagFilterRepository;
    CourseListAdapterGame courseListAdapter;
    CourseListTempRepository courseListTempRepository;
    List<CourseModel> courseModelList = new ArrayList();
    List<CourseModel> courseModelListgame = new ArrayList();
    List<String> courseModelsCat;
    List<String> courseModelsTags;
    List<CourseModel> digitalAssetsMasterCategoryLists;
    List<CourseModel> digitalAssetsMasterListfilterd;
    List<CourseModel> digitalAssetsMasterTagsLists;
    ImageView emptyimage;
    TextView emptymessage;
    TextView emptytagsview;
    ImageView expandfilter;
    boolean expired;
    TextView filtered_text;
    RelativeLayout filterheading;
    TextView filterheadingtext;
    View filterlay;
    RelativeLayout filtersection;
    String fromcatfilter = "";
    String fromtagfilter = "";
    View gamepage;
    GridLayoutManager grid;
    LinearLayout header;
    RelativeLayout headersection;
    WebView helpView;
    View helplayout;
    TextView higlighttext;
    ImageView icon_cats;
    ImageView icon_filter;
    ImageView icon_search;
    ImageView icon_tags;
    boolean in_progress_checked;
    boolean isFilterenabled = false;
    public double latitude;
    public double longitude;
    View lpcourse;
    CheckBox mCheckCompleted;
    CheckBox mCheckExpired;
    CheckBox mCheckInProgress;
    CheckBox mCheckMaxAttempts;
    CheckBox mCheckYetToStart;
    Context mContext;
    ImageView mCourseFilter;
    HashMap<String, Integer> mCourseMap;
    HashMap<String, Integer> mCourseTagsMap;
    View mEmptyView;
    TextView mFilteredCountStatus;
    ProgressDialog mProgressDialog;
    boolean max_attempts_reached;
    MessageDialog messageDialog;
    List<CourseModel> msearchcourse;
    SearchView msearchtext;
    ImageView notification;
    TextView notificationcount;
    View notificationsec;
    PackageInfo packageInfo = null;
    ImageView pos0;
    Dialog prerequsite;
    View profilepage;
    EmptyRecyclerView recyclerView;
    TextView retrybutton;
    SearchManager searchManager;
    LinearLayout searchlayout;
    TextView sectiontext;
    ImageView settings;
    boolean tagfiltered;
    CourseTagsListAdapterGame tagsListRecyclerAdapter;
    TextView tags_filtered_count;
    View tagselection;
    List<String> tagslist;
    View taskpage;
    ImageView tickfilter;
    UploadActivity uploadActivity;
    boolean yet_to_start_checked;

    ImageView gameexpand,hangmangame;
    RelativeLayout gameheader,gamesectionview;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.game_recycler);
        this.mContext = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        this.assetAnalyticsUpsynctoServer = new AssetAnalyticsUpsynctoServer();
        this.assetAnalyticsUpsynctoServer.AssetAnalyticsUpsynctoServer(this.mContext);
        this.uploadActivity = new UploadActivity(this.mContext);
        this.courseListTempRepository = new CourseListTempRepository(this.mContext);
        this.courseCatTagFilterRepository = new CourseCatTagFilterRepository(this.mContext);
        initializeView();
        setUpRecyclerView();
        initializebottomnavigation();
        bottomnavigationonClickevents();
        getgameaccess();
        gameheader.setVisibility(View.GONE);
        if(PreferenceUtils.getgameactive(mContext) == 1)
        {
            gameheader.setVisibility(View.VISIBLE);
        }
        setthemeforView();
        if (!NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            this.emptyimage.setImageResource(R.drawable.no_results);
            TextView textView = this.emptymessage;
            textView.setText(getString(R.string.oops_no_result) + getString(R.string.enable_network));
        } else if (PreferenceUtils.getCourse_User_Assignment_Id(this.mContext) == 0 || PreferenceUtils.getCouse_User_Section_Mapping_Id(this.mContext) == 0) {
            this.assetAnalyticsUpsynctoServer.getAnalyticsfromDb(false, 0, 0);
        } else {
            this.assetAnalyticsUpsynctoServer.getAnalyticsfromDb(false, PreferenceUtils.getCourse_User_Assignment_Id(this.mContext), PreferenceUtils.getCouse_User_Section_Mapping_Id(this.mContext));
        }
        this.uploadActivity.uploadTestTableRecords();
        this.tagslist = new ArrayList();
        this.catlist = new ArrayList();
        onClickListeners();
        showApplyButton();
    }

    public void getgameaccess() {
        if (NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            showProgressDialog();
            ((LPCourseService) RetrofitAPIBuilder.getInstance().create(LPCourseService.class)).getusergameaccess(PreferenceUtils.getCompnayId(this.mContext), PreferenceUtils.getUserId(mContext)).enqueue(new Callback<ArrayList<UserGameAccess>>() {
                public void onResponse(Response<ArrayList<UserGameAccess>> response, Retrofit retrofit3) {
                    ArrayList<UserGameAccess> userGameAccess = response.body();
                    if (userGameAccess == null && userGameAccess.size() == 0 ) {
                        dismissProgressDialog();
                        return;
                    }
                    else {
                        dismissProgressDialog();
                        if(userGameAccess.get(0).getIsActive() == 1)
                        {
                            PreferenceUtils.setGameactive(mContext,1);
//                            hangmangame.setVisibility(View.VISIBLE);
                           // gameheader.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            PreferenceUtils.setGameactive(mContext,0);
                          //  gameheader.setVisibility(View.GONE);
                        }
                    }
                }

                public void onFailure(Throwable t) {
                    Log.d(t.toString(), "Error");
                    dismissProgressDialog();
                }
            });
        }
    }

    public void getnotificationcount() {
        String subdomainName = PreferenceUtils.getSubdomainName(this);
        int CompanyId = PreferenceUtils.getCompnayId(this);
        int UserId = PreferenceUtils.getUserId(this);
        NotificationTempRepository notificationTempRepository = new NotificationTempRepository(this.mContext);
        notificationTempRepository.setGetNotificationDataCBListner(new NotificationTempRepository.GetNotificationDataCBListner() {
            public void GetNotificationDataSuccessCB(ArrayList<NotificationModel> customers) {
                if (customers != null && customers.size() > 0) {
                    int num = customers.get(0).getCount() + customers.get(1).getCount();
                    if (num > 0) {
                        GameActivity.this.notificationcount.setVisibility(View.VISIBLE);
                        if (num > 99) {
                            GameActivity.this.notificationcount.setText("99+");
                            return;
                        }
                        TextView textView = GameActivity.this.notificationcount;
                        textView.setText(num + "");
                        return;
                    }
                    GameActivity.this.notificationcount.setVisibility(View.GONE);
                }
            }

            public void GetNotificationDataFailureCB(String message) {
            }
        });
        notificationTempRepository.getNotificationHubCountFromApi(subdomainName, CompanyId, UserId);
        this.chatviewsec.setVisibility(View.GONE);
    }

    public void initializeView() {
        this.companylogo = (ImageView) findViewById(R.id.companylogo);
        this.mEmptyView = findViewById(R.id.empty_view);
        this.recyclerView = (EmptyRecyclerView) findViewById(R.id.recyclerView);
        this.expandfilter = (ImageView) findViewById(R.id.icon_expandslider);
        this.bottomheader = (RelativeLayout) findViewById(R.id.bottomheader);
        this.headersection = (RelativeLayout) findViewById(R.id.headersection);
        this.checkBoxGroupView = (RelativeLayout) findViewById(R.id.check_box);
        this.mCheckCompleted = (CheckBox) findViewById(R.id.check_completed);
        this.mCheckInProgress = (CheckBox) findViewById(R.id.check_in_progress);
        this.mCheckYetToStart = (CheckBox) findViewById(R.id.check_yet_to_start);
        this.mCheckExpired = (CheckBox) findViewById(R.id.check_expired);
        this.mCheckMaxAttempts = (CheckBox) findViewById(R.id.max_attempts);
        this.emptymessage = (TextView) findViewById(R.id.emptymessage);
        this.activity_list = (RelativeLayout) findViewById(R.id.activity_asset_list);
        this.settings = (ImageView) findViewById(R.id.settings);
        this.messageDialog = new MessageDialog(this.mContext);
        this.notification = (ImageView) findViewById(R.id.notification);
        this.chatview = (ImageView) findViewById(R.id.chatview);
        this.helpView = (WebView) findViewById(R.id.helpview);
        this.helplayout = findViewById(R.id.helplayout);
        this.closehelp = (TextView) findViewById(R.id.closehelp);
        this.header = (LinearLayout) findViewById(R.id.header);
        this.cattag_recyclerView = (EmptyRecyclerView) findViewById(R.id.cattag_recyclerView);
        this.filtersection = (RelativeLayout) findViewById(R.id.filtersection);
        this.close_filter = (ImageView) findViewById(R.id.close_filter);
        this.catselection = findViewById(R.id.catselection);
        this.tagselection = findViewById(R.id.tagselection);
        this.filterlay = findViewById(R.id.filterlay);
        this.clearfilters = (TextView) findViewById(R.id.clearfilters);
        this.applyfilters = (TextView) findViewById(R.id.applyfilters);
        this.assetfilterheading = findViewById(R.id.assetfilterheading);
        this.filtered_text = (TextView) findViewById(R.id.filtered_text);
        this.clear_assetfilter = (TextView) findViewById(R.id.clear_assetfilter);
        this.mCourseFilter = (ImageView) findViewById(R.id.icon_filter);
        this.cat_filtered_count = (TextView) findViewById(R.id.cat_filtered_count);
        this.tags_filtered_count = (TextView) findViewById(R.id.tags_filtered_count);
        this.mFilteredCountStatus = (TextView) findViewById(R.id.label_filtered_count);
        this.emptytagsview = (TextView) findViewById(R.id.emptytagsview);
        this.checkInProgress = findViewById(R.id.checkInProgress);
        this.checkyettostart = findViewById(R.id.checkyettostart);
        this.checkcompleted = findViewById(R.id.checkcompleted);
        this.checkexpired = findViewById(R.id.checkexpired);
        this.checkmax_attempts = findViewById(R.id.checkmax_attempts);
        this.filterheadingtext = (TextView) findViewById(R.id.filterheadingtext);
        this.cattagmenus = findViewById(R.id.cattagmenus);
        this.icon_cats = (ImageView) findViewById(R.id.icon_cats);
        this.icon_tags = (ImageView) findViewById(R.id.icon_tags);
        this.icon_filter = (ImageView) findViewById(R.id.icon_filter);
        this.searchlayout = (LinearLayout) findViewById(R.id.searchlayout);
        this.msearchtext = (SearchView) findViewById(R.id.searchtext);
        this.searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        this.icon_search = (ImageView) findViewById(R.id.icon_search);
        this.closesearch = (ImageView) findViewById(R.id.closesearch);
        this.tickfilter = (ImageView) findViewById(R.id.tickfilter);
        this.filterheading = (RelativeLayout) findViewById(R.id.filterheading);
        this.emptyimage = (ImageView) findViewById(R.id.emptyimage);
        this.retrybutton = (TextView) findViewById(R.id.retrybutton);
        this.notificationsec = findViewById(R.id.notificationsec);
        this.chatviewsec = findViewById(R.id.chatviewsec);
        this.notificationcount = (TextView) findViewById(R.id.notificationcount);
        this.chatcount = (TextView) findViewById(R.id.chatcount);
        this.sectiontext = (TextView) findViewById(R.id.section_text);
        gameheader = findViewById(R.id.gamesectionview);
        gamesectionview = findViewById(R.id.gameview);
        gameexpand = (ImageView) findViewById(R.id.gameexpandbutton);
        hangmangame = (ImageView) findViewById(R.id.hangman);
    }

    public void setthemeforView() {
        this.header.setBackgroundColor(Color.parseColor(Constants.HEADERBAR_COLOR));
        this.activity_list.setBackgroundColor(-7829368);
        String landingPageAccess = PreferenceUtils.getLandingPageAccess(this.mContext);
        String logo = PreferenceUtils.getClientLogo(this.mContext);
        TextUtils.isEmpty(logo);
        Ion.with(this.companylogo).placeholder((int) R.color.topbar).error((int) R.color.topbar).load(logo);
        File imgFile = new File("/storage/sdcard0/SwaaS LMS/companylogo.jpg");
        if (imgFile.exists()) {
            this.companylogo.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        }
        this.expandfilter.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));
        this.settings.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));
       // this.pos0.setColorFilter(Color.parseColor(Constants.COMPANY_COLOR));
        this.clearfilters.setTextColor(Color.parseColor(Constants.COMPANY_COLOR));
        this.mCheckInProgress.setButtonTintList(ColorStateList.valueOf(Color.parseColor(Constants.COMPANY_COLOR)));
        this.mCheckCompleted.setButtonTintList(ColorStateList.valueOf(Color.parseColor(Constants.COMPANY_COLOR)));
        this.mCheckYetToStart.setButtonTintList(ColorStateList.valueOf(Color.parseColor(Constants.COMPANY_COLOR)));
        this.mCheckExpired.setButtonTintList(ColorStateList.valueOf(Color.parseColor(Constants.COMPANY_COLOR)));
        this.mCheckMaxAttempts.setButtonTintList(ColorStateList.valueOf(Color.parseColor(Constants.COMPANY_COLOR)));
        this.emptymessage.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        this.closesearch.setBackgroundColor(Color.parseColor(Constants.HEADERBAR_COLOR));
        this.closesearch.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));
        this.icon_search.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));
        this.filtered_text.setTypeface(this.filtered_text.getTypeface(), Typeface.ITALIC);
        this.filterheading.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
        this.filterheadingtext.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        this.close_filter.setColorFilter(Color.parseColor(Constants.TEXT_COLOR));
        this.assetfilterheading.setBackgroundColor(Color.parseColor(Constants.HEADERBAR_COLOR));
        this.filtered_text.setTextColor(Color.parseColor(Constants.TOPBARICON_COLOR));
        this.tickfilter.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));
        this.emptyimage.setColorFilter(Color.parseColor(Constants.TEXT_COLOR));
        this.retrybutton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Constants.CARDBACKGROUND_COLOR)));
        this.retrybutton.setTextColor(Color.parseColor(Constants.TEXT_COLOR));
        this.notification.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));
        this.chatview.setColorFilter(Color.parseColor(Constants.TOPBARICON_COLOR));



    }

    public void initializebottomnavigation() {
        this.bottommenusection = findViewById(R.id.bottommenusection);
        this.bottommenus = (LinearLayout) findViewById(R.id.bottommenus);
        this.lpcourse = findViewById(R.id.lpcourse);
        this.assetpage = findViewById(R.id.assetpage);
        this.chklistpage = findViewById(R.id.chklistpage);
        this.taskpage = findViewById(R.id.reports);
        this.profilepage = findViewById(R.id.profilepage);
        this.clear_assetfilter_img = findViewById(R.id.clear_assetfilter_img);
        this.pos0 = (ImageView) findViewById(R.id.pos0);
        this.higlighttext = (TextView) findViewById(R.id.higlighttext);
        this.gamepage = findViewById(R.id.gamepage);
    }

    public void bottomnavigationonClickevents() {
        LandingPageAccess landingobj;
        int count = 1;
        if (!(PreferenceUtils.getLandingPageAccess(this.mContext) == null || (landingobj = (LandingPageAccess) new Gson().fromJson(PreferenceUtils.getLandingPageAccess(this.mContext), LandingPageAccess.class)) == null)) {
            if (landingobj.getLibrary().equalsIgnoreCase("Y")) {
                this.assetpage.setVisibility(View.VISIBLE);
                count = 1 + 1;
            } else {
                this.assetpage.setVisibility(View.GONE);
            }
            if (landingobj.getCourse().equalsIgnoreCase("L")) {
                this.lpcourse.setVisibility(View.VISIBLE);
                count++;
            } else if (landingobj.getCourse().equalsIgnoreCase("S")) {
                this.lpcourse.setVisibility(View.VISIBLE);
                count++;
            } else if (landingobj.getCourse().equalsIgnoreCase("A")) {
                this.lpcourse.setVisibility(View.VISIBLE);
                count++;
            } else {
                this.lpcourse.setVisibility(View.GONE);
            }
            if (landingobj.getChecklist() == null || !landingobj.getChecklist().equalsIgnoreCase("Y")) {
                this.chklistpage.setVisibility(View.GONE);
            } else {
                this.chklistpage.setVisibility(View.VISIBLE);
                count++;
            }
            if (landingobj.getTask() == null || !landingobj.getTask().equalsIgnoreCase("Y")) {
                this.taskpage.setVisibility(View.GONE);
            } else {
                this.taskpage.setVisibility(View.VISIBLE);
                count++;
            }
            if (!TextUtils.isEmpty(landingobj.getGame()) && landingobj.getGame().equalsIgnoreCase("Y")) {
                if (landingobj.getGame() == null || !landingobj.getGame().equalsIgnoreCase("Y")) {
                    this.gamepage.setVisibility(View.GONE);
                } else {
                    this.gamepage.setVisibility(View.VISIBLE);
                    count++;
                }
            }
        }

        gamesectionview.setVisibility(View.GONE);
        gameexpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gamesectionview.getVisibility() == View.GONE) {
                    gamesectionview.setVisibility(View.VISIBLE);
                    gameexpand.setImageResource(R.drawable.ic_keyboard_arrow_up_black_36dp);
                }
                else
                {
                    gameexpand.setImageResource(R.drawable.ic_keyboard_arrow_down_black_36dp);
                    gamesectionview.setVisibility(View.GONE);
                }
            }
        });
        hangmangame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,GameCategory.class);
                startActivity(i);
            }
        });
        this.bottommenus.setWeightSum((float) count);
        this.lpcourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!NetworkUtils.checkIfNetworkAvailable(GameActivity.this)) {
                    Toast.makeText(GameActivity.this, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
                GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, CourseListActivity.class));
                GameActivity.this.finish();
            }
        });
        this.assetpage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, AssetListActivity.class));
                GameActivity.this.finish();
            }
        });
        this.chklistpage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!NetworkUtils.checkIfNetworkAvailable(GameActivity.this)) {
                    Toast.makeText(GameActivity.this, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
                GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, ChecklistLandingActivity.class));
                GameActivity.this.finish();
            }
        });
        this.taskpage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!NetworkUtils.checkIfNetworkAvailable(GameActivity.this)) {
                    Toast.makeText(GameActivity.this, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
                GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, TaskListActivity.class));
                GameActivity.this.finish();
            }
        });
        this.profilepage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!NetworkUtils.checkIfNetworkAvailable(GameActivity.this)) {
                    Toast.makeText(GameActivity.this, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
                GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, MoreMenuActivity.class));
                GameActivity.this.finish();
            }
        });
        this.gamepage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    private void setUpRecyclerView() {
        this.recyclerView.setHasFixedSize(true);
        this.cattag_recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, 1, false);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            this.grid = new GridLayoutManager(this, 2);
            this.recyclerView.setLayoutManager(this.grid);
        } else if (getResources().getConfiguration().orientation == 2) {
            this.grid = new GridLayoutManager(this, 5);
            this.recyclerView.setLayoutManager(this.grid);
        } else {
            this.grid = new GridLayoutManager(this, 3);
            this.recyclerView.setLayoutManager(this.grid);
        }
        firstVisibleInListview = this.grid.findFirstVisibleItemPosition();
        this.cattag_recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setUpRecyclerView();
    }

    public void loadSearchdata(String newText) {
        this.msearchcourse = new ArrayList();
        this.msearchcourse = this.courseModelList;
        List<CourseModel> filteredList = new ArrayList<>();
        if (this.msearchcourse.size() > 0) {
            for (CourseModel row : this.msearchcourse) {
                if (row.getCourse_Name().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(row);
                }
            }
            if (filteredList.size() > 0) {
                showhideemptystate(false, "", 0);
                this.recyclerView.setVisibility(View.VISIBLE);
                this.courseListAdapter = new CourseListAdapterGame(this.mContext, filteredList);
                this.recyclerView.setAdapter(this.courseListAdapter);
                this.courseListAdapter.notifyDataSetChanged();
                return;
            }
            this.recyclerView.setVisibility(View.GONE);
            showhideemptystate(true, getResources().getString(R.string.no_result), 2);
            return;
        }
        this.recyclerView.setVisibility(View.GONE);
        showhideemptystate(true, getResources().getString(R.string.no_result), 2);
    }

    public void onClickListeners() {
        this.retrybutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GameActivity.this.getListOfCourses();
            }
        });
        this.companylogo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        this.closesearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GameActivity.this.searchlayout.setVisibility(View.GONE);
                GameActivity.this.notificationsec.setVisibility(View.VISIBLE);
                GameActivity.this.header.setVisibility(View.VISIBLE);
            }
        });
        this.icon_search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.searchlayout.setVisibility(View.VISIBLE);
                GameActivity.this.notificationsec.setVisibility(View.GONE);
                GameActivity.this.header.setVisibility(View.INVISIBLE);
            }
        });
        this.searchlayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.msearchtext.setSearchableInfo(this.searchManager.getSearchableInfo(getComponentName()));
        this.msearchtext.setOnCloseListener(new SearchView.OnCloseListener() {
            public boolean onClose() {
                GameActivity.this.searchlayout.setVisibility(View.GONE);
                return false;
            }
        });
        this.msearchtext.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    GameActivity.this.closesearch.setVisibility(View.GONE);
                } else {
                    GameActivity.this.closesearch.setVisibility(View.VISIBLE);
                }
                GameActivity.this.loadSearchdata(newText);
                return false;
            }
        });
        this.checkInProgress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameActivity.this.mCheckInProgress.isChecked()) {
                    GameActivity.this.mCheckInProgress.setChecked(false);
                } else {
                    GameActivity.this.mCheckInProgress.setChecked(true);
                }
                GameActivity.this.showApplyButton();
            }
        });
        this.checkyettostart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameActivity.this.mCheckYetToStart.isChecked()) {
                    GameActivity.this.mCheckYetToStart.setChecked(false);
                } else {
                    GameActivity.this.mCheckYetToStart.setChecked(true);
                }
                GameActivity.this.showApplyButton();
            }
        });
        this.checkcompleted.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameActivity.this.mCheckCompleted.isChecked()) {
                    GameActivity.this.mCheckCompleted.setChecked(false);
                } else {
                    GameActivity.this.mCheckCompleted.setChecked(true);
                }
                GameActivity.this.showApplyButton();
            }
        });
        this.checkexpired.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameActivity.this.mCheckExpired.isChecked()) {
                    GameActivity.this.mCheckExpired.setChecked(false);
                } else {
                    GameActivity.this.mCheckExpired.setChecked(true);
                }
                GameActivity.this.showApplyButton();
            }
        });
        this.checkmax_attempts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameActivity.this.mCheckMaxAttempts.isChecked()) {
                    GameActivity.this.mCheckMaxAttempts.setChecked(false);
                } else {
                    GameActivity.this.mCheckMaxAttempts.setChecked(true);
                }
                GameActivity.this.showApplyButton();
            }
        });
        this.close_filter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameActivity.this.isFilterenabled) {
                    GameActivity.this.filtersection.setVisibility(View.GONE);
                    GameActivity.this.bottommenusection.setVisibility(View.GONE);
                    GameActivity.this.assetfilterheading.setVisibility(View.GONE);
                    GameActivity.this.header.setVisibility(View.VISIBLE);
                    return;
                }
                GameActivity.this.filtersection.setVisibility(View.GONE);
                GameActivity.this.catlist.clear();
                GameActivity.this.tagslist.clear();
                GameActivity.this.CATS = "";
                GameActivity.this.TAGS = "";
                GameActivity.this.mCheckCompleted.setChecked(false);
                GameActivity.this.mCheckYetToStart.setChecked(false);
                GameActivity.this.mCheckInProgress.setChecked(false);
                GameActivity.this.mCheckExpired.setChecked(false);
                GameActivity.this.mCheckMaxAttempts.setChecked(false);
                GameActivity.this.catFiltered = false;
                GameActivity.this.tagfiltered = false;
                GameActivity.this.showApplyButton();
                GameActivity.this.getCourses();
                GameActivity.this.bottommenusection.setVisibility(View.VISIBLE);
            }
        });
        this.catselection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.loadCategorySlider();
                GameActivity.this.checkBoxGroupView.setVisibility(View.GONE);
            }
        });
        this.tagselection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.loadTagsSlider();
                GameActivity.this.checkBoxGroupView.setVisibility(View.GONE);
            }
        });
        this.filterlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.toggleselection(3);
                GameActivity.this.cattag_recyclerView.setVisibility(View.GONE);
                GameActivity.this.checkBoxGroupView.setVisibility(View.VISIBLE);
            }
        });
        this.clearfilters.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    GameActivity.this.clearfilters.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
                    GameActivity.this.clearfilters.setTextColor(-1);
                } else if (event.getAction() == 1) {
                    GameActivity.this.clearfilters.setBackgroundColor(-1);
                    GameActivity.this.clearfilters.setTextColor(Color.parseColor(Constants.COMPANY_COLOR));
                    GameActivity.this.clearfiltersFunction();
                    GameActivity.this.filtersection.setVisibility(View.GONE);
                    GameActivity.this.catFiltered = false;
                    GameActivity.this.tagfiltered = false;
                    GameActivity.this.getCourses();
                    GameActivity.this.bottommenusection.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        this.applyfilters.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 0) {
                    GameActivity.this.applyfilters.setBackgroundColor(-1);
                    GameActivity.this.applyfilters.setTextColor(Color.parseColor(Constants.COMPANY_COLOR));
                } else if (event.getAction() == 1) {
                    GameActivity.this.applyfilters.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
                    GameActivity.this.applyfilters.setTextColor(-1);
                    GameActivity.this.getCoursesbyIDs();
                    GameActivity.this.filtersection.setVisibility(View.GONE);
                    GameActivity.this.header.setVisibility(View.INVISIBLE);
                    GameActivity.this.assetfilterheading.setVisibility(View.VISIBLE);
                    GameActivity.this.filtered_text.setText(Html.fromHtml("<u>" + GameActivity.this.getResources().getString(R.string.clearfilter) + "</u>"));
                    GameActivity.this.isFilterenabled = true;
                    GameActivity.this.bottommenusection.setVisibility(View.GONE);
                }
                return true;
            }
        });
        this.clear_assetfilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.filtersection.setVisibility(View.GONE);
                GameActivity.this.catlist.clear();
                GameActivity.this.tagslist.clear();
                GameActivity.this.CATS = "";
                GameActivity.this.TAGS = "";
                GameActivity.this.assetfilterheading.setVisibility(View.GONE);
                GameActivity.this.header.setVisibility(View.VISIBLE);
                GameActivity.this.isFilterenabled = false;
                GameActivity.this.clearfiltersFunction();
                GameActivity.this.bottommenusection.setVisibility(View.VISIBLE);
                GameActivity.this.getListOfCourses();
            }
        });
        this.expandfilter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.filtersection.setVisibility(View.VISIBLE);
                GameActivity.this.catlist.clear();
                GameActivity.this.tagslist.clear();
                GameActivity.this.digitalAssetsMasterCategoryLists = GameActivity.this.courseCatTagFilterRepository.getAllCategory(GameActivity.this.CATS);
                GameActivity.this.digitalAssetsMasterTagsLists = GameActivity.this.courseCatTagFilterRepository.getAllTags(GameActivity.this.TAGS);
                GameActivity.this.cattag_recyclerView.setVisibility(View.VISIBLE);
                GameActivity.this.checkBoxGroupView.setVisibility(View.GONE);
                GameActivity.this.loadCategorySlider();
                GameActivity.this.bottommenusection.setVisibility(View.GONE);
            }
        });
        this.clear_assetfilter_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.bottommenusection.setVisibility(View.GONE);
                GameActivity.this.assetfilterheading.setVisibility(View.GONE);
                GameActivity.this.header.setVisibility(View.VISIBLE);
                GameActivity.this.filtersection.setVisibility(View.VISIBLE);
            }
        });
        this.filterheadingtext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.filtered_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GameActivity.this.filtersection.setVisibility(View.GONE);
                GameActivity.this.catlist.clear();
                GameActivity.this.tagslist.clear();
                GameActivity.this.CATS = "";
                GameActivity.this.TAGS = "";
                GameActivity.this.assetfilterheading.setVisibility(View.GONE);
                GameActivity.this.header.setVisibility(View.VISIBLE);
                GameActivity.this.isFilterenabled = false;
                GameActivity.this.clearfiltersFunction();
                GameActivity.this.bottommenusection.setVisibility(View.VISIBLE);
                GameActivity.this.getListOfCourses();
            }
        });
        this.mCheckCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameActivity.this.showApplyButton();
            }
        });
        this.mCheckYetToStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameActivity.this.showApplyButton();
            }
        });
        this.mCheckInProgress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameActivity.this.showApplyButton();
            }
        });
        this.mCheckExpired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameActivity.this.showApplyButton();
            }
        });
        this.mCheckMaxAttempts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GameActivity.this.showApplyButton();
            }
        });
        this.settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtils.checkIfNetworkAvailable(GameActivity.this.mContext)) {
                    GameActivity.this.messageDialog.showEmailPop(GameActivity.this.mContext, new View.OnClickListener() {
                        public void onClick(View Approve) {
                            Intent emailIntent = new Intent("android.intent.action.SEND");
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{Constants.SUPPORT_EMAIL});
                            emailIntent.putExtra("android.intent.extra.SUBJECT", "SwaaS LMS support");
                            GameActivity.this.startActivity(Intent.createChooser(emailIntent, GameActivity.this.getResources().getString(R.string.sendemail)));
                            GameActivity.this.messageDialog.dialogDismiss();
                        }
                    }, new View.OnClickListener() {
                        public void onClick(View close) {
                            GameActivity.this.messageDialog.dialogDismiss();
                        }
                    }, true);
                } else {
                    Toast.makeText(GameActivity.this.mContext, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.notificationsec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtils.checkIfNetworkAvailable(GameActivity.this.mContext)) {
                    GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, NotificationActivity.class));
                    return;
                }
                Toast.makeText(GameActivity.this.mContext, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });
        this.chatviewsec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtils.checkIfNetworkAvailable(GameActivity.this.mContext)) {
                    GameActivity.this.startActivity(new Intent(GameActivity.this.mContext, NotificationActivity.class));
                    return;
                }
                Toast.makeText(GameActivity.this.mContext, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            }
        });
        this.closehelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GameActivity.this.recyclerView.setVisibility(View.VISIBLE);
                GameActivity.this.helplayout.setVisibility(View.GONE);
            }
        });
    }

    public void clearfiltersFunction() {
        this.catlist.clear();
        this.tagslist.clear();
        this.CATS = "";
        this.TAGS = "";
        this.catFiltered = false;
        this.tagfiltered = false;
        this.mCheckCompleted.setChecked(false);
        this.mCheckYetToStart.setChecked(false);
        this.mCheckInProgress.setChecked(false);
        this.mCheckExpired.setChecked(false);
        this.mCheckMaxAttempts.setChecked(false);
        showApplyButton();
        this.isFilterenabled = false;
        this.digitalAssetsMasterCategoryLists = this.courseCatTagFilterRepository.getAllCategory(this.CATS);
        this.digitalAssetsMasterTagsLists = this.courseCatTagFilterRepository.getAllTags(this.TAGS);
        this.categoryListRecyclerAdapter = new CourseCategoryListAdapterGame(this.mContext, this.digitalAssetsMasterCategoryLists, true);
        this.categoryListRecyclerAdapter.notifyDataSetChanged();
        this.tagsListRecyclerAdapter = new CourseTagsListAdapterGame(this.mContext, this.digitalAssetsMasterTagsLists, true);
        this.tagsListRecyclerAdapter.notifyDataSetChanged();
        this.catselection.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
        this.tagselection.setBackgroundColor(Color.parseColor(Constants.OPAQUE_COLOR));
        this.filterlay.setBackgroundColor(Color.parseColor(Constants.OPAQUE_COLOR));
        this.cattag_recyclerView.setVisibility(View.VISIBLE);
        this.checkBoxGroupView.setVisibility(View.GONE);
        this.cattag_recyclerView.setAdapter(this.categoryListRecyclerAdapter);
    }

    public Set<String> getCategoreyList(List<CourseModel> coursecategory) {
        Set<String> courselist = new HashSet<>();
        for (CourseModel coursecat : coursecategory) {
            courselist.add(coursecat.getCategory_Name());
        }
        return courselist;
    }

    public void loadCoursebyCategory(String categoryName) {
        List<CourseModel> courseAssetscategory = new ArrayList<>();
        for (CourseModel courseModel : this.courseModelList) {
            if (categoryName.equalsIgnoreCase(courseModel.getCategory_Name())) {
                courseAssetscategory.add(courseModel);
            }
        }
        setUpRecyclerView();
        this.courseListAdapter = new CourseListAdapterGame(this.mContext, courseAssetscategory);
        this.recyclerView.setAdapter(this.courseListAdapter);
    }

    public void loadCoursebyTags(String tagName) {
        List<CourseModel> courseAssetsTags = new ArrayList<>();
        for (CourseModel courseModel : this.courseModelList) {
            if (courseModel.getCourse_Tags() != null && !courseModel.getCourse_Tags().isEmpty() && courseModel.getCourse_Tags().contains(tagName)) {
                courseAssetsTags.add(courseModel);
            }
        }
        setUpRecyclerView();
        this.courseListAdapter = new CourseListAdapterGame(this.mContext, courseAssetsTags);
        this.recyclerView.setAdapter(this.courseListAdapter);
    }

    public void getListOfCourses() {
        if (!isFinishing()) {
            dismissProgressDialog();
        }
        if (NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            PreferenceUtils.setNWEAvisible(this.mContext, false);
            PreferenceUtils.setVisibleActivityName(this.mContext, "Course");
            if (!isFinishing()) {
                showProgressDialog();
            }
            Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
            LPCourseService userService = retrofitAPI.create(LPCourseService.class);
            String offsetFromUtc = CommonUtils.getUtcOffsetincluded10k();
            Log.d("getUTC", offsetFromUtc);
            int CompanyId = PreferenceUtils.getCompnayId(this.mContext);
            Log.d(UserTrackertableRepository.CompanyId, String.valueOf(CompanyId));
            Log.d("CompanyId", String.valueOf(CompanyId));
            String SubdomainName = PreferenceUtils.getSubdomainName(mContext);
            int UserId = PreferenceUtils.getUserId(mContext);
            Call call = userService.getAvailableKACourses(SubdomainName, CompanyId, UserId, offsetFromUtc);
            call.enqueue(new Callback<ArrayList<CourseModel>>() {
                public void onResponse(Response<ArrayList<CourseModel>> response, Retrofit retrofit3) {
                    ArrayList<CourseModel> courseListModel = response.body();
                    if (courseListModel == null || courseListModel.size() <= 0) {
                        GameActivity.this.showhideemptystate(true, GameActivity.this.getResources().getString(R.string.emptyDashboard), 2);
                        GameActivity.this.recyclerView.setVisibility(View.GONE);
                        GameActivity.this.icon_search.setVisibility(View.INVISIBLE);
                        GameActivity.this.expandfilter.setVisibility(View.GONE);
                        if (!GameActivity.this.isFinishing()) {
                            GameActivity.this.dismissProgressDialog();
                            return;
                        }
                        return;
                    }
                    GameActivity.this.showhideemptystate(false, "", 0);
                    GameActivity.this.courseModelList = courseListModel;
                    GameActivity.this.icon_search.setVisibility(View.VISIBLE);
                    GameActivity.this.expandfilter.setVisibility(View.VISIBLE);
                    GameActivity.this.getCourses();
                    GameActivity.this.insertintocategoreytagstable();
                }

                public void onFailure(Throwable t) {
                    Log.d(t.toString(), "Error");
                    GameActivity.this.showhideemptystate(true, GameActivity.this.getResources().getString(R.string.error_message), 1);
                    if (!GameActivity.this.isFinishing()) {
                        GameActivity.this.dismissProgressDialog();
                    }
                }
            });
            return;
        }
        Toast.makeText(this.mContext, getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
        showhideemptystate(true, getResources().getString(R.string.error_message), 1);
        this.recyclerView.setVisibility(View.GONE);
        if (!isFinishing()) {
            dismissProgressDialog();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        getListOfCourses();
        getnotificationcount();
        if (!this.isFilterenabled) {
            this.filtersection.setVisibility(View.GONE);
            this.catlist.clear();
            this.tagslist.clear();
            this.CATS = "";
            this.TAGS = "";
            this.catFiltered = false;
            this.tagfiltered = false;
            this.assetfilterheading.setVisibility(View.GONE);
            this.header.setVisibility(View.VISIBLE);
            this.isFilterenabled = false;
            clearfiltersFunction();
            this.bottommenusection.setVisibility(View.VISIBLE);
        }
        this.uploadActivity.insertUserTracking("LP", this.latitude, this.longitude);
        this.uploadActivity = new UploadActivity(this.mContext);
        if (NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            this.uploadActivity.uploadTrackingTable();
        } else {
            this.emptyimage.setImageResource(R.drawable.interenet_error_image);
            TextView textView = this.emptymessage;
            textView.setText(getString(R.string.oops_no_result) + getString(R.string.enable_network));
        }
        checkForAppUpdates();
    }

    private void checkForAppUpdates() {
        User userobj = (User) new Gson().fromJson(PreferenceUtils.getUser(this), User.class);
        if (TextUtils.isEmpty(PreferenceUtils.getCompanyCode(this.mContext))) {
            PreferenceUtils.setCompanyCode(this.mContext, userobj.getCompany_Code());
        }
        try {
            this.packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            Log.d("DashboardActivity", e1.getMessage());
        }
        if (this.packageInfo != null) {
            getLatestAppVersionFromAPI();
        }
    }

    private void getLatestAppVersionFromAPI() {
        if (NetworkUtils.checkIfNetworkAvailable(this)) {
            ((AppInfoService) RetrofitAPIBuilder.getInstance().create(AppInfoService.class)).getLatestAppVersion(PreferenceUtils.getCompanyCode(this.mContext), Constants.OS_NAME).enqueue(new Callback<APIResponse<AppInfo>>() {
                public void onResponse(Response<APIResponse<AppInfo>> response, Retrofit retrofit3) {
                    APIResponse apiResponse = response.body();
                    List<AppInfo> appInfo = null;
                    if (apiResponse != null) {
                        appInfo = apiResponse.getResultDetails();
                    }
                    if (appInfo != null && appInfo.size() > 0 && appInfo.get(0).isUpgradeRequired() == 1 && Double.valueOf((double) GameActivity.this.packageInfo.versionCode).doubleValue() < Double.valueOf(appInfo.get(0).getVersion()).doubleValue()) {
                        PreferenceUtils.setIsForUpdateVersion(GameActivity.this, appInfo.get(0).getVersion());
                        PreferenceUtils.setIsForUpdateAvailable(GameActivity.this, true);
                        GameActivity.this.showForceUpdateAlert();
                    }
                }

                public void onFailure(Throwable t) {
                    Log.d("Error", "Getting version from API" + t.getMessage());
                }
            });
        } else if (!PreferenceUtils.getIsForUpdateAvailable(this)) {
        } else {
            if (this.packageInfo.versionCode < Integer.parseInt(PreferenceUtils.getIsForUpdateVersion(this))) {
                showForceUpdateAlert();
            } else {
                PreferenceUtils.setIsForUpdateAvailable(this, false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void showForceUpdateAlert() {
        new iOSDialogBuilder(this.mContext).setTitle("New Version Available").setSubtitle(getResources().getString(R.string.update_alert)).setBoldPositiveLabel(false).setCancelable(false).setSingleButtonView(true).setPositiveListener("", (iOSDialogClickListener) null).setNegativeListener("", (iOSDialogClickListener) null).setSinglePositiveListener("UPDATE", new iOSDialogClickListener() {
            public void onClick(iOSDialog dialog) {
                dialog.dismiss();
                GameActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.APP_URL)));
            }
        }).build().show();
    }

    public void onLocationChanged(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public void getCourses() {
        if (!this.isFilterenabled) {
            if (this.courseModelList.size() > 0) {
                showhideemptystate(false, "", 0);
                this.recyclerView.setVisibility(View.VISIBLE);
                this.courseModelListgame.clear();
                for (int i = 0; i < this.courseModelList.size(); i++) {
                    if (this.courseModelList.get(i).getIs_Game_Course() == 1 && this.courseModelList.get(i).getGame_Id() != 0) {
                        this.courseModelListgame.add(this.courseModelList.get(i));
                    }
                }
                this.courseListAdapter = new CourseListAdapterGame(this.mContext, this.courseModelListgame);
                new SectionedRecyclerViewAdapter();
                List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<>();
                if (this.courseModelListgame.size() > 0) {
                    for (int i2 = 0; i2 < this.courseModelListgame.size(); i2++) {
                        if (i2 == 0) {
                            sections.add(new SectionedGridRecyclerViewAdapter.Section(0, this.courseModelListgame.get(0).Category_Name));
                        }
                        if (i2 + 1 < this.courseModelListgame.size() && this.courseModelListgame.get(i2).getGame_Id() != this.courseModelListgame.get(i2 + 1).getGame_Id()) {
                            sections.add(new SectionedGridRecyclerViewAdapter.Section(i2 + 1, this.courseModelListgame.get(i2 + 1).getGame_Name()));
                        }
                    }
                }
                SectionedGridRecyclerViewAdapter mSectionedAdapter = new SectionedGridRecyclerViewAdapter(this.mContext, R.layout.section, R.id.section_text, this.recyclerView, this.courseListAdapter);
                mSectionedAdapter.setSections((SectionedGridRecyclerViewAdapter.Section[]) sections.toArray(new SectionedGridRecyclerViewAdapter.Section[sections.size()]));
                this.recyclerView.setAdapter(mSectionedAdapter);
                if (!isFinishing()) {
                    dismissProgressDialog();
                }
            } else {
                showhideemptystate(true, getResources().getString(R.string.emptyDashboard), 3);
                this.recyclerView.setVisibility(View.GONE);
            }
            loadadapterclick();
            return;
        }
        getCoursesbyIDs();
    }

    public void loadadapterclick() {
        this.courseListAdapter.setOnItemClickListener(new CourseListAdapterGame.MyClickListener() {
            public void onItemClick(int courseId) {
                if (NetworkUtils.isNetworkAvailable()) {
                    CourseModel courseModel = new CourseModel();
                    Iterator<CourseModel> it = GameActivity.this.courseModelList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        CourseModel course = it.next();
                        if (courseId == course.getCourse_Id()) {
                            courseModel = course;
                            break;
                        }
                    }
                    if (!courseModel.getPrerequisite().equalsIgnoreCase("") && courseModel.getCourse_Status_Value() == 0) {
                        GameActivity.this.ShowPreRequsite(courseModel.getPrerequisite());
                    } else if (courseModel.getCourse_Status_Value() == 4) {
                        if (!courseModel.isCourseExtend()) {
                            GameActivity.this.gotosectionActivity(courseModel);
                        } else if (courseModel.getAutoExtendDays() < courseModel.getExtendLimits()) {
                            GameActivity.this.ShowCourseExtendpopup(courseModel);
                        } else {
                            GameActivity.this.gotosectionActivity(courseModel);
                        }
                    } else if (courseModel.getCourse_Status_Value() == 4) {
                        GameActivity.this.gotosectionActivity(courseModel);
                    } else if (courseModel.getIsCourseRestart() == 1) {
                        GameActivity.this.courseCheckListRestartUpdate(courseModel);
                        GameActivity.this.gotosectionActivity(courseModel);
                    } else {
                        GameActivity.this.gotosectionActivity(courseModel);
                    }
                } else {
                    Toast.makeText(GameActivity.this.mContext, GameActivity.this.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gotosectionActivity(CourseModel courseModel) {
        Intent intent = new Intent(this.mContext, SectionActivity.class);
        intent.putExtra("Course_Id", courseModel.getCourse_Id());
        intent.putExtra("Publish_Id", courseModel.getPublish_Id());
        intent.putExtra("Course_Name", courseModel.getCourse_Name());
        intent.putExtra(Constants.Course_Description, courseModel.getCourse_Description());
        intent.putExtra(Constants.Course_Thumbnail, courseModel.getCourse_Image_URL());
        intent.putExtra(Constants.Course_Status, courseModel.getCourse_Status_String());
        intent.putExtra("Course_Status_INT", courseModel.getCourse_Status_Value());
        intent.putExtra("Cats", courseModel.getCategory_Name());
        intent.putExtra("Tags", courseModel.getCourse_Tags());
        Bundle bundle = new Bundle();
        bundle.putSerializable(AbstractEvent.VALUE, courseModel);
        intent.putExtras(bundle);
        intent.putExtra(Constants.Is_From_DashBoard, false);
        intent.putExtra("isgame", true);
        startActivity(intent);
    }

    public void ShowCourseExtendpopup(final CourseModel courseModel) {
        this.messageDialog.ShowCourseExtend(this.mContext, getString(R.string.extend_course), new View.OnClickListener() {
            public void onClick(View Approve) {
                GameActivity.this.requestExtentDateForUsers(courseModel);
                GameActivity.this.messageDialog.dialogDismiss();
            }
        }, new View.OnClickListener() {
            public void onClick(View close) {
                GameActivity.this.messageDialog.dialogDismiss();
                GameActivity.this.gotosectionActivity(courseModel);
            }
        }, true);
    }

    public void requestExtentDateForUsers(final CourseModel courseModel) {
        if (NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            String offsetFromUtc = CommonUtils.getUtcOffsetincluded10k();
            int CompanyId = PreferenceUtils.getCompnayId(this.mContext);
            String SubdomainName = PreferenceUtils.getSubdomainName(this.mContext);
            CourseExtendModel c = new CourseExtendModel();
            c.setCompanyId(CompanyId);
            c.setCourseId(courseModel.getCourse_Id());
            c.setCourseUserMappingIds(String.valueOf(courseModel.getCourse_User_Assignment_Id()));
            c.setExtendDays(courseModel.getExtendDays());
            c.setLocalTimeZone(offsetFromUtc);
            c.setOffsetValue(offsetFromUtc);
            c.setSubdomainName(SubdomainName);
            ((LPCourseService) RetrofitAPIBuilder.getInstance().create(LPCourseService.class)).LPCourseAutoExtentDateForUsers(c).enqueue(new Callback<String>() {
                public void onResponse(Response<String> response, Retrofit retrofit3) {
                    String courseListModel = response.body();
                    if (courseListModel != null && courseListModel.equals("Success")) {
                        GameActivity.this.gotosectionActivity(courseModel);
                    }
                }

                public void onFailure(Throwable t) {
                    Log.d(t.toString(), "Error");
                }
            });
        }
    }

    public void courseCheckListRestartUpdate(CourseModel courseModel) {
        if (NetworkUtils.checkIfNetworkAvailable(this.mContext)) {
            int CompanyId = PreferenceUtils.getCompnayId(this.mContext);
            String SubdomainName = PreferenceUtils.getSubdomainName(this.mContext);
            int UserId = PreferenceUtils.getUserId(this.mContext);
            CourseExtendModel c = new CourseExtendModel();
            c.setCompanyId(CompanyId);
            c.setCourseId(courseModel.getCourse_Id());
            c.setUser_Id(UserId);
            c.setSubdomainName(SubdomainName);
            ((LPCourseService) RetrofitAPIBuilder.getInstance().create(LPCourseService.class)).CourseCheckListRestartUpdate(CompanyId, courseModel.getCourse_Id(), UserId, SubdomainName).enqueue(new Callback<String>() {
                public void onResponse(Response<String> response, Retrofit retrofit3) {
                    String courseListModel = response.body();
                    if (courseListModel != null) {
                        courseListModel.equals("Success");
                    }
                }

                public void onFailure(Throwable t) {
                    Log.d(t.toString(), "Error");
                }
            });
        }
    }

    public void showProgressDialog() {
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage(getResources().getString(R.string.loading));
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setProgressStyle(16973854);
        this.mProgressDialog.setIndeterminate(false);
        this.mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }

    public void ShowPreRequsite(String premessage) {
        this.messageDialog.showCustomAlertMessageDialog(this.mContext, "Pre Requsite", getResources().getString(R.string.prerequsitebefore) + " '" + premessage + "' " + getResources().getString(R.string.prerequsiteafter), new View.OnClickListener() {
            public void onClick(View Approve) {
                GameActivity.this.messageDialog.dialogDismiss();
            }
        }, true);
    }

    private void loadFilteredList(List<CourseModel> csmodel) {
        if (!isFinishing() || this.mProgressDialog != null) {
            dismissProgressDialog();
        }
        this.isFilterenabled = true;
        int countchecked = 0;
        ArrayList<CourseModel> filteredCourse = new ArrayList<>();
        this.completed_checked = this.mCheckCompleted.isChecked();
        this.yet_to_start_checked = this.mCheckYetToStart.isChecked();
        this.in_progress_checked = this.mCheckInProgress.isChecked();
        this.expired = this.mCheckExpired.isChecked();
        this.max_attempts_reached = this.mCheckMaxAttempts.isChecked();
        if (this.completed_checked || this.yet_to_start_checked || this.in_progress_checked || this.expired || this.max_attempts_reached) {
            if (this.completed_checked) {
                for (CourseModel courseModel : csmodel) {
                    if (courseModel.getCourse_Status_Value() == 2) {
                        filteredCourse.add(courseModel);
                    }
                }
                countchecked = 0 + 1;
            }
            if (this.yet_to_start_checked) {
                for (CourseModel courseModel2 : csmodel) {
                    if (courseModel2.getCourse_Status_Value() == 0) {
                        filteredCourse.add(courseModel2);
                    }
                }
                countchecked++;
            }
            if (this.in_progress_checked) {
                for (CourseModel courseModel3 : csmodel) {
                    if (courseModel3.getCourse_Status_Value() == 1) {
                        filteredCourse.add(courseModel3);
                    }
                }
                countchecked++;
            }
            if (this.expired) {
                for (CourseModel courseModel4 : csmodel) {
                    if (courseModel4.getCourse_Status_Value() == 4) {
                        filteredCourse.add(courseModel4);
                    }
                }
                countchecked++;
            }
            if (this.max_attempts_reached) {
                for (CourseModel courseModel5 : csmodel) {
                    if (courseModel5.getCourse_Status_Value() == 3) {
                        filteredCourse.add(courseModel5);
                    }
                }
                int countchecked2 = countchecked + 1;
            }
            showhideemptystate(false, "", 0);
            this.courseListAdapter = new CourseListAdapterGame(this.mContext, filteredCourse);
            this.recyclerView.setAdapter(this.courseListAdapter);
            if (filteredCourse.size() == 0) {
                showhideemptystate(true, getResources().getString(R.string.no_result), 2);
                return;
            }
            return;
        }
        this.courseListAdapter = new CourseListAdapterGame(this.mContext, csmodel);
        this.recyclerView.setAdapter(this.courseListAdapter);
        this.mFilteredCountStatus.setVisibility(View.INVISIBLE);
    }

    public void loadPopUpHelpView() {
        String URL;
        String lan = "";
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.equalsIgnoreCase("English")) {
            lan = "en-";
        } else if (language.equalsIgnoreCase("Español")) {
            lan = "es-";
        }
        if (PreferenceUtils.getSubdomainName(this.mContext).contains("tacobell")) {
            URL = "http://kanglenewqa.kangle.me//HelpFiles/taco/" + lan + "Kanglehelpcourse.htm";
        } else {
            URL = "http://kanglenewqa.kangle.me//HelpFiles/other/Kanglehelpcourse.html";
        }
        this.messageDialog.Showhelppopup(this.mContext, new View.OnClickListener() {
            public void onClick(View Approve) {
                GameActivity.this.messageDialog.dialogDismiss();
            }
        }, URL, false);
    }

    public void loadHelpView() {
        String URL;
        this.recyclerView.setVisibility(View.GONE);
        this.helplayout.setVisibility(View.VISIBLE);
        String lan = "";
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.equalsIgnoreCase("English")) {
            lan = "en-";
        } else if (language.equalsIgnoreCase("Español")) {
            lan = "es-";
        }
        if (PreferenceUtils.getSubdomainName(this.mContext).contains("tacobell")) {
            URL = "http://kanglenewqa.kangle.me//HelpFiles/taco/" + lan + "Kanglehelpcourse.htm";
        } else {
            URL = "http://kanglenewqa.kangle.me//HelpFiles/other/Kanglehelpcourse.html";
        }
        this.helpView.getSettings().setDomStorageEnabled(true);
        this.helpView.getSettings().setJavaScriptEnabled(true);
        this.helpView.getSettings().setLoadWithOverviewMode(true);
        this.helpView.getSettings().setUseWideViewPort(true);
        this.helpView.getSettings().setBuiltInZoomControls(true);
        this.helpView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
            }
        });
        this.helpView.loadUrl(URL);
    }

    public void loadCategorySlider() {
        try {
            toggleselection(1);
            if (this.tagslist.size() > 0) {
                String tagnames = "";
                for (String catname : this.tagslist) {
                    tagnames = tagnames + ",'" + catname.toString() + "'";
                }
                StringBuilder sb = new StringBuilder(tagnames);
                sb.deleteCharAt(0);
                reloadCatSlider(this.courseCatTagFilterRepository.getCategorybySelectedTags(sb.toString().replace("''", "'"), this.CATS));
                return;
            }
            reloadCatSlider(this.courseCatTagFilterRepository.getAllCategory(this.CATS));
        } catch (Exception e) {
        }
    }

    public void loadTagsSlider() {
        try {
            toggleselection(2);
            if (this.catlist.size() > 0) {
                String catnames = "";
                for (String catname : this.catlist) {
                    catnames = catnames + ",'" + catname.toString() + "'";
                }
                StringBuilder sb = new StringBuilder(catnames);
                sb.deleteCharAt(0);
                reloadTagSlider(this.courseCatTagFilterRepository.getTagsbySelectedcategorey(sb.toString().replace("''", "'"), this.TAGS));
                return;
            }
            reloadTagSlider(this.courseCatTagFilterRepository.getAllTags(this.TAGS));
        } catch (Exception e) {
        }
    }

    public void reloadCatSlider(List<CourseModel> newmodel) {
        List<CourseModel> catrefresh = new ArrayList<>();
        new ArrayList();
        for (CourseModel existing : this.digitalAssetsMasterCategoryLists) {
            if (existing.iscatchecked) {
                catrefresh.add(existing);
            }
        }
        for (CourseModel ne : newmodel) {
            catrefresh.add(ne);
        }
        this.emptytagsview.setVisibility(View.GONE);
        this.cattag_recyclerView.setVisibility(View.VISIBLE);
        this.digitalAssetsMasterCategoryLists = catrefresh;
        this.categoryListRecyclerAdapter = new CourseCategoryListAdapterGame(this.mContext, this.digitalAssetsMasterCategoryLists, true);
        this.cattag_recyclerView.setAdapter(this.categoryListRecyclerAdapter);
    }

    public void reloadTagSlider(List<CourseModel> newmodel) {
        List<CourseModel> tagrefresh = new ArrayList<>();
        new ArrayList();
        for (CourseModel existing : this.digitalAssetsMasterTagsLists) {
            if (existing.istagchecked) {
                tagrefresh.add(existing);
            }
        }
        for (CourseModel ne : newmodel) {
            tagrefresh.add(ne);
        }
        this.digitalAssetsMasterTagsLists = tagrefresh;
        showtagsView(this.digitalAssetsMasterTagsLists, true);
    }

    public void showtagsView(List<CourseModel> digitalAssetsMasterTagsList, boolean status) {
        if (digitalAssetsMasterTagsList.size() > 0) {
            this.emptytagsview.setVisibility(View.GONE);
            this.checkBoxGroupView.setVisibility(View.GONE);
            this.cattag_recyclerView.setVisibility(View.VISIBLE);
            this.tagsListRecyclerAdapter = new CourseTagsListAdapterGame(this.mContext, digitalAssetsMasterTagsList, status);
            this.cattag_recyclerView.setAdapter(this.tagsListRecyclerAdapter);
            return;
        }
        this.cattag_recyclerView.setVisibility(View.GONE);
        this.checkBoxGroupView.setVisibility(View.GONE);
        this.emptytagsview.setVisibility(View.VISIBLE);
    }

    public void insertintocategoreytagstable() {
        List<CourseModel> digitalAssetsListfortags = this.courseModelList;
        List<CourseModel> cttags = new ArrayList<>();
        for (CourseModel dig : digitalAssetsListfortags) {
            if (dig.getCategory_Name() != null && !dig.getCategory_Name().isEmpty()) {
                if (dig.getCourse_Tags() == "" || dig.getCourse_Tags() == null || dig.getCourse_Tags().isEmpty()) {
                    CourseModel values = new CourseModel();
                    values.setCourse_Id(dig.getCourse_Id());
                    values.setCourse_Category_Id(dig.getCourse_Category_Id());
                    values.setCourse_Tags(dig.getCourse_Tags());
                    values.setCategory_Name(dig.getCategory_Name());
                    values.setTags((String) null);
                    cttags.add(values);
                } else {
                    for (String t : dig.getCourse_Tags().replace("^", "#").split("#")) {
                        CourseModel values2 = new CourseModel();
                        values2.setCourse_Id(dig.getCourse_Id());
                        values2.setCourse_Category_Id(dig.getCourse_Category_Id());
                        values2.setCourse_Tags(dig.getCourse_Tags());
                        values2.setCategory_Name(dig.getCategory_Name());
                        values2.setTags(t);
                        cttags.add(values2);
                    }
                }
            }
        }
        this.courseCatTagFilterRepository.catTagsBulkInsert(cttags);
    }

    public void filteredcatList(CourseModel cat) {
        try {
            if (cat.iscatchecked()) {
                this.catlist.add(cat.getCategory_Name());
            } else {
                this.catlist.remove(cat.getCategory_Name());
            }
            if (this.catlist.size() > 0) {
                this.catFiltered = true;
            } else {
                this.catFiltered = false;
            }
            if (this.catFiltered) {
                String catnames = "";
                for (String catname : this.catlist) {
                    catnames = catnames + ",'" + catname.toString() + "'";
                }
                StringBuilder sb = new StringBuilder(catnames);
                sb.deleteCharAt(0);
                this.CATS = sb.toString().replace("''", "'");
            } else {
                this.CATS = "";
            }
            showApplyButton();
        } catch (Exception e) {
        }
    }

    public void filteredtagList(CourseModel cat) {
        try {
            if (cat.istagchecked()) {
                this.tagslist.add(cat.getTags());
            } else {
                this.tagslist.remove(cat.getTags());
            }
            if (this.tagslist.size() > 0) {
                this.tagfiltered = true;
            } else {
                this.tagfiltered = false;
            }
            if (this.tagfiltered) {
                String tagnames = "";
                for (String catname : this.tagslist) {
                    tagnames = tagnames + ",'" + catname.toString() + "'";
                }
                StringBuilder sb = new StringBuilder(tagnames);
                sb.deleteCharAt(0);
                this.TAGS = sb.toString().replace("''", "'");
            } else {
                this.TAGS = "";
            }
            showApplyButton();
        } catch (Exception e) {
        }
    }

    public void getCoursesbyIDs() {
        String selectQuery = "SELECT tbl_COURSE_CAT_TAG_MASTER.Course_Id FROM tbl_COURSE_CAT_TAG_MASTER  ";
        try {
            if (this.CATS.length() > 0 || this.TAGS.length() > 0) {
                selectQuery = selectQuery + "WHERE ";
                if (this.CATS.length() > 0) {
                    selectQuery = selectQuery + "tbl_COURSE_CAT_TAG_MASTER.Category_Name in (" + this.CATS + ") ";
                }
                if (this.TAGS.length() > 0) {
                    if (this.CATS.length() > 0) {
                        selectQuery = selectQuery + " AND ";
                    }
                    selectQuery = selectQuery + "tbl_COURSE_CAT_TAG_MASTER.Tags in (" + this.TAGS + ") ";
                }
            }
            filteredlist(this.courseCatTagFilterRepository.getAssetIdbySelectedCatTag(selectQuery + " Group by Course_Id"));
        } catch (Exception e) {
        }
    }

    public void filteredlist(List<CourseModel> DAIDlist) {
        if (DAIDlist != null) {
            try {
                if (DAIDlist.size() > 0) {
                    this.digitalAssetsMasterListfilterd = new ArrayList();
                    for (CourseModel cms : DAIDlist) {
                        for (CourseModel cs : this.courseModelList) {
                            if (cms.getCourse_Id() == cs.getCourse_Id()) {
                                this.digitalAssetsMasterListfilterd.add(cs);
                            }
                        }
                    }
                    loadFilteredList(this.digitalAssetsMasterListfilterd);
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
        } else {
            loadFilteredList(this.courseModelList);
        }
    }

    public void showApplyButton() {
        if (this.catFiltered || this.tagfiltered || this.mCheckCompleted.isChecked() || this.mCheckYetToStart.isChecked() || this.mCheckInProgress.isChecked() || this.mCheckExpired.isChecked() || this.mCheckMaxAttempts.isChecked()) {
            this.applyfilters.setVisibility(View.VISIBLE);
            this.applyfilters.setBackgroundColor(Color.parseColor(Constants.COMPANY_COLOR));
            this.applyfilters.setEnabled(true);
            return;
        }
        this.applyfilters.setBackgroundColor(Color.parseColor(Constants.GREY_COLOR));
        this.applyfilters.setEnabled(false);
    }

    public void toggleselection(int num) {
        this.cattagmenus.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
        if (num == 1) {
            this.filterheadingtext.setText(getResources().getString(R.string.category));
            this.catselection.setBackgroundColor(-1);
            this.icon_cats.setColorFilter(Color.parseColor(Constants.COMPANY_COLOR));
            this.icon_tags.setColorFilter(-1);
            this.icon_filter.setColorFilter(-1);
            this.tagselection.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
            this.filterlay.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
        } else if (num == 2) {
            this.filterheadingtext.setText(getResources().getString(R.string.Tags));
            this.tagselection.setBackgroundColor(-1);
            this.icon_tags.setColorFilter(Color.parseColor(Constants.COMPANY_COLOR));
            this.icon_cats.setColorFilter(-1);
            this.icon_filter.setColorFilter(-1);
            this.filterlay.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
            this.catselection.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
        } else if (num == 3) {
            this.emptytagsview.setVisibility(View.GONE);
            this.filterheadingtext.setText(getResources().getString(R.string.status));
            this.filterlay.setBackgroundColor(-1);
            this.icon_filter.setColorFilter(Color.parseColor(Constants.COMPANY_COLOR));
            this.icon_tags.setColorFilter(-1);
            this.icon_cats.setColorFilter(-1);
            this.catselection.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
            this.tagselection.setBackgroundColor(Color.parseColor(Constants.CARDBACKGROUND_COLOR));
        }
    }

    public void showhideemptystate(boolean showempty, String message, int type) {
        if (showempty) {
            this.mEmptyView.setVisibility(View.VISIBLE);
            if (type == 1) {
                this.emptyimage.setVisibility(View.VISIBLE);
                this.emptyimage.setImageResource(R.drawable.interenet_error_image);
                this.retrybutton.setVisibility(View.VISIBLE);
            } else if (type == 2) {
                this.emptyimage.setVisibility(View.VISIBLE);
                this.emptyimage.setImageResource(R.drawable.no_results);
                this.retrybutton.setVisibility(View.GONE);
            } else if (type == 3) {
                this.emptyimage.setVisibility(View.GONE);
                this.retrybutton.setVisibility(View.GONE);
            }
            this.emptymessage.setText(message.toString());
            return;
        }
        this.mEmptyView.setVisibility(View.GONE);
    }
}
