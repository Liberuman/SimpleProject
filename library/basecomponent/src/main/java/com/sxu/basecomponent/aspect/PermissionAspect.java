package com.sxu.basecomponent.aspect;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.sxu.basecomponent.activity.PermissionActivity;
import com.sxu.basecomponent.annotation.CheckPermission;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.PermissionUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/*******************************************************************************
 * Description: 权限切面
 *
 * Author: Freeman
 *
 * Date: 2018/8/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
@Aspect
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
			PermissionActivity.setOnPermissionRequestListener(new PermissionUtil.OnPermissionRequestListener() {
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

				}

				@Override
				public void onDenied() {

				}
			});

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);
			PermissionActivity.enter(context, checkPermission.permissions(), checkPermission.permissionDesc(),
					checkPermission.settingDesc());
		}

		return null;
	}
}
