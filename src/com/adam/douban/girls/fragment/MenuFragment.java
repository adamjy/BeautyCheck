package com.adam.douban.girls.fragment;

import com.adam.douban.girls.AboutActivity;
import com.adam.douban.girls.MainActivity;
import com.adam.douban.girls.R;
import com.adam.douban.girls.base.BaseFragment;
import com.adam.douban.girls.utils.ConstantValues;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MenuFragment extends BaseFragment implements OnItemClickListener {
	private View view;
	private ListView lvMenu;
	private LinearLayout llAbout;
	private ImageView ivAbout;
	private TextView tvAbout;
	private LeftMenuAdapter menuAdapter;
	
	@Override
	public void initData() {
	}

	@Override
	public View initView(LayoutInflater inflater) {
		view = View.inflate(ctx, R.layout.layout_left_menu, null);
		lvMenu = (ListView) view.findViewById(R.id.lv_left_menu);
		menuAdapter = new LeftMenuAdapter();
		lvMenu.setAdapter(menuAdapter);
		lvMenu.setOnItemClickListener(this);
		
		llAbout = (LinearLayout) view.findViewById(R.id.ll_about);
		ivAbout = (ImageView) view.findViewById(R.id.iv_about);
		tvAbout = (TextView) view.findViewById(R.id.tv_about);
		llAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, AboutActivity.class);
				ctx.startActivity(intent);
			}
		});
		
		return view;
	}

	private class LeftMenuAdapter extends BaseAdapter {
		
		private int selectedIndex;
		public void setSelectedIndex(int position) {
			selectedIndex = position;
			menuAdapter.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return ConstantValues.menuArr.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = View.inflate(ctx, R.layout.layout_item_menu, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
			tv.setText(ConstantValues.menuArr[position]);
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv_menu_item);
			
			if(position == selectedIndex) {
				convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
				tv.setTextColor(getResources().getColor(R.color.red));
				iv.setImageResource(R.drawable.menu_arr_select);
			} else {
				convertView.setBackgroundResource(R.drawable.transparent);
				tv.setTextColor(getResources().getColor(R.color.white));
				iv.setImageResource(R.drawable.menu_arr_normal);
			}
			
			return convertView;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		((MainActivity)ctx).refreshContent(ConstantValues.baseUrls[position]);
		menuAdapter.setSelectedIndex(position);
	}

}
