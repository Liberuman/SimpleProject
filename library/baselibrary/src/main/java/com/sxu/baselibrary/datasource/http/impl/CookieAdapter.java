package com.sxu.baselibrary.datasource.http.impl;

import java.io.Serializable;
import java.net.HttpCookie;

import okhttp3.Cookie;

/*******************************************************************************
 * Description: 兼容okhttp2和okhttp3的Cookie对象
 *
 * Author: Freeman
 *
 * Date: 2018/10/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public final class CookieAdapter implements Serializable {

	private String name;
	private String value;
	private String domain;
	private String path;
	private boolean isExpires;
	private boolean secure;
	private boolean httpOnly;
	private boolean persistent;
	private boolean hostOnly;

	public CookieAdapter(Cookie cookie) {
		this.name = cookie.name();
		this.value = cookie.value();
		this.domain = cookie.domain();
		this.path = cookie.path();
		this.secure = cookie.secure();
		this.httpOnly = cookie.httpOnly();
		this.persistent = cookie.persistent();
		this.hostOnly = cookie.hostOnly();
	}

	public CookieAdapter(HttpCookie cookie) {
		this.name = cookie.getName();
		this.value = cookie.getValue();
		this.domain = cookie.getDomain();
		this.path = cookie.getPath();
		this.secure = cookie.getSecure();
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public boolean isExpires() {
		return isExpires;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPath() {
		return path;
	}

	public boolean isSecure() {
		return secure;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public boolean isHostOnly() {
		return hostOnly;
	}
}
