package com.ljj.commonlib.jectpack.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseMutableListAdpater()
    : RecyclerView.Adapter<BaseViewHolder>() {
    abstract fun getLayoutIds(): MutableList<RecyclerViewBean>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), getLayoutIds()[viewType].layoutId, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        getLayoutIds().forEachIndexed { index, recyclerViewBean ->
            if (recyclerViewBean.type == list[position].javaClass) {
                return index
            }
        }
        return 0
    }

    override fun getItemCount(): Int = list.size

    val list = mutableListOf<Any>()
    var itemClick: RecyclerviewItemClick? = null

    fun setData(data: MutableList<Any>) {
        list.clear()
        list.addAll(data)
    }

    fun addHeader(position: Int, data: Any) {
        list.add(position,data)
    }


    fun addData(data: Any) {
        list.add(data)
    }

    fun addData(data: MutableList<Any>) {
        list.addAll(data)
    }

    fun removeData(data: Any) {
        list.remove(data)
    }


}


