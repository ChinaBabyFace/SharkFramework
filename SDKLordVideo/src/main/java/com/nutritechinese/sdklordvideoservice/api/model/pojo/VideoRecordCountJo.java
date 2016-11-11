package com.nutritechinese.sdklordvideoservice.api.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lipeng on 2015/12/17.
 */
public class VideoRecordCountJo implements Serializable {
    @SerializedName("videoPlayRecordCount")
    @Expose
    private Integer videoPlayRecordCount;

    public Integer getVideoPlayRecordCount() {
        return videoPlayRecordCount;
    }

    public void setVideoPlayRecordCount(Integer videoPlayRecordCount) {
        this.videoPlayRecordCount = videoPlayRecordCount;
    }
}
