package com.sxu.mainmodule.setting;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.sxu.basecomponent.activity.BaseWebViewActivity;
import com.sxu.basecomponent.manager.PathManager;
import com.sxu.basecomponent.uiwidget.BottomDialog;
import com.sxu.basecomponent.uiwidget.CommonDialog;
import com.sxu.baselibrary.commonutils.FileUtil;
import com.sxu.baselibrary.commonutils.LaunchUtil;
import com.sxu.baselibrary.commonutils.NetworkUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;
import com.sxu.baselibrary.datasource.disk.ACache;
import com.sxu.baselibrary.datasource.preference.PreferencesManager;
import com.sxu.commonbusiness.share.activity.ShareActivity;
import com.sxu.imageloader.ImageLoaderManager;
import com.sxu.permission.CheckPermission;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * Description: 通用的设置功能
 *
 * Author: Freeman
 *
 * Date: 2019/3/21
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class SettingPresenter {

	private Context context;

	/**
	 * 记录开发环境
	 */
	public final static String KEY_IS_PRODUCE_ENVIRONMENT = "key_is_product_environment";

	public SettingPresenter(Context context) {
		this.context = context;
	}

	/**
	 * 关于
	 * @param title
	 * @param url
	 */
	public void aboutUs(String title, String url) {
		BaseWebViewActivity.enter(context, title, url);
	}

	/**
	 * 联系我们
	 * @param telNumber
	 */
	public void contactUs(final String telNumber) {
		CommonDialog dialog = new CommonDialog();
		dialog.setTitle("是否拨打客服电话");
		dialog.setMessage(telNumber)
			.setCancelButtonClickListener("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.setOkButtonClickListener("拨打", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					realCallPhone(context, telNumber);
				}
			}).show((FragmentActivity) context);
	}

	@CheckPermission(permissions = {Manifest.permission.CALL_PHONE}, permissionDesc = "没有权限无法拨打电话",
			settingDesc = "快去设置中开启拨打电话的权限")
	private void realCallPhone(Context context, String telNumber) {
		LaunchUtil.openPhone(context, telNumber);
	}

	/**
	 * 帮助页面
	 * @param title
	 * @param url
	 */
	public void askHelp(String title, String url) {
		BaseWebViewActivity.enter(context, title, url);
	}

	/**
	 * 分享APP
	 * @param title
	 * @param desc
	 * @param url
	 * @param iconUrl
	 */
	public void shareApp(String title, String desc, String url, String iconUrl) {
		ShareActivity.enter(context, title, desc, url, iconUrl);
	}

	/**
	 * 评价APP
	 */
	public void appraiseApp() {
		if (!NetworkUtil.isConnected(context)) {
			ToastUtil.show("请先连接网络~");
			return;
		}

		LaunchUtil.openAppMarket(context);
	}

	/**
	 * 更新版本
	 */
	public void updateVersion() {

	}

	/**
	 * 切换开发环境
	 */
	public void switchEnvironment(final TextView textView) {
		BottomDialog dialog = new BottomDialog();
		List<String> menuList = new ArrayList<>();
		menuList.add("正式环境");
		menuList.add("测试环境");
		dialog.setMenuList(menuList);
		dialog.setOnItemListener(new BottomDialog.OnItemClickListener() {
			@Override
			public void onItemClicked(String value, int position) {
				if (position == 0) {
					PreferencesManager.putBoolean(KEY_IS_PRODUCE_ENVIRONMENT, true);
				} else if (position == 1) {
					PreferencesManager.putBoolean(KEY_IS_PRODUCE_ENVIRONMENT, false);
				}
				textView.setText(value);
			}
		});
		dialog.show((FragmentActivity) context);
	}

	/**
	 * 清除缓存
	 */
	public void clearCache() {
		int totalSize = getTotalCacheSize();
		if (totalSize == 0) {
			ToastUtil.show("暂无缓存可清理~");
			return;
		}

		CommonDialog dialog = new CommonDialog();
		dialog.setTitle("清除缓存");
		dialog.setMessage("确认要清除缓存么？");
		dialog.show(((FragmentActivity)context).getSupportFragmentManager());
		dialog.setOkButtonClickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				realClearCache();
			}
		});
	}

	public int getTotalCacheSize() {
		long totalCacheSize = ACache.get(context).getCacheSize();
		totalCacheSize += FileUtil.getFileSize(PathManager.getInstance(context).getDataCachePath());
		totalCacheSize += FileUtil.getFileSize(PathManager.getInstance(context).getImageCachePath());
		//totalCacheSize += ImageLoaderManager.getInstance().getCacheSize();
		int totalSize = (int) (totalCacheSize / (1024 * 1024));

		return totalSize;
	}

	private void realClearCache() {
		// 清理ACache
		ACache aCache = ACache.get(context);
		aCache.clear();

		// 清理APP缓存目录
		FileUtil.deleteFile(PathManager.getInstance(context).getDataCachePath());
		FileUtil.deleteFile(PathManager.getInstance(context).getImageCachePath());

		// 清理图片缓存
		//ImageLoaderManager.getInstance().clear();
	}
}
