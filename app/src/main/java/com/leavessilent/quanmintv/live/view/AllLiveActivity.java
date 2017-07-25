package com.leavessilent.quanmintv.live.view;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.adapter.CommonAdapter;
import com.leavessilent.quanmintv.adapter.LiveAdapter;
import com.leavessilent.quanmintv.common.base.BaseMvpActivity;
import com.leavessilent.quanmintv.common.base.Constant;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.live.presenter.AllLivePresenter;
import com.leavessilent.quanmintv.play.view.PlayActivity;
import com.leavessilent.quanmintv.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class AllLiveActivity extends BaseMvpActivity<IAllLiveView, AllLivePresenter> implements IAllLiveView, CommonAdapter.OnItemClickListener {
    @BindView(R.id.rv_all_live)
    RecyclerView mAllLiveRv;
    @BindView(R.id.image_loading)
    ImageView mLoadingImage;

    private LiveAdapter mAdapter;

    private AnimationDrawable mAnimationDrawable;


    @Override
    protected AllLivePresenter initPresenter() {
        return new AllLivePresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String classify = intent.getStringExtra(Constant.CLASSIFY);

        mAnimationDrawable = ((AnimationDrawable) mLoadingImage.getDrawable());


        mAdapter = new LiveAdapter(this, null);
        mAdapter.setListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mAllLiveRv.setLayoutManager(layoutManager);
        mAllLiveRv.setAdapter(mAdapter);
        mPresenter.getAllClassifyLive(classify);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_all_live;
    }

    @Override
    public void hideLoading() {
        mAnimationDrawable.stop();
        mLoadingImage.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mAnimationDrawable.start();
        mLoadingImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateData(List<LinkObject> data) {
        mAdapter.update(data);
    }

    @Override
    public void showError(Throwable throwable) {
        ToastUtil.makeText(throwable.getMessage(), this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(Constant.UID, mAdapter.get(position).getUid());
        startActivity(intent);
    }
}
