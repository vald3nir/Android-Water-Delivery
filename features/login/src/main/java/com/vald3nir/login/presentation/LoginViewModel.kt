package com.vald3nir.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.commom.domain.dtos.ClientDTO
import com.vald3nir.commom.domain.dtos.LoginDTO
import com.vald3nir.commom.presentation.view.BaseViewModel
import com.vald3nir.login.R
import com.vald3nir.login.domain.form.DataUserInputForm
import com.vald3nir.login.domain.usecases.AuthUseCase
import com.vald3nir.utils.validations.isEmailValid
import com.vald3nir.utils.validations.isPasswordValid
import kotlinx.coroutines.launch

class LoginViewModel(
    val titleScreen: String,
    val showRegisterButton: Boolean,
    private val authUseCase: AuthUseCase,
) : BaseViewModel() {

    private val _loginForm = MutableLiveData<DataUserInputForm>()
    val loginFormState: LiveData<DataUserInputForm> = _loginForm

    private val _registerForm = MutableLiveData<DataUserInputForm>()
    val registerFormState: LiveData<DataUserInputForm> = _registerForm

    private val _loginDTO = MutableLiveData<LoginDTO?>()
    val loginDTO: LiveData<LoginDTO?> = _loginDTO

    fun loadLoginData() {
        viewModelScope.launch {
            _loginDTO.postValue(authUseCase.loadLoginData(requireActivityContext()))
        }
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
            finishWithResult()
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

    fun registerNewUser(
        email: String,
        password: String,
        confirmPassword: String,
        onError: (e: Exception?) -> Unit
    ) {
        if (checkRegisterData(email, password, confirmPassword)) {
            viewModelScope.launch {
                authUseCase.registerNewUser(
                    activity = requireActivityContext(),
                    email = email,
                    password = password,
                    onSuccess = { registerUserType() },
                    onError = { onError.invoke(it) }
                )
            }
        }
    }

    private fun registerUserType() {
        viewModelScope.launch {
            authUseCase.registerClient(
                activity = requireActivityContext(),
                clientDTO = ClientDTO(
                    name = null,
                    userID = authUseCase.getUserID().orEmpty(),
                    photo = null,
                    telephone = null,
                    email = null,
                    address = null,
                ),
                onSuccess = { onBackPressed() },
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

        if (!isPasswordValid(confirmPassword)) {
            isValid = false
            dataUserInputForm.confirmPasswordError = getString(R.string.invalid_password)
        }

        if (confirmPassword.isNotBlank() && password != confirmPassword) {
            isValid = false
            dataUserInputForm.confirmPasswordError = getString(R.string.passwords_not_equals)
        }

        if (!isValid) {
            _registerForm.value = dataUserInputForm
        }

        return isValid
    }
}