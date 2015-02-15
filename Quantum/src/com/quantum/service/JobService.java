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

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;


import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.quantum.CustomApplication;
import com.quantum.bean.Content;
import com.quantum.db.DbManage;
import com.quantum.utils.Http;
import com.quantum.utils.HttpHelper;
import com.quantum.utils.QuartzManager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public final class JobService extends Service {

	private static final String SERVICE_NAME = "com.quantum.service.JobService";
	static Context mcontext;

	public JobService() {

	}

	@Override
	public void onCreate() {
		List<Content> list = (List<Content>) DbManage
				.findAllByBean(Content.class);
		if (list != null)
			for (Content content : list) {
				if (content.getAutoUpdate()) {
					String jobName = "job_" + content.getType();
					QuanJob job = new QuanJob();
					try {
						if (QuartzManager.isJobExist(jobName))
							QuartzManager.removeJob(jobName);
						QuartzManager.addJob(jobName, job, content.getRate(),
								content);
						 
					} catch (SchedulerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		stopService();
	}

	public static void modifyJobTime(String name, String time) {
		String jobName = "job_" + name;
		try {
			QuartzManager.modifyJobTime(jobName, time);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {

	}

	public static void startService(final Context context) {
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Intent intent = JobService.getIntent();
				context.startService(intent);
				mcontext = context;
			}
		});
		serviceThread.start();
		
		
	}

	public static void stopService() {
		Intent intent = JobService.getIntent();
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
