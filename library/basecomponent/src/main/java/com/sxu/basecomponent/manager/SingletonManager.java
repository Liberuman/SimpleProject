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

	private static Map<Class, Object> singletonMap = new ConcurrentHashMap<>();

	public static<T> T getInstance(@NonNull Class<T> tClass) {
		if (tClass.getName().contains("$")) {
			throw new IllegalArgumentException("tClass can't be inner class!");
		}
		Object result = singletonMap.get(tClass);
		if (result == null) {
			synchronized (singletonMap) {
				if (result == null) {
					try {
						Constructor constructor = tClass.getDeclaredConstructor();
						constructor.setAccessible(true);
						result = constructor.newInstance();
						singletonMap.put(tClass, result);
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}

		return (T) result;
	}

	public static void removeInstance(@NonNull Class<?> tClass) {
		singletonMap.remove(tClass);
	}

	public static void clear() {
		singletonMap.clear();
	}
}
