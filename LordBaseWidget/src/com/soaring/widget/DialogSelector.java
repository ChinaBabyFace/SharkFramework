package com.soaring.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.TextView;


public class DialogSelector {

	/**
	 * 
	 * <b>showSelectDlg。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 单选对话框
	 * @param context
	 * @param sTitle
	 * @param choices
	 * @param tv
	 */
	public static void showSelectDlg(Context context, String sTitle,
			final String[] choices, final TextView tv) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (sTitle != null) {
			builder.setTitle(sTitle);
		}
		builder.setSingleChoiceItems(choices, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (tv != null)
							tv.setText(choices[which]);
					}
				});
		builder.create().show();
	}

	/**
	 * 
	 * <b>showSelectDlg。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 单选对话框
	 * @param context
	 * @param sTitle
	 * @param choices
	 * @param onItemSelectListener
	 */
	public static void showSelectDlg(Context context, String sTitle,
			final String[] choices,
			final OnItemSelectListener onItemSelectListener) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (!TextUtils.isEmpty(sTitle)) {
			builder.setTitle(sTitle);
		}
		builder.setSingleChoiceItems(choices, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (onItemSelectListener != null) {
							onItemSelectListener.onItemSelected(which);
						}
					}
				});
		builder.create().show();
	}

	public interface OnItemSelectListener {
		void onItemSelected(int position);
	}
}

