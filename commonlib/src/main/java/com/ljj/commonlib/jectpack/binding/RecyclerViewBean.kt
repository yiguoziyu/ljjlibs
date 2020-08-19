package com.ljj.commonlib.jectpack.binding

class RecyclerViewBean(var type: Class<out Any>, var layoutId: Int, var viewType: RecyclerViewType = RecyclerViewType.CONETNT)

enum class RecyclerViewType {
    HEADER,
    CONETNT,
    FOOTER
}