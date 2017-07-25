package com.leavessilent.quanmintv.utils;

import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;

import com.leavessilent.quanmintv.play.view.PlayActivity;

/**
 * Created by user on 2016/9/13.
 */
public class LightController {

    /**
     * 亮度加  distanceY > 0
     */
    public static void lightUp(Activity activity, float distanceY, int mScreenWidth) {

        //先关闭系统的亮度自动调节
        try {
            float addLight = 255 * 2 * distanceY / mScreenWidth;

            int currentLight = (int) (android.provider.Settings.System.getInt(activity.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS,
                    255));

            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.screenBrightness = Math.min(1.0f, currentLight / 255 + addLight);
            activity.getWindow().setAttributes(attributes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 亮度加  distanceY < 0
     */
    public static void lightDown(Activity activity, float distanceY, int mScreenWidth) {
        //先关闭系统的亮度自动调节
        try {
            float reduceLight = 2 * distanceY / mScreenWidth;

            int currentLight = (int) (android.provider.Settings.System.getInt(activity.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS,
                    255));

            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.screenBrightness = Math.max(0f, currentLight / 255 + reduceLight);
            activity.getWindow().setAttributes(attributes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
