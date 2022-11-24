package com.vald3nir.diskwater.domain

import com.vald3nir.core_repository.BaseDTO

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