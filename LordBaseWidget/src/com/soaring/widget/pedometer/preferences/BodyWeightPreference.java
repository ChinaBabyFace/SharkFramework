package com.soaring.widget.pedometer.preferences;

import android.content.Context;
import android.util.AttributeSet;

import com.soaring.widget.R;

public class BodyWeightPreference extends EditMeasurementPreference {

	public BodyWeightPreference(Context context) {
		super(context);
	}

	public BodyWeightPreference(Context context, AttributeSet attr) {
		super(context, attr);
	}

	public BodyWeightPreference(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
	}

	protected void initPreferenceDetails() {
		mTitleResource = R.string.body_weight_setting_title;
		mMetricUnitsResource = R.string.kilograms;
		mImperialUnitsResource = R.string.pounds;
	}
}
