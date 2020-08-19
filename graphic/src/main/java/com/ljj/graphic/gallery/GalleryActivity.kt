package com.ljj.graphic.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.common.lib.R
import kotlinx.android.synthetic.main.activity_grallery.*


/***
 * 画廊
 */
class GalleryActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GalleryActivity"
        private const val POSTION = "postion"
        private const val DATA = "data"
        fun openGalleryPage(
                context: Context?,
                postion: Int,
                dataList: ArrayList<String>
        ) {
            context?.startActivity(Intent(context, GalleryActivity::class.java).apply {
                putExtra(POSTION, postion)
                putExtra(DATA, dataList)
            })
        }
    }

    private lateinit var mSmallAdapter: GallerySmallAdpater
    private lateinit var dataList: MutableList<String>
    private var postion: Int = 0
    private var isNeedSmallView = true

    /**
     * 底部小图
     */
    private fun initSmallView() {
        mSmallAdapter = GallerySmallAdpater()
        rv_small.layoutManager = CenterLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_small.adapter = mSmallAdapter
        mSmallAdapter.setGallertSmallCallBack(object : GallerySmallAdpater.GallertSmallCallBack {
            override fun setItemClick(position: Int) {
                smallRvSetCurrentIndex(position)
                viewPager2.setCurrentItem(position, false)
            }
        })
        mSmallAdapter.setData(dataList.map { GalleryImage(it) } as MutableList<GalleryImage>)
    }

    /**
     * 主要内容大图
     */
    private fun initPrimView() {
        val mAdapter = GalleryAdapter()
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = mAdapter
        mAdapter.setData(dataList)
        mAdapter.setItemClick(object : ItemClick {
            override fun onFinish() {
                finish()
            }

            override fun onHideBottomView(flag: Boolean) {
                if (flag) {
                    hideBottomView()
                } else {
                    showBottomView()
                }
            }
        })
        viewPager2.setCurrentItem(postion, false)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                smallRvSetCurrentIndex(position)
                showBottomView()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grallery)
        initSystemBarTinit()
        postion = intent.getIntExtra(POSTION, 0)
        dataList = intent.getStringArrayListExtra(DATA)
        isNeedSmallView = dataList.size > 1
        if (isNeedSmallView) {
            initSmallView()
        }
        initPrimView()
        smallRvSetCurrentIndex(postion)
    }
    /***初始化沉浸式***/
    private fun initSystemBarTinit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = true
                field.setInt(window.decorView, Color.TRANSPARENT)  //改为透明
                window.navigationBarColor = resources.getColor(R.color.black)
            } catch (e: Exception) {
            }
        }
    }
    private fun smallRvSetCurrentIndex(position: Int) {
        tv_page_number.text = "${position + 1}/${dataList.size}"
        if (isNeedSmallView) {
            rv_small.smoothScrollToPosition(position)
            mSmallAdapter.setCurrentItem(position)
        }
    }

    private fun hideBottomView() {
        if (isNeedSmallView) {
            rv_small.animate().translationY(rv_small.height + 40f)
        }
    }

    private fun showBottomView() {
        if (isNeedSmallView) {
            rv_small.animate().translationY(0f)
        }
    }
}