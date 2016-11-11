package com.nutritechinese.sdklordvideoservice.api.callback;

import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoMenuItemJo;

import java.util.List;

/**
 * Created by renyuxiang on 2015/12/9.
 */
public interface OnMenuListReceivedListener {
    void onReceived(List<VideoMenuItemJo> list);
}
