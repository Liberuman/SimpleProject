package com.sxu.module1;

import android.os.Handler;
import android.view.View;

import com.sxu.basecomponent.activity.CommonActivity;
import com.sxu.basecomponent.mvp.BasePresenter;
import com.sxu.basecomponent.mvp.BaseView;
import com.sxu.basecomponent.processor.RequestProcessor;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/10/11
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class BaseProgressActivityStylePresenter implements TestContract.TestPresenter {

	private TestContract.TestView view;

	public BaseProgressActivityStylePresenter(TestContract.TestView view) {
		this.view = view;
	}

	@Override
	public void requestData() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				view.requestComplete(RequestProcessor.MSG_LOAD_EMPTY);
			}
		}, 2000);
	}
}
