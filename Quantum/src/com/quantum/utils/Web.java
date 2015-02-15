package com.quantum.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class Web extends WebView {

	public Web(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.getSettings().setDomStorageEnabled(true);
		this.getSettings().setJavaScriptEnabled(true); 
		
	}

	public void init(String fileName) {
		 
		this.loadUrl("file:///android_asset/" + fileName + ".html");
		
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return false;
	}

	public void enablecrossdomain() {
		try {
			Field field = WebView.class.getDeclaredField("mWebViewCore");
			field.setAccessible(true);
			Object webviewcore = field.get(this);
			Method method = webviewcore.getClass().getDeclaredMethod(
					"nativeRegisterURLSchemeAsLocal", String.class);
			method.setAccessible(true);
			method.invoke(webviewcore, "http");
			method.invoke(webviewcore, "https");
		} catch (Exception e) {
			Log.d("wokao", "enablecrossdomain error");
			e.printStackTrace();
		}
	}
}
