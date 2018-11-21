package com.sxu.module1;

import com.sxu.basecomponent.mvp.BasePresenter;
import com.sxu.basecomponent.mvp.BaseView;

/*******************************************************************************
 * Description: 
 *
 * Author: Freeman
 *
 * Date: 2018/10/11
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class TestContract {

	private static boolean flag = false;
	private TestContract() {

	}
	interface TestView extends BaseView {

	}

	interface TestPresenter extends BasePresenter {

	}
}

