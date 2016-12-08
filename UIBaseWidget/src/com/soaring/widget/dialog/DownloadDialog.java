/**
 * SoaringWidgetSettings.java 2015-1-7
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaring.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

/**
 * <b>DownloadDialog。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 这是个用于调起第三方应用去实现下载的复合型对话框，例如调起浏览器下载apk。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-13 上午11:21:52</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class DownloadDialog {
	private Context context;
	private String dialogTitle;
	private String dialogMessage;
	private String sureLabel;
	private String cancelLabel;
	private String apkPath;

	/**
	 * <b>构造方法。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param context
	 */
	public DownloadDialog(Context context) {
		this.context = context;
	}

	/**
	 * <b>show。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 显示对话框。
	 */
	public void show() {
		createDownloadConfirmDialog().show();
	}

	/**
	 * <b>createDownloadConfirmDialog。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 创建下载对话框。
	 * @return
	 */
	public  Dialog createDownloadConfirmDialog() {
		Dialog dialog = new AlertDialog.Builder(getContext()).setMessage(getDialogMessage()).setTitle(getDialogTitle()).setPositiveButton(getSureLabel(), new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				downloadApk(getContext(), getApkPath());
			}
		}).setNegativeButton(getCancelLabel(), new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create();
		return dialog;
	}

	/**
	 * <b>downloadApk。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 直接调用浏览器进行下载。
	 * @param context
	 * @param downloadPath
	 */
	public void downloadApk(Context context, String downloadPath) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setFlags(268435456);
		Uri url = Uri.parse(downloadPath);
		intent.setData(url);
		try {
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the dialogTitle
	 */
	public String getDialogTitle() {
		return dialogTitle;
	}

	/**
	 * @param dialogTitle the dialogTitle to set
	 */
	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	/**
	 * @return the dialogMessage
	 */
	public String getDialogMessage() {
		return dialogMessage;
	}

	/**
	 * @param dialogMessage the dialogMessage to set
	 */
	public void setDialogMessage(String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}

	/**
	 * @return the sureLabel
	 */
	public String getSureLabel() {
		return sureLabel;
	}

	/**
	 * @param sureLabel the sureLabel to set
	 */
	public void setSureLabel(String sureLabel) {
		this.sureLabel = sureLabel;
	}

	/**
	 * @return the cancelLabel
	 */
	public String getCancelLabel() {
		return cancelLabel;
	}

	/**
	 * @param cancelLabel the cancelLabel to set
	 */
	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
	}

	/**
	 * @return the apkPath
	 */
	public String getApkPath() {
		return apkPath;
	}

	/**
	 * @param apkPath the apkPath to set
	 */
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
}
