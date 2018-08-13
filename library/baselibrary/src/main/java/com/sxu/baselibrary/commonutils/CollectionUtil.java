package com.sxu.baselibrary.commonutils;

import java.util.Collection;
import java.util.Map;

/*******************************************************************************
 * Description: 集合类的相关操作
 *
 * Author: Freeman
 *
 * Date: 2018/3/23
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class CollectionUtil {

	/**
	 * 判断指定容器是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection collection) {
		if (collection == null || collection.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断指定Map容器是否为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Map collection) {
		if (collection == null || collection.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断指定数组是否为空
	 * @param items
	 * @return
	 */
	public static boolean isEmpty(Object[] items) {
		if (items == null || items.length == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 使用指定字符拼接容器元素
	 * @param collection
	 * @param separator
	 * @return
	 */
	public static String join(Collection<String> collection, String separator) {
		if (collection == null) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (String item : collection) {
			builder.append(item).append(separator);
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}

		return builder.toString();
	}

	/**
	 * 使用指定字符拼接数组元素
	 * @param items
	 * @param separator
	 * @return
	 */
	public static String join(String[] items, String separator) {
		if (items == null) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (String item : items) {
			builder.append(item).append(separator);
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}

		return builder.toString();
	}
}
