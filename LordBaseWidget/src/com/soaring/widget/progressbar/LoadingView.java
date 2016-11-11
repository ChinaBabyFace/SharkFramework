package com.soaring.widget.progressbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.soaring.widget.R;
import com.soaringcloud.kit.box.JavaKit;

/**
 * <b>LoadingView。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-3-26 下午3:03:20</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class LoadingView
		extends Dialog {

	private Context mContext;
	private static LoadingView instance;
	private View root;
	private TextView messageTextView;
	private ImageView ivSuccess;
	private ImageView ivFailure;
	private ImageView ivProgressSpinner;
	private AnimationDrawable adProgressSpinner;
	private String successMessage;
	private String failMessage;
	private OnDialogDismiss onDialogDismiss;

	public static LoadingView getInstance(Context context) {
		if (instance == null || instance.mContext != context) {
			instance = new LoadingView(context);
		}
		return instance;
	}

	public LoadingView(Context context) {
		super(context, 0);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		this.setCanceledOnTouchOutside(false);
		this.mContext = context;
		root = View.inflate(context, R.layout.loading_view_layout, null);
		messageTextView = (TextView) root.findViewById(R.id.textview_message);
		ivSuccess = (ImageView) root.findViewById(R.id.imageview_success);
		ivFailure = (ImageView) root.findViewById(R.id.imageview_failure);
		ivProgressSpinner = (ImageView) root.findViewById(R.id.imageview_progress_spinner);
		this.setContentView(root);
	}

	public void setAnimRes(int animRes) {

		ivProgressSpinner.setImageResource(animRes);

		adProgressSpinner = (AnimationDrawable) ivProgressSpinner.getDrawable();

	}

	public void setMessage(String message) {
		messageTextView.setText(message);
	}

	@Override
	public void show() {
		if (!((Activity) mContext).isFinishing()) {
			super.show();
		} else {
			instance = null;
		}
	}

	public void dismissWithSuccess() {
		messageTextView.setText(JavaKit.isStringEmpty(getSuccessMessage()) ? "" : getSuccessMessage());
		showSuccessImage();

		if (onDialogDismiss != null) {
			this.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					onDialogDismiss.onDismiss();
				}
			});
		}
		dismissWithDelay();
	}

	public void dismissWithSuccess(long waiteMillis) {
		messageTextView.setText(JavaKit.isStringEmpty(getSuccessMessage()) ? "" : getSuccessMessage());
		showSuccessImage();

		if (onDialogDismiss != null) {
			this.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					onDialogDismiss.onDismiss();
				}
			});
		}
		dismissWithDelay(waiteMillis);
	}

	public void dismissWithSuccess(String message) {
		showSuccessImage();
		messageTextView.setText(JavaKit.isStringEmpty(message) ? "" : message);
		if (onDialogDismiss != null) {
			this.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					onDialogDismiss.onDismiss();
				}
			});
		}
		dismissWithDelay();
	}

	public void dismissWithFailure() {
		showFailureImage();
		messageTextView.setText(JavaKit.isStringEmpty(getFailMessage()) ? "" : getFailMessage());
		if (onDialogDismiss != null) {
			this.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					onDialogDismiss.onDismiss();
				}
			});
		}
		dismissWithDelay();
	}

	public void dismissWithFailure(long waiteMillis) {
		showFailureImage();
		messageTextView.setText(JavaKit.isStringEmpty(getFailMessage()) ? "" : getFailMessage());
		if (onDialogDismiss != null) {
			this.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					onDialogDismiss.onDismiss();
				}
			});
		}
		dismissWithDelay(waiteMillis);
	}

	public void dismissWithFailure(String message) {
		showFailureImage();
		messageTextView.setText(JavaKit.isStringEmpty(message) ? "" : message);

		if (onDialogDismiss != null) {
			this.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					onDialogDismiss.onDismiss();
				}
			});
		}
		dismissWithDelay();
	}

	protected void showSuccessImage() {
		ivProgressSpinner.setVisibility(View.GONE);
		ivSuccess.setVisibility(View.VISIBLE);
	}

	protected void showFailureImage() {
		ivProgressSpinner.setVisibility(View.GONE);
		ivFailure.setVisibility(View.VISIBLE);
	}

	protected void reset() {
		ivProgressSpinner.setVisibility(View.VISIBLE);
		ivFailure.setVisibility(View.GONE);
		ivSuccess.setVisibility(View.GONE);
		messageTextView.setText("");
	}

	protected void dismissWithDelay(final long waiteMillis) {
		AsyncTask<String, Integer, Long> task = new AsyncTask<String, Integer, Long>() {

			@Override
			protected Long doInBackground(String... params) {
				SystemClock.sleep(waiteMillis);
				return null;
			}

			@Override
			protected void onPostExecute(Long result) {
				super.onPostExecute(result);
				dismiss();
				reset();
			}
		};
		task.execute();
	}

	protected void dismissWithDelay() {
		AsyncTask<String, Integer, Long> task = new AsyncTask<String, Integer, Long>() {

			@Override
			protected Long doInBackground(String... params) {
				SystemClock.sleep(500);
				return null;
			}

			@Override
			protected void onPostExecute(Long result) {
				super.onPostExecute(result);
				if (!((Activity) mContext).isFinishing()) {
					dismiss();
				} else {
					instance = null;
				}

				reset();
			}
		};
		task.execute();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		ivProgressSpinner.post(new Runnable() {

			@Override
			public void run() {
				adProgressSpinner.start();

			}
		});
	}

	public OnDialogDismiss getOnDialogDismiss() {
		return onDialogDismiss;
	}

	public void setOnDialogDismiss(OnDialogDismiss onDialogDismiss) {
		this.onDialogDismiss = onDialogDismiss;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}

	public interface OnDialogDismiss {

		void onDismiss();
	}

}
