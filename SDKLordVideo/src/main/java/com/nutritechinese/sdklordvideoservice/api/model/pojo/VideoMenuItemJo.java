package com.nutritechinese.sdklordvideoservice.api.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renyuxiang on 2015/12/7.
 */
public class VideoMenuItemJo {
    @SerializedName("videoClassifyId")
    @Expose
    private String videoClassifyId;
    @SerializedName("classifyName")
    @Expose
    private String classifyName;
    @SerializedName("classifyDesc")
    @Expose
    private String classifyDesc;
    @SerializedName("sorting")
    @Expose
    private Integer sorting;
    @SerializedName("children")
    @Expose
    private List<Object> children = new ArrayList<Object>();

    /**
     * @return The videoClassifyId
     */
    public String getVideoClassifyId() {
        return videoClassifyId;
    }

    /**
     * @param videoClassifyId The videoClassifyId
     */
    public void setVideoClassifyId(String videoClassifyId) {
        this.videoClassifyId = videoClassifyId;
    }

    /**
     * @return The classifyName
     */
    public String getClassifyName() {
        return classifyName;
    }

    /**
     * @param classifyName The classifyName
     */
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    /**
     * @return The classifyDesc
     */
    public String getClassifyDesc() {
        return classifyDesc;
    }

    /**
     * @param classifyDesc The classifyDesc
     */
    public void setClassifyDesc(String classifyDesc) {
        this.classifyDesc = classifyDesc;
    }

    /**
     * @return The sorting
     */
    public Integer getSorting() {
        return sorting;
    }

    /**
     * @param sorting The sorting
     */
    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    /**
     * @return The children
     */
    public List<Object> getChildren() {
        return children;
    }

    /**
     * @param children The children
     */
    public void setChildren(List<Object> children) {
        this.children = children;
    }

}
