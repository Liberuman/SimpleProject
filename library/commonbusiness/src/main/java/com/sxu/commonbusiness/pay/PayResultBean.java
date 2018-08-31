package com.sxu.commonbusiness.pay;

import android.text.TextUtils;

import java.util.Collections;
import java.util.Map;

/*******************************************************************************
 * Description: 支付宝支付返回的结果数据
 *
 * Author: Freeman
 *
 * Date: 2018/07/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PayResultBean {

	private String resultStatus;
	private String result;
	private String memo;

	public PayResultBean(String rawResult) {

		if (TextUtils.isEmpty(rawResult)) {
			return;
		}

		String[] resultParams = rawResult.split(";");
		if (resultParams == null || resultParams.length == 0) {
			return;
		}

		for (String resultParam : resultParams) {
			if (resultParam.startsWith("resultStatus")) {
				resultStatus = gatValue(resultParam, "resultStatus");
			}
			if (resultParam.startsWith("result")) {
				result = gatValue(resultParam, "result");
			}
			if (resultParam.startsWith("memo")) {
				memo = gatValue(resultParam, "memo");
			}
		}
	}

	public PayResultBean(Map<String, String> rawResult) {
		if (rawResult == null) {
			return;
		}

		for (String key : rawResult.keySet()) {
			if (TextUtils.equals(key, "resultStatus")) {
				resultStatus = rawResult.get(key);
			} else if (TextUtils.equals(key, "result")) {
				result = rawResult.get(key);
			} else if (TextUtils.equals(key, "memo")) {
				memo = rawResult.get(key);
			}
		}
	}

	@Override
	public String toString() {
		return "resultStatus={" + resultStatus + "};memo={" + memo + "};result={" + result + "}";
	}

	private String gatValue(String content, String key) {
		String prefix = key + "={";
		return content.substring(content.indexOf(prefix) + prefix.length(),
				content.lastIndexOf("}"));
	}

	/**
	 * @return the resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
}
