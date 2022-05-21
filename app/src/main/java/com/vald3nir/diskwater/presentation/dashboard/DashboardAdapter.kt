package com.vald3nir.diskwater.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vald3nir.diskwater.data.dto.OrderDTO
import com.vald3nir.diskwater.databinding.OrderItemViewBinding

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    private val items = ArrayList<OrderDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = OrderItemViewBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(orders: List<OrderDTO>) {
        items.clear()
        items.addAll(orders)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: OrderItemViewBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        fun bind(orderDTO: OrderDTO) {
            itemView.apply {

            }
        }

    }
}
