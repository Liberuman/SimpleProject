package com.sxu.basecomponent.manager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sxu.basecomponent.R;
import com.sxu.baselibrary.commonutils.CollectionUtil;
import com.sxu.baselibrary.commonutils.LogUtil;

import java.util.List;

/*******************************************************************************
 * Description: Fragment的管理
 *
 * Author: Freeman
 *
 * Date: 2018/12/16
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class FragmentHandlerManager {

	private Fragment parentFragment;
	private FragmentActivity context;

	public FragmentHandlerManager(FragmentActivity context) {
		this(context, null);
	}

	public FragmentHandlerManager(FragmentActivity context, Fragment parentFragment) {
		if (context == null) {
			return;
		}
		this.context = context;
		this.parentFragment = parentFragment;
	}

	/**
	 * 添加Fragment操作
	 */
	public final static int OPERATOR_ADD = 0;
	/**
	 * 替换Fragment操作
	 */
	public final static int OPERATOR_REPLACE = 1;
	/**
	 * 移除Fragment操作
	 */
	public final static int OPERATOR_REMOVE = 2;

	public void addFragment(int containerId, Fragment fragment) {
		operatorFragment(containerId, true, OPERATOR_ADD, fragment);
	}

	public void addFragment(int containerId, boolean addToStack, Fragment fragment) {
		operatorFragment(containerId, addToStack, OPERATOR_ADD, fragment);
	}

	public void replaceFragment(int containerId, Fragment fragment) {
		operatorFragment(containerId, true, OPERATOR_REPLACE, fragment);
	}

	public void replaceFragment(int containerId, boolean addToStack, Fragment fragment) {
		operatorFragment(containerId, addToStack, OPERATOR_REPLACE, fragment);
	}

	public void removeFragment(int containerId, Fragment fragment) {
		operatorFragment(containerId, true, OPERATOR_REMOVE, fragment);
	}

	public void removeFragment(int containerId, boolean addToStack, Fragment fragment) {
		operatorFragment(containerId, addToStack, OPERATOR_REMOVE, fragment);
	}

	private void operatorFragment(int containerId, boolean addToStack, int operator, Fragment fragment) {
		if (context == null) {
			return;
		}

		FragmentManager fm = (parentFragment != null) ? parentFragment.getChildFragmentManager()
				: context.getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		switch (operator) {
			case OPERATOR_ADD:
				List<Fragment> fragmentList = fm.getFragments();
				transaction.setCustomAnimations(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300,
						R.anim.anim_translate_left_in_300, R.anim.anim_translate_right_out_300)
						.add(containerId, fragment);
				if (!CollectionUtil.isEmpty(fragmentList)) {
					transaction.hide(fragmentList.get(fragmentList.size() - 1));
				}
				break;
			case OPERATOR_REPLACE:
				transaction.setCustomAnimations(R.anim.anim_translate_right_in_300, R.anim.anim_translate_left_out_300,
						R.anim.anim_translate_left_in_300, R.anim.anim_translate_right_out_300)
						.replace(containerId, fragment);
				break;
			case OPERATOR_REMOVE:
				if (fm.findFragmentById(fragment.getId()) != null || fm.findFragmentByTag(fragment.getTag()) != null) {
					transaction.remove(fragment);
				}
				break;
			default:
				throw new IllegalArgumentException("can't support operator");
		}

		if (addToStack) {
			transaction.addToBackStack(null);
		}
		transaction.commitAllowingStateLoss();
	}

	/**
	 * Activity重启时恢复Fragment
	 */
	public void resumeFragment() {
		FragmentManager fm = context.getSupportFragmentManager();
		if (fm != null) {
			for (int i = 0, size = fm.getFragments().size(); i < size; i++) {
				Fragment fragment = fm.getFragments().get(i);
				fragment.onAttach((Context)context);
				resumeChildFragment(fragment);
			}
		}
	}

	/**
	 * 恢复嵌套的Fragment
	 */
	public void resumeChildFragment(Fragment fragment) {
		FragmentManager fm = fragment.getFragmentManager();
		if (fm == null) {
			return;
		}

		for (int i = 0, size = fm.getFragments().size(); i < size; i++) {
			fm.getFragments().get(i).onAttach((Context) context);
		}
	}
}
