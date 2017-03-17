package com.leavessilent.quanmintv.category.view;

import com.leavessilent.quanmintv.category.model.CategoryModel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface ICategoryView {

    void showLoading();

    void hideLoading();

    void setData(List<CategoryModel> data);

    void showError(Throwable e);

}
