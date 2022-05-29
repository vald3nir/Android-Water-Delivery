package com.vald3nir.toolkit.data

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Ignore
import com.google.gson.Gson
import java.util.UUID.randomUUID

open class BaseDTO(
    @Ignore val uid: String = randomUUID().toString()
) {

    fun toJson(): String? {
        return Gson().toJson(this)
    }

    fun <T> fromJson(json: String?, classOfT: Class<T>): T {
        return Gson().fromJson(json, classOfT)
    }
}

fun <T> baseDiffUtil(): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is BaseDTO && newItem is BaseDTO)
            oldItem.uid == newItem.uid
        else
            false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
//        if (oldItem is BaseDTO && newItem is BaseDTO)
        return oldItem == newItem
//        else return false
    }
}