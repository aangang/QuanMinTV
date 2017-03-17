package com.leavessilent.quanmintv.live.view;

import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.live.model.AllLiveModel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface ILiveView {
    void hideLoading();

    void showLoading();

    void updateData(List<LinkObject> data);

    void showError(Throwable throwable);
}
