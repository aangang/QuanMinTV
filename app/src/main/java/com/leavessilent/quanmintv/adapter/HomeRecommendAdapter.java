package com.leavessilent.quanmintv.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.utils.ScreenHelper;

import java.text.DecimalFormat;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/2/17.
 */

public class HomeRecommendAdapter extends CommonAdapter<HomeModel.ApprecommendationBean> {

    private final Context mContext;

    public HomeRecommendAdapter(Context context, List<HomeModel.ApprecommendationBean> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void switchData() {

    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_live;
    }

    @Override
    protected void onBindDataToView(CommonViewHolder holder, HomeModel.ApprecommendationBean bean, int position) {
        ImageView image = (ImageView) holder.getView(R.id.live_item_image);
        TextView count = (TextView) holder.getView(R.id.live_item_count);
        ImageView avatar = (ImageView) holder.getView(R.id.live_item_avatar);
        TextView nick = (TextView) holder.getView(R.id.live_item_nick);
        TextView title = (TextView) holder.getView(R.id.live_item_title);

        LinkObject link_object = bean.getLink_object();

        Glide.with(mContext)
                .load(link_object.getThumb())
                .placeholder(R.mipmap.live_default)
                .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenHelper.dp2px(mContext, 5), 0))
                .into(image);

        int view = link_object.getView();
        String text;
        if (view > 10000) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            text = decimalFormat.format(view / (double) 10000) + "W";
        } else {
            text = String.valueOf(view);
        }
        count.setText(text);

        Glide.with(mContext)
                .load(link_object.getAvatar())
                .placeholder(R.mipmap.img_touxiang_default)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(avatar);

        nick.setText(link_object.getNick());
        title.setText(bean.getTitle());

    }
}
