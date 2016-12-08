/**
 * FileTools.java 2015-1-5
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.soaringcloud.kit.box;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <b>FileTools。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-5 下午8:38:55</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class FileKit {

	public static boolean deleteDependon(File file, int maxRetryCount) {
		int retryCount = 1;
		maxRetryCount = maxRetryCount < 1 ? 5 : maxRetryCount;
		boolean isDeleted = false;
		if (file != null) {
			do {
				if (!(isDeleted = file.delete())) {
					retryCount++;
				}
			} while ((!isDeleted) && (retryCount <= maxRetryCount) && (file.isFile()) && (file.exists()));
		}
		return isDeleted;
	}

	public static void mkdirs(File dir_) {
		if (dir_ == null) {
			return;
		}
		if ((!dir_.exists()) && (!dir_.mkdirs())) {
			throw new RuntimeException("fail to make " + dir_.getAbsolutePath());
		}
	}

	public static void createNewFile(File file_) {
		if (file_ == null) {
			return;
		}
		if (!__createNewFile(file_)) {
			throw new RuntimeException(file_.getAbsolutePath() + " doesn't be created!");
		}
	}

	public static void delete(File f) {
		if ((f != null) && (f.exists()) && (!f.delete())) {
			throw new RuntimeException(f.getAbsolutePath() + " doesn't be deleted!");
		}
	}

	public static boolean __createNewFile(File file_) {
		if (file_ == null) {
			return false;
		}
		makesureParentExist(file_);
		if (file_.exists()) {
			delete(file_);
		}
		try {
			return file_.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteDependon(String filepath, int maxRetryCount) {
		if (TextUtils.isEmpty(filepath)) {
			return false;
		}
		return deleteDependon(new File(filepath), maxRetryCount);
	}

	public static boolean deleteDependon(String filepath) {
		return deleteDependon(filepath, 0);
	}

	public static boolean doesExisted(File file) {
		return (file != null) && (file.exists());
	}

	public static boolean doesExisted(String filepath) {
		if (TextUtils.isEmpty(filepath)) {
			return false;
		}
		return doesExisted(new File(filepath));
	}

	public static void makesureParentExist(File file_) {
		if (file_ == null) {
			return;
		}
		File parent = file_.getParentFile();
		if ((parent != null) && (!parent.exists())) {
			mkdirs(parent);
		}
	}

	public static void makesureFileExist(File file) {
		if (file == null) {
			return;
		}
		if (!file.exists()) {
			makesureParentExist(file);
			createNewFile(file);
		}
	}

	public static void makesureFileExist(String filePath_) {
		if (filePath_ == null) {
			return;
		}
		makesureFileExist(new File(filePath_));
	}

	public static boolean saveBitmap(Bitmap bitmap, final String path) {

		File photoFile = new File(path);
		if (!photoFile.getParentFile().exists()) {
			photoFile.getParentFile().mkdirs();
		}

		BufferedOutputStream bos;
		try {
			photoFile.createNewFile();
			bos = new BufferedOutputStream(new FileOutputStream(photoFile));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
