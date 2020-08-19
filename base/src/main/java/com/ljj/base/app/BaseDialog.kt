package com.ljj.base.app

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

/**
 * 基类Dialog
 */
abstract class BaseDialog : DialogFragment() {
    companion object {
        private const val TAG = "BaseDialog"
    }

    //点击外部取消
    private var isCanceledOnTouchOutside = true

//    //弹框位置
//    private var mDialogLocation: DialogLocation = DialogLocation.CENTER
//
//    //弹框大小
//    private var mDialogSize: DialogSize = DialogSize.NORMAL


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(getLayoutId(), container, false)
        initViewModels()
        initViewData(mView)
        return mView
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    abstract fun getLayoutId(): Int
    abstract fun initViewData(mView: View)
    open fun initViewModels(){}
    fun showDialog() {

    }


    fun setCanceledOnTouchOutSide(isCanceledOnTouchOutside: Boolean): BaseDialog {
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside
        return this
    }


    /**
     * 设置背景透明
     */
    fun setBackgroundTransparent() {
        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0.0f
    }

    /**
     * 设置dialog大小
     */
    fun setDialgSize(width: Int? = null, height: Int?): BaseDialog {
        dialog?.window?.let {
            val dm = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)
            it.setLayout(width ?: ViewGroup.LayoutParams.WRAP_CONTENT, height
                    ?: ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return this
    }

    /**
     * 设置通用大小
     */
    fun setCommonDialogSize(): BaseDialog {
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        setDialgSize((0.75 * dm.widthPixels).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        return this
    }

    /**
     * 设置宽全屏大小
     */
    fun setFullWidthDialogSize(): BaseDialog {
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        setDialgSize(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT)
        return this
    }

    /**
     * Gravity.CENTER
     * Gravity.BOTTOM
     * Gravity.TOP
     * Gravity.LEFT
     * Gravity.RIGHT
     */
    fun setDialogLayoutParams(gravity: Int, func: WindowManager.LayoutParams.() -> Unit = {}) {
        dialog?.window?.let {
            val mLayoutParams = it.attributes
            mLayoutParams.gravity = gravity
            mLayoutParams.func()
            it.attributes = mLayoutParams
        }
    }

    /**
     * 去除黑色背景
     */
    fun transpatentDialog() {
        //背景改透明
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        //去除半透明阴影
        val layoutParams = dialog?.window?.attributes
        layoutParams?.dimAmount = 0.0f
        dialog?.window?.attributes = layoutParams
    }
//
//    fun setDialogLocation(mDialogLocation: DialogLocation): BaseDialog {
//        this.mDialogLocation = mDialogLocation
//        return this
//    }
//
//    fun setDialogSize(mDialogSize: DialogSize): BaseDialog {
//        this.mDialogSize = mDialogSize
//        return this
//    }

//    enum class DialogLocation {
//        BOTTOM, LEFT, RIGHT, CENTER, TOP
//    }
//
//    enum class DialogSize {
//        FULL, FULL_HEIGHT, FULL_WIDTH, NORMAL
//    }
}