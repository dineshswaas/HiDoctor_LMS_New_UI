package com.swaas.kangle;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.swaas.kangle.API.model.LandingPageAccess;
import com.swaas.kangle.API.service.UserService;
import com.swaas.kangle.LPCourse.CourseListActivity;
import com.swaas.kangle.db.RetrofitAPIBuilder;
import com.swaas.kangle.models.MenuModel;
import com.swaas.kangle.preferences.PreferenceUtils;
import com.swaas.kangle.utils.NetworkUtils;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;


    public Context getCtx() {
        return mContext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;


        try {
            if(PreferenceUtils.getUser(MainActivity.this) != null){
                if (!PreferenceUtils.getUser(MainActivity.this).isEmpty()) {
                    if (NetworkUtils.checkIfNetworkAvailable(mContext)) {
                        getMenus();
                        //getMenusNewApi();
                    } else {

                        SetTheme();
                      //  Toast.makeText(mContext, mContext.getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    //Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }else{
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }catch(Exception e){

        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        //animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate() {
        ImageView logoImageView = (ImageView) findViewById(R.id.logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.activity_main);

        ViewCompat.animate(logoImageView)
                .translationY(-250)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (!(v instanceof Button)) {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(1000);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((ITEM_DELAY * i) + 500)
                        .setDuration(500);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }


    public void getMenus() {
            Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
            UserService userService = retrofitAPI.create(UserService.class);
            Call call = userService.getLandingPageAccess(PreferenceUtils.getSubdomainName(mContext), PreferenceUtils.getUserId(mContext), PreferenceUtils.getCompnayId(mContext));
            call.enqueue(new Callback<List<LandingPageAccess>>() {
                @Override
                public void onResponse(Response<List<LandingPageAccess>> response, Retrofit retrofit) {
                    List<LandingPageAccess> apiResponse = response.body();
                    if (apiResponse != null) {
                        Gson gson = new Gson();
                        String landingobj = gson.toJson(apiResponse.get(0));
                        PreferenceUtils.setLandingPageAccess(mContext, landingobj);
                        SetTheme();
                    } else {
                        Log.d("retrofit", "error 2");
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.d("Login", "error");
                    //error
                }
            });
    }

    //New Menu Api method
    public void getMenusNewApi() {
        Retrofit retrofitAPI = RetrofitAPIBuilder.getInstance();
        UserService userService = retrofitAPI.create(UserService.class);
        Call call = userService.getLandingmenus_API(PreferenceUtils.getSubdomainName(mContext), PreferenceUtils.getUserId(mContext), PreferenceUtils.getCompnayId(mContext),"en_us");
        call.enqueue(new Callback<List<MenuModel>>() {
            @Override
            public void onResponse(Response<List<MenuModel>> response, Retrofit retrofit) {
                List<MenuModel> apiResponse = response.body();
                if (apiResponse != null) {
                    converttoLandingObject(apiResponse);
                } else {
                    Log.d("retrofit", "error 2");
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("Login", "error");
                //error
            }
        });
    }

    public void converttoLandingObject(List<MenuModel> menu){
        LandingPageAccess landingpage = new LandingPageAccess();
        for(MenuModel m : menu){
            if(m.getMenu_id() == 1){
                landingpage.setLibrary("Y");
            }
            if(m.getMenu_id() == 2){
                landingpage.setCourse("L");
            }
            if(m.getMenu_id() == 3){
                landingpage.setChecklist("Y");
            }
            if(m.getMenu_id() == 4){
                landingpage.setTask("Y");
            }
        }
        Gson gson = new Gson();
        String landingobj = gson.toJson(landingpage);
        PreferenceUtils.setLandingPageAccess(mContext, landingobj);

        SetTheme();
    }

    public void gotoActivity(){
        Intent intent = new Intent(MainActivity.this, CourseListActivity.class);
        startActivity(intent);
        finish();
    }

    //New Theme Setup
    public void SetTheme(){
        final ThemeClass t = new ThemeClass(mContext);
        t.setGetThemeDataCBListner(new ThemeClass.GetThemeDataCBListner() {

            @Override
            public void GetThemeDataSuccessCB(boolean Success) {
                if(Success){
                    gotoActivity();
                }
            }

            @Override
            public void GetThemeDataFailureCB(boolean message) {
                t.setTheme();
            }
        });
        t.setTheme();
    }
}
