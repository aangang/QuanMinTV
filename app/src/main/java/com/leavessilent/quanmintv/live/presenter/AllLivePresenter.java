package com.leavessilent.quanmintv.live.presenter;

import com.leavessilent.quanmintv.common.base.BasePresenter;
import com.leavessilent.quanmintv.listener.OnLoadingDataListener;
import com.leavessilent.quanmintv.live.model.AllLiveModel;
import com.leavessilent.quanmintv.live.model.AllLiveModelImpl;
import com.leavessilent.quanmintv.live.view.IAllLiveView;

/**
 * Created by Administrator on 2017/3/25.
 */

public class AllLivePresenter extends BasePresenter<IAllLiveView> implements IAllLivePresenter {
    private final AllLiveModelImpl mAllLiveModel;

    public AllLivePresenter() {
        mAllLiveModel = new AllLiveModelImpl();
    }

    @Override
    public void getAllClassifyLive(String classify) {
        mView.showLoading();
        mAllLiveModel.getAllLiveModelByClassify(classify, new OnLoadingDataListener<AllLiveModel>() {
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
                mView.updateData(data.getData());
            }
        });
    }
}
