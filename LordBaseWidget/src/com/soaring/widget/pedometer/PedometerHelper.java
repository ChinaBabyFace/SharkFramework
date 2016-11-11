/**
 * PedometerHelper.java 2015-1-21
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaring.widget.pedometer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

/**
 * <b>PedometerHelper。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-21 下午3:23:52</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class PedometerHelper {

	private static final String TAG = "PedometerHelper";
	private Context context;
	private SharedPreferences mSettings;
	private PedometerSettings pedometerSettings;
	private Utils mUtils;

	// private TextView mStepValueView;
	// private TextView mPaceValueView;
	// private TextView mDistanceValueView;
	// private TextView mSpeedValueView;
	// private TextView mCaloriesValueView;
	TextView mDesiredPaceView;
	private int mStepValue;
	private int mPaceValue;
	private float mDistanceValue;
	private float mSpeedValue;
	private float mCaloriesValue;
	private float mDesiredPaceOrSpeed;
	private int mMaintain;
	private boolean mIsMetric;
	private float mMaintainInc;
	private boolean mQuitting = false; // Set when user selected Quit from menu, can be used by onPause, onStop, onDestroy
	private OnPedometerChangedListener onPedometerChangedListener;
	/**
	 * True, when service is running.
	 */
	private boolean mIsRunning = false;

	public PedometerHelper(Context context) {
		setContext(context);
		onCreate();
	}

	/** Called when the activity is first created. */
	public void onCreate() {
		Log.i(TAG, "onCreate");
		mStepValue = 0;
		mPaceValue = 0;
		mUtils = Utils.getInstance();
		mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
		pedometerSettings = new PedometerSettings(mSettings);
	}

	public void onResume() {
		Log.i(TAG, " onResume");

		mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
		pedometerSettings = new PedometerSettings(mSettings);
		mUtils.setSpeak(mSettings.getBoolean("speak", false));

		// Read from preferences if the service was running on the last onPause
		mIsRunning = pedometerSettings.isServiceRunning();

		// Start the service if this is considered to be an application start (last onPause was long ago)
		if (!mIsRunning && pedometerSettings.isNewStart()) {
			startStepService();
			bindStepService();
		} else if (mIsRunning) {
			bindStepService();
		}

		pedometerSettings.clearServiceRunning();

		mIsMetric = pedometerSettings.isMetric();
		// 单位
		// ((TextView) findViewById(R.id.distance_units)).setText(getString(mIsMetric ? R.string.kilometers : R.string.miles));
		// ((TextView) findViewById(R.id.speed_units)).setText(getString(mIsMetric ? R.string.kilometers_per_hour : R.string.miles_per_hour));

		mMaintain = pedometerSettings.getMaintainOption();
		// ((LinearLayout) this.findViewById(R.id.desired_pace_control)).setVisibility(mMaintain != PedometerSettings.M_NONE ? View.VISIBLE : View.GONE);
		if (mMaintain == PedometerSettings.M_PACE) {
			mMaintainInc = 5f;
			mDesiredPaceOrSpeed = (float) pedometerSettings.getDesiredPace();
		} else if (mMaintain == PedometerSettings.M_SPEED) {
			mDesiredPaceOrSpeed = pedometerSettings.getDesiredSpeed();
			mMaintainInc = 0.1f;
		}
		// Button button1 = (Button) findViewById(R.id.button_desired_pace_lower);
		// button1.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// mDesiredPaceOrSpeed -= mMaintainInc;
		// mDesiredPaceOrSpeed = Math.round(mDesiredPaceOrSpeed * 10) / 10f;
		// displayDesiredPaceOrSpeed();
		// setDesiredPaceOrSpeed(mDesiredPaceOrSpeed);
		// }
		// });
		// Button button2 = (Button) findViewById(R.id.button_desired_pace_raise);
		// button2.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// mDesiredPaceOrSpeed += mMaintainInc;
		// mDesiredPaceOrSpeed = Math.round(mDesiredPaceOrSpeed * 10) / 10f;
		// displayDesiredPaceOrSpeed();
		// setDesiredPaceOrSpeed(mDesiredPaceOrSpeed);
		// }
		// });
		if (mMaintain != PedometerSettings.M_NONE) {
			// ((TextView) findViewById(R.id.desired_pace_label)).setText(mMaintain == PedometerSettings.M_PACE ? R.string.desired_pace
			// : R.string.desired_speed);
		}

	}

	public void onPause() {
		Log.i(TAG, " onPause");
		if (mIsRunning) {
			unbindStepService();
		}
		if (mQuitting) {
			pedometerSettings.saveServiceRunningWithNullTimestamp(mIsRunning);
		} else {
			pedometerSettings.saveServiceRunningWithTimestamp(mIsRunning);
		}
		savePaceSetting();
	}

	/**
	 * @return the pedometerSettings
	 */
	public PedometerSettings getPedometerSettings() {
		return pedometerSettings;
	}

	/**
	 * @param pedometerSettings the pedometerSettings to set
	 */
	public void setPedometerSettings(PedometerSettings pedometerSettings) {
		this.pedometerSettings = pedometerSettings;
	}

	public void setDesiredPaceOrSpeed(float desiredPaceOrSpeed) {
		if (mService != null) {
			if (mMaintain == PedometerSettings.M_PACE) {
				mService.setDesiredPace((int) desiredPaceOrSpeed);
			} else if (mMaintain == PedometerSettings.M_SPEED) {
				mService.setDesiredSpeed(desiredPaceOrSpeed);
			}
		}
	}

	private void savePaceSetting() {
		pedometerSettings.savePaceOrSpeedSetting(mMaintain, mDesiredPaceOrSpeed);
	}

	private StepService mService;

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = ((StepService.StepBinder) service).getService();

			mService.registerCallback(mCallback);
			mService.reloadSettings();

		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
		}
	};

	private void startStepService() {
		if (!mIsRunning) {
			Log.i(TAG, "[SERVICE] Start");
			mIsRunning = true;
			getContext().startService(new Intent(getContext(), StepService.class));
		}
	}

	private void bindStepService() {
		Log.i(TAG, "[SERVICE] Bind");
		getContext().bindService(new Intent(getContext(), StepService.class), mConnection, Context.BIND_AUTO_CREATE + Context.BIND_DEBUG_UNBIND);
	}

	private void unbindStepService() {
		Log.i(TAG, "[SERVICE] Unbind");
		getContext().unbindService(mConnection);
	}

	public void stopStepService() {
		Log.i(TAG, "[SERVICE] Stop");
		if (mService != null) {
			Log.i(TAG, "[SERVICE] stopService");
			getContext().stopService(new Intent(getContext(), StepService.class));
		}
		mIsRunning = false;
	}

	public void resetValues(boolean updateDisplay) {
		if (mService != null && mIsRunning) {
			mService.resetValues();
		} else {
			// mStepValueView.setText("0");
			// mPaceValueView.setText("0");
			// mDistanceValueView.setText("0");
			// mSpeedValueView.setText("0");
			// mCaloriesValueView.setText("0");
			SharedPreferences state = getContext().getSharedPreferences("state", 0);
			SharedPreferences.Editor stateEditor = state.edit();
			if (updateDisplay) {
				stateEditor.putInt("steps", 0);
				stateEditor.putInt("pace", 0);
				stateEditor.putFloat("distance", 0);
				stateEditor.putFloat("speed", 0);
				stateEditor.putFloat("calories", 0);
				stateEditor.commit();
			}
		}
	}

	public int getTotalStep() {
		try {
			return getContext().getSharedPreferences("state", 0).getInt("steps", 0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
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
	 * @return the onPedometerChangedListener
	 */
	public OnPedometerChangedListener getOnPedometerChangedListener() {
		return onPedometerChangedListener;
	}

	/**
	 * @param onPedometerChangedListener the onPedometerChangedListener to set
	 */
	public void setOnPedometerChangedListener(OnPedometerChangedListener onPedometerChangedListener) {
		this.onPedometerChangedListener = onPedometerChangedListener;
	}

	// TODO: unite all into 1 type of message
	private StepService.ICallback mCallback = new StepService.ICallback() {

		public void stepsChanged(int value) {
			mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
		}

		public void paceChanged(int value) {
			mHandler.sendMessage(mHandler.obtainMessage(PACE_MSG, value, 0));
		}

		public void distanceChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(DISTANCE_MSG, (int) (value * 1000), 0));
		}

		public void speedChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(SPEED_MSG, (int) (value * 1000), 0));
		}

		public void caloriesChanged(float value) {
			mHandler.sendMessage(mHandler.obtainMessage(CALORIES_MSG,(int)(value),0));
		}
	};

	private static final int STEPS_MSG = 1;
	private static final int PACE_MSG = 2;
	private static final int DISTANCE_MSG = 3;
	private static final int SPEED_MSG = 4;
	private static final int CALORIES_MSG = 5;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STEPS_MSG:
				if (getOnPedometerChangedListener() != null) {
					mStepValue = msg.arg1;
					if (mStepValue > 0) {
						getOnPedometerChangedListener().onStepChanged(mStepValue);
					} else {
						getOnPedometerChangedListener().onStepChanged(0);
					}
				}
				break;
			case PACE_MSG:
				mPaceValue = msg.arg1;
				if (getOnPedometerChangedListener() != null) {
					if (mPaceValue > 0) {
						getOnPedometerChangedListener().onPeaceChanged(mPaceValue);
					} else {
						getOnPedometerChangedListener().onPeaceChanged(0);
					}
				}
				break;
			case DISTANCE_MSG:
				if (getOnPedometerChangedListener() != null) {
					mDistanceValue = msg.arg1 / 1000f;
					if (mDistanceValue > 0) {
						getOnPedometerChangedListener().onDistanceChanged(mDistanceValue);
					} else {
						getOnPedometerChangedListener().onDistanceChanged(0);
					}
				}
				break;
			case SPEED_MSG:
				if (getOnPedometerChangedListener() != null) {
					mSpeedValue = msg.arg1 / 1000f;
					if (mSpeedValue > 0) {
						getOnPedometerChangedListener().onSpeedChanged(mSpeedValue);
					} else {
						getOnPedometerChangedListener().onSpeedChanged(0);
					}
				}
				break;
			case CALORIES_MSG:
				if (getOnPedometerChangedListener() != null) {
					mCaloriesValue = msg.arg1;
					if (mCaloriesValue > 0) {
						getOnPedometerChangedListener().onCaloriesChanged(mCaloriesValue);
					} else {
						getOnPedometerChangedListener().onCaloriesChanged(0);
					}
				}
				break;
			default:
				super.handleMessage(msg);
			}
		}

	};
}
