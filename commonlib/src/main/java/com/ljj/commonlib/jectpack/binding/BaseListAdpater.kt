package com.ljj.commonlib.jectpack.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdpater()
    : RecyclerView.Adapter<BaseViewHolder>() {
    abstract fun getLayoutId(): Int
    override fun getItemCount(): Int = list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), getLayoutId(), parent, false))
    }

    val list = mutableListOf<Any>()
    var itemClick: RecyclerviewItemClick? = null

    fun setData(data: MutableList<out Any>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: Any) {
        list.add(data)
        notifyDataSetChanged()
    }

    fun addData(data: MutableList<out Any>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun removeData(data: Any) {
        list.remove(data)
        notifyDataSetChanged()
    }


}

interface RecyclerviewItemClick {
    fun onItemClick(view: View, postion: Int, data: Any)
}

