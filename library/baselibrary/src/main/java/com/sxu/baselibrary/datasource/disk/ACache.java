package com.sxu.baselibrary.datasource.disk;

/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.sxu.baselibrary.commonutils.ThreadPoolManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Michael Yang（www.yangfuhai.com） update at 2013.08.07
 */
public class ACache {
	public static final int TIME_HOUR = 60 * 60;
	public static final int TIME_DAY = TIME_HOUR * 24;

	private static final int CACHE_MAX_SIZE = 1000 * 1000 * 50;
	private static final int CACHE_MAX_COUNT = Integer.MAX_VALUE;
	private static Map mInstanceMap = new HashMap<String, ACache>();
	private ACacheManager mCache;

	/**
	 * 默认的字符编码
	 */
	private final static String DEFAULT_CHARSET_NAME = "UTF-8";

	public static ACache get(Context ctx) {
		return get(ctx.getApplicationContext(), "ACache");
	}

	public static ACache get(Context ctx, String cacheName) {
		File f = new File(ctx.getCacheDir(), cacheName);
		return get(f, CACHE_MAX_SIZE, CACHE_MAX_COUNT);
	}

	public static ACache get(File cacheDir) {
		return get(cacheDir, CACHE_MAX_SIZE, CACHE_MAX_COUNT);
	}

	public static ACache get(Context ctx, long maxSize, int maxCount) {
		File f = new File(ctx.getCacheDir(), "ACache");
		return get(f, maxSize, maxCount);
	}

	public static ACache get(File cacheDir, long maxSize, int maxCount) {
		ACache manager = (ACache) mInstanceMap.get(cacheDir.getAbsoluteFile() + myPid());
		if (manager == null) {
			manager = new ACache(cacheDir, maxSize, maxCount);
			mInstanceMap.put(cacheDir.getAbsolutePath() + myPid(), manager);
		}
		return manager;
	}

	private static String myPid() {
		return "_" + android.os.Process.myPid();
	}

	private ACache(File cacheDir, long maxSize, int maxCount) {
		if (!cacheDir.exists() && !cacheDir.mkdirs()) {
			throw new RuntimeException("can't make dirs in "
					+ cacheDir.getAbsolutePath());
		}
		mCache = new ACacheManager(cacheDir, maxSize, maxCount);
	}

	// =======================================
	// ============ String数据 读写 ==============
	// =======================================
	/**
	 * 保存 String数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的String数据
	 */
	public void put(String key, String value) {
		File file = mCache.newFile(key);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), DEFAULT_CHARSET_NAME));
			out.write(value);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			mCache.put(file);
		}
	}

	/**
	 * 保存 String数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的String数据
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, String value, int saveTime) {
		put(key, Utils.newStringWithDateInfo(saveTime, value));
	}

	/**
	 * 读取 String数据
	 *
	 * @param key
	 * @return String 数据
	 */
	public String getAsString(String key) {
		File file = mCache.get(key);
		if (!file.exists()) {
			return null;
		}
		boolean removeFile = false;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET_NAME));
			StringBuilder readString = new StringBuilder();
			String currentLine;
			while ((currentLine = in.readLine()) != null) {
				readString.append(currentLine);
			}
			if (!Utils.isDue(readString.toString())) {
				return Utils.clearDateInfo(readString.toString());
			} else {
				removeFile = true;
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (removeFile) {
				remove(key);
			}
		}
	}

	// =======================================
	// ============= JSONObject 数据 读写 ==============
	// =======================================
	/**
	 * 保存 JSONObject数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的JSON数据
	 */
	public void put(String key, JSONObject value) {
		put(key, value.toString());
	}

	/**
	 * 保存 JSONObject数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的JSONObject数据
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, JSONObject value, int saveTime) {
		put(key, value.toString(), saveTime);
	}

	/**
	 * 读取JSONObject数据
	 *
	 * @param key
	 * @return JSONObject数据
	 */
	public JSONObject getAsJSONObject(String key) {
		String jsonString = getAsString(key);
		try {
			return new JSONObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// =======================================
	// ============ JSONArray 数据 读写 =============
	// =======================================
	/**
	 * 保存 JSONArray数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的JSONArray数据
	 */
	public void put(String key, JSONArray value) {
		put(key, value.toString());
	}

	/**
	 * 保存 JSONArray数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的JSONArray数据
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, JSONArray value, int saveTime) {
		put(key, value.toString(), saveTime);
	}

	/**
	 * 读取JSONArray数据
	 *
	 * @param key
	 * @return JSONArray数据
	 */
	public JSONArray getAsJSONArray(String key) {
		String jsonString = getAsString(key);
		try {
			return new JSONArray(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// =======================================
	// ============== byte 数据 读写 =============
	// =======================================
	/**
	 * 保存 byte数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的数据
	 */
	public void put(String key, byte[] value) {
		File file = mCache.newFile(key);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(value);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			quietClose(out);
			mCache.put(file);
		}
	}

	/**
	 * 保存 byte数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的数据
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, byte[] value, int saveTime) {
		put(key, Utils.newByteArrayWithDateInfo(saveTime, value));
	}

	/**
	 * 获取 byte 数据
	 *
	 * @param key
	 * @return byte 数据
	 */
	public byte[] getAsBinary(String key) {
		RandomAccessFile randomFile = null;
		boolean removeFile = false;
		try {
			File file = mCache.get(key);
			if (!file.exists()) {
				return null;
			}
			randomFile = new RandomAccessFile(file, "r");
			byte[] byteArray = new byte[(int) randomFile.length()];
			randomFile.read(byteArray);
			if (!Utils.isDue(byteArray)) {
				return Utils.clearDateInfo(byteArray);
			} else {
				removeFile = true;
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (removeFile) {
				remove(key);
			}
		}
	}

	// =======================================
	// ============= 序列化 数据 读写 ===============
	// =======================================
	/**
	 * 保存 Serializable数据 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 */
	public void put(String key, Serializable value) {
		put(key, value, -1);
	}

	/**
	 * 保存 Serializable数据到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的value
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, Serializable value, int saveTime) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			byte[] data = baos.toByteArray();
			if (saveTime != -1) {
				put(key, data, saveTime);
			} else {
				put(key, data);
			}
			oos.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			quietClose(oos);
			quietClose(baos);
		}
	}

	/**
	 * 读取 Serializable数据
	 *
	 * @param key
	 * @return Serializable 数据
	 */
	public Object getAsObject(String key) {
		byte[] data = getAsBinary(key);
		if (data != null) {
			ByteArrayInputStream byteInputStream = null;
			ObjectInputStream ois = null;
			try {
				byteInputStream = new ByteArrayInputStream(data);
				ois = new ObjectInputStream(byteInputStream);
				return ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					if (byteInputStream != null) {
						byteInputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if (ois != null) {
						ois.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	// =======================================
	// ============== bitmap 数据 读写 =============
	// =======================================
	/**
	 * 保存 bitmap 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的bitmap数据
	 */
	public void put(String key, Bitmap value) {
		put(key, Utils.bitmapToBytes(value));
	}

	/**
	 * 保存 bitmap 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的 bitmap 数据
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, Bitmap value, int saveTime) {
		put(key, Utils.bitmapToBytes(value), saveTime);
	}

	/**
	 * 读取 bitmap 数据
	 *
	 * @param key
	 * @return bitmap 数据
	 */
	public Bitmap getAsBitmap(String key) {
		if (getAsBinary(key) == null) {
			return null;
		}
		return Utils.bytesToBitmap(getAsBinary(key));
	}

	// =======================================
	// ============= drawable 数据 读写 =============
	// =======================================
	/**
	 * 保存 drawable 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的drawable数据
	 */
	public void put(String key, Drawable value) {
		put(key, Utils.drawableToBitmap(value));
	}

	/**
	 * 保存 drawable 到 缓存中
	 *
	 * @param key
	 *            保存的key
	 * @param value
	 *            保存的 drawable 数据
	 * @param saveTime
	 *            保存的时间，单位：秒
	 */
	public void put(String key, Drawable value, int saveTime) {
		put(key, Utils.drawableToBitmap(value), saveTime);
	}

	/**
	 * 读取 Drawable 数据
	 *
	 * @param key
	 * @return Drawable 数据
	 */
	public Drawable getAsDrawable(Context context, String key) {
		if (getAsBinary(key) == null) {
			return null;
		}
		return Utils.bitmap2Drawable(context, Utils.bytesToBitmap(getAsBinary(key)));
	}

	/**
	 * 获取缓存文件
	 *
	 * @param key
	 * @return value 缓存的文件
	 */
	public File file(String key) {
		File f = mCache.newFile(key);
		if (f.exists()) {
			return f;
		}
		return null;
	}

	/**
	 * 移除某个key
	 *
	 * @param key
	 * @return 是否移除成功
	 */
	public boolean remove(String key) {
		return mCache.remove(key);
	}

	/**
	 * 清除所有数据
	 */
	public void clear() {
		mCache.clear();
	}

	/**
	 * 获取缓存大小
	 * @return
	 */
	public long getCacheSize() {
		return mCache.cacheSize.get();
	}

	/**
	 * @title 缓存管理器
	 * @author 杨福海（michael） www.yangfuhai.com
	 * @version 1.0
	 */
	public class ACacheManager {
		private final AtomicLong cacheSize;
		private final AtomicInteger cacheCount;
		private final long sizeLimit;
		private final int countLimit;
		private final Map<File, Long> lastUsageDates = Collections
				.synchronizedMap(new HashMap<File, Long>());
		protected File cacheDir;

		private ACacheManager(File cacheDir, long sizeLimit, int countLimit) {
			this.cacheDir = cacheDir;
			this.sizeLimit = sizeLimit;
			this.countLimit = countLimit;
			cacheSize = new AtomicLong();
			cacheCount = new AtomicInteger();
			calculateCacheSizeAndCacheCount();
		}

		/**
		 * 计算 cacheSize和cacheCount
		 */
		private void calculateCacheSizeAndCacheCount() {
			ThreadPoolManager.executeTask(new Runnable() {
				@Override
				public void run() {
					int size = 0;
					int count = 0;
					File[] cachedFiles = cacheDir.listFiles();
					if (cachedFiles != null) {
						for (File cachedFile : cachedFiles) {
							size += calculateSize(cachedFile);
							count += 1;
							lastUsageDates.put(cachedFile,
									cachedFile.lastModified());
						}
						cacheSize.set(size);
						cacheCount.set(count);
					}
				}
			});
		}

		private void put(File file) {
			int curCacheCount = cacheCount.get();
			while (curCacheCount + 1 > countLimit) {
				long freedSize = removeNext();
				cacheSize.addAndGet(-freedSize);

				curCacheCount = cacheCount.addAndGet(-1);
			}
			cacheCount.addAndGet(1);

			long valueSize = calculateSize(file);
			long curCacheSize = cacheSize.get();
			while (curCacheSize + valueSize > sizeLimit) {
				long freedSize = removeNext();
				curCacheSize = cacheSize.addAndGet(-freedSize);
			}
			cacheSize.addAndGet(valueSize);

			Long currentTime = System.currentTimeMillis();
			file.setLastModified(currentTime);
			lastUsageDates.put(file, currentTime);
		}

		private File get(String key) {
			File file = newFile(key);
			Long currentTime = System.currentTimeMillis();
			file.setLastModified(currentTime);
			lastUsageDates.put(file, currentTime);

			return file;
		}

		private File newFile(String key) {
			return new File(cacheDir, key.hashCode() + "");
		}

		private boolean remove(String key) {
			File image = get(key);
			return image.delete();
		}

		private void clear() {
			lastUsageDates.clear();
			cacheSize.set(0);
			File[] files = cacheDir.listFiles();
			if (files != null) {
				for (File f : files) {
					f.delete();
				}
			}
		}

		/**
		 * 移除旧的文件
		 *
		 * @return
		 */
		private long removeNext() {
			if (lastUsageDates.isEmpty()) {
				return 0;
			}

			Long oldestUsage = null;
			File mostLongUsedFile = null;
			Set<Entry<File, Long>> entries = lastUsageDates.entrySet();
			synchronized (lastUsageDates) {
				for (Entry<File, Long> entry : entries) {
					if (mostLongUsedFile == null) {
						mostLongUsedFile = entry.getKey();
						oldestUsage = entry.getValue();
					} else {
						Long lastValueUsage = entry.getValue();
						if (lastValueUsage < oldestUsage) {
							oldestUsage = lastValueUsage;
							mostLongUsedFile = entry.getKey();
						}
					}
				}
			}

			long fileSize = calculateSize(mostLongUsedFile);
			if (mostLongUsedFile != null && mostLongUsedFile.delete()) {
				lastUsageDates.remove(mostLongUsedFile);
			}
			return fileSize;
		}

		private long calculateSize(File file) {
			return file != null ? file.length() : 0;
		}
	}

	/**
	 * @title 时间计算工具类
	 * @author 杨福海（michael） www.yangfuhai.com
	 * @version 1.0
	 */
	private static class Utils {

		/**
		 * 判断缓存的String数据是否到期
		 *
		 * @param str
		 * @return true：到期了 false：还没有到期
		 */
		private static boolean isDue(String str) {
			try {
				return isDue(str.getBytes(DEFAULT_CHARSET_NAME));
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}

			return false;
		}

		/**
		 * 判断缓存的byte数据是否到期
		 *
		 * @param data
		 * @return true：到期了 false：还没有到期
		 */
		private static boolean isDue(byte[] data) {
			String[] strArray = getDateInfoFromDate(data);
			int arrayLength = 2;
			if (strArray != null && strArray.length == arrayLength) {
				String saveTimeStr = strArray[0];
				while (saveTimeStr.startsWith("0")) {
					saveTimeStr = saveTimeStr
							.substring(1, saveTimeStr.length());
				}
				long saveTime = Long.valueOf(saveTimeStr);
				long deleteAfter = Long.valueOf(strArray[1]);
				return System.currentTimeMillis() > saveTime + deleteAfter * 1000;
			}
			return false;
		}

		private static String newStringWithDateInfo(int second, String strInfo) {
			return createDateInfo(second) + strInfo;
		}

		private static byte[] newByteArrayWithDateInfo(int second, byte[] data2) {
			byte[] data1 = null;
			try {
				data1 = createDateInfo(second).getBytes(DEFAULT_CHARSET_NAME);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			byte[] dataResult;
			int startIndex = 0;
			if (data1 != null && data1.length > 0) {
				dataResult = new byte[data1.length + data2.length];
				System.arraycopy(data1, 0, dataResult, 0, data1.length);
			} else {
				dataResult = new byte[data2.length];
			}
			System.arraycopy(data2, 0, dataResult, startIndex, data2.length);
			return dataResult;
		}

		private static String clearDateInfo(String strInfo) {
			try {
				if (strInfo != null && hasDateInfo(strInfo.getBytes(DEFAULT_CHARSET_NAME))) {
					strInfo = strInfo.substring(strInfo.indexOf(TIME_SEPARATOR) + 1,
							strInfo.length());
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			return strInfo;
		}

		private static byte[] clearDateInfo(byte[] data) {
			if (hasDateInfo(data)) {
				return copyOfRange(data, indexOf(data, TIME_SEPARATOR) + 1,
						data.length);
			}
			return data;
		}

		private static boolean hasDateInfo(byte[] data) {
			return data != null && data.length > 15 && data[13] == '-'
					&& indexOf(data, TIME_SEPARATOR) > 14;
		}

		private static String[] getDateInfoFromDate(byte[] data) {
			if (hasDateInfo(data)) {
				try {
					String saveDate = new String(copyOfRange(data, 0, 13), DEFAULT_CHARSET_NAME);
					String deleteAfter = new String(copyOfRange(data, 14,
							indexOf(data, TIME_SEPARATOR)), DEFAULT_CHARSET_NAME);
					return new String[] { saveDate, deleteAfter };
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
			return null;
		}

		private static int indexOf(byte[] data, char c) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] == c) {
					return i;
				}
			}
			return -1;
		}

		private static byte[] copyOfRange(byte[] original, int from, int to) {
			int newLength = to - from;
			if (newLength < 0) {
				throw new IllegalArgumentException(from + " > " + to);
			}
			byte[] copy = new byte[newLength];
			System.arraycopy(original, from, copy, 0,
					Math.min(original.length - from, newLength));
			return copy;
		}

		private static final char TIME_SEPARATOR = ' ';

		private static String createDateInfo(int second) {
			String currentTime = System.currentTimeMillis() + "";
			return currentTime + "-" + second + TIME_SEPARATOR;
		}

		/**
		 * bitmap转成bytes
		 * @param bm
		 * @return
		 */
		private static byte[] bitmapToBytes(Bitmap bm) {
			if (bm == null) {
				return null;
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();
		}

		/**
		 * 将byte转换为bitmap对象
		 * @param b
		 * @return
		 */
		private static Bitmap bytesToBitmap(byte[] b) {
			if (b.length == 0) {
				return null;
			}
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}

		/**
		 * 将drawable对象转换为Bitmap对象
		 * @param drawable
		 * @return
		 */
		private static Bitmap drawableToBitmap(Drawable drawable) {
			if (drawable == null) {
				return null;
			}
			// 取 drawable 的长宽
			int w = drawable.getIntrinsicWidth();
			int h = drawable.getIntrinsicHeight();
			// 取 drawable 的颜色格式
			Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
					: Bitmap.Config.RGB_565;
			// 建立对应 bitmap
			Bitmap bitmap = Bitmap.createBitmap(w, h, config);
			// 建立对应 bitmap 的画布
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, w, h);
			// 把 drawable 内容画到画布中
			drawable.draw(canvas);
			return bitmap;
		}

		/*
		 * Bitmap → Drawable
		 */

		/**
		 * 将Bitmap对象转换为Drawable对象
		 * @param context
		 * @param bm
		 * @return
		 */
		private static Drawable bitmap2Drawable(Context context, Bitmap bm) {
			if (bm == null) {
				return null;
			}
			return new BitmapDrawable(context.getResources(), bm);
		}
	}

	private static void quietClose(Closeable obj) {
		if (obj == null) {
			return;
		}

		try {
			obj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
