package com.quantum;

import com.baidu.frontia.FrontiaApplication;
import com.quantum.bean.Constants;
import com.quantum.db.DbManage;
import com.quantum.download.DownloadManager;
import com.quantum.service.LocationService;
import com.quantum.utils.Http;

/*
 * 如果您的工程中实现了Application的继承类，那么，您需要将父类改为com.baidu.frontia.FrontiaApplication。
 * 如果您没有实现Application的继承类，那么，请在AndroidManifest.xml的Application标签中增加属性： 
 * <application android:name="com.baidu.frontia.FrontiaApplication"
 * 。。。
 */
public class CustomApplication extends FrontiaApplication {
 
	@Override
    public void onCreate() {
        super.onCreate();
        new Constants(this);
        // 以下是您原先的代码实现，保持不变
        new DbManage(this);
        new Http(this); 
        new DownloadManager(this);
      
        LocationService.startService(this);
    }
}
