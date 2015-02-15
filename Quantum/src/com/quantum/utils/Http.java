package com.quantum.utils;

import java.util.Date;

import android.content.Context;
import android.content.Intent;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.quantum.bean.Constants;
import com.quantum.bean.Content;
import com.quantum.bean.Message;
import com.quantum.db.DbManage;

public class Http {
	static HttpUtils http = new HttpUtils();
	static Context context;

	public Http(Context context) {
		Http.context = context;
	}

	public static void Post(String url, String json) {
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("json", json);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {

					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	public static void Get(String url, final Content content) {

		// RequestParams params = new RequestParams();
		// params.addHeader("name", "value");
		// params.addQueryStringParameter("name", "value");

		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 10);
		http.send(HttpRequest.HttpMethod.GET, url,
		// params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						if (Utils.isGoodJson(responseInfo.result)) {
							content.setContent(responseInfo.result);
							content.setTime(new Date());
							// 删除相同项
							DbManage.delete(DbManage.findContentByType(content
									.getType()));
							DbManage.saveBean(content);

							Intent intent = new Intent(
									Constants.ACTION_SHOW_NOTIFICATION);
							context.sendBroadcast(intent);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}
}
