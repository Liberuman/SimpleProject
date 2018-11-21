package com.sxu.basecomponent.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;

/*******************************************************************************
 * Description: 用户会话管理
 *
 * Author: Freeman
 *
 * Date: 2018/11/21
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class UserManager<T> {

	private T userInfo;
	private boolean isLogin;

	private final String KEY_USER_INFO = "key_user_info";

	private UserManager() {
		loadUserInfo();
	}

	public static UserManager getInstance() {
		return SingletonHolder.instance;
	}

	private void loadUserInfo() {
		userInfo = new Gson().fromJson(PreferencesManager.getString(KEY_USER_INFO),
				new TypeToken<T>(){}.getType());
		if (userInfo != null) {
			isLogin = true;
		}
	}

	public boolean isLogin() {
		return isLogin;
	}

	public T getUserInfo() {
		if (userInfo == null) {
			loadUserInfo();
		}
		return userInfo;
	}

	public void setUserInfo(T userInfo) {
		this.userInfo = userInfo;
		if (userInfo != null) {
			isLogin = true;
		}
		PreferencesManager.putString(KEY_USER_INFO, new Gson().toJson(userInfo));
	}

	public void clearUserInfo() {
		isLogin = false;
		userInfo = null;
		PreferencesManager.putString(KEY_USER_INFO, "");
	}

	private static class SingletonHolder {
		public static final UserManager instance = new UserManager();
	}
}
