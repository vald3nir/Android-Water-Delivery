package com.vald3nir.toolkit.componets.lists

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.vald3nir.toolkit.extensions.actionClickListener
import com.vald3nir.toolkit.databinding.CustomListComponentBinding

class CustomListComponent : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    init {
        orientation = VERTICAL
    }

    data class CustomListTab(val title: String, val onTabSelectedListener: () -> Unit)

    private val binding = CustomListComponentBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setTabs(tabs: List<CustomListTab>) {
        binding.tblOptions.apply {
            tabs.forEach { tab ->
                addTab(newTab().setText(tab.title))
            }
            actionClickListener { position ->
                tabs[position].onTabSelectedListener.invoke()
            }
            isVisible = true
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

