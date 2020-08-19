package com.ljj.commonlib.jectpack.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ljj.commonlib.http.HTTP_SUC
import com.ljj.commonlib.jectpack.launch
import com.ljj.commonlib.jectpack.state.RefreshState
import com.ljj.commonlib.jectpack.state.ResponseState
import com.ljj.commonlib.jectpack.state.ViewState
import com.ljj.commonlib.model.BaseResponse
import com.ljj.commonlib.model.createErrorResponse
import retrofit2.Response
private const val TAG="DataSourceHelper"
fun <Value> PageKeyedDataSource<Int, Value>.refresh(response: () -> Response<BaseResponse<List<Value>>>,
                                                    viewState: MutableLiveData<ViewState>? = null,
                                                    refreshState: MutableLiveData<RefreshState>? = null,
                                                    responsState: MutableLiveData<ResponseState>? = null,
                                                    callback: PageKeyedDataSource.LoadInitialCallback<Int, Value>,
                                                    nextPageKey: Int, limitSize: Int = 0) {
    try {
        val res = response()
        Log.e(TAG,"refresh ========================>1")
        if (res.isSuccessful) {
            Log.e(TAG,"refresh ========================>2")
            val body = res.body()
            Log.e(TAG,"refresh ========================>3")
            body?.let {
                Log.e(TAG,"refresh ========================>4")
                if (it.Flag == HTTP_SUC) {
                    Log.e(TAG,"refresh ========================>5")
                    responsState?.postValue(ResponseState.HttpSuccess(it))
                } else {
                    Log.e(TAG,"refresh ========================>6")
                    responsState?.postValue(ResponseState.HttpSpecial(it))
                }
            }
            Log.e(TAG,"refresh ========================>7")
            val data = body?.data ?: emptyList()
            Log.e(TAG,"refresh ========================>8")
            if (data.isEmpty()) {
                Log.e(TAG,"refresh ========================>9")
                viewState?.postValue(ViewState.ViewEmpty())
            } else {
                Log.e(TAG,"refresh ========================>10")
                viewState?.postValue(ViewState.ViewContent())
            }
            Log.e(TAG,"refresh ========================>11")
            val dataList = when {
                limitSize==0->data
                data.isNotEmpty() && data.size > limitSize&&limitSize>0 -> {
                    data.subList(0, limitSize)
                }
                data.isNotEmpty() && data.size < limitSize&&limitSize>0  -> {
                    data.subList(0, data.size)
                }
                else -> data
            }
            Log.e(TAG,"refresh ========================>12")
            callback.onResult(dataList, null, nextPageKey)
            Log.e(TAG,"refresh ========================>13")
            refreshState?.postValue(RefreshState.RefreshFinish())
            Log.e(TAG,"refresh ========================>14")
        } else {
            Log.e(TAG,"refresh ========================>15")
            viewState?.postValue(ViewState.ViewFail())
        }
    } catch (e: Exception) {
        Log.e(TAG,"refresh ========================>1")
        Log.e(TAG,"refresh error :${e.message}")
        viewState?.postValue(ViewState.ViewFail())
//        responsState?.postValue(ResponseState.HttpFail(createErrorResponse(e.message)))
    }
}

fun <Value> PageKeyedDataSource<Int, Value>.load(response: suspend () -> BaseResponse<List<Value>>,
                                                 refreshState: MutableLiveData<RefreshState>? = null,
                                                 responsState: MutableLiveData<ResponseState>? = null,
                                                 params: PageKeyedDataSource.LoadParams<Int>,
                                                 callback: PageKeyedDataSource.LoadCallback<Int, Value>,
                                                 limitSize: Int = 0) {
    launch({
        val res = response()
        if (res.Flag == HTTP_SUC) {
            responsState?.postValue(ResponseState.HttpSuccess(res))
        } else {
            responsState?.postValue(ResponseState.HttpSpecial(res))
        }
        if (res.data.isEmpty()) {
            refreshState?.postValue(RefreshState.LoadNoMoreData())
        } else {
            if (limitSize == 0) {
                callback.onResult(res.data, params.key + 1)
            }
            refreshState?.postValue(RefreshState.LoadFinish())
        }
    }, {
        Log.e(TAG,"load error :${it.message}")
        responsState?.postValue(ResponseState.HttpFail(createErrorResponse(it.message)))
    })
}