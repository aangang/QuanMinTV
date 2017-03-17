package com.leavessilent.quanmintv.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.category.view.CategoryFragment;
import com.leavessilent.quanmintv.home.view.HomeFragment;
import com.leavessilent.quanmintv.live.view.LiveFragment;
import com.leavessilent.quanmintv.mine.MineFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private Fragment mShowFragment;
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBind = ButterKnife.bind(this);
        mShowFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mShowFragment, HomeFragment.TAG).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @OnClick({R.id.controller_category, R.id.controller_home, R.id.controller_mine, R.id.controller_live})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.controller_category:
                switchPage(CategoryFragment.TAG, CategoryFragment.class);
                break;
            case R.id.controller_home:
                switchPage(HomeFragment.TAG, HomeFragment.class);
                break;
            case R.id.controller_mine:
                switchPage(MineFragment.TAG, MineFragment.class);
                break;
            case R.id.controller_live:
                switchPage(LiveFragment.TAG, LiveFragment.class);
                break;
        }
    }


    /**
     * @param tag 标记名
     * @param cls 需要创建的Fragment的类名
     */
    private void switchPage(String tag, Class cls) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            try {
                // 通过反射创建出该类对象
                Fragment instance = (Fragment) cls.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.container, instance, tag).hide(mShowFragment).commit();
                mShowFragment = instance;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            getSupportFragmentManager().beginTransaction().hide(mShowFragment).show(fragment).commit();
            mShowFragment = fragment;
        }
    }
}
