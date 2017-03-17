package com.leavessilent.quanmintv.common.base;

/**
 * Created by Administrator on 2017/2/14.
 */

public abstract class BasePresenter<T> {
    public T mView;

    public void onStart(T view) {
        this.mView = view;
    }

    public void onDestroy() {
        this.mView = null;
    }
}
