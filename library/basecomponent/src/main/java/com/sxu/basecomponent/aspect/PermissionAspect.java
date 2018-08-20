package com.sxu.basecomponent.aspect;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.sxu.basecomponent.activity.PermissionActivity;
import com.sxu.basecomponent.annotation.CheckPermission;
import com.sxu.baselibrary.commonutils.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/*******************************************************************************
 * Description: 权限切面
 *
 * Author: Freeman
 *
 * Date: 2018/8/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PermissionAspect {

	@Pointcut("execution(@com.sxu.basecomponent.annotation.CheckPermission * *(..))")
	public void executionCheckPermission() {

	}

	@Around("executionCheckPermission()")
	public Object checkPermission(final ProceedingJoinPoint joinPoint) {
		Context context = null;
		Object object = joinPoint.getThis();
		if (object instanceof Activity) {
			context = (Context) object;
		} else if (object instanceof Fragment) {
			context = ((Fragment) object).getActivity();
		} else if (object instanceof android.support.v4.app.Fragment) {
			context = ((android.support.v4.app.Fragment) object).getActivity();
		}

		if (context != null) {
			PermissionActivity.enter(context, new PermissionActivity.OnPermissionListener() {
				@Override
				public void onGranted() {
					try {
						joinPoint.proceed();
					} catch (Throwable throwable) {
						throwable.printStackTrace(System.err);
					}
				}

				@Override
				public void onCanceled() {
					MethodSignature signature = (MethodSignature) joinPoint.getSignature();
					CheckPermission checkPermission = signature.getMethod().getAnnotation(CheckPermission.class);
					if (checkPermission != null) {
						ToastUtil.show("申请权限被取消");
					}
				}

				@Override
				public void onDenied() {
					MethodSignature signature = (MethodSignature) joinPoint.getSignature();
					CheckPermission checkPermission = signature.getMethod().getAnnotation(CheckPermission.class);
					if (checkPermission != null) {
						ToastUtil.show("申请权限被拒绝");
					}
				}
			});
		}

		return null;
	}
}
