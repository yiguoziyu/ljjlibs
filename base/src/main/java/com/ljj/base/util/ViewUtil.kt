package com.common.lib.kit.util

import android.view.View
import android.view.ViewGroup


/**
 * Created by 一锅子鱼 on 2018/8/21.
 */
object ViewUtil {

    fun setSquare(view: View, mWidth: Int) {
        var params = view.layoutParams
        params.width = mWidth
        params.height = params.width
        view.layoutParams = params
    }

    fun setDimension(view: View, mWidth: Int, mHeight: Int) {
        var params = view.layoutParams
        params.width = mWidth
        params.height = mHeight
        view.layoutParams = params
    }

    fun setWidth(view: View, mWidth: Int) {
        var params = view.layoutParams
        params.width = mWidth
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        view.layoutParams = params
    }

    fun setHeight(view: View, mHeight: Int) {
        var params = view.layoutParams
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
        params.height = mHeight
        view.layoutParams = params
    }

    fun setMargin() {

    }


}