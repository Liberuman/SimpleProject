package com.sxu.baselibrary.datasource.http.impl;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/*******************************************************************************
 * Description: 空数据的异常处理
 *
 * Author: Freeman
 *
 * Date: 2018/3/19
 *
 * Copyright: all rights reserved by Freeman
 *******************************************************************************/

class NullOnEmptyConverterFactory extends Converter.Factory {

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
		final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
		return new Converter<ResponseBody, Object>() {
			@Override
			public Object convert(ResponseBody value) throws IOException {
				if (value.contentLength() == 0) {
					return null;
				}

				try {
					JSONObject object = new JSONObject(value.string());
					Object data = object.get("data");
					if (data == null || data instanceof List && ((List) data).size() == 0) {
						object.remove("data");
						value = ResponseBody.create(value.contentType(), object.toString());
					}
				} catch (Exception e) {

				}

				return delegate.convert(value);
			}
		};
	}
}
