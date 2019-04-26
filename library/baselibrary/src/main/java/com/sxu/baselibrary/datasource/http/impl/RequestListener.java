package com.sxu.baselibrary.datasource.http.impl;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/8/14
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface RequestListener {

	abstract class BaseSimpleRequestListener<T> implements RequestListener {

		/**
		 * 网络请求成功时被调用
		 * @param response 应答数据
		 */
		public abstract void onSuccess(T response);

		/**
		 * 网络请求失败时被调用
		 * @param code 应答Code
		 * @param msg 应答消息提示
		 */
		public abstract void onFailure(int code, String msg);
	}

	abstract class BaseRequestListenerEx<T> implements RequestListener {

		/**
		 * 网络请求成功时被调用
		 * @param response
		 * @param msg
		 */
		abstract void onSuccess(T response, String msg);

		/**
		 * 网络请求失败时被调用
		 * @param response 应答数据
		 * @param code 应答Code
		 * @param msg 应答消息提示
		 */
		abstract void onFailure(T response, int code, String msg);
	}
}
