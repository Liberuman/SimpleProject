package com.sxu.basecomponent.manager;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.sxu.baselibrary.commonutils.FileUtil;

import java.io.File;

/*******************************************************************************
 * Description: App路径管理
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class PathManager {

	private final static String IMAGE_CACHE_PATH = "imageCache/";
	private final static String DATA_CACHE_PATH = "dataCache/";
	private final static String INFO_DIR_PATH = "info/";
	private final static String CRASH_DIR_PATH = "crash/";
	private final static String FILES_DIR_PATH = "files/";

	private String rootPath;
	private static PathManager instance;

	private PathManager(Context context) {
		setRootPath(context);
		if (!TextUtils.isEmpty(rootPath)) {
			FileUtil.mkdirs(rootPath);
			FileUtil.mkdirs(getImageCachePath());
			FileUtil.mkdirs(getDataCachePath());
			FileUtil.mkdirs(getInfoPath());
			FileUtil.mkdirs(getCrashPath());
			FileUtil.mkdirs(getFilesPath());
		}
	}

	public static PathManager getInstance(Context context) {
		if (instance == null) {
			synchronized (PathManager.class) {
				instance = new PathManager(context);
			}
		}

		return instance;
	}

	public String setRootPath(Context context) {
		if (TextUtils.isEmpty(rootPath)) {
			if (FileUtil.SDCardIsValid()) {
				// /storage/emulated/0/Android/data/[packagename]在Android2.2之后卸载时会自动删除
				File file = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
				rootPath = file.getAbsolutePath();
			} else {
				rootPath = FileUtil.getPrivatePath(context);
			}

			rootPath += File.separator + context.getPackageName() + "/";
		}

		return rootPath;
	}

	public String getImageCachePath() {
		return rootPath + IMAGE_CACHE_PATH;
	}

	public String getDataCachePath() {
		return rootPath + DATA_CACHE_PATH;
	}

	public String getInfoPath() {
		return rootPath + INFO_DIR_PATH;
	}

	public String getCrashPath() {
		return rootPath + CRASH_DIR_PATH;
	}
	public String getFilesPath() {
		return rootPath + FILES_DIR_PATH;
	}
}
