/*
 * Copyright (c) 2016.  任宇翔创建
 */

package com.soaring.umeng.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.soaring.umeng.Platform;
import com.soaring.umeng.R;
import com.soaringcloud.kit.box.AndroidKit;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * <b>ShareHelper銆�</b>
 * <p><b>璇︾粏璇存槑锛�</b></p>
 * <!-- 鍦ㄦ娣诲姞璇︾粏璇存槑 -->
 * 鐢ㄤ簬杈呭姪鏀寔鍙嬬洘鍒嗕韩锛岀洰鍓嶈绫诲浐瀹氭敮鎸佸井淇°�佸井淇℃湅鍙嬪湀锛孮Q绌洪棿锛屾柊娴井鍗氥��
 * <p><b>淇敼鍒楄〃锛�</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>搴忓彿</td><td>浣滆��</td><td>淇敼鏃ユ湡</td><td>淇敼鍐呭</td></tr>
 * <!-- 鍦ㄦ娣诲姞淇敼鍒楄〃锛屽弬鑰冪涓�琛屽唴瀹� -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-10 涓嬪崍9:06:02</td><td>寤虹珛绫诲瀷</td></tr>
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-13 涓婂崍10:33:02</td><td>鎾板啓娉ㄩ噴</td></tr>
 * <p>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class ShareHelper {

    private UMSocialService umShareService;
    private Context context;

    private SoaringShareContent shareContent;
    private OnShareClickListener onShareClickListener;

    public ShareHelper(Context mContext) {
        this.context = mContext;
        umShareService = UMServiceFactory.getUMSocialService("com.umeng.share");
        umShareService.getConfig().closeToast();
    }

    public void emailShare() {
        if (shareContent != null) {
            Intent email = new Intent(android.content.Intent.ACTION_SEND);
            email.setType("plain/text");
            EmailHandler emailHandler = new EmailHandler();
            emailHandler.addToSocialSDK();
            UMImage urlImage = null;
            if (shareContent.getImage() != null) {
                urlImage = new UMImage(context, shareContent.getImage());
            } else if (shareContent.getImagePath() != null && !shareContent.getImagePath().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImagePath());
            } else if (shareContent.getImageUrl() != null && !shareContent.getImageUrl().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImageUrl());
            }
            MailShareContent mailContent = new MailShareContent(urlImage);
            mailContent.setTitle(shareContent.getTitle());
            mailContent.setShareContent(shareContent.getContent());
            umShareService.setShareMedia(mailContent);
            umShareService.postShare(context, SHARE_MEDIA.EMAIL, null);
            shareContent = null;
        }
    }

    public void sinaShare() {
        if (shareContent != null) {
            umShareService.setShareContent(shareContent.getContent());
            UMImage urlImage = null;
            if (shareContent.getImage() != null) {
                urlImage = new UMImage(context, shareContent.getImage());
            } else if (shareContent.getImagePath() != null && !shareContent.getImagePath().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImagePath());
            } else if (shareContent.getImageUrl() != null && !shareContent.getImageUrl().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImageUrl());
            }
            umShareService.setShareImage(urlImage);
            umShareService.postShare(context, SHARE_MEDIA.SINA, null);
            shareContent = null;
        }
    }

    public void weixin() {
        if (shareContent != null) {
            UMWXHandler wxHandler = new UMWXHandler(context, AndroidKit.getMetaDataValue(context, Platform.WECHAT_SOCIAL_APP_ID),
                    AndroidKit.getMetaDataValue(context, Platform.WECHAT_SOCIAL_APP_SECRET));
            wxHandler.addToSocialSDK();
            WeiXinShareContent weixinContent = new WeiXinShareContent();
            weixinContent.setShareContent(shareContent.getContent());
            weixinContent.setTitle(shareContent.getTitle());
            weixinContent.setTargetUrl(shareContent.getContentUrl());
            UMImage urlImage = null;
            if (shareContent.getImage() != null) {
                urlImage = new UMImage(context, shareContent.getImage());
            } else if (shareContent.getImagePath() != null && !shareContent.getImagePath().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImagePath());
            } else if (shareContent.getImageUrl() != null && !shareContent.getImageUrl().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImageUrl());
            }
            weixinContent.setShareImage(urlImage);
            umShareService.setShareMedia(weixinContent);
            umShareService.postShare(context, SHARE_MEDIA.WEIXIN, null);
            shareContent = null;
        }
    }

    public void weixinFriends() {
        if (shareContent != null) {
            UMWXHandler wxCircleHandler = new UMWXHandler(context, AndroidKit.getMetaDataValue(context, Platform.WECHAT_SOCIAL_APP_ID),
                    AndroidKit.getMetaDataValue(context, Platform.WECHAT_SOCIAL_APP_SECRET));
            wxCircleHandler.setToCircle(true);
            wxCircleHandler.addToSocialSDK();

            CircleShareContent circleMedia = new CircleShareContent();
            circleMedia.setShareContent(shareContent.getContent());
            circleMedia.setTargetUrl(shareContent.getContentUrl());
            circleMedia.setTitle(shareContent.getTitle());
            UMImage urlImage = null;
            if (shareContent.getImage() != null) {
                urlImage = new UMImage(context, shareContent.getImage());
            } else if (shareContent.getImagePath() != null && !shareContent.getImagePath().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImagePath());
            } else if (shareContent.getImageUrl() != null && !shareContent.getImageUrl().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImageUrl());
            }
            circleMedia.setShareImage(urlImage);
            umShareService.setShareMedia(circleMedia);
            umShareService.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE, null);
            shareContent = null;
        }
    }

    public void qqZoneShare() {
        if (shareContent != null) {

            QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context,
                    AndroidKit.getMetaDataValue(context, Platform.QQ_SOCIAL_APP_ID), AndroidKit.getMetaDataValue(context, Platform.QQ_SOCIAL_APP_KEY));
            qZoneSsoHandler.addToSocialSDK();
            QZoneShareContent qzone = new QZoneShareContent();
            qzone.setShareContent(shareContent.getContent());
            qzone.setTargetUrl(shareContent.getContentUrl());
            qzone.setTitle(shareContent.getTitle());
            UMImage urlImage = null;
            if (shareContent.getImage() != null) {
                urlImage = new UMImage(context, shareContent.getImage());
            } else if (shareContent.getImagePath() != null && !shareContent.getImagePath().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImagePath());
            } else if (shareContent.getImageUrl() != null && !shareContent.getImageUrl().trim().equals("")) {
                urlImage = new UMImage(context, shareContent.getImageUrl());
            }
            qzone.setShareImage(urlImage);
            umShareService.setShareMedia(qzone);
            umShareService.postShare(context, SHARE_MEDIA.QZONE, null);
            shareContent = null;
        }
    }

    public void qqShare() {
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, AndroidKit.getMetaDataValue(context, Platform.QQ_SOCIAL_APP_ID),
                AndroidKit.getMetaDataValue(context, Platform.QQ_SOCIAL_APP_KEY));
        qqSsoHandler.addToSocialSDK();
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(shareContent.getContent());
        qqShareContent.setTargetUrl(shareContent.getContentUrl());
        qqShareContent.setTitle(shareContent.getTitle());
        UMImage urlImage = null;
        if (shareContent.getImage() != null) {
            urlImage = new UMImage(context, shareContent.getImage());
        } else if (shareContent.getImagePath() != null && !shareContent.getImagePath().trim().equals("")) {
            urlImage = new UMImage(context, shareContent.getImagePath());
        } else if (shareContent.getImageUrl() != null && !shareContent.getImageUrl().trim().equals("")) {
            urlImage = new UMImage(context, shareContent.getImageUrl());
        }
        qqShareContent.setShareImage(urlImage);
        umShareService.setShareMedia(qqShareContent);
        umShareService.postShare(context, SHARE_MEDIA.QQ, null);
        shareContent = null;
    }

    public void showShareWindow(ViewGroup parent) {
        View priceLayout = View.inflate(context, R.layout.activity_share_layout, null);
        Button sinaButton = (Button) priceLayout.findViewById(R.id.sina);
        Button weixinButton = (Button) priceLayout.findViewById(R.id.wechat);
        Button weixinFriendsButton = (Button) priceLayout.findViewById(R.id.wechat_circle);
        Button qqZoneButton = (Button) priceLayout.findViewById(R.id.qq_zone);

        final PopupWindow popupWindow = new PopupWindow(priceLayout, -1, -2, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        sinaButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onShareClickListener != null) {
                    onShareClickListener.onClick(SHARE_MEDIA.SINA);
                }
                sinaShare();
                popupWindow.dismiss();
            }
        });
        weixinButton.setOnClickListener(new OnClickListener() {

            /**
             * <b>onClick銆�</b>
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            @Override
            public void onClick(View v) {
                if (onShareClickListener != null) {
                    onShareClickListener.onClick(SHARE_MEDIA.WEIXIN);
                }
                weixin();
                popupWindow.dismiss();
            }
        });
        weixinFriendsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onShareClickListener != null) {
                    onShareClickListener.onClick(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                weixinFriends();
                popupWindow.dismiss();
            }
        });
        qqZoneButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onShareClickListener != null) {
                    onShareClickListener.onClick(SHARE_MEDIA.QZONE);
                }
                qqZoneShare();
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.umeng_socialize_popup_dialog_anim);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    public void openShareWindow() {
        umShareService.openShare((Activity) context, false);
    }

    /**
     * @return the onShareClickListener
     */
    public OnShareClickListener getOnShareClickListener() {
        return onShareClickListener;
    }

    /**
     * @param onShareClickListener the onShareClickListener to set
     */
    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    /**
     * @return the shareContent
     */
    public SoaringShareContent getShareContent() {
        return shareContent;
    }

    /**
     * @param shareContent the shareContent to set
     */
    public void setShareContent(SoaringShareContent shareContent) {
        this.shareContent = shareContent;
    }
}
