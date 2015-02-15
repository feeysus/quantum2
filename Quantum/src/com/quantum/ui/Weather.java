package com.quantum.ui;

import com.google.gson.Gson;
import com.quantum.R;
import com.quantum.bean.Wea;
import com.quantum.utils.OthersUtil;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Weather {
	Context context;
	Gson gson = new Gson();

	public Weather(Context context, View convertView, String item) {
		this.context = context;
		Wea wea = gson.fromJson(item, Wea.class);
		
		if(wea.getError()!=0)
			return;
	 
		View wea_city = convertView.findViewById(R.id.item_wea_city);
		((TextView) wea_city).setText(wea.getResults().get(0).getCurrentCity());

		View wea_air = convertView.findViewById(R.id.item_wea_air);
		int pm = Integer.valueOf(wea.getResults().get(0).getPm25().toString());
		((TextView) wea_air).setText(Html.fromHtml(OthersUtil.getPm(pm)));

		View item_wea_temp_now = convertView
				.findViewById(R.id.item_wea_temp_now);
		String temp_now = wea.getResults().get(0).getWeather_data().get(0)
				.getDate();
		if(temp_now.contains("实时")){
			temp_now = temp_now.split("：")[1];
			temp_now = temp_now.substring(0, temp_now.length() - 2) + "°";
		}
		else{
			temp_now =  wea.getResults().get(0).getWeather_data().get(0).getTemperature();
			temp_now = temp_now.substring(0, temp_now.length() - 1) + "°";
		}
 
		((TextView) item_wea_temp_now).setText(temp_now);

//		View item_wea_temp1 = convertView.findViewById(R.id.item_wea_temp1);
//		((TextView) item_wea_temp1).setText(wea.getResults().get(0)
//				.getWeather_data().get(0).getTemperature());
//
//		View wea_img = convertView.findViewById(R.id.item_wea_img1);
//		((ImageView) wea_img).setImageResource(OthersUtil.judgeWeaImage(wea
//				.getResults().get(0).getWeather_data().get(0).getWeather(),
//				context));

		for (int j = 1; j <= 4; j++) {
			if (j == 1) {
				int id_wea = context.getResources().getIdentifier(
						"item_wea_weather" + j, "id", context.getPackageName());
				View wea_temp = convertView.findViewById(id_wea);
				((TextView) wea_temp).setText(wea.getResults().get(0)
						.getWeather_data().get(j - 1).getWeather());
			}

			int id_img = context.getResources().getIdentifier(
					"item_wea_img" + j, "id", context.getPackageName());
			View wea_img = convertView.findViewById(id_img);
			((ImageView) wea_img).setImageResource(OthersUtil.judgeWeaImage(wea
					.getResults().get(0).getWeather_data().get(j - 1)
					.getWeather(), context));

			String temp = wea.getResults().get(0)
					.getWeather_data().get(j - 1).getTemperature();
			temp = temp.substring(0,temp.length()-1)+ "°";
			int id_temp = context.getResources().getIdentifier(
					"item_wea_temp" + j, "id", context.getPackageName());
			View wea_temp = convertView.findViewById(id_temp);
			((TextView) wea_temp).setText(temp);

			if (j == 1)
				continue;
			int id = context.getResources().getIdentifier("item_wea_week" + j,
					"id", context.getPackageName());
			View wea_week = convertView.findViewById(id);
			((TextView) wea_week).setText(wea.getResults().get(0)
					.getWeather_data().get(j - 1).getDate());
		}

	}

}
