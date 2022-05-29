package com.vald3nir.toolkit.componets.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class CustomGenericAdapter<Item, Binding : ViewBinding>(
    var bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> Binding,
    var list: Collection<Item>,
    var onBind: (Item, Binding, Int, CustomGenericAdapter<Item, Binding>) -> Unit
) : RecyclerView.Adapter<CustomGenericAdapter<*, *>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false), this
        )
    }

    fun update(list: List<Item>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: CustomGenericAdapter<*, *>.ViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(
        private val binding: Binding,
        private val customGenericAdapter: CustomGenericAdapter<Item, Binding>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(position: Int) {
            list.elementAt(position).apply {
                onBind.invoke(this, binding, position, customGenericAdapter)
            }
        }
    }
}
