package com.sxu.baselibrary.datasource.http.impl;


import com.sxu.baselibrary.datasource.http.bean.ResponseBean;

/* *****************************************************************************
 * Description: 网络应答数据处理器
 *
 * Author: Freeman
 *
 * Date: 2018/1/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ResponseProcessor {
	
	// 请求成功
	public final static int RESPONSE_CODE_SUCCESS = 1;
	// 数据为空
	public final static int RESPONSE_CODE_EMPTY = 0;
	// 未登录
	public final static int RESPONSE_CODE_NO_LOGIN = -1;
	// 默认错误 -> 请求失败或解析错误
	public final static int RESPONSE_CODE_DEFAULT = 10001;
	
	// 错误提示消息
	public final static String ERROR_MSG_NETWORK_ERROR = "网络异常或解析错误";


	public static void process(ResponseBean response, RequestListener listener) {
		if (listener == null) {
			return;
		}

		if (listener instanceof RequestListener.SimpleRequestListener) {
			simpleRequestProcess(response, (RequestListener.SimpleRequestListener) listener);
		} else {
			requestProcess(response, (RequestListener.RequestListenerEx) listener);
		}
	}

	private static void simpleRequestProcess(ResponseBean response, RequestListener.SimpleRequestListener listener) {
		if (response != null) {
			if (response.code == RESPONSE_CODE_SUCCESS) {
				listener.onSuccess(response.data);
			} else {
				listener.onFailure(response.code, response.msg);
			}
		} else {
			listener.onFailure(RESPONSE_CODE_DEFAULT, ERROR_MSG_NETWORK_ERROR);
		}
	}

	private static void requestProcess(ResponseBean response, RequestListener.RequestListenerEx listener) {
		if (response != null) {
			if (response.code == RESPONSE_CODE_SUCCESS) {
				listener.onSuccess(response.data, response.msg);
			} else {
				listener.onFailure(response, response.code, response.msg);
			}
		} else {
			listener.onFailure(null, RESPONSE_CODE_DEFAULT, ERROR_MSG_NETWORK_ERROR);
		}
	}
}
