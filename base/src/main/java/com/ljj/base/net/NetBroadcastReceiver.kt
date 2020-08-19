package com.common.lib.kit.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NetBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "NetBroadcastReceiver"
    }

    //网络发生变化时调用
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e(TAG, "onReceive===>")
        context?.let {
            NetUtil.getNetWorkState(it)
        }
    }
}