package com.quantum.utils;

import android.content.Context;

public class OthersUtil {

	public static int judgeWeaImage(String wea, Context context) {
		String image_num = "0";
		if (wea.indexOf("雨") > 0) {
			image_num = "3";
			if (wea.indexOf("雷") > 0)
				image_num = "4";

		} else if (wea.indexOf("雪") > 0) {
			image_num = "13";
		} else if (wea.indexOf("云") > 0 || wea.indexOf("阴") >= 0) {
			image_num = "2";
			if (wea.indexOf("晴") > 0)
				image_num = "1";
		} else if (wea.indexOf("雾") > 0) {
			image_num = "18";
		}

		return context.getResources().getIdentifier("d_" + image_num,
				"drawable", context.getPackageName());
	}

	public static String getPm(int pm) {
		String name = "优";
		if (0 < pm && pm <= 50) {
			name = "<font color='#00E400'>优</font>";
		} else if (50 < pm && pm <= 100) {
			name = "<font color='#FFD306'>良</font>";
		} else if (100 < pm && pm <= 150) {
			name = "<font color='#FF7D00'>轻度污染</font>";
		} else if (150 < pm && pm <= 200) {
			name = "<font color='#FF0000'>中度污染</font>";
		} else if (200 < pm && pm <= 300) {
			name = "<font color='#98004B'>重度污染</font>";
		} else {
			name = "<font color='#7D0022'>严重污染</font>";
		}
		return name;
	}

	
	
	// public static void setRandomBC(View convertView) {
	// Random random = new Random();
	// int img = convertView.getContext()
	// .getResources()
	// .getIdentifier("item_color_"+random.nextInt(2), "color",
	// convertView.getContext().getPackageName());
	// LinearLayout linearLayout =
	// (LinearLayout)convertView.findViewById(R.id.item_main);
	// linearLayout.setBackgroundResource(img);
	// }

}
