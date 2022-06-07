package com.vald3nir.diskwater.domain.utils

import com.vald3nir.diskwater.data.dto.ProductDTO
import com.vald3nir.toolkit.data.dto.BaseDTO

fun MutableList<ProductDTO>.toMutableBaseList(): MutableList<BaseDTO> {
    val baseList: MutableList<BaseDTO> = mutableListOf()
    this.map { baseList.add(it) }
    return baseList
}