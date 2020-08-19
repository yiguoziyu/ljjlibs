package com.ljj.commonlib.jectpack.repostitory

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ljj.commonlib.jectpack.state.RefreshState
import com.ljj.commonlib.jectpack.state.ResponseState
import com.ljj.commonlib.jectpack.state.ViewState

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val viewState: LiveData<ViewState>,
    val refreshState: LiveData<RefreshState>,
    val responseState:LiveData<ResponseState>,
    val refresh: () -> Unit
)