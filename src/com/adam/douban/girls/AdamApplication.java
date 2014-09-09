package com.adam.douban.girls;

import com.adam.douban.girls.engine.MyVolley;

import android.app.Application;

public class AdamApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		MyVolley.init(getApplicationContext());
	}

}
