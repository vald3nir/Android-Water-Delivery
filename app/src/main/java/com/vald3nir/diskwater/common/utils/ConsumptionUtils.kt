package com.vald3nir.diskwater.common.utils

import android.content.Context
import android.graphics.Color
import com.vald3nir.diskwater.R

const val MAX_VALUE_POWER_CONSUMPTION = 2000
const val MEDIA_VALUE_POWER_CONSUMPTION = 300
const val INTERMEDIARY_VALUE_POWER_CONSUMPTION = 450


fun getColorPowerValue(power: Double): Int {
    return when {
        power <= MEDIA_VALUE_POWER_CONSUMPTION -> Color.GREEN
        power <= INTERMEDIARY_VALUE_POWER_CONSUMPTION -> Color.YELLOW
        else -> Color.RED
    }
}

fun getPowerStatus(context: Context, power: Double): String {
    return when {
        power <= MEDIA_VALUE_POWER_CONSUMPTION -> context.getString(R.string.normal)
        power <= INTERMEDIARY_VALUE_POWER_CONSUMPTION -> context.getString(R.string.moderate)
        else -> context.getString(R.string.high)
    }
}

