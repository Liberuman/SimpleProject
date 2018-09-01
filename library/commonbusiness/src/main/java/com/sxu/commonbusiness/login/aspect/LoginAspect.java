package com.sxu.commonbusiness.login.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

	@Pointcut("execution(@com.sxu.commonbusiness.login.aspect.CheckLogin * *(..))")
	public void executionCheckLogin() {

	}

	@Around("executionCheckLogin()")
	public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		CheckLogin checkLogin = signature.getMethod().getAnnotation(CheckLogin.class);
		if (checkLogin != null) {
			if (loginListener != null) {
				loginListener.onLogin(joinPoint);
			} else {
				joinPoint.proceed();
			}
		}

		return null;
	}

	public static void setOnLoginListener(OnLoginListener listener) {
		loginListener = listener;
	}

	public interface OnLoginListener {
		void onLogin(ProceedingJoinPoint joinPoint);
	}
}
