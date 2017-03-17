package com.leavessilent.quanmintv.home.model;

import com.leavessilent.quanmintv.listener.OnLoadingDataListener;
import com.leavessilent.quanmintv.utils.HttpUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/16.
 */

public class HomeModelImpl implements IHomeModel {

    @Override
    public void getHomeModel(final OnLoadingDataListener<HomeModel> listener) {
        HttpUtils.getService()
                .getHomeModel()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<HomeModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModel value) {
                        listener.onCallback(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
    }

}
