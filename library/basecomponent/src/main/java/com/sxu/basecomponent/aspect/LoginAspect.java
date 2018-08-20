package com.sxu.basecomponent.aspect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sxu.basecomponent.annotation.CheckLogin;
import com.sxu.baselibrary.commonutils.LogUtil;
import com.sxu.baselibrary.commonutils.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/*******************************************************************************
 * Description: 登录切面
 *
 * Author: Freeman
 *
 * Date: 2018/8/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
@Aspect
public class LoginAspect {

	private static OnLoginListener loginListener;

	@Pointcut("execution(@com.sxu.basecomponent.annotation.CheckLogin * *(..))")
	public void executionCheckLogin() {

	}

	@Around("executionCheckLogin()")
	public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
		Log.i("out", "=============onCheckLogin begin");
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		CheckLogin checkLogin = signature.getMethod().getAnnotation(CheckLogin.class);
		if (checkLogin != null) {
			if (loginListener != null) {
				loginListener.onLogin(joinPoint);
			} else {
				joinPoint.proceed();
			}
		}
		Log.i("out", "=============onCheckLogin end");

		return null;
	}

	public static void setOnLoginListener(OnLoginListener listener) {
		loginListener = listener;
	}

	public interface OnLoginListener {
		void onLogin(ProceedingJoinPoint joinPoint);
	}
}
