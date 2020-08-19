package com.common.lib.kit.util

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class BaseProxy<T : Any> constructor(val target: T) : InvocationHandler {
    fun create(): T {
        return Proxy.newProxyInstance(target.javaClass.classLoader, target.javaClass.interfaces, this) as T
    }

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        return method?.invoke(target, *args.orEmpty())
    }
}