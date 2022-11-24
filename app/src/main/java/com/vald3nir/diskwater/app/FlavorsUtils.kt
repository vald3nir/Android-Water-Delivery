package com.vald3nir.diskwater.app

import com.vald3nir.diskwater.BuildConfig

fun isAppClient(): Boolean {
    return BuildConfig.FLAVOR == "client"
}

fun isAppSalesman(): Boolean {
    return BuildConfig.FLAVOR == "salesman"
}