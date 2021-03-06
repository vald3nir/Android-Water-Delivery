package com.vald3nir.diskwater.common.componets

import android.content.Context
import android.util.AttributeSet
import com.vald3nir.diskwater.R
import com.vald3nir.toolkit.core.componets.customviews.CustomButton

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
            rootDrawable = R.drawable.button_white_layout,
            clickListener = clickListener,
        )
    }
}