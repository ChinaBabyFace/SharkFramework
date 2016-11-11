package com.nutritechinese.sdklordvideoservice.api.model.param;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renyuxiang on 2015/12/9.
 */
public class GetMenuListParam {
    @SerializedName("videoMenuId")
    @Expose
    private String videoMenuId;

    public String getVideoMenuId() {
        return videoMenuId;
    }

    public void setVideoMenuId(String videoMenuId) {
        this.videoMenuId = videoMenuId;
    }
}
