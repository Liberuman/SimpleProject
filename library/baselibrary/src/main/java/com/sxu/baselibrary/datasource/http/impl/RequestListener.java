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

	abstract class SimpleRequestListener<T> implements RequestListener {

		public abstract void onSuccess(T response);

		public abstract void onFailure(int code, String msg);
	}

	abstract class RequestListenerEx<T> implements RequestListener {

		abstract void onSuccess(T response, String msg);

		abstract void onFailure(T response, int code, String msg);
	}
}
