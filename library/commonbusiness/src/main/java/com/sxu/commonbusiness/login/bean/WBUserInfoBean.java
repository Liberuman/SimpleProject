package com.sxu.commonbusiness.login.bean;

import java.io.Serializable;

/*******************************************************************************
 * Description: 微博用户信息
 *
 * Author: Freeman
 *
 * Date: 2018/8/31
 *******************************************************************************/
public class WBUserInfoBean implements Serializable {

	public String id;
	public String screen_name;
	public String name;
	public int province;
	public int city;
	public String location;
	public String description;
	public String url;
	public String profile_image_url;
	public String domain;
	public String gender;
	public int followers_count;
	public int friends_count;
	public int statuses_count;
	public int favourites_count;
	public String created_at;
	public boolean following;
	public boolean allow_all_act_msg;
	public boolean geo_enabled;
}
