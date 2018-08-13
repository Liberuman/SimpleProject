package com.sxu.baselibrary.datasource.http.impl;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sxu.baselibrary.datasource.http.bean.ResponseBean;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*******************************************************************************
 * Description: 容错性的数据解析Adapter
 *
 * Author: Freeman
 *
 * Date: 2018/3/29
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class WrapTypeAdapters {

	/**
	 * 解决对于Int类型的数据，服务端返回String类型的问题
	 */
	public static class IntegerAdapter extends TypeAdapter<Integer> {
		@Override
		public Integer read(JsonReader in) {
			try {
				if (in.peek() == JsonToken.NULL) {
					in.nextNull();
					return 0;
				}
				return in.nextInt();
			} catch (Exception e) {
				try {
					in.skipValue();
				} catch (Exception e1) {

				}
			}

			return 0;
		}

		@Override
		public void write(JsonWriter out, Integer value) throws IOException {
			out.value(value);
		}
	}

	/**
	 * 将服务端返回的null值替换为""
	 */
	public static class StringAdapter extends TypeAdapter<String> {

		@Override
		public String read(JsonReader in) throws IOException {
			JsonToken peek = in.peek();
			if (peek == JsonToken.NULL) {
				in.nextNull();
				return "";
			}

			if (peek == JsonToken.BOOLEAN) {
				return Boolean.toString(in.nextBoolean());
			}
			return in.nextString();
		}
		@Override
		public void write(JsonWriter out, String value) throws IOException {
			out.value(value);
		}
	}

	/**
	 * 解决客户端需要Object类型，而服务端返回了Array类型的问题
	 */
	public static class CompatibleDeserializer<T> implements JsonDeserializer<ResponseBean<T>> {

		@Override
		public ResponseBean<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject object = json.getAsJsonObject();
			if (typeOfT instanceof ParameterizedType && ((ParameterizedType) typeOfT).getActualTypeArguments()[0]
					instanceof ParameterizedType) {
				object.remove("data");
			}

			return new Gson().fromJson(json, new TypeToken<ResponseBean<T>>(){}.getType());
		}
	}
}
