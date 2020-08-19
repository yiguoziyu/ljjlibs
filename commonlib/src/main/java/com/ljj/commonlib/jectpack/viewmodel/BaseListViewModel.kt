package com.ljj.commonlib.jectpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ljj.commonlib.jectpack.repostitory.Listing

abstract class BaseListViewModel<T> : ViewModel() {
    val listingLiveData = MutableLiveData<Listing<T>>()
    val pagedList = Transformations.switchMap(listingLiveData) {
        it.pagedList
    }
    val viewState = Transformations.switchMap(listingLiveData) {
        it.viewState
    }
    val refreshState = Transformations.switchMap(listingLiveData) {
        it.refreshState
    }
    val responseState = Transformations.switchMap(listingLiveData) {
        it.responseState
    }

    fun refresh() {
        listingLiveData.value?.refresh?.invoke()
    }

    abstract fun init()
}
