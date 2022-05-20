package com.vald3nir.diskwater.presentation.main

import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.address.AddressUseCase
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase

class MainViewModel(
    private val screenNavigation: ScreenNavigation
) : BaseViewModel() {}