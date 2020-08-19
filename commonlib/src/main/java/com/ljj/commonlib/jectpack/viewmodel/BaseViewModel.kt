package com.ljj.commonlib.jectpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.common.lib.jetpack.LoadState
import com.ljj.commonlib.jectpack.state.DialogState
import com.ljj.commonlib.jectpack.state.ResponseState
import com.ljj.commonlib.jectpack.state.ViewState

abstract class BaseViewModel : ViewModel() {
    val loadState = MutableLiveData<LoadState>()
    val viewState = MutableLiveData<ViewState>()
    val responseState = MutableLiveData<ResponseState>()

    val toastState = MutableLiveData<String>()
    val loadingDialogState = MutableLiveData<DialogState>()
}
