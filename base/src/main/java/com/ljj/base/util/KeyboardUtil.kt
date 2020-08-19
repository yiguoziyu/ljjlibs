package com.common.lib.kit.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

object KeyboardUtil {
    fun showKeyboard(editText: View) {
        editText.postDelayed({
            editText.requestFocus()
            val manager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(editText, 0)
        }, 50)
    }

    fun hideKeyboard(editText: View) {
        val manager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun hideKeyboard(activity: FragmentActivity) {
        val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}