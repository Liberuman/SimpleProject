package com.sxu.basecomponent.manager;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*******************************************************************************
 * Description: 管理单例对象(需要实现单例的类只需要将构造方法设置为private即可)
 *
 * Author: Freeman
 *
 * Date: 2018/11/21
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class SingletonManager {

	/**
	 * 内部类的分隔符
	 */
	private final static String INNER_CLASS_SEPARATOR = "$";

	private final static Map<Class, Object> SINGLETON_MAP = new ConcurrentHashMap<>();

	public static<T> T getInstance(@NonNull Class<T> tClass) {
		if (tClass.getName().contains(INNER_CLASS_SEPARATOR)) {
			throw new IllegalArgumentException("tClass can't be inner class!");
		}
		Object result = SINGLETON_MAP.get(tClass);
		if (result == null) {
			synchronized (SINGLETON_MAP) {
				try {
					Constructor constructor = tClass.getDeclaredConstructor();
					constructor.setAccessible(true);
					result = constructor.newInstance();
					SINGLETON_MAP.put(tClass, result);
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return (T) result;
	}

	public static void removeInstance(@NonNull Class<?> tClass) {
		SINGLETON_MAP.remove(tClass);
	}

	public static void clear() {
		SINGLETON_MAP.clear();
	}
}
