package com.leavessilent.quanmintv.play.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.common.base.BaseMvpActivity;
import com.leavessilent.quanmintv.common.base.Constant;
import com.leavessilent.quanmintv.play.presenter.PlayPresenter;
import com.leavessilent.quanmintv.utils.LightController;
import com.leavessilent.quanmintv.utils.LogUtil;
import com.leavessilent.quanmintv.utils.ScreenUtil;
import com.leavessilent.quanmintv.utils.ToastUtil;
import com.leavessilent.quanmintv.utils.VolumController;

import butterknife.BindView;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class PlayActivity extends BaseMvpActivity<IPlayView, PlayPresenter> implements Handler.Callback, GestureDetector.OnGestureListener, IPlayView, MediaPlayer.OnPreparedListener, View.OnTouchListener {
    private static final int CHANGE_HEADER_STATUS = 10;
    private static final long DELAY_TIME = 3000;
    @BindView(R.id.play_video_view)
    VideoView mVideoView;

    @BindView(R.id.play_image_loading)
    ImageView mLoadingImage;

    @BindView(R.id.play_tv_title)
    TextView mTitleTv;

    @BindView(R.id.play_linear_big_control1)
    View mHeaderView;

    private Handler mHandler;

    private boolean mIsShow = true;

    private int mThreshold = 3;
    private int mHeightPixels;
    private int mWidthPixels;
    private GestureDetector mGestureDetector;


    @Override
    protected PlayPresenter initPresenter() {
        return new PlayPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        hideStatusBarAndNavigation();
        mHandler = new Handler(this);

        mHeightPixels = ScreenUtil.getScreenHeight(this);
        mWidthPixels = ScreenUtil.getScreenWidth(this);
        mGestureDetector = new GestureDetector(this, this);

        Intent intent = getIntent();
        String uid = intent.getStringExtra(Constant.UID);


        showLoading();
        mVideoView.setOnTouchListener(this);
        mVideoView.setOnPreparedListener(this);
        mPresenter.getVideoInfo(uid);

    }

    private void hideStatusBarAndNavigation() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(option);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_play;
    }

    @Override
    public void hideLoading() {
        mLoadingImage.getAnimation().cancel();
        mLoadingImage.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mLoadingImage.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(PlayActivity.this, R.anim.anim_rotate);
        mLoadingImage.startAnimation(animation);

    }

    @Override
    public void showError(Throwable throwable) {
        finish();
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setVideoPath(String path) {
        mVideoView.setVideoPath(path);
    }

    @Override
    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideoView.start();
        mHandler.sendEmptyMessageDelayed(CHANGE_HEADER_STATUS, DELAY_TIME);
        hideLoading();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @OnClick(R.id.play_image_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_image_back:
                this.finish();
                break;
        }
    }


    /**
     * 显示音量 亮度的Toast
     *
     * @param text
     * @param flag
     */


    public void showToast(String text, boolean flag) {
        ToastUtil.makeToast(text, flag, this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        hideStatusBarAndNavigation();
        switch (v.getId()) {
            case R.id.play_video_view:
                mGestureDetector.onTouchEvent(event);
                return true;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        mHandler.sendEmptyMessage(CHANGE_HEADER_STATUS);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int x = (int) e1.getX();
        if (Math.abs(distanceY) > mThreshold) {
            if (x > (mWidthPixels / 2 + 0.2 * mWidthPixels)) {  //音量改变
                if (distanceY > 0) {
                    VolumController.volumUp(this, distanceY, mHeightPixels);
                } else {
                    VolumController.volumDown(this, distanceY, mHeightPixels);
                }
            } else if (x < (mWidthPixels / 2 - 0.2 * mWidthPixels)) {
                if (distanceY > 0) {
                    LightController.lightUp(this, distanceY, mHeightPixels);
                } else {
                    LightController.lightDown(this, distanceY, mHeightPixels);
                }
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case CHANGE_HEADER_STATUS:
                if (mIsShow) {
                    mHeaderView.setVisibility(View.GONE);
                } else {
                    mHeaderView.setVisibility(View.VISIBLE);
                }
                mIsShow = !mIsShow;
                return true;
        }
        return false;
    }
}
