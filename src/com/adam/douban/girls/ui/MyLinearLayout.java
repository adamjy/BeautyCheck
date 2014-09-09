package com.adam.douban.girls.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
/**
 * Customize linearlayout, dispatch touch event to children
 * @description
 * @author AdamJY
 * 2014下午4:00:53
 */
public class MyLinearLayout extends LinearLayout {

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLinearLayout(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int width=getWidth()/getChildCount();
		int height = getHeight();
		int count=getChildCount();
		float eventX = event.getX();
		
		if (eventX<width){	// 滑动左边的 listView
			event.setLocation(width/2, event.getY());
			getChildAt(0).dispatchTouchEvent(event);
			return true;
			
		} else {
			float eventY = event.getY();
			if (eventY < height / 2) {
				event.setLocation(width / 2, event.getY());
				for (int i = 0; i < count; i++) {
					View child = getChildAt(i);
					try {
						child.dispatchTouchEvent(event);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				return true;
			} else {
				event.setLocation(width / 2, event.getY());
				try {
					getChildAt(1).dispatchTouchEvent(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
	}
}
