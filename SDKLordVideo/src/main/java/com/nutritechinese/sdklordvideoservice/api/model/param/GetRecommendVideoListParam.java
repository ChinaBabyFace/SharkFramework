package com.nutritechinese.sdklordvideoservice.api.model.param;

/**
 * Created by renyuxiang on 2015/12/10.
 */
public class GetRecommendVideoListParam {
    private String videoId;
    private int pageIndex = 1;
    private int pageSize = 10;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
