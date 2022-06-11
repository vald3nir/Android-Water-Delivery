package com.vald3nir.diskwater.presentation.login

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.vald3nir.diskwater.R
import com.vald3nir.diskwater.common.BaseActivity
import com.vald3nir.diskwater.databinding.ActivityLoginBinding
import com.vald3nir.toolkit.extensions.actionDoneListener
import com.vald3nir.toolkit.extensions.afterTextChanged
import com.vald3nir.toolkit.extensions.format
import com.vald3nir.toolkit.extensions.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun registerViewModel() {
        viewModel.registerController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.initViews()
        binding.setupObservers()
        viewModel.loadLoginData()
    }

    override fun onBackPressed() {
    }

    private fun ActivityLoginBinding.initViews() {
        txvAppName.text = getTypeAppName()
        btnRegister.isVisible = viewModel.showRegisterButton
        btnLogin.setButtonTitle(R.string.login)
    }

    private fun ActivityLoginBinding.setupObservers() {
        btnLogin.setButtonClickListener { login() }
        btnRegister.setOnClickListener { register() }

        viewModel.loginDTO.observe(this@LoginActivity) {

            edtEmail.setText(it?.email)
            edtPassword.setText(it?.password)
            cbRememberLogin.isChecked = it?.rememberLogin == true

            edtEmail.afterTextChanged { loginDataChanged() }
            edtPassword.apply {
                afterTextChanged { loginDataChanged() }
                actionDoneListener { login() }
            }
        }

        viewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            edtEmailLayout.error = loginState.emailError
            edtPasswordLayout.error = loginState.passwordError
            btnLogin.showLoading(false)
        })
    }

    private fun ActivityLoginBinding.clearError() {
        edtEmailLayout.error = null
        edtPasswordLayout.error = null
    }

    private fun ActivityLoginBinding.loginDataChanged() {
        clearError()
        viewModel.checkLoginData(
            edtEmail.text.format(),
            edtPassword.text.format()
        )
    }

    private fun ActivityLoginBinding.login() {
        hideKeyboard()
        btnLogin.showLoading(true)
        viewModel.login(
            email = edtEmail.text.format(),
            password = edtPassword.text.format(),
            rememberLogin = cbRememberLogin.isChecked
        ) {
            btnLogin.showLoading(false)
            showMessage(it)
        }
    }

    private fun register() {
        viewModel.register()
    }
}