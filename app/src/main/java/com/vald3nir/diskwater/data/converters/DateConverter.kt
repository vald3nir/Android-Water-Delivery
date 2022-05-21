package com.vald3nir.diskwater.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vald3nir.diskwater.data.dto.OrderItemDTO
import java.util.*

class DateConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<OrderItemDTO?>? {
        if (data == null) return Collections.emptyList()
        val listType = object : TypeToken<List<OrderItemDTO?>?>() {}.type
        return gson.fromJson<List<OrderItemDTO?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<OrderItemDTO?>?): String? {
        return gson.toJson(someObjects)
    }
}