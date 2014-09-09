package com.adam.douban.girls.test;

import java.util.List;

import com.adam.douban.girls.engine.MyVolley;
import com.adam.douban.girls.utils.NetTools;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.test.AndroidTestCase;

public class NetTest extends AndroidTestCase {

	public void testGetUrl() {
		StringRequest request = new StringRequest(
				"http://www.dbmeizi.com/?p=0", new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						getImagUrl(arg0);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
		MyVolley.addRequest(request);
	}
	
	public void getImagUrl(String html) {
		List<String> imgUrls = NetTools.getImgUrls(html);
		System.out.println(imgUrls);
	}
}
