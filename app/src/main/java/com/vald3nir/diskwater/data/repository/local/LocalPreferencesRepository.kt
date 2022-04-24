package com.vald3nir.diskwater.data.repository.local

import com.vald3nir.diskwater.data.dto.LoginDTO

interface LocalPreferencesRepository {
    fun saveLoginPreferences(loginDTO: LoginDTO?)
    fun loadLoginPreferences(): LoginDTO?
}