package com.ljj.graphic.compression

import android.content.Context
import java.io.File

public interface CompressCallBack {
    /**
     * Fired when the compression is started, override to handle in your own code
     */
    abstract fun onStart(postion: Int?)

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    abstract fun onSuccess(file: File?, postion: Int?)

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    abstract fun onError(e: Throwable?, postion: Int?)
}

interface CompressionInterface {
    fun compression(context: Context, mCurrentFile: String?, compressCallBack: CompressCallBack?,postion:Int?=null)
}