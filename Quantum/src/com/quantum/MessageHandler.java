package com.quantum;

import com.google.gson.Gson;
import com.quantum.bean.Constants;
import com.quantum.bean.Content;
import com.quantum.bean.Message;
import com.quantum.bean.User;
import com.quantum.db.DbManage;
import com.quantum.service.JobService; 
import com.quantum.utils.Http;
import com.quantum.utils.Utils;

import android.content.Context; 

public class MessageHandler {
	Context context;
	public static MessageHandler instanse;
	private Gson gson = new Gson();

	public MessageHandler(Context context) {
		this.context = context;
		instanse = this;
	}

	// 登录
	public void login(String appid, String userId, String channelId) {
		User user = new User();
		user.setUsername(userId);
		user.setPassword(channelId);
		//DbManage.saveBean(user);
		//Http.Post(Constants.action_regist, gson.toJson(user));
	}

	// 接收推送来的信息
	public void publishMessage(String conent) {
		if (Utils.isGoodJson(conent)) {
			Message msg = gson.fromJson(conent, Message.class);
			Content con = msg.getContent();
			// 删除相同项
			DbManage.delete(DbManage.findContentByType(con
					.getType()));
			DbManage.saveBean(con);
			JobService.startService(context);
			//DbManage.saveBean(msg);
		}
	}
}
