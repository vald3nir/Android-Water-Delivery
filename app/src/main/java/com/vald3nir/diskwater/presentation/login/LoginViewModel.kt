package com.vald3nir.diskwater.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.dto.LoginDTO
import com.vald3nir.diskwater.data.form.DataUserInputForm
import com.vald3nir.diskwater.domain.navigation.ScreenNavigation
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import com.vald3nir.diskwater.domain.utils.isAppClient
import com.vald3nir.toolkit.utils.validations.isEmailValid
import com.vald3nir.toolkit.utils.validations.isPasswordValid
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
            _loginDTO.postValue(authUseCase.loadLoginData(requireActivityContext()))
        }
    }

    fun register() {
        screenNavigation.redirectToRegister(requireActivityContext())
    }

    fun login(
        email: String,
        password: String,
        rememberLogin: Boolean,
        onError: (e: Exception?) -> Unit
    ) {
        viewModelScope.launch {
            if (checkLoginData(email, password)) {

                val loginDTO = LoginDTO(
                    email = email,
                    password = password,
                    rememberLogin = rememberLogin
                )

                authUseCase.login(activity = requireActivityContext(), loginDTO = loginDTO,
                    onSuccess = {
                        saveLoginData(loginDTO)
                    },
                    onError = {
                        onError.invoke(it)
                    }
                )
            }
        }
    }

    private fun saveLoginData(loginDTO: LoginDTO) {
        viewModelScope.launch {
            authUseCase.saveLoginData(requireActivityContext(), loginDTO)
            screenNavigation.redirectToHome(requireActivityContext())
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