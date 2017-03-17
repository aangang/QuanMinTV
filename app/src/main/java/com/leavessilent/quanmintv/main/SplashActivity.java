package com.leavessilent.quanmintv.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.leavessilent.quanmintv.R;


public class SplashActivity extends AppCompatActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    private static final int TO_MAIN = 10;
    private static final long DELAY_TIME = 3000;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_MAIN:
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_opacity);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        findViewById(R.id.image_splash).setAnimation(animation);

        mHandler.sendEmptyMessageDelayed(TO_MAIN, DELAY_TIME);


    }
}
