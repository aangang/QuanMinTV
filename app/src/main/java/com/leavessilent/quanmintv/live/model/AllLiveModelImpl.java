package com.leavessilent.quanmintv.live.model;

import com.leavessilent.quanmintv.listener.OnLoadingDataListener;
import com.leavessilent.quanmintv.utils.HttpUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/20.
 */

public class AllLiveModelImpl implements IAllLiveModel {
    @Override
    public void getAllLiveModel(final OnLoadingDataListener<AllLiveModel> listener) {
        HttpUtils.getService().getAllLive()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllLiveModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllLiveModel value) {
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
