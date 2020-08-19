package com.ljj.commonlib.jectpack.state

/**
 * 页面显示状态
 */
sealed class ViewState(val msg: String) {
    //页面
    class ViewFail(msg: String = "") : ViewState(msg)

    class ViewContent(msg: String = "") : ViewState(msg)
    class ViewEmpty(msg: String = "") : ViewState(msg)
    class ViewProgress(msg: String = "") : ViewState(msg)
    //loadingDialog
    class LoadingShow(msg: String = "") : ViewState(msg)

    class LoadingDismiss(msg: String = "") : ViewState(msg)

}

