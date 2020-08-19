package com.ljj.commonlib.model

import com.ljj.commonlib.http.HTTP_ERROR


fun createErrorResponse(errorMsg: String?) = BaseResponse<String>(
        HTTP_ERROR, errorMsg
        ?: "请求失败", "")

data class BaseResponse<T>(
        val Flag: Int,
        val FlagString: String,
        var data:T
)