package com.quantum;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.lidroid.xutils.ViewUtils;
import com.quantum.R;
import com.quantum.bean.Constants;
import com.quantum.service.JobService;
import com.quantum.service.LocationService;
import com.quantum.ui.Cards;
import com.quantum.ui.ObservableScrollView;
import com.quantum.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity implements ScrollViewListener {

	@ViewInject(R.id.dismissable_container)
	ViewGroup dismissableContainer;
	@ViewInject(R.id.scrollView)
	ObservableScrollView scrollView;

	Cards cards;
	private NotificationReceiver notificationReceiver;
	RelativeLayout.LayoutParams scrollView_params;

	int old_margin = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				Utils.getMetaValue(MainActivity.this, "api_key"));

		cards = new Cards(this, dismissableContainer, scrollView, null);
		cards.getView();

		notificationReceiver = new NotificationReceiver();
		registerNotificationReceiver();

		JobService.startService(this);

		scrollView_params = (RelativeLayout.LayoutParams) scrollView
				.getLayoutParams();
		scrollView.setScrollViewListener(this);
		scrollView.setOnTouchListener(new TouchListener());
		old_margin = scrollView_params.topMargin;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 更新界面显示内容
	private void updateDisplay() {

	}

	private class NotificationReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			cards.getView();
		}
	}

	private void registerNotificationReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_SHOW_NOTIFICATION);
		registerReceiver(notificationReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(notificationReceiver);
		super.onDestroy();
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (y == 0) {
			scrollView_params.topMargin += 5;
			scrollView.setLayoutParams(scrollView_params);
		}
	}

	class TouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				scrollView_params.topMargin = old_margin;
				scrollView.setLayoutParams(scrollView_params);
			}
			return false;
		}

	}
}
