package com.nutritechinese.sdklordvideoservice;

import android.app.Activity;
import android.content.Context;

import com.nutritechinese.sdklordvideoservice.api.VideoService;
import com.nutritechinese.sdklordvideoservice.api.callback.OnGetVideoRecordListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnMenuListReceivedListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnPostCompleteListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnVideoDetailReceivedListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnVideoListReceivedListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnVideoPlayRecordCountListener;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetMenuListParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetRecommendVideoListParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoDetailParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoListParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoPlayRecordCountParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoRecordParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.PostVideoRecordParam;
import com.nutritechinese.sdklordvideoservice.net.VideoUserAgent;
import com.soaringcloud.kit.box.AndroidKit;
import com.soaringcloud.kit.box.DisplayKit;

public class LordVideoManager {
    private VideoUserAgent userAgent;
    private VideoService videoService;
    private Context context;
    private Activity activity;
    private int screenWidth;
    private int screenHeight;

    public LordVideoManager(Context context, int screenWidth, int screenHeight) {
        initUserAgent(context, screenWidth, screenHeight);
    }

    public LordVideoManager(Activity context) {
        initUserAgent(context);
    }

    private  void initUserAgent(Activity activity) {
        userAgent = new VideoUserAgent();
        userAgent.setAppId(AndroidKit.getMetaDataValue(activity, LordSettings.META_APP_ID));
        userAgent.setUserId(AndroidKit.getClientId(activity));
        userAgent.setResolution(DisplayKit.getScreenMetric(activity).widthPixels + "*" + DisplayKit.getScreenMetric(activity).heightPixels);
        userAgent.setVersion("Android" + AndroidKit.getAndroidVersionCode());
        userAgent.setPlaySource(AndroidKit.getMetaDataValue(activity, LordSettings.META_LORD_SOURCE));
        videoService = new VideoService(userAgent);
    }

    private  void initUserAgent(Context context, int width, int height) {
        userAgent = new VideoUserAgent();
        userAgent.setAppId(AndroidKit.getMetaDataValue(context, LordSettings.META_APP_ID));
        userAgent.setUserId(AndroidKit.getClientId(context));
        userAgent.setResolution(width + "*" + height);
        userAgent.setVersion("Android" + AndroidKit.getAndroidVersionCode());
        userAgent.setPlaySource(AndroidKit.getMetaDataValue(context, LordSettings.META_LORD_SOURCE));
        videoService = new VideoService(userAgent);
    }

    /**
     * 用于设置自定义UserAgent
     */
    public  void setUserAgent(VideoUserAgent ua) {
        userAgent = ua;
        videoService = new VideoService(ua);
    }

    /**
     * 用于设置自定义UserID
     */
    public void setUserId(String userId) {
        userAgent.setUserId(userId);
        videoService = new VideoService(userAgent);
    }

    public void getVideoList(GetVideoListParam param, final OnVideoListReceivedListener listener) {
        videoService.getVideoList(param, listener);
    }

    public void postVideoPlayRecord(PostVideoRecordParam param, final OnPostCompleteListener listener) {
        videoService.postVideoPlayRecord(param, listener);
    }

    public void getVideoPlayRecord(GetVideoRecordParam param, final OnGetVideoRecordListener listener) {
        videoService.getVideoPlayRecord(param, listener);
    }

    public void getVideoDetail(GetVideoDetailParam param, final OnVideoDetailReceivedListener listener) {
        videoService.getVideoDetail(param, listener);
    }

    public void getMenu(GetMenuListParam param, OnMenuListReceivedListener listener) {
        videoService.getVideoMenuList(param, listener);
    }

    public void getRecommendVideoList(GetRecommendVideoListParam param, OnVideoListReceivedListener listener) {
        videoService.getRecommendVideoList(param, listener);
    }

    public void getVideoPlayRecordCount(GetVideoPlayRecordCountParam param, OnVideoPlayRecordCountListener listener) {
        videoService.getVideoPlayRecordCount(param, listener);
    }

    public void postVideoAppPlayCount(GetVideoPlayRecordCountParam param, OnPostCompleteListener listener) {
        videoService.getVideoAppPlayCount(param, listener);
    }

    public void onDestroy() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
