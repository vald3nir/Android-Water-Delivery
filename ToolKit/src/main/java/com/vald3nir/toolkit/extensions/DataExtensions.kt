package com.vald3nir.toolkit.extensions

import android.text.Editable
import com.google.gson.Gson


fun Float?.toMoney(): String {
    return "R$ $this"
}

fun Editable?.format(): String {
    return this.toString().trim()
}

fun <T> String?.toDTO(classOfT: Class<T>): T {
    return Gson().fromJson(this, classOfT)
}