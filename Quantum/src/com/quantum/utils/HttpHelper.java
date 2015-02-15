package com.quantum.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HttpHelper {
	private static final String CHARSET = HTTP.UTF_8;
	private static HttpClient customerHttpClient;

	public HttpHelper() {

	}

	public static synchronized HttpClient getHttpClient() {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 1000);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, 2000);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 4000);

			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			customerHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customerHttpClient;
	}

	/**
	 * 同步带参数方法
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            POST或GET要传递的参数
	 * @param method
	 *            方法,POST或GET
	 * @param callback
	 *            回调方法
	 */
	public String syncConnect(final String url, final Map<String, String> params) {
		String json = null;
		BufferedReader reader = null;

		try {
			HttpClient client = getHttpClient();
			HttpUriRequest request = getRequest(url, params, HttpMethod.GET);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuilder sb = new StringBuilder();
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					sb.append(s);
				}

				json = sb.toString();
			}
		} catch (ClientProtocolException e) {
			Log.e("HttpConnectionUtil", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("HttpConnectionUtil", e.getMessage(), e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// ignore me
			}
		}
		return json;
	}

	/**
	 * POST跟GET传递参数不同,POST是隐式传递,GET是显式传递
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            参数
	 * @param method
	 *            方法
	 * @return
	 */
	private HttpUriRequest getRequest(String url, Map<String, String> params,
			HttpMethod method) {
		if (method.equals(HttpMethod.POST)) {
			List<NameValuePair> listParams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String name : params.keySet()) {
					listParams.add(new BasicNameValuePair(name, params
							.get(name)));
				}
			}
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						listParams);
				HttpPost request = new HttpPost(url);
				request.setEntity(entity);
				return request;
			} catch (UnsupportedEncodingException e) {
				// Should not come here, ignore me.
				throw new java.lang.RuntimeException(e.getMessage(), e);
			}
		} else {
			if (url.indexOf("?") < 0) {
				url += "?";
			}
			if (params != null) {
				for (String name : params.keySet()) {
					try {
						url += "&" + name + "="
								+ URLEncoder.encode(params.get(name), "UTF-8");

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			HttpGet request = new HttpGet(url);
			return request;
		}
	}

	/**
	 * 回调接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface HttpConnectionCallback {
		/**
		 * Call back method will be execute after the http request return.
		 * 
		 * @param response
		 *            the response of http request. The value will be null if
		 *            any error occur.
		 */
		void execute(String response);
	}
}
