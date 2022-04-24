package com.vald3nir.diskwater.common.extensions

import android.text.Editable
import com.google.gson.Gson


fun Editable?.format(): String {
    return this.toString().trim()
}

fun <T> String?.toDTO(classOfT: Class<T>): T {
    return Gson().fromJson(this, classOfT)
}