package com.sxu.commonbusiness.share;

import android.app.Activity;

import com.sxu.commonbusiness.share.instance.QQBaseShareInstance;
import com.sxu.commonbusiness.share.instance.BaseShareInstance;
import com.sxu.commonbusiness.share.instance.WeChatBaseShareInstance;
import com.sxu.commonbusiness.share.instance.WeiboBaseShareInstance;

/*******************************************************************************
 * Description: 分享管理类
 *
 * Author: Freeman
 *
 * Date: 2018/07/12
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ShareManager {

	private String title;
	private String desc;
	private String iconUrl;
	private String url;
	private Activity activity;

	private static ShareManager instance;
	private BaseShareInstance shareInstance = null;

	private ShareManager() {

	}

	public static ShareManager getInstance() {
		if (instance == null) {
			instance = new ShareManager();
		}

		return instance;
	}

	public void init(Activity activity, String title, String desc, String iconUrl, String url) {
		this.activity = activity;
		this.title = title;
		this.desc = desc;
		this.iconUrl = iconUrl;
		this.url = url;
	}

	public void share(int flowId, ShareListener listener) {
		switch (flowId) {
			case ShareConstants.SHARE_BY_WECHAT:
				shareInstance = new WeChatBaseShareInstance(activity, flowId, listener);
				break;
			case ShareConstants.SHARE_BY_WECHAT_MOMENT:
				shareInstance = new WeChatBaseShareInstance(activity, flowId, listener);
				break;
			case ShareConstants.SHARE_BY_MINI_PROGRAM:
				shareInstance = new WeChatBaseShareInstance(activity, ShareConstants.SHARE_BY_MINI_PROGRAM, listener);
				break;
			case ShareConstants.SHARE_BY_WEIBO:
				shareInstance = new WeiboBaseShareInstance(activity, listener);
				break;
			case ShareConstants.SHARE_BY_QQ:
				shareInstance = new QQBaseShareInstance(activity, ShareConstants.SHARE_BY_QQ, listener);
				break;
			case ShareConstants.SHARE_BY_QQ_ZONE:
				shareInstance = new QQBaseShareInstance(activity, ShareConstants.SHARE_BY_QQ_ZONE, listener);
				break;
			default:
				break;
		}

		if (shareInstance != null) {
			shareInstance.onShare(title, desc, iconUrl, url);
		}
	}

	public BaseShareInstance getShareInstance() {
		return shareInstance;
	}
}
