package com.leavessilent.quanmintv.live.view;

import com.leavessilent.quanmintv.home.model.LinkObject;

import java.util.List;

/**
 * Created by Administrator on 2017/3/25.
 */

public interface IAllLiveView {
    void hideLoading();

    void showLoading();

    void updateData(List<LinkObject> data);

    void showError(Throwable throwable);
}
