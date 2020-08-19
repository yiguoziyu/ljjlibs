package com.ljj.commonlib.jectpack.state

import com.ljj.commonlib.model.BaseResponse


sealed class ResponseState(var response: BaseResponse<out Any?>) {
    class HttpSuccess(response: BaseResponse<out Any?>) : ResponseState(response)
    class HttpFail(response: BaseResponse<out Any?>) : ResponseState(response)
    class HttpSpecial(response: BaseResponse<out Any?>) : ResponseState(response)
}

