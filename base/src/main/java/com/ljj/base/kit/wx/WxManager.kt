package com.ljj.lettercircle.util.wx

import android.content.Context
import com.ljj.base.kit.wx.pay.WxPayManager
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * 微信管理类
 */
class WxManager {
    var mWxPayManager: WxPayManager

    constructor(context: Context) {
        mWxPayManager = WxPayManager(this, context)
    }

    fun getIWXAPI(context: Context): IWXAPI {
        return WXAPIFactory.createWXAPI(context, null)
    }

    /**
     * 是否安装微信
     */
    fun isWXAppInstalled(mIWXAPI: IWXAPI): Boolean {
        return mIWXAPI.isWXAppInstalled
    }
}