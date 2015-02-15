package com.quantum.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.DbException;
import com.quantum.bean.Content;
import com.quantum.bean.Cook;
import com.quantum.bean.Location;
import com.quantum.db.DbManage;
import com.quantum.download.DownloadManager;
import com.quantum.utils.Http;
import com.quantum.utils.HttpHelper;

import android.util.Log;

public class QuanJob implements Job {
	private Content content;
	private HttpHelper httpHelper;
	private DownloadManager downloadManager;
	private Gson gson = new Gson();

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		httpHelper = new HttpHelper();

		JobDataMap data = context.getJobDetail().getJobDataMap();
		content = (Content) data.get("data");
		Log.d("---------->", "job");
		if ("weather".equals(content.getType())) {
			getWeatherData();
		} else if ("cook".equals(content.getType())) {
			getCookData(content.getUrl());
		}
	}

	private void getWeatherData() {
		Location loc = (Location) DbManage.findFirstByBean(Location.class);
		String where = loc.getAdds();
		if (where == null)
			return;
		String url = content.getUrl() + "&location=" + loc.getLng() + ","
				+ loc.getLat();
		Http.Get(url, content);
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

	private void getCookData(String url) {
		String html = httpHelper.syncConnect(url, null);
		get_index_zzw(Jsoup.parse(html));
	}

	private void get_index_zzw(Document doc) {
		Elements contentEle = doc.select("#index_zzw_main");
		Elements div_h3 = contentEle.select("div h3");
		Elements div_ul = contentEle.select("div ul");

		for (int i = 0; i < div_ul.size(); i++) {
			Elements img = div_ul.get(i).select("img[src$=.jpg]");
			String btitle = div_h3.get(i).text();
			Elements herf = div_ul.get(i).select("h2 a");
			Elements a = div_ul.get(i).select("span a");
			Elements strong = div_ul.get(i).select("strong");
			List<Cook> list = new ArrayList<Cook>();
			for (int j = 0; j < img.size(); j++) {
				Cook cook = new Cook();
				cook.setHerf(herf.get(j).attr("href"));
				cook.setImg(img.get(j).attr("src"));
				cook.setTitle(img.get(j).attr("alt"));
				cook.setInfo(strong.get(j).text());
				cook.setTip(a.get(j).text());
				cook.setBtitle(btitle);
				cook.setTime(new Date());
				cook.setImg_name(new Date().getTime() + "");
				list.add(cook);
				try {
					DownloadManager.addNewDownload(cook.getImg(),
							cook.getImg_name()+".jpg", "cook", true, true, null);
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
			content.setContent(gson.toJson(list));
			// 删除相同项
			DbManage.delete(DbManage.findContentByType(content.getType()));
			DbManage.saveBean(content);
		}

	}

}
