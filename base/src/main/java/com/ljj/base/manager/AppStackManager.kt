package com.ljj.base.manager

import android.app.Activity
import android.util.Log
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 管理Actiivty的栈
 */
object AppStackManager {
    private const val TAG = "AppStackManager"

    //栈
    private val mStack by lazy { Stack<Activity>() }

    /***将activity压入栈中***/
    fun addActivity(activity: Activity?) = activity?.let { mStack.add(it);Log.e(TAG, "入栈==>${activity.javaClass.simpleName}") }

    /***将activity移出栈***/
    fun removeActivity(activity: Activity?) = activity?.let { mStack.remove(it);Log.e(TAG, "出栈==>${activity.javaClass.simpleName}") }

    /***移除并结束activity***/
    fun finishActivity(activity: Activity?) {
        activity?.let {
            removeActivity(it)
            it.finish()
        }
    }

    /***移除指定类名的activty***/
    fun finishActivity(activity: Class<Activity>?) {
        activity?.let {
            CopyOnWriteArrayList(mStack).forEachIndexed { index, act ->
                if (act.javaClass == it) {
                    finishActivity(act)
                    return@forEachIndexed
                }
            }
        }
    }

    /***移除指定类名的activty***/
    fun finishActivitys(vararg activity: Class<Activity>?) {
        var number = activity.size
        CopyOnWriteArrayList(mStack).forEachIndexed { index, act ->
            activity.forEach {
                if (act.javaClass == it) {
                    finishActivity(act)
                    number--
                    if (number == 0) return@forEachIndexed
                }
            }
        }
    }

    /***移除所有有activity除了指定的activity***/
    fun finishOtherActivity(activity: Class<Activity>) {
        activity.let {
            CopyOnWriteArrayList(mStack).forEachIndexed { index, act ->
                if (act.javaClass != it) {
                    finishActivity(act)
                }
            }
        }
    }

    /***删除所有的activity***/
    fun finishAllActivity() {
        while (!mStack.isEmpty()) {
            mStack.pop().finish()
        }
    }

    /***获取当前activity的位置***/
    fun searchActivity(activity: Activity?): Int = if (activity == null) -1 else mStack.search(activity)

    /***定制***/
    fun customAction(func: Stack<Activity>.() -> Unit) {
        mStack.func()
    }

}