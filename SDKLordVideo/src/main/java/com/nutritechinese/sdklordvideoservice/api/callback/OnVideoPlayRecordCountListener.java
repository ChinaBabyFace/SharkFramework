package com.nutritechinese.sdklordvideoservice.api.callback;

import com.nutritechinese.sdklordvideoservice.api.model.pojo.VideoRecordCountJo;

/**
 * Created by lipeng on 2015/12/17.
 */
public interface OnVideoPlayRecordCountListener {

    void  onReceived(VideoRecordCountJo countJo);
}
