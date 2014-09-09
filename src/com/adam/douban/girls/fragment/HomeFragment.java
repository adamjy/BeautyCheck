package com.adam.douban.girls.fragment;

import java.util.ArrayList;
import java.util.List;

import com.adam.douban.girls.MainActivity;
import com.adam.douban.girls.R;
import com.adam.douban.girls.ViewPagerActivity;
import com.adam.douban.girls.base.BaseFragment;
import com.adam.douban.girls.engine.MyVolley;
import com.adam.douban.girls.utils.ConstantValues;
import com.adam.douban.girls.utils.NetTools;
import com.adam.douban.girls.utils.PromptManager;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends BaseFragment implements OnClickListener {
	protected static final String TAG = "HomeFragment";
	private View view;
	private ListView lvLeft;
	private ListView lvRight;
	private TextView tvPageIndex;
	private int mPageIndex;
	private Button btnPrev;
	private Button btnNext;
	private ImageView ivRefresh;
	private ImageView ivMenu;
	private String baseUrl;
	private static ListViewAdapter mAdapterLeft;
	private static ListViewAdapter mAdapterRight;
	private static ArrayList<String> mUrlsLeft;
	private static ArrayList<String> mUrlsRight;
	private static boolean mIsLastPage;
	private boolean mIsRefresh = false;

	/**
	 * 设置分类地址baseurl
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		mPageIndex = 0;
		mIsLastPage = false;
		this.baseUrl = baseUrl;
	}
	
	/**
	 * menu选择分类后，通知adapter更新数据
	 */
	public void notifyAdatperChange() {
		if(mAdapterLeft != null && mAdapterRight != null) {
			mPageIndex = 0;
			tvPageIndex.setText("第" + (mPageIndex+1) + "页");
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2),true);
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2 + 1), false);
		}
	}

	@Override
	public void initData() {
		mUrlsLeft = new ArrayList<String>();
		mUrlsRight = new ArrayList<String>();
		
		mAdapterLeft = new ListViewAdapter(mUrlsLeft);
		mAdapterRight = new ListViewAdapter(mUrlsRight);
		
//		lvLeft.setAdapter(mAdapterLeft);
//		lvRight.setAdapter(mAdapterRight);
		lvLeft.setAdapter(mAdapterLeft);
		lvRight.setAdapter(mAdapterRight);
		
		lvLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ctx, ViewPagerActivity.class);
				Bundle b = new Bundle();
				b.putStringArrayList("urls", mUrlsLeft);
				b.putInt("position", position);
				intent.putExtra("value", b);
				ctx.startActivity(intent);
			}
		});
		lvRight.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ctx, ViewPagerActivity.class);
				Bundle b = new Bundle();
				b.putStringArrayList("urls", mUrlsRight);
				b.putInt("position", position);
				intent.putExtra("value", b);
				ctx.startActivity(intent);
			}
		});
		
		baseUrl = ConstantValues.baseUrls[0];
		mPageIndex = 0;
		PromptManager.showProgressDialog(ctx);
		startRequest(getImamgeUrl(baseUrl, mPageIndex*2),true);
		startRequest(getImamgeUrl(baseUrl, mPageIndex*2 + 1), false);
	}
	
	/**
	 * Start to request
	 * @param url
	 * @param ifLeft whether is left listview request data
	 */
	public void startRequest(String url, final boolean isLeft) {
		StringRequest request = new StringRequest(
				url, new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if(mIsRefresh) {
							ivRefresh.setVisibility(View.VISIBLE);
							mIsRefresh = false;
						}
						PromptManager.closeProgressDialog();
						if(NetTools.getImgUrls(arg0).isEmpty()) {
							Toast.makeText(ctx, "已经是最后一页了~", 0).show();
							mIsLastPage = true;
							return;
						}
						if(isLeft) {
							mUrlsLeft.clear();
							mUrlsLeft.addAll(NetTools.getImgUrls(arg0));
							mAdapterLeft.notifyDataSetChanged();
							lvLeft.setSelection(0);
						} else {
							mUrlsRight.clear();
							mUrlsRight.addAll(NetTools.getImgUrls(arg0));
							mAdapterRight.notifyDataSetChanged();
							lvRight.setSelection(0);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						PromptManager.closeProgressDialog();
						Toast.makeText(ctx, "服务器错误", 0).show();
					}
				});
		MyVolley.addRequest(request);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		view = View.inflate(ctx, R.layout.home_content, null);
		lvLeft = (ListView) view.findViewById(R.id.home_lv_left);
		lvRight = (ListView) view.findViewById(R.id.home_lv_right);
		tvPageIndex = (TextView) view.findViewById(R.id.home_tv_page_index);
		
		btnPrev = (Button) view.findViewById(R.id.home_btn_prev);
		btnNext = (Button) view.findViewById(R.id.home_btn_next);
		ivMenu = (ImageView) view.findViewById(R.id.iv_titlebar);
		ivRefresh = (ImageView) view.findViewById(R.id.iv_refresh);
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		ivMenu.setOnClickListener(this);
		ivRefresh.setOnClickListener(this);
		
		return view;
	}

	private class ListViewAdapter extends BaseAdapter {
		private List<String> imgUrls = null;

		public ListViewAdapter(List<String> imgUrls) {
			super();
			this.imgUrls = imgUrls;
		}

		@Override
		public int getCount() {
			return imgUrls.size();
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
			if (convertView == null) {
				convertView = View
						.inflate(ctx, R.layout.item_listview, null);
			}
			
			MyVolley.getImage(imgUrls.get(position), (ImageView)convertView.findViewById(R.id.iv_item_lv), 
					R.drawable.ic_default, 
					R.drawable.ic_error);
			
			return convertView;
		}

	}
	
	/**
	 * Get real url of image
	 * @param baseurl
	 * @param pageIndex
	 * @return
	 */
	public String getImamgeUrl(String baseurl, int pageIndex) {
		return baseurl + "?p=" + pageIndex;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_btn_prev:
			if(mPageIndex == 0) {
				return;
			}
			mPageIndex--;
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2), true);
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2 + 1), false);
			tvPageIndex.setText("第" + (mPageIndex+1) + "页");
			mIsLastPage = false;
			break;
		case R.id.home_btn_next:
			if(mIsLastPage) {
				Toast.makeText(ctx, "已经是最后一页了~", 0).show();
			}
			mPageIndex++;
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2), true);
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2 + 1), false);
			tvPageIndex.setText("第" + (mPageIndex+1) + "页");
			break;
		case R.id.iv_titlebar:
			((MainActivity)ctx).getSm().toggle();
			break;
		case R.id.iv_refresh:
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2), true);
			startRequest(getImamgeUrl(baseUrl, mPageIndex*2 + 1), false);
			ivRefresh.setVisibility(View.INVISIBLE);
			PromptManager.showRefreshDialog(ctx);
			mIsRefresh = true;
			break;
		}
		
	}

}
