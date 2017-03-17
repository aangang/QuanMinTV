package com.leavessilent.quanmintv.category.model;

import com.leavessilent.quanmintv.listener.OnLoadingDataListener;
import com.leavessilent.quanmintv.utils.HttpUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CategoryModelImpl implements ICategoryModel {
    @Override
    public void getCategoryModelList(final OnLoadingDataListener<List<CategoryModel>> listener) {
        HttpUtils.getService()
                .getCategoryModelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CategoryModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CategoryModel> value) {
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
