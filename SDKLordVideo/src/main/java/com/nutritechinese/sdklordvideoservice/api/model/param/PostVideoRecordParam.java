package com.nutritechinese.sdklordvideoservice.api.model.param;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostVideoRecordParam {

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("suspendTime")
    @Expose
    private int suspendTime;

    /**
     * @return The videoId
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param videoId The videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return The suspendTime
     */
    public int getSuspendTime() {
        return suspendTime;
    }

    /**
     * @param suspendTime The suspendTime
     */
    public void setSuspendTime(int suspendTime) {
        this.suspendTime = suspendTime;
    }

}
