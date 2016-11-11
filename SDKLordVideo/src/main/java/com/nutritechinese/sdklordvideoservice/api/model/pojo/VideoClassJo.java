package com.nutritechinese.sdklordvideoservice.api.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dongbinbin on 2015/11/26.
 */
public class VideoClassJo {
    @SerializedName("videoClassName")
    @Expose
    private String videoClassName;
    @SerializedName("videoClassId")
    @Expose
    private String videoClassId;
    @SerializedName("isVideoClassSelected")
    @Expose
    private boolean isVideoClassSelected = false;


    public String getVideoClassName() {
        return videoClassName;
    }

    public void setVideoClassName(String videoClassName) {
        this.videoClassName = videoClassName;
    }

    public String getVideoClassId() {
        return videoClassId;
    }

    public void setVideoClassId(String videoClassId) {
        this.videoClassId = videoClassId;
    }

    public boolean isVideoClassSelected() {
        return isVideoClassSelected;
    }

    public void setIsVideoClassSelected(boolean isVideoClassSelected) {
        this.isVideoClassSelected = isVideoClassSelected;
    }
}
