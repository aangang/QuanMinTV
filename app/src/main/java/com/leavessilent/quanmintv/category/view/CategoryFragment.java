package com.leavessilent.quanmintv.category.view;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.adapter.CategoryAdapter;
import com.leavessilent.quanmintv.category.model.CategoryModel;
import com.leavessilent.quanmintv.category.presenter.CategoryPresenter;
import com.leavessilent.quanmintv.common.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/2/14.
 */

public class CategoryFragment extends BaseFragment<ICategoryView, CategoryPresenter> implements ICategoryView {
    public static final String TAG = CategoryFragment.class.getSimpleName();

    @BindView(R.id.layout_loading)
    View mLoadingView;
    @BindView(R.id.image_loading)
    ImageView mLoadingImage;
    @BindView(R.id.rv_category)
    RecyclerView mCategoryRv;
    private AnimationDrawable mAnimationDrawable;
    private CategoryAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mAnimationDrawable = ((AnimationDrawable) mLoadingImage.getDrawable());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mCategoryRv.setLayoutManager(layoutManager);
        mAdapter = new CategoryAdapter(getContext(), null);
        mCategoryRv.setAdapter(mAdapter);
        mPresenter.getCategories();
    }

    @Override
    protected CategoryPresenter initPresenter() {
        return new CategoryPresenter();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_category;
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
    public void setData(List<CategoryModel> data) {
        mAdapter.update(data);
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
