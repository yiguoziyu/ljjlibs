package com.ljj.commonlib.jectpack

import android.view.View




/**
 * 绑定显示
 */
fun View.bindVisibility(flag: Boolean) {
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


fun Boolean.bindVisiable(identitys: MutableList<View>? = null, unIdentitys: MutableList<View>? = null, isInVisible: Boolean = false) {
    identitys?.forEachIndexed { _, view ->
        view.visibility = if (this) {
            View.VISIBLE
        } else {
            if (isInVisible) {
                View.INVISIBLE
            } else {
                View.GONE
            }
        }
    }
    unIdentitys?.forEachIndexed { _, view ->
        view.visibility = if (this) {
            View.GONE
        } else {
            if (isInVisible) {
                View.INVISIBLE
            } else {
                View.GONE
            }
        }
    }
}


fun View.OnClickListener.bindView(view: View) {
    view.setOnClickListener(this)
}

fun View.OnClickListener.bindViews(vararg view: View) {
    view.forEachIndexed { index, view ->
        view.setOnClickListener(this)
    }
}


