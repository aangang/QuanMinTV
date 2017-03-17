package com.leavessilent.quanmintv.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.utils.ScreenHelper;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/2/16.
 */

public class HomeClassifyAdapter extends CommonAdapter<HomeModel.AppclassificationBean> {


    private final Context mContext;

    public HomeClassifyAdapter(Context context, List<HomeModel.AppclassificationBean> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.home_item_classify;
    }

    @Override
    protected void onBindDataToView(CommonViewHolder holder, HomeModel.AppclassificationBean appclassificationBean, int position) {
        ImageView image = (ImageView) holder.getView(R.id.item_classify_image);
        TextView name = (TextView) holder.getView(R.id.item_classify_name);
        Glide.with(mContext)
                .load(appclassificationBean.getThumb())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(image);
        name.setText(appclassificationBean.getTitle());
    }
}
