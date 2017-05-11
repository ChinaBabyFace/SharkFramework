package com.soaringcloud.kit.box;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.soaringcloud.kit.KitSettings;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * <b>TakePhotoTools。</b>
 * <p>
 * <b>详细说明：</b>
 * </p>
 * <!-- 在此添加详细说明 --> 拍照、从相册选图片
 * <p>
 * <b>修改列表：</b>
 * </p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF">
 * <td>序号</td>
 * <td>作者</td>
 * <td>修改日期</td>
 * <td>修改内容</td>
 * </tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr>
 * <td>1</td>
 * <td>Wancheng</td>
 * <td>2014-6-21 下午3:51:17</td>
 * <td>建立类型</td>
 * </tr>
 * 
 * </table>
 * 
 * @version 1.0
 * @author Wancheng
 * @since 1.0
 */
public class CameraKit {

	public static final int CAMERA = 1;
	public static final int ALBUM = 2;
	public static final int PHOTO_REQUEST_CUT = 3;
	public static final int MAX_SIZE = 200;
	public static String fileName;
	static DisplayMetrics displayMetrics = new DisplayMetrics();

	/**
	 * 
	 * <b>startCameraOrGallery。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 从相册或相机获取图片 启动相机或相册
	 * 
	 * @param context
	 * @param type
	 */
	public static void startCameraOrGallery(Activity context, int type) {
		try {
			Intent intent;
			if (!isExternalStorageWritable()) {
				showToast(context, "请检查是否有SD卡");
				return;
			}
			fileName = getFileName();
			if (type == CAMERA) {
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileName)));
			} else {
				intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			}

			context.startActivityForResult(intent, type);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * <b>startPhotoZoom。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 无。
	 * 
	 * @param context
	 * @param uri
	 * @param size
	 */
	public static void startPhotoZoom(Activity context, Uri uri, int size) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileName)));
		intent.putExtra("return-data", false);// 若为false则表示不返回数据
		context.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	/**
	 * 
	 * <b>startPhotoZoom。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 无。
	 * 
	 * @param context
	 * @param uri
	 * @param w
	 * @param h
	 */
	public static void startPhotoZoom(Activity context, Uri uri, int w, int h) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", w);
		intent.putExtra("aspectY", h);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", w);
		intent.putExtra("outputY", h);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(fileName)));
		intent.putExtra("return-data", false);// 若为false则表示不返回数据
		context.startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/**
	 * 
	 * <b>getFileName。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 生成文件路径和文件名
	 * 
	 * @return
	 */
	public static String getFileName() {

		String saveDir = Environment.getExternalStorageDirectory() + KitSettings.FILE_PATH + "/images";
		File file = new File(saveDir);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}
		// 用日期作为文件名，确保唯一性
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
		String fileName = saveDir + File.separator + dateFormat.format(date) + ".jpg";
		return fileName;
	}

	/**
	 * 
	 * <b>getPhoto。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 获取图片
	 * 
	 * @param context
	 * @param data
	 * @return
	 */
	public static Bitmap getPhoto(Activity context, Intent data) {
		context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Bitmap bitmap = null;
		if (data != null) {
			// 照片的原始资源地址
			Uri originUri = data.getData();
			// 通过URI获取原始图片path
			fileName = getRealPath(context, originUri);
		}

		bitmap = decodeSampledBitmapFromResource(fileName, displayMetrics.widthPixels, displayMetrics.heightPixels);
		// 是否旋转图片
		int degree = getPictureDegree(fileName);
		if (degree > 0) {
			bitmap = rotaingImage(degree, bitmap);
		}
		Bitmap bit = compressImage(bitmap, MAX_SIZE);
		if (data != null) {
			fileName = getFileName();
		}
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(fileName));
			// 采用压缩转档方法
			bit.compress(CompressFormat.JPEG, 100, bos);
			// 调用flush()方法，更新BufferStream
			bos.flush();
			bos.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		bitmap.recycle();
		return bit;
	}

	/**
	 *
	 * <b>getPhotoBytes。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 获取图片
	 *
	 * @param context
	 * @param data
	 * @return
	 */
	public static byte[] getPhotoBytes(Activity context, Intent data) {
		context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Bitmap bitmap = null;
		if (data != null) {
			// 照片的原始资源地址
			Uri originUri = data.getData();
			// 通过URI获取原始图片path
			fileName = getRealPath(context, originUri);
		}

		bitmap = decodeSampledBitmapFromResource(fileName, displayMetrics.widthPixels, displayMetrics.heightPixels);
		// 是否旋转图片
		int degree = getPictureDegree(fileName);
		if (degree > 0) {
			bitmap = rotaingImage(degree, bitmap);
		}
		Bitmap bit = compressImage(bitmap, MAX_SIZE);
		if (data != null) {
			fileName = getFileName();
		}
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(fileName));
			// 采用压缩转档方法
			bit.compress(CompressFormat.JPEG, 100, bos);
			// 调用flush()方法，更新BufferStream
			bos.flush();
			bos.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		bitmap.recycle();
		return bitmap2Bytes(bit);
	}

	/**
	 *
	 * <b>Bitmap2Bytes。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 将bitmap转换成byte[]
	 *
	 * @param bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	// public static void saveBitmap(Bitmap bmp, File file){
	// FileOutputStream outStream = null;
	// try {
	// outStream = new FileOutputStream(file);
	// outStream.write(bitmap2Bytes(bmp));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// if(null != outStream)
	// try {
	// outStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	/**
	 * 
	 * <b>compressImage。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 按质量缩放图片到指定大小
	 * 
	 * @param bitmap
	 * @param maxSize
	 * @return
	 */
	public static Bitmap compressImage(Bitmap bitmap, int maxSize) {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到arrayOutputStream中
		bitmap.compress(CompressFormat.JPEG, 100, arrayOutputStream);
		int options = 100;
		// 循环判断如果压缩后图片是否大于maxSize,大于继续压缩
		while (arrayOutputStream.toByteArray().length / 1024 > maxSize) {
			options -= 10;// 降低质量
			arrayOutputStream.reset();// 清空数组数据
			bitmap.compress(CompressFormat.JPEG, options, arrayOutputStream);// 循环压缩图片
		}
		// 把压缩后的数据arrayOutputStream存放到ByteArrayInputStream中
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
		// 用ByteArrayInputStream数据生成图片
		Bitmap bit = BitmapFactory.decodeStream(arrayInputStream);
		bitmap.recycle();
		return bit;
	}

	/**
	 * 
	 * <b>decodeSampledBitmapFromResource。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 按比例缩放图片 根据路径获取图片
	 * 
	 * @param path
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 
	 * <b>calculateInSampleSize。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 计算缩放比例
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// height/width of image
		int height = options.outHeight;
		int width = options.outWidth;

		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			while ((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;

	}

	/**
	 * 
	 * <b>calculateInSampleSize1。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 计算缩放比例
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	// private static int calculateInSampleSize1(BitmapFactory.Options options,
	// int reqWidth, int reqHeight) {
	// // height/width of image
	// int height = options.outHeight;
	// int width = options.outWidth;
	//
	// int inSampleSize = 1;
	//
	// if (height > reqHeight || width > reqWidth) {
	// final int heightScale = Math.round((float) height / (float) reqHeight);
	// final int widthScale = Math.round((float) width / (float) reqWidth);
	// inSampleSize = heightScale > widthScale ? heightScale : widthScale;
	// }
	//
	// return inSampleSize;
	//
	// }

	/**
	 * 
	 * <b>getRealPath。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> URI转Filepath
	 * 
	 * @param context
	 * @param fileUrl
	 * @return
	 */
	private static String getRealPath(Context context, Uri fileUrl) {
		String fileName = null;
		Uri filePathUri = fileUrl;
		if (fileUrl != null) {
			if (fileUrl.getScheme().toString().compareTo("content") == 0) {
				// content://开头的URI
				Cursor cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					fileName = cursor.getString(column_index); // 取出文件路径
					cursor.close();
				}
			} else if (fileUrl.getScheme().compareTo("file") == 0) {
				// file:///开头的URI
				fileName = filePathUri.toString();
				fileName = filePathUri.toString().replace("file://", "");// 替换file://
			}
		}
		return fileName;
	}

	/**
	 * 
	 * <b>getPictureDegree。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 获取图片的旋转角度 path
	 * 
	 * @param path
	 * @return
	 */
	private static int getPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			default:
				break;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 
	 * <b>rotaingImage。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 无。
	 * 
	 * @param degree
	 * @param bitmap
	 * @return
	 */
	private static Bitmap rotaingImage(int degree, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		// 创建新的图片
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		bitmap.recycle();
		return newBitmap;
	}

	/**
	 * 
	 * <b>showToast。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 显示Toast
	 * 
	 * @param mContext
	 * @param showText
	 */
	public static void showToast(Context mContext, String showText) {
		Toast.makeText(mContext, showText, Toast.LENGTH_SHORT).show();
	}

}
