package com.ljj.graphic.gallery

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.common.lib.R
import com.ljj.graphic.util.WindowUtil
import uk.co.senab.photoview.PhotoViewAttacher

class GalleryAdapter : RecyclerView.Adapter<GralleryViewHolder>() {
    companion object {
        private const val TAG = "GalleryAdapter"
        private const val THRESHOLDVALUE = 100
    }

    private val dataList = mutableListOf<String>()
    private var mItemClick: ItemClick? = null
    private var mHideFlag = false
    fun setItemClick(mItemClick: ItemClick) {
        this.mItemClick = mItemClick
    }

    fun setData(mDataList: MutableList<String>) {
        this.dataList.clear()
        this.dataList.addAll(mDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GralleryViewHolder {
        return GralleryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_grallery, parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: GralleryViewHolder, position: Int) {
        holder.mNestedscrollview?.let {
            it.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
                if (scrollY > THRESHOLDVALUE) {
                    if (!mHideFlag) {
                        mHideFlag = true
                        mItemClick?.onHideBottomView(mHideFlag)
                    }
                } else {
                    if (mHideFlag) {
                        mHideFlag = false
                        mItemClick?.onHideBottomView(mHideFlag)
                    }
                }
            }
        }
        holder.mImageView?.let {
            val context = it.context
            //设置图片最小高度
            it.minimumHeight = WindowUtil.getScreenHeight(context)
            when (getUrlType(dataList[position])) {
                is GalleryBean.GalleryGifImageTarget -> {
                    Glide.with(context)
                        .load(dataList[position])
                        .into(it)
                }
                else -> {
                    Glide.with(context)
                        .asBitmap()
                        .load(dataList[position])
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onLoadCleared(placeholder: Drawable?) {
                            }

                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                it.setBitmap(resource)
                            }
                        })
                }
            }
            it.setOnPhotoTapListener(object : PhotoViewAttacher.OnPhotoTapListener {
                override fun onPhotoTap(view: View?, x: Float, y: Float) {
                    mItemClick?.onFinish()
                }

                override fun onOutsidePhotoTap() {
                    mItemClick?.onFinish()
                }

            })
        }

    }

}

interface ItemClick {
    fun onFinish()
    fun onHideBottomView(flag: Boolean)
}

class GralleryViewHolder internal constructor(private val view: View) :
    RecyclerView.ViewHolder(view) {
    var mImageView: AutoImageView? = null
    var mNestedscrollview: NestedScrollView? = null

    init {
        mImageView = view.findViewById(R.id.imgage)
        mNestedscrollview = view.findViewById(R.id.nestedscrollview)
    }

}