package com.leavessilent.quanmintv.category.presenter;

import com.leavessilent.quanmintv.category.model.CategoryModel;
import com.leavessilent.quanmintv.category.model.CategoryModelImpl;
import com.leavessilent.quanmintv.category.view.ICategoryView;
import com.leavessilent.quanmintv.common.base.BasePresenter;
import com.leavessilent.quanmintv.listener.OnLoadingDataListener;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public class CategoryPresenter extends BasePresenter<ICategoryView> implements ICategoryPresenter {

    private CategoryModelImpl mModel;

    public CategoryPresenter() {
        mModel = new CategoryModelImpl();
    }

    @Override
    public void getCategories() {
        mView.showLoading();
        mModel.getCategoryModelList(new OnLoadingDataListener<List<CategoryModel>>() {
            @Override
            public void onComplete() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e);
            }

            @Override
            public void onCallback(List<CategoryModel> data) {
                mView.setData(data);
            }
        });
    }
}
