package com.leavessilent.quanmintv.home.view;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.adapter.HomeAdapter;
import com.leavessilent.quanmintv.common.base.BaseFragment;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.home.presenter.HomePresenter;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/2/14.
 */

public class HomeFragment extends BaseFragment<IHomeView, HomePresenter> implements IHomeView {
    public static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.rv_home)
    RecyclerView mHomeRv;
    @BindView(R.id.layout_loading)
    View mLoadingView;
    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    private HomeAdapter mAdapter;
    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void init(Bundle savedInstanceState) {
        mAnimationDrawable = ((AnimationDrawable) mLoadingImage.getDrawable());
        mAdapter = new HomeAdapter(getContext(), null);
        mHomeRv.setAdapter(mAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mHomeRv.setLayoutManager(manager);
        mPresenter.loadingData();
        showLoading();
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public void showLoading() {
        mAnimationDrawable.start();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mAnimationDrawable.stop();
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateData(HomeModel homeModel) {
        mAdapter.update(homeModel);
    }
}
