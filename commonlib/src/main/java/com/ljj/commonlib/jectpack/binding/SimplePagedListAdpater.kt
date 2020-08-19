package com.ljj.commonlib.jectpack.binding

import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil

abstract class SimplePagedListAdpater<T>(private val layoutId: Int, private val variableId: Int, @NonNull diffCallback: DiffUtil.ItemCallback<T>)
    : BasePagedListAdpater<T>(diffCallback) {
    companion object {
        const val TAG = "SimplePagedListAdpater"
    }

    override fun getLayoutId(): Int = layoutId
    var itemClickListener: ListItemClick<T>? = null
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.apply {
                setVariable(variableId, it)
                bindData(this, position)
                executePendingBindings()
            }
        }
    }

    open fun bindData(bind: ViewDataBinding, position: Int) {
        bind.root.setOnClickListener {
            itemClickListener?.onItemClick(it, getItem(position))
        }
    }

}

