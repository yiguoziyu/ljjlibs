package com.ljj.commonlib.jectpack.repostitory

import androidx.lifecycle.switchMap
import androidx.paging.toLiveData

abstract class BaseRepository<T> {
    abstract fun createFactory(): BaseDataSourceFactory<T>
    fun init(): Listing<T> {
        val sourceFactory = createFactory()
        val livePagedList = sourceFactory.toLiveData(pageSize = 1)
        val viewState = sourceFactory.sourceLiveData.switchMap {
            it.viewState
        }
        val refreshState = sourceFactory.sourceLiveData.switchMap {
            it.refreshState
        }
        val responseState = sourceFactory.sourceLiveData.switchMap {
            it.responseState
        }
        return Listing(
                pagedList = livePagedList,
                viewState = viewState,
                refreshState = refreshState,
                responseState = responseState,
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                }
        )
    }
}