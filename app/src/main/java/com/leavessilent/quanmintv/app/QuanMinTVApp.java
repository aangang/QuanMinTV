package com.leavessilent.quanmintv.app;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.vov.vitamio.Vitamio;

/**
 * Created by Administrator on 2017/2/21.
 */

public class QuanMinTVApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Vitamio.isInitialized(this);
    }
}
