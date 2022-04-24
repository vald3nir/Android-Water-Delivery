package com.vald3nir.diskwater.data.repository.local

import android.content.Context
import android.content.SharedPreferences

open class LocalRepository(private val context: Context) {

    companion object {
        const val KEY_LOGIN_DATA = "KEY_LOGIN_DATA"
    }

    fun getLocalPreferences(): SharedPreferences {
        return context.getSharedPreferences("Local Preferences", Context.MODE_PRIVATE)
    }

    fun getLocalPreferencesEditor(): SharedPreferences.Editor {
        return getLocalPreferences().edit()
    }
}