package com.ljj.base.kit.pay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.ljj.base.kit.pay.alipay.util.OrderInfoUtil2_0;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by 一锅子鱼 on 2018/4/24.
 */

public class AliPay {
    private PayCallback payCallback;

    public interface PayCallback {
        void aliPaySuc(String resultInfo);

        void aliPayError(String error);
    }

    private String APPID = "";
    private String RSA2_PRIVATE = "";
    private String RSA_PRIVATE = "";
    private Activity mActivity;

    public AliPay(Activity mActivity, String APPID, String RSA2_PRIVATE, String RSA_PRIVATE, PayCallback payCallback) {
        this.mActivity = mActivity;
        this.APPID = APPID;
        this.RSA2_PRIVATE = RSA2_PRIVATE;
        this.RSA_PRIVATE = RSA_PRIVATE;
        this.payCallback = payCallback;
    }

    public AliPay(PayCallback payCallback, Activity mActivity) {
        this.payCallback = payCallback;
        this.mActivity = mActivity;
    }

    public void aliPay(String money, String subject, String body) {
        checkConfig();
        String orderInfo = alipayRepare(money, subject, body);
        alipayskie(orderInfo);
    }

    public void aliPay2(String orderInfo) {
        alipayskie(orderInfo);
    }

    private void checkConfig() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(mActivity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {

                        }
                    }).show();
            return;
        }
    }

    private String alipayRepare(String money, String subject, String body) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, money, subject, body);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        String orderInfo = orderParam + "&" + sign;
        return orderInfo;
    }

    /**
     * 要求外部订单号必须唯一。
     *
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    @SuppressLint("CheckResult")
    private void alipayskie(final String orderInfo) {
        Observable.create(emitter -> {
            PayTask alipay = new PayTask(mActivity);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            emitter.onNext(result);
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) o);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            payCallback.aliPaySuc(resultInfo);
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        } else if (TextUtils.equals(resultStatus, "6001")){
                            payCallback.aliPayError("您已取消充值");
                        }else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            payCallback.aliPayError("充值失败");
                        }
                    }
                });


    }
}
