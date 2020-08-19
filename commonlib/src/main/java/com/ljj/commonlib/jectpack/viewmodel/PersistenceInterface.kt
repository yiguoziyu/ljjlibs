package com.ljj.commonlib.jectpack.viewmodel

/***
 * 持久化统一接口
 */
interface PersistenceInterface<V> {
    fun insert(value: V)

    fun update(func: V.() -> Unit)

    fun clear()

    fun query(): V
}