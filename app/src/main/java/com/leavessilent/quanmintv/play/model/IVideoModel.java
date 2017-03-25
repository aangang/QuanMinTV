package com.leavessilent.quanmintv.play.model;

import com.leavessilent.quanmintv.listener.OnLoadingDataListener;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface IVideoModel {
    void getVideoInfo(String uid, OnLoadingDataListener<Video> listener);
}
