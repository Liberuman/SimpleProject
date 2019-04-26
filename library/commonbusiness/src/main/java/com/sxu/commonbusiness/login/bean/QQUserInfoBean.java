package com.sxu.commonbusiness.login.bean;

import java.io.Serializable;

/*******************************************************************************
 * Description: QQ用户信息
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public class QQUserInfoBean implements Serializable {

	/**
	 * 返回码
	 */
	public int  ret;
	/**
	 * 返回码含义
	 */
	public String msg;

	public String openid;
	public String token;
	public String expires;
	public String nickname;
	public String gender;
	public String year;

	/**
	 * 用户头像 size=30x30
	 */
	public String figureurl;
	/**
	 * 用户头像 size=50x50
	 */
	public String figureurl_1;
	/**
	 * 用户头像 size=100x100
	 */
	public String figureurl_2;
	/**
	 * 用户头像 size=40x40
	 */
	public String figureurl_qq_1;
	/**
	 * 用户头像 size=100x100
	 */
	public String figureurl_qq_2;
}
