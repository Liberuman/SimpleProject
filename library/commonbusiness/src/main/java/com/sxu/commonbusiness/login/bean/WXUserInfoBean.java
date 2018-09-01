package com.sxu.commonbusiness.login.bean;

import java.io.Serializable;

/*******************************************************************************
 * Description: 微信用户的信息
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public class WXUserInfoBean implements Serializable {

	/**
	 * 1表示男性，2表示女性
	 */
	public int sex; // 1表示男性，2表示女性
	/**
	 * 用户统一标识，针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
	 */
	public String unionid;

	public String openid;
	public String nickname;
	public String city;
	public String province;
	public String country;
	public String headimgurl;
}
