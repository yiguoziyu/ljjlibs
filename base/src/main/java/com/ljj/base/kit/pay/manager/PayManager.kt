package com.ljj.base.kit.pay.manager

import androidx.fragment.app.FragmentActivity
import com.ljj.base.helper.ToastHelper
import com.ljj.base.kit.pay.alipay.AliPay
import com.ljj.base.model.WxPayBean
import com.ljj.lettercircle.util.wx.WxManager

/**
 * 支付管理器
 */
class PayManager constructor(val context: FragmentActivity) {
    //微信管理器
    private var mWxManager: WxManager = WxManager(context)

    //开启微信支付
    var openWxFlag = true

    /**
     * 微信支付
     */
    fun wxPay(payBean: WxPayBean) {
        mWxManager.mWxPayManager.wxPay(payBean) { ToastHelper.showToast(it) }
    }

    /**
     * 支付宝支付
     */
    fun alipay(orderInfo: String, mPayCallback: AliPay.PayCallback) {
        val map = HashMap<String, String>()
        map["type"] = "alipay"
        val pay = AliPay(mPayCallback, context)
        pay.aliPay2(orderInfo)
    }
}