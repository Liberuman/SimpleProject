package com.sxu.baselibrary.datasource.http.bean;

import java.io.Serializable;

/* *****************************************************************************
 * Description: 网络请求数据协议
 *
 * Author: Freeman
 *
 * Date: 2017/6/21
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ResponseBean<T> extends BaseBean {

	public int code;

	public String msg;

	public T data;

	@Override
	public String toString() {
		String dataStr = data != null ? data.toString() : "";
		return "code=" + code
				+ "  msg=" + msg
				+ "  data=" + dataStr;
	}
}

