/**
 * FileTools.java 2015-1-5
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <b>BitmapKit。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-13 下午1:44:35</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public final class BitmapKit {
	public final static double MAX_AREA=5242880D;
	/**
	 * 
	 * <b>toRoundBitmap。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 转换图片成圆形
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}
	
	/**
	 * 
	 * <b>getRoundedCornerBitmap。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 转圆角Bitmap
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float roundPx) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;// 颜色值（0xff---alpha）
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);// Rect是使用int类型作为数值，RectF是使用float类型作为数值
			// --------抗锯齿-------//
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			// canvas.drawBitmap(bitmap, null, rect, paint);
			canvas.drawBitmap(bitmap, src, rect, paint);
			return output;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public Bitmap getScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay().getHeight();
		// 去掉标题栏
		
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * <b>calculateInSampleSize。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 计算图片压缩比例参数。
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	/**
	 * <b>isRectVeryLarge。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 判断矩形区域是否过大。
	 * @param rect
	 * @return
	 */
	public static boolean isRectVeryLarge(Rect rect) {
		return rect.width() * rect.height() * 2 <= MAX_AREA;
	}

	/**
	 * <b>getBitmapSampleSizeByRect。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据图片大小获得压缩系数。
	 * @param rect
	 * @return
	 */
	public static int getBitmapSampleSizeByRect(Rect rect) {
		double ratio = rect.width() * rect.height() * 2.0D / MAX_AREA;
		return ratio >= 1.0D ? (int) ratio : 1;
	}

	/**
	 * <b>getSampleSizeAutoFitToScreen。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 获得可以适应屏幕的压缩系数。
	 * @param vWidth
	 * @param vHeight
	 * @param bWidth
	 * @param bHeight
	 * @return
	 */
	public static int getSampleSizeAutoFitToScreen(int vWidth, int vHeight, int bWidth, int bHeight) {
		if ((vHeight == 0) || (vWidth == 0)) {
			return 1;
		}
		int ratio = Math.max(bWidth / vWidth, bHeight / vHeight);

		int ratioAfterRotate = Math.max(bHeight / vWidth, bWidth / vHeight);

		return Math.min(ratio, ratioAfterRotate);
	}

	public static boolean verifyBitmap(byte[] datas) {
		return isBitmapValidByStream(new ByteArrayInputStream(datas));
	}

	/**
	 * <b>isBitmapValid。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 图片是否有效。
	 * @param input
	 * @return
	 */
	public static boolean isBitmapValidByStream(InputStream input) {
		if (input == null) {
			return false;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		input = (input instanceof BufferedInputStream) ? input : new BufferedInputStream(input);
		BitmapFactory.decodeStream(input, null, options);
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (options.outHeight > 0) && (options.outWidth > 0);
	}

	/**
	 * <b>isBitmapValidByPath。</b>  
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 根据图片路径判断图片是否有效。
	 * @param path
	 * @return
	 */
	public static boolean isBitmapValidByPath(String path) {
		try {
			return isBitmapValidByStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
