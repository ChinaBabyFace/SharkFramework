package com.nutritechinese.sdklordvideoservice.api.callback;

import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoJo;

import java.util.List;

public interface OnVideoListReceivedListener {
    void onReceived(List<VideoJo> list);
}
