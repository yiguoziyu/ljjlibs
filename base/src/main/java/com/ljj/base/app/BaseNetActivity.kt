package com.ljj.base.app

import android.os.Bundle
import com.common.lib.kit.net.NetStatusLifecycle

abstract class BaseNetActivity : BaseActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        lifecycle.addObserver(NetStatusLifecycle)
    }

}