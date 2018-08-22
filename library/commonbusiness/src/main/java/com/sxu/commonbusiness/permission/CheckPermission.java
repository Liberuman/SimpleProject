package com.sxu.commonbusiness.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*******************************************************************************
 * Description: 权限注解
 *
 * Author: Freeman
 *
 * Date: 2018/8/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {

	String DEFAULT_PERMISSION_DESC = "此功能需要开启权限才可使用~";
	String DEFAULT_SETTING_DESC = "此功能需要开启权限才可使用, 请去设置中开启";

	/**
	 * 所需要申请的权限
	 * @return
	 */
	String[] permissions();

	/**
	 * 权限被拒绝时的提示信息
	 * @return
	 */
	String permissionDesc() default DEFAULT_PERMISSION_DESC;

	/**
	 * 设置权限的描述信息
	 * @return
	 */
	String settingDesc() default DEFAULT_SETTING_DESC;
}
