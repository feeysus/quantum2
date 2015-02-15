/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quantum.service;

import com.quantum.CustomApplication;
import com.quantum.bean.Content;
import com.quantum.bean.Location;
import com.quantum.db.DbManage;
import com.quantum.utils.Http;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public final class WeatherService extends Service {

	private static final String SERVICE_NAME = "com.quantum.service.WeatherService";
	private static Content content;
	static Context mcontext;

	public WeatherService() {

	}

	@Override
	public void onCreate() {
		if ("local".equals(content.getMode())) {
			Location loc = (Location) DbManage.findFirstByBean(Location.class);
			
			String where = loc.getAdds();
			String url = content.getUrl() + "&location="
					+ getCityName(where);
			Http.Get(url, content);
		} else
			Http.Get(content.getUrl(), null);

		stopService();
	}

	private String getCityName(String where) {
		int sheng = where.indexOf("省");
		int shi = where.indexOf("市");
		int xian = where.indexOf("县");
		if (xian > 0)
			if (shi > 0)
				where = where.substring(shi + 1, xian);
			else
				where = where.substring(sheng + 1, xian);
		else if (shi > 0)
			where = where.substring(sheng + 1, shi);
		else
			where = where.substring(0, sheng);
		return where;
	}

	@Override
	public void onDestroy() {

	}

	public static void startService(final Context context, final Content con) {
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Intent intent = WeatherService.getIntent();
				context.startService(intent);
				mcontext = context;
				content = con;
			}
		});
		serviceThread.start();
	}

	public static void stopService() {
		Intent intent = WeatherService.getIntent();
		mcontext.stopService(intent);
	}

	public static Intent getIntent() {
		return new Intent(SERVICE_NAME);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private CustomApplication getApp() {
		return (CustomApplication) mcontext.getApplicationContext();
	}
}
