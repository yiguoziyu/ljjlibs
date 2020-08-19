package com.ljj.base.manager

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.ljj.base.manager.AppStackManager

class AppStackManagerLifecycleObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (owner is FragmentActivity)
            AppStackManager.addActivity(owner)
        else if (owner is Activity)
            AppStackManager.addActivity(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        if (owner is FragmentActivity)
            AppStackManager.removeActivity(owner)
        else if (owner is Activity)
            AppStackManager.removeActivity(owner)
    }
}