package com.ljj.base.ex

import android.text.TextUtils
import android.widget.EditText


//Edit
fun EditText.value() = text.toString()
fun EditText.valueTrim() = value().trim()
fun EditText.isNotEmpty() = value().isNotEmpty()
fun EditText.isEmpty() = value().isEmpty()
fun EditText.isEmpty(func: () -> Unit) {
    if (value().isEmpty()) {
        func()
    }
}

fun EditText.checkPhone(): Boolean {
    val number: String = valueTrim()
    val mRegex = Regex("[1]\\d{10}" )
    return if (TextUtils.isEmpty(number)) {
        false
    } else {
        //matches():字符串是否在给定的正则表达式匹配
        mRegex.matches(number)
    }
}


