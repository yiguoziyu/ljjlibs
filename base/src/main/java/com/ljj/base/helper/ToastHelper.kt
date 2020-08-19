package com.ljj.base.helper

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.ljj.base.R
import com.ljj.base.context.ContextProvider

object ToastHelper {
     fun showToast(mContentRes: Int) {
        val mContext = ContextProvider.get().application
         showToast(mContext.resources.getString(mContentRes))
    }
     fun showToast(mContent: String) {
        val mContext = ContextProvider.get().application
        var toast = Toast.makeText(mContext, mContent, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)//设置位置
        val ll = LayoutInflater.from(mContext).inflate(R.layout.toast_style, null)
        var tx = ll.findViewById<TextView>(R.id.toast_content)
        tx.text = mContent
        toast.view = ll//设置外观
        toast.show()
    }
    fun showLongToast(mContent: String) {
        val mContext = ContextProvider.get().application
        var toast = Toast.makeText(mContext, mContent, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)//设置位置
        val ll = LayoutInflater.from(mContext).inflate(R.layout.toast_style, null)
        var tx = ll.findViewById<TextView>(R.id.toast_content)
        tx.text = mContent
        toast.view = ll//设置外观
        toast.show()

    }
}