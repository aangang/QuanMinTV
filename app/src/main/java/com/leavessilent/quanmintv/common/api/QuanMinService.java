package com.leavessilent.quanmintv.common.api;

import com.leavessilent.quanmintv.category.model.CategoryModel;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.live.model.AllLiveModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/2/16.
 */

public interface QuanMinService {
    @GET("page/appv2-index/info.json")
    Observable<HomeModel> getHomeModel();

    @GET("categories/list.json")
    Observable<List<CategoryModel>> getCategoryModelList();

    @GET("play/list.json")
    Observable<AllLiveModel> getAllLive();
}
