package com.ljj.commonlib.jectpack.state

/**
 * 页面显示状态
 */
sealed class RefreshState(val msg: String) {
    //RefreshView
    class RefreshStart(msg: String = "") : RefreshState(msg)
    class RefreshFinish(msg: String = "") : RefreshState(msg)
    class LoadNoMoreData(msg: String = "") : RefreshState(msg)
    class LoadFinish(msg: String = "") : RefreshState(msg)
}

