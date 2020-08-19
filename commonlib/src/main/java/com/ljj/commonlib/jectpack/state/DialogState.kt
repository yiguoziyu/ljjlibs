package com.ljj.commonlib.jectpack.state

import com.ljj.commonlib.model.BaseResponse


class DialogState(val code: Int, val open: Boolean = true, val data: String? = null, val response: BaseResponse<out Any?>? = null)

