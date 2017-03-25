package com.leavessilent.quanmintv.play.view;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface IPlayView {
    void hideLoading();

    void showLoading();

    void showError(Throwable throwable);

    void setVideoPath(String path);

    void setTitle(String title);
}
