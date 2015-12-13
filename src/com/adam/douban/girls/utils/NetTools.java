package com.adam.douban.girls.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Debug;
import android.util.Log;

/**
 * Net util
 * @description
 * @author AdamJY
 * 2014下午5:57:16
 */
public class NetTools {
	private static final String TAG = NetTools.class.getSimpleName();
	private static boolean DEBUG = true;
	/**
	 * Scrap url from internet
	 * @param url Image html code
	 * @return
	 */
	public static List<String> getImgUrls(String html) {
		List<String> urlList = new ArrayList<String>();
		String reg = "<img.*src=(.*?)[^>]*?>";
		Pattern pImg = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcherImg = pImg.matcher(html);
		StringBuilder imgBuilder = new StringBuilder();
		
		while (matcherImg.find()) {
			imgBuilder.append(",").append(matcherImg.group());
			Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(matcherImg.group()); // 匹配src
			while (m.find()) {
				urlList.add(m.group(1));
			}
			imgBuilder.setLength(0);
		}
		if(urlList.size() > 2) {
			urlList.remove(0);
			urlList.remove(urlList.size() - 1);
		}
		for (String string : urlList) {
			if(DEBUG) LogHelper.d(TAG, "url: " + string);
		}
		return urlList;
	}
	
	/**
	 * Check net
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		boolean isWifi = isWifiConnection(context);
		boolean isAPN = isAPNConnection(context);

		if (!isWifi && !isAPN) {
			return false;
		}

		return true;
	}

	private static boolean isAPNConnection(Context context) {
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo!=null)
		{
			return networkInfo.isConnected();
		}
		return false;
	}

	private static boolean isWifiConnection(Context context) {
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo!=null)
		{
			return networkInfo.isConnected();
		}
		return false;
	}
}
