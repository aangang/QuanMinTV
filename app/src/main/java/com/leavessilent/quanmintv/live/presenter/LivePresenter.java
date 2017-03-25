package com.leavessilent.quanmintv.live.presenter;

import com.leavessilent.quanmintv.common.base.BasePresenter;
import com.leavessilent.quanmintv.listener.OnLoadingDataListener;
import com.leavessilent.quanmintv.live.model.AllLiveModel;
import com.leavessilent.quanmintv.live.model.AllLiveModelImpl;
import com.leavessilent.quanmintv.live.view.ILiveView;

/**
 * Created by Administrator on 2017/2/20.
 */

public class LivePresenter extends BasePresenter<ILiveView> implements ILivePresenter {
    private AllLiveModel mData;
    private AllLiveModelImpl mModel;

    public LivePresenter() {
        mModel = new AllLiveModelImpl();
    }

    @Override
    public void getAllLive() {
        mView.showLoading();
        mModel.getAllLiveModel(new OnLoadingDataListener<AllLiveModel>() {

            @Override
            public void onComplete() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e);
            }

            @Override
            public void onCallback(AllLiveModel data) {
                mData = data;
                mView.updateData(data.getData());
            }
        });
    }

    @Override
    public String getVideoUid(int position) {
        return mData.getData().get(position).getUid();
    }
}
