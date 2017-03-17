package com.leavessilent.quanmintv.home.presenter;

import com.leavessilent.quanmintv.common.base.BasePresenter;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.home.model.HomeModelImpl;
import com.leavessilent.quanmintv.home.view.IHomeView;
import com.leavessilent.quanmintv.listener.OnLoadingDataListener;

/**
 * Created by Administrator on 2017/2/15.
 */

public class HomePresenter extends BasePresenter<IHomeView> implements IHomePresenter {
    private HomeModelImpl mModel;

    public HomePresenter() {
        mModel = new HomeModelImpl();
    }

    @Override
    public void loadingData() {
        mView.showLoading();
        mModel.getHomeModel(new OnLoadingDataListener<HomeModel>() {
            @Override
            public void onComplete() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e);
            }

            @Override
            public void onCallback(HomeModel data) {
                mView.updateData(data);
            }
        });

    }
}
