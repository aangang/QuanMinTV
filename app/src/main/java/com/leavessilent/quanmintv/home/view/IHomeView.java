package com.leavessilent.quanmintv.home.view;

import com.leavessilent.quanmintv.home.model.HomeModel;

/**
 * Created by Administrator on 2017/2/15.
 */

public interface IHomeView {

    void showLoading();

    void hideLoading();

    void showError(Throwable e);

    void updateData(HomeModel homeModel);


}
