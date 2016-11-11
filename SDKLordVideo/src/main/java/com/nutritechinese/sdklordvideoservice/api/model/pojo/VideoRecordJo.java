package com.nutritechinese.sdklordvideoservice.api.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dongbinbin on 2015/11/25.
 */
public class VideoRecordJo {
    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("suspendTime")
    @Expose
    private int suspendTime;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime(int suspendTime) {
        this.suspendTime = suspendTime;
    }
}
