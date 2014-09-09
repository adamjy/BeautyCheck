package com.adam.douban.girls.base;

import com.adam.douban.girls.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Base fragment
 * @description
 * @author AdamJY
 * 2014下午3:37:33
 */
public abstract class BaseFragment extends Fragment {

	protected Context ctx;
	protected SlidingMenu slidingMenu;
	// Whether data has been loaded once
	protected boolean mIsLoaded = false;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	public abstract void initData();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = getActivity();
		slidingMenu = ((MainActivity)ctx).getSlidingMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = initView(inflater);
		return view;
	}

	public abstract View initView(LayoutInflater inflater);
	
}
