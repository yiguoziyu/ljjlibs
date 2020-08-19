package com.ljj.commonlib.jectpack.livedata

import java.util.*

object LiveDataBus {
    private var bus: MutableMap<String, ResponseLiveData<Any>?> = HashMap()
    @Synchronized
    fun <T> with(key: String): ResponseLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = ResponseLiveData()
        }
        return bus[key] as ResponseLiveData<T>
    }
}