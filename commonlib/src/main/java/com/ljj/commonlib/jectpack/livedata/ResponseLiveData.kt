package com.ljj.commonlib.jectpack.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


open class ResponseLiveData<T> : MutableLiveData<T>() {
    companion object{
        const val TAG="ResponseLiveData"
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        try {
            hook(observer as Observer<T>)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 反射技术  使observer.mLastVersion = mVersion
     *
     * @param observer ob
     */
    @Throws(java.lang.Exception::class)
    private fun hook(observer: Observer<T>) { //根据源码 如果使observer.mLastVersion = mVersion; 就不会走回调
        //            OnChange方法了，所以就算注册
        //也不会收到消息
        //首先获取liveData的class
        val classLiveData = LiveData::class.java
        //通过反射获取该类里mObserver属性对象
        val fieldObservers = classLiveData.getDeclaredField("mObservers")
        //设置属性可以被访问
        fieldObservers.isAccessible = true
        //获取的对象是this里这个对象值，他的值是一个map集合
        val objectObservers = fieldObservers[this]
        //获取map对象的类型
        val classObservers: Class<*> = objectObservers.javaClass
        //获取map对象中所有的get方法
        val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
        //设置get方法可以被访问
        methodGet.isAccessible = true
        //执行该get方法，传入objectObservers对象，然后传入observer作为key的值
        val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
        //定义一个空的object对象
        var objectWrapper: Any? = null
        //判断objectWrapperEntry是否为Map.Entry类型
        if (objectWrapperEntry is Map.Entry<*, *>) {
            objectWrapper = objectWrapperEntry.value
        }
        if (objectWrapper == null) {
            throw NullPointerException("Wrapper can not be null!")
        }
        //如果不是空 就得到该object的父类
        val classObserverWrapper: Class<*>? = objectWrapper.javaClass.superclass
        //通过他的父类的class对象，获取mLastVersion字段
        val fieldLastVersion = classObserverWrapper!!.getDeclaredField("mLastVersion")
        fieldLastVersion.isAccessible = true
        val fieldVersion = classLiveData.getDeclaredField("mVersion")
        fieldVersion.isAccessible = true
        val objectVersion = fieldVersion[this]
        //把mVersion 字段的属性值设置给mLastVersion
        fieldLastVersion[objectWrapper] = objectVersion
    }
}