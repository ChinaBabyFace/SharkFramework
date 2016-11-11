package com.nutritechinese.sdklordvideoservice.api;


import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoPlayRecordCountParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.PostVideoRecordParam;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.ResultJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoMenuItemJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoRecordCountJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoRecordJo;
import com.soaring.nuitrio.http.response.MetaDataResponse;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface VideoAPI {
    /**
     * 获取分类视频列表
     * optionSearchKeyWord按关键字搜索,可为空
     * videoClassifyId按分类搜,可为空
     * 都不为空时表示按关键字在分类下搜
     */
    @GET("GetVideoList")
    Call<MetaDataResponse<List<VideoJo>>> getVideoList(
            @Query("optionSearchKeyWord") String optionSearchKeyWord,
            @Query("videoClassifyId") String videoClassifyId,
            @Query("orderBy") String orderBy,
            @Query("pageSize") Integer pageSize,
            @Query("pageIndex") Integer pageIndex);

    /**
     * 获取单个视频播详情
     */
    @GET("GetVideo")
    Call<MetaDataResponse<VideoJo>> getVideoDetail(
            @Query("videoId") String videoId);

    @GET("GetRecommendVideoList")
    Call<MetaDataResponse<List<VideoJo>>> getRecommendVideoList(
            @Query("videoId") String videoId,
            @Query("pageSize") Integer pageSize,
            @Query("pageIndex") Integer pageIndex);

    /**
     * 获取指定视频历史播放进度
     */
    @GET("VideoPlayRecord")
    Call<MetaDataResponse<VideoRecordJo>> getVideoRecordList(
            @Query("videoId") String videoId);

    /**
     * 上传指定视频播放进度
     */
    @POST("VideoPlayRecord")
    Call<MetaDataResponse<ResultJo>> postVideoRecord(
            @Body PostVideoRecordParam param);

    /**
     * 获取菜单
     * menuId为Null时搜顶级全部菜单
     * menuId不为空时搜子菜单
     */
    @GET("GetVideoClassifyList")
    Call<MetaDataResponse<List<VideoMenuItemJo>>> getMenuList(
            @Query("videoMenuId") String menuId);
    /**
     * 获取单个视频的播放次数*/
    @GET("VideoPlayRecordCount")
    Call<MetaDataResponse<VideoRecordCountJo>> getVideoPlayRecordCount(
            @Query("videoId") String videoId);

    @POST("VideoAppPlayCount")
    Call<MetaDataResponse<ResultJo>> postVideoAppPlayCount(@Body GetVideoPlayRecordCountParam param);

}
