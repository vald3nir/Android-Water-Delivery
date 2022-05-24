package com.vald3nir.diskwater.common.componets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.vald3nir.diskwater.databinding.CustomListComponentBinding

class CustomListComponent : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    init {
        orientation = VERTICAL
    }

    private val binding = CustomListComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setTitle(title: String?, backgroundColor: Int) {
        setTabs(listOf<String>("Pedidos ativos", "Pedidos Fechados"))
    }

    fun setTabs(tabs: List<String>) {
        binding.txtTitle.apply {
            tabs.forEach { addTab(newTab().setText(it)) }
            tabGravity = TabLayout.GRAVITY_FILL
        }
    }

    fun notifyListSize(size: Int) {
        binding.apply {
            rcvItems.isVisible = size > 0
            txtListEmpty.isVisible = size == 0
        }
    }

    fun getRecyclerView(): RecyclerView {
        return binding.rcvItems
    }
}

