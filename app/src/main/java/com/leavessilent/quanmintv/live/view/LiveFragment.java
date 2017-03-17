package com.leavessilent.quanmintv.live.view;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.adapter.LiveAdapter;
import com.leavessilent.quanmintv.common.base.BaseFragment;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.live.presenter.LivePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment<ILiveView, LivePresenter> implements ILiveView {

    @BindView(R.id.rv_live)
    RecyclerView mLiveRv;

    @BindView(R.id.layout_loading)
    View mLoadingView;
    @BindView(R.id.image_loading)
    ImageView mLoadingImage;

    public static final String TAG = LiveFragment.class.getSimpleName();
    private AnimationDrawable mAnimationDrawable;
    private LiveAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mAnimationDrawable = ((AnimationDrawable) mLoadingImage.getDrawable());

        mAdapter = new LiveAdapter(getContext(), null);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mLiveRv.setLayoutManager(layoutManager);
        mLiveRv.setAdapter(mAdapter);
        mPresenter.getAllLive();
    }

    @Override
    protected LivePresenter initPresenter() {
        return new LivePresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_live;
    }

    @Override
    public void hideLoading() {
        mAnimationDrawable.stop();
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mAnimationDrawable.start();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateData(List<LinkObject> data) {
        mAdapter.update(data);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
