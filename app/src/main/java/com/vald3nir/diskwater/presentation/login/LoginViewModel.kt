package com.vald3nir.diskwater.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.core.BaseViewModel
import com.vald3nir.diskwater.common.utils.isAppClient
import com.vald3nir.diskwater.common.validations.isEmailValid
import com.vald3nir.diskwater.common.validations.isPasswordValid
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.form.DataUserInputForm
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val screenNavigation: ScreenNavigation,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {

    private val _loginForm = MutableLiveData<DataUserInputForm>()
    val loginFormState: LiveData<DataUserInputForm> = _loginForm

    val showRegisterButton: Boolean = isAppClient()

    private val _loginDTO = MutableLiveData<LoginDTO?>()
    val loginDTO: LiveData<LoginDTO?> = _loginDTO

    fun loadLoginData() {
        viewModelScope.launch {
            authUseCase.loadLoginData(
                onSuccess = { _loginDTO.postValue(it) },
                onError = { showMessage(it?.message) }
            )
        }
    }

    fun register() {
        screenNavigation.redirectToRegister(view)
    }

    fun login(email: String, password: String, rememberLogin: Boolean) {

        viewModelScope.launch {
            if (checkLoginData(email, password)) {
                showLoading(true)

                val loginDTO = LoginDTO(
                    email = email,
                    password = password,
                    rememberLogin = rememberLogin
                )

                authUseCase.login(appView = view, loginDTO = loginDTO,
                    onSuccess = { saveLoginData(loginDTO) },
                    onError = {
                        showLoading(false)
                        showMessage(it?.message)
                    }
                )
            }
        }
    }

    private fun saveLoginData(loginDTO: LoginDTO) {
        viewModelScope.launch {
            authUseCase.saveLoginData(
                loginDTO,
                onSuccess = { screenNavigation.redirectToHome(view) })
        }
    }

    fun checkLoginData(email: String, password: String): Boolean {
        var isValid = true
        val dataUserInputForm = DataUserInputForm()

        if (!isEmailValid(email)) {
            isValid = false
            dataUserInputForm.emailError = getString(R.string.invalid_email)
        }

        if (!isPasswordValid(password)) {
            isValid = false
            dataUserInputForm.passwordError = getString(R.string.invalid_password)
        }

        if (!isValid) {
            _loginForm.value = dataUserInputForm
        }

        return isValid
    }
}