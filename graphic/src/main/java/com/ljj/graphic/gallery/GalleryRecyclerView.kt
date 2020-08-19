package com.ljj.graphic.gallery

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView


/**
 * 中间放大的RecyclerView
 */
class GalleryRecyclerView : RecyclerView {
    private var mHeight = 0f
    private var mWidth = 0f
    private lateinit var mPaint: Paint

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initConfig()
    }

    private fun initConfig() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        val spanFactor: Float = 100 / (mHeight / 2f)
        val linearGradient = LinearGradient(
            0f,
            0f,
            mWidth / 2f,
            0f,
            intArrayOf(0x00000000, -0x1000000, -0x1000000),
            floatArrayOf(0f, spanFactor, 1f),
            Shader.TileMode.MIRROR
        )
        mPaint.shader = linearGradient
    }

    override fun draw(c: Canvas?) {
        c?.saveLayer(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        super.draw(c)
        c?.drawRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), mPaint)
        c?.restore()
    }
}