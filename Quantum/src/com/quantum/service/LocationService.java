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

import java.util.Date;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.quantum.bean.Location;
import com.quantum.db.DbManage;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public final class LocationService extends Service {

	private static final String SERVICE_NAME = "com.quantum.service.LocationService";

	static Context mcontext;

	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();

	public LocationService() {

	}

	@Override
	public void onCreate() {
		initLocation();
	}

	@Override
	public void onDestroy() {
		mLocClient.stop();
	}

	public static void startService(final Context context) {
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Intent intent = LocationService.getIntent();
				context.startService(intent);
				mcontext = context;
			}
		});
		serviceThread.start();
	}

	public static void stopService() {
		Intent intent = LocationService.getIntent();
		mcontext.stopService(intent);
	}

	public static Intent getIntent() {
		return new Intent(SERVICE_NAME);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void initLocation() {
		mLocClient = new LocationClient(getApplication());
		mLocClient.registerLocationListener(myListener);
		setLocationOption();
		mLocClient.start();
	}

	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setScanSpan(900);
		mLocClient.setLocOption(option);
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			if (location.getLocType() != 161) {
				mLocClient.requestOfflineLocation();
				return;
			}

			if (location.getLocType() != 62)
				updateLocation(location);
		}
	}

	private void updateLocation(BDLocation location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		double radius = location.getRadius();
		String adds = location.getAddrStr();

		Location loc = new Location();
		loc.setLat(lat);
		loc.setLng(lng);
		loc.setRadius(radius);
		loc.setAdds(adds);
		loc.setTime(new Date());
		DbManage.saveLocation(loc);
		
		stopService();
	}
}
