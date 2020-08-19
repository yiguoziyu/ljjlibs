package com.ljj.graphic.gallery

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.ljj.base.util.WindowUtil
import uk.co.senab.photoview.PhotoViewAttacher

class AutoImageView : AppCompatImageView {

    private var mAttacher: PhotoViewAttacher? = null

    companion object {
        private const val TAG = "AutoImageView"
    }

    //真正的宽
    private var realWidth = 0

    //真正的高
    private var realHeight = 0

    //获取网络图片宽
    private var netWidth = 0

    //获取网络图片高
    private var netHeight = 0

    //获取屏幕宽
    private val screenWidth = WindowUtil.getScreenWidth(context)

    //获取屏幕高
    private val screenHeight = WindowUtil.getScreenHeight(context)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        initData()
    }

    private fun initData() {
        if (null == mAttacher || null == mAttacher!!.imageView) {
            mAttacher = PhotoViewAttacher(this)
        }

    }

    fun setBitmap(resource: Bitmap) {
        minimumHeight = screenHeight
        netWidth = resource.width
        netHeight = resource.height
        realWidth = screenWidth
        realHeight = screenWidth * netHeight / netWidth
        val lp = layoutParams
        lp.width = realWidth
        if (realHeight < screenHeight) {
            realHeight = screenHeight
        }
        lp.height = realHeight
        layoutParams = lp
        setImageBitmap(resource)
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        val changed = super.setFrame(l, t, r, b)
        mAttacher?.update()
        return changed
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initData()
    }

    fun setOnPhotoTapListener(mOnPhotoTapListener: PhotoViewAttacher.OnPhotoTapListener) {
        mAttacher?.setOnPhotoTapListener(mOnPhotoTapListener)
    }

}