package com.leavessilent.quanmintv.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

import com.leavessilent.quanmintv.play.view.PlayActivity;


/**
 * Created by user on 2016/9/13.
 */
public class VolumController {

    public static void volumUp(Activity activity, float distanceY, int mScreenWidth) {
        try {

            // 获取声音的管理者
            AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            // 获取当前音量
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            // 获取最大音量
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            // 计算增加的音量   add < 0
            double add = 2 * maxVolume * distanceY / mScreenWidth;
            double changed = Math.min(maxVolume, currentVolume + add);
            // 设置音量
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) changed, AudioManager.FLAG_PLAY_SOUND);
            ((PlayActivity) activity).showToast((int)(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / maxVolume) + "%", true);
        }catch (Exception e){

        }
    }

    public static void volumDown(Activity activity, float distanceY, int mScreenWidth) {
        try {

        // 获取声音的管理者
        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        // 获取当前音量
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // 获取最大音量
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 计算减少的音量   add < 0
        double add = 2 * maxVolume * distanceY / mScreenWidth;
        double changed = Math.max(0, currentVolume + add);
        // 设置音量
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) changed, AudioManager.FLAG_PLAY_SOUND);
        ((PlayActivity) activity).showToast((int)(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / maxVolume) + "%", true);
        } catch (Exception e){

        }
    }
}
