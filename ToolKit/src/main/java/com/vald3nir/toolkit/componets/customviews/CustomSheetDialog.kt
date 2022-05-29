package com.vald3nir.toolkit.componets.customviews

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vald3nir.toolkit.databinding.CustomItemListBinding
import com.vald3nir.toolkit.databinding.CustomSheetDialogBinding

class CustomSheetDialog(context: Context, private val items: List<CustomItemSheet>) :
    BottomSheetDialog(context) {

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CustomSheetDialogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.rvItems.apply {
            adapter = SheetDialogAdapter()
            layoutManager = LinearLayoutManager(context)
        }
    }

    inner class SheetDialogAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(CustomItemListBinding.inflate(layoutInflater))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindView(items[position])
        }

        override fun getItemCount(): Int = items.size
    }

    inner class ViewHolder(private val customItemSheet: CustomItemListBinding) :
        RecyclerView.ViewHolder(customItemSheet.root) {

        fun bindView(item: CustomItemSheet) {
            customItemSheet.apply {
                root.setOnClickListener {
                    dismiss()
                    item.action.invoke()
                }
                txvLabel.text = item.title
                imvIcon.setImageResource(item.icon)
            }
        }
    }

    data class CustomItemSheet(
        val icon: Int,
        val title: String?,
        val action: () -> Unit
    )

}