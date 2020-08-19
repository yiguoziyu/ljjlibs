package com.ljj.commonlib.jectpack

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.common.lib.jetpack.LoadState
import com.ljj.commonlib.jectpack.state.ViewState
import kotlinx.coroutines.*

fun <T> PositionalDataSource<T>.launchWithLoading(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {},
    httpState: MutableLiveData<LoadState>? = null,
    loadingState: MutableLiveData<ViewState>? = null
) {
    GlobalScope.launch {
        try {
            loadingState?.postValue(ViewState.LoadingShow())
            block.invoke(this)
        } catch (e: Exception) {
            httpState?.postValue(LoadState.Fail(e.message ?: ""))
            onError(e)
        } finally {
            loadingState?.postValue(ViewState.LoadingDismiss())
            onComplete()
        }
    }
}

fun <T> PositionalDataSource<T>.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {},
    httpState: MutableLiveData<LoadState>? = null,
    loadingState: MutableLiveData<ViewState>? = null
) {
    GlobalScope.launch {
        try {
            loadingState?.postValue(ViewState.ViewProgress())
            withContext(Dispatchers.IO) {
                block.invoke(this)
            }
        } catch (e: Exception) {
            httpState?.postValue(LoadState.Fail(e.message ?: ""))
            loadingState?.postValue(ViewState.ViewFail())
            onError(e)
        } finally {
            onComplete()
        }
    }
}


fun <T, K> PageKeyedDataSource<T, K>.launch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Throwable) -> Unit = {},
    onComplete: () -> Unit = {},
    httpState: MutableLiveData<LoadState>? = null,
    viewState: MutableLiveData<ViewState>? = null
) {
    GlobalScope.launch {
        try {
            block.invoke(this)
        } catch (e: Exception) {
            viewState?.postValue(ViewState.ViewFail())
            onError(e)
        } finally {
            onComplete()
        }
    }
}

