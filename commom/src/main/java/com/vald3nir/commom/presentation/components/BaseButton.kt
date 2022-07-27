package com.vald3nir.commom.presentation.components

import android.content.Context
import android.util.AttributeSet
import com.vald3nir.commom.R
import com.vald3nir.core_ui.components.CustomButton

class BaseButton : CustomButton {

    constructor(context: Context?) : super(context) {
        initComponent()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initComponent()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initComponent()
    }

    private fun initComponent() {
        orientation = VERTICAL
    }

    fun setup(
        title: Int,
        clickListener: () -> Unit,
    ) {
        setup(
            title = context.getString(title),
            titleColor = R.color.blue,
            rootDrawable = R.drawable.base_button_layout,
            clickListener = clickListener,
        )
    }
}