package com.ljj.commonlib.jectpack.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ljj.commonlib.jectpack.state.RefreshState
import com.ljj.commonlib.jectpack.state.ResponseState
import com.ljj.commonlib.jectpack.state.ViewState
import com.ljj.commonlib.model.BaseResponse
import retrofit2.Response

abstract class BasePageKeyedDataSource<T> : PageKeyedDataSource<Int, T>() {
    val viewState = MutableLiveData<ViewState>()
    val refreshState = MutableLiveData<RefreshState>()
    val responseState = MutableLiveData<ResponseState>()
    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Int>, callback: PageKeyedDataSource.LoadInitialCallback<Int, T>) {
        refresh({
            refresh()
        }, viewState = viewState,
                refreshState = refreshState,
                responsState = responseState,
                callback = callback,
                nextPageKey = PAGE_FIRST + 1,
                limitSize = setLimitSize())
    }

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, T>) {
        load({
            load(params.key)
        }, refreshState = refreshState,
                responsState = responseState,
                params = params,
                callback = callback,
                limitSize = setLimitSize())
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, T>) {
    }

    abstract fun refresh(): Response<BaseResponse<List<T>>>
    abstract suspend fun load(page: Int): BaseResponse<List<T>>
    open fun setLimitSize(): Int {
        return 0
    }
}