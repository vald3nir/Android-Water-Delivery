package com.vald3nir.diskwater.data.dto

import androidx.room.PrimaryKey
import com.google.gson.Gson

open class BaseDTO(
    @PrimaryKey(autoGenerate = false) var uid: Int = 0
) {

    fun toJson(): String? {
        return Gson().toJson(this)
    }

    fun <T> fromJson(json: String?, classOfT: Class<T>): T {
        return Gson().fromJson(json, classOfT)
    }

}