package com.vald3nir.toolkit.componets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.vald3nir.toolkit.R
import com.vald3nir.toolkit.databinding.CustomToolbarBinding

class HeaderNavigationComponent : LinearLayout {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initAttrs(attrs)
    }

    init {
        orientation = VERTICAL
    }

    private val binding = CustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.HeaderNavigationComponent
        )
        setTitle(
            typedArray.getString(
                R.styleable.HeaderNavigationComponent_title
            )
        )
        showBackButton(
            typedArray.getBoolean(
                R.styleable.HeaderNavigationComponent_showBackButton,
                false
            )
        )
        typedArray.recycle()
    }

    fun setTitle(title: String?) {
        binding.title.text = title
    }

    fun showBackButton(show: Boolean) {
        binding.btnBack.isVisible = show
    }

    fun setBackButtonClickListener(listener: () -> Unit) {
        binding.btnBack.setOnClickListener { listener.invoke() }
    }

    fun showMenuButton(show: Boolean) {
        binding.btnConfig.isVisible = show
    }

    fun setMenuButtonClickListener(listener: () -> Unit) {
        binding.btnConfig.setOnClickListener { listener.invoke() }
    }
}

