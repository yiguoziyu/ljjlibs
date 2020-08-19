package com.ljj.base.app

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ljj.base.R
import com.ljj.base.manager.AppStackManagerLifecycleObserver
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper

/**
 * 基类Activity
 */
abstract class BaseActivity : AppCompatActivity(), SwipeBackActivityBase {
    private lateinit var mHelper: SwipeBackActivityHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        lifecycle.addObserver(AppStackManagerLifecycleObserver())
        initSystemBarTinit()
        if (enableSwipeBack()) {
            initSwipeBack()
        }
        initView(savedInstanceState)
        initViewModels()
        initData()
    }
    abstract fun getLayoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
    open fun initViewModels() {}

    //----------------------------------------沉浸式API---------------------------------------------//

    /***初始化沉浸式***/
    open fun initSystemBarTinit() {
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


    //----------------------------------------侧滑API---------------------------------------------//
    /**
     * 侧滑开关
     */
    open fun enableSwipeBack(): Boolean {
        return true
    }

    /**
     * 初始化侧滑回退
     */
    private fun initSwipeBack() {
        mHelper = SwipeBackActivityHelper(this)
        mHelper.onActivityCreate()
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }

    override fun getSwipeBackLayout(): SwipeBackLayout = mHelper.swipeBackLayout
    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }


}