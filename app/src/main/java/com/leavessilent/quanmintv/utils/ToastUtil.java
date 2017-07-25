package com.leavessilent.quanmintv.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leavessilent.quanmintv.R;

/**
 * Created by Leavessilent on 2016/9/12.
 */
public class ToastUtil {
    private static Toast sToast = null;
    private static Toast mToast = null;
    private static View view;
    private static TextView text;
    private static ImageView image;

    public static void makeText(String text, Context context) {
        if (sToast == null) {
            sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        sToast.setText(text);
        sToast.show();
    }

    public static void makeToast(String string,boolean flag, Context context) {
        if (mToast == null) {
            view = View.inflate(context, R.layout.toast_item, null);
            text = (TextView) view.findViewById(R.id.play_text_volum_light);
            image = ((ImageView) view.findViewById(R.id.play_image_volum_light));
            mToast = new Toast(context);
            mToast.setView(view);
            mToast.setGravity(Gravity.CENTER,0,0);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
            if (flag){
                image.setImageResource(R.mipmap.sound);
            }else {
                image.setImageResource(R.mipmap.light);
            }
            text.setText(string);
            mToast.show();
    }
}
