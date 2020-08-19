package com.common.lib.kit.util

import android.util.Base64
import java.io.UnsupportedEncodingException

object Base64Utils {
    /**
     * 字符Base64加密
     * @param str
     * @return
     */
    fun encodeToString(str: String): String? {
        try {
//            return Base64.encodeToString(str.toByteArray(charset("UTF-8")), Base64.DEFAULT)
            return Base64.encodeToString(str.toByteArray(), Base64.DEFAULT)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 字符Base64解密
     * @param str
     * @return
     */
    fun decodeToString(str: String): String? {
        try {
            return String(Base64.decode(str.toByteArray(charset("UTF-8")), Base64.DEFAULT))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }
}