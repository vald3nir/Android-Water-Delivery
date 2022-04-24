package com.vald3nir.diskwater.common.componets

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.vald3nir.diskwater.R

class CustomImageView : AppCompatImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    fun showLoadingAnimation() {
        startAnimation(
            AnimationUtils.loadAnimation(context, R.anim.rotate_indefinitely)
        )
    }
}