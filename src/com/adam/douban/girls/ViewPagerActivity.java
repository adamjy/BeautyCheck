package com.adam.douban.girls;

import java.util.ArrayList;

import com.adam.douban.girls.engine.MyVolley;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class ViewPagerActivity extends Activity {
	private ViewPager vp;
	private static final String STATE_POSITION = "STATE_POSITION";
	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_viewpager);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("value");
		ArrayList<String> mUrls = bundle.getStringArrayList("urls");
		int position = bundle.getInt("position", 0);
		
		if (savedInstanceState != null) {
			position = savedInstanceState.getInt(STATE_POSITION);
		}
		
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		vp = (ViewPager) findViewById(R.id.vp);
		vp.setAdapter(new MyViewPagerAdapter(mUrls));
		vp.setCurrentItem(position);
	}
	
	private class MyViewPagerAdapter extends PagerAdapter {
		private LayoutInflater inflater;
		private ArrayList<String> mUrls;
		
		public MyViewPagerAdapter(ArrayList<String> mUrls) {
			this.mUrls = mUrls;
			inflater = getLayoutInflater();
		}
		
		@Override
		public int getCount() {
			return mUrls.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			MyVolley.getImage(mUrls.get(position), imageView, 
					R.drawable.ic_default, 
					R.drawable.ic_error);
			
			view.addView(imageLayout, 0);
			return imageLayout;
		}
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, vp.getCurrentItem());
	}
}
