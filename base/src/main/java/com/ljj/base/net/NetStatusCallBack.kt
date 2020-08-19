package com.common.lib.kit.net

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
open class NetStatusCallBack : ConnectivityManager.NetworkCallback() {
    /**
     * 网络连接成功，通知可以使用的时候调用
     */
    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
    }

    /**
     * 当网络连接的属性被修改时调用
     */
    override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
        super.onLinkPropertiesChanged(network, linkProperties)

    }
    /**
     * 当网络已断开连接时调用
     */
    override fun onLost(network: Network?) {
        super.onLost(network)
    }

    /**
     * 当网络正在断开连接时调用
     */
    override fun onLosing(network: Network?, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)

    }
    /**
     * 当网络状态修改但仍旧是可用状态时调用
     */
    override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
        super.onCapabilitiesChanged(network, networkCapabilities)
    }

    /**
     * 当网络连接超时或网络请求达不到可用要求时调用
     */
    override fun onUnavailable() {
        super.onUnavailable()

    }
}