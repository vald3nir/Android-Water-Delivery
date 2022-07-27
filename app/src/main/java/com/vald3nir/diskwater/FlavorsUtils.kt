package com.vald3nir.diskwater

fun isAppClient(): Boolean {
    return BuildConfig.FLAVOR == "client"
}

fun isAppSalesman(): Boolean {
    return BuildConfig.FLAVOR == "salesman"
}