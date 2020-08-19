package com.ljj.commonlib.jectpack.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagedListAdpater<T>(@NonNull diffCallback: DiffUtil.ItemCallback<T>)
    : PagedListAdapter<T, BaseViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), getLayoutId(), parent, false)
        bindView(viewDataBinding)
        return BaseViewHolder(viewDataBinding)
    }

    open fun bindView(bind: ViewDataBinding) {

    }

    abstract fun getLayoutId(): Int
}

