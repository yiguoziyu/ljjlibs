package com.common.lib.kit.net

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*

object NetStatusLifecycle : DefaultLifecycleObserver {
    private const val TAG = "NetStatusLifecycle"
    private var mNetBroadcastReceiver: NetBroadcastReceiver? = null
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        try {
            mNetBroadcastReceiver = NetBroadcastReceiver()
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            if (owner is AppCompatActivity) {
                owner.registerReceiver(mNetBroadcastReceiver, filter)
            } else if (owner is Fragment) {
                Objects.requireNonNull(owner.activity)?.registerReceiver(mNetBroadcastReceiver, filter)
            }
        } catch (e: Exception) {
            Log.e(TAG, "onResume==>e:${e.message}")
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        try {
            if (owner is AppCompatActivity) {
                owner.unregisterReceiver(mNetBroadcastReceiver)
            } else if (owner is Fragment) {
                Objects.requireNonNull(owner.activity)
                        ?.unregisterReceiver(mNetBroadcastReceiver)
            }
        } catch (e: Exception) {
            Log.e(TAG, "onPause==>e:${e.message}")
        }
    }
}