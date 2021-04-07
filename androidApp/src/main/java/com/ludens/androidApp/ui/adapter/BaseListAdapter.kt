package com.ludens.androidApp.ui.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ludens.shared.core.ListItem

abstract class BaseListAdapter<Item : ListItem, ViewHolder : BindingViewHolder<Item, out ViewBinding>> :
    ListAdapter<Item, ViewHolder>(DiffUtilCallback()) {

    override fun onViewRecycled(holder: ViewHolder) {
        holder.clear()
        super.onViewRecycled(holder)
    }
}

abstract class BindingViewHolder<Item, Binding : ViewBinding>(private val binding: Binding) : RecyclerView.ViewHolder(binding.root) {

    fun render(item: Item) = binding.render(item)

    fun clear() = binding.clear()

    abstract fun Binding.render(item: Item)

    open fun Binding.clear() {
        // Template
    }
}
