package com.leavessilent.quanmintv.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.category.model.CategoryModel;
import com.leavessilent.quanmintv.utils.ScreenHelper;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CategoryAdapter extends CommonAdapter<CategoryModel> {

    private final Context mContext;

    public CategoryAdapter(Context context, List<CategoryModel> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_category;
    }

    @Override
    protected void onBindDataToView(CommonViewHolder holder, CategoryModel categoryModel, int position) {
        ImageView image = (ImageView) holder.getView(R.id.item_category_image);
        TextView name = (TextView) holder.getView(R.id.item_category_name);
        Glide.with(mContext)
                .load(categoryModel.getThumb())
                .bitmapTransform(new RoundedCornersTransformation(mContext, ScreenHelper.dp2px(mContext, 4), 0))
                .into(image);
        name.setText(categoryModel.getName());
    }
}
