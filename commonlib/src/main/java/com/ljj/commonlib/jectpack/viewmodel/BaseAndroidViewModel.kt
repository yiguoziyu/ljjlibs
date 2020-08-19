package com.ljj.commonlib.jectpack.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.common.lib.jetpack.LoadState
import com.ljj.commonlib.jectpack.state.RefreshState
import com.ljj.commonlib.jectpack.state.ResponseState
import com.ljj.commonlib.jectpack.state.ViewState

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    val loadState = MutableLiveData<LoadState>()
    val viewState = MutableLiveData<ViewState>()
    val responseState = MutableLiveData<ResponseState>()
    val toastState = MutableLiveData<String>()
    val refreshState=MutableLiveData<RefreshState>()
}
