package com.sxu.baselibrary.datasource.http.impl;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * Description: 解决需要Object类型时，后台返回Array的情况
 *
 * Author: Freeman
 *
 * Date: 2018/3/15
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class ObjectTypeAdapter2<T> extends TypeAdapter<List<T>> {

	@Override
	public List<T> read(JsonReader in) throws IOException {
		JsonToken token = in.peek();

		switch (token) {
			case BEGIN_ARRAY:
				List<T> list = new ArrayList<>();
				in.beginArray();
				while (in.hasNext()) {
					//list.add(in.next);
				}
				in.endArray();
				return list.size() > 0 ? list : null;
			default:
				throw new IllegalStateException();
		}
	}

	@Override
	public void write(JsonWriter out, List<T> value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
	}
}
