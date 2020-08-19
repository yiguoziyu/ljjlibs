package com.ljj.graphic.compression

import android.content.Context
import android.text.TextUtils
import android.util.Log
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

object LubanManager : CompressionInterface {

    private const val TAG = "LubanManager"
    override fun compression(context: Context, mCurrentFile: String?, compressCallBack: CompressCallBack?, postion: Int?) {
        Luban.with(context)
                .load(mCurrentFile)
                .ignoreBy(100)
                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .setCompressListener(object : OnCompressListener {
                    override fun onSuccess(file: File?) {
                        Log.e(TAG, "compress onSuccess==>${file?.absolutePath}")
                        compressCallBack?.onSuccess(file, postion)
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "compress onError==>${e?.message}")
                        compressCallBack?.onError(e, postion)
                    }

                    override fun onStart() {
                        Log.e(TAG, "compress onStart")
                        compressCallBack?.onStart(postion)
                    }

                }).launch()
    }

}

