package com.leavessilent.quanmintv.play.presenter;

import com.leavessilent.quanmintv.common.base.BasePresenter;
import com.leavessilent.quanmintv.listener.OnLoadingDataListener;
import com.leavessilent.quanmintv.play.model.Video;
import com.leavessilent.quanmintv.play.model.VideoModelImpl;
import com.leavessilent.quanmintv.play.view.IPlayView;
import com.leavessilent.quanmintv.utils.HttpUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/23.
 */

public class PlayPresenter extends BasePresenter<IPlayView> implements IPlayPresenter, OnLoadingDataListener<Video> {

    private final VideoModelImpl mModel;

    public PlayPresenter() {
        mModel = new VideoModelImpl();
    }

    @Override
    public void getVideoInfo(String uid) {

        mModel.getVideoInfo(uid, this);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        mView.showError(e);
    }

    @Override
    public void onCallback(Video data) {
        mView.setVideoPath(data.getLive().getWs().getFlv().get_$5().getSrc());
        mView.setTitle(data.getTitle());
    }
}
