package com.leavessilent.quanmintv.listener;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface OnLoadingDataListener<T> {

    void onComplete();

    void onError(Throwable e);

    void onCallback(T data);

}
