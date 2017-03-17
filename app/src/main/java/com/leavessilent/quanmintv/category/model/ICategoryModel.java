package com.leavessilent.quanmintv.category.model;

import com.leavessilent.quanmintv.listener.OnLoadingDataListener;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface ICategoryModel {

    void getCategoryModelList(OnLoadingDataListener<List<CategoryModel>> listener);


}
