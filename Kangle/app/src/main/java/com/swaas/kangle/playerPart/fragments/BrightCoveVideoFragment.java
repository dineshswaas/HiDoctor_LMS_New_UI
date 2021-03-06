package com.swaas.kangle.playerPart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.swaas.kangle.R;
import com.swaas.kangle.playerPart.AssetPlayerActivity;
import com.swaas.kangle.playerPart.DigitalAssets;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sayellessh on 05-04-2018.
 */

public class BrightCoveVideoFragment extends Fragment {

    private Context mContext;
    private String mVideoUrl;
    private int CurrentAssetPosition, playmode;
    private AssetPlayerActivity assetPlayerActivity;
    private BrightcoveExoPlayerVideoView brightcoveVideoView;

    /*public String AccountId = "4800266849001";
    public String PolicyKey = "BCpkADawqM3n0ImwKortQqSZCgJMcyVbb8lJVwt0z16UD0a_h8MpEYcHyKbM8CGOPxBRp0nfSVdfokXBrUu3Sso7Nujv3dnLo0JxC_lNXCl88O7NJ0PR0z2AprnJ_Lwnq7nTcy1GBUrQPr5e";
    public String VideoId = "5255514387001";*/


    /*public String AccountId = "5758753464001";
    public String PolicyKey = "BCpkADawqM3RSUdhZcVqUb2t1BZvbQ6rTIJECTg-V_RL85NDVPC0oOyMyXwvnooP2RvMUb2GMYZKRKMZbCCBhuD6X2K25H6k2gi_fN3dCGiuFWYuPr4Ag579FpxPh432zuAl9i5DToX-CgLV";
    public String VideoId = "5758850080001";*/

    public String AccountId;
    public String PolicyKey;
    public String VideoId;
    public Date DetailStartTime;
    public int initialStartTime = -1;
    DigitalAssets assetobj;
    public boolean isVisible;
    public boolean isViewed = false;
    private CountDownTimer waitTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mContext = getActivity().getApplicationContext();
        assetPlayerActivity = (AssetPlayerActivity) getActivity();
        DetailStartTime = null;
        /*if (bundle!=null){
            mVideoUrl = bundle.getString("url");
            CurrentAssetPosition = bundle.getInt("Index");
            playmode =  bundle.getInt("playmode");
        }*/

        if (bundle != null) {

            mVideoUrl = bundle.getString("url");
            CurrentAssetPosition = bundle.getInt("Index");
            playmode = bundle.getInt("playmode");
            VideoId = bundle.getString("VideoId");
            AccountId = bundle.getString("AccountId");
            PolicyKey = bundle.getString("PolicyKey");

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            isVisible = true;
            DetailStartTime = null;
            initialStartTime = 0;
            isViewed = true;
            setTimer();
            if(brightcoveVideoView != null) {
                initializePlayer();
            }
        }else {
            isVisible = false;

            if (waitTimer!=null){
                waitTimer.cancel();
                waitTimer = null;
            }

            if(isViewed) {
                if (assetPlayerActivity!=null) {
                    if (DetailStartTime != null) {
                        assetPlayerActivity.UpdateBrightCovePlayerEndTime(CurrentAssetPosition, Calendar.getInstance().getTime());
                        brightcoveVideoView.stopPlayback();
                    }
                    if (DetailStartTime != null) {
                        Log.d("===>DetailStartTime",""+DetailStartTime);
                        assetPlayerActivity.UpdateEdetailEndTime(Calendar.getInstance().getTime(), CurrentAssetPosition);
                        DetailStartTime = null;

                    }
                }
                isViewed = false;
            }

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_brightcove_player_viewer, container, false);
        brightcoveVideoView = (BrightcoveExoPlayerVideoView) view.findViewById(R.id.brightcove_video_view);
        setTimer();
        initializePlayer();
        return view;
    }

    public void initializePlayer() {
        EventEmitter eventEmitter = brightcoveVideoView.getEventEmitter();
        Catalog catalog = new Catalog(eventEmitter, AccountId, PolicyKey);
        catalog.findVideoByID(VideoId, new VideoListener() {

            // Add the video found to the queue with add().
            // Start playback of the video with start().
            @Override
            public void onVideo(Video video) {
                brightcoveVideoView.add(video);
                brightcoveVideoView.start();
                brightcoveVideoView.getEventEmitter().emit(EventType.ENTER_FULL_SCREEN);
            }

            @Override
            public void onError(String s) {
                throw new RuntimeException(s);
            }
        });
    }

    private void setTimer() {
        waitTimer = new CountDownTimer(1000, 300) {
            public void onTick(long millisUntilFinished) {
                //called every 300 milliseconds, which could be used to
                //send messages or some other action
            }
            public void onFinish() {

                Calendar calendarfinish = Calendar.getInstance();
                calendarfinish.add(Calendar.SECOND, -1);
                DetailStartTime = Calendar.getInstance().getTime();
                assetPlayerActivity.InsertEdetailStarttime(DetailStartTime, 0, CurrentAssetPosition);
                initialStartTime = 0;
                assetPlayerActivity.InsertPlayerStartTime(initialStartTime, CurrentAssetPosition, playmode);
            }
        }.start();
    }
}