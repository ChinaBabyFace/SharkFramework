/**
 * FileTools.java 2015-1-5
 * <p/>
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 */
package com.soaringcloud.kit.box;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * <b>AndroidKit。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-13 下午1:53:01</td><td>建立类型</td></tr>
 * <p>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public final class AndroidKit {

    /**
     * <b>getSign。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 获取APK签名。
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static String getApkSignature(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 64);
        } catch (NameNotFoundException localNameNotFoundException) {
            return null;
        }

        for (int j = 0; j < packageInfo.signatures.length; j++) {
            byte[] str = packageInfo.signatures[j].toByteArray();
            if (str != null) {
                return MD5Kit.hexdigest(str);
            }
        }
        return null;
    }

    /**
     * <b>isAvilible。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 判断手机上是否安装了某应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    pName.add(pn);
                }
            }
            boolean b = pName.contains(packageName);
            return b;// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * <b>isTabletDevice。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 是否是平板设备
     * Test screen size, use reflection because isLayoutSizeAtLeast is only available since 11。
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean isTabletDevice(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
            Configuration con = context.getResources().getConfiguration();
            try {
                Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con, 0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
                System.err.println("pad");
                return r;
            } catch (Exception x) {
                x.printStackTrace();
                System.err.println("not pad");
                return false;
            }
        }
        System.err.println("not pad");
        return false;
    }

    /**
     * <b>getCurrencySymbol。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 获得当前国家的货币符号。
     *
     * @param context
     * @param currencyCode 国家code
     * @return
     */
    public static String getCurrencySymbol(Context context, String currencyCode) {
        String currencySymbol = null;
        currencyCode = currencyCode.toUpperCase(Locale.getDefault());
        Currency currency = Currency.getInstance(currencyCode);
        Locale locale = context.getResources().getConfiguration().locale;
        try {
            currencySymbol = currency.getSymbol(locale);
        } catch (IllegalArgumentException e) {
            currencySymbol = "";
        }
        return currencySymbol;
    }

    /**
     * <b>isChineseLocale。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 判断当前手机是否是中文环境。
     *
     * @param context
     * @return
     */
    public static boolean isChineseLocale(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            if ((Locale.CHINA.equals(locale)) || (Locale.CHINESE.equals(locale)) || (Locale.SIMPLIFIED_CHINESE.equals(locale))
                    || (Locale.TAIWAN.equals(locale))) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * <b>getVersionCode。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 获取APP的VersionCode。
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
        // info.versionCode;
        // info.versionName;
        // info.packageName;
        // info.signatures;
    }

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * <b>getMetaDataValue。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 根据KeyName 获取存放在Mainfest中的键值。
     *
     * @param context
     * @param name
     * @return
     */
    public static String getMetaDataValue(Context context, String name) {
        String value = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            android.content.pm.ApplicationInfo applicationInfo;
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name).toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read the META_DATA in the Manifest file.", e);
        }
        return value;
    }

    /**
     * <b>getClientId。</b>
     * <p><b>详细说明：</b></p>
     * <!-- 在此添加详细说明 -->
     * 获取android设备的设备码或唯一标示。
     *
     * @param context
     * @return
     */
    public static String getClientId(Context context) {
        String clientId = "";
        String androidId = null;
        String deviceId = null;
        String macAddress = null;
        UUID deviceUUID = null;
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (null != telephony) {
            deviceId = telephony.getDeviceId();
        }
        if (null != wifi) {
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            if (null != wifiInfo) {
                macAddress = wifiInfo.getMacAddress();
            }
        }
        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (null != androidId && null != deviceId && null != macAddress) {
            deviceUUID = new UUID(androidId.hashCode(), ((long) deviceId.hashCode() << 32) | macAddress.hashCode());
        }
        if (null != deviceUUID) {
            clientId = deviceUUID.toString();
        }
        return clientId;
    }

    public static int getAndroidVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getDeviceId(Context context) {
        String deviceId = "";
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != telephony) {
            deviceId = telephony.getDeviceId();
        }
        return deviceId;
    }

    public static String getMacAddress(Context context) {
        String macAddress = "";
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (null != wifi) {
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            if (null != wifiInfo) {
                macAddress = wifiInfo.getMacAddress();
            }
        }
        return macAddress;
    }
}
