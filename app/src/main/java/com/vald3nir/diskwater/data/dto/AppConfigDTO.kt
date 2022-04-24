package com.vald3nir.diskwater.data.dto

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class AppConfigDTO(
    val autoLogin: Boolean = false,
    val ipServer: String? = null
) {
    constructor() : this(false, null) {}

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "autoLogin" to autoLogin,
            "ipServer" to ipServer,
        )
    }
}