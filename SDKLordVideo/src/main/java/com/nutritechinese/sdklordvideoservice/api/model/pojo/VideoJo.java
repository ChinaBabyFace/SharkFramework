package com.nutritechinese.sdklordvideoservice.api.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VideoJo implements Serializable {
    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("videoName")
    @Expose
    private String videoName;
    @SerializedName("videoDesc")
    @Expose
    private String videoDesc;
    @SerializedName("videoImg")
    @Expose
    private String videoImg;
    @SerializedName("publishTime")
    @Expose
    private String publishTime;
    @SerializedName("videoPlayRecordCount")
    @Expose
    private Integer videoPlayRecordCount;
    @SerializedName("classifyName")
    @Expose
    private String classifyName;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

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
     * @return The videoUrl
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * @param videoUrl The videoUrl
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * @return The videoName
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * @param videoName The videoName
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * @return The videoDesc
     */
    public String getVideoDesc() {
        return videoDesc;
    }

    /**
     * @param videoDesc The videoDesc
     */
    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    /**
     * @return The videoImg
     */
    public String getVideoImg() {
        return videoImg;
    }

    /**
     * @param videoImg The videoImg
     */
    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    /**
     * @return The publishTime
     */
    public String getPublishTime() {
        return publishTime;
    }

    /**
     * @param publishTime The publishTime
     */
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * @return The videoPlayRecordCount
     */
    public Integer getVideoPlayRecordCount() {
        return videoPlayRecordCount;
    }

    /**
     * @param videoPlayRecordCount The videoPlayRecordCount
     */
    public void setVideoPlayRecordCount(Integer videoPlayRecordCount) {
        this.videoPlayRecordCount = videoPlayRecordCount;
    }
}