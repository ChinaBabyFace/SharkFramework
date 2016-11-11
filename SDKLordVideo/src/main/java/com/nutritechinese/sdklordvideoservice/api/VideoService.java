package com.nutritechinese.sdklordvideoservice.api;

import com.nutritechinese.sdklordvideoservice.api.callback.OnGetVideoRecordListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnMenuListReceivedListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnPostCompleteListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnVideoDetailReceivedListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnVideoListReceivedListener;
import com.nutritechinese.sdklordvideoservice.api.callback.OnVideoPlayRecordCountListener;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetMenuListParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetRecommendVideoListParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoDetailParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoListParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoPlayRecordCountParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.GetVideoRecordParam;
import com.nutritechinese.sdklordvideoservice.api.model.param.PostVideoRecordParam;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.ResultJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoMenuItemJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoRecordCountJo;
import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoRecordJo;
import com.soaring.nuitrio.http.core.HttpManager;
import com.soaring.nuitrio.http.header.IUserAgent;
import com.soaring.nuitrio.http.response.MetaDataResponse;
import com.soaringcloud.kit.box.LogKit;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by renyuxiang on 2015/12/7.
 */
public class VideoService {
//    public static final String SERVER_API = "http://114.215.89.173:8888/videosAPI/v1.0/";
//    测试
//    public static final String SERVER_API = "http://121.42.155.39:6666/videosAPI/v1.0/";
//    正式
    public static final String SERVER_API = "http://114.215.89.173:9999/videosAPI/v1.0/";
    private HttpManager httpManager;
    private VideoAPI videoAPI;

    public VideoService(IUserAgent userAgent) {
        httpManager = new HttpManager(SERVER_API, userAgent);
        videoAPI = httpManager.createAPI(VideoAPI.class);
        init();
    }

    public void init() {
        videoAPI = httpManager.createAPI(VideoAPI.class);
    }

    public void getVideoList(GetVideoListParam param, final OnVideoListReceivedListener listener) {
        Call<MetaDataResponse<List<VideoJo>>> task = videoAPI.getVideoList(param.getOptionSearchKeyWord(), param.getVideoClassifyId(),param.getOrderBy(), param.getPageSize(),
                param.getPageIndex());
        task.enqueue(new Callback<MetaDataResponse<List<VideoJo>>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<List<VideoJo>>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getVideoList");
                if (listener != null) {
                    listener.onReceived(response.body().getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onReceived(null);
                }
            }
        });
    }

    public void getVideoDetail(GetVideoDetailParam param, final OnVideoDetailReceivedListener listener) {
        videoAPI.getVideoDetail(param.getVideoId()).enqueue(new Callback<MetaDataResponse<VideoJo>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<VideoJo>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getVideoDetail");
                if (listener != null) {
                    listener.onReceived(response.body().getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onReceived(null);
                }
            }
        });
    }

    public void getVideoPlayRecord(GetVideoRecordParam param, final OnGetVideoRecordListener listener) {
        videoAPI.getVideoRecordList(param.getVideoId()).enqueue(new Callback<MetaDataResponse<VideoRecordJo>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<VideoRecordJo>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getVideoPlayRecord");
                if (listener != null) {
                    listener.onReceived(response.body().getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onReceived(null);
                }
            }
        });
    }

    public void postVideoPlayRecord(PostVideoRecordParam param, final OnPostCompleteListener listener) {
        videoAPI.postVideoRecord(param).enqueue(new Callback<MetaDataResponse<ResultJo>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<ResultJo>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse postVideoPlayRecord");
                if (listener != null) {
                    listener.onCompleted(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onCompleted(false);
                }
            }
        });
    }

    public void getVideoMenuList(GetMenuListParam param, final OnMenuListReceivedListener listener) {
        videoAPI.getMenuList(param.getVideoMenuId()).enqueue(new Callback<MetaDataResponse<List<VideoMenuItemJo>>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<List<VideoMenuItemJo>>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getVideoMenuList");
                if (listener != null) {
                    listener.onReceived(response.body().getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onReceived(null);
                }
            }
        });
    }
    public void getRecommendVideoList(GetRecommendVideoListParam param, final OnVideoListReceivedListener listener){
        Call<MetaDataResponse<List<VideoJo>>> task = videoAPI.getRecommendVideoList(param.getVideoId(), param.getPageSize(), param.getPageIndex());
        task.enqueue(new Callback<MetaDataResponse<List<VideoJo>>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<List<VideoJo>>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getRecommendVideoList");
                if (listener != null) {
                    listener.onReceived(response.body().getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onReceived(null);
                }
            }
        });

    }

    public void getVideoPlayRecordCount(GetVideoPlayRecordCountParam param, final OnVideoPlayRecordCountListener listener){
        videoAPI.getVideoPlayRecordCount(param.getVideoId()).enqueue(new Callback<MetaDataResponse<VideoRecordCountJo>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<VideoRecordCountJo>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getVideoPlayRecordCount");
                if (listener != null) {
                    listener.onReceived(response.body().getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener != null) {
                    listener.onReceived(null);
                }
            }
        });
    }

    public void getVideoAppPlayCount(GetVideoPlayRecordCountParam param, final OnPostCompleteListener listener){
        videoAPI.postVideoAppPlayCount(param).enqueue(new Callback<MetaDataResponse<ResultJo>>() {
            @Override
            public void onResponse(Response<MetaDataResponse<ResultJo>> response, Retrofit retrofit) {
                LogKit.e(this,"onResponse getVideoAppPlayCount");
                if (listener!=null){
                    listener.onCompleted(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (listener!=null){
                    listener.onCompleted(false);
                }
            }
        });
    }

    public void getRelativeVideoList(GetRecommendVideoListParam param, OnVideoListReceivedListener listener) {
        videoAPI.getRecommendVideoList(param.getVideoId(), param.getPageSize(), param.getPageIndex());
    }
}
