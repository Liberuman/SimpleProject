package com.sxu.baselibrary.commonutils;

/*******************************************************************************
 * Description: 根据两点的经纬度计算直线距离
 *
 * Author: Freeman
 *
 * Date: 2018/3/7
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class DistanceUtil {

	private static final double EARTH_RADIUS = 6378137.0;

	/**
	 * 根据经纬度获取距离
	 * @param longitude1
	 * @param latitude1
	 * @param longitude2
	 * @param latitude2
	 * @return
	 */
	public static double getDistance(double longitude1, double latitude1,
	                                 double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据经纬度获取距离
	 * @param longitude1
	 * @param latitude1
	 * @param longitude2
	 * @param latitude2
	 * @return
	 */
	public static String getDistanceString(double longitude1, double latitude1,
	                                       double longitude2, double latitude2){
		String distanceStr;
		double distance = getDistance(longitude1, latitude1, longitude2, latitude2);
		if(distance < 1000){
			distanceStr = "距离" + (int)distance + "米";
		}else{
			distanceStr = "距离" + (int)(distance/1000) + "公里";
		}

		return  distanceStr;
	}

	/**
	 * 根据经纬度获取距离
	 * @param longitude1
	 * @param latitude1
	 * @param longitude2
	 * @param latitude2
	 * @return
	 */
	public static String getDistanceString(String longitude1, String latitude1,
	                                       String longitude2, String latitude2){
		return getDistanceString(ConvertUtil.stringToDouble(longitude1), ConvertUtil.stringToDouble(latitude1),
				ConvertUtil.stringToDouble(longitude2), ConvertUtil.stringToDouble(latitude2));
	}
}
