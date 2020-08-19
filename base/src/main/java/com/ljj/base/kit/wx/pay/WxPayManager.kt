package com.ljj.base.kit.wx.pay

import android.content.Context
import com.ljj.base.model.WxPayBean
import com.ljj.lettercircle.util.wx.WxManager
import com.tencent.mm.opensdk.modelpay.PayReq

/**
 * 微信支付管理器
 */
class WxPayManager(val wxManager: WxManager, val context: Context) {
    /***微信支付***/
    fun wxPay(orderInfo: WxPayBean, error: (error: String) -> Unit = {}) {
        val msgApi = wxManager.getIWXAPI(context)
        if (wxManager.isWXAppInstalled(msgApi)) {
            val map = HashMap<String, String>()
            map["type"] = "wxPay"
            val request = PayReq()
            request.appId = orderInfo.appid
            request.partnerId = orderInfo.partnerid
            request.prepayId = orderInfo.prepayid
            request.packageValue = orderInfo.packageX
            request.nonceStr = orderInfo.noncestr
            request.timeStamp = orderInfo.timestamp
            request.extData = orderInfo.extData
            request.sign = orderInfo.sign
            msgApi.sendReq(request)
        } else {
            error("未安装微信")
        }
    }
}