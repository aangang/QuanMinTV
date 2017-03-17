package com.leavessilent.quanmintv.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leavessilent.quanmintv.play.model.Video;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/14.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    private View mRootView;
    private Unbinder mUnbinder;
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mPresenter = initPresenter();
        mPresenter.onStart((V) this);
        init(savedInstanceState);
        return mRootView;
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract T initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.onDestroy();
    }

    protected abstract int getContentViewId();
}
