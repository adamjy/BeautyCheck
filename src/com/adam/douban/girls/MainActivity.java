package com.adam.douban.girls;

import com.adam.douban.girls.fragment.HomeFragment;
import com.adam.douban.girls.fragment.MenuFragment;
import com.adam.douban.girls.utils.NetTools;
import com.adam.douban.girls.utils.PromptManager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity {
	private SlidingMenu sm;
	private MenuFragment menu;
	private HomeFragment homeFramgent;
	// Operate double click event
	private long[] mHits = new long[2];
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set side menu
		setBehindContentView(R.layout.menu_frame);
		setContentView(R.layout.content_frame);
		
		sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setMode(SlidingMenu.LEFT);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		
		homeFramgent = new HomeFragment();
		menu = new MenuFragment();
		
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, homeFramgent, "HOME").commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.fm_left_menu, menu, "MEMU").commit();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!NetTools.checkNet(this)){
			PromptManager.showNoNetWork(this);
		}
	}

	/**
	 * When click menu item, refresh the data of home fragment
	 * @param baseUrl
	 */
	public void refreshContent(String baseUrl) {
		sm.toggle();
		HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HOME");
		fragment.setBaseUrl(baseUrl);
		fragment.notifyAdatperChange();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
			mHits[mHits.length - 1] = SystemClock.uptimeMillis();
			if(mHits[0] > SystemClock.uptimeMillis() - 1500) {
				// 退出系统
				return super.onKeyDown(keyCode, event);
			}
			Toast.makeText(this, "再按一次退出系统", 0).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public SlidingMenu getSm() {
		return sm;
	}
}
