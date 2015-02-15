package com.quantum.ui;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.quantum.R;
import com.quantum.bean.Cook;
import com.quantum.utils.BitmapHelp;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

public class Cooks {
	private Button but_movie_change;
	private TextView item_title;
	private TextView item_info;
	private TextView item_name;
	private TextView item_tips;
	private ImageView imageView;
	private JSONArray array;
	private int i = 0;
	private String picURL = "";
	private BitmapUtils bitmapUtils;
	private static String PATH = Environment.getExternalStorageDirectory() + "";
	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
			android.R.color.transparent);

	public Cooks(Context context, View convertView, String item) {
		List<Cook> retList = new Gson().fromJson(item,
				new TypeToken<List<Cook>>() {
				}.getType());

		PATH += "/" + context.getString(R.string.app_name) + "/download/cook/";

		item_info = (TextView) convertView.findViewById(R.id.item_info);
		item_name = (TextView) convertView.findViewById(R.id.item_name);
		item_tips = (TextView) convertView.findViewById(R.id.item_tips);
		imageView = (ImageView) convertView.findViewById(R.id.item_image);

		Cook cook = retList.get(0);
		item_info.setText(cook.getInfo());
		item_name.setText(cook.getBtitle());
		item_tips.setText(cook.getTip());

		BitmapDisplayConfig bigPicDisplayConfig = new BitmapDisplayConfig();
		bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
		bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils
				.getScreenSize(context));

		bitmapUtils = BitmapHelp.getBitmapUtils(context);
		bitmapUtils.display(imageView, cook.getImg(), bigPicDisplayConfig,
				null);
	}

	public class CustomBitmapLoadCallBack extends
			DefaultBitmapLoadCallBack<ImageView> {

		@Override
		public void onLoading(ImageView container, String uri,
				BitmapDisplayConfig config, long total, long current) {

		}

		@Override
		public void onLoadCompleted(ImageView container, String uri,
				Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
			fadeInDisplay(container, bitmap);
		}
	}

	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
		final TransitionDrawable transitionDrawable = new TransitionDrawable(
				new Drawable[] { TRANSPARENT_DRAWABLE,
						new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.startTransition(500);
	}
}
