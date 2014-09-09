package com.adam.douban.girls.utils;

import com.adam.douban.girls.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Prompt operation manager
 */

public class PromptManager {
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.dbmz);
		dialog.setTitle(R.string.app_name);

		dialog.setMessage("请等候，数据加载中……");
		dialog.show();
	}

	public static void showRefreshDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.dbmz);
		dialog.setTitle(R.string.app_name);

		dialog.setMessage("请等候，正在刷新数据……");
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * When network is unavailable call this.
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.drawable.icon)
				//
				.setTitle(R.string.app_name)
				//
				.setMessage("当前无网络")
				.setPositiveButton("设置", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到系统的网络设置界面
						Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
						context.startActivity(intent);
					}
				}).setNegativeButton("知道了", null).show();
	}

	/**
	 * Show error dialog
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context)//
				.setIcon(R.drawable.icon)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton(context.getString(R.string.is_positive),
						null).show();
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}

}
