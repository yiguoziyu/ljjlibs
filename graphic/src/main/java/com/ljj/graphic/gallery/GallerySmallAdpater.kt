package com.ljj.graphic.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.common.lib.R

class GallerySmallAdpater : RecyclerView.Adapter<GallerySmallAdpater.GallerySmallViewHodler>() {

    companion object {
        private const val TAG = "GallerySmallAdpater"
    }

    private val dataList = mutableListOf<GalleryImage>()
    private var mGallertSmallCallBack: GallertSmallCallBack? = null


    fun setGallertSmallCallBack(mGallertSmallCallBack: GallertSmallCallBack?) {
        this.mGallertSmallCallBack = mGallertSmallCallBack
    }

    fun setData(mDataList: MutableList<GalleryImage>) {
        this.dataList.clear()
        this.dataList.addAll(mDataList)
        notifyDataSetChanged()
    }

    fun setCurrentItem(pos: Int) {
        if (dataList.size > pos && pos >= 0) {
            dataList.forEachIndexed { index, galleryImage ->
                dataList[index].isSelect = index == pos
            }
        }
        notifyDataSetChanged()
    }

    class GallerySmallViewHodler(val view: View) : RecyclerView.ViewHolder(view) {
        var mImageView: ImageView? = null
        var mRootView: RelativeLayout? = null

        init {
            mImageView = view.findViewById(R.id.imgage)
            mRootView = view.findViewById(R.id.rootview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GallerySmallViewHodler {
        return GallerySmallViewHodler(
            LayoutInflater.from(parent.context).inflate(R.layout.item_small_gallery, parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size

    private fun driveGlideByType(url: String?,view: ImageView) {
        when (getUrlType(url)) {
            is GalleryBean.GalleryGifImageTarget-> Glide.with(view).asGif().load(url).into(view)
            else-> Glide.with(view).asBitmap().load(url).into(view)
        }
    }

    override fun onBindViewHolder(holder: GallerySmallViewHodler, position: Int) {
        val bean = dataList[position]
        holder.mImageView?.let {
            driveGlideByType(bean.url,it)
            val marginWidth = dp2px(it.context, 120f)
            val topHeight = if (bean.isSelect) 0 else dp2px(it.context, 20f)
            val lp = it.layoutParams as RelativeLayout.LayoutParams
            lp.removeRule(RelativeLayout.CENTER_IN_PARENT)
            lp.removeRule(RelativeLayout.ALIGN_PARENT_LEFT)
            lp.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            when (position) {
                0 -> {
                    lp.setMargins(marginWidth, 0, 5, topHeight)
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                    lp.addRule(RelativeLayout.CENTER_VERTICAL)
                }
                dataList.size - 1 -> {
                    lp.setMargins(5, 0, marginWidth, topHeight)
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                    lp.addRule(RelativeLayout.CENTER_VERTICAL)
                }
                else -> {
                    lp.setMargins(5, 0, 5, topHeight)
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT)
                }
            }
            it.layoutParams = lp
        }
        holder.mRootView?.isSelected = bean.isSelect
        holder.itemView.setOnClickListener {
            mGallertSmallCallBack?.setItemClick(holder.bindingAdapterPosition)
        }

    }

    interface GallertSmallCallBack {
        fun setItemClick(position: Int)
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dpValue * density + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val density = context.resources.displayMetrics.density
        return (pxValue / density + 0.5f).toInt()
    }
}