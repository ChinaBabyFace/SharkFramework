package com.nutritechinese.sdklordvideoservice.api.callback;

import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoRecordJo;

/**
 * Created by dongbinbin on 2015/11/25.
 */
public interface OnGetVideoRecordListener {
    void onReceived(VideoRecordJo vo);
}
