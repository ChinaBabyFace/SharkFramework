/**
 * MobSmsController.java 2015-4-17
 * 龙德恒方科技发展有限公司(c) 2015 - 2015 。
 */
package cn.smssdk.gui;

import android.content.Context;

import com.soaringcloud.kit.box.AndroidKit;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * <b>MobSmsController。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-4-17 下午4:10:11</td><td>建立类型</td></tr>
 * <p>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public class MobSmsController {

    public static final String MOB_SMS_APPKEY = "MOB_SMS_APPKEY";
    public static final String MOB_SMS_APPSECRET = "MOB_SMS_APPSECRET";
    private Context context;
    /**
     * 国家代码，默认中国86。
     */
    private String countryCode = "86";

    public MobSmsController(Context context) {
        this.context = context;
        this.init();
    }

    private void init() {
        SMSSDK.initSDK(context, AndroidKit.getMetaDataValue(context, MOB_SMS_APPKEY), AndroidKit.getMetaDataValue(context, MOB_SMS_APPSECRET));
    }

    public void getVerificationCode(String mobilPhone) {
        SMSSDK.getVerificationCode(countryCode, mobilPhone);
    }

    public void setEventHandler(EventHandler eventHandler) {
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void destory() {
        SMSSDK.unregisterAllEventHandler();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
