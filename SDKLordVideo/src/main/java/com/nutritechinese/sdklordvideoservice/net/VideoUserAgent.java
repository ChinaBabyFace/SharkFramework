package com.nutritechinese.sdklordvideoservice.net;

import com.nutritechinese.sdklordvideoservice.LordSettings;
import com.soaring.nuitrio.http.header.IUserAgent;

public class VideoUserAgent implements IUserAgent {
    private String appId;
    private String version;
    private String resolution;
    private String userId;
    private String playSource;

    @Override
    public String generateUA() {
        StringBuffer ua = new StringBuffer("");
        ua.append(getAppId());
        ua.append(LordSettings.HEADER_SEPARATOR);
        ua.append(getVersion());
        ua.append(LordSettings.HEADER_SEPARATOR);
        ua.append(getResolution());
        ua.append(LordSettings.HEADER_SEPARATOR);
        ua.append(getUserId());
        ua.append(LordSettings.HEADER_SEPARATOR);
        ua.append(getPlaySource());
        ua.append(LordSettings.HEADER_SEPARATOR);
        return ua.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPlaySource() {
        return playSource;
    }

    public void setPlaySource(String playSource) {
        this.playSource = playSource;
    }
}
