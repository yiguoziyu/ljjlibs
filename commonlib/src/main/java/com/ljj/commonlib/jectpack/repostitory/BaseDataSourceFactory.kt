package com.ljj.commonlib.jectpack.repostitory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ljj.commonlib.jectpack.paging.BasePageKeyedDataSource

abstract class BaseDataSourceFactory<value> : DataSource.Factory<Int, value>() {

    val sourceLiveData = MutableLiveData<BasePageKeyedDataSource<value>>()

    abstract fun createDataSource(): BasePageKeyedDataSource<value>

    override fun create(): DataSource<Int, value> {
        val source = createDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}