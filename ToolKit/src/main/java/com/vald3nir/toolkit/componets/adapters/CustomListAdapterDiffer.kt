package com.vald3nir.toolkit.componets.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.vald3nir.toolkit.data.BaseDTO

class CustomListAdapterDiffer<Item, Binding : ViewBinding>(
    val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> Binding,
    var list: Collection<Item>,
    itemDiffUtil: DiffUtil.ItemCallback<Item>,
    val onBind: (Item, Binding, Int, CustomListAdapterDiffer<Item, Binding>) -> Unit
) : ListAdapter<Item, CustomListAdapterDiffer<Item, Binding>.ViewHolder>(itemDiffUtil) {

    init {
        submitList(list.toMutableList())
    }

    private var itemClickListener: ((item: Item, pos: Int) -> Unit)? = null
    private var itemLongClickListener: ((itemView: View, item: Item, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false), this
        )
    }

    fun addItem(item: Item, toStart: Boolean, onAdded: (() -> Unit)? = null) {
        list = currentList
        val index = if (toStart) 0 else (currentList.size)
        list.toMutableList().apply {
            add(index, item)
            list = this
        }
        submitListAsync(list) {
            onAdded?.invoke()
        }
    }

    fun removeItem(item: Item, pos: Int, onRemoved: (() -> Unit)? = null) {
        list = currentList
        list.toMutableList().apply {
            remove(item)
            list = this
        }
        submitListAsync(list) {
            onRemoved?.invoke()
        }
    }

    fun submitListAsync(newList: Collection<Item>, onChanged: (() -> Unit)? = null) {
        submitList(newList.toMutableList()) {
            list = currentList
            onChanged?.invoke()
        }
    }

    override fun submitList(list: MutableList<Item>?, commitCallback: Runnable?) {
        super.submitList(list) {
            this.list = currentList
            commitCallback?.run()
        }
    }

    override fun submitList(list: MutableList<Item>?) {
        super.submitList(list)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentList[position]?.let { item ->
            (holder as? ViewHolder)?.bindItem(
                item,
                position
            )
        }
    }

    inner class ViewHolder(
        private val binding: Binding,
        private val genericAdapter: CustomListAdapterDiffer<Item, Binding>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Item, position: Int) {
            itemView.setOnClickListener {
                itemClickListener?.invoke(item, layoutPosition)
            }
            itemView.setOnLongClickListener {
                itemLongClickListener?.invoke(itemView, item, layoutPosition)
                true
            }
            onBind(item, binding, position, genericAdapter)
        }
    }

    fun setOnItemClickListener(listener: (item: Item, pos: Int) -> Unit) {
        itemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (itemView: View, item: Item, pos: Int) -> Unit) {
        itemLongClickListener = listener
    }
}

