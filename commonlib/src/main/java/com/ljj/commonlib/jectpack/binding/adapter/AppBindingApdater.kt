package com.ljj.commonlib.jectpack.binding.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object AppBindingApdater {
    @JvmStatic
    @BindingAdapter(value = ["distance", "unit"], requireAll = true)
    fun location(view: TextView, distance: String?, unit: String?) {
        var data = ""
        when (distance) {
            "0.0", "0","",null -> {
                view.visibility = View.GONE
            }
            "-1" -> {
                data += "保密"
                view.visibility = View.VISIBLE
            }
            else -> {
                data += "" + distance + unit
                view.visibility = View.VISIBLE
            }
        }
        view.text = data
    }

    @JvmStatic
    @BindingAdapter(value = ["valueVisibility"], requireAll = true)
    fun valueVisibility(view: TextView, value: String?) {
        if (value.isNullOrEmpty()) {
            view.visibility = View.GONE
        }else{
            view.text=value
            view.visibility = View.VISIBLE
        }
    }
}