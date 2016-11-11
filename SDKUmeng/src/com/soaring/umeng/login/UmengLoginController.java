/**
 * UmengLoginController.java 2015-4-16
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package com.soaring.umeng.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.soaring.umeng.Platform;
import com.soaringcloud.kit.box.AndroidKit;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;

/**
 * <b>UmengLoginController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-16 下午8:41:12</td><td>建立类型</td></tr>
 * <p>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class UmengLoginController {

    private Context context;
    private UMSocialService mController;
    private UMAuthListener umAuthListener;
    private UserPlatformInformationListener userPlatformInformationListener;
    private SocializeClientListener socializeClientListener;

    public UmengLoginController(Context context) {
        this.context = context;
        this.init();
    }

    // "1104536398", "qDJHnrWfZcVugBvS"
    private void init() {
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        mController.getConfig().closeToast();
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, AndroidKit.getMetaDataValue(context, Platform.QQ_SOCIAL_APP_ID),
                AndroidKit.getMetaDataValue(context, Platform.QQ_SOCIAL_APP_KEY));
        qqSsoHandler.addToSocialSDK();
        UMWXHandler wxHandler = new UMWXHandler(context, AndroidKit.getMetaDataValue(context, Platform.WECHAT_SOCIAL_APP_ID),
                AndroidKit.getMetaDataValue(context, Platform.WECHAT_SOCIAL_APP_SECRET));
        wxHandler.addToSocialSDK();
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
    }

    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void doOauthVerify(SHARE_MEDIA platform) {
        mController.doOauthVerify(context, platform, getUmAuthListener());
    }

    public void undoOauth(SHARE_MEDIA platform) {
        mController.deleteOauth(context, platform, getSocializeClientListener());
    }

    public void getPlatformInfo(final SHARE_MEDIA platform) {
        mController.getPlatformInfo(context, platform, new UMDataListener() {

            @Override
            public void onStart() {
                if (getUserPlatformInformationListener() != null) {
                    getUserPlatformInformationListener().onStart();
                }
            }

            @Override
            public void onComplete(int status, Map<String, Object> info) {
                if (getUserPlatformInformationListener() != null) {
                    getUserPlatformInformationListener().onComplete(platform, status, info);
                }
            }
        });
    }

    public UMAuthListener getUmAuthListener() {
        return umAuthListener;
    }

    public void setUmAuthListener(UMAuthListener umAuthListener) {
        this.umAuthListener = umAuthListener;
    }

    public SocializeClientListener getSocializeClientListener() {
        return socializeClientListener;
    }

    public void setSocializeClientListener(SocializeClientListener socializeClientListener) {
        this.socializeClientListener = socializeClientListener;
    }

    public UserPlatformInformationListener getUserPlatformInformationListener() {
        return userPlatformInformationListener;
    }

    public void setUserPlatformInformationListener(UserPlatformInformationListener userPlatformInformationListener) {
        this.userPlatformInformationListener = userPlatformInformationListener;
    }

    public interface UserPlatformInformationListener {

        void onStart();

        void onComplete(SHARE_MEDIA platform, int status, Map<String, Object> info);

    }
}
