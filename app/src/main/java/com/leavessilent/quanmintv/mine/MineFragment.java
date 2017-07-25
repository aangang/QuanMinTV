package com.leavessilent.quanmintv.mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.mine.model.User;
import com.leavessilent.quanmintv.mine.util.OrderInfoUtil2_0;
import com.leavessilent.quanmintv.utils.LogUtil;
import com.mob.commons.SHARESDK;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/2/14.
 */

public class MineFragment extends Fragment implements PlatformActionListener, Handler.Callback {
    public static final String TAG = MineFragment.class.getSimpleName();
    private static final int LOGIN_SUCCESS = 10;

    @BindView(R.id.mine_image_icon)
    ImageView mIconImage;

    @BindView(R.id.mine_tv_login)
    TextView mNameTv;

    @BindView(R.id.mine_layout_pay)
    View mPayView;

    @BindView(R.id.mine_tv_seed)
    TextView mSeedTv;

    private Handler mHandler;
    private int mCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mHandler = new Handler(this);
        ShareSDK.initSDK(getContext());
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        if (platform.isAuthValid()) {
            showUser(platform);
        }
    }

    private void showUser(Platform platform) {
        Message msg = Message.obtain();
        PlatformDb db = platform.getDb();
        String icon = db.get("user_icon");
        String name = db.getUserName();
        User user = new User(name, icon);
        msg.what = LOGIN_SUCCESS;
        msg.obj = user;
        mHandler.sendMessage(msg);
    }

    @OnClick({R.id.mine_image_icon, R.id.mine_tv_login, R.id.mine_tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_image_icon:
            case R.id.mine_tv_login:
                login();
                break;
            case R.id.mine_tv_pay:
                EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                pay();
                break;
        }
    }

    private void login() {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        if (platform.isAuthValid()) {
            showUser(platform);
            return;
        }
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.showUser(null);
        platform.authorize();
    }


    // 第三方登录监听回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        Message msg = Message.obtain();
        String icon = hashMap.get("figureurl_qq_2").toString();
        String name = hashMap.get("nickname").toString();
        User user = new User(name, icon);
        msg.what = LOGIN_SUCCESS;
        msg.obj = user;
        mHandler.sendMessage(msg);

        platform.getDb().put("user_icon", icon);

        Set<Map.Entry<String, Object>> entrySet = hashMap.entrySet();
        for (Map.Entry<String, Object> obj : entrySet) {
            LogUtil.d(obj.getKey() + ", " + obj.getValue());
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        platform.removeAccount(true);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        platform.removeAccount(true);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOGIN_SUCCESS:
                User user = (User) msg.obj;
                mNameTv.setText(user.getName());
                Glide.with(this)
                        .fromString()
                        .load(user.getIcon())
                        .bitmapTransform(new CropCircleTransformation(getContext()))
                        .into(mIconImage);
                mPayView.setVisibility(View.VISIBLE);
                break;
            case SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                LogUtil.d(resultInfo);
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(getContext(), "充值成功", Toast.LENGTH_SHORT).show();
                    mSeedTv.setText("" + (mCount += 100));
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case SDK_AUTH_FLAG: {
                @SuppressWarnings("unchecked")
                AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                String resultStatus = authResult.getResultStatus();

                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    Toast.makeText(getContext(),
                            "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // 其他状态值则为授权失败
                    Toast.makeText(getContext(),
                            "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                }
                break;
            }
            default:
                break;
        }
        return false;
    }


    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016080400163089";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe6Nv9E0k7u1X25tZa57cN81YSlfvFGlzd1IVc4onJ5NKGuCaR8m0VMbR6Dw8Ij+x0iebAU5I54obJzc/aDE5popTZWSo15QjbsZ9zDLfWgSoMP9o0exI5u7xaPTEwZw4JxPeSfn5NzYtBEnfdjiPofFWw1//ZowVySvRo682oTAZqR2fb3ZLH/NeCw8C1NaLuSfls8FDcTqmyUchFfqtfDJAPNlQ1/d1xBH+kQncdX3LRXZDfKjK4Vvwne7XG9xhWbruUb+giBRIYmrLnwoiAxa+O0AG16QNEbV9eNnSaG21SLJNPciA9gwyXtsc3Eu/lxrFxzCS1chPmysru6K9lAgMBAAECggEAH3Vdx5X/03FcrUo5eTmSBZL8oSVL+FMlu0yNMjwupudDviPKju39Jkr2vYspoLpNRyzdn4lr00XDBURXN4VkKIllCjoanxy+Si+5rx7/bdmYJ2Cko+sRSpidywd+K2TbZJe3oyqjemeMf89WJ2gyN54VoLLCFaWuOFgaVoMH7Z7/CELfm+z8J8/kP70J2hsWIlUvlfOyupBYA3pGAFvasRlCY3P/ZwU+2NsBl11zDvfrvSDukZP+ox2nlNwia11CZCpdjjlgLczZJVC6zeF1Z/r8jvXVsTu6IVNkXYJnro46dZN29nkQAHBqo7Bcg4f3Oe7WXPMfSxK0lRWuNnIuKQKBgQDfU4sA7AjQqlrYvm4wIUZJABnz/Iwo5b8JlnFmxnEFE6dvuwPM1A505ERMxEBYnUEXu+2p8W8+M10SPQbG8XUxthbamIXj0bnxSkSUM4nrk/lJcmYrTeU/ibt+X81ctDMnQ4/nav3y3zy5tW5x1kR5a04ZZzNk19uaJVoKEBW1zwKBgQC2KKd9LbK9NOA6XTrlPnpWT60LGW1+209eDOoFLbjIQPpE5cVQ+1EzoJB6fnksG22BdgfXuaCW2bz34DOsqd35YgPyBUm/WP6hDy1uQB09RsR+2w3VyxRkKtH4BdCA+HT7Cm4sOqRGHyoL7SedPk5PSwjd5WSIz2GFt5vciCeIiwKBgHiBGNlng54svhaEMurPmaBcaKSp2mtbBQlEX26Il4WYxFlavUMyDZbvcRdHMj1epTgMVMLFnzQd/RORUG3a7lTjn1NWx7BVg5L6cyTQ5pPyZapHC3BEePYe+MfJJAPozE1cfHLv3ZXG9XpztuPALXSi/SJh+G5qt3lmvD3/zecrAoGATIy3HhbXN0YCOOS2/GGOHblr+e8coaPeLaL1sWlubtFHmy6IBiknDbAbJy8BEvUPxsjoMNL0VpB4Jh7U+GmGO+fhypJIVJ48m0h6igttfjPAHaNGpCKslg+cn0MqRRikaF8Qv4uBP2qXS93EuQLW++GHf2CIKComTIBFgp7BJlMCgYEA3JgEa2tkWL9JgKvonqXg5AKbHuJAz9sotJMYHgCOPTedDxqH6I8jtwkxZ9pxVamom2aaNrZ0QoaEsjrRbRBsA+Le7qENKhCs4isYc5C4aj/ucgu75OCBmlvBZcmBmqWRyVGMu2xzYQ/sZ24XUY9AD3m367BUtRFA66oeWm1xZSA=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    /**
     * 支付宝支付业务
     */
    public void pay() {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA2_PRIVATE)) {
            new AlertDialog.Builder(getContext()).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            getActivity().finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        LogUtil.d(orderInfo);

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
