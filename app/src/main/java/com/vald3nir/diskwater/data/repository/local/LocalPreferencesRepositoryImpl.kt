package com.vald3nir.diskwater.data.repository.local

import android.content.Context
import com.vald3nir.toolkit.extensions.toDTO
import com.vald3nir.diskwater.data.dto.LoginDTO

class LocalPreferencesRepositoryImpl(context: Context) : LocalRepository(context),
    LocalPreferencesRepository {

    override fun saveLoginPreferences(loginDTO: LoginDTO?) {
        val sharedPref = getLocalPreferencesEditor()
        sharedPref.putString(KEY_LOGIN_DATA, loginDTO?.toJson())
        sharedPref.apply()
    }

    override fun loadLoginPreferences(): LoginDTO? {
        val sharedPref = getLocalPreferences()
        val json: String? = sharedPref.getString(KEY_LOGIN_DATA, null)
        return json?.toDTO(LoginDTO::class.java)
    }
}