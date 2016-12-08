package com.soaringcloud.kit.box;

public class UnitKit {
	/**
	 * <b>getStepCountToDistance。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 步数转换成距离。
	 * 
	 * @param isMetric
	 * @param stepCount
	 * @param stepLength
	 */
	public static float getStepCountToDistance(boolean isMetric, long stepCount, float stepLength) {
		float distance = 0f;
//		if (isMetric) {
			// centimeters/kilometer
			distance += ((int) (stepLength * stepCount / 100000.0 * 1000))/ 1000f;
//		} else {
//			// inches/mile
//			distance += ((int) (stepLength * stepCount / 63360.0 * 1000))/ 1000f;
//		}
		return distance;
	}
}
