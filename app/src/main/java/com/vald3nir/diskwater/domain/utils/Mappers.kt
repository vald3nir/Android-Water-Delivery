package com.vald3nir.diskwater.domain.utils

import com.vald3nir.toolkit.data.dto.BaseDTO

//fun <T> MutableList<T>?.toMutableBaseList(): MutableList<BaseDTO> {
//    val baseList: MutableList<BaseDTO> = mutableListOf()
//    this.map { if (it is BaseDTO) baseList.add(it) }
//    return baseList
//}

fun <T> List<T>?.toMutableBaseList(): MutableList<BaseDTO> {
    val baseList: MutableList<BaseDTO> = mutableListOf()
    this?.map { if (it is BaseDTO) baseList.add(it) }
    return baseList
}