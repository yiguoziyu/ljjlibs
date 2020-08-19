package com.common.lib.jetpack

sealed class LoadState(val msg: String) {
    class ProgressShow(msg: String = "") : LoadState(msg)
    class Success(msg: String = "") : LoadState(msg)
    class Fail(msg: String = "") : LoadState(msg)
    class ProgressDissmiss(msg: String = "") : LoadState(msg)
}