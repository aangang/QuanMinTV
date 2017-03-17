package com.leavessilent.quanmintv.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.utils.ScreenHelper;

import java.text.DecimalFormat;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/2/17.
 */

public class LiveAdapter extends CommonAdapter<LinkObject> {

    private final Context mContext;

    public LiveAdapter(Context context, List<LinkObject> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_live;
    }

    @Override
    protected void onBindDataToView(CommonViewHolder holder, LinkObject linkObject, int position) {
        ImageView image = (ImageView) holder.getView(R.id.live_item_image);
        TextView count = (TextView) holder.getView(R.id.live_item_count);
        ImageView avatar = (ImageView) holder.getView(R.id.live_item_avatar);
        TextView nick = (TextView) holder.getView(R.id.live_item_nick);
        TextView title = (TextView) holder.getView(R.id.live_item_title);
        Glide.with(mContext)
                .load(linkObject.getThumb())
                .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenHelper.dp2px(mContext, 5), 0))
                .into(image);

        int view = linkObject.getView();
        String text;
        if (view > 10000) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            text = decimalFormat.format(view / (double) 10000) + "W";
        } else {
            text = String.valueOf(view);
        }
        count.setText(text);

        Glide.with(mContext)
                .load(linkObject.getAvatar())
                .placeholder(R.mipmap.img_touxiang_default)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(avatar);

        nick.setText(linkObject.getNick());
        title.setText(linkObject.getTitle());


    }
}
