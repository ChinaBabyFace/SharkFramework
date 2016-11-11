package com.nutritechinese.sdklordvideoservice.api.model.param;

public class GetVideoListParam {
    private String videoClassifyId;
    private String optionSearchKeyWord;
    private int pageIndex = 1;
    private int pageSize = 10;
    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getVideoClassifyId() {
        return videoClassifyId;
    }

    public void setVideoClassifyId(String videoClassifyId) {
        this.videoClassifyId = videoClassifyId;
    }
    public String getOptionSearchKeyWord() {
        return optionSearchKeyWord;
    }

    public void setOptionSearchKeyWord(String optionSearchKeyWord) {
        this.optionSearchKeyWord = optionSearchKeyWord;
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
