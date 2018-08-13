package com.sxu.baselibrary.commonutils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*******************************************************************************
 * Description: 文件相关的操作
 *
 * Author: Freeman
 *
 * Date: 2017/6/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class FileUtil {

	/**
	 * SD卡是否有效
	 * @return
	 */
	public static boolean SDCardIsValid() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean fileIsExist(String filePath) {
		if (SDCardIsValid()) {
			File file = new File(filePath);
			if (file.exists()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 创建新的目录
	 * @param path
	 * @return
	 */
	public static boolean mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file.mkdirs();
		}

		return false;
	}

	/**
	 * 获取APP的缓存路径 data/data/包名/cache
	 * @param context
	 * @return
	 */
	public static String getPrivatePath(Context context) {
		return context.getCacheDir().getAbsolutePath();
	}

	/**
	 * 保存文件到指定目录
	 * @param directoryPath
	 * @param fileName
	 * @param content
	 */
	public static void saveFile(String directoryPath, String fileName, String content) {
		if (SDCardIsValid()) {
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			File file = new File(directoryPath, fileName);
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				outputStream.write(content.getBytes());
				outputStream.flush();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception e) {
						e.printStackTrace(System.out);
					}
				}
			}
		}
	}

	/**
	 * 删除指定文件
	 * @param directoryPath
	 * @param fileName
	 */
	public static void deleteFile(String directoryPath, String fileName) {
		deleteFile(directoryPath + File.separator + fileName);
	}

	/**
	 * 删除指定文件
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		if (SDCardIsValid()) {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 读取指定文件的内容
	 * @param directoryPath
	 * @param fileName
	 * @return
	 */
	public static String readFile(String directoryPath, String fileName) {
		return readFile(directoryPath + File.separator + fileName);
	}

	/**
	 * 读取指定文件的内容
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		String content = null;
		if (SDCardIsValid()) {
			File file = new File(filePath);
			if (file.exists()) {
				FileInputStream inputStream = null;
				try {
					inputStream = new FileInputStream(file);
					int len = inputStream.available();
					byte[] buffer = new byte[len];
					inputStream.read(buffer);
					content = buffer.toString();
				} catch (Exception e) {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (Exception e1) {
							e1.printStackTrace(System.out);
						}
					}
				}

			}

		}

		return content;
	}

	/**
	 * 获取指定文件的大小
	 * @param directoryPath
	 * @param fileName
	 * @return
	 */
	public static int getFileSize(String directoryPath, String fileName) {
		return getFileSize(directoryPath + File.separator + fileName);
	}

	/**
	 * 获取指定文件的大小
	 * @param filePath
	 * @return
	 */
	public static int getFileSize(String filePath) {
		if (SDCardIsValid()) {
			File file = new File(filePath);
			if (file.exists()) {
				file.getTotalSpace();
			}
		}

		return 0;
	}
}
