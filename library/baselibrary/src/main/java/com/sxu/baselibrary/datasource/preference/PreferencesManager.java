package com.sxu.baselibrary.datasource.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.sxu.baselibrary.commonutils.BaseContentProvider;

import java.util.Set;

/*******************************************************************************
 * Description: SharedPreferences的封装类
 * 
 * 对于单个或少量数据, 提倡使用putXXX保存数据,
 * 如果保存多个数据,提倡使用putXXXNoCommit进行保存, 最后调用commit进行持久化。
 *
 * Author: Freeman
 *
 * Date: 2018/6/20
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class PreferencesManager {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String PREFERENCE_NAME = "SXU";
    private static Context context;

    static {
        init(BaseContentProvider.context, PREFERENCE_NAME);
    }

    public static void init(Context context) {
        init(context, PREFERENCE_NAME);
    }

    public static void init(Context _context, String preferenceName) {
        context = _context;
        PREFERENCE_NAME = preferenceName;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void putString(String name, String value) {
        editor.putString(name, value);
        editor.apply();
    }

    public static void putStringNoCommit(String name, String value) {
        editor.putString(name, value);
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static String getString(String key,String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public static void putBoolean(String name, boolean value) {
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static void putBooleanNoCommit(String name, boolean value) {
        editor.putBoolean(name, value);
    }

    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static void putInt(String name, int value) {
        editor.putInt(name, value);
        editor.apply();
    }

    public static void putIntNoCommit(String name, int value) {
        editor.putInt(name, value);
    }

    public static int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public static void putLong(String name, long value) {
        editor.putLong(name, value);
        editor.apply();
    }

    public static void putLongNoCommit(String name, long value) {
        editor.putLong(name, value);
    }

    public static long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public static void putFloat(String name, float value) {
        editor.putFloat(name, value);
        editor.apply();
    }

    public static void putFloatNoCommit(String name, float value) {
        editor.putFloat(name, value);
    }

    public static float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public static void putStringSet(String name, Set<String> value) {
        editor.putStringSet(name, value);
        editor.apply();
    }

    public static void putStringSetNoCommit(String name, Set<String> value) {
        editor.putStringSet(name, value);
    }

    public static Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, null);
    }

    public static void removeKey(String key) {
        editor.remove(key);
        editor.apply();
    }

    public static void commit() {
        editor.apply();
    }
}
