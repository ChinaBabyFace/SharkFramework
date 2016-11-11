package com.nutritechinese.sdklordvideoservice.api.model.param;

/**
 * Created by lipeng on 2015/12/17.
 */
public class GetVideoPlayRecordCountParam {

    private String videoId;
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
