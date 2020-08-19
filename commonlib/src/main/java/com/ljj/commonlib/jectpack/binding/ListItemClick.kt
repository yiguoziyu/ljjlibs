package com.ljj.commonlib.jectpack.binding

import android.view.View

interface ListItemClick<T> {
    fun onItemClick(view: View, data: T?)
}