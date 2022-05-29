package com.vald3nir.diskwater.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseViewModel
import com.vald3nir.diskwater.data.form.DataUserInputForm
import com.vald3nir.diskwater.domain.use_cases.auth.AuthUseCase
import com.vald3nir.diskwater.domain.use_cases.register.RegisterUseCase
import com.vald3nir.diskwater.domain.utils.isAppSalesman
import com.vald3nir.toolkit.validations.isEmailValid
import com.vald3nir.toolkit.validations.isPasswordValid
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {

    private val _registerForm = MutableLiveData<DataUserInputForm>()
    val registerFormState: LiveData<DataUserInputForm> = _registerForm

    fun registerNewUser(email: String, password: String, confirmPassword: String) {
        if (checkRegisterData(email, password, confirmPassword)) {
            appView?.showLoading(true)
            viewModelScope.launch {
                registerUseCase.registerNewUser(
                    appView = appView,
                    email = email,
                    password = password,
                    onSuccess = { registerUserType() },
                    onError = { showError(it) }
                )
            }
        }
    }

    private fun registerUserType() {
        viewModelScope.launch {
            registerUseCase.registerUserType(
                appView = appView,
                userID = authUseCase.getUserID().orEmpty(),
                isSalesman = isAppSalesman(),
                onSuccess = { finish() },
                onError = { showError(it) }
            )
        }
    }

    fun checkRegisterData(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

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

        if (password != confirmPassword) {
            isValid = false
            dataUserInputForm.confirmPasswordError = getString(R.string.passwords_not_equals)
        }

        if (!isValid) {
            _registerForm.value = dataUserInputForm
        }

        return isValid
    }
}