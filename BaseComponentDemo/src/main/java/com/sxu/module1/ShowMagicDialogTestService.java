package com.sxu.module1;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sxu.basecomponent.uiwidget.MagicDialog;
import com.sxu.baselibrary.commonutils.DisplayUtil;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/12/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class ShowMagicDialogTestService extends Service {

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			TextView textView = new TextView(ShowMagicDialogTestService.this);
			textView.setGravity(Gravity.CENTER);
			textView.setMinimumHeight(DisplayUtil.dpToPx(100));
			textView.setText("测试全局弹框");
			MagicDialog.show(ShowMagicDialogTestService.this, textView, false);
			handler.sendEmptyMessageDelayed(0, 4000);
		}
	};

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handler.sendEmptyMessageDelayed(0, 3000);
		return super.onStartCommand(intent, flags, startId);
	}
}
