/**
 * FrameworkSettings.java 2015-1-5
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * <b>NetTools。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-5 下午8:04:59</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
@SuppressWarnings("deprecation")
public class NetKit {
	/**
	 * <b>hasInternetPermission。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 是否拥有联网权限。
	 * @param context
	 * @return
	 */
	public static boolean hasInternetPermission(Context context) {
		if (context != null) {
			return context.checkCallingOrSelfPermission("android.permission.INTERNET") == PackageManager.PERMISSION_GRANTED;
		}
		return true;
	}
    /*
    *检查url的是否有效 */
    public static boolean checkURL(String url){
        boolean value=false;
        try {
            HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
            int code=conn.getResponseCode();
            System.out.println(">>>>>>>>>>>>>>>> "+code+" <<<<<<<<<<<<<<<<<<");
			value = code == 200;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return value;
    }
	/**
	 * <b>isConnect。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 是否联网。
	 * @param context
	 * @return
	 */
	public static boolean isConnect(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * <b>getConnectivityManager。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取ConnectivityManager。
	 * @param context
	 * @return
	 */
	public static ConnectivityManager getConnectivityManager(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectivity;
	}

	/**
	 * <b>getActiveNetworkInfo。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 * @return
	 */
	public static NetworkInfo getActiveNetworkInfo(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			return connectivity.getActiveNetworkInfo();
		} else {
			return null;
		}
	}

	/**
	 * <b>isMobileNetwork。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 是否连接的是移动通信网。
	 * @param context
	 * @return
	 */
	public static boolean isMobileNetwork(Context context) {
		if (context != null) {
			NetworkInfo info = getActiveNetworkInfo(context);
			if (info == null) {
				return false;
			}
			return (info != null) && (info.getType() == 0) && (info.isConnected());
		}
		return false;
	}

	/**
	 * <b>isWifi。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 是否处于WIFI网络
	 * @param 
	 * @return
	 */
	public static boolean isWifi(Context context) {
		NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
		return (activeNetInfo != null) && (activeNetInfo.getType() == 1);
	}

//	/**
//	 * <b>isNetworkAvailable。</b>
//	 * <p><b>详细说明：</b></p>
//	 * <!-- 在此添加详细说明 -->
//	 * 无。
//	 * @param Context
//	 * @return
//	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = getConnectivityManager(context);
		if (connectivity == null) {
			return false;
		}
		NetworkInfo[] info = connectivity.getAllNetworkInfo();
		if (info != null) {
			for (NetworkInfo name : info) {
				if (NetworkInfo.State.CONNECTED == name.getState()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <b>isUsingProxy。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 是否使用了代理。
	 * @return
	 */
	public static boolean isUsingProxy() {
		String host = android.net.Proxy.getDefaultHost();
		return !(host == null || host.trim().equals(""));
	}

	/**
	 * <b>getNetworkType。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取网络类型。
	 * @param context
	 * @return
	 */
	public static int getNetworkType(Context context) {
		if (context != null) {
			NetworkInfo info = getActiveNetworkInfo(context);

			return info == null ? -1 : info.getType();
		}
		return -1;
	}

	/**
	 * <b>getWifiState。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取wifi状态。
	 * @param context
	 * @return
	 */
	public static int getWifiState(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifi == null) {
			return 4;
		}
		return wifi.getWifiState();
	}

	/**
	 * <b>getWifiConnectivityState。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获取wifi连接状态。
	 * @param context
	 * @return
	 */
	public static NetworkInfo.DetailedState getWifiConnectivityState(Context context) {
		NetworkInfo networkInfo = getActiveNetworkInfo(context);
		return networkInfo == null ? NetworkInfo.DetailedState.FAILED : networkInfo.getDetailedState();
	}
	
	/**
	 * <b>wifiConnection。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用密码连接WIFI。
	 * @param context
	 * @param wifiSSID
	 * @param password
	 * @return
	 */
	public static boolean connectWifiWithPassWord(Context context, String wifiSSID, String password) {
		boolean isConnection = false;
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		String strQuotationSSID = "\"" + wifiSSID + "\"";

		WifiInfo wifiInfo = wifi.getConnectionInfo();
		if ((wifiInfo != null) && ((wifiSSID.equals(wifiInfo.getSSID())) || (strQuotationSSID.equals(wifiInfo.getSSID())))) {
			isConnection = true;
		} else {
			List<ScanResult> scanResults = wifi.getScanResults();
			if ((scanResults != null) && (scanResults.size() != 0)) {
				for (int nAllIndex = scanResults.size() - 1; nAllIndex >= 0; nAllIndex--) {
					String strScanSSID = scanResults.get(nAllIndex).SSID;
					if ((wifiSSID.equals(strScanSSID)) || (strQuotationSSID.equals(strScanSSID))) {
						WifiConfiguration config = new WifiConfiguration();
						config.SSID = strQuotationSSID;
						config.preSharedKey = ("\"" + password + "\"");
						config.status = 2;

						int nAddWifiId = wifi.addNetwork(config);
						isConnection = wifi.enableNetwork(nAddWifiId, false);
						break;
					}
				}
			}
		}
		return isConnection;
	}

	/**
	 * <b>clearCookies。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 清空Cookies。
	 * @param context
	 */
	public static void clearCookies(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieSyncManager.getInstance().sync();
	}

	/**
	 * <b>generateUA。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 生成UA。
	 * @param ctx
	 * @return
	 */
	public static String generateUA(Context ctx) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Android");
		buffer.append("__");
		buffer.append("weibo");
		buffer.append("__");
		buffer.append("sdk");
		buffer.append("__");
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = null;
			pi = pm.getPackageInfo(ctx.getPackageName(), 16);
			String versionCode = pi.versionName;
			buffer.append(versionCode.replaceAll("\\s+", "_"));
		} catch (Exception localE) {
			buffer.append("unknown");
		}
		return buffer.toString();
	}
}
