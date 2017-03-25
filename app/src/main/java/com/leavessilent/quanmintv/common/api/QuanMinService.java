package com.leavessilent.quanmintv.common.api;

import com.leavessilent.quanmintv.category.model.CategoryModel;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.live.model.AllLiveModel;
import com.leavessilent.quanmintv.play.model.Video;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    @GET("rooms/{uid}/info.json")
    Observable<Video> getVideoInfo(@Path("uid")String uid);

    @GET("categories/{classify}/list.json")
    Observable<AllLiveModel> getAllClassifyLive(@Path("classify") String classify);
}
